
package model;

public class BishopMove implements ICanMove{

	@Override
	public boolean canMove(Piece bishop, int targetCol, int targetRow) {
		if (bishop.isWhithinBoard(targetRow, targetCol) && !bishop.isSameSquare(targetCol, targetRow)) {
			if (Math.abs(targetRow - bishop.getPreRow()) == Math.abs(targetCol - bishop.getPreCol())) {
				if (bishop.isValidSquare(targetCol, targetRow) && !bishop.pieceIsOnDiagonalLine(targetCol, targetRow)) {
					return true;
				}
			}
		}
		return false;
	}

}
