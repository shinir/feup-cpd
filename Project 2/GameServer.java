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
            if (authenticateUser(reader, writer)) {
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

    private boolean authenticateUser(BufferedReader reader, PrintWriter writer) throws IOException {
        writer.println("Enter your username:");
        String username = reader.readLine();

        writer.println("Enter your password:");
        String password = reader.readLine();

        // Add your authentication logic here (e.g., check against a user database)

        return true; // Placeholder for successful authentication
    }

    public static void main(String[] args) {
        GameServer gameServer = new GameServer();
        gameServer.start();
    }
}
