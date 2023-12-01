package server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private List<Socket> playerSockets;
    private int[] playerGuesses;
    private int currentPlayerIndex;
    private int secretNumber;

    public Game(List<Socket> playerSockets) {
        this.playerSockets = playerSockets;
        this.playerGuesses = new int[playerSockets.size()];
        this.currentPlayerIndex = 0;
        this.secretNumber = generateSecretNumber();
    }

    public void start() {
        try {
            PrintWriter[] writers = new PrintWriter[playerSockets.size()];
            BufferedReader[] readers = new BufferedReader[playerSockets.size()];
            Scanner scanner = new Scanner(System.in);


            for (int i = 0; i < playerSockets.size(); i++) {
                Socket socket = playerSockets.get(i);
                writers[i] = new PrintWriter(socket.getOutputStream(), true);
                readers[i] = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            }

            for (int i = 0; i < playerSockets.size(); i++) {
                writers[i].println("Welcome to the Pick a Number game! You are Player " + (i + 1));
            }

            boolean gameEnded = false;

            while (!gameEnded) {
                Socket currentPlayerSocket = playerSockets.get(currentPlayerIndex);
                PrintWriter currentPlayerWriter = writers[currentPlayerIndex];
                BufferedReader currentPlayerReader = readers[currentPlayerIndex];

                currentPlayerWriter.println("Your turn. Guess a number between 1 and 100:");

                System.out.println("Computer's number:" + secretNumber);

                // Read the integer input from the console
                //String inputLine = currentPlayerReader.readLine();
                //int guess = Integer.parseInt(inputLine);
                int guess = scanner.nextInt();

                System.out.println("User's guess:" + guess);

                currentPlayerWriter.println("Your guess was: " + guess);

                if (guess < 0) {
                    handleDisconnect(writers[0], writers[1]);
                    break;
                }

                if (guess < secretNumber) {
                    currentPlayerWriter.println("Higher!");
                } else if (guess > secretNumber) {
                    currentPlayerWriter.println("Lower!");
                } else {
                    currentPlayerWriter.println("Correct! You win!");
                    gameEnded = true;
                    break;
                }

                currentPlayerIndex = (currentPlayerIndex + 1) % playerSockets.size();
            }

            // Close sockets
            for (Socket socket : playerSockets) {
                socket.close();
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleDisconnect(PrintWriter player1Writer, PrintWriter player2Writer) {
        player1Writer.println("The other player has disconnected. The game is over.");
        player2Writer.println("The other player has disconnected. The game is over.");
    }

    private int generateSecretNumber() {
        Random random = new Random();
        return random.nextInt(100) + 1; // Generate a random number between 1 and 100
    }

    private int readIntegerInputFromConsole() {
        Scanner scanner = new Scanner(System.in);
        int input;

        try {
            input = scanner.nextInt();
        } catch (Exception e) {
            input = -1; // Set an invalid value to handle disconnect
        }

        return input;
    }
}
