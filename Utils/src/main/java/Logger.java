import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    private static Logger instance = null;

    private Logger() {}

    public static Logger getInstance() {
        if (instance == null) instance = new Logger();
        return instance;
    }

    public void log(String msg) throws IOException {
        FileWriter writer = new FileWriter(FileNames.FILE_LOG.toString(), true);
        writer.write("["
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))
                + "] " + msg + "\r\n");
        writer.flush();
    }
}
