import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class LoggerTest {

    @BeforeAll
    public static void setUp() throws IOException {
        var logger = Logger.getInstance();
        var expected = "test";
        logger.log(expected);
    }

    @Test
    public void testLogger() {
        var expected = "test";
        var list = new ArrayList<String>();

        try (Scanner scan = new Scanner(new BufferedReader(new FileReader("../Utils/file.log")))) {
            while (scan.hasNextLine()) {
                list.add(scan.nextLine());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        var actual = list.get(list.size() - 1);
        Assertions.assertEquals(expected, actual);
        System.out.println("Expected: " + expected);
        System.out.println("Actual: " + actual);


    }


}
