import java.io.*;
import java.net.*;

public class GameClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8888;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Welcome to the game! Please authenticate to play.");
            System.out.println("1. Existing User");
            System.out.println("2. New User");

            // User authentication
            String choice = consoleReader.readLine();
            if (choice.equals("1")) {
                // Existing User
                System.out.print("Username: ");
                String username = consoleReader.readLine();
                writer.println(username);

                System.out.print("Password: ");
                String password = consoleReader.readLine();
                writer.println(password);
            } else if (choice.equals("2")) {
                // New User
                System.out.println("Creating a new user.");
                System.out.print("Enter a username: ");
                String username = consoleReader.readLine();

                System.out.print("Enter a password: ");
                String password = consoleReader.readLine();

                writer.println("NEW_USER");
                writer.println(username);
                writer.println(password);
            } else {
                System.out.println("Invalid choice.");
                socket.close();
                return;
            }

            String response = reader.readLine();
            System.out.println(response);

            // Enter the game queue and continue with the rest of the code

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
