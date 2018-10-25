import java.io.FileWriter;
import java.io.IOException;

class Logs {
    FileWriter writer;

    Logs() throws IOException {
        writer = new FileWriter("output.txt");
    }

    void close() throws IOException {
        writer.flush();
        writer.close();
    }
}
