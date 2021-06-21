import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;

public class ConnectionTest {

    @BeforeAll
    public static void setUp() {
        new Thread(() -> {
            try {
                var connServer = new Connection(new ServerSocket(8080));
                connServer.writeLine("test");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Test
    public void testSendingMessage() {
        var expected = "test";

        new Thread(() -> {
            var connClient = new Connection("127.0.0.1", 8080);
            String actual;
            try {
                actual = connClient.readLine();
                System.out.println("Expected: " + expected);
                System.out.println("Actual: " + actual);
                Assertions.assertEquals(expected, actual);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();
    }
}
