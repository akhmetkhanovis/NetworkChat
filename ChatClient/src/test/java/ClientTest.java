import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ClientTest {

    @Test
    public void testGetPortFromSettings() {
        var expected = 9199;

        int actual;

        try (BufferedReader reader = new BufferedReader(new FileReader("../settings.txt"))) {
            var settings = reader.readLine().split(":");
            actual = Integer.parseInt(settings[1]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals(expected, actual);

    }

    @Test
    public void testGetIpFromSettings() {
        var expected = "127.0.0.1";

        String actual;

        try (BufferedReader reader = new BufferedReader(new FileReader("../settings.txt"))) {
            String[] settings = reader.readLine().split(":");
            actual = settings[0];
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals(expected, actual);

    }
}
