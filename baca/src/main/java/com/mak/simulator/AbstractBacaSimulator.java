package com.mak.simulator;

import com.mak.casino.Baccarat;
import com.mak.casino.VegasBaccarat;
import com.mak.casino.Baccarat.Outcome;

import application.mak.btc.BtcScoreCalc;
import application.mak.btc.BtcScoreCard;

public abstract class AbstractBacaSimulator {

	protected Baccarat game;
	protected BtcScoreCalc calc;
	protected int ties;

	public AbstractBacaSimulator() {
		super();
	}

	public void prepareGame(int lastCardPostion) {
		game = new VegasBaccarat();
		calc = new BtcScoreCalc(false);
		game.prepareShoe();
		game.setLastCardPosition(lastCardPostion);
		ties = 0;
	}

	public void beginSimulation() {
		while(game.hasNextHand()){
			Outcome o = game.dealOneHand();
			if(o == Outcome.tie){
				ties++;
			}
			calc.recordOutcome(o, 0, true);
		}
	}
	
	public BtcScoreCard getScoreCard() {
		return calc.getScoreCard();
	}
	
	public int getTotalHands(){
		return getScoreCard().getNoOfHands()+ties;
	}

}