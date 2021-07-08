/**
 * @author Rían Errity
 * @since 10/04/2021 DDMMYYYY
 * @implSpec An abstract class that provides the operations (methods) which any type of Connect Four Player class must override.
 * */
public abstract class ConnectPlayer {

    /**
     * Represents a numerical ID which the player's token will be both identified and labelled as.
      */
    private final short playerId;

    public ConnectPlayer(short playerId) {
        this.playerId = playerId;
    }

    /**
     * Determines where this player is going to place their next token
     * */
    public abstract int determineMove();

    public short getPlayerId() {
        return playerId;
    }
}
