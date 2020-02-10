package stacs.arcade.reversi;

/**
 * Implementation of the model for the Othello game.
 *
 * @author 190026870
 */
public class ReversiModel {

    public enum PlayerColour {BLACK, WHITE}

    private PlayerColour[][] board;
    private final static int HEIGHT = 8;
    private final static int WIDTH = 8;
    private PlayerColour currentColour;
    private int moveNumber;

    /**
     * Needs a simple constructor, required for construction by the
     * class that contains the tests.
     */
    public ReversiModel() {
        initBoard();
    }

    private void initBoard() {
        board = new PlayerColour[HEIGHT][WIDTH];
        currentColour = PlayerColour.BLACK;
        for (int x = 0; x < HEIGHT; x++) {
            for (int y = 0; y < WIDTH; y++) {
                board[x][y] = null;
            }
        }
        moveNumber = 0;
    }

    /**
     * Returns the colour of the piece at the given position, null if no piece is on this field.
     */
    public PlayerColour getAt(int x, int y) {
        if (board[x][y] == null) {
            return null;
        } else {
            return board[x][y];
        }
    }

    /**
     * Returns the player who is to move next.
     */
    private PlayerColour nextToMove() {
        if (currentColour == PlayerColour.BLACK) {
            return PlayerColour.WHITE;
        } else {
            return PlayerColour.BLACK;
        }
    }

    /**
     * Make a move by placing a piece of the given colour on the given field.
     *
     * @throws IllegalMoveException if it is not the player's move, if the field
     *                              is already occupied or if the coordinates are out of range.
     */
    public void makeMove(PlayerColour player, int x, int y) throws IllegalMoveException {
        rejectMoveOutOfBounds(x, y);
        if (moveNumber == 0) {
            rejectInitialMoveOutsideCenterFour(player, x, y);
        } else if (board[x][y] == null) {
            rejectMovesThatDoNotCapture(player, x, y);
        } else {
            throw new IllegalMoveException("Illegal move");
        }

    }

    /**
     * rejects the moves that are not captured.
     *
     * @param player the current player
     * @param x      the x coordinate
     * @param y      the y coordinate
     * @throws IllegalMoveException throws exception if the field is occupied or invalid coordinates or illegal move
     */
    private void rejectMovesThatDoNotCapture(PlayerColour player, int x, int y) throws IllegalMoveException {
                if ((getAt(x - 1,y - 1) != null) || (getAt(x - 1,y) != null) || (getAt(x - 1,y + 1) != null) || (getAt(x,y - 1) != null) || (getAt(x,y + 1) != null) || (getAt(x + 1,y - 1) != null) || (getAt(x + 1, y) != null) || (getAt(x + 1,y + 1) != null)) {
            board[x][y] = player;
            executeCapturingMoves(player, x, y);
            enforceTurnTaking(player);
        }  else {
            throw new IllegalMoveException("invalid move");
        } }


    /**
     * rejects initial moves if they are not placced at center.
     *
     * @param player the current player
     * @param x      the x coordinate
     * @param y      the y coordinate
     * @throws IllegalMoveException throws exception if the field is occupied or invalid coordinates or illegal move
     */
    private void rejectInitialMoveOutsideCenterFour(PlayerColour player, int x, int y) throws IllegalMoveException {
        if (((x == HEIGHT / 2 - 1) && (y == WIDTH / 2 - 1)) || ((x == HEIGHT / 2 - 1) && (y == WIDTH / 2) || ((x == HEIGHT / 2) && (y == WIDTH / 2 - 1))) || ((x == HEIGHT / 2) && (y == WIDTH / 2))) {
            board[x][y] = player;
            moveNumber += 1;
            enforceTurnTaking(player);
        } else {
            throw new IllegalMoveException("Illegal move");
        }
    }

    /**
     * rejects the moves that are out of the board(bounds)
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @throws IllegalMoveException throws exception if the field is occupied or invalid coordinates or illegal move
     */
    private void rejectMoveOutOfBounds(int x, int y) throws IllegalMoveException {
        if ((x < 0) || (y >= WIDTH)) {
            throw new IllegalMoveException("Illegal move");
        } else if ((x >= HEIGHT) || (y < 0)) {
            throw new IllegalMoveException("Illegal move");
        }
    }

    /**
     * executes the moves that are captured.
     *
     * @param player the current player
     * @param x      the x coordinate
     * @param y      the y coordinate
     */
    private void executeCapturingMoves(PlayerColour player, int x, int y) {
        if (x <= 1 && y <= 1) {
            checkCapturingMovesAtTopLeftCornerOfTheBoard(player, x, y);
        } else if ((x > 1 && x < HEIGHT - 2) && y <= 1) {
            checkCapturingMovesAtLeftSideOfTheBoard(player, x, y);
        } else if ((x >= HEIGHT - 2 && x <= HEIGHT - 1) && y <= 1) {
            checkCapturingMovesAtBottomLeftCornerOfTheBoard(player, x, y);
        } else if ((y > 1 && y < WIDTH - 2) && x <= 1) {
            checkCapturingMovesAtTopOfTheBoard(player, x, y);
        } else if (x <= 1 && (y >= WIDTH - 2 && y <= WIDTH - 1)) {
            checkCapturingMovesAtTopRightCornerOfTheBoard(player, x, y);
        } else if ((x > 1 && x < HEIGHT - 2) && (y >= WIDTH - 2 && y <= WIDTH - 1)) {
            checkCapturingMovesAtRightSideOfTheBoard(player, x, y);
        } else if ((x >= HEIGHT - 2 && x <= HEIGHT - 1) && (y >= WIDTH - 2 && y <= WIDTH - 1)) {
            checkCapturingMovesAtBottomRightCornerOfTheBoard(player, x, y);
        } else if ((x >= HEIGHT - 2 && x <= HEIGHT - 1) && (y > 1 && y < WIDTH - 2)) {
            checkCapturingMovesAtBottomOfTheBoard(player, x, y);
        } else {
            checkcapturingMovesAtCenterOfTheBoard(player, x, y);
        }
    }

    /**
     * chesks the capturing moves for the coordinates x <= 1 && y <= 1.
     *
     * @param player the current player
     * @param x      the x coordinate
     * @param y      the y coordinate
     */
    private void checkCapturingMovesAtTopLeftCornerOfTheBoard(PlayerColour player, int x, int y) {
        if ((getAt(x, y + 1) != player) && (getAt(x, y + 2) == player)) {
            board[x][y + 1] = player;
        } else if ((getAt(x + 1, y) != player) && (getAt(x + 2, y) == player)) {
            board[x + 1][y] = player;
        } else if ((getAt(x + 1, y + 1) != player) && (getAt(x + 2, y + 2) == player)) {
            board[x + 1][y + 1] = player;
        }
    }

    /**
     * checks the capturing moves for the coordinates (x > 1 && x < HEIGHT - 2) && y <= 1.
     *
     * @param player the current player
     * @param x      the x coordinate
     * @param y      the y coordinate
     */
    private void checkCapturingMovesAtLeftSideOfTheBoard(PlayerColour player, int x, int y) {
        if ((getAt(x, y + 1) != player) && (getAt(x, y + 2) == player)) {
            board[x][y + 1] = player;
        } else if ((getAt(x + 1, y) != player) && (getAt(x + 2, y) == player)) {
            board[x + 1][y] = player;
        } else if ((getAt(x + 1, y + 1) != player) && (getAt(x + 2, y + 2) == player)) {
            board[x + 1][y + 1] = player;
        } else if ((getAt(x - 1, y) != player) && (getAt(x - 2, y) == player)) {
            board[x - 1][y] = player;
        } else if ((getAt(x - 1, y + 1) != player) && (getAt(x - 2, y + 2) == player)) {
            board[x - 1][y + 1] = player;
        }
    }

    /**
     * checks the capturing moves for the coordinates (x >= HEIGHT - 2 && x <= HEIGHT - 1) && y <= 1.
     *
     * @param player the current player
     * @param x      the x coordinate
     * @param y      the y coordinate
     */
    private void checkCapturingMovesAtBottomLeftCornerOfTheBoard(PlayerColour player, int x, int y) {
        if ((getAt(x - 1, y) != player) && (getAt(x - 2, y) == player)) {
            board[x - 1][y] = player;
        } else if ((getAt(x - 1, y + 1) != player) && (getAt(x - 2, y + 2) == player)) {
            board[x - 1][y + 1] = player;
        } else if ((getAt(x, y + 1) != player) && (getAt(x, y + 2) == player)) {
            board[x][y + 1] = player;
        }
    }

    /**
     * checks the capturing moves for the coordinates (y > 1 && y < WIDTH - 2) && x <= 1.
     *
     * @param player the current player
     * @param x      the x coordinate
     * @param y      the y coordinate
     */
    private void checkCapturingMovesAtTopOfTheBoard(PlayerColour player, int x, int y) {
        if ((getAt(x + 1, y) != player) && (getAt(x + 2, y) == player)) {
            board[x + 1][y] = player;
        } else if ((getAt(x + 1, y + 1) != player) && (getAt(x + 2, y + 2) == player)) {
            board[x + 1][y + 1] = player;
        } else if ((getAt(x, y - 1) != player) && getAt(x, y - 2) == player) {
            board[x][y - 1] = player;
        } else if ((getAt(x, y + 1) != player) && getAt(x, y + 2) == player) {
            board[x][y + 1] = player;
        } else if ((getAt(x + 1, y - 1) != player) && getAt(x + 2, y - 2) == player) {
            board[x + 1][y - 1] = player;
        }
    }

    /**
     * checks the capturing moves for the coordinates x <= 1 && (y >= WIDTH - 2 && y <= WIDTH - 1).
     *
     * @param player the current player
     * @param x      the x coordinate
     * @param y      the y coordinate
     */
    private void checkCapturingMovesAtTopRightCornerOfTheBoard(PlayerColour player, int x, int y) {
        if ((getAt(x, y - 1) != player) && getAt(x, y - 2) == player) {
            board[x][y - 1] = player;
        } else if ((getAt(x + 1, y) != player) && (getAt(x + 2, y) == player)) {
            board[x + 1][y] = player;
        } else if ((getAt(x + 1, y - 1) != player) && getAt(x + 2, y - 2) == player) {
            board[x + 1][y - 1] = player;
        }
    }

    /**
     * checks the capturing moves for the coordinates (x > 1 && x < HEIGHT - 2) && (y >= WIDTH - 2 && y <= WIDTH - 1).
     *
     * @param player the current player
     * @param x      the x coordinate
     * @param y      the y coordinate
     */
    private void checkCapturingMovesAtRightSideOfTheBoard(PlayerColour player, int x, int y) {
        if ((getAt(x, y - 1) != player) && getAt(x, y - 2) == player) {
            board[x][y - 1] = player;
        } else if ((getAt(x - 1, y) != player) && (getAt(x - 2, y) == player)) {
            board[x - 1][y] = player;
        } else if ((getAt(x + 1, y) != player) && (getAt(x + 2, y) == player)) {
            board[x + 1][y] = player;
        } else if ((getAt(x - 1, y - 1) != player) && (getAt(x - 2, y - 2) == player)) {
            board[x - 1][y - 1] = player;
        } else if ((getAt(x + 1, y - 1) != player) && (getAt(x + 2, y - 2) == player)) {
            board[x + 1][y - 1] = player;
        }
    }

    /**
     * checks the capturing moves for the coordinates (x >= HEIGHT - 2 && x <= HEIGHT - 1) && (y >= HEIGHT - 2 && y <= HEIGHT - 1).
     *
     * @param player the current player
     * @param x      the x coordinate
     * @param y      the y coordinate
     */
    private void checkCapturingMovesAtBottomRightCornerOfTheBoard(PlayerColour player, int x, int y) {
        if ((getAt(x, y - 1) != player) && getAt(x, y - 2) == player) {
            board[x][y - 1] = player;
        } else if ((getAt(x - 1, y) != player) && (getAt(x - 2, y) == player)) {
            board[x - 1][y] = player;
        } else if ((getAt(x - 1, y - 1) != player) && (getAt(x - 2, y - 2) == player)) {
            board[x - 1][y - 1] = player;
        }
    }

    /**
     * checks the capturing moves for the coordinates (x >= HEIGHT - 2 && x <= HEIGHT - 1) && (y >= WIDTH - 2 && y <= WIDTH - 1).
     *
     * @param player the current player
     * @param x      the x coordinate
     * @param y      the y coordinate
     */
    private void checkCapturingMovesAtBottomOfTheBoard(PlayerColour player, int x, int y) {
        if ((getAt(x, y - 1) != player) && getAt(x, y - 2) == player) {
            board[x][y - 1] = player;
        } else if ((getAt(x - 1, y) != player) && (getAt(x - 2, y) == player)) {
            board[x - 1][y] = player;
        } else if ((getAt(x - 1, y - 1) != player) && (getAt(x - 2, y - 2) == player)) {
            board[x - 1][y - 1] = player;
        } else if ((getAt(x, y + 1) != player) && getAt(x, y + 2) == player) {
            board[x][y + 1] = player;
        } else if ((getAt(x - 1, y + 1) != player) && (getAt(x - 2, y + 2) == player)) {
            board[x - 1][y + 1] = player;
        }
    }

    /**
     * checks the capturing moves that are at the center of the board (other than previously checked conditions.
     *
     * @param player the current player
     * @param x      the x coordinate
     * @param y      the y coordinate
     */
    private void checkcapturingMovesAtCenterOfTheBoard(PlayerColour player, int x, int y) {
        if ((getAt(x, y - 1) != player) && (getAt(x, y - 2) == player)) {
            board[x][y - 1] = player;
        } else if ((getAt(x + 1, y) != player) && (getAt(x + 2, y) == player)) {
            board[x + 1][y] = player;
        } else if ((getAt(x + 1, y - 1) != player) && (getAt(x + 2, y - 2)) == player) {
            board[x + 1][y - 1] = player;
        } else if ((getAt(x, y - 1) != player) && (getAt(x, y - 2) == player)) {
            board[x][y - 1] = player;
        } else if ((getAt(x + 1, y) != player) && (getAt(x + 2, y) == player)) {
            board[x + 1][y] = player;
        } else if ((getAt(x + 1, y - 1) != player) && (getAt(x + 2, y - 2) == player)) {
            board[x + 1][y - 1] = player;
        } else if ((getAt(x, y + 1) != player) && (getAt(x, y + 2) == player)) {
            board[x][y + 1] = player;
        } else if ((getAt(x - 1, y + 1) != player) && (getAt(x - 2, y + 2) == player)) {
            board[x - 1][y + 1] = player;
        } else if ((getAt(x + 1, y + 1) != player) && (getAt(x + 2, y + 2) == player)) {
            board[x - 1][y - 1] = player;
        } else if ((getAt(x - 1, y) != player) && getAt(x - 2, y) == player) {
            board[x - 1][y] = player;
        } else if ((getAt(x - 1, y - 1) != player) && getAt(x - 2, y - 2) == player) {
            board[x - 1][y - 1] = player;
        }
    }

    /**
     * enforces the turn taking.
     *
     * @param player the current player
     * @throws IllegalMoveException throws exception if the field is occupied or invalid coordinates or illegal move
     */
    private void enforceTurnTaking(PlayerColour player) throws IllegalMoveException {
        currentColour = nextToMove();
        if (currentColour == player) {
            throw new IllegalMoveException("Invalid player");
        }
    }

    /**
     * Return the number of black stones currently on the board.
     */
    public int getNoBlackStones() {
        int countBlack = 0;
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (board[i][j] == PlayerColour.BLACK) {
                    countBlack += 1;
                }
            }
        }
        return countBlack;
    }

    /**
     * Return the number of white stones currently on the board.
     */
    public int getNoWhiteStones() {
        int countWhite = 0;
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (board[i][j] == PlayerColour.WHITE) {
                    countWhite += 1;
                }
            }
        }
        return countWhite;
    }

}
