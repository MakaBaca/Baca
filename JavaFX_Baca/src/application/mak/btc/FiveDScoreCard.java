package application.mak.btc;

import com.mak.casino.Baccarat.Outcome;

public class FiveDScoreCard {
	
	String pb;
	
	int orCount = 0;
	
	int otCount = 0;
	
	int oottCount = 0;
	
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
	
	Outcome fourDIndicates = null;
	
	String strScore = null;
	
	String did4DWin = null;
	
	
	public FiveDScoreCard(String pb, int orCount, int otCount, int oottCount, int noOfHands, double score, int betAmount,
			boolean isBetOnPlayer, boolean didWin, Outcome handOutcome, Outcome fourDIndicates, String did4DWin) {
		super();
		this.pb = pb;
		this.orCount = orCount;
		this.otCount = otCount;
		this.oottCount = oottCount;
		this.noOfHands = noOfHands;
		this.score = score;
		this.strScore = Double.toString(score);
		strScore = strScore.length() > 5 ? strScore.substring(0, 5) : strScore;
		this.betAmount = betAmount;
		this.isBetOnPlayer = isBetOnPlayer;
		this.win = didWin;
		this.fourDIndicates = fourDIndicates;
		this.did4DWin = did4DWin;
		//Player bet win
		if(betAmount > 0 && isBetOnPlayer && didWin){
			this.player = FiveDScoreCard.CAPS_O+FiveDScoreCard.SPACE+betAmount;
		}else if(betAmount > 0 && !isBetOnPlayer && didWin){ //banker bet win
			this.banker = FiveDScoreCard.CAPS_O+FiveDScoreCard.SPACE+betAmount;
		}else if(betAmount > 0 && isBetOnPlayer && !didWin){//Player bet lost
			this.player = FiveDScoreCard.BLANK+betAmount;
			this.banker = FiveDScoreCard.CAPS_O;
		}else if(betAmount > 0 && !isBetOnPlayer && !didWin){//Banker bet lost
			this.banker = FiveDScoreCard.BLANK+betAmount;
			this.player = FiveDScoreCard.CAPS_O;
		}else if(betAmount < 1 && handOutcome == Outcome.player){
			this.player = FiveDScoreCard.CAPS_O;
		}else if(betAmount < 1 && handOutcome == Outcome.banker){
			this.banker = FiveDScoreCard.CAPS_O;
		}
	}
	
	public FiveDScoreCard(){
		
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
	
	public int getOottCount() {
		return oottCount;
	}

	public void setOottCount(int oottCount) {
		this.oottCount = oottCount;
	}
	
	public Outcome getFourDIndicates() {
		return fourDIndicates;
	}

	public void setFourDIndicates(Outcome fourDIndicates) {
		this.fourDIndicates = fourDIndicates;
	}

	public String getStrScore() {
		return strScore;
	}

	public String getDid4DWin() {
		return did4DWin;
	}

	public void setDid4DWin(String did4dWin) {
		did4DWin = did4dWin;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(20);
		sb.append("Total Hands Excluding Tie's: "+noOfHands+System.lineSeparator());
		sb.append("P/B : "+pb+System.lineSeparator());
		sb.append("O/R : "+orCount+System.lineSeparator());
		sb.append("O/T : "+otCount+System.lineSeparator());
		sb.append("OO/TT : "+fourDIndicates+System.lineSeparator());
		sb.append("4D Says : "+oottCount+System.lineSeparator());
		sb.append("1's : "+oneInARow+System.lineSeparator());
		sb.append("2's : "+twoInARow+System.lineSeparator());
		sb.append("3+ : "+threeOrMoreInARow+System.lineSeparator());
		sb.append("4D Win? : "+did4DWin+System.lineSeparator());
		return sb.toString();
	}
}
