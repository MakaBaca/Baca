package application;



import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Button;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TestToogleButton extends Application{
	public static void main(String[] args) {
	    launch(args);
	  }

	  @Override
	  public void start(Stage stage) {
	    Scene scene = new Scene(new Group());
	    stage.setTitle("Radio Button Sample");
	    stage.setWidth(250);
	    stage.setHeight(150);

	    final ToggleGroup group = new ToggleGroup();

	    ToggleButton rb1 = new ToggleButton("A");
	    rb1.setToggleGroup(group);
	    rb1.setUserData("A");
	    rb1.setSelected(true);

	    ToggleButton rb2 = new ToggleButton("B");
	    rb2.setToggleGroup(group);
	    rb2.setUserData("B");

	    ToggleButton rb3 = new ToggleButton("C");
	    rb3.setToggleGroup(group);
	    rb3.setUserData("C");
	    
	    Button b = new Button("Check");
	    b.setOnAction(new EventHandler<ActionEvent>() {
	        @Override public void handle(ActionEvent e) {
	           System.out.println("Check");
	        }
	    });

	    group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
	      public void changed(ObservableValue<? extends Toggle> ov,
	          Toggle old_toggle, Toggle new_toggle) {
	    	  System.out.println("Inside-->"+old_toggle);
	        if (group.getSelectedToggle() != null) {
	          System.out.println(group.getSelectedToggle().getUserData().toString());
	        }
	      }
	    });

	    HBox hbox = new HBox();
	    VBox vbox = new VBox();

	    vbox.getChildren().add(rb1);
	    vbox.getChildren().add(rb2);
	    vbox.getChildren().add(rb3);
	    vbox.getChildren().add(b);
	    vbox.setSpacing(10);

	    hbox.getChildren().add(vbox);
	    hbox.setSpacing(50);
	    hbox.setPadding(new Insets(20, 10, 10, 20));

	    ((Group) scene.getRoot()).getChildren().add(hbox);
	    stage.setScene(scene);
	    stage.show();
	  }

}
