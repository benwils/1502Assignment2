package mru.model;

public class Puzzles extends Toy{
	
	private char puzzleType;
	
	public Puzzles(long SN, String name, String brand, double price, int availableCount, int ageAppropriate, char puzzleType) {
		super(SN, name, brand, price, availableCount, ageAppropriate);
		this.puzzleType = puzzleType;
	}
	
	public char getPuzzleType() {
		return puzzleType;
	}

	public void setPuzzleType(char puzzleType) {
		this.puzzleType = puzzleType;
	}

	public String toString() {
		if (puzzleType == 'M') {
			return super.toString() + " Puzzle Type: Mechanical";
		}
		else if (puzzleType == 'C') {
			return super.toString() + " Puzzle Type: Cryptic";
		}
		else if (puzzleType == 'L') {
			return super.toString() + " Puzzle Type: Logic";
		}
		else if (puzzleType == 'T') {
			return super.toString() + " Puzzle Type: Trivia";
		}
		else {
			return super.toString() + " Puzzle Type: Riddle";
		}
	}
}
