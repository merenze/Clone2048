
public class Tile {
	/**
	 * Number stored in this tile
	 */
	private int value;
	/**
	 * Whether the tile has been used this move
	 */
	private boolean used;

	public Tile() {
		java.util.Random random = new java.util.Random();
		if (random.nextInt(2) == 1)
			value = 2;
		else
			value = 4;
		used = false;
	}

	public Tile(int value) {
		this.value = value;
	}

	/**
	 * @return Number stored in this tile
	 */
	public int getValue() {
		return value;
	}

	@Override
	public String toString() {
		return Integer.toString(value);
	}
	
	/**
	 * @return True if tile has been used in this round
	 */
	public boolean used() {
		return used;
	}
	
	/**
	 * @param tile Tile to test
	 * @return True if tiles are of equal value
	 */
	public boolean equals(Tile tile) {
		if (tile.getValue() == value)
			return true;
		else
			return false;
	}

	/**
	 * Double the value of the tile
	 */
	public void doubleValue() {
		value *= 2;
	}
	
	/**
	 * Mark the tile as used or not used.
	 * @param used 
	 */
	public void setUsed(boolean used) {
		this.used = used;
	}
}
