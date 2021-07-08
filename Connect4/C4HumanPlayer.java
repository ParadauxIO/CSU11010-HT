import java.util.Scanner;

/**
 * @author Rían Errity
 * @since 10/04/2021 DDMMYYYY
 * @implSpec A class which extends the ConnectPlayer abstract class and whose objects represent a human player (a user) who is involved in
 *           a Connect Four game.
 * */
public class C4HumanPlayer extends ConnectPlayer {

    private final Scanner scanner;

    public C4HumanPlayer(short playerId, Scanner scanner) {
        super(playerId);
        this.scanner = scanner;
    }

    @Override
    public int determineMove() {
        System.out.print("Player " + super.getPlayerId() + ": Where would you like to put your next token? (0-6) : ");
        return scanner.nextShort();
    }
}
