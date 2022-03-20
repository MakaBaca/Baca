package com.mak.simulator;

import java.util.ArrayList;
import java.util.List;

import com.mak.casino.Baccarat.Outcome;

import application.mak.btc.BtcScoreCard;

public class VinSapSimulator extends AbstractBacaSimulator implements BacaSystemSimulator {

	int stopWin = 5;

	int stopLoss = -30;

	int totalWinings = 0;

	int noOfHandsPlayed = 0;

	int fbStopWin = 3;

	int fbStopLoss = -5;

	int disparity = 2;
	
	int bbOneEvent = 14;
	
	int bbTwoEvent = 6;
	
	int bbThreePlusEvent = 6;
	
	int bbOneb4Hand = 65;
	
	int bbTwob4Hand = 64;
	
	int bbThreePlusb4Hand = 62;
	
	int bonusWinings = 6;
	
	boolean negativeProgression = false;
	
	int totalUnitsWaggered = 0;

	public static void main(String[] args) {
		int unitsWon = 0;
		int unitsWaggered = 0;
		List<Integer> winningsList = new ArrayList<Integer>();
		for(int i=1; i<=1; i++){
			
			VinSapSimulator sim = new VinSapSimulator();
			sim.prepareGame(78);
			sim.beginSimulation();
			unitsWon = unitsWon + sim.totalWinings;
			unitsWaggered = unitsWaggered + sim.totalUnitsWaggered;
			winningsList.add(unitsWon);
			if(unitsWon >=30 || unitsWon <= -30 ){
				System.out.println("Total game played:"+i);
				break;
			}
		}
		System.out.println("unitsWon:"+unitsWon);
		System.out.println("unitsWaggered :"+unitsWaggered);
		//System.out.println("player Advantage : "+(unitsWon/unitsWaggered)*100);
		System.out.println(winningsList);
	}

	@Override
	public Bet whatToPlay() {
		int potentialEvent = calc.getPotentialEvent();
		BtcScoreCard scoreCard = getScoreCard();
		int ones = scoreCard.getOneInARow();
		int twos = scoreCard.getTwoInARow();
		int threePlus = scoreCard.getThreeOrMoreInARow();
		negativeProgression = false;
		System.out.print(ones+"\t"+twos+"\t"+threePlus+"\t PotentialEvent is: "+potentialEvent);
		//First preference goes for Big bet then bias bets
		Bet bb = checkForBigBet(ones, twos, threePlus, potentialEvent);
		if(bb != Bet.noBet){
			return bb;
		}
		// Check for bias on 1's
		if (ones - twos >= disparity && potentialEvent == 1) {
			return Bet.opposite;
		}
		// Check for bias on 2's against 1's or 2's and 3's against 1's.
		else if ((twos - ones >= disparity || (twos+threePlus) - ones >= disparity) && potentialEvent == 1) {
			return Bet.repeat;
		}
		// Check for bias on 2's against 1's.
		/*else if (twos - ones >= disparity && potentialEvent == 1) {
			if(twos == bbTwoEvent && getTotalHands() < bbTwob4Hand){
				negativeProgression = true;
			}
			return Bet.repeat;
		}*/
		// Check for bias on 2's against 3's
		else if (twos - threePlus >= disparity && potentialEvent == 2) {
			return Bet.opposite;
		}
		// Check for bias on 3's against 2's
		else if (threePlus - twos >= disparity && potentialEvent == 2) {
			return Bet.repeat;
		}
		
		return Bet.noBet;
	}
	
	public Bet checkForBigBet(int ones, int twos, int threePlus, int potentialEvent){
		if(ones == bbOneEvent && getTotalHands() < bbOneb4Hand && potentialEvent == 1){
			negativeProgression = true;
			return Bet.opposite;
		}else if(twos == bbTwoEvent && getTotalHands() < bbTwob4Hand  && potentialEvent == 1){
			negativeProgression = true;
			return Bet.repeat;
		} else if(twos == bbTwoEvent && getTotalHands() < bbTwob4Hand  && potentialEvent == 2){
			negativeProgression = true;
			return Bet.opposite;
		} else if(threePlus == bbThreePlusEvent && getTotalHands() < bbThreePlusb4Hand && potentialEvent == 2){
			negativeProgression = true;
			return Bet.repeat;
		}
		return Bet.noBet;
	}

	@Override
	public int unitsToWagger() {
		if(totalWinings <= stopLoss && !negativeProgression){
			return 0;
		} else if(negativeProgression  && totalWinings < stopWin){
			if(stopWin - totalWinings > totalWinings - stopLoss)
				return totalWinings - stopLoss;
			return stopWin - totalWinings;
		} else if(negativeProgression && totalWinings == stopWin){
			if(bonusWinings - totalWinings > totalWinings - stopLoss)
				return totalWinings - stopLoss;
			return bonusWinings - totalWinings;
		} else if(!negativeProgression && totalWinings < fbStopWin && totalWinings > fbStopLoss){
			return 1;
		}else{
			return 0;
		}
	}
	
	@Override
	public void beginSimulation() {
		Outcome previousOutcome = null;
		Bet systemSays = Bet.noBet;
		Outcome betOn = null;
		while(game.hasNextHand()){
			systemSays = whatToPlay();
			if(systemSays == Bet.opposite){
				betOn = (previousOutcome == Outcome.player) ?  Outcome.banker : Outcome.player;
			}else if(systemSays == Bet.repeat){
				betOn = (previousOutcome == Outcome.player) ?  Outcome.player : Outcome.banker;
			}
			Outcome o = game.dealOneHand();
			if(o == Outcome.tie){
				ties++;
				System.out.println("====================>Tie<====================");
				continue;
			}else{
				calc.recordOutcome(o, 0, true);
				previousOutcome = o;
			}
			System.out.print("\t Outcome :"+o+"\t");
			if(betOn != null){
				int waggerUnits = unitsToWagger();
				totalWinings = (betOn == o) ? totalWinings + waggerUnits : totalWinings - waggerUnits;
				if(waggerUnits > 0){
					totalUnitsWaggered = totalUnitsWaggered + waggerUnits;
					noOfHandsPlayed ++;
					System.out.print("systemSays :"+betOn+"\t waggerUnits : "+waggerUnits+"\t totalWinings -->"+totalWinings+"\t"+"negativeProgression -->"+negativeProgression+" Hand # "+getTotalHands());
				}
				
			}
			System.out.println();
		}
	}

	@Override
	public boolean canPlay() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
