import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		java.util.Scanner scanner = new java.util.Scanner(System.in);

		System.out.print("Enter board size: ");
		int size;
		while ((size = scanner.nextInt()) < 1) {
			System.out.print("Try again, stupid. ");
		}
		Board board = new Board(size);
		
		boolean running = true;
		boolean valid;
		boolean skip = false;
		while (running) {
			board.spawnTile();
			if (board.victory() && !skip) {
				System.out.println(board);
				System.out.println("Victory! Keeping playing? Y/N: ");
				if (!scanner.next().equalsIgnoreCase("Y")) {
					running = false;
				}
				skip = true;
			} else if (!board.lost()) {
				valid = false;
				while (!valid) {
					System.out.print(board);

					System.out.print("Enter move (WASD): ");
					String move = scanner.next();

					switch (move.toUpperCase()) {
					case "W":
						if (board.up())
							valid = true;
						break;
					case "A":
						if (board.left())
							valid = true;
						break;
					case "S":
						if (board.down())
							valid = true;
						break;
					case "D":
						if (board.right())
							valid = true;
						break;
					default:
						System.out.println("Please enter a valid move.");
						break;
					}
				}
			} else {
				System.out.println(board);
				System.out.println("You lose!");
				running = false;
			}
		}
		scanner.close();

	}
}
