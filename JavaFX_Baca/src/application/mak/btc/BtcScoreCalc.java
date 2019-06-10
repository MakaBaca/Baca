package application.mak.btc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mak.casino.Baccarat.Outcome;

/**
 * Keeps Track the proceeding in btc score card style.
 * Calculates P/B, OR count and OT count.
 * Keeps track of the units wins and losses.
 * Warning this does record ties and ignores it
 * Also as per btc score card it assumes that bets on 
 * either banker or player and not on both.
 * This doesn't consider bonus bets as these are not btc recommended.
 * This also calculates unweighed vinSAP
 * @author Visitor
 *
 */
public class BtcScoreCalc {
	
	private int betsPlaced = 0;
	
	private Outcome handOutcome;
	
	private Outcome tb4l;
	
	private Outcome t2b4l;
	
	private Outcome previousHand;
	
	private Outcome betsOn;
	
	private int orCount = 0;
	
	private int otCount = 0;
	
	private int oottCount = 0;
	
	private int noOfHands = 0;
	
	private int playerCount = 0;
	
	private int bankerCount = 0;
	
	private boolean noCommission = false;
	
	private boolean freeHand = false;
	
	private double score = 0.0;
	
	private int oneInARow = 0;
	
	private int twoInARow = 0;
	
	private int threeOrMoreInARow = 0;
	
	private  int potentialEvent = 1;
	
	private List<Integer> shortHand;
	
	private Outcome firstOutcome;
	
	private int currentEvent = 1;
	
	private Outcome fourDIndicates;
	
	private String did4DWin = null;
	
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
		shortHand = new ArrayList<>();
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
		if(noOfHands == 1){
			firstOutcome = o;
		}
		handOutcome = o;
		did4DWin = null;
		if(fourDIndicates != null){
			did4DWin = fourDIndicates == handOutcome ? "Y":"N";
		}
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
		
		if(noOfHands > 3){
			updateOOTTCount();
		}
		
		if(noOfHands > 2)
			updateOTCount();
		previousHand = handOutcome;
		update4DIndication();
	}
	
	private void updateOTCount() {
		if(handOutcome == tb4l){
			otCount--;
		}else{
			otCount++;
		}
		t2b4l = tb4l;
		tb4l = previousHand;
	}
	
	private void updateOOTTCount() {
		if(handOutcome == t2b4l){
			oottCount--;
		}else{
			oottCount++;
		}
	}
	
	private void update4DIndication(){
		if(noOfHands <= 3){
			return;
		}
		fourDIndicates = null;
		int precedenceDisparity = 5;
		int otb4lPrecendence = 2;
		int pbDisparity = playerCount - bankerCount;
		pbDisparity = pbDisparity > 0 ?  pbDisparity : pbDisparity * -1;
		int orDisparity = orCount > 0 ?  orCount : orCount * -1;
		int otDisparity = otCount > 0 ?  otCount : otCount * -1;
		int oottDisparity = oottCount > 0 ?  oottCount : oottCount * -1;
		int dispArray[] = {pbDisparity, orDisparity, otDisparity, oottDisparity};
		int bankerBias = 0;
		int playerBias = 0;
		Arrays.sort(dispArray);
		//if only one disparity is greater that 5 
		//then ignore others disparities less than 6. 
		if(dispArray[3] > precedenceDisparity && dispArray[2] <= precedenceDisparity){
			if(pbDisparity > precedenceDisparity){
				fourDIndicates = playerCount > bankerCount ? Outcome.player : Outcome.banker;
			}else if(orDisparity > precedenceDisparity){
				if(orCount > 0)
					fourDIndicates = handOutcome == Outcome.banker ? Outcome.player : Outcome.banker;
				else 
					fourDIndicates = handOutcome == Outcome.banker ? Outcome.banker : Outcome.player;
			}else if(otDisparity > precedenceDisparity){
				if(otCount > 0)
					fourDIndicates = tb4l == Outcome.player ? Outcome.banker : Outcome.player;
				else 
					fourDIndicates = tb4l == Outcome.player ? Outcome.player : Outcome.banker;
			}else if(oottDisparity > precedenceDisparity){
				if(oottCount > 0)
					fourDIndicates = t2b4l == Outcome.player ? Outcome.banker : Outcome.player;
				else 
					fourDIndicates = t2b4l == Outcome.player ? Outcome.player : Outcome.banker;
			}
		}
		//if more than one disparity is greater than 6 then check bias
		else if(dispArray[3] > precedenceDisparity && dispArray[2] > precedenceDisparity){
			//Checking bias on PB
			if(pbDisparity > precedenceDisparity){
				@SuppressWarnings("unused")
				int dummy = bankerCount > playerCount ? bankerBias++ : playerBias++;
			}
			//Checking bias on OR
			if(orDisparity > precedenceDisparity && orCount > 0){
				@SuppressWarnings("unused")
				int dummy = handOutcome == Outcome.player ? bankerBias++ : playerBias++;
			}else if(orDisparity > precedenceDisparity && orCount < 0){
				@SuppressWarnings("unused")
				int dummy = handOutcome == Outcome.player ? playerBias++ : bankerBias++;
			}
			//Checking bias on OT
			if(otDisparity > precedenceDisparity && otCount > 0){
				@SuppressWarnings("unused")
				int dummy = tb4l == Outcome.player ? bankerBias++ : playerBias++;
			}else if(otDisparity > precedenceDisparity && otCount < 0){
				@SuppressWarnings("unused")
				int dummy = tb4l == Outcome.player ? playerBias++ : bankerBias++;
			}
			//Checking Bias on OOTT
			if(oottDisparity > precedenceDisparity && oottCount > 0){
				@SuppressWarnings("unused")
				int dummy = t2b4l == Outcome.player ? bankerBias++ : playerBias++;
			}else if(oottDisparity > precedenceDisparity && oottCount < 0){
				@SuppressWarnings("unused")
				int dummy = t2b4l == Outcome.player ? playerBias++ : bankerBias++;
			}
			if(bankerBias > playerBias && playerBias == 0)
				fourDIndicates = Outcome.banker;
			else if(playerBias > bankerBias && bankerBias == 0)
				fourDIndicates = Outcome.player;
			else if(playerBias == bankerBias)
				fourDIndicates = null;
		}
		//if all disparity is less than 2 then play OTB4L
		else if(dispArray[3] <= otb4lPrecendence){
			fourDIndicates = tb4l == Outcome.player ? Outcome.banker : Outcome.player;
		}
		//if any of the disparity is greater than 2
		//and non greater 6 
		//then check for bias
		else if(dispArray[3] > otb4lPrecendence){
			//checking bias on pbDisparity
			if(pbDisparity <= otb4lPrecendence){
				@SuppressWarnings("unused")
				int dummy = tb4l == Outcome.player ? bankerBias++ : playerBias++;
			}else if(pbDisparity > otb4lPrecendence && playerCount > bankerCount){
				playerBias++;
			}else if(pbDisparity > otb4lPrecendence && playerCount < bankerCount){
				bankerBias++;
			}
			//checking bias on orDisparity
			if(orDisparity <= otb4lPrecendence){
				@SuppressWarnings("unused")
				int dummy = tb4l == Outcome.player ? bankerBias++ : playerBias++;
			}else if(orDisparity > otb4lPrecendence && orCount > 0){
				@SuppressWarnings("unused")
				int dummy = handOutcome == Outcome.player ? bankerBias++ : playerBias++;
			}else if(orDisparity > otb4lPrecendence && orCount < 0){
				@SuppressWarnings("unused")
				int dummy = handOutcome == Outcome.player ? playerBias++ : bankerBias++;
			}
			//checking bias on otDisparity
			if(otDisparity <= otb4lPrecendence){
				@SuppressWarnings("unused")
				int dummy = tb4l == Outcome.player ? bankerBias++ : playerBias++;
			}else if(otDisparity > otb4lPrecendence && otCount > 0){
				@SuppressWarnings("unused")
				int dummy = tb4l == Outcome.player ? bankerBias++ : playerBias++;
			}else if(otDisparity > otb4lPrecendence && otCount < 0){
				@SuppressWarnings("unused")
				int dummy = tb4l == Outcome.player ? playerBias++ : bankerBias++;
			}
			//checking bias on oottDisparity
			if(oottDisparity <= otb4lPrecendence){
				@SuppressWarnings("unused")
				int dummy = tb4l == Outcome.player ? bankerBias++ : playerBias++;
			}else if(oottDisparity > otb4lPrecendence && oottCount > 0){
				@SuppressWarnings("unused")
				int dummy = t2b4l == Outcome.player ? bankerBias++ : playerBias++;
			}else if(oottDisparity > otb4lPrecendence && oottCount < 0){
				@SuppressWarnings("unused")
				int dummy = t2b4l == Outcome.player ? playerBias++ : bankerBias++;
			}
			if(bankerBias > playerBias)
				fourDIndicates = Outcome.banker;
			else if(playerBias > bankerBias)
				fourDIndicates = Outcome.player;
			else if(playerBias == bankerBias)
				fourDIndicates = null;
		}
	}

	private void updateORCount(){
		if(handOutcome == previousHand){
			orCount--;
			currentEvent++;
			if(potentialEvent < 3){
				potentialEvent ++;
			}
		}else{
			orCount++;
			switch (potentialEvent) {
			case 1:
				oneInARow++;
				break;
			case 2:
				twoInARow++;
				break;	
			case 3:
				threeOrMoreInARow++;
				break;
			default:
				break;
			}
			potentialEvent = 1;
			
			//on opposite add current event to short hand and reset to 1
			shortHand.add(currentEvent);
			currentEvent = 1;
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
		c.setOneInARow(oneInARow);
		c.setTwoInARow(twoInARow);
		c.setThreeOrMoreInARow(threeOrMoreInARow);
		return c;
	}
	
	/**
	 * This returns the score as FiveDScoreCard instance.
	 * Things to note down are if its a free hand ignore isBetOnPlayer and didWin.
	 * If not free hand isBetOnPlayer true meaning player bet and false meaning banker bets.
	 * @return result as {@link FiveDScoreCard} instance. Warning this ignores ties and returns an empty score card. 
	 */
	public FiveDScoreCard get5DScoreCard(){
		//Ignores tie and returns empty score card.
		if(handOutcome == Outcome.tie)
			return new FiveDScoreCard();
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
		FiveDScoreCard c = new FiveDScoreCard(playerCount+BtcScoreCalc.SLASH+bankerCount, 
				orCount, 
				otCount, 
				oottCount,
				noOfHands, 
				score, 
				betsPlaced, isBetOnPlayer, didWin,handOutcome,fourDIndicates,did4DWin);
		c.setOneInARow(oneInARow);
		c.setTwoInARow(twoInARow);
		c.setThreeOrMoreInARow(threeOrMoreInARow);
		return c;
	}
	
	public int getOneInARow() {
		return oneInARow;
	}

	public int getTwoInARow() {
		return twoInARow;
	}

	public int getThreeOrMoreInARow() {
		return threeOrMoreInARow;
	}

	public int getPotentialEvent(){
		return potentialEvent;
	}

	public List<Integer> getShortHand() {
		return shortHand;
	}

	public Outcome getFirstOutcome() {
		return firstOutcome;
	}
}
