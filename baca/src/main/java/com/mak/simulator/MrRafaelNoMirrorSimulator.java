package com.mak.simulator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections4.ListUtils;

import com.mak.casino.Baccarat.Outcome;

import application.mak.btc.MrRafaelScoreCard;

public class MrRafaelNoMirrorSimulator extends AbstractBacaSimulator {
	
	enum Roads {bigRoad, bigEyeBoy, smallRoad, cockroachRoad};
	
	Roads playOn = Roads.bigRoad;
	
	int mirrorRow = 0;
	
	List<MrRafaelScoreCard> scoreCard = new ArrayList<>();
	
	List<String> mirrorList = new ArrayList<>();
	
	List<String> prevMirrorCol = new ArrayList<>();
	
	List<Integer> betSpreads = Arrays.asList(1,1,1,1,1,1,1,5,5,10,10,15,20,25,35,45,60);
	
	int betIndex = -1;
	
	int stopLoss = -240;
	
	int stopWin = 15;
	
	double score;
	
	boolean isParlae = false;
	
	public MrRafaelNoMirrorSimulator(int stopLoss, int stopWin, List<Integer> betSpreads, int betIndex) {
		this.stopLoss = stopLoss;
		this.stopWin = stopWin;
		this.betSpreads = betSpreads;
		this.betIndex = betIndex;
		int randomWithMathRandom = (int) ((Math.random() * (4)) + 1);
		switch (randomWithMathRandom) {
		case 1:
			playOn = Roads.bigRoad;
			break;
		case 2:
			playOn = Roads.bigEyeBoy;
			break;
		case 3:
			playOn = Roads.smallRoad;
			break;
		case 4:
			playOn = Roads.cockroachRoad;
			break;
		default:
			break;
		}
		this.mirrorRow = (int) ((Math.random() * (2)) + 5);
		System.out.println("Play On --->"+playOn+ " mirrorRow--->"+ mirrorRow);
	}

	@Override
	public Bet whatToPlay() {
		Bet bet = Bet.noBet;
		if(calc.getNoOfHands() == 0) {
			return Bet.noBet;
		}
		MrRafaelScoreCard mr = calc.getMrRafaelScoreCard();
		scoreCard.add(mr);
		score = mr.getScore();
		/*
		 * if(calc.getNoOfHands() < this.mirrorRow) { return Bet.noBet; }
		 */
		populateMirrorList(mr);
		List<String> tmpList = new ArrayList<>();
		for(String s : this.mirrorList) {
			tmpList.add(s);
		}
		List<List<String>> beadRoad = ListUtils.partition(tmpList, this.mirrorRow);
		List<String> lastCol = beadRoad.size() > 0 ? beadRoad.get(beadRoad.size()-1) : new ArrayList<String>();
		if(this.prevMirrorCol.size() == 0 && lastCol.size() < this.mirrorRow) {
			return Bet.noBet;
		}
		if(lastCol.size() == this.mirrorRow) {
			this.prevMirrorCol = lastCol;
			lastCol = new ArrayList<>();
		}
		if(this.prevMirrorCol.size() == this.mirrorRow) {
			int lastColSize = lastCol.size();
			String refValue = this.prevMirrorCol.get(lastColSize);
			bet = decision(refValue, mr);
		}
		return bet;
	}
	
	private void populateMirrorList(MrRafaelScoreCard mr) {
		if(this.playOn == Roads.bigRoad) {
			this.mirrorList.add(mr.getPlayer().startsWith("O") ? "P":"B");
		}else if(this.playOn == Roads.bigEyeBoy && mr.isBebOn()) {
			this.mirrorList.add(mr.isChaosOnBeb() ? "C":"P");
		} else if(this.playOn == Roads.smallRoad && mr.isSrOn()) {
			this.mirrorList.add(mr.isChaosOnSr() ? "C":"P");
		}else if(this.playOn == Roads.cockroachRoad && mr.isCpOn()) {
			this.mirrorList.add(mr.isChaosOnCp() ? "C":"P");
		}
	}
	
	private Bet decision(String refValue, MrRafaelScoreCard mr) {
		Bet bet = Bet.noBet;
		Outcome wantedOutcome;
		if(this.playOn == Roads.bigRoad) {
			if(refValue.equals("P")) {
				bet = this.previousHand == Outcome.player ? Bet.opposite : Bet.repeat;
			}else {
				bet = this.previousHand == Outcome.banker ? Bet.opposite : Bet.repeat;
			}
		}else if(this.playOn == Roads.bigEyeBoy) {
			if(refValue.equals("C")) {
				wantedOutcome = mr.getNextBanker().substring(0, 1).equals("P") ? Outcome.banker : Outcome.player;
			}else {// refValue is Predictable "P"
				wantedOutcome = mr.getNextBanker().substring(0, 1).equals("C") ? Outcome.banker : Outcome.player;
			}
			bet = wantedOutcome == this.previousHand ? Bet.repeat : Bet.opposite;
		} else if(this.playOn == Roads.smallRoad) {
			if(refValue.equals("C")) {
				wantedOutcome = mr.getNextBanker().substring(1, 2).equals("P") ? Outcome.banker : Outcome.player;
			}else {// refValue is Predictable "P"
				wantedOutcome = mr.getNextBanker().substring(1, 2).equals("C") ? Outcome.banker : Outcome.player;
			}
			bet = wantedOutcome == this.previousHand ? Bet.repeat : Bet.opposite;
		}else if(this.playOn == Roads.cockroachRoad) {
			if(refValue.equals("C")) {
				wantedOutcome = mr.getNextBanker().substring(2, 3).equals("P") ? Outcome.banker : Outcome.player;
			}else {// refValue is Predictable "P"
				wantedOutcome = mr.getNextBanker().substring(2, 3).equals("C") ? Outcome.banker : Outcome.player;
			}
			bet = wantedOutcome == this.previousHand ? Bet.repeat : Bet.opposite;
		}
		return bet;
	}

	@Override
	public int unitsToWagger() {
		int unit = 0;
		try {
			if(this.betStarted) {
				if(!this.didWinPrev) {//lost previous hand
					betIndex++;
					unit = this.betSpreads.get(betIndex);
					isParlae = false;
				}else {//won previous hand
					if(!isParlae) {
						isParlae = true;
						unit = this.betSpreads.get(betIndex)*2;
					}else {
						betIndex = 0;
						isParlae = false;
						unit = this.betSpreads.get(betIndex);
					}
				}
			}
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Reached End of Bet Spread");
			unit = 0;
		}
		System.out.println("Score---->"+score+" Wagger-->"+unit);
		return unit;
	}
	
	@Override
	public boolean canPlay() {
		return score > stopLoss && score < stopWin ? true : false;
	}
	
	public static void main(String args[]) {
		/*
		 * for(int i =0 ; i < 500; i++) { int t = (int) ((Math.random() * (2)) + 5);
		 * System.out.println(t); }
		 */
		
		/*
		 * double d = 0.1; System.out.println( d != 0);
		 */
		
		MrRafaelNoMirrorSimulator sim = new MrRafaelNoMirrorSimulator(-240, 15, Arrays.asList(1,1,1,1,1,1,1,5,5,10,10,15,20,25,35,45,60), -1);
		sim.prepareGame(78);
		sim.beginSimulation();
		System.out.println(sim.calc.getShortHand().toString());
	}
}
