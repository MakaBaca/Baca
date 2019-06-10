package com.mak.casino;

import java.util.ArrayList;
import java.util.List;

import com.mak.baca.card.BaccaratCards;
import com.mak.casino.Baccarat.Outcome;

public class VegasBaccarat extends AbstractBaccarat {
	
	public static enum Bonus{
		PlayerPair("PP"), BankerPair("BP"),EitherPair("EP"), PlayerDragon("PD"),BankerDragon("BD");

		String bonus;

		Bonus(String r) {
			bonus = r;
		}

		public String getResult() {
			return bonus;
		}
	}
	
	private List<BonusBet> bonusBetsPlaced = new ArrayList<BonusBet>();
	private boolean isPlayerPair = false;
	private boolean isBankerPair = false;
	private int dragonBonus = 0;

	@Override
	public List<String> getBonusBets() {
		List<String> bonusList= new ArrayList<String>();
		for(Bonus b:Bonus.values()){
			bonusList.add(b.getResult());
		}
		return bonusList;
	}
	
	public Outcome dealOneHand() {
		super.dealOneHand();
		//bonus wins
		isPlayerPair = hasPair(getPlayerCards());
		isBankerPair = hasPair(getBankerCards());
		if(handOutcome == Outcome.player){
			dragonBonus = getDragonBouns(getPlayerCards(), getBankerCards());
		}else if(handOutcome == Outcome.banker){
			dragonBonus = getDragonBouns(getBankerCards(), getPlayerCards());
		}
		return handOutcome;
		
	}

	@Override
	public void placeBonusBets(String bonus, int units) {
		if(bonus.equalsIgnoreCase(Bonus.PlayerPair.getResult())){
			bonusBetsPlaced.add(new BonusBet(Bonus.PlayerPair, units));
		} else if(bonus.equalsIgnoreCase(Bonus.BankerPair.getResult())){
			bonusBetsPlaced.add(new BonusBet(Bonus.BankerPair, units));
		} else if(bonus.equalsIgnoreCase(Bonus.EitherPair.getResult())){
			bonusBetsPlaced.add(new BonusBet(Bonus.EitherPair, units));
		} else if(bonus.equalsIgnoreCase(Bonus.PlayerDragon.getResult())){
			bonusBetsPlaced.add(new BonusBet(Bonus.PlayerDragon, units));
		} else if(bonus.equalsIgnoreCase(Bonus.BankerDragon.getResult())){
			bonusBetsPlaced.add(new BonusBet(Bonus.BankerDragon, units));
		}
	}

	@Override
	public double payout() {
		double winingUnits = 0;
		//base bets payout
		for(BaseBet bb:baseBetsPlaced){
			if(bb.getOutcome() == handOutcome){
				if(handOutcome == Outcome.player){
					winingUnits = winingUnits + bb.getBetUnits() + bb.getBetUnits();
				} else if(handOutcome == Outcome.banker){
					winingUnits = winingUnits + (bb.getBetUnits() * 0.95) + bb.getBetUnits();
				} else if(handOutcome == Outcome.tie){
					winingUnits = winingUnits + (bb.getBetUnits() * 8) + bb.getBetUnits();
				}
			}
			//in case of tie repay the player and banker base bets
			else if(handOutcome == Outcome.tie && (bb.getOutcome() == Outcome.player || bb.getOutcome() == Outcome.banker)){
				winingUnits = winingUnits + bb.getBetUnits();
			}
		}
		
		//Pay bonus bets
		for(BonusBet bonusBet:bonusBetsPlaced){
			if(bonusBet.getBonus() == Bonus.EitherPair && (isPlayerPair || isBankerPair)){
				winingUnits = winingUnits + (bonusBet.getBetUnits()*5) + bonusBet.getBetUnits();
			} else if(bonusBet.getBonus() == Bonus.PlayerPair && isPlayerPair){
				winingUnits = winingUnits + (bonusBet.getBetUnits()*11) + bonusBet.getBetUnits();
			} else if(bonusBet.getBonus() == Bonus.BankerPair && isBankerPair){
				winingUnits = winingUnits + (bonusBet.getBetUnits()*11) + bonusBet.getBetUnits();
			} else if(bonusBet.getBonus() == Bonus.PlayerDragon && handOutcome == Outcome.player && dragonBonus >0){
				winingUnits = winingUnits + (bonusBet.getBetUnits() * dragonBonus) + bonusBet.getBetUnits();
			} else if(bonusBet.getBonus() == Bonus.BankerDragon && handOutcome == Outcome.banker && dragonBonus >0){
				winingUnits = winingUnits + (bonusBet.getBetUnits() * dragonBonus) + bonusBet.getBetUnits();
			} 
		}
		return winingUnits;
	}
	
	/**
	 * Calculates the dragon bonus
	 * @param winingCards
	 * @param losingCards
	 * @return payout as integer
	 */
	private int getDragonBouns(List<BaccaratCards> winingCards, List<BaccaratCards> losingCards){
		if(isNatural){
			return 1;
		} 
		int winingHand = 0;
		int losingHand = 0;
		int winingDifference = 0;
		for(BaccaratCards card: winingCards){
			winingHand = winingHand + baccaratCardValue(card);
			if(winingHand > 9){
				winingHand = winingHand - 10;
			}
		}
		
		for(BaccaratCards card: losingCards){
			losingHand = losingHand + baccaratCardValue(card);
			if(losingHand > 9){
				losingHand = losingHand - 10;
			}
		}
		winingDifference = winingHand - losingHand;
		
		if(winingDifference < 4){
			return 0;
		}else if(winingDifference == 4){
			return 1;
		}else if(winingDifference == 5){
			return 2;
		}else if(winingDifference == 6){
			return 4;
		}else if(winingDifference == 7){
			return 6;
		}else if(winingDifference == 8){
			return 10;
		}else {
			return 30;
		}
		
	}
	
	public static boolean hasPair(List<BaccaratCards> cards){
		BaccaratCards firstCard;
		BaccaratCards secondCard;
		if(cards.size() > 1){
			firstCard = cards.get(0);
			secondCard = cards.get(1);
			if(firstCard.getRank().getPriority() == secondCard.getRank().getPriority()){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public List<String> bonusOutcome() {
		List<String> bonusList = new ArrayList<String>();
		if(isPlayerPair){
			bonusList.add(Bonus.PlayerPair.getResult());
		}
		if(isBankerPair){
			bonusList.add(Bonus.BankerDragon.getResult());
		}
		if(isPlayerPair || isBankerPair){
			bonusList.add(Bonus.EitherPair.getResult());
		}
		if(handOutcome == Outcome.player && dragonBonus >0){
			bonusList.add(Bonus.PlayerDragon.getResult());
		}
		if(handOutcome == Outcome.banker && dragonBonus >0){
			bonusList.add(Bonus.BankerDragon.getResult());
		}
		return bonusList;
	}
	
	@Override
	public void clearBonusBets() {
		bonusBetsPlaced.clear();
		
	}
	
	@Override
	public void clearAllBets() {
		bonusBetsPlaced.clear();
		baseBetsPlaced.clear();
	}
	
	class BonusBet{
		
		Bonus bonus;
		
		int betUnits;
		
		public Bonus getBonus() {
			return bonus;
		}

		public int getBetUnits() {
			return betUnits;
		}

		BonusBet(Bonus b, int units){
			bonus = b;
			betUnits = units;
		}
	}

}
