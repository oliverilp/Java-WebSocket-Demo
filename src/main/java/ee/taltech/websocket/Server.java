package ee.taltech.websocket;

import ee.taltech.websocket.model.Message;
import ee.taltech.websocket.model.User;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

public class Server extends WebSocketServer {
    private final User serverUser = new User("SERVER");
    private final Serializer<Message> serializer = new Serializer<>(Message.class);

    public Server(InetSocketAddress address) {
        super(address);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        Message message = new Message(serverUser, "Welcome to the server!");
        conn.send(serializer.encode(message)); //This method sends a message to the new client

        message = new Message(serverUser, "New connection: " + conn.getRemoteSocketAddress());
        broadcast(serializer.encode(message)); //This method sends a message to all clients connected
        System.out.println("New connection to " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("Closed " + conn.getRemoteSocketAddress() + " with exit code " + code + " additional info: " + reason);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        Message messageDecoded = serializer.decode(message);
        String value = String.format("[%s] %s: %s",
                conn.getRemoteSocketAddress(),
                messageDecoded.getAuthor().getName(),
                messageDecoded.getContent());
        System.out.println(value);
        broadcast(message);
    }

    @Override
    public void onMessage(WebSocket conn, ByteBuffer message) {
        System.out.println("Received ByteBuffer from " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.err.println("An error occurred on connection." + conn.getRemoteSocketAddress() + ":" + ex);
    }

    @Override
    public void onStart() {
        System.out.println("Server started successfully!");
    }


    public static void main(String[] args) {
        String host = "localhost";
        int port = 8887;

        WebSocketServer server = new Server(new InetSocketAddress(host, port));
        server.run();
    }
}
