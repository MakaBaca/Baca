package application.mak.btc;

import com.mak.casino.Baccarat.Outcome;

public class BtcScoreCard {
	
	String pb;
	
	int orCount = 0;
	
	int otCount = 0;
	
	int noOfHands = 0;
	
	double score = 0;
	
	int betAmount = 0;
	
	boolean isBetOnPlayer = false;
	
	boolean win = false;
	
	String player = BLANK;
	
	String banker = BLANK;
	
	private static final String CAPS_O = "O";
	
	private static final String SPACE = "  ";
	
	private static final String BLANK = "";
	
	int oneInARow = 0;
	
	int twoInARow = 0;
	
	int threeOrMoreInARow = 0;
	
	public BtcScoreCard(String pb, int orCount, int otCount, int noOfHands, double score, int betAmount,
			boolean isBetOnPlayer, boolean didWin, Outcome handOutcome) {
		super();
		this.pb = pb;
		this.orCount = orCount;
		this.otCount = otCount;
		this.noOfHands = noOfHands;
		this.score = score;
		this.betAmount = betAmount;
		this.isBetOnPlayer = isBetOnPlayer;
		this.win = didWin;
		//Player bet win
		if(betAmount > 0 && isBetOnPlayer && didWin){
			this.player = BtcScoreCard.CAPS_O+BtcScoreCard.SPACE+betAmount;
		}else if(betAmount > 0 && !isBetOnPlayer && didWin){ //banker bet win
			this.banker = BtcScoreCard.CAPS_O+BtcScoreCard.SPACE+betAmount;
		}else if(betAmount > 0 && isBetOnPlayer && !didWin){//Player bet lost
			this.player = BtcScoreCard.BLANK+betAmount;
			this.banker = BtcScoreCard.CAPS_O;
		}else if(betAmount > 0 && !isBetOnPlayer && !didWin){//Banker bet lost
			this.banker = BtcScoreCard.BLANK+betAmount;
			this.player = BtcScoreCard.CAPS_O;
		}else if(betAmount < 1 && handOutcome == Outcome.player){
			this.player = BtcScoreCard.CAPS_O;
		}else if(betAmount < 1 && handOutcome == Outcome.banker){
			this.banker = BtcScoreCard.CAPS_O;
		}
	}
	
	public BtcScoreCard(){
		
	}

	public String getPb() {
		return pb;
	}

	public void setPb(String pb) {
		this.pb = pb;
	}

	public int getOrCount() {
		return orCount;
	}

	public void setOrCount(int orCount) {
		this.orCount = orCount;
	}

	public int getOtCount() {
		return otCount;
	}

	public void setOtCount(int otCount) {
		this.otCount = otCount;
	}

	public int getNoOfHands() {
		return noOfHands;
	}

	public void setNoOfHands(int noOfHands) {
		this.noOfHands = noOfHands;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public int getBetAmount() {
		return betAmount;
	}

	public void setBetAmount(int betAmount) {
		this.betAmount = betAmount;
	}

	public boolean isBetOnPlayer() {
		return isBetOnPlayer;
	}

	public void setBetOnPlayer(boolean isBetOnPlayer) {
		this.isBetOnPlayer = isBetOnPlayer;
	}

	public boolean isWin() {
		return win;
	}

	public void setWin(boolean win) {
		this.win = win;
	}
	
	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

	public String getBanker() {
		return banker;
	}

	public void setBanker(String banker) {
		this.banker = banker;
	}
	
	public int getOneInARow() {
		return oneInARow;
	}

	public void setOneInARow(int oneInARow) {
		this.oneInARow = oneInARow;
	}

	public int getTwoInARow() {
		return twoInARow;
	}

	public void setTwoInARow(int twoInARow) {
		this.twoInARow = twoInARow;
	}

	public int getThreeOrMoreInARow() {
		return threeOrMoreInARow;
	}

	public void setThreeOrMoreInARow(int threeOrMoreInARow) {
		this.threeOrMoreInARow = threeOrMoreInARow;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(20);
		sb.append("Total Hands Excluding Tie's: "+noOfHands+System.lineSeparator());
		sb.append("P/B : "+pb+System.lineSeparator());
		sb.append("O/R : "+orCount+System.lineSeparator());
		sb.append("O/T : "+otCount+System.lineSeparator());
		sb.append("1's : "+oneInARow+System.lineSeparator());
		sb.append("2's : "+twoInARow+System.lineSeparator());
		sb.append("3+ : "+threeOrMoreInARow+System.lineSeparator());
		return sb.toString();
	}

	
}
