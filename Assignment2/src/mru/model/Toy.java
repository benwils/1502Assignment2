package mru.model;

public abstract class Toy {
	
	private String SN;
	private String name;
	private String brand;
	private double price;
	private int availableCount;
	private int ageAppropriate;

	public Toy(String SN, String name, String brand, double price, int availableCount, int ageAppropriate) {
		this.SN = SN;
		this.name = name;
		this.brand = brand;
		this.price = price;
		this.availableCount = availableCount;
		this.ageAppropriate = ageAppropriate;
	}
	
	public String getSN() {
		return SN;
	}
	
	public void setSN(String SN) {
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
	
	public String toString() {
		return "Serial Number: " + SN + " Name: " + name + " Brand: " + brand + " Price: " + price + " Available Count: " + availableCount + " Age Appropriate: " + ageAppropriate + "Min";
	}
	public String formatForSave() {
		return SN + ";" + name + ";" + brand + ";" + price + ";" + availableCount + ";" + ageAppropriate;
	}

}