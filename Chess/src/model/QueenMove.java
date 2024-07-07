package model;

public class QueenMove implements ICanMove {

	@Override
	public boolean canMove(Piece queen, int targetCol, int targetRow) {
		if (queen.isWhithinBoard(targetRow, targetCol) && !queen.isSameSquare(targetCol, targetRow)) {
			//Vertical && Horizontal
			if (targetCol == queen.getPreCol() || targetRow == queen.getPreRow()) {
				if (queen.isValidSquare(targetCol, targetRow) && !queen.pieceIsOnStraightLine(targetCol, targetRow)) {
					return true;
				}
			}
			//Diagonal
			if (Math.abs(targetRow - queen.getPreRow()) == Math.abs(targetCol - queen.getPreCol())) {
				if (queen.isValidSquare(targetCol, targetRow) && !queen.pieceIsOnDiagonalLine(targetCol, targetRow)) {
					return true;
				}
			}
		}
		return false;
	}

}
