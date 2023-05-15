import java.net.Socket;
import java.util.List;

import java.io.*;
import java.util.*;

public class Game {
    private static Map<String, String> registeredUsers = new HashMap<>();
    private static String registeredUsersFilePath = "registeredUsers.txt";
    private List<Socket> userSockets;

    public Game(int players, List<Socket> userSockets) {
        this.userSockets = userSockets;
    }

    public void start() {
        // Code to start the game
        System.out.println("Starting game with " + userSockets.size() + " players");
        
    }

    public static void main(String[] args) {
        // Register users
        registerUser("user1", "password1");
        registerUser("user2", "password2");
        
        // Authenticate user
        String username = "user1";
        String password = "password1";
        boolean authenticated = authenticateUser(username, password);
        if (authenticated) {
            System.out.println("User " + username + " authenticated successfully.");
            // Play game instances
            playGameInstance();
            playGameInstance();
        } else {
            System.out.println("User " + username + " not authenticated.");
        }
    }

    private static void registerUser(String username, String password) {
        // Add user to registeredUsers map
        registeredUsers.put(username, password);

        // Persist registration data in file
        try (PrintWriter writer = new PrintWriter(new FileWriter(registeredUsersFilePath, true))) {
            writer.println(username + "," + password);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean authenticateUser(String username, String password) {
        String storedPassword = registeredUsers.get(username);
        return storedPassword != null && storedPassword.equals(password);
    }

    private static void playGameInstance() {
        // Game logic goes here
        System.out.println("Playing game instance...");
    }
}
