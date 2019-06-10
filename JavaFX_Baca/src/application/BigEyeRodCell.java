package application;

public class BigEyeRodCell {
	
	private int rowIndex = 0;
	
	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public int getColIndex() {
		return colIndex;
	}

	public void setColIndex(int colIndex) {
		this.colIndex = colIndex;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	private int colIndex = 0;
	
	private String imageURL;
	
	public BigEyeRodCell(int rowIndex, int colIndex, String imageUrl) {
		this.rowIndex = rowIndex;
		this.colIndex = colIndex;
		this.imageURL = imageUrl;
	}
}
