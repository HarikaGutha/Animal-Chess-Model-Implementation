package stacs.arcade.reversi;

/**
 * Implementation of the model for the Othello game.
 * 
 * @author !your matric number here, not your email address!
 */
public class ReversiModel {

	public enum PlayerColour {BLACK, WHITE}
	private PlayerColour [][] board;
	private final static int HEIGHT = 8;
	private final static int WIDTH = 8;
	private PlayerColour current_Color;
	//private PlayerColour opponent;
	//private int countBlack;
	//private int countWhite;
	private boolean north_west, north_north, north_east, west_west, east_east, south_west, south_south, south_east;
	
    /**
     * Needs a simple constructor, required for construction by the
     * class that contains the tests. 
     */
	public ReversiModel() {		
		initBoard();
	}
	
	private void initBoard() {
		board = new PlayerColour [8][8] ;
		current_Color = PlayerColour.BLACK;
		for (int x = 0; x < HEIGHT; x++) {
			for (int y = 0; y < WIDTH; y++) {
				board[x][y] = null;
			}
		}
	}
//	public void initialMoves() {
//		board[HEIGHT/2 - 1][WIDTH/2 - 1] = PlayerColour.WHITE;
//		board[HEIGHT/2 - 1][WIDTH/2] = PlayerColour.BLACK;
//		board[HEIGHT/2][WIDTH/2 - 1] = PlayerColour.BLACK;
//		board[HEIGHT/2][WIDTH/2] = PlayerColour.WHITE;
//	}


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
	public PlayerColour nextToMove() {		
		if ( current_Color == PlayerColour.BLACK) {
			return PlayerColour.WHITE;
		} else {
			return PlayerColour.BLACK;
		}
	}
	
	/**
	 * Make a move by placing a piece of the given colour on the given field.
	 * @throws IllegalMoveException if it is not the player's move, if the field
	 * is already occupied or if the coordinates are out of range.
	 */
	public void makeMove(PlayerColour player, int x, int y) throws IllegalMoveException {
		if (board[x][y] == null ) {
			board [x][y] = player;
		}
		else {
			throw new IllegalMoveException("Illegal move");
		}
		/*for (int i = 0; x < HEIGHT; x++) {
			for (int j = 0; y < WIDTH; y++) {
		initialMoves();
				if(board[x][y] == null) {
					north_west = valid_move (player, -1, -1, x, y);
					north_north = valid_move (player, -1, 0, x, y);
					north_east = valid_move (player, -1, 1, x, y);
					west_west = valid_move (player, 0, -1, x, y);
					east_east = valid_move (player, 0, 1, x, y);
					south_west = valid_move (player, 1, -1, x, y);
					south_south = valid_move (player, 1, 0, x, y);
					south_east = valid_move (player, 1, 1, x, y);
					if (north_west || north_north || north_east || west_west || east_east || south_west || south_south || south_east) {
						board[x][y] = player;
					}
				}

			}
		} */
	}

	/*private boolean valid_move(PlayerColour player, int new_x, int new_y, int x, int y) {
		if (player == PlayerColour.BLACK) {
			opponent = PlayerColour.WHITE;
		} else {
			opponent = PlayerColour.BLACK;
		}
		if((x + new_x < 0) || (x + new_x >= HEIGHT)) {
			return false;
		}
		if((y + new_y < 0) || (y + new_y >= WIDTH)) {
			return false;
		}
		if((board[x + new_x][y + new_y] != nextToMove())) {
			return false;
		}
		if((x + new_x + new_x < 0) || (x + new_x + new_x >= HEIGHT)) {
			return false;
		}
		if((y + new_y + new_y < 0) || (y + new_y + new_y >= WIDTH)) {
			return false;
		}
		return check_line_match(player, new_x, new_y, x + new_x + new_x, y + new_y + new_y);
	}

	private boolean check_line_match(PlayerColour player, int new_x, int new_y, int x, int y) {
		if (board [x][y] == player) {
			return true;
		}
		if ((x + new_x < 0) || (x + new_x >= HEIGHT)) {
			return false;
		}
		if ((y + new_y < 0) || (y + new_y >= WIDTH)) {
			return false;
		}
		return check_line_match(player, new_x, new_y, x+new_x, y+new_y);
	} */


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
