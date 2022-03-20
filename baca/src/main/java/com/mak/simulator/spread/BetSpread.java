package com.mak.simulator.spread;

import java.util.List;

public interface BetSpread {
	
	public List<Integer> betSpread = null;
	
	public int nextBet(boolean didWinPrev);

}
