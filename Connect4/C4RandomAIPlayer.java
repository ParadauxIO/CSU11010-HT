import java.util.Random;

/**
 * @author Rían Errity
 * @since 10/04/2021 DDMMYYYY
 * @implSpec A class which extends the ConnectPlayer abstract class and whose objects represent Random AI Player (a computer) which is
 *           involved in a Connect Four game.
 * @implNote I could've implemented a more complex AI which uses some more advanced modelling, although given it had "random" in the name and
 *           the level of students in the class, I presume this is what was intended.
 * */
public class C4RandomAIPlayer extends ConnectPlayer {

    private final Random random;

    public C4RandomAIPlayer(short playerId) {
        super(playerId);
        this.random = new Random();
    }

    @Override
    public int determineMove() {
        return random.nextInt(6);
    }

}
