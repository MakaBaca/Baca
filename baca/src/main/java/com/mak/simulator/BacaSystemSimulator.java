package com.mak.simulator;

public interface BacaSystemSimulator {
	
	enum Bet {opposite, repeat, noBet};
	
	public Bet whatToPlay();
	
	public int unitsToWagger();
	
}
