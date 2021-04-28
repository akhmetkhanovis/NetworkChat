import java.io.*;
import java.net.ServerSocket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {

    private static final int PORT = 9199;
    private static final BlockingQueue<Connection> connections = new LinkedBlockingQueue<>();
    private static final Logger logger = Logger.getInstance();

    public static void main(String[] args) throws IOException {

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            System.out.println("Server started");
            logger.log("Server started");

            while (true) {
                Connection connection = new Connection(serverSocket);
                connections.add(connection);

                new Thread(() -> {
                    try {
                        connection.writeLine("SERVER: Type your nickname");
                        String name = connection.readLine();

                        sendToChat("SERVER: Welcome, " + name + "! To leave chat type /exit");
                        logger.log(connection + " (" + name + ") connected");

                        String message;
                        while (true) {
                            try {
                                message = connection.readLine();
                                if (message.equals("/exit")) {
                                    connections.remove(connection);
                                    logger.log(connection + " (" + name + ")" + " disconnected");
                                    sendToChat("SERVER: " + name + " has left chat");
                                    break;
                                }
                                sendToChat(name + ": " + message);
                                logger.log(connection + " (" + name + ") wrote to chat: " + message);
                            } catch (Exception e) {
                                connections.remove(connection);
                                logger.log(connection + " (" + name + ")" + " disconnected");
                                sendToChat("SERVER: " + name + " was disconnected");
                                break;
                            }

                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }).start();

            }

        } catch (IOException e) {
            logger.log("Client disconnected");
        }

    }

    public static void sendToChat(String msg) {
        System.out.println(msg);
        for (Connection c : connections) {
            try {
                c.writeLine(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
