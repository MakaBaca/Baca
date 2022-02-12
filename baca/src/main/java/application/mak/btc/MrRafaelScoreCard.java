package application.mak.btc;

import com.mak.casino.Baccarat.Outcome;

public class MrRafaelScoreCard {
	
	String pb;
	
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
	
	String strScore = null;
	
	//Beb - Big Eye Boy
	boolean isBebOn = false;
	boolean chaosOnBeb = false;
	
	//Sr - small road
	boolean isSrOn = false;
	boolean chaosOnSr = false;
	
	//cp - cockroachPig
	boolean isCpOn=false;
	boolean chaosOnCp = false;
	
	private String nextPlayer;
	
	private String nextBanker;
	
	public MrRafaelScoreCard(String pb, int noOfHands, double score, int betAmount, boolean isBetOnPlayer, boolean didWin,
			boolean isBebOn, boolean chaosOnBeb, boolean isSrOn, boolean chaosOnSr,
			boolean isCpOn, boolean chaosOnCp, Outcome handOutcome) {
		super();
		this.pb = pb;
		this.noOfHands = noOfHands;
		this.score = score;
		this.strScore = Double.toString(score);
		strScore = strScore.length() > 5 ? strScore.substring(0, 5) : strScore;
		this.betAmount = betAmount;
		this.isBetOnPlayer = isBetOnPlayer;
		this.win = didWin;
		this.isBebOn = isBebOn;
		this.chaosOnBeb = chaosOnBeb;
		this.isSrOn = isSrOn;
		this.chaosOnSr = chaosOnSr;
		this.isCpOn = isCpOn;
		this.chaosOnCp = chaosOnCp;
		if(betAmount > 0 && isBetOnPlayer && didWin){
			this.player = MrRafaelScoreCard.CAPS_O+MrRafaelScoreCard.SPACE+betAmount;
		}else if(betAmount > 0 && !isBetOnPlayer && didWin){ //banker bet win
			this.banker = MrRafaelScoreCard.CAPS_O+MrRafaelScoreCard.SPACE+betAmount;
		}else if(betAmount > 0 && isBetOnPlayer && !didWin){//Player bet lost
			this.player = MrRafaelScoreCard.BLANK+betAmount;
			this.banker = MrRafaelScoreCard.CAPS_O;
		}else if(betAmount > 0 && !isBetOnPlayer && !didWin){//Banker bet lost
			this.banker = MrRafaelScoreCard.BLANK+betAmount;
			this.player = MrRafaelScoreCard.CAPS_O;
		}else if(betAmount < 1 && handOutcome == Outcome.player){
			this.player = MrRafaelScoreCard.CAPS_O;
		}else if(betAmount < 1 && handOutcome == Outcome.banker){
			this.banker = MrRafaelScoreCard.CAPS_O;
		}
	}
	
	
	
	public MrRafaelScoreCard() {
	}

	public String getPb() {
		return pb;
	}
	public void setPb(String pb) {
		this.pb = pb;
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
	public boolean isBebOn() {
		return isBebOn;
	}
	public void setBebOn(boolean isBebOn) {
		this.isBebOn = isBebOn;
	}
	public boolean isChaosOnBeb() {
		return chaosOnBeb;
	}
	public void setChaosOnBeb(boolean chaosOnBeb) {
		this.chaosOnBeb = chaosOnBeb;
	}
	public boolean isSrOn() {
		return isSrOn;
	}
	public void setSrOn(boolean isSrOn) {
		this.isSrOn = isSrOn;
	}
	public boolean isChaosOnSr() {
		return chaosOnSr;
	}
	public void setChaosOnSr(boolean chaosOnSr) {
		this.chaosOnSr = chaosOnSr;
	}
	public boolean isCpOn() {
		return isCpOn;
	}
	public void setCpOn(boolean isCpOn) {
		this.isCpOn = isCpOn;
	}
	public boolean isChaosOnCp() {
		return chaosOnCp;
	}
	public void setChaosOnCp(boolean chaosOnCp) {
		this.chaosOnCp = chaosOnCp;
	}
	
	public String getStrScore() {
		return strScore;
	}
	public String getNextPlayer() {
		return nextPlayer;
	}
	public void setNextPlayer(String nextPlayer) {
		this.nextPlayer = nextPlayer;
	}
	public String getNextBanker() {
		return nextBanker;
	}
	public void setNextBanker(String nextBanker) {
		this.nextBanker = nextBanker;
	}
	
	@Override
	public String toString() {
		return "MrRafaelScoreCard [pb=" + pb + ", noOfHands=" + noOfHands + ", score=" + score + ", betAmount="
				+ betAmount + ", isBetOnPlayer=" + isBetOnPlayer + ", win=" + win + ", player=" + player + ", banker="
				+ banker + ", strScore=" + strScore + ", isBebOn=" + isBebOn + ", chaosOnBeb=" + chaosOnBeb
				+ ", isSrOn=" + isSrOn + ", chaosOnSr=" + chaosOnSr + ", isCpOn=" + isCpOn + ", chaosOnCp=" + chaosOnCp
				+ "]";
	}
	
	

}
