package model;

public class Knight extends Piece {

	public Knight(int color, int col, int row) {
		super(color, col, row);

		canMove = new KnightMove();
		
		setType(TypePieces.KNIGHT);
		
		if (color == ChessModel.getWhite()) {
			setImage(getImage("/pieces/White_horse"));
		} else {
			setImage(getImage("/pieces/Black_horse"));
		}
	}

//	public boolean canMove(int targetCol, int targetRow) {
//
//		if (isWhithinBoard(targetRow, targetCol)) {
//			// knight can move if its movement ratio of col and row is 1:2 or 2:1
//			if (Math.abs(targetCol - getPreCol()) * Math.abs(targetRow - getPreRow()) == 2) {
//				if (isValidSquare(targetCol, targetRow)) {
//					return true;
//				}
//			}
//		}
//		return false;
//	}

}
