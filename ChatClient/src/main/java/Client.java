import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Client {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Logger logger = Logger.getInstance();

    public static void main(String[] args) {

        try (Connection connection = new Connection(getIP(), getPort())) {

            System.out.println("Connected to server");
            logger.log(getLocalDateTimeNow() + "You have connected to server");
            System.out.println(connection.readLine());
            String name = scanner.nextLine();
            connection.writeLine(name);

            new Thread(() -> {
                while (true) {
                    try {
                        String income = connection.readLine();
                        System.out.println(income);
                        logger.log(income);
                    } catch (IOException e) {
                        try {
                            connection.close();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        break;
                    }
                }

            }).start();

            while (true) {
                String message = scanner.nextLine();
                if (message.equals("")) continue;
                if (message.equals("/exit")) {
                    logger.log(getLocalDateTimeNow() + "You have disconnected from server");
                    connection.close();
                    break;
                }
                connection.writeLine(message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getPort() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(FileNames.SETTINGS.toString()))) {
            String[] settings = reader.readLine().split(":");
            return Integer.parseInt(settings[1]);
        } catch (IOException e) {
            System.out.println("Failed to get PORT from settings file");
            logger.log(getLocalDateTimeNow() + "Failed to get PORT from settings file");
            throw new RuntimeException(e);
        }
    }

    private static String getIP() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(FileNames.SETTINGS.toString()))) {
            String[] settings = reader.readLine().split(":");
            return settings[0];
        } catch (IOException e) {
            System.out.println("Failed to get IP from settings file");
            logger.log(getLocalDateTimeNow() + "Failed to get IP from settings file");
            throw new RuntimeException(e);
        }
    }

    public static String getLocalDateTimeNow() {
        return "[" +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")) +
                "] ";
    }
}
