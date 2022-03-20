package com.mak.simulator;

import com.mak.casino.Baccarat;
import com.mak.casino.VegasBaccarat;
import com.mak.casino.Baccarat.Outcome;

import application.mak.btc.BtcScoreCalc;
import application.mak.btc.BtcScoreCard;

public abstract class AbstractBacaSimulator implements BacaSystemSimulator {

	protected Baccarat game;
	protected BtcScoreCalc calc;
	protected int ties;
	protected Outcome previousHand = Outcome.tie;
	protected boolean betStarted = false;
	protected boolean didWinPrev = false;

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
		while(game.hasNextHand() && canPlay()){
			Outcome o = game.dealOneHand();
			boolean isPlayer = false;
			if(o == Outcome.tie){
				ties++;
				continue;
			}
			Bet bet = whatToPlay();
			if(bet != Bet.noBet) {
				isPlayer = (bet == Bet.repeat && previousHand == Outcome.player) ? true : (bet == Bet.opposite && previousHand == Outcome.banker) ? true : false;
				if(!betStarted) {
					betStarted = true;
				}
			}
			calc.recordOutcome(o, bet == Bet.noBet ? 0: unitsToWagger(), isPlayer);
			if(o != Outcome.tie) {
				previousHand = o;
			}
			if(bet != Bet.noBet) {
				didWinPrev = o == Outcome.player && isPlayer ? true : o == Outcome.banker && !isPlayer ? true : false;
				System.out.println(didWinPrev ? "Won":"Lost");
			}
		}
	}
	
	public BtcScoreCard getScoreCard() {
		return calc.getScoreCard();
	}
	
	public int getTotalHands(){
		return getScoreCard().getNoOfHands()+ties;
	}

}