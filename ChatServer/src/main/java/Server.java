import java.io.*;
import java.net.ServerSocket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {

    private static final BlockingQueue<Connection> connections = new LinkedBlockingQueue<>();

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(getPort())) {

            System.out.println("Server started");

            while (true) {
                Connection connection = new Connection(serverSocket);
                connections.add(connection);

                new Thread(() -> {
                    try {
                        connection.writeLine("SERVER: Type your nickname");
                        String name = connection.readLine();

                        sendToChat("SERVER: Welcome, " + name + "! To leave chat type /exit");

                        String message;
                        while (true) {
                            try {
                                message = connection.readLine();
                                if (message.equals("/exit")) {
                                    connections.remove(connection);
                                    sendToChat("SERVER: " + name + " has left chat");
                                    break;
                                }
                                sendToChat(name + ": " + message);
                            } catch (Exception e) {
                                connections.remove(connection);
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
            e.printStackTrace();
        }

    }

    public static void sendToChat(String msg) {
        String now = getLocalDateTimeNow();
        System.out.println(msg);
        for (Connection c : connections) {
            try {
                c.writeLine(now + msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static int getPort() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FileNames.SETTINGS.toString()))) {
            String[] settings = reader.readLine().split(":");
            return Integer.parseInt(settings[1]);
        } catch (IOException e) {
            System.out.println("Failed to get settings from file");
            throw new RuntimeException(e);
        }
    }

    public static String getLocalDateTimeNow() {
        return "[" +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")) +
                "] ";
    }

}
