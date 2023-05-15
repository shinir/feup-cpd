import java.net.Socket;
import java.util.List;

public class Game {
    public static final int NUM_PLAYERS = 2; // Change the number of players as per your game's requirements
    private List<Socket> userSockets;

    public Game(List<Socket> userSockets) {
        this.userSockets = userSockets;
    }

    public void start() {
        // Code to start the game
        System.out.println("Starting game with " + userSockets.size() + " players");

        // escolher aleatoriamente quem vai ser o primeiro a escolher a palavra
        // escolher a palavra
        // aleatoriamente atribuir uma ordem de jogada
        // jogo começa

        // SCORE
        // acertar a letra -> +200xp
        // errar a letra -> -20xp
        // acertar a palavra -> +1000xp
        // errar a palavra -> -100xp


        // Implement your game logic here
        // You can communicate with players over their respective sockets
        // Example:
        // PrintWriter player1Writer = new PrintWriter(userSockets.get(0).getOutputStream(), true);
        // BufferedReader player1Reader = new BufferedReader(new InputStreamReader(userSockets.get(0).getInputStream()));
        // player1Writer.println("Welcome to the game! Let's begin.");
        // String player1Move = player1Reader.readLine();
        // ...
    }
}
