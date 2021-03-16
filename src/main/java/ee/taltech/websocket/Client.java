package ee.taltech.websocket;

import ee.taltech.websocket.model.Message;
import ee.taltech.websocket.model.User;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.Scanner;

public class Client extends WebSocketClient {
    private final Serializer<Message> serializer = new Serializer<>(Message.class);
    private User user;

    public Client(URI serverUri, Draft draft) {
        super(serverUri, draft);
    }

    public Client(URI serverURI, User user) {
        super(serverURI);
        this.user = user;
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        Message message = new Message(user, String.format("Hello, my name is %s!", user.getName()));
        send(serializer.encode(message));
        System.out.println("New connection opened.");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Closed with exit code " + code + " additional info: " + reason);
    }

    @Override
    public void onMessage(String message) {
        Message messageDecoded = serializer.decode(message);
        String value = String.format("%s: %s",
                messageDecoded.getAuthor().getName(),
                messageDecoded.getContent());
        System.out.println(value);
    }

    @Override
    public void onMessage(ByteBuffer message) {
        System.out.println("Received ByteBuffer");
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("An error occurred:" + ex);
    }

    public static void main(String[] args) throws URISyntaxException {
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name = keyboard.nextLine();
        User user = new User(name);

        WebSocketClient client = new Client(new URI("ws://localhost:8887"), user);
        client.connect();
        Serializer<Message> serializer = new Serializer<>(Message.class);

        while (true) {
            System.out.println("\n[ENTER NEW MESSAGE]");
            String text = keyboard.nextLine();
            Message message = new Message(user, text);
            client.send(serializer.encode(message));
        }
    }
}
