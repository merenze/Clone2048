
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
		for (int row=1;row<4;row++) {
			for (int col=0;col<4;col++) {
				iteration(row, col);
				if (board[row][col]!=null) { //If tile contains value
					if (board[row-1][col]==null) { //If space above is empty
						board[row-1][col]=board[row][col];
						board[row][col]=null;
						
						description(row,col,row-1,col,false);
						
						row=1;
						col=0;
					} else if (board[row-1][col].getValue()==board[row][col].getValue()&&!board[row-1][col].used()&&!board[row][col].used()) { //If tile above is same value and has not been used
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
		for(int row=0;row<4;row++) {
			for(int col=0;col<4;col++) {
				if(board[row][col]!=null) board[row][col].setUsed(false);
			}
		}
		System.out.println("MOVE COMPLETED");
	}
	public void down() {
		
	}
	public void left() {
		
	}
	public void right() {
		
	}
	
	private void iteration(int row, int col) {
		System.out.println("Row " + row + ", Col " + col);
	}
	private void description(int r1, int c1, int r2, int c2, boolean combining) {
		String result = "Moving [" + r1 + "][" + c1 + "] to [" + r2 + "][" + c2 + "]";
		if (combining) result+=" (COMBINING)";
		else result+=" (SPACE AVAILABLE)";
		System.out.println(result);
	}
}
