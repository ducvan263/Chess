package model;

public class Move {
	
	private Piece piece;
	private int startCol;
	private int endCol;
	private int startRow;
	private int endRow;

	public Move(Piece piece, int startCol, int endCol, int startRow, int endRow) {
		super();
		this.piece = piece;
		this.startCol = startCol;
		this.endCol = endCol;
		this.startRow = startRow;
		this.endRow = endRow;
	}

	public Piece getPiece() {
		return piece;
	}

	public int getStartCol() {
		return startCol;
	}

	public int getEndCol() {
		return endCol;
	}

	public int getStartRow() {
		return startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	@Override
	public String toString() {
		String colorP = "";
		if (piece.getColor() == 0) {
			colorP = "White";
		} else
			colorP = "Black";

		return colorP + " " + piece.getClass().getSimpleName() + " from (" + startCol + "," + startRow + ") to ("
				+ endCol + "," + endRow + ")";
	}

}
