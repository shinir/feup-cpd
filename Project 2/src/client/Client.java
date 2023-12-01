package client;
import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 8888;

    public static void main(String[] args) {
        try {
            Socket clientSocket = new Socket(SERVER_IP, SERVER_PORT);

            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);

            // Authentication or user creation
            System.out.println("Welcome to the game!\nPlease enter your username:");
            Scanner scanner = new Scanner(System.in);
            String username = scanner.nextLine();

            System.out.println("Enter your password:");
            String password = scanner.nextLine();

            // Check if user wants to create a new user
            System.out.println("Do you want to create a new user? (Y/N)");
            String newUserOption = scanner.nextLine();

            if (newUserOption.equalsIgnoreCase("Y")) {
                // Send "NEW_USER" message to the server
                writer.println("NEW_USER");
                writer.println(username);
                writer.println(password);

                String response = reader.readLine();
                System.out.println(response);
            } else {
                // Send the username and password for authentication
                writer.println(username);
                writer.println(password);

                String response = reader.readLine();
                System.out.println(response);

                if (response.contains("Authenticated")) {
                    // Enter the game queue
                    System.out.println("You are now in the game queue. Waiting for other players...");

                    // Receive the player token from the server
                    //String token = reader.readLine();
                    //System.out.println("Player token: " + token);

                    // Keep reading server messages until the game starts
                    String gameMessage = reader.readLine();
                    while (!gameMessage.contains("Starting game")) {
                        System.out.println(gameMessage);
                        gameMessage = reader.readLine();
                    }

                    // Game started, print the initial game message
                    System.out.println(gameMessage);

                    // Continue reading and displaying game messages until game ends
                    String gameResult = reader.readLine();
                    while (!gameResult.contains("Game over")) {
                        System.out.println(gameResult);
                        gameResult = reader.readLine();
                    }

                    // Print the final game result
                    System.out.println(gameResult);
                }
            }

            // Close the client socket
            clientSocket.close();
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}