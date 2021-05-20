import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerTest {

    @Test
    public void testSendMessageToAllClients() throws IOException, InterruptedException {
        Connection connServer = new Connection(new ServerSocket(9199));
        Thread.sleep(1000);
        Connection connClient = new Connection("127.0.0.1", 9199);
        String test = "hello";

        Assertions.assertEquals(test, connClient.readLine());
    }
}
