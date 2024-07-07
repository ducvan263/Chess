package model;

public class Queen extends Piece{

	public Queen(int color, int col, int row) {
		super(color, col, row);
		
		canMove = new QueenMove();
		
		setType(TypePieces.QUEEN);
		
		if(color == ChessModel.getWhite()) {
//			image = getImage("/pieces/White_queen");
			setImage(getImage("/pieces/White_queen"));
		}else {
//			image = getImage("/pieces/Black_queen");
			setImage(getImage("/pieces/Black_queen"));
		}
	}
	
//	public boolean canMove(int targetCol, int targetRow) {
//
//		if (isWhithinBoard(targetRow, targetCol) && !isSameSquare(targetCol, targetRow)) {
//			//Vertical && Horizontal
//			if (targetCol == getPreCol() || targetRow == getPreRow()) {
//				if (isValidSquare(targetCol, targetRow) && !pieceIsOnStraightLine(targetCol, targetRow)) {
//					return true;
//				}
//			}
//			//Diagonal
//			if (Math.abs(targetRow - getPreRow()) == Math.abs(targetCol - getPreCol())) {
//				if (isValidSquare(targetCol, targetRow) && !pieceIsOnDiagonalLine(targetCol, targetRow)) {
//					return true;
//				}
//			}
//		}
//		return false;
//	}
}
