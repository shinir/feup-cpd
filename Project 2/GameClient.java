import java.io.*;
import java.net.*;

public class GameClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8888;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

// Send a message to the server
            writer.println("AUTHENTICATE");
            writer.println(username);
            writer.println(password);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
