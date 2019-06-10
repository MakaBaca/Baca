package application;

import com.mak.casino.Baccarat.Outcome;

public class BigEyeRod6Xn {
	
	private int rowIndex =0;
	
	private int colIndex = 0;
	
	private  Outcome lastHand;
	
	private Outcome lastPlayerBankerOutcome;
	
	private boolean onAPole = false;
	
	private int poleBreakColIndex = 0;
	
	private int noOfPlayers = 0;
	
	private int noOfBankers = 0;
	
	private int noOfTies = 0;
	
	public int getNoOfPlayers() {
		return noOfPlayers;
	}

	public int getNoOfBankers() {
		return noOfBankers;
	}

	public int getNoOfTies() {
		return noOfTies;
	}

	public int getNoOfHands() {
		return noOfHands;
	}

	private int noOfHands = 0;
	
	private int continuesTies = 0;
	
	private boolean [] []  grid = new boolean[6][100];
	
	public BigEyeRod6Xn() {
		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 100; j++){
				grid[i][j] = false;
			}
		}
	}
	
	public BigEyeRodCell updateHand(Outcome handOutcome){
		BigEyeRodCell cellValue = null;
		//First Hand tie
		if(noOfPlayers == 0 && 
		   noOfBankers == 0 &&	
		   handOutcome == Outcome.tie){
			continuesTies++;
			cellValue = startWithTies();
			noOfTies++;
		}else if(handOutcome == Outcome.tie){
			continuesTies++;
			noOfTies++;
			cellValue = enterTies();
		}
		else {
			//First outcome after ties
			if(noOfHands > 0 && noOfPlayers == 0 && noOfBankers == 0){
				//no update to index
				lastPlayerBankerOutcome = handOutcome;
				cellValue = enterTies();
			}
			//first hand player or banker
			else if(noOfHands == 0){
				//no Update to Index
				cellValue = enterOutcome(handOutcome);
				grid[rowIndex][colIndex] = true;
			}
			//Other than first hand and same as last outcome
			else if(lastPlayerBankerOutcome == handOutcome) {
				if(rowIndex < 5 && grid[rowIndex+1][colIndex] == false && !onAPole){
					rowIndex++;
				}
				//on a Pole(L)
				else {
					onAPole = true;
					if(poleBreakColIndex == 0){
						poleBreakColIndex = colIndex + 1;
					}
					colIndex++;
				}
				cellValue = enterOutcome(handOutcome);
				grid[rowIndex][colIndex] = true;
			}
			//Other than first hand and not same as last outcome
			else if(lastPlayerBankerOutcome != handOutcome){
				rowIndex = 0;
				if(poleBreakColIndex > 0){
					colIndex = poleBreakColIndex;
					poleBreakColIndex = 0;
					onAPole = false;
				}else{
					colIndex++;
				}
				cellValue = enterOutcome(handOutcome);
				grid[rowIndex][colIndex] = true;
			}
			lastPlayerBankerOutcome = handOutcome;
			continuesTies = 0;
			if(handOutcome == Outcome.player){
				noOfPlayers++;
			}else{
				noOfBankers++;
			}
		}
		lastHand = handOutcome;
		noOfHands++;
		return cellValue;
	}
	
	private BigEyeRodCell startWithTies(){
		BigEyeRodCell cellValue = null;
		switch (continuesTies){
		case 0:
			break;
		case 1: 
			cellValue =  new BigEyeRodCell(rowIndex, colIndex, "images/firstHandTie.png"); 
			break;
		case 2:
			cellValue =  new BigEyeRodCell(rowIndex, colIndex, "images/firstHand2Tie.png"); 
			break;
		case 3:
			cellValue =  new BigEyeRodCell(rowIndex, colIndex, "images/firstHand3Tie.png"); 
			break;
		case 4:
			cellValue =  new BigEyeRodCell(rowIndex, colIndex, "images/firstHand4Tie.png"); 
			break;
		default:
			if(continuesTies > 4){
				cellValue =  new BigEyeRodCell(rowIndex, colIndex, "images/firstHand4+Tie.png"); 
			}
		}
		return cellValue;
	}
	
	private BigEyeRodCell enterTies(){
		BigEyeRodCell cellValue = null;
		if(lastPlayerBankerOutcome == Outcome.player){
			switch (continuesTies){
			case 0:
				break;
			case 1: 
				cellValue =  new BigEyeRodCell(rowIndex, colIndex, "images/playerTie.png"); 
				break;
			case 2:
				cellValue =  new BigEyeRodCell(rowIndex, colIndex, "images/player2Tie.png"); 
				break;
			case 3:
				cellValue =  new BigEyeRodCell(rowIndex, colIndex, "images/player3Tie.png"); 
				break;
			case 4:
				cellValue =  new BigEyeRodCell(rowIndex, colIndex, "images/player4Tie.png"); 
				break;
			default:
				if(continuesTies > 4){
					cellValue =  new BigEyeRodCell(rowIndex, colIndex, "images/player4+Tie.png"); 
				}
			}
		}else if(lastPlayerBankerOutcome == Outcome.banker){

			switch (continuesTies){
			case 0:
				break;
			case 1: 
				cellValue =  new BigEyeRodCell(rowIndex, colIndex, "images/bankerTie.png"); 
				break;
			case 2:
				cellValue =  new BigEyeRodCell(rowIndex, colIndex, "images/banker2Tie.png"); 
				break;
			case 3:
				cellValue =  new BigEyeRodCell(rowIndex, colIndex, "images/banker3Tie.png"); 
				break;
			case 4:
				cellValue =  new BigEyeRodCell(rowIndex, colIndex, "images/banker4Tie.png"); 
				break;
			default:
				if(continuesTies > 4){
					cellValue =  new BigEyeRodCell(rowIndex, colIndex, "images/banker4+Tie.png"); 
				}
			}
		
		}
		return cellValue;
	}
	
	/**
	 * Can only be player or banker
	 * @param handOutcome
	 * @return
	 */
	private BigEyeRodCell enterOutcome(Outcome handOutcome){
		BigEyeRodCell cellValue = null;
		if(handOutcome == Outcome.player){
			cellValue =  new BigEyeRodCell(rowIndex, colIndex, "images/player.png");
		}else if(handOutcome == Outcome.banker){
			cellValue =  new BigEyeRodCell(rowIndex, colIndex, "images/banker.png");
		}
		return cellValue;
	}
	
	

}