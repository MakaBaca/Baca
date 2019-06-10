package application;

import com.mak.casino.Baccarat.Outcome;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class EzBigRodTester{
	
	EzBacaBigEyeRod6Xn rod;
	
	BigEyeRodCell cellValue;
	
	int width = 24;
	
	int height = 20;
	
	@FXML GridPane bigRod;
	
	@FXML AnchorPane gip;
	
	@FXML Text msg;
	
	@FXML Label noOfDragon;
	
	@FXML Label noOfPanda;
	
	public void onClickPlayer(){
		cellValue = rod.updateHand(Outcome.player);
		bigRod.add(new ImageView(new Image(cellValue.getImageURL(),width,height,false,false)), cellValue.getColIndex(), cellValue.getRowIndex());
		/*Timeline t = new Timeline(
				new KeyFrame(Duration.seconds(0.01), new KeyValue(msg.textProperty(),"Game in Progress")),
				new KeyFrame(Duration.seconds(0.01), new KeyValue(gip.disableProperty(), true)),
				new KeyFrame(Duration.seconds(2), new KeyValue(msg.textProperty(),null)),
				new KeyFrame(Duration.seconds(2), new KeyValue(gip.disableProperty(), false))
				);
		t.play();*/
	}
	
	public void onClickBanker(){
		cellValue = rod.updateHand(Outcome.banker);
		bigRod.add(new ImageView(new Image(cellValue.getImageURL(),width,height,false,false)), cellValue.getColIndex(), cellValue.getRowIndex());
	}
	
	public void onClickTie(){
		cellValue = rod.updateHand(Outcome.tie);
		bigRod.add(new ImageView(new Image(cellValue.getImageURL(),width,height,false,false)), cellValue.getColIndex(), cellValue.getRowIndex());
	}
	
	public void onClickClear(){
		ObservableList<Node> list = bigRod.getChildren();
		ImageView iv = null;
		for(Node n:list){
			if(n instanceof ImageView){
				iv = (ImageView) n;
				iv.setImage(null);
			}
		}
		rod = new EzBacaBigEyeRod6Xn();
	}
	
	
	public EzBigRodTester() {
		rod = new EzBacaBigEyeRod6Xn();
	}
	
	public void onClickDragon(){
		cellValue = rod.updateDragon();
		bigRod.add(new ImageView(new Image(cellValue.getImageURL(),width,height,false,false)), cellValue.getColIndex(), cellValue.getRowIndex());
		noOfDragon.setText(Integer.toString(rod.getNoOfDragon()));
	}
	
	public void onClickPanda(){
		cellValue = rod.updatePanda();
		bigRod.add(new ImageView(new Image(cellValue.getImageURL(),width,height,false,false)), cellValue.getColIndex(), cellValue.getRowIndex());
		noOfPanda.setText(Integer.toString(rod.getNoOfPanda()));
	}
	
	
	

}
