package application;

import java.net.URL;
import java.util.ResourceBundle;

import com.mak.casino.Baccarat.Outcome;

import application.mak.btc.BtcCountTableCellFactory;
import application.mak.btc.BtcPBTableCellFactory;
import application.mak.btc.BtcScoreCalc;
import application.mak.btc.BtcScoreCard;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

public class BtcTester implements Initializable {
	
	@FXML TableView<BtcScoreCard> btcScoreCard;
	
	@FXML TableColumn<BtcScoreCard, Integer> hands;
	
	@FXML TableColumn<BtcScoreCard, Double> score;
	
	@FXML TableColumn<BtcScoreCard, String> p;
	
	@FXML TableColumn<BtcScoreCard, String> b;
	
	@FXML TableColumn<BtcScoreCard, String> pbCount;
	
	@FXML TableColumn<BtcScoreCard, Integer> orCount;
	
	@FXML TableColumn<BtcScoreCard, Integer> otCount;
	
	@FXML TableColumn<BtcScoreCard, Integer> one;
	
	@FXML TableColumn<BtcScoreCard, Integer> two;
	
	@FXML TableColumn<BtcScoreCard, Integer> threePlus;
	
	
	@FXML ToggleGroup betg;
	
	@FXML RadioButton pb;
	
	@FXML RadioButton bb;
	
	@FXML RadioButton nb;
	
	BtcScoreCalc calc;

	public BtcTester() {
		calc = new BtcScoreCalc(true);
	}
	
	public void onClickPlayer(){
		boolean betOnPlayer = false;
		int bet = 0;
		if(betg.getSelectedToggle().getUserData().equals("pb")){
			betOnPlayer = true;
		}
		if(!betg.getSelectedToggle().getUserData().equals("nb")){
			bet = 1;
		}
		calc.recordOutcome(Outcome.player, bet, betOnPlayer);
		btcScoreCard.getItems().add(calc.getScoreCard());
		
	}
	
	public void onClickBanker(){
		boolean betOnPlayer = true;
		int bet = 0;
		if(betg.getSelectedToggle().getUserData().equals("bb")){
			betOnPlayer = false;
		}
		if(!betg.getSelectedToggle().getUserData().equals("nb")){
			bet = 1;
		}
		calc.recordOutcome(Outcome.banker, bet, betOnPlayer);
		btcScoreCard.getItems().add(calc.getScoreCard());
	}
	
	public void onClickTie(){
		/*boolean betOnPlayer = false;
		int bet = 0;
		if(betg.getSelectedToggle().getUserData().equals("pb")){
			betOnPlayer = true;
		}
		if(!betg.getSelectedToggle().getUserData().equals("nb")){
			bet = 1;
		}
		calc.recordOutcome(Outcome.tie, bet, betOnPlayer);
		btcScoreCard.getItems().add(calc.getScoreCard());*/
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		hands.setCellValueFactory(new PropertyValueFactory<BtcScoreCard, Integer>("noOfHands"));
		score.setCellValueFactory(new PropertyValueFactory<BtcScoreCard, Double>("score"));
		p.setCellValueFactory(new PropertyValueFactory<BtcScoreCard, String>("player"));
		p.setCellFactory(new BtcPBTableCellFactory());
		b.setCellValueFactory(new PropertyValueFactory<BtcScoreCard, String>("banker"));
		b.setCellFactory(new BtcPBTableCellFactory());
		pbCount.setCellValueFactory(new PropertyValueFactory<BtcScoreCard, String>("pb"));
		orCount.setCellValueFactory(new PropertyValueFactory<BtcScoreCard, Integer>("orCount"));
		orCount.setCellFactory(new BtcCountTableCellFactory());
		otCount.setCellValueFactory(new PropertyValueFactory<BtcScoreCard, Integer>("otCount"));
		otCount.setCellFactory(new BtcCountTableCellFactory());
		one.setCellValueFactory(new PropertyValueFactory<BtcScoreCard, Integer>("oneInARow"));
		two.setCellValueFactory(new PropertyValueFactory<BtcScoreCard, Integer>("twoInARow"));
		threePlus.setCellValueFactory(new PropertyValueFactory<BtcScoreCard, Integer>("threeOrMoreInARow"));
		pb.setUserData("pb");
		bb.setUserData("bb");
		nb.setUserData("nb");
	}
	
	

}
