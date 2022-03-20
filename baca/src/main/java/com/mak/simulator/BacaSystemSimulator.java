package com.mak.simulator;

public interface BacaSystemSimulator {
	
	public enum Bet {opposite, repeat, noBet};
	
	public Bet whatToPlay();
	
	public int unitsToWagger();
	
	public boolean canPlay();
	
}
