import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Connection implements Closeable {

    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public Connection(String ip, int port) {
        try {
            this.socket = new Socket(ip, port);
            this.reader = createReader();
            this.writer = createWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Connection(ServerSocket serverSocket) {
        try {
            this.socket = serverSocket.accept();
            this.reader = createReader();
            this.writer = createWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void writeLine(String message) throws IOException {
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            this.close();
        }
    }

    public String readLine() throws IOException {
        return reader.readLine();
    }

    private BufferedReader createReader() throws IOException {
        return new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream()));
    }

    private BufferedWriter createWriter() throws IOException {
        return new BufferedWriter(
                new OutputStreamWriter(
                        socket.getOutputStream()));
    }

    @Override
    public String toString() {
        return "Client: " + socket.getInetAddress() + ":" + socket.getPort();
    }

    @Override
    public void close() throws IOException {
        writer.close();
        reader.close();
        socket.close();
    }

}
