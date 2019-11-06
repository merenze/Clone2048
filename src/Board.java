import java.util.ArrayList;
import java.util.Arrays;

//Add test for loser - traverse board, check if current tile == next
public class Board {
	/**
	 * Underlying Tile matrix
	 */
	private Tile[][] board;
	/**
	 * Side length of the board
	 */
	private int size;
	/**
	 * Score for this game
	 */
	private int score;
	
	public Board() {
		this(4);
	}
	/**
	 * @param size Side length of the board
	 */
	public Board(int size) {
		this.size = size;
		board = new Tile[size][size];
	}

	@Override
	public String toString() {
		String result = "\nScore: " + score + "\n+";
		for (int i = 0; i < size; i++)
			result += "----+";
		result += "\n";
		
		for (int row = 0; row < size; row++) {
			result += "|";
			for (int col = 0; col < size; col++) {
				if (board[row][col] == null) {
					result += "    |";
				} else {
					int value = board[row][col].getValue();
					int length = board[row][col].toString().length();
					if (length == 1) {
						result += value + "   |";
					} else if (length == 2) {
						result += value + "  |";
					} else if (length == 3) {
						result += value + " |";
					} else if (length == 4) {
						result += value + "|";
					}
				}
			}
			result += "\n+";
			for (int i = 0; i < size; i++)
				result += "----+";
			result += "\n";
		}
		return result;
	}

	/**
	 * @return True if the board is full, else false.
	 */
	public boolean isFull() {
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				if (board[row][col] == null)
					return false;
			}
		}
		return true;
	}

	/**
	 * @return True if a 2048 tile exists, else false.
	 */
	public boolean victory() {
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				if (board[row][col] != null) {
					if (board[row][col].getValue() == 2048)
						return true;
				}
			}
		}
		return false;
	}

	/**
	 * @return True if there are no possible moves, else false.
	 */
	public boolean lost() {
		if (!isFull())
			return false;
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size - 1; col++) {
				if (board[row][col].equals(board[row][col + 1]))
					return false;
			}
		}
		for (int col = 0; col < size; col++) {
			for (int row = 0; row < size - 1; row++) {
				if (board[row][col].equals(board[row + 1][col]))
					return false;
			}
		}
		return true;
	}

	/**
	 * Spawn a tile in a random spot on the board.
	 */
	public void spawnTile() {
		ArrayList<int[]> emptyTiles = new ArrayList<>();
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				if (board[i][j] == null) {
					int[] coord = {i, j};
					emptyTiles.add(Arrays.copyOf(coord, 2));
				}
		int[] coord = emptyTiles.get(new java.util.Random().nextInt(emptyTiles.size()));
		board[coord[0]][coord[1]] = new Tile();
	}
	
	/**
	 * Do a move up
	 * @return True if successful
	 */
	public boolean up() {
		boolean result = false; // Tells whether tiles have been moved
		// Move the tiles
		for (int row = 1; row < size; row++) {
			for (int col = 0; col < size; col++) {
				// If tile contains value
				if (board[row][col] != null) {
					// If space above is empty
					if (board[row - 1][col] == null) {
						move(row, col, row - 1, col);
						result = true;

						row = 1;
						col = -1;
					}
					// If tile above is same value and neither tile has been
					// used
					else if (board[row - 1][col].equals(board[row][col]) && !board[row - 1][col].used()
							&& !board[row][col].used()) {
						combine(row, col, row - 1, col);
						result = true;

						row = 1;
						col = -1;
					}
				}
			}
		}
		markUnused();
		return result;
	}

	/**
	 * Do a move down
	 * @return True if successful
	 */
	public boolean down() {
		boolean result = false;
		for (int row = size - 2; row >= 0; row--) {
			for (int col = 0; col < size; col++) {
				// iteration(row,col);

				if (board[row][col] != null) {
					if (board[row + 1][col] == null) {
						move(row, col, row + 1, col);
						result = true;

						row = size - 2;
						col = 0;
					} else if (board[row + 1][col].equals(board[row][col]) && !board[row + 1][col].used()
							&& !board[row][col].used()) {
						combine(row, col, row + 1, col);
						result = true;

						row = size - 2;
						col = 0;
					}
				}
			}
		}
		markUnused();
		return result;
	}

	/**
	 * Do a move left
	 * @return True if successful
	 */
	public boolean left() {
		boolean result = false;
		for (int col = 1; col < size; col++) {
			for (int row = 0; row < size; row++) {
				// iteration(row,col);
				if (board[row][col] != null) {
					if (board[row][col - 1] == null) {
						move(row, col, row, col - 1);
						result = true;

						row = -1;
						col = 1;
					} else if (board[row][col - 1].equals(board[row][col]) && !board[row][col - 1].used()
							&& !board[row][col].used()) {
						combine(row, col, row, col - 1);
						result = true;

						row = -1;
						col = 1;
					}
				}
			}
		}
		markUnused();
		return result;
	}

	/**
	 * Do a move right
	 * @return True if successful
	 */
	public boolean right() {
		boolean result = false;
		for (int col = size - 2; col >= 0; col--) {
			for (int row = 0; row < size; row++) {
				// iteration(row,col);
				if (board[row][col] != null) {
					if (board[row][col + 1] == null) {
						move(row, col, row, col + 1);
						result = true;

						row = -1;
						col = size - 2;
					} else if (board[row][col + 1].equals(board[row][col]) && !board[row][col + 1].used()
							&& !board[row][col].used()) {
						combine(row, col, row, col + 1);
						result = true;

						row = -1;
						col = size - 2;
					}
				}
			}
		}
		markUnused();
		return result;
	}

	// Helper methods
	private void markUnused() {
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				if (board[row][col] != null)
					board[row][col].setUsed(false);
			}
		}
	}

	private void move(int r1, int c1, int r2, int c2) {
		board[r2][c2] = board[r1][c1];
		board[r1][c1] = null;
		// description(r1,c1,r2,c2,false);
	}

	private void combine(int r1, int c1, int r2, int c2) {
		board[r2][c2].doubleValue();
		board[r2][c2].setUsed(true);
		board[r1][c1] = null;
		score += board[r2][c2].getValue();
		// description(r1,c1,r2,c2,true);
	}

	// Debug messages
	private void iteration(int row, int col) {
		String result = "Row " + row + ", Col " + col;
		if (board[row][col] == null) {
			result += " (NULL)";
		} else {
			result += " (" + board[row][col] + ")";
		}
		System.out.println(result);
	}

	private void description(int r1, int c1, int r2, int c2, boolean combining) {
		String result = "Moving [" + r1 + "][" + c1 + "] to [" + r2 + "][" + c2 + "]";
		if (combining)
			result += " (COMBINING TILES)";
		else
			result += " (SPACE AVAILABLE)";
		result += " (" + board[r2][c2] + ")";
		System.out.println(result);
	}
}
