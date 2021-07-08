import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;

/**
 * @author Rían Errity
 * @implSpec A class whose objects represent an executing Connect Four two-player game application. The class will allow a user to start
 * a new Connect Four game involving two players whose types are chosen by the user. This class consists of a mainline which
 * handles user input. The class creates a grid using the connect4Grid interface and a human player and a computer player using
 * the player abstract class.
 * @since 10/04/2021 DDMMYYYY
 */
public class Connect4Game {

    private static final HashSet<String> QUIT_COMMANDS = new HashSet<>();

    static {
        Collections.addAll(QUIT_COMMANDS, "quit", "leave", "stop");
    }

    public static void main(String[] args) {
        System.out.println("Connect 4: Rían Errity");
        Scanner scanner = new Scanner(System.in);

        Connect4Grid2DArray gameBoard = new Connect4Grid2DArray();

        System.out.print("Player 1 Type (ai, human): ");
        ConnectPlayer player1 = scanner.nextLine().equals("ai") ? new C4RandomAIPlayer((short) 1) : new C4HumanPlayer((short) 1, scanner);

        System.out.print("Player 2 Type (ai, human): ");
        ConnectPlayer player2 = scanner.nextLine().equals("ai") ? new C4RandomAIPlayer((short) 2) : new C4HumanPlayer((short) 2, scanner);

        boolean hasQuit = false;

        // Rather than determining if the grid is full it would be less computationally expensive to tell if we are just on turn 42 or not.
        int currentTurn = 0;

        do {
            System.out.print("Would you like to continue playing, quit or clear the board? (play, quit, clear) : ");
            String operation = scanner.nextLine();

            if (QUIT_COMMANDS.contains(operation.trim())) {
                hasQuit = true;
            } else {
                if (operation.equalsIgnoreCase("clear")) {
                    currentTurn = 0;
                    gameBoard.emptyGrid();
                }

                System.out.println("Turn " + currentTurn);
                System.out.println("Determining moves for players..");

                gameBoard.dropPiece(player1, player1.determineMove());
                System.out.println(gameBoard);

                gameBoard.dropPiece(player2, player2.determineMove());
                System.out.println(gameBoard);

                if (gameBoard.didLastPieceConnect4()) {
                    System.out.println("Player " + gameBoard.getWinner() + " has won!");
                    return;
                }

                if (gameBoard.isGridFull()) {
                    System.out.println("Looks like it was a tie! The board is full");
                    return;
                }

                currentTurn++;
            }

        } while (!hasQuit);

        scanner.close();
    }
}
