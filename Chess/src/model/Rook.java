package model;

public class Rook extends Piece {

	public Rook(int color, int col, int row) {
		super(color, col, row);

		canMove = new RookMove();
		
		setType(TypePieces.ROOK);

		if (color == ChessModel.getWhite()) {
//			image = getImage("/pieces/White_castle");
			setImage(getImage("/pieces/White_castle"));
		} else {
//			image = getImage("/pieces/Black_castle");
			setImage(getImage("/pieces/Black_castle"));
		}
	}

//	public boolean canMove(int targetCol, int targetRow) {
//
//		if (isWhithinBoard(targetRow, targetCol) && !isSameSquare(targetCol, targetRow)) {
//			// Rook can move as long as either its col or row is the same
//			if (targetCol == getPreCol() || targetRow == getPreRow()) {
//				if (isValidSquare(targetCol, targetRow) && pieceIsOnStraightLine(targetCol, targetRow) == false) {
//					return true;
//				}
//			}
//		}
//		return false;
//	}
}
