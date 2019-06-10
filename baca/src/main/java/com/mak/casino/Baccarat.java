package com.mak.casino;

import java.util.List;

import com.mak.baca.card.BaccaratCards;

public interface Baccarat {
	
	public enum Outcome {
		banker("B"), player("P"), tie("T");

		String result;

		Outcome(String r) {
			result = r;
		}

		public String getResult() {
			return result;
		}
	}
	
	public List<String> getBonusBets();
	
	public List<String> bonusOutcome();
	
	public void placeBets(Outcome o, int units);
	
	public void placeBonusBets(String bonus, int units);
	
	public void requestFreeHand();
	
	public void setLastCardPosition(int lastCard);
		
	public boolean hasNextHand();
	
	public boolean prepareShoe();
	
	public Outcome dealOneHand();
	
	public double payout();
	
	public void bankerReteriveBets();
	
	public List<BaccaratCards> getPlayerCards();
	
	public List<BaccaratCards> getBankerCards();
	
	public int baccaratCardValue(BaccaratCards card);
	
	public int getPlayerTwoCardsValue();
	
	public int getBankerTwoCardsValue();
	
	public int getPlayerFinalValue();
	
	public int getBankerFinalValue();
	
	public void clearBaseBets();
	
	public void clearBonusBets();
	
	public void clearAllBets();
	
	public BaccaratCards getBurnCard(); 
	

}
