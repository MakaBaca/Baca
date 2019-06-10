package com.mak.simulator;

import java.util.ArrayList;
import java.util.List;

import com.mak.casino.Baccarat.Outcome;

public class Plus1Simulator extends AbstractBacaSimulator implements BacaSystemSimulator {
	
	int baseUnit = 1;
	
	int stopLoss = 2000;
	
	int stopWin = 100;
	
	List<Integer> lostList = new ArrayList<Integer>();
	
	public static void main(String args[]){
		int noOfGames = 0;
		double totalScore = 0;
		while(true){
			noOfGames++;
			Plus1Simulator sim = new Plus1Simulator();
			sim.prepareGame(78);
			sim.beginSimulation();
			double score = sim.calc.getScoreCard().getScore();
			totalScore = totalScore + score;
			System.out.println("Score on Game "+noOfGames+" is :"+score);
			System.out.println("Total on Game "+noOfGames+" is :"+totalScore);
			if( totalScore >= sim.stopWin || totalScore > sim.stopLoss){
				System.out.println("Final Score :"+totalScore);
				break;
			}
		}
	}
	
	public Plus1Simulator() {
		System.out.println("Outcome\tScore\tStack List");
		System.out.println("=================================================");
	}

	@Override
	public Bet whatToPlay() {
		//NO decision needed now as always banker wager
		return null;
	}

	@Override
	public int unitsToWagger() {
		if(lostList.size() == 0){
			return baseUnit;
		}else{
			return lostList.get(0)+1;
		}
	}
	
	@Override
	public void beginSimulation() {
		Outcome o = null;
		Outcome betOn = Outcome.banker;
		while(game.hasNextHand()){
			int wager = unitsToWagger();
			o = game.dealOneHand();
			if(o == Outcome.tie){
				ties++;
			}
			calc.recordOutcome(o, wager, false);
			if(o != Outcome.tie && betOn != o){
				lostList.add(wager);
			}else if(betOn == o && lostList.size() >0){
				lostList.remove(0);
			}
			String printScore  = Double.toString(calc.getScoreCard().getScore());
			if(printScore.length() > 6){
				printScore = printScore.substring(0, 5);
			}
			System.out.println(o+"\t"+printScore+"\t"+lostList);
		}
	}
}
