/**
 * @author Rían Errity
 * @since 10/04/2021 DDMMYYYY
 * @implNote Specifying public in an interface is redundant.
 * */
public interface IConnect4Grid {

    /**
     * Resets the game board (re-initialises the 2D array with 0)
     * */
    void emptyGrid();

    /**
     * Stringifies the game board as a 2D Matrix
     * */
    @Override
    String toString();

    /**
     * Determines if the specified column is indeed valid
     * */
    boolean isValidColumn(int column);

    /**
     * Determines if the specified column is full (no room for further chips)
     * */
    boolean isColumnFull(int column);

    /**
     * "drops" a token into the game board.
     * */
    void dropPiece(ConnectPlayer player, int column);

    /**
     * Determines if the last move end the game
     * */
    boolean didLastPieceConnect4();

    /**
     * Determines if the grid is full.
     * */
    boolean isGridFull();

}
