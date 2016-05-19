
public class Board {
	private Tile[][] board;
	//Constructors
	public Board() {
		board = new Tile[4][4];
	}
	//Accessors
	@Override
	public String toString() {
		String result="\n|----|----|----|----|\n";
		for (int row=0;row<4;row++) {
			result+="|";
			for (int col=0;col<4;col++) {
				if (board[row][col]==null) {
					result+="    |";
				} else {
					int value = board[row][col].getValue();
					int length = board[row][col].toString().length();
					if (length==1) {
						result +=  value + "   |";
					} else if (length==2) {
						result +=  value + "  |";
					}
					else if(length==3) {
						result +=  value + " |";
					} else if(length==4) {
						result +=  value + "|";
					}
				}
			}
			result+="\n|----|----|----|----|\n";
		}
		return result;
	}
	public boolean isFull() {
		for (int row=0;row<=3;row++) {
			for (int col=0;col<=3;col++) {
				if (board[row][col]==null) return false;
			}
		}
		return true;
	}
	public boolean victory() {
		for (int row=0;row<=3;row++) {
			for (int col=0;col<=3;col++) {
				if (board[row][col].getValue()==2048) {
					return true;
				}
			}
		}
		return false;
	}
	//Mutators
	public void spawnTile() {
		java.util.Random random = new java.util.Random();
		int row = random.nextInt(4);
		int col = random.nextInt(4);
		boolean valid = board[row][col]==null;
		if (!isFull()) {
			while (!valid) {
				if (row<3) {
					row++;
				} else {
					row=0;
					if (col<3) {
						col++;
					} else {
						col=0;
					}
				}
				System.out.printf("Attempting to spawn tile at [%d][%d]\n",row,col);
				try {
					valid=board[row][col]==null;
				} catch (ArrayIndexOutOfBoundsException ex) {
					System.out.printf("Array index out of bounds: [%d][%d]\n", row,col);
				}
			}
			board[row][col]=new Tile();
			System.out.printf("Spawning new tile at [%d][%d]\n",row,col);
		} else {
			System.out.println("Failed to spawn new tile: Board full");
		}
	}
	//Moves
	public boolean up() {
		boolean result=false; //Tells whether tiles have been moved
		//Move the tiles
		for (int row=1;row<=3;row++) {
			for (int col=0;col<=3;col++) {
				iteration(row, col);
				
				if (board[row][col]!=null) { //If tile contains value
					if (board[row-1][col]==null) { //If space above is empty
						board[row-1][col]=board[row][col];
						board[row][col]=null;
						
						description(row,col,row-1,col,false);
						
						result=true;
						row=1;
						col=-1;
					} else if (board[row-1][col].equals(board[row][col]) && //If tile above is same value
							   !board[row-1][col].used() && !board[row][col].used()) { //And neither tile has been used
						board[row-1][col].doubleValue(); //Combine the tiles
						board[row-1][col].setUsed(true); //Mark the above tile as used
						board[row][col]=null;
						
						description(row,col,row-1,col,true);
						
						result=true;
						row=1;
						col=-1;
					}
				}
			}
		}
		markUnused();
		return result;
	}
	public boolean down() {
		boolean result=false;
		for (int row=2;row>=0;row--) {
			for (int col=0;col<=3;col++) {
				iteration(row,col);
				
				if(board[row][col]!=null) {
					if (board[row+1][col]==null) {
						board[row+1][col]=board[row][col];
						board[row][col]=null;
						
						description(row,col,row+1,col,false);
						
						result=true;
						row=2;
						col=0;
					}
					else if (board[row+1][col].equals(board[row][col]) &&
							 !board[row+1][col].used() && !board[row][col].used()) {
						board[row+1][col].doubleValue();
						board[row+1][col].setUsed(true);
						board[row][col]=null;
						
						description(row,col,row+1,col,true);
						
						result=true;
						row=2;
						col=0;
					}
				}
			}
		}
		markUnused();
		return result;
	}
	public boolean left() {
		boolean result=false;
		for (int col=1;col<=3;col++) {
			for (int row=0;row<=3;row++) {
				iteration(row,col);
				if(board[row][col]!=null) {
					if (board[row][col-1]==null) {
						board[row][col-1]=board[row][col];
						board[row][col]=null;
						
						description(row,col,row,col-1,false);
						
						result=true;
						row=-1;
						col=1;
					} else if(board[row][col-1].equals(board[row][col]) &&
							!board[row][col-1].used() && !board[row][col].used()) {
					board[row][col-1].doubleValue();
					board[row][col-1].setUsed(true);
					board[row][col]=null;
					
					description(row,col,row,col-1,true);
					
					result=true;
					row=-1;
					col=1;
					}
				}
			}
		}
		markUnused();
		return result;
	}
	public boolean right() {
		boolean result=false;
		for (int col=2;col>=0;col--) {
			for (int row=0;row<=3;row++) {
				iteration(row,col);
				if(board[row][col]!=null) {
					if(board[row][col+1]==null) {
						board[row][col+1]=board[row][col];
						board[row][col]=null;
						
						description(row,col,row,col+1,false);
						
						result=true;
						row=-1;
						col=2;
					} else if (board[row][col+1].equals(board[row][col]) &&
							!board[row][col+1].used() && !board[row][col].used()) {
						board[row][col+1].doubleValue();
						board[row][col+1].setUsed(true);
						board[row][col]=null;
						
						description(row,col,row,col+1,true);
						
						result=true;
						row=-1;
						col=2;
					}
				}
			}
		}
		markUnused();
		return result;
	}
	//Helper methods
	private void markUnused() {
		for(int row=0;row<4;row++) {
			for(int col=0;col<4;col++) {
				if(board[row][col]!=null) board[row][col].setUsed(false);
			}
		}
	}
	//Debug messages
	private void iteration(int row, int col) {
		String result="Row " + row + ", Col " + col;
		if(board[row][col]==null) {
			result+=" (NULL)";
		} else {
			result+=" (" + board[row][col] + ")";
		}
		System.out.println(result);
	}
	private void description(int r1, int c1, int r2, int c2, boolean combining) {
		String result = "Moving [" + r1 + "][" + c1 + "] to [" + r2 + "][" + c2 + "]";
		if (combining) result+=" (COMBINING TILES)";
		else result+=" (SPACE AVAILABLE)";
		result+=" (" + board[r2][c2] + ")";
		System.out.println(result);
	}
}
