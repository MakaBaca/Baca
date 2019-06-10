package com.mak.casino;

import java.util.ArrayList;
import java.util.List;

import com.mak.baca.card.BaccaratCards;

public class EzBaccarat extends AbstractBaccarat {
	
	private boolean isDragon = false;
	
	private boolean isPanda = false;
	
	private List<BonusBet> bonusBetsPlaced = new ArrayList<BonusBet>();

	public static enum Bonus {
		Panda("PD"), Dragon("DR");

		String bonus;

		Bonus(String r) {
			bonus = r;
		}

		public String getResult() {
			return bonus;
		}
	}

	@Override
	public List<String> getBonusBets() {
		List<String> bonusList= new ArrayList<String>();
		for(Bonus b:Bonus.values()){
			bonusList.add(b.getResult());
		}
		return bonusList;
	}
	
	@Override
	public Outcome dealOneHand() {
		super.dealOneHand();
		//resetting panda and dragon flag
		isPanda = false;
		isDragon = false;
		//No bonus for Natural
		if(isNatural){
			return handOutcome;
		}
		if(handOutcome == Outcome.player){
			isPanda = checkIfPanda();
		}else if(handOutcome == Outcome.banker){
			isDragon = checkIfDragon();
		}
		return handOutcome;
	}

	private boolean checkIfDragon() {
		List<BaccaratCards> bankerCards = getBankerCards();
		if(handOutcome == Outcome.banker && 
				bankerCards.size() > 2 && 
				getBankerFinalValue() == 7){
			return true;
		}
		return false;
	}

	private boolean checkIfPanda() {
		List<BaccaratCards> playerCards = getPlayerCards();
		if(handOutcome == Outcome.player && 
				playerCards.size() > 2 && 
				getPlayerFinalValue() == 8){
			return true;
		}
		return false;
	}

	@Override
	public List<String> bonusOutcome() {
		List<String> bonusOutcome = new ArrayList<>();
		if(isPanda){
			bonusOutcome.add(Bonus.Panda.getResult());
		}else if(isDragon){
			bonusOutcome.add(Bonus.Dragon.getResult());
		}
		return bonusOutcome;
	}

	@Override
	public void placeBonusBets(String bonus, int units) {
		if(bonus.equalsIgnoreCase(Bonus.Panda.getResult())){
			if(bonusBetsPlaced.size() == 0){
				bonusBetsPlaced.add(new BonusBet(Bonus.Panda,units));
				return;
			}
			for (BonusBet b : bonusBetsPlaced) {
				if(b.getBonus() == Bonus.Panda){
					b.setBetUnits(b.getBetUnits()+units);
					break;
				}else{
					bonusBetsPlaced.add(new BonusBet(Bonus.Panda,units));
					break;
				}
			}
			//bonusBetsPlaced.add(new BonusBet(Bonus.Panda,units));
		}else if(bonus.equalsIgnoreCase(Bonus.Dragon.getResult())){
			if(bonusBetsPlaced.size() == 0){
				bonusBetsPlaced.add(new BonusBet(Bonus.Dragon,units));
				return;
			}
			for (BonusBet b : bonusBetsPlaced) {
				if(b.getBonus() == Bonus.Dragon){
					b.setBetUnits(b.getBetUnits()+units);
					break;
				}else{
					bonusBetsPlaced.add(new BonusBet(Bonus.Dragon,units));
					break;
				}
			}
			//bonusBetsPlaced.add(new BonusBet(Bonus.Dragon,units));
		}
	}

	@Override
	public double payout() {
		double winingUnits = 0;
		//base bets pay out
		for(BaseBet bb:baseBetsPlaced){
			if(bb.getOutcome() == handOutcome){
				if(handOutcome == Outcome.player){
					winingUnits = winingUnits + bb.getBetUnits() + bb.getBetUnits();
				} else if(handOutcome == Outcome.banker && isDragon == false){//If Dragon banker pushes
					winingUnits = winingUnits + bb.getBetUnits() + bb.getBetUnits();
				} 
				//in case of Dragon push the banker bet
				else if(handOutcome == Outcome.banker && isDragon && bb.getOutcome() == Outcome.banker){
					winingUnits = winingUnits + bb.getBetUnits();
				}else if(handOutcome == Outcome.tie){
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
			if(bonusBet.getBonus() == Bonus.Panda && isPanda){
				winingUnits = winingUnits + (bonusBet.getBetUnits()*25) + bonusBet.getBetUnits();
			} else if(bonusBet.getBonus() == Bonus.Dragon && isDragon){
				winingUnits = winingUnits + (bonusBet.getBetUnits()*40) + bonusBet.getBetUnits();
			} 
		}
		return winingUnits;
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

	class BonusBet {

		Bonus bonus;

		int betUnits;

		public void setBetUnits(int betUnits) {
			this.betUnits = betUnits;
		}

		public Bonus getBonus() {
			return bonus;
		}

		public int getBetUnits() {
			return betUnits;
		}

		BonusBet(Bonus b, int units) {
			bonus = b;
			betUnits = units;
		}
		
		@Override
		public boolean equals(Object arg0) {
			if(arg0 instanceof BonusBet == false){
				return false;
			}
			if(((BonusBet)arg0).getBonus() == this.getBonus()){
				return true;
			}
			return false;
		}
	}

}
