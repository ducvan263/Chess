package model;

public class KnightMove implements ICanMove {

	@Override
	public boolean canMove(Piece knight, int targetCol, int targetRow) {
		if (knight.isWhithinBoard(targetRow, targetCol)) {
			// knight can move if its movement ratio of col and row is 1:2 or 2:1
			if (Math.abs(targetCol - knight.getPreCol()) * Math.abs(targetRow - knight.getPreRow()) == 2) {
				if (knight.isValidSquare(targetCol, targetRow)) {
					return true;
				}
			}
		}
		return false;
	}

}
