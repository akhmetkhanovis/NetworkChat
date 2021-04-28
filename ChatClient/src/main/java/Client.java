import java.io.*;
import java.util.Scanner;

public class Client {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        try (Connection connection = new Connection(getIP(), getPort())) {

            System.out.println("Connected to server");
            System.out.println(connection.readLine());
            String name = scanner.nextLine();
            connection.writeLine(name);

            new Thread(() -> {
                while (true) {
                    try {
                        System.out.println(connection.readLine());
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
                connection.writeLine(message);
                if (message.equals("/exit")) {
                    connection.close();
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
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

    private static String getIP() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FileNames.SETTINGS.toString()))) {
            String[] settings = reader.readLine().split(":");
            return settings[0];
        } catch (IOException e) {
            System.out.println("Failed to get settings from file");
            throw new RuntimeException(e);
        }
    }
}
