
public class Main {
	public static void main(String[] args) {
		Board board = new Board();
		java.util.Scanner scanner = new java.util.Scanner(System.in);
		boolean running=true;
		boolean valid;
		
		while(running) {
			board.spawnTile();
			if (board.victory()) {
				System.out.println("Victory! Keeping playing? Y/N: ");
				if (!scanner.next().equalsIgnoreCase("Y")) {
					running=false;
				}
			} else if (!board.lost()) {
				valid=false;
				while(!valid) {
					System.out.print(board);
					
					System.out.print("Enter move (WASD): ");
					String move = scanner.next();
					
					switch(move.toUpperCase()) {
						case "W":
							if(board.up()) valid=true;
							break;
						case "A":
							if(board.left()) valid=true;
							break;
						case "S":
							if(board.down()) valid=true;
							break;
						case "D":
							if(board.right()) valid=true;
							break;
						default:
							System.out.println("Please enter a valid move.");
							break;
					}
				}
			} else {
				System.out.println(board);
				System.out.println("You lose!");
				running=false;
			}
		}
		scanner.close();

	}
}
