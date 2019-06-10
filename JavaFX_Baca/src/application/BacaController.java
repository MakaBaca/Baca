package application;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.mak.baca.card.BaccaratCards;
import com.mak.casino.Baccarat;
import com.mak.casino.Baccarat.Outcome;

import application.mak.btc.BtcCountTableCellFactory;
import application.mak.btc.BtcPBTableCellFactory;
import application.mak.btc.BtcScoreCalc;
import application.mak.btc.BtcScoreCard;

import com.mak.casino.Player;
import com.mak.casino.VegasBaccarat;

import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class BacaController implements Initializable{
	
	private static final String BLANK = "";

	private static final String IMAGES_DOLLAR100_JPG = "images/dollar100.jpg";

	private static final String IMAGES_DOLLAR25_JPG = "images/dollar25.jpg";

	private static final String IMAGES_DOLLAR5_JPG = "images/dollar5.jpg";
	
	private static final String IMAGES_FOLDER = "images/";
	
	private static final String CARD_IMAGE_EXTENSION = ".png";

	private static final String ONE_HUNDRED = "100";

	private static final String TWENTY_FIVE = "25";

	private static final String FIVE = "5";
	
	private static final String PLAYER_HAS = "Player has ";
	
	private static final String BANKER_HAS = "Banker has ";
	
	private static final String PLAYER_WINS = "Player Wins ";
	
	private static final String BANKER_WINS = "Banker Wins ";
	
	private static final String OVER = " over ";
	
	private static final String TIE = "It's a Tie";
	
	private double placedBets;
	
	private int playerBets = 0;
	
	private int bankerBets = 0;
	
	private int tieBets = 0;
	
	private int eitherPairBets = 0;
	
	private int playerPairBets = 0;
	
	private int bankerPairBets = 0;
	
	private int playerDragonBets = 0;
	
	private int bankerDragonBets = 0;
	

	@FXML Circle dollar5;
	
	@FXML Circle dollar25;
	
	@FXML Circle dollar100;
	
	@FXML ToggleGroup unit;
	
	@FXML RadioButton five;
	
	@FXML RadioButton twentyFive;
	
	@FXML RadioButton hundred;
	
	@FXML Rectangle playerBet;
	
	@FXML StackPane playerStack;
	
	@FXML StackPane bankerStack;
	
	@FXML StackPane tieStack;
	
	@FXML StackPane playerDragon;
	
	@FXML StackPane bankerDragon;
	
	@FXML StackPane playerPair;
	
	@FXML StackPane eitherPair;
	
	@FXML StackPane bankerPair;
	
	@FXML Label pUnits;
	
	@FXML Label tUnits;
	
	@FXML Label bUnits;
	
	@FXML Label ppUnits;
	
	@FXML Label bpUnits;
	
	@FXML Label epUnits;
	
	@FXML Label pdUnits;
	
	@FXML Label bdUnits;
	
	@FXML ImageView playerFirstCard;
	
	@FXML ImageView playerSecondCard;
	
	@FXML ImageView playerThirdCard;
	
	@FXML ImageView bankerThirdCard;
	
	@FXML ImageView bankerSecondCard;
	
	@FXML ImageView bankerFirstCard;
	
	@FXML Text result;
	
	@FXML Label unitBalance;
	
	@FXML GridPane bigRod;
	
	@FXML Button deal;
	
	@FXML Button clearBets;
	
	@FXML AnchorPane gamePane;
	
	@FXML Label noOfHands;
	
	@FXML Label noOfPlayers;
	
	@FXML Label noOfBankers;
	
	@FXML Label noOfTies;
	
	@FXML TableView<BetHistory> betHistory;
	
	@FXML TableColumn<BetHistory, Double> hBets;
	
	@FXML TableColumn<BetHistory, Double> hWon;
	
	@FXML TableColumn<BetHistory, Double> hBalance;
	
	@FXML TableView<BtcScoreCard> btcScoreCard;
	
	@FXML TableColumn<BtcScoreCard, Integer> hands;
	
	@FXML TableColumn<BtcScoreCard, Double> score;
	
	@FXML TableColumn<BtcScoreCard, String> p;
	
	@FXML TableColumn<BtcScoreCard, String> b;
	
	@FXML TableColumn<BtcScoreCard, String> pbCount;
	
	@FXML TableColumn<BtcScoreCard, Integer> orCount;
	
	@FXML TableColumn<BtcScoreCard, Integer> otCount;
	
	Baccarat vegasBaca;
	
	Player gambi;
	
	BigEyeRod6Xn rodComputer;
	
	BtcScoreCalc calc;
	
	double winingUnits = 0;
	
	public BacaController() {
		vegasBaca = new VegasBaccarat();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dollar5.setFill(new ImagePattern(new Image(IMAGES_DOLLAR5_JPG)));
		dollar25.setFill(new ImagePattern(new Image(IMAGES_DOLLAR25_JPG)));
		dollar100.setFill(new ImagePattern(new Image(IMAGES_DOLLAR100_JPG)));
		five.setUserData(FIVE);
		twentyFive.setUserData(TWENTY_FIVE);
		hundred.setUserData(ONE_HUNDRED);
		vegasBaca.prepareShoe();
		vegasBaca.setLastCardPosition(11);
		result.setText("New Shoe Ready for you !!!!! Burn Card is: "+vegasBaca.getBurnCard());
		gambi = new Player("Makesh",500);
		unitBalance.setText("500");
		rodComputer = new BigEyeRod6Xn();
		calc = new BtcScoreCalc(false);
		hBets.setCellValueFactory(new PropertyValueFactory<BetHistory, Double>("placedBets"));
		hWon.setCellValueFactory(new PropertyValueFactory<BetHistory, Double>("wonBets"));
		hBalance.setCellValueFactory(new PropertyValueFactory<BetHistory, Double>("balance"));
		hands.setCellValueFactory(new PropertyValueFactory<BtcScoreCard, Integer>("noOfHands"));
		score.setCellValueFactory(new PropertyValueFactory<BtcScoreCard, Double>("score"));
		p.setCellValueFactory(new PropertyValueFactory<BtcScoreCard, String>("player"));
		//p.setCellFactory(new BtcPBTableCellFactory());
		b.setCellValueFactory(new PropertyValueFactory<BtcScoreCard, String>("banker"));
		//b.setCellFactory(new BtcPBTableCellFactory());
		pbCount.setCellValueFactory(new PropertyValueFactory<BtcScoreCard, String>("pb"));
		orCount.setCellValueFactory(new PropertyValueFactory<BtcScoreCard, Integer>("orCount"));
		orCount.setCellFactory(new BtcCountTableCellFactory());
		otCount.setCellValueFactory(new PropertyValueFactory<BtcScoreCard, Integer>("otCount"));
		otCount.setCellFactory(new BtcCountTableCellFactory());
		}
	
	public void selectedUnit(){
		System.out.println(unit.getSelectedToggle());
		
	}
	
	public void deal(){
		winingUnits = 0;
		if(!vegasBaca.hasNextHand()){
			//Shoe is Over inform user and ask their confirmation 
			Alert confirmation = new Alert(AlertType.CONFIRMATION);
			confirmation.setTitle("End OF Shoe");
			confirmation.setHeaderText("That was the last Hand of this shoe!!!!!!");
			confirmation.setContentText("To Continue playing in new shoe press Ok else cancel");
			// option != null.
		    Optional<ButtonType> option = confirmation.showAndWait();
            if (option.get() == null) {
            	//This can't happen
            }else if(option.get() == ButtonType.OK){
            	prepareNewShoe();
            }

			return;
		}
		Outcome handOutcome = vegasBaca.dealOneHand();
		/*List<BaccaratCards> playerCards = vegasBaca.getPlayerCards();
		List<BaccaratCards> bankerCards = vegasBaca.getBankerCards();
		
		System.out.println("Setting player cards -->"+playerCards+" Final value--->"+vegasBaca.getPlayerFinalValue());
		System.out.println("Setting banker cards -->"+bankerCards+" Final value--->"+vegasBaca.getBankerFinalValue());
		System.out.println("Setting outcome -->"+handOutcome.toString());*/
		winingUnits = vegasBaca.payout();
		gambi.setUnitBalance(gambi.getUnitBalance()+winingUnits);
		if(handOutcome == Outcome.player){
			animatePlayerWin();
		}else if(handOutcome == Outcome.banker){
			animateBankerWin();
		}else{
			animateTie();
		}
	}

	public void prepareNewShoe() {
		vegasBaca.prepareShoe();
		vegasBaca.setLastCardPosition(11);
		rodComputer = new BigEyeRod6Xn();
		calc = new BtcScoreCalc(false);
		ObservableList<Node> list = bigRod.getChildren();
		ImageView iv = null;
		for(Node n:list){
			if(n instanceof ImageView){
				iv = (ImageView) n;
				iv.setImage(null);
			}
		}
		result.setText("New Shoe Ready for you !!!!! Burn Card is: "+vegasBaca.getBurnCard());
		noOfHands.setText("0");
		noOfPlayers.setText("0");
		noOfBankers.setText("0");
		noOfTies.setText("0");
		betHistory.getItems().clear();
		btcScoreCard.getItems().clear();
	}
	
	private void updateBalance() {
		unitBalance.setText(Double.toString(gambi.getUnitBalance()));
	}

	public void animatePlayerWin(){
		List<BaccaratCards> playerCards = vegasBaca.getPlayerCards();
		List<BaccaratCards> bankerCards = vegasBaca.getBankerCards();
		int noOfPlayerCards = playerCards.size();
		int noOfBankerCards = bankerCards.size();
		int playerTwoCardValue = vegasBaca.getPlayerTwoCardsValue();
		int playerFinalValue = vegasBaca.getPlayerFinalValue();
		int bankerTwoCardValue = vegasBaca.getBankerTwoCardsValue();
		int bankerFinalValue = vegasBaca.getBankerFinalValue();
		Timeline playerWinsTimeLine = null;
		/*Timeline disableBetsTimeLine = new Timeline(
				new KeyFrame(Duration.INDEFINITE, new KeyValue(deal.disableProperty(), true)),
				new KeyFrame(Duration.INDEFINITE, new KeyValue(clearBets.disableProperty(), true))
				);
		disableBetsTimeLine.play();*/
		System.out.println(IMAGES_FOLDER+playerCards.get(0).toString()+CARD_IMAGE_EXTENSION);
		//Natural or 2 card on both sides only
		if(noOfPlayerCards == 2 && noOfBankerCards == 2){
			playerWinsTimeLine = new Timeline(
					new KeyFrame(Duration.seconds(0.01), new KeyValue(gamePane.disableProperty(), true)),
					new KeyFrame(Duration.seconds(1), new KeyValue(playerFirstCard.imageProperty(), new Image(IMAGES_FOLDER+playerCards.get(0).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(1), new KeyValue(playerSecondCard.imageProperty(), new Image(IMAGES_FOLDER+playerCards.get(1).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(1), new KeyValue(result.textProperty(), PLAYER_HAS+playerFinalValue)),
					new KeyFrame(Duration.seconds(2), new KeyValue(bankerFirstCard.imageProperty(), new Image(IMAGES_FOLDER+bankerCards.get(0).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(2), new KeyValue(bankerSecondCard.imageProperty(), new Image(IMAGES_FOLDER+bankerCards.get(1).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(2), new KeyValue(result.textProperty(), BANKER_HAS+bankerFinalValue)),
					new KeyFrame(Duration.seconds(3), new KeyValue(result.textProperty(), PLAYER_WINS+playerFinalValue+OVER+bankerFinalValue))
					);
		} 
		//Only banker hits
		else if(noOfPlayerCards == 2 && noOfBankerCards == 3){
			playerWinsTimeLine = new Timeline(
					new KeyFrame(Duration.seconds(0.01), new KeyValue(gamePane.disableProperty(), true)),
					new KeyFrame(Duration.seconds(1), new KeyValue(playerFirstCard.imageProperty(), new Image(IMAGES_FOLDER+playerCards.get(0).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(1), new KeyValue(playerSecondCard.imageProperty(), new Image(IMAGES_FOLDER+playerCards.get(1).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(1), new KeyValue(result.textProperty(), PLAYER_HAS+playerFinalValue)),
					new KeyFrame(Duration.seconds(2), new KeyValue(bankerFirstCard.imageProperty(), new Image(IMAGES_FOLDER+bankerCards.get(0).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(2), new KeyValue(bankerSecondCard.imageProperty(), new Image(IMAGES_FOLDER+bankerCards.get(1).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(2), new KeyValue(result.textProperty(), BANKER_HAS+bankerTwoCardValue)),
					new KeyFrame(Duration.seconds(3), new KeyValue(bankerThirdCard.imageProperty(), new Image(IMAGES_FOLDER+bankerCards.get(2).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(3), new KeyValue(result.textProperty(), BANKER_HAS+bankerFinalValue)),
					new KeyFrame(Duration.seconds(4), new KeyValue(result.textProperty(), PLAYER_WINS+playerFinalValue+OVER+bankerFinalValue))
					);
		}
		//Only Player hits
		else if(noOfPlayerCards == 3 && noOfBankerCards == 2){
			playerWinsTimeLine = new Timeline(
					new KeyFrame(Duration.seconds(0.01), new KeyValue(gamePane.disableProperty(), true)),
					new KeyFrame(Duration.seconds(1), new KeyValue(playerFirstCard.imageProperty(), new Image(IMAGES_FOLDER+playerCards.get(0).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(1), new KeyValue(playerSecondCard.imageProperty(), new Image(IMAGES_FOLDER+playerCards.get(1).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(1), new KeyValue(result.textProperty(), PLAYER_HAS+playerTwoCardValue)),
					new KeyFrame(Duration.seconds(2), new KeyValue(bankerFirstCard.imageProperty(), new Image(IMAGES_FOLDER+bankerCards.get(0).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(2), new KeyValue(bankerSecondCard.imageProperty(), new Image(IMAGES_FOLDER+bankerCards.get(1).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(2), new KeyValue(result.textProperty(), BANKER_HAS+bankerFinalValue)),
					new KeyFrame(Duration.seconds(3), new KeyValue(playerThirdCard.imageProperty(), new Image(IMAGES_FOLDER+playerCards.get(2).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(3), new KeyValue(result.textProperty(), PLAYER_HAS+playerFinalValue)),
					new KeyFrame(Duration.seconds(4), new KeyValue(result.textProperty(), PLAYER_WINS+playerFinalValue+OVER+bankerFinalValue))
					);
		}
		//Both Hits
		else if(noOfPlayerCards == 3 && noOfBankerCards == 3){
			playerWinsTimeLine = new Timeline(
					new KeyFrame(Duration.seconds(0.01), new KeyValue(gamePane.disableProperty(), true)),
					new KeyFrame(Duration.seconds(1), new KeyValue(playerFirstCard.imageProperty(), new Image(IMAGES_FOLDER+playerCards.get(0).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(1), new KeyValue(playerSecondCard.imageProperty(), new Image(IMAGES_FOLDER+playerCards.get(1).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(1), new KeyValue(result.textProperty(), PLAYER_HAS+playerTwoCardValue)),
					new KeyFrame(Duration.seconds(2), new KeyValue(bankerFirstCard.imageProperty(), new Image(IMAGES_FOLDER+bankerCards.get(0).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(2), new KeyValue(bankerSecondCard.imageProperty(), new Image(IMAGES_FOLDER+bankerCards.get(1).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(2), new KeyValue(result.textProperty(), BANKER_HAS+bankerTwoCardValue)),
					new KeyFrame(Duration.seconds(3), new KeyValue(playerThirdCard.imageProperty(), new Image(IMAGES_FOLDER+playerCards.get(2).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(3), new KeyValue(result.textProperty(), PLAYER_HAS+playerFinalValue)),
					new KeyFrame(Duration.seconds(4), new KeyValue(bankerThirdCard.imageProperty(), new Image(IMAGES_FOLDER+bankerCards.get(2).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(4), new KeyValue(result.textProperty(), BANKER_HAS+bankerFinalValue)),
					new KeyFrame(Duration.seconds(5), new KeyValue(result.textProperty(), PLAYER_WINS+playerFinalValue+OVER+bankerFinalValue))
					);
		}
		playerWinsTimeLine.play();
		playerWinsTimeLine.statusProperty().addListener(new ChangeListener<Status>(){

			@Override
			public void changed(ObservableValue<? extends Status> observable, Status oldValue, Status newValue) {
				if(newValue == Status.STOPPED){
					updateBigRod(Outcome.player);
				}
			}
		});
	}
	
	private void updateBigRod(Outcome o){
		BigEyeRodCell cellValue = rodComputer.updateHand(o);
		bigRod.add(new ImageView(new Image(cellValue.getImageURL(),18,18,false,false)), cellValue.getColIndex(), cellValue.getRowIndex());
		noOfHands.setText(Integer.toString(rodComputer.getNoOfHands()));
		if(o == Outcome.player){
			noOfPlayers.setText(Integer.toString(rodComputer.getNoOfPlayers()));
		}else if(o == Outcome.banker){
			noOfBankers.setText(Integer.toString(rodComputer.getNoOfBankers()));
		}else if(o == Outcome.tie){
			noOfTies.setText(Integer.toString(rodComputer.getNoOfTies()));
		}
		updateBalance();
		updateHistory();
		updateBtcScoreCard(o);
		clearTheTabel();
	}
	
	private void updateBtcScoreCard(Outcome handOutcome) {
		if(handOutcome == Outcome.tie)
			return;
		if(playerBets > 0){
			calc.recordOutcome(handOutcome, playerBets, true);
		}else {
			calc.recordOutcome(handOutcome, bankerBets, false);
		}
		btcScoreCard.getItems().add(calc.getScoreCard());
	}

	private void updateHistory(){
		betHistory.getItems().add(new BetHistory(placedBets, winingUnits,gambi.getUnitBalance()));
	}
	
	public void animateBankerWin(){
		List<BaccaratCards> playerCards = vegasBaca.getPlayerCards();
		List<BaccaratCards> bankerCards = vegasBaca.getBankerCards();
		int noOfPlayerCards = playerCards.size();
		int noOfBankerCards = bankerCards.size();
		int playerTwoCardValue = vegasBaca.getPlayerTwoCardsValue();
		int playerFinalValue = vegasBaca.getPlayerFinalValue();
		int bankerTwoCardValue = vegasBaca.getBankerTwoCardsValue();
		int bankerFinalValue = vegasBaca.getBankerFinalValue();
		Timeline bankerWinTimeLine = null;
		/*Timeline disableBetsTimeLine = new Timeline(
				new KeyFrame(Duration.INDEFINITE, new KeyValue(deal.disableProperty(), true)),
				new KeyFrame(Duration.INDEFINITE, new KeyValue(clearBets.disableProperty(), true))
				);
		disableBetsTimeLine.play();*/
		System.out.println(IMAGES_FOLDER+playerCards.get(0).toString()+CARD_IMAGE_EXTENSION);
		//Natural or 2 card on both sides only
		if(noOfPlayerCards == 2 && noOfBankerCards == 2){
			bankerWinTimeLine = new Timeline(
					new KeyFrame(Duration.seconds(0.01), new KeyValue(gamePane.disableProperty(), true)),
					new KeyFrame(Duration.seconds(1), new KeyValue(playerFirstCard.imageProperty(), new Image(IMAGES_FOLDER+playerCards.get(0).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(1), new KeyValue(playerSecondCard.imageProperty(), new Image(IMAGES_FOLDER+playerCards.get(1).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(1), new KeyValue(result.textProperty(), PLAYER_HAS+playerFinalValue)),
					new KeyFrame(Duration.seconds(2), new KeyValue(bankerFirstCard.imageProperty(), new Image(IMAGES_FOLDER+bankerCards.get(0).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(2), new KeyValue(bankerSecondCard.imageProperty(), new Image(IMAGES_FOLDER+bankerCards.get(1).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(2), new KeyValue(result.textProperty(), BANKER_HAS+bankerFinalValue)),
					new KeyFrame(Duration.seconds(3), new KeyValue(result.textProperty(), BANKER_WINS+bankerFinalValue+OVER+playerFinalValue))
					);
		} 
		//Only banker hits
		else if(noOfPlayerCards == 2 && noOfBankerCards == 3){
			bankerWinTimeLine = new Timeline(
					new KeyFrame(Duration.seconds(0.01), new KeyValue(gamePane.disableProperty(), true)),
					new KeyFrame(Duration.seconds(1), new KeyValue(playerFirstCard.imageProperty(), new Image(IMAGES_FOLDER+playerCards.get(0).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(1), new KeyValue(playerSecondCard.imageProperty(), new Image(IMAGES_FOLDER+playerCards.get(1).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(1), new KeyValue(result.textProperty(), PLAYER_HAS+playerFinalValue)),
					new KeyFrame(Duration.seconds(2), new KeyValue(bankerFirstCard.imageProperty(), new Image(IMAGES_FOLDER+bankerCards.get(0).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(2), new KeyValue(bankerSecondCard.imageProperty(), new Image(IMAGES_FOLDER+bankerCards.get(1).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(2), new KeyValue(result.textProperty(), BANKER_HAS+bankerTwoCardValue)),
					new KeyFrame(Duration.seconds(3), new KeyValue(bankerThirdCard.imageProperty(), new Image(IMAGES_FOLDER+bankerCards.get(2).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(3), new KeyValue(result.textProperty(), BANKER_HAS+bankerFinalValue)),
					new KeyFrame(Duration.seconds(4), new KeyValue(result.textProperty(), BANKER_WINS+bankerFinalValue+OVER+playerFinalValue))
					);
		}
		//Only Player hits
		else if(noOfPlayerCards == 3 && noOfBankerCards == 2){
			bankerWinTimeLine = new Timeline(
					new KeyFrame(Duration.seconds(0.01), new KeyValue(gamePane.disableProperty(), true)),
					new KeyFrame(Duration.seconds(1), new KeyValue(playerFirstCard.imageProperty(), new Image(IMAGES_FOLDER+playerCards.get(0).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(1), new KeyValue(playerSecondCard.imageProperty(), new Image(IMAGES_FOLDER+playerCards.get(1).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(1), new KeyValue(result.textProperty(), PLAYER_HAS+playerTwoCardValue)),
					new KeyFrame(Duration.seconds(2), new KeyValue(bankerFirstCard.imageProperty(), new Image(IMAGES_FOLDER+bankerCards.get(0).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(2), new KeyValue(bankerSecondCard.imageProperty(), new Image(IMAGES_FOLDER+bankerCards.get(1).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(2), new KeyValue(result.textProperty(), BANKER_HAS+bankerFinalValue)),
					new KeyFrame(Duration.seconds(3), new KeyValue(playerThirdCard.imageProperty(), new Image(IMAGES_FOLDER+playerCards.get(2).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(3), new KeyValue(result.textProperty(), PLAYER_HAS+playerFinalValue)),
					new KeyFrame(Duration.seconds(4), new KeyValue(result.textProperty(), BANKER_WINS+bankerFinalValue+OVER+playerFinalValue))
					);
		}
		//Both Hits
		else if(noOfPlayerCards == 3 && noOfBankerCards == 3){
			bankerWinTimeLine = new Timeline(
					new KeyFrame(Duration.seconds(0.01), new KeyValue(gamePane.disableProperty(), true)),
					new KeyFrame(Duration.seconds(1), new KeyValue(playerFirstCard.imageProperty(), new Image(IMAGES_FOLDER+playerCards.get(0).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(1), new KeyValue(playerSecondCard.imageProperty(), new Image(IMAGES_FOLDER+playerCards.get(1).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(1), new KeyValue(result.textProperty(), PLAYER_HAS+playerTwoCardValue)),
					new KeyFrame(Duration.seconds(2), new KeyValue(bankerFirstCard.imageProperty(), new Image(IMAGES_FOLDER+bankerCards.get(0).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(2), new KeyValue(bankerSecondCard.imageProperty(), new Image(IMAGES_FOLDER+bankerCards.get(1).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(2), new KeyValue(result.textProperty(), BANKER_HAS+bankerTwoCardValue)),
					new KeyFrame(Duration.seconds(3), new KeyValue(playerThirdCard.imageProperty(), new Image(IMAGES_FOLDER+playerCards.get(2).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(3), new KeyValue(result.textProperty(), PLAYER_HAS+playerFinalValue)),
					new KeyFrame(Duration.seconds(4), new KeyValue(bankerThirdCard.imageProperty(), new Image(IMAGES_FOLDER+bankerCards.get(2).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(4), new KeyValue(result.textProperty(), BANKER_HAS+bankerFinalValue)),
					new KeyFrame(Duration.seconds(5), new KeyValue(result.textProperty(), BANKER_WINS+bankerFinalValue+OVER+playerFinalValue))
					);
		}
		bankerWinTimeLine.play();
		bankerWinTimeLine.statusProperty().addListener(new ChangeListener<Status>(){

			@Override
			public void changed(ObservableValue<? extends Status> observable, Status oldValue, Status newValue) {
				if(newValue == Status.STOPPED){
					updateBigRod(Outcome.banker);
				}
			}
		});
	}
	
	public void animateTie(){
		List<BaccaratCards> playerCards = vegasBaca.getPlayerCards();
		List<BaccaratCards> bankerCards = vegasBaca.getBankerCards();
		int noOfPlayerCards = playerCards.size();
		int noOfBankerCards = bankerCards.size();
		int playerTwoCardValue = vegasBaca.getPlayerTwoCardsValue();
		int playerFinalValue = vegasBaca.getPlayerFinalValue();
		int bankerTwoCardValue = vegasBaca.getBankerTwoCardsValue();
		int bankerFinalValue = vegasBaca.getBankerFinalValue();
		Timeline tieTimeline = null;
		/*Timeline disableBetsTimeLine = new Timeline(
				new KeyFrame(Duration.INDEFINITE, new KeyValue(deal.disableProperty(), true)),
				new KeyFrame(Duration.INDEFINITE, new KeyValue(clearBets.disableProperty(), true))
				);
		disableBetsTimeLine.play();*/
		System.out.println(IMAGES_FOLDER+playerCards.get(0).toString()+CARD_IMAGE_EXTENSION);
		//Natural or 2 card on both sides only
		if(noOfPlayerCards == 2 && noOfBankerCards == 2){
			tieTimeline = new Timeline(
					new KeyFrame(Duration.seconds(0.01), new KeyValue(gamePane.disableProperty(), true)),
					new KeyFrame(Duration.seconds(1), new KeyValue(playerFirstCard.imageProperty(), new Image(IMAGES_FOLDER+playerCards.get(0).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(1), new KeyValue(playerSecondCard.imageProperty(), new Image(IMAGES_FOLDER+playerCards.get(1).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(1), new KeyValue(result.textProperty(), PLAYER_HAS+playerFinalValue)),
					new KeyFrame(Duration.seconds(2), new KeyValue(bankerFirstCard.imageProperty(), new Image(IMAGES_FOLDER+bankerCards.get(0).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(2), new KeyValue(bankerSecondCard.imageProperty(), new Image(IMAGES_FOLDER+bankerCards.get(1).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(2), new KeyValue(result.textProperty(), BANKER_HAS+bankerFinalValue)),
					new KeyFrame(Duration.seconds(3), new KeyValue(result.textProperty(), TIE))
					);
		} 
		//Only banker hits
		else if(noOfPlayerCards == 2 && noOfBankerCards == 3){
			tieTimeline = new Timeline(
					new KeyFrame(Duration.seconds(0.01), new KeyValue(gamePane.disableProperty(), true)),
					new KeyFrame(Duration.seconds(1), new KeyValue(playerFirstCard.imageProperty(), new Image(IMAGES_FOLDER+playerCards.get(0).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(1), new KeyValue(playerSecondCard.imageProperty(), new Image(IMAGES_FOLDER+playerCards.get(1).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(1), new KeyValue(result.textProperty(), PLAYER_HAS+playerFinalValue)),
					new KeyFrame(Duration.seconds(2), new KeyValue(bankerFirstCard.imageProperty(), new Image(IMAGES_FOLDER+bankerCards.get(0).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(2), new KeyValue(bankerSecondCard.imageProperty(), new Image(IMAGES_FOLDER+bankerCards.get(1).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(2), new KeyValue(result.textProperty(), BANKER_HAS+bankerTwoCardValue)),
					new KeyFrame(Duration.seconds(3), new KeyValue(bankerThirdCard.imageProperty(), new Image(IMAGES_FOLDER+bankerCards.get(2).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(3), new KeyValue(result.textProperty(), BANKER_HAS+bankerFinalValue)),
					new KeyFrame(Duration.seconds(4), new KeyValue(result.textProperty(), TIE))
					);
		}
		//Only Player hits
		else if(noOfPlayerCards == 3 && noOfBankerCards == 2){
			tieTimeline = new Timeline(
					new KeyFrame(Duration.seconds(0.01), new KeyValue(gamePane.disableProperty(), true)),
					new KeyFrame(Duration.seconds(1), new KeyValue(playerFirstCard.imageProperty(), new Image(IMAGES_FOLDER+playerCards.get(0).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(1), new KeyValue(playerSecondCard.imageProperty(), new Image(IMAGES_FOLDER+playerCards.get(1).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(1), new KeyValue(result.textProperty(), PLAYER_HAS+playerTwoCardValue)),
					new KeyFrame(Duration.seconds(2), new KeyValue(bankerFirstCard.imageProperty(), new Image(IMAGES_FOLDER+bankerCards.get(0).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(2), new KeyValue(bankerSecondCard.imageProperty(), new Image(IMAGES_FOLDER+bankerCards.get(1).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(2), new KeyValue(result.textProperty(), BANKER_HAS+bankerFinalValue)),
					new KeyFrame(Duration.seconds(3), new KeyValue(playerThirdCard.imageProperty(), new Image(IMAGES_FOLDER+playerCards.get(2).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(3), new KeyValue(result.textProperty(), PLAYER_HAS+playerFinalValue)),
					new KeyFrame(Duration.seconds(4), new KeyValue(result.textProperty(), TIE))
					);
		}
		//Both Hits
		else if(noOfPlayerCards == 3 && noOfBankerCards == 3){
			tieTimeline = new Timeline(
					new KeyFrame(Duration.seconds(0.01), new KeyValue(gamePane.disableProperty(), true)),
					new KeyFrame(Duration.seconds(1), new KeyValue(playerFirstCard.imageProperty(), new Image(IMAGES_FOLDER+playerCards.get(0).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(1), new KeyValue(playerSecondCard.imageProperty(), new Image(IMAGES_FOLDER+playerCards.get(1).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(1), new KeyValue(result.textProperty(), PLAYER_HAS+playerTwoCardValue)),
					new KeyFrame(Duration.seconds(2), new KeyValue(bankerFirstCard.imageProperty(), new Image(IMAGES_FOLDER+bankerCards.get(0).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(2), new KeyValue(bankerSecondCard.imageProperty(), new Image(IMAGES_FOLDER+bankerCards.get(1).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(2), new KeyValue(result.textProperty(), BANKER_HAS+bankerTwoCardValue)),
					new KeyFrame(Duration.seconds(3), new KeyValue(playerThirdCard.imageProperty(), new Image(IMAGES_FOLDER+playerCards.get(2).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(3), new KeyValue(result.textProperty(), PLAYER_HAS+playerFinalValue)),
					new KeyFrame(Duration.seconds(4), new KeyValue(bankerThirdCard.imageProperty(), new Image(IMAGES_FOLDER+bankerCards.get(2).toString()+CARD_IMAGE_EXTENSION))),
					new KeyFrame(Duration.seconds(4), new KeyValue(result.textProperty(), BANKER_HAS+bankerFinalValue)),
					new KeyFrame(Duration.seconds(5), new KeyValue(result.textProperty(), TIE))
					);
		}
		tieTimeline.play();
		tieTimeline.statusProperty().addListener(new ChangeListener<Status>(){

			@Override
			public void changed(ObservableValue<? extends Status> observable, Status oldValue, Status newValue) {
				if(newValue == Status.STOPPED){
					updateBigRod(Outcome.tie);
				}
			}
		});
	}
	
	private void clearTheTabel() {
		if(playerStack.getChildren().size() >0){
			playerStack.getChildren().clear();
		}
		if(bankerStack.getChildren().size() >0){
			bankerStack.getChildren().clear();
		}
		if(tieStack.getChildren().size() >0){
			tieStack.getChildren().clear();
		}
		if(playerPair.getChildren().size() >0){
			playerPair.getChildren().clear();
		}
		if(bankerPair.getChildren().size() >0){
			bankerPair.getChildren().clear();
		}
		if(eitherPair.getChildren().size() >0){
			eitherPair.getChildren().clear();
		}
		if(playerDragon.getChildren().size() >0){
			playerDragon.getChildren().clear();
		}
		if(bankerDragon.getChildren().size() >0){
			bankerDragon.getChildren().clear();
		}
		//removing cards
		playerFirstCard.setImage(null);
		playerSecondCard.setImage(null);
		playerThirdCard.setImage(null);
		bankerFirstCard.setImage(null);
		bankerSecondCard.setImage(null);
		bankerThirdCard.setImage(null);
		//result.setText(null);
		vegasBaca.clearAllBets();
		//deal.setDisable(false);
		//clearBets.setDisable(false);
		gamePane.setDisable(false);
		pUnits.setText(BLANK);
		playerBets = 0;
		bUnits.setText(BLANK);
		bankerBets = 0;
		tUnits.setText(BLANK);
		tieBets = 0;
		ppUnits.setText(BLANK);
		playerPairBets = 0;
		bpUnits.setText(BLANK);
		bankerPairBets = 0;
		epUnits.setText(BLANK);
		eitherPairBets = 0;
		pdUnits.setText(BLANK);
		playerDragonBets = 0;
		bdUnits.setText(BLANK);
		bankerDragonBets = 0;
		placedBets = 0;
	}
	
	public void placePlayerBet(InputEvent event){
		//System.out.println(event.getSource());
		String selectedUnit = (String) unit.getSelectedToggle().getUserData();
		if(selectedUnit != null){
			int iSelectedUnit = Integer.parseInt(selectedUnit);
			if(iSelectedUnit > gambi.getUnitBalance()){
				displayInsufficientBalance(pUnits);
				return;
			}else{
				placedBets = placedBets + iSelectedUnit;
				playerBets = playerBets+iSelectedUnit;
				pUnits.setText(Double.toString(playerBets));
			}
			gambi.setUnitBalance(gambi.getUnitBalance() - iSelectedUnit);
			playerStack.getChildren().add(getUnitCircle(selectedUnit));
			vegasBaca.placeBets(Outcome.player, iSelectedUnit);
			updateBalance();
		}
	}
	
	public void placeBankerBet(InputEvent event){
		//System.out.println(event.getSource());
		String selectedUnit = (String) unit.getSelectedToggle().getUserData();
		if(selectedUnit != null){
			int iSelectedUnit = Integer.parseInt(selectedUnit);
			if(iSelectedUnit > gambi.getUnitBalance()){
				displayInsufficientBalance(bUnits);
				return;
			}else{
				placedBets = placedBets + iSelectedUnit;
				bankerBets = bankerBets+iSelectedUnit;
				bUnits.setText(Double.toString(bankerBets));
			}
			gambi.setUnitBalance(gambi.getUnitBalance() - iSelectedUnit);
			bankerStack.getChildren().add(getUnitCircle(selectedUnit));
			vegasBaca.placeBets(Outcome.banker, iSelectedUnit);
			updateBalance();
		}
	}
	
	public void placeTieBet(InputEvent event){
		//System.out.println(event.getSource());
		String selectedUnit = (String) unit.getSelectedToggle().getUserData();
		if(selectedUnit != null){
			int iSelectedUnit = Integer.parseInt(selectedUnit);
			if(iSelectedUnit > gambi.getUnitBalance()){
				displayInsufficientBalance(tUnits);
				return;
			}else{
				placedBets = placedBets + iSelectedUnit;
				tieBets = tieBets + iSelectedUnit;
				tUnits.setText(Double.toString(tieBets));
			}
			gambi.setUnitBalance(gambi.getUnitBalance() - iSelectedUnit);
			tieStack.getChildren().add(getUnitCircle(selectedUnit));
			vegasBaca.placeBets(Outcome.tie, iSelectedUnit);
			updateBalance();
		}
	}
	
	public void placeEitherPair(InputEvent event){
		//System.out.println(event.getSource());
		String selectedUnit = (String) unit.getSelectedToggle().getUserData();
		if(selectedUnit != null){
			int iSelectedUnit = Integer.parseInt(selectedUnit);
			if(iSelectedUnit > gambi.getUnitBalance()){
				displayInsufficientBalance(epUnits);
				return;
			}else{
				placedBets = placedBets + iSelectedUnit;
				eitherPairBets = eitherPairBets + iSelectedUnit;
				epUnits.setText(Double.toString(eitherPairBets));
			}
			gambi.setUnitBalance(gambi.getUnitBalance() - iSelectedUnit);
			eitherPair.getChildren().add(getUnitCircle(selectedUnit));
			vegasBaca.placeBonusBets(VegasBaccarat.Bonus.EitherPair.getResult(), iSelectedUnit);
			updateBalance();
		}
	}
	
	public void placePlayerPair(InputEvent event){
		//System.out.println(event.getSource());
		String selectedUnit = (String) unit.getSelectedToggle().getUserData();
		if(selectedUnit != null){
			int iSelectedUnit = Integer.parseInt(selectedUnit);
			if(iSelectedUnit > gambi.getUnitBalance()){
				displayInsufficientBalance(ppUnits);
				return;
			}else{
				placedBets = placedBets + iSelectedUnit;
				playerPairBets = playerPairBets + iSelectedUnit;
				ppUnits.setText(Double.toString(playerPairBets));
			}
			gambi.setUnitBalance(gambi.getUnitBalance() - iSelectedUnit);
			playerPair.getChildren().add(getUnitCircle(selectedUnit));
			vegasBaca.placeBonusBets(VegasBaccarat.Bonus.PlayerPair.getResult(), iSelectedUnit);
			updateBalance();
		}
	}
	
	public void placeBankerPair(InputEvent event){
		//System.out.println(event.getSource());
		String selectedUnit = (String) unit.getSelectedToggle().getUserData();
		if(selectedUnit != null){
			int iSelectedUnit = Integer.parseInt(selectedUnit);
			if(iSelectedUnit > gambi.getUnitBalance()){
				displayInsufficientBalance(bpUnits);
				return;
			}else{
				placedBets = placedBets + iSelectedUnit;
				bankerPairBets = bankerPairBets + iSelectedUnit;
				bpUnits.setText(Double.toString(bankerPairBets));
			}
			gambi.setUnitBalance(gambi.getUnitBalance() - iSelectedUnit);
			bankerPair.getChildren().add(getUnitCircle(selectedUnit));
			vegasBaca.placeBonusBets(VegasBaccarat.Bonus.BankerPair.getResult(), iSelectedUnit);
			updateBalance();
		}
	}
	
	public void placePlayerDragon(InputEvent event){
		//System.out.println(event.getSource());
		String selectedUnit = (String) unit.getSelectedToggle().getUserData();
		if(selectedUnit != null){
			int iSelectedUnit = Integer.parseInt(selectedUnit);
			if(iSelectedUnit > gambi.getUnitBalance()){
				displayInsufficientBalance(pdUnits);
				return;
			}else{
				placedBets = placedBets + iSelectedUnit;
				playerDragonBets = playerDragonBets + iSelectedUnit;
				pdUnits.setText(Double.toString(playerDragonBets));
			}
			gambi.setUnitBalance(gambi.getUnitBalance() - iSelectedUnit);
			playerDragon.getChildren().add(getUnitCircle(selectedUnit));
			vegasBaca.placeBonusBets(VegasBaccarat.Bonus.PlayerDragon.getResult(), iSelectedUnit);
			updateBalance();
		}
	}
	
	public void placeBankerDragon(InputEvent event){
		//System.out.println(event.getSource());
		String selectedUnit = (String) unit.getSelectedToggle().getUserData();
		if(selectedUnit != null){
			int iSelectedUnit = Integer.parseInt(selectedUnit);
			if(iSelectedUnit > gambi.getUnitBalance()){
				displayInsufficientBalance(bdUnits);
				return;
			}else{
				placedBets = placedBets + iSelectedUnit;
				bankerDragonBets = bankerDragonBets + iSelectedUnit;
				bdUnits.setText(Double.toString(bankerDragonBets));
			}
			gambi.setUnitBalance(gambi.getUnitBalance() - iSelectedUnit);
			bankerDragon.getChildren().add(getUnitCircle(selectedUnit));
			vegasBaca.placeBonusBets(VegasBaccarat.Bonus.BankerDragon.getResult(), iSelectedUnit);
			updateBalance();
		}
	}
	
	
	public void displayInsufficientBalance(Label label) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Insufficient Balance");
		alert.setHeaderText("Balance Insufficient to plac bets");
		alert.setContentText("Go to Cashier to load balance");
		alert.showAndWait();
	}
	
	public void getCash(){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Load Cash");
		alert.setHeaderText("Get Cash");
		alert.setContentText("Choose your Amount.");

		ButtonType hundred = new ButtonType("100");
		ButtonType fivehundred = new ButtonType("500");
		ButtonType thousand = new ButtonType("1000");
		ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(hundred, fivehundred, thousand, cancel);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == hundred){
		    gambi.setUnitBalance(gambi.getUnitBalance()+100);
		} else if (result.get() == fivehundred) {
			gambi.setUnitBalance(gambi.getUnitBalance()+500);
		} else if (result.get() == thousand) {
			gambi.setUnitBalance(gambi.getUnitBalance()+1000);
		} else {
		    // ... user chose CANCEL or closed the dialog
		}
		updateBalance();
	}
	
	private Circle getUnitCircle(String unit){
		Circle unitCircle;
		if(unit != null && unit.equals(FIVE)){
			unitCircle = new Circle(24.0, new ImagePattern(new Image(IMAGES_DOLLAR5_JPG)));
		}else if(unit != null && unit.equals(TWENTY_FIVE)){
			unitCircle = new Circle(24.0, new ImagePattern(new Image(IMAGES_DOLLAR25_JPG)));
		}else{
			unitCircle = new Circle(24.0, new ImagePattern(new Image(IMAGES_DOLLAR100_JPG)));
		}
		return unitCircle;
	}
	
	public void clearBets(){
		if(playerStack.getChildren().size() >0){
			playerStack.getChildren().clear();
			pUnits.setText(BLANK);
			playerBets = 0;
		}
		if(bankerStack.getChildren().size() >0){
			bankerStack.getChildren().clear();
			bUnits.setText(BLANK);
			bankerBets = 0;
		}
		if(tieStack.getChildren().size() >0){
			tieStack.getChildren().clear();
			tUnits.setText(BLANK);
			tieBets = 0;
		}
		if(playerDragon.getChildren().size() >0){
			playerDragon.getChildren().clear();
			pdUnits.setText(BLANK);
			playerDragonBets = 0;
		}
		if(bankerDragon.getChildren().size() >0){
			bankerDragon.getChildren().clear();
			bdUnits.setText(BLANK);
			bankerDragonBets = 0;
		}
		if(playerPair.getChildren().size() >0){
			playerPair.getChildren().clear();
			ppUnits.setText(BLANK);
			playerPairBets = 0;
		}
		if(bankerPair.getChildren().size() >0){
			bankerPair.getChildren().clear();
			bpUnits.setText(BLANK);
			bankerPairBets = 0;
		}
		if(eitherPair.getChildren().size() >0){
			eitherPair.getChildren().clear();
			epUnits.setText(BLANK);
			eitherPairBets = 0;
		}
		gambi.setUnitBalance(gambi.getUnitBalance()+placedBets);
		placedBets = 0;
		vegasBaca.clearAllBets();
		updateBalance();
	}
	
}
