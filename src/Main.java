
public class Main {
	public static void main(String[] args) {
		Board board = new Board();
		java.util.Scanner scanner = new java.util.Scanner(System.in);
		boolean running=true;
		boolean valid;
		
		while(running) {
			
			valid=false;
			while(!valid) {
				board.spawnTile();
				System.out.print(board);
				
				System.out.print("Enter move (WASD): ");
				String move = scanner.next();
				
				switch(move.toUpperCase()) {
					case "W":
						board.up();
						valid=true;
						break;
					case "A":
						board.left();
						valid=true;
						break;
					case "S":
						board.down();
						valid=true;
						break;
					case "D":
						board.right();
						valid=true;
						break;
					default:
						System.out.println("Please enter a valid move.");
				}
			}
		}
		System.out.println("Loop exited");
		scanner.close();

	}
}
