package mru.model;

public class BoardGames extends Toy {

	private int minPlayers;
	private int maxPlayers;
	private String designers;
	
	public BoardGames(String SN, String name, String brand, double price, int availableCount, int ageAppropriate, int minPlayers, int maxPlayers, String designers) {
		super(SN, name, brand, price, availableCount, ageAppropriate);
		this.minPlayers = minPlayers;
		this.maxPlayers = maxPlayers;
		this.designers = designers;
	}

	public int getMinPlayers() {
		return minPlayers;
	}

	public void setMinPlayers(int minPlayers) {
		this.minPlayers = minPlayers;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	public String getDesigners() {
		return designers;
	}

	public void setDesigners(String designers) {
		this.designers = designers;
	}
	
	public String toString() {
		return super.toString() + " Players: " + minPlayers + "-" + maxPlayers + " Designers: " + designers;
	}
	
	public String formatForSave() {
		return super.formatForSave() + ";" + minPlayers + "-" + maxPlayers + ";" + designers;
	}
}