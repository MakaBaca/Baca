package application;

import com.mak.casino.Baccarat.Outcome;

public class EzBacaBigEyeRod6Xn extends BigEyeRod6Xn {

	private int noOfPanda = 0;

	private int noOfDragon = 0;
	
	public int getNoOfPanda() {
		return noOfPanda;
	}

	public int getNoOfDragon() {
		return noOfDragon;
	}

	public BigEyeRodCell updateDragon() {
		noOfDragon++;
		BigEyeRodCell cellValue = updateHand(Outcome.banker);
		String imageUrl = cellValue.getImageURL();
		if (imageUrl.endsWith("Tie")) {
			switch (continuesTies) {
			case 0:
				break;
			case 1:
				cellValue.setImageURL("images/DragonTie.png");
				break;
			case 2:
				cellValue.setImageURL("images/Dragon2Tie.png");
				break;
			case 3:
				cellValue.setImageURL("images/Dragon3Tie.png");
				break;
			case 4:
				cellValue.setImageURL("images/Dragon4Tie.png");
				break;
			default:
				if (continuesTies > 4) {
					cellValue.setImageURL("images/Dragon4+Tie.png");
				}
			}
		} else {
			cellValue.setImageURL("images/Dragon.png");
		}
		return cellValue;
	}

	public BigEyeRodCell updatePanda() {
		noOfPanda++;
		BigEyeRodCell cellValue = updateHand(Outcome.player);
		String imageUrl = cellValue.getImageURL();
		if (imageUrl.endsWith("Tie")) {
			switch (continuesTies) {
			case 0:
				break;
			case 1:
				cellValue.setImageURL("images/PandaTie.png");
				break;
			case 2:
				cellValue.setImageURL("images/Panda2Tie.png");
				break;
			case 3:
				cellValue.setImageURL("images/Panda3Tie.png");
				break;
			case 4:
				cellValue.setImageURL("images/Panda4Tie.png");
				break;
			default:
				if (continuesTies > 4) {
					cellValue.setImageURL("images/Panda4+Tie.png");
				}
			}
		} else {
			cellValue.setImageURL("images/Panda.png");
		}
		return cellValue;
	}

}
