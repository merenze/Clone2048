
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
	//Mutators
	public void spawnTile() {
		java.util.Random random = new java.util.Random();
		int row = random.nextInt(4);
		int col = random.nextInt(4);
		boolean valid = board[row][col]==null;
		while (!valid) {
			if (row<=3) {
				row++;
			} else {
				row=0;
				col++;
			}
		}
		board[row][col]=new Tile();
	}
	//Moves
	public void up() {
		//Move the tiles
		for (int row=1;row<=3;row++) {
			for (int col=0;col<=3;col++) {
				iteration(row, col);
				
				if (board[row][col]!=null) { //If tile contains value
					if (board[row-1][col]==null) { //If space above is empty
						board[row-1][col]=board[row][col];
						board[row][col]=null;
						
						description(row,col,row-1,col,false);
						
						row=1;
						col=0;
					} else if (board[row-1][col].equals(board[row][col]) && //If tile above is same value
							   !board[row-1][col].used() && !board[row][col].used()) { //And neither tile has been used
						board[row-1][col].doubleValue(); //Combine the tiles
						board[row-1][col].setUsed(true); //Mark the above tile as used
						board[row][col]=null;
						
						description(row,col,row-1,col,true);
						
						row=1;
						col=0;
					}
				}
			}
		}
		//Mark tiles as unused
		markUnused();
		System.out.println("MOVE COMPLETED");
	}
	public void down() {
		for (int row=2;row>=0;row--) {
			for (int col=0;col<=3;col++) {
				if(board[row][col]!=null) {
					if (board[row+1][col]==null) {
						board[row+1][col]=board[row][col];
						board[row][col]=null;
						
						description(row,col,row+1,col,false);
						
						row=2;
						col=0;
					}
					else if (board[row+1][col].equals(board[row][col]) &&
							 !board[row+1][col].used() && !board[row][col].used()) {
						board[row+1][col].doubleValue();
						board[row+1][col].setUsed(true);
						board[row][col]=null;
						
						description(row,col,row+1,col,true);
						
						row=2;
						col=0;
					}
				}
			}
		}
		markUnused();
	}
	public void left() {
		for (int col=1;col<=3;col++) {
			for (int row=0;row<=3;row++) {
				if(board[row][col]!=null) {
					if (board[row][col-1]==null) {
						board[row][col-1]=board[row][col];
						board[row][col]=null;
						
						description(row,col,row,col-1,false);
						
						row=0;
						col=1;
					}
					else if(board[row][col-1].equals(board[row][col]) &&
							!board[row][col-1].used() && !board[row][col].used()) {
					board[row][col-1].doubleValue();
					board[row][col-1].setUsed(true);
					board[row][col]=null;
					
					description(row,col,row,col-1,true);
					
					row=0;
					col=1;
					}
				}
			}
		}
		markUnused();
	}
	public void right() {
		
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
		System.out.println("Row " + row + ", Col " + col);
	}
	private void description(int r1, int c1, int r2, int c2, boolean combining) {
		String result = "Moving [" + r1 + "][" + c1 + "] to [" + r2 + "][" + c2 + "]";
		if (combining) result+=" (COMBINING TILES)";
		else result+=" (SPACE AVAILABLE)";
		System.out.println(result);
	}
}
