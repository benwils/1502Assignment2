package mru.model;

public class Figures extends Toy {
	
	private char classification; 
	
	public Figures(String SN, String name, String brand, double price, int availableCount, int ageAppropriate, char classification) {
		super(SN, name, brand, price, availableCount, ageAppropriate);
		this.classification = classification;
	}

	public char getClassification() {
		return classification;
	}

	public void setClassification(char classification) {
		this.classification = classification;
	}
	
	public String toString() {
		if (classification == 'A') {
			return super.toString() + " Classification: Action";
		}
		else if (classification == 'D') {
			return super.toString() + " Classification: Doll";
		}
		else {
			return super.toString() + " Classification: Historic";
		}
	}
	
	public String formatForSave() {
		return super.formatForSave() + ";" + classification;
	}
}