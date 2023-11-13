package mru.model;

public abstract class Toy {
	
	private int SN;
	private String name;
	private String brand;
	private double price;
	private int availableCount;
	private int ageAppropriate;

	public Toy(int SN, String name, String brand, double price, int availableCount, int ageAppropriate) {
		this.SN = SN;
		this.name = name;
		this.brand = brand;
		this.price = price;
		this.availableCount = availableCount;
		this.ageAppropriate = ageAppropriate;
	}
	
	public int getSN() {
		return SN;
	}
	
	public void setSN(int SN) {
		this.SN = SN;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getAvailableCount() {
		return availableCount;
	}

	public void setAvailableCount(int availableCount) {
		this.availableCount = availableCount;
	}

	public int getAgeAppropriate() {
		return ageAppropriate;
	}

	public void setAgeAppropriate(int ageAppropriate) {
		this.ageAppropriate = ageAppropriate;
	}

}
