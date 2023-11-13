package mru.model;

public class Animals extends Toy {
	
	private String material;
	private char size;
	
	public Animals(int SN, String name, String brand, double price, int availableCount, int ageAppropriate, String material, char size) {
		super(SN, name, brand, price, availableCount, ageAppropriate);
		this.material = material;
		this.size = size;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public char getSize() {
		return size;
	}

	public void setSize(char size) {
		this.size = size;
	}
	
	
}
