package application.mak.btc;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.paint.Color;
import javafx.util.Callback;

/**
 * This sets the cell background to light green for wining bets and light red losing bets.
 * @author Visitor
 * lose color code #f79a91
 * win color code #d2f7b4
 */
public class BtcPBTableCellFactory implements Callback<TableColumn<BtcScoreCard, String>, TableCell<BtcScoreCard, String>> {

	@Override
	public TableCell<BtcScoreCard, String> call(TableColumn<BtcScoreCard, String> param) {
		TableCell<BtcScoreCard, String> cell = new TableCell<BtcScoreCard, String>(){
			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if(empty || item == null || item.length() == 0){
					setText(null);
					setGraphic(null);
					return;
				}
				setText(item);
				if(item != null && item.startsWith("O") && item.length() > 1){
					this.setTextFill(Color.GREEN);
				}else if(item != null && !item.startsWith("O") && item.length() > 0){
					this.setTextFill(Color.RED);
				}
			}
		};
		return cell;
	}
	
}

	
