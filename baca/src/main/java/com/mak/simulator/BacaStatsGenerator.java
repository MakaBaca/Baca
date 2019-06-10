package com.mak.simulator;

import application.mak.btc.BtcScoreCard;

public class BacaStatsGenerator extends AbstractBacaSimulator {
	
	public static void main(String[] args) {
		BacaStatsGenerator stats = new BacaStatsGenerator();
		int totalShoes = 100;
		int ones = 0;
		int twos = 0;
		int threePlus = 0;
		int totalHands = 0;
		System.out.println("==========================================================");
		for(int i =0; i < totalShoes; i++){
			stats.prepareGame(78);
			stats.beginSimulation();
			BtcScoreCard card = stats.getScoreCard();
			ones = ones + card.getOneInARow();
			twos = twos + card.getTwoInARow();
			threePlus = threePlus + card.getThreeOrMoreInARow();
			totalHands = totalHands + card.getNoOfHands()+stats.ties;
			System.out.println("Total Hands : "+Integer.toString(card.getNoOfHands()+stats.ties));
			System.out.println("Ties : "+Integer.toString(stats.ties));
			System.out.print(stats.getScoreCard());
			System.out.println("==========================================================");
		}
		System.out.println("Average 1's in "+totalShoes+" Shoes is :"+(ones/totalShoes));
		System.out.println("Average 2's in "+totalShoes+" Shoes is :"+(twos/totalShoes));
		System.out.println("Average 3+ in "+totalShoes+" Shoes is :"+(threePlus/totalShoes));
		System.out.println("Average Hands in "+totalShoes+" Shoes is :"+(totalHands/totalShoes));
	}

}
