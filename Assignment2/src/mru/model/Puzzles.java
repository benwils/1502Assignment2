package mru.model;

public class Puzzles extends Toy{
	
	private char puzzleType;
	
	public Puzzles(int SN, String name, String brand, double price, int availableCount, int ageAppropriate, char puzzleType) {
		super(SN, name, brand, price, availableCount, ageAppropriate);
		this.puzzleType = puzzleType;
	}
	
	public char getPuzzleType() {
		return puzzleType;
	}

	public void setPuzzleType(char puzzleType) {
		this.puzzleType = puzzleType;
	}

}
