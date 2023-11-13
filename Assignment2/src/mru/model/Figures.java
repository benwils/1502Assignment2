package mru.model;

public class Figures extends Toy {
	
	private char classification; 
	
	public Figures(int SN, String name, String brand, double price, int availableCount, int ageAppropriate, char classification) {
		super(SN, name, brand, price, availableCount, ageAppropriate);
		this.classification = classification;
	}

	public char getClassification() {
		return classification;
	}

	public void setClassification(char classification) {
		this.classification = classification;
	}
	
	
}
