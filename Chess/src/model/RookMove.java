package model;

public class RookMove implements ICanMove {

	@Override
	public boolean canMove(Piece rook, int targetCol, int targetRow) {
		if (rook.isWhithinBoard(targetRow, targetCol) && !rook.isSameSquare(targetCol, targetRow)) {
			// Rook can move as long as either its col or row is the same
			if (targetCol == rook.getPreCol() || targetRow == rook.getPreRow()) {
				if (rook.isValidSquare(targetCol, targetRow) && rook.pieceIsOnStraightLine(targetCol, targetRow) == false) {
					return true;
				}
			}
		}
		return false;
	}

}
