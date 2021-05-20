import java.io.FileWriter;
import java.io.IOException;

public class Logger {

    private static Logger instance = null;

    private Logger() {}

    public synchronized static Logger getInstance() {
        if (instance == null) instance = new Logger();
        return instance;
    }

    public synchronized void log(String msg) throws IOException {
        FileWriter writer = new FileWriter(FileNames.FILE_LOG.toString(), true);
        writer.write(msg + "\r\n");
        writer.flush();
    }
}
