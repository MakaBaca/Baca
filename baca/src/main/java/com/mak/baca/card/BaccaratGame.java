package com.mak.baca.card;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.UnaryOperator;

import com.mak.excel.IExcelWriter;
import com.mak.excel.PoiExcelWriter;

public class BaccaratGame {

	private Shoe bacaShoe;

	private List<String> bigRoad;

	private List<String> result;

	private int lastCardPosition = 0;

	private int noOfNaturals = 0;

	private static final String BLANK = "";

	public int getNoOfNaturals() {
		return noOfNaturals;
	}

	public int getNoOfDragons() {
		return noOfDragons;
	}

	public int getNoOfPandas() {
		return noOfPandas;
	}

	private int noOfDragons = 0;

	private int noOfPandas = 0;

	private List<String> drangonHands;

	private List<String> pandaHands;

	public enum Outcome {
		banker("B"), player("P"), tie("T");

		String result;

		Outcome(String r) {
			result = r;
		}

		public String getResult() {
			return result;
		}
	}

	public BaccaratGame() {
		bacaShoe = new Shoe(8);
		result = new ArrayList<String>();
		bacaShoe.shuffleShoe(100);
		bacaShoe.stackShoe();
		bacaShoe.burnCards();
		drangonHands = new ArrayList<String>();
		pandaHands = new ArrayList<String>();
		System.out.println("Stack of cards:" + System.lineSeparator() + bacaShoe.getStackOfCards());
	}

	public void setLastCardPosition(int lastCardPosition) {
		this.lastCardPosition = lastCardPosition;
	}

	public Outcome dealHand() {
		int playerValue = 0;
		int bankerValue = 0;
		int ptcValue = 0;
		int tcruleValue = 0;
		playerValue = baccaratCardValue(bacaShoe.dealOneCard());
		bankerValue = baccaratCardValue(bacaShoe.dealOneCard());
		playerValue = playerValue + baccaratCardValue(bacaShoe.dealOneCard());
		bankerValue = bankerValue + baccaratCardValue(bacaShoe.dealOneCard());
		// reducing 10 if total greater than 10.
		if (playerValue > 9) {
			playerValue = playerValue - 10;
		}
		if (bankerValue > 9) {
			bankerValue = bankerValue - 10;
		}

		if (playerValue > 7 || bankerValue > 7) {// Natural Check
			noOfNaturals++;
			return determineOutcome(playerValue, bankerValue);
		} else if (playerValue > 5 && bankerValue > 5) {// No draw bbq
			return determineOutcome(playerValue, bankerValue);
		} else if (playerValue > 5 && bankerValue <= 5) {// only banker hit
			bankerValue = bankerValue + baccaratCardValue(bacaShoe.dealOneCard());
			// reducing 10 if total greater than 10.
			if (bankerValue > 9) {
				bankerValue = bankerValue - 10;
			}
			if (isDragon(playerValue, bankerValue)) {
				noOfDragons++;
			}
			return determineOutcome(playerValue, bankerValue);
		} else if (bankerValue == 7 && playerValue <= 5) {// only player hit
			playerValue = playerValue + baccaratCardValue(bacaShoe.dealOneCard());
			// reducing 10 if total greater than 10.
			if (playerValue > 9) {
				playerValue = playerValue - 10;
			}
			if (isPanda(playerValue, bankerValue)) {
				noOfPandas++;
			}
			return determineOutcome(playerValue, bankerValue);
		} else {
			// Third card rule
			// A math formula equivalent to the drawing rules is: take the value
			// of Player's third card,
			// counting 8 and 9 as -2 and -1. Divide by 2 always rounding
			// towards zero.
			// (Thus -1, 0, and 1 all round to zero when this division is done.)
			// Add three to the result.
			// If the Banker's current total is this final value or less, then
			// draw; otherwise, stand.
			ptcValue = baccaratCardValue(bacaShoe.dealOneCard());
			playerValue = playerValue + ptcValue;
			// reducing 10 if total greater than 10.
			if (playerValue > 9) {
				playerValue = playerValue - 10;
			}
			if (ptcValue >= 0 && ptcValue <= 7) {
				tcruleValue = ptcValue;
			} else if (ptcValue == 8) {
				tcruleValue = -2;
			} else {
				tcruleValue = -1;
			}

			tcruleValue = tcruleValue / 2 + 3;
			if (bankerValue <= tcruleValue) {
				bankerValue = bankerValue + baccaratCardValue(bacaShoe.dealOneCard());
				// reducing 10 if total greater than 10.
				if (bankerValue > 9) {
					bankerValue = bankerValue - 10;
				}
				if (isPanda(playerValue, bankerValue)) {
					noOfPandas++;
				} else if (isDragon(playerValue, bankerValue)) {
					noOfDragons++;
				}
				return determineOutcome(playerValue, bankerValue);
			} else {
				if (isPanda(playerValue, bankerValue)) {
					noOfPandas++;
				}
				return determineOutcome(playerValue, bankerValue);
			}
		}
	}

	private Outcome determineOutcome(int playerValue, int bankerValue) {
		if (playerValue == bankerValue) {
			return Outcome.tie;
		} else if (playerValue > bankerValue) {
			return Outcome.player;
		} else {
			return Outcome.banker;
		}
	}

	public int baccaratCardValue(BaccaratCards card) {
		int cardValue = card.getRank().getPriority();
		if (cardValue > 9) {
			return 0;
		} else {
			return cardValue;
		}
	}

	private boolean isDragon(int player, int banker) {
		if (banker > player && banker == 7) {
			drangonHands.add(((Integer) (result.size() + 1)).toString());
			return true;
		} else {
			return false;
		}
	}

	private boolean isPanda(int player, int banker) {
		if (player > banker && player == 8) {
			pandaHands.add(((Integer) (result.size() + 1)).toString());
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args) {
		//for (int i = 0; i < 10; i++) {
			BaccaratGame game = new BaccaratGame();
			int p = 0;
			int b = 0;
			int t = 0;
			game.setLastCardPosition(78);
			while (game.bacaShoe.getCardsLeftInShoe() >= game.getLastCardPosition()) {
				String tempResult = game.dealHand().getResult();
				game.result.add(tempResult);
				if (tempResult.equalsIgnoreCase("P")) {
					p++;
				} else if (tempResult.equalsIgnoreCase("B")) {
					b++;
				} else {
					t++;
				}
			}
			StringBuffer sb = new StringBuffer();
			sb.append("Total Hands are " + game.result.size()).append(System.lineSeparator());
			System.out.println(game.result);
			sb.append("Game stats: Player =" + p + " Banker =" + b + " Tie =" + t).append(System.lineSeparator());
			// numberOfJumps(game.result);
			sb.append("No.Of Natural = " + game.getNoOfNaturals()).append(System.lineSeparator());
			// System.out.println("No.Of Natural = "+game.getNoOfNaturals());
			// System.out.println("No.Of Pandas = "+game.getNoOfPandas()+" ---->
			// in Hands :"+game.pandaHands);
			sb.append("No.Of Pandas = " + game.getNoOfPandas() + " ----> in Hands :" + game.pandaHands)
					.append(System.lineSeparator());
			sb.append("No.Of Dragons = " + game.getNoOfDragons() + " ----> in Hands :" + game.drangonHands)
					.append(System.lineSeparator());
			// System.out.println("No.Of Dragons = "+game.getNoOfDragons()+"
			// ----> in Hands :"+game.drangonHands);
			BaccaratGame.recordResultsInExcel(game.result, sb.toString());
			System.out.println(sb);
		//}
	}

	public static void recordResultsInExcel(List<String> resultList, String CustomString) {
		IExcelWriter writer = new PoiExcelWriter();
		writer.createWorkBook();
		writer.createSheet("Results");
		String font = "Arial";
		int fontSize = 20;
		int rowIndex = 0;
		int colIndex = 0;
		int noOfJumps = 0;
		int noOfHands = resultList.size();
		int maxRow = 0;
		boolean isStartingWithTie = false;
		String previousResult = BLANK;
		if (resultList.get(0).equalsIgnoreCase(Outcome.tie.getResult())) {
			isStartingWithTie = true;
		}
		for (String currentResult : resultList) {
			if (previousResult.equalsIgnoreCase(BLANK)) {
				// do Nothing
			} else if (isStartingWithTie || currentResult.equalsIgnoreCase(Outcome.tie.getResult())
					|| previousResult.equalsIgnoreCase(currentResult)) {
				// Same result or tie next row
				rowIndex++;
			} else if (!previousResult.equalsIgnoreCase(currentResult)) {
				// different result switch column.
				colIndex++;
				if (maxRow < rowIndex) {
					maxRow = rowIndex;
				}
				rowIndex = 0;
			}
			if (currentResult.equalsIgnoreCase(Outcome.player.getResult())) {
				writer.createCell(rowIndex, colIndex);
				writer.setCellStyle(font, fontSize, IExcelWriter.BLUE);
				writer.setCellValue("O");
				isStartingWithTie = false;
			} else if (currentResult.equalsIgnoreCase(Outcome.banker.getResult())) {
				writer.createCell(rowIndex, colIndex);
				writer.setCellStyle(font, fontSize, IExcelWriter.RED);
				writer.setCellValue("O");
				isStartingWithTie = false;
			} else {
				writer.createCell(rowIndex, colIndex);
				writer.setCellStyle(font, fontSize, IExcelWriter.GREEN);
				writer.setCellValue("-");
			}
			if (isStartingWithTie || !currentResult.equalsIgnoreCase(Outcome.tie.getResult())) {
				// for plotting result on same col if not switch
				previousResult = currentResult;
			}
		}

		noOfJumps = colIndex + 1;

		writer.createCell(maxRow + 3, 0);
		StringBuffer sb = new StringBuffer(CustomString);
		sb.append(System.lineSeparator());
		sb.append("Number of Columns Traveresed = " + noOfJumps);
		sb.append(System.lineSeparator());
		sb.append("Average Jumps in " + (double) noOfHands / noOfJumps + " hands");
		writer.setCellValue(sb.toString());
		//write disparity in new sheet.
		writer.createSheet("Disparity");
		String header[] = {"P","B","P/B","O/R","O/T","SAP1", "SAP2","SAP3"};
		writer.setHeader(header);
		int pbDisparity = 0;
		int orDisparity = 0;
		int otDisparity = 0;
		int i=1;
		List<String> disparityResultList = new ArrayList<String>();
		for(String tResult:resultList){
			if(!tResult.equalsIgnoreCase(Outcome.tie.getResult())){
				disparityResultList.add(tResult);
			}
		}
		//Ignoring ties
		int listIndex =0;
		for(String result:disparityResultList){
			if(result.equalsIgnoreCase(Outcome.player.getResult())){
				writer.createCell(i, 0);
				writer.setCellStyle(font, fontSize, IExcelWriter.BLUE);
				writer.setCellValue("O");
				pbDisparity++;
			}else if(result.equalsIgnoreCase(Outcome.banker.getResult())){
				writer.createCell(i, 1);
				writer.setCellStyle(font, fontSize, IExcelWriter.RED);
				writer.setCellValue("O");
				pbDisparity--;
			}
			//Write pbdisparity
			writer.createCell(i,2);
			writer.setCellValue(new Integer(pbDisparity).toString());
			//Write ordisparity
			writer.createCell(i, 3);
			if(i > 1){
				int j = listIndex -1;
				if(result.equalsIgnoreCase(disparityResultList.get(j))){	
					orDisparity--;
				}else{
					orDisparity++;
				}
			}
			if(orDisparity < 0){
				writer.setCellStyle(font, 11, IExcelWriter.RED);
			}
			writer.setCellValue(new Integer(orDisparity).toString());
			//write otdisparity
			writer.createCell(i, 4);
			if(i > 2){
				int j = listIndex -2;
				if(result.equalsIgnoreCase(disparityResultList.get(j))){	
					otDisparity--;
				}else{
					otDisparity++;
				}
			}
			if(otDisparity < 0){
				writer.setCellStyle(font, 11, IExcelWriter.RED);
			}
			writer.setCellValue(new Integer(otDisparity).toString());
			i++;
			listIndex++;
		}
		writer.createExcel("Result_" + new Date().getTime() + ".xlsx");
		System.out.println("Excel Writen");
	}
	
	public int getLastCardPosition() {
		return lastCardPosition;
	}

	public static void numberOfJumps(List<String> results) {
		int jumps = 0;
		String lastResult = "";
		for (String r : results) {
			if (!lastResult.equalsIgnoreCase(r) && !r.equalsIgnoreCase("T")) {
				jumps++;
				lastResult = r;
			}
		}
		System.out.println("Number of Columns Traveresed = " + jumps);
		System.out.println("Average Jumps in " + (double) results.size() / jumps + " hands");
	}

}
