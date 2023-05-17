import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class GameServer {
    private static final int PORT = 8888;
    private static final int MAX_CONCURRENT_GAMES = 5;

    private ServerSocket serverSocket;
    private ExecutorService threadPool;
    private List<Game> activeGames;
    private List<Socket> authenticatedUsers;

    public GameServer() {
        activeGames = new ArrayList<>();
        authenticatedUsers = new ArrayList<>();
        threadPool = Executors.newFixedThreadPool(MAX_CONCURRENT_GAMES);
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Game server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New connection from: " + clientSocket.getInetAddress());

                // Create a new thread to handle the client's requests
                Thread clientThread = new Thread(() -> handleClient(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket clientSocket) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);

            // Authenticate user
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Welcome to the game! Please authenticate to play.");
            System.out.println("1. Existing User");
            System.out.println("2. New User");

            String choice = consoleReader.readLine();

            if (authenticateUser(reader, writer, choice)) {
                authenticatedUsers.add(clientSocket);
                writer.println("Authenticated successfully. You are now in the game queue.");

                // Check if we have enough authenticated users to start a game
                if (authenticatedUsers.size() >= Game.NUM_PLAYERS) {
                    List<Socket> gameUsers = new ArrayList<>(authenticatedUsers.subList(0, Game.NUM_PLAYERS));
                    authenticatedUsers.removeAll(gameUsers);

                    Game game = new Game(gameUsers);
                    activeGames.add(game);
                    threadPool.execute(() -> game.start());
                }
            } else {
                writer.println("Authentication failed. Please try again.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean authenticateUser(BufferedReader reader, PrintWriter writer, String choice) throws IOException {
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
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
            return false;
        }

        String response = reader.readLine();
        if (response.equals("AUTHENTICATED")) {
            System.out.println("Authenticated successfully.");
            return true;
        } else {
            System.out.println("Authentication failed. Please try again.");
            return false;
        }
    }

    public static void main(String[] args) {
        GameServer gameServer = new GameServer();
        gameServer.start();
    }
}
