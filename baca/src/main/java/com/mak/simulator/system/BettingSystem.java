package com.mak.simulator.system;

import com.mak.simulator.BacaSystemSimulator.Bet;

import application.mak.btc.BtcScoreCalc;

public interface BettingSystem {
	
	public Bet systemSays(BtcScoreCalc calc);

}
