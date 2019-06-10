package application.mak.btc;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.paint.Color;
import javafx.util.Callback;

/**
 * This changes the font color to red for negative counts.
 * @author Visitor
 */
public class BtcCountTableCellFactory implements Callback<TableColumn<BtcScoreCard, Integer>, TableCell<BtcScoreCard, Integer>> {

	@Override
	public TableCell<BtcScoreCard, Integer> call(TableColumn<BtcScoreCard, Integer> param) {
		TableCell<BtcScoreCard, Integer> cell = new TableCell<BtcScoreCard, Integer>(){
			@Override
			protected void updateItem(Integer count, boolean empty) {
				super.updateItem(count, empty);
				if(empty || count == null){
					setText(null);
					setGraphic(null);
					setTextFill(null);
					return;
				}
				setText(""+count);
				if(count < 0){
					this.setTextFill(Color.RED);
				}else{
					this.setTextFill(Color.BLACK);
				}
			}
		};
		return cell;
	}

}
