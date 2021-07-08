import java.util.ArrayList;
import java.util.List;

/**
 * @author Rían Errity
 * @since 10/04/2021 DDMMYYYY
 * @implSpec This class implements the Connect4Grid interface and provides appropriate functionality for each of the methods in the
 *           interface
 * */
public class Connect4Grid2DArray implements IConnect4Grid {

    private static final int BOARD_WIDTH = 7;
    private static final int BOARD_HEIGHT = 6;

    private short winner;

    /**
     * This represents the game board.
     * 0 implies an empty slot;
     * 1 and 2 represent a token from player1 and player2 respectively
     * */
    private final short[][] board;

    public Connect4Grid2DArray() {
        board = new short[BOARD_HEIGHT][BOARD_WIDTH];
    }

    @Override
    public void emptyGrid() {
        for(int i = 0; i < BOARD_HEIGHT ; i++) {
            for (int j = 0; j < BOARD_WIDTH - 3; j++) {
                board[i][j] = 0;
            }
        }
    }

    @Override
    public boolean isValidColumn(int column) {
        // if requested column is out of bounds
        if (column > BOARD_WIDTH-1) {
            return false;
        }

        // Ensure validity of nested short[]
        for (short[] arr : board) {
            if (arr == null) {
                return false;
            }
        }

        // Ensure column is not out of bounds
        for (short s : getColumn(column)) {
            if (!(s == 0 || s == 1 || s == 2)) {
                return false;
            }
        }

        // Ensures column is not full
        return !isColumnFull(column);
    }

    @Override
    public boolean isColumnFull(int column) {
        return getColumn(column)[0] != 0;
    }

    /**
     * Gets the content of the specified Column
     * @param column The column you wish to see
     * */
    public Short[] getColumn(int column) {
        List<Short> numbers = new ArrayList<>();
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            numbers.add(board[i][column]);
        }

        return numbers.toArray(new Short[0]);
    }


    @Override
    public void dropPiece(ConnectPlayer player, int column) {
        if (isGridFull()) {
            return;
        }

        if (!isValidColumn(column)) {
            return;
        }

        for (int i = 0; i < BOARD_HEIGHT; i++) {
            if (board[i][column] != 0) {
                board[i-1][column] = player.getPlayerId();
                return;
            }

            if (i == BOARD_HEIGHT-1) {
                board[i][column] = player.getPlayerId();
                return;
            }
        }
    }

    @Override
    public boolean didLastPieceConnect4() {
        // Check against Y axis (length)
        for(int i = 0; i < BOARD_HEIGHT ; i++) {
            for (int j = 0; j < BOARD_WIDTH - 3; j++) {
                // Checks if player 1 won
                if (board[i][j] == 1 && board[i][j+1] == 1 && board[i][j+2] == 1 && board[i][j+3] == 1) {
                    winner = 1;
                    return true;
                }

                // Checks if player 2 won
                if (board[i][j] == 2 && board[i][j+1] == 2 && board[i][j+2] == 2 && board[i][j+3] == 2) {
                    winner = 2;
                    return true;
                }
            }
        }

        // Check against X axis (width)
        for(int i = 0; i < BOARD_HEIGHT - 3; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                // Checks if player 1 won
                if (board[i][j] == 1 && board[i+1][j] == 1 && board[i+2][j] == 1 && board[i+3][j] == 1) {
                    winner = 1;
                    return true;
                }

                // Checks if player 2 won
                if (board[i][j] == 2 && board[i+1][j] == 2 && board[i+2][j] == 2 && board[i+3][j] == 2) {
                    winner = 2;
                    return true;
                }
            }
        }

        // Check against Y/X (diagonal)
        for(int i = 0; i < BOARD_HEIGHT - 3; i++) {
            for(int j = 0; j < BOARD_WIDTH - 3; j++) {
                // Checks if player 1 won
                if (board[i][j] == 1 && board[i+1][j+1] == 1 && board[i+2][j+2] == 1 && board[i+3][j+3] == 1) {
                    winner = 1;
                    return true;
                }

                // Checks if player 2 won
                if (board[i][j] == 2 && board[i+1][j+1] == 2 && board[i+2][j+2] == 2 && board[i+3][j+3] == 2) {
                    winner = 2;
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean isGridFull() {
        int availableSlots = BOARD_HEIGHT * BOARD_WIDTH;

        for(int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                if (board[i][j] != 0) {
                    availableSlots--;
                }
            }
        }

        return availableSlots <= 0;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder()
                .append(" 0 1 2 3 4 5 6\n")
                .append("-----------------\n");

        for (int i = 0; i < BOARD_HEIGHT; i++) {
            builder.append('|');

            for (int j = 0; j < BOARD_WIDTH; j++) {
                builder.append(board[i][j]);
                builder.append('|');
            }

            builder.append('\n');
        }

        builder.append("-----------------\n")
                .append(" 0 1 2 3 4 5 6\n");

        return builder.toString();
    }

    public short getWinner() {
        return winner;
    }
}
