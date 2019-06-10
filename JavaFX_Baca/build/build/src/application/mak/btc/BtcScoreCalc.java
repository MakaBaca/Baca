package application.mak.btc;

import com.mak.casino.Baccarat.Outcome;

/**
 * Keeps Track the proceeding in btc score card style.
 * Calculates P/B, OR count and OT count.
 * Keeps track of the units wins and losses.
 * Warning this does record ties and ignores it
 * Also as per btc score card it assumes that bets on 
 * either banker or player and not on both.
 * THis doesn't consider bonus bets as these are not btc recommended.
 * @author Visitor
 *
 */
public class BtcScoreCalc {
	
	int betsPlaced = 0;
	
	Outcome handOutcome;
	
	Outcome tb4l;
	
	Outcome previousHand;
	
	Outcome betsOn;
	
	int orCount = 0;
	
	int otCount = 0;
	
	int noOfHands = 0;
	
	int playerCount = 0;
	
	int bankerCount = 0;
	
	boolean noCommission = false;
	
	boolean freeHand = false;
	
	double score = 0.0;
	
	/**
	 * Defines the object with noCommision Baccarat or 
	 * with commission which pays 1:0.95 on banker.
	 * Also this not does not consider Dragon winners as push bet. 
	 * Instead pass outcome as tie to recordOutcome if bets placed on banker.
	 * @param noCommision true for noCommision and
	 * 					  false for commission mode.
	 */
	public BtcScoreCalc(boolean noCommission) {
		this.noCommission = noCommission;
	}


	private static final String SLASH = "/";
	
	/**
	 * This records the outcome on btc score card and calculate PB, OR and OT count. 
	 * @param o {@link Outcome}
	 * @param bets placed bets, if this is 0 it will be considered as free and OnPlayer attribute will be ignored.
	 * @param onPlayer if bets placed on player set true else if bets on banker set false.
	 */
	public void recordOutcome(Outcome o, int bets, boolean onPlayer){
		if(o == Outcome.tie)
			return;
		noOfHands++;
		handOutcome = o;
		betsPlaced = bets;
		if(bets == 0){
			freeHand = true;
		}else{
			freeHand = false;
			if(onPlayer){
				betsOn = Outcome.player;
			}else{
				betsOn = Outcome.banker;
			}
		}
		if(handOutcome == Outcome.player){
			playerCount++;
		}else{
			bankerCount++;
		}
		if(noOfHands > 1){
			updateORCount();
		}else{
			tb4l = handOutcome;
		}
		if(noOfHands > 2)
			updateOTCount();
		previousHand = handOutcome;
	}
	
	private void updateOTCount() {
		if(handOutcome == tb4l){
			otCount--;
		}else{
			otCount++;
		}
		tb4l = previousHand;
	}

	private void updateORCount(){
		if(handOutcome == previousHand){
			orCount--;
		}else{
			orCount++;
		}
	}
	
	/**
	 * This returns the score as BtcScoreCard instance.
	 * Things to note down are if its a free hand ignore isBetOnPlayer and didWin.
	 * If not free hand isBetOnPlayer true meaning player bet and false meaning banker bets.
	 * @return result as {@link BtcScoreCard} instance. Warning this ignores ties and returns an empty score card. 
	 */
	public BtcScoreCard getScoreCard(){
		//Ignores tie and returns empty score card.
		if(handOutcome == Outcome.tie)
			return new BtcScoreCard();
		boolean isBetOnPlayer = false;
		boolean didWin = false;
		if(!freeHand){
			if(betsOn == Outcome.player){
				isBetOnPlayer = true;
			}
			if(betsOn == handOutcome){
				didWin = true;
				if(noCommission || handOutcome == Outcome.player){
					score = score + betsPlaced;
				}
				else if(!noCommission && handOutcome == Outcome.banker){
					score = score + (betsPlaced * 0.95);
				}
			}else {
				score = score - betsPlaced;
			}
		}
		BtcScoreCard c = new BtcScoreCard(playerCount+BtcScoreCalc.SLASH+bankerCount, 
				orCount, 
				otCount, 
				noOfHands, 
				score, 
				betsPlaced, isBetOnPlayer, didWin,handOutcome);
		return c;
	}

}
