# Java-WebSocket-Demo

Small example client-server application using Java WebSockets for networking and Gson for data serialization.

WebSockets allow you to open a connection and keep it open. Traditionally HTTP is a request, response model, 
and it closes the connection after each data packet it sent.

WebSockets allow a open and persistent connection so you don't have to close it after each data packet is sent. 
This allows for real-time functionality in apps (like Google Docs), games etc. 
[[1]](https://www.reddit.com/r/explainlikeimfive/comments/1fpwqx/eli5_what_are_websockets/cc307f6)

The project uses Gradle, so it should just work when you open it in Intellij.
First start Server.java then Client.java files.

Serializer.java class converts your data objects to JSON strings and vice versa.

You can read more about Java Sockets and find more examples from [here](https://github.com/TooTallNate/Java-WebSocket).
