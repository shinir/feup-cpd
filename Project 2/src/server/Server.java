package server;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Server {
    private static final int NUM_GAMES = 2;
    private static final int MAX_CONCURRENT_GAMES = 5;
    private static final int PLAYERS_PER_GAME = 2; // Number of players per game
    private static final long RANK_MODE_UPDATE_INTERVAL = 60000; // Interval to update rank mode (milliseconds)
    private static final int RANK_MODE_LEVEL_DIFFERENCE_THRESHOLD = 3; // Level difference threshold for rank mode

    private ServerSocket serverSocket;
    private ExecutorService threadPool;
    private List<Game> activeGames;
    private List<Socket> authenticatedUsers;
    private String credentialsFilePath; // Path to the credentials file
    private int port;
    private boolean rankMode;
    private Map<Socket, Integer> userLevels;

    public Server(int port, boolean rankMode) {
        activeGames = new ArrayList<>();
        authenticatedUsers = new ArrayList<>();
        threadPool = Executors.newFixedThreadPool(MAX_CONCURRENT_GAMES);
        credentialsFilePath = "credentials.txt"; // Specify the file path to save the credentials
        this.port = port;
        this.rankMode = rankMode;
        userLevels = new HashMap<>();
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Game server started on port " + port);

            if (rankMode) {
                Thread rankModeThread = new Thread(this::updateRankMode);
                rankModeThread.start();
            }

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
    
            // Authenticate user or create a new user
            String message = reader.readLine();
            if (message.equals("NEW_USER")) {
                // Create a new user
                String username = reader.readLine();
                String password = reader.readLine();
    
                // Save the new user's information to the file
                saveCredentials(username, password);
    
                writer.println("New user created successfully. You can now authenticate and enter the game queue.");
            } else {
                // Authenticate existing user
                String username = message;
                String password = reader.readLine();
    
                // Check if the credentials match
                if (checkCredentials(username, password)) {
                    writer.println("Authenticated successfully. You are now in the game queue.");
    
                    synchronized (authenticatedUsers) {
                        authenticatedUsers.add(clientSocket);
    
                        // Check if we have enough authenticated users to start a game
                        if (authenticatedUsers.size() >= PLAYERS_PER_GAME) {
                            List<Socket> gameUsers = new ArrayList<>(authenticatedUsers.subList(0, PLAYERS_PER_GAME));
                            authenticatedUsers.removeAll(gameUsers);
    
                            Game game = new Game(gameUsers);
                            activeGames.add(game);
                            threadPool.execute(() -> game.start());
                        }
                    }
                } else {
                    writer.println("Authentication failed. Please try again.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveCredentials(String username, String password) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(credentialsFilePath, true));
            writer.write(username + ":" + password);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkCredentials(String username, String password) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(credentialsFilePath));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    reader.close();
                    return true;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void updateRankMode() {
        while (true) {
            try {
                Thread.sleep(RANK_MODE_UPDATE_INTERVAL);

                synchronized (authenticatedUsers) {
                    // Update user levels based on their game scores
                    for (Socket userSocket : authenticatedUsers) {
                        // Simulate updating user levels based on game scores
                        int level = userLevels.getOrDefault(userSocket, 0);
                        int newLevel = level + new Random().nextInt(3) - 1; // Randomly increase/decrease level by 0, 1, or 2
                        userLevels.put(userSocket, newLevel);
                    }

                    // Matchmaking algorithm to create teams of similar levels
                    while (authenticatedUsers.size() >= PLAYERS_PER_GAME) {
                        List<Socket> gameUsers = new ArrayList<>();
                        for (int i = 0; i < PLAYERS_PER_GAME; i++) {
                            // Find the user with the closest level to the average level of the remaining users
                            int averageLevel = authenticatedUsers.stream()
                                    .mapToInt(userSocket -> userLevels.getOrDefault(userSocket, 0))
                                    .sum() / authenticatedUsers.size();

                            Socket closestUser = null;
                            int closestLevelDifference = Integer.MAX_VALUE;

                            for (Socket userSocket : authenticatedUsers) {
                                int userLevel = userLevels.getOrDefault(userSocket, 0);
                                int levelDifference = Math.abs(userLevel - averageLevel);
                                if (levelDifference < closestLevelDifference) {
                                    closestUser = userSocket;
                                    closestLevelDifference = levelDifference;
                                }
                            }

                            gameUsers.add(closestUser);
                            authenticatedUsers.remove(closestUser);
                        }

                        Game game = new Game(gameUsers);
                        activeGames.add(game);
                        threadPool.execute(() -> game.start());
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // Start the server in simple or rank mode based on command-line argument
        boolean rankMode = args.length > 0 && args[0].equalsIgnoreCase("rank");
        int port = 8888;
        for (int i = 0; i < NUM_GAMES; i++) {
            Server gameServer = new Server(port, rankMode);
            gameServer.start();
        }
    }
}
