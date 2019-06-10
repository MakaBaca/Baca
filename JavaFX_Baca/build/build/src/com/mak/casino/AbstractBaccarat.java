package com.mak.casino;

import java.util.ArrayList;
import java.util.List;

import com.mak.baca.card.BaccaratCards;
import com.mak.baca.card.Shoe;
import com.mak.baca.card.BaccaratGame.Outcome;

public abstract class AbstractBaccarat implements Baccarat {
	
	public List<BaseBet> baseBetsPlaced = new ArrayList<BaseBet>();
	
	private int lastCardIndex = 35;
	
	private Shoe bacaShoe;
	
	private List<BaccaratCards> playerCards;
	
	private List<BaccaratCards> bankerCards;
	
	public Outcome handOutcome;
	
	public boolean isNatural = false;
	
	private boolean isFreeHand = false;
	
	private BaccaratCards burnCard = null;

	@Override
	public void setLastCardPosition(int lastCard) {
		lastCardIndex = lastCard;
	}

	@Override
	public boolean hasNextHand() {
		if(bacaShoe != null && bacaShoe.getCardsLeftInShoe() > lastCardIndex){
			return true;
		}
		return false;
	}

	@Override
	public boolean prepareShoe() {
		bacaShoe = new Shoe(8);
		bacaShoe.shuffleShoe(100);
		bacaShoe.stackShoe();
		burnCard = bacaShoe.burnCards();
		return true;
	}
	
	public BaccaratCards getBurnCard(){
		return burnCard;
	}

	@Override
	public Outcome dealOneHand() {
		int playerValue = 0;
		int bankerValue = 0;
		int ptcValue = 0;
		int tcruleValue = 0;
		playerCards = new ArrayList<BaccaratCards>();
		bankerCards = new ArrayList<BaccaratCards>();
		BaccaratCards playerCard = bacaShoe.dealOneCard();
		playerCards.add(playerCard);
		playerValue = baccaratCardValue(playerCard);
		BaccaratCards bankerCard = bacaShoe.dealOneCard();
		bankerCards.add(bankerCard);
		bankerValue = baccaratCardValue(bankerCard);
		playerCard = bacaShoe.dealOneCard();
		playerCards.add(playerCard);
		playerValue = playerValue + baccaratCardValue(playerCard);
		bankerCard = bacaShoe.dealOneCard();
		bankerCards.add(bankerCard);
		bankerValue = bankerValue + baccaratCardValue(bankerCard);
		// reducing 10 if total greater than 10.
		if (playerValue > 9) {
			playerValue = playerValue - 10;
		}
		if (bankerValue > 9) {
			bankerValue = bankerValue - 10;
		}
		isNatural = false;
		if (playerValue > 7 || bankerValue > 7) {// Natural Check
			isNatural = true;
			return determineOutcome(playerValue, bankerValue);
		} else if (playerValue > 5 && bankerValue > 5) {// No draw bbq
			return determineOutcome(playerValue, bankerValue);
		} else if (playerValue > 5 && bankerValue <= 5) {// only banker hit
			bankerCard = bacaShoe.dealOneCard();
			bankerCards.add(bankerCard);
			bankerValue = bankerValue + baccaratCardValue(bankerCard);
			// reducing 10 if total greater than 10.
			if (bankerValue > 9) {
				bankerValue = bankerValue - 10;
			}
			return determineOutcome(playerValue, bankerValue);
		} else if (bankerValue == 7 && playerValue <= 5) {// only player hit
			playerCard = bacaShoe.dealOneCard();
			playerCards.add(playerCard);
			playerValue = playerValue + baccaratCardValue(playerCard);
			// reducing 10 if total greater than 10.
			if (playerValue > 9) {
				playerValue = playerValue - 10;
			}
			return determineOutcome(playerValue, bankerValue);
		} else {
			// Third card rule
			// A math formula equivalent to the drawing rules is: take the value
			// of Player's third card,
			// counting 8 and 9 as -2 and -1. Divide by 2 always rounding
			// towards zero.
			// (Thus -1, 0, and 1 all round to zero when this division is done.)
			// Add three to the result.
			// If the Banker's current total is this final value or less, then
			// draw; otherwise, stand.
			playerCard = bacaShoe.dealOneCard();
			playerCards.add(playerCard);
			ptcValue = baccaratCardValue(playerCard);
			playerValue = playerValue + ptcValue;
			// reducing 10 if total greater than 10.
			if (playerValue > 9) {
				playerValue = playerValue - 10;
			}
			if (ptcValue >= 0 && ptcValue <= 7) {
				tcruleValue = ptcValue;
			} else if (ptcValue == 8) {
				tcruleValue = -2;
			} else {
				tcruleValue = -1;
			}

			tcruleValue = tcruleValue / 2 + 3;
			if (bankerValue <= tcruleValue) {
				bankerCard = bacaShoe.dealOneCard();
				bankerCards.add(bankerCard);
				bankerValue = bankerValue + baccaratCardValue(bankerCard);
				// reducing 10 if total greater than 10.
				if (bankerValue > 9) {
					bankerValue = bankerValue - 10;
				}
				return determineOutcome(playerValue, bankerValue);
			} else {
				return determineOutcome(playerValue, bankerValue);
			}
		}
		
	}
	
	private Outcome determineOutcome(int playerValue, int bankerValue) {
		if (playerValue == bankerValue) {
			handOutcome = Outcome.tie;
		} else if (playerValue > bankerValue) {
			handOutcome = Outcome.player;
		} else {
			handOutcome = Outcome.banker;
		}
		return handOutcome;
	}
	
	@Override
	public int baccaratCardValue(BaccaratCards card) {
		int cardValue = card.getRank().getPriority();
		if (cardValue > 9) {
			return 0;
		} else {
			return cardValue;
		}
	}
	
	@Override
	public List<BaccaratCards> getPlayerCards() {
		return playerCards;
	}
	
	@Override
	public List<BaccaratCards> getBankerCards() {
		return bankerCards;
	}

	@Override
	public void bankerReteriveBets() {
		// TODO Auto-generated method stub

	}

	@Override
	public void placeBets(Outcome o, int units) {
		baseBetsPlaced.add(new BaseBet(o, units));		
	}

	@Override
	public void requestFreeHand() {
		isFreeHand = true;
	}
	
	@Override
	public int getPlayerTwoCardsValue() {
		int value = baccaratCardValue(playerCards.get(0)) + baccaratCardValue(playerCards.get(1));
		if(value > 9){
			value = value -10;
		}
		return value;
	}
	
	@Override
	public int getBankerFinalValue() {
		int value = 0;
		for(BaccaratCards c:bankerCards){
			value = value + baccaratCardValue(c);
			if(value > 9){
				value = value -10;
			}
		}
		return value;
	}
	
	@Override
	public int getPlayerFinalValue() {
		int value = 0;
		for(BaccaratCards c:playerCards){
			value = value + baccaratCardValue(c);
			if(value > 9){
				value = value -10;
			}
		}
		return value;
	}
	
	@Override
	public int getBankerTwoCardsValue() {
		int value = baccaratCardValue(bankerCards.get(0)) + baccaratCardValue(bankerCards.get(1));
		if(value > 9){
			value = value -10;
		}
		return value;
	}
	
	@Override
	public void clearBaseBets() {
		baseBetsPlaced.clear();
	}
	
	class BaseBet{
		
		Outcome outcome;
		
		int betUnits;
		
		BaseBet(Outcome o, int units){
			outcome = o;
			betUnits = units; 
		}

		public Outcome getOutcome() {
			return outcome;
		}

		public int getBetUnits() {
			return betUnits;
		}
	}

}
