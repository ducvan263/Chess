package model;

public class Bishop extends Piece {

	public Bishop(int color, int col, int row) {
		super(color, col, row);

		canMove = new BishopMove();
		
		setType(TypePieces.BISHOP);
		
		if (color == ChessModel.getWhite()) {
			setImage(getImage("/pieces/White_bishop"));
		} else {
			setImage(getImage("/pieces/Black_bishop"));
		}
	}

//	public boolean canMove(int targetCol, int targetRow) {
//
//		if (isWhithinBoard(targetRow, targetCol) && !isSameSquare(targetCol, targetRow)) {
//			if (Math.abs(targetRow - getPreRow()) == Math.abs(targetCol - getPreCol())) {
//				if (isValidSquare(targetCol, targetRow) && !pieceIsOnDiagonalLine(targetCol, targetRow)) {
//					return true;
//				}
//			}
//		}
//		return false;
//	}
}
