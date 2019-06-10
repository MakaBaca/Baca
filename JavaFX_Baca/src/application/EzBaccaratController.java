package application;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.mak.casino.Baccarat.Outcome;
import com.mak.casino.EzBaccarat;

import application.mak.btc.BtcScoreCalc;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.layout.StackPane;

public class EzBaccaratController extends AbstractBacaController {
	
	@FXML StackPane pandaStack;
	
	@FXML StackPane dragonStack;
	
	@FXML Label pandaUnits;
	
	@FXML Label dragonUnits;
	
	@FXML Label noOfPanda;
	
	@FXML Label noOfDragon;
	
	int pandaBets = 0;
	
	int dragonBets = 0;
	
	EzBacaBigEyeRod6Xn ezRodComputer;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		calc = new BtcScoreCalc(true);
		ezRodComputer = new EzBacaBigEyeRod6Xn();
	}
	
	public void placePanda(InputEvent event){
		//System.out.println(event.getSource());
		String selectedUnit = (String) unit.getSelectedToggle().getUserData();
		if(selectedUnit != null){
			int iSelectedUnit = Integer.parseInt(selectedUnit);
			if(iSelectedUnit > gambi.getUnitBalance()){
				displayInsufficientBalance(pandaUnits);
				return;
			}else{
				placedBets = placedBets + iSelectedUnit;
				pandaBets = pandaBets + iSelectedUnit;
				pandaUnits.setText(Double.toString(pandaBets));
			}
			gambi.setUnitBalance(gambi.getUnitBalance() - iSelectedUnit);
			pandaStack.getChildren().add(getUnitCircle(selectedUnit));
			baccaratGame.placeBonusBets(EzBaccarat.Bonus.Panda.getResult(), iSelectedUnit);
			updateBalance();
		}
	}
	
	public void placeDragon(InputEvent event){
		//System.out.println(event.getSource());
		String selectedUnit = (String) unit.getSelectedToggle().getUserData();
		if(selectedUnit != null){
			int iSelectedUnit = Integer.parseInt(selectedUnit);
			if(iSelectedUnit > gambi.getUnitBalance()){
				displayInsufficientBalance(dragonUnits);
				return;
			}else{
				placedBets = placedBets + iSelectedUnit;
				dragonBets = dragonBets + iSelectedUnit;
				dragonUnits.setText(Double.toString(dragonBets));
			}
			gambi.setUnitBalance(gambi.getUnitBalance() - iSelectedUnit);
			dragonStack.getChildren().add(getUnitCircle(selectedUnit));
			baccaratGame.placeBonusBets(EzBaccarat.Bonus.Dragon.getResult(), iSelectedUnit);
			updateBalance();
		}
	}

	public EzBaccaratController() {
		baccaratGame = new EzBaccarat();
	}
	
	@Override
	public void prepareNewShoe() {
		calc = new BtcScoreCalc(true);
		ezRodComputer = new EzBacaBigEyeRod6Xn();
		super.prepareNewShoe();
		
	}
	
	@Override
	public void clearBets() {
		if(pandaStack.getChildren().size() >0){
			pandaStack.getChildren().clear();
			pandaUnits.setText(BLANK);
			pandaBets = 0;
		}
		if(dragonStack.getChildren().size() >0){
			dragonStack.getChildren().clear();
			dragonUnits.setText(BLANK);
			dragonBets = 0;
		}
		super.clearBets();
	}
	
	@Override
	protected void clearTheTabel() {
		if(dragonStack.getChildren().size() >0){
			dragonStack.getChildren().clear();
		}
		if(pandaStack.getChildren().size() >0){
			pandaStack.getChildren().clear();
		}
		dragonUnits.setText(BLANK);
		pandaUnits.setText(BLANK);
		dragonBets = 0;
		pandaBets = 0;
		super.clearTheTabel();
	}
	
	@Override
	protected void updateBigRod(Outcome o) {
		BigEyeRodCell cellValue = null;
		boolean isDragon = false;
		List<String> bonus = baccaratGame.bonusOutcome();
		if(bonus.size() > 0){
			for(String b: bonus){
				if(b.equals(EzBaccarat.Bonus.Panda.getResult())){
					cellValue = ezRodComputer.updatePanda();
					noOfPanda.setText(Integer.toString(ezRodComputer.getNoOfPanda()));
				}else if(b.equals(EzBaccarat.Bonus.Dragon.getResult())){
					cellValue = ezRodComputer.updateDragon();
					noOfDragon.setText(Integer.toString(ezRodComputer.getNoOfDragon()));
					isDragon = true;
				}
			}
		}else {
			cellValue = ezRodComputer.updateHand(o);
		}
		bigRod.add(new ImageView(new Image(cellValue.getImageURL(),18,18,false,false)), cellValue.getColIndex(), cellValue.getRowIndex());
		noOfHands.setText(Integer.toString(ezRodComputer.getNoOfHands()));
		if(o == Outcome.player){
			noOfPlayers.setText(Integer.toString(ezRodComputer.getNoOfPlayers()));
		}else if(o == Outcome.banker){
			noOfBankers.setText(Integer.toString(ezRodComputer.getNoOfBankers()));
		}else if(o == Outcome.tie){
			noOfTies.setText(Integer.toString(ezRodComputer.getNoOfTies()));
		}
		updateBalance();
		updateHistory();
		if(isDragon && bankerBets > 0){
			//banker bets pushes in dragon so setting banker bet as 0 in btc score card
			calc.recordOutcome(o, 0, false);
			btcScoreCard.getItems().add(calc.getScoreCard());
		}else{
			updateBtcScoreCard(o);
		}
		clearTheTabel();
	}
	
}
