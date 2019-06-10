package com.mak.simulator;

import java.util.ArrayList;
import java.util.List;

import com.mak.casino.Baccarat.Outcome;

import application.mak.btc.BtcScoreCard;

public class MDB5Simulator extends AbstractBacaSimulator implements BacaSystemSimulator {

	int stopWin = 5;

	int stopLoss = -10;

	int totalWinings = 0;

	int noOfHandsPlayed = 0;

	int fbStopWin = 3;

	int fbStopLoss = -5;

	int disparity = 2;

	int bbOneEvent = 13;

	int bbTwoEvent = 5;

	int bbThreePlusEvent = 5;

	int bbOneb4Hand = 65;

	int bbTwob4Hand = 64;

	int bbThreePlusb4Hand = 62;

	int bonusWinings = 6;

	boolean negativeProgression = false;

	int totalUnitsWaggered = 0;

	public static void main(String[] args) {
		int unitsWon = 0;
		int unitsWaggered = 0;
		int holdingUnits = 30;
		List<Integer> winningsList = new ArrayList<Integer>();
		List<Integer> holdingsList = new ArrayList<Integer>();
		for(int i=1; true; i++){
			MDB5Simulator sim = new MDB5Simulator();
			sim.prepareGame(78);
			sim.beginSimulation();
			unitsWon = unitsWon + sim.totalWinings;
			holdingUnits = holdingUnits + sim.totalWinings;
			unitsWaggered = unitsWaggered + sim.totalUnitsWaggered;
			winningsList.add(sim.totalWinings);
			holdingsList.add(holdingUnits);
			if(unitsWon >=15 || holdingUnits <= 0 || holdingUnits < sim.stopLoss){
				System.out.println("Total game played:"+i);
				break;
			}
		}
		System.out.println("unitsWon:"+unitsWon);
		System.out.println("unitsWaggered :"+unitsWaggered);
		//System.out.println("player Advantage : "+(unitsWon/unitsWaggered)*100);
		System.out.println(winningsList);
		System.out.println(holdingsList);
	}

	public MDB5Simulator(){
		System.out.println("1's\t2's\t3+\tOutcome\tVsays\tResult\tWagger\tScore\tprogress\t#");
		System.out.println("===================================================================================");
	}

	@Override
	public Bet whatToPlay() {
		int potentialEvent = calc.getPotentialEvent();
		BtcScoreCard scoreCard = getScoreCard();
		int ones = scoreCard.getOneInARow();
		int twos = scoreCard.getTwoInARow();
		int threePlus = scoreCard.getThreeOrMoreInARow();
		negativeProgression = true;
		//System.out.print(ones+"\t"+twos+"\t"+threePlus+"\t"+potentialEvent);
		//First preference goes for Big bet then bias bets
		Bet bb = checkForBigBet(ones, twos, threePlus, potentialEvent);
		if(bb != Bet.noBet){
			return bb;
		}
		// Check for bias on 1's
		if (ones - (twos+threePlus) >= disparity && potentialEvent == 1) {
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
			betOn = null;
			systemSays = whatToPlay();
			if(systemSays == Bet.opposite){
				betOn = (previousOutcome == Outcome.player) ?  Outcome.banker : Outcome.player;
			}else if(systemSays == Bet.repeat){
				betOn = (previousOutcome == Outcome.player) ?  Outcome.player : Outcome.banker;
			}
			Outcome o = game.dealOneHand();
			if(o == Outcome.tie){
				ties++;
				printSAPCounts();
				System.out.println("\tTie");
				continue;
			}else{
				calc.recordOutcome(o, 0, true);
				previousOutcome = o;
				printSAPCounts();
			}
			System.out.print("\t"+o);
			if(betOn != null){
				int waggerUnits = unitsToWagger();
				totalWinings = (betOn == o) ? totalWinings + waggerUnits : totalWinings - waggerUnits;
				System.out.print("\t"+betOn);
				if(betOn == o) {
					System.out.print("\tWon");
				} else{
					System.out.print("\tLost");
				}
				if(waggerUnits > 0){
					totalUnitsWaggered = totalUnitsWaggered + waggerUnits;
					noOfHandsPlayed ++;
					System.out.print("\t"+waggerUnits+"\t"+totalWinings+"\t"+negativeProgression+"\t\t"+getTotalHands());
				}
			}
			System.out.println();
		}
	}

	private void printSAPCounts(){
		BtcScoreCard scoreCard = getScoreCard();
		int ones = scoreCard.getOneInARow();
		int twos = scoreCard.getTwoInARow();
		int threePlus = scoreCard.getThreeOrMoreInARow();
		System.out.print(ones+"\t"+twos+"\t"+threePlus);
	}


}