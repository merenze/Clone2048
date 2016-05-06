
public class Tile {
	private int value;
	private boolean used;
	//Constructors
	public Tile() {
		java.util.Random random = new java.util.Random();
		if (random.nextInt(2)==1) value=2;
		else value=4;
		used=false;
	}
	public Tile(int value) {
		this.value=value;
	}
	//Accessors
	public int getValue() {
		return value;
	}
	@Override
	public String toString() {
		return Integer.toString(value);
	}
	public boolean used() {
		return used;
	}
	public boolean equals(Tile tile) {
		if (tile.getValue()==value) return true;
		else return false;
	}
	//Mutators
	public void doubleValue() {
		value*=2;
	}
	public void setUsed(boolean used) {
		this.used=used;
	}
}
