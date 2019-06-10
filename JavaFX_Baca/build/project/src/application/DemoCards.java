package application;

import java.util.List;

import com.mak.baca.card.BaccaratCards;
import com.mak.baca.card.Deck;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class DemoCards extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		AnchorPane root = new AnchorPane();
		HBox h = new HBox(5);
		root.getChildren().add(h);
		Deck d = new Deck();
		List<BaccaratCards> deckOfCards = d.getDeckOfCards();
		for(BaccaratCards card: deckOfCards){
			System.out.println("Setting-->"+"images/"+card.toString()+".png");
			ImageView iv = new ImageView(new Image("images/"+card.toString()+".png"));
			iv.setFitHeight(15);
			iv.setFitWidth(10);
			h.getChildren().add(iv);
		}
		primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();

		
	}
	
	public static void main(String[] args) {
        launch(args);
    }

}
