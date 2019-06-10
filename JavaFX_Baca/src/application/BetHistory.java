package application;

public class BetHistory {
	
	private double placedBets = 0;
	
	private double wonBets = 0;
	
	private double balance = 0;
	
	public double getPlacedBets() {
		return placedBets;
	}

	public void setPlacedBets(double placedBets) {
		this.placedBets = placedBets;
	}

	public double getWonBets() {
		return wonBets;
	}

	public void setWonBets(double wonBets) {
		this.wonBets = wonBets;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public BetHistory(double placedBets, double wonBets, double balance) {
		super();
		this.placedBets = placedBets;
		this.wonBets = wonBets;
		this.balance = balance;
	}

	
	
	
}
