package model;

public class KingMove implements ICanMove {

	@Override
	public boolean canMove(Piece king, int targetCol, int targetRow) {
		// Move
		if (king.isWhithinBoard(targetRow, targetCol)) {
			if (Math.abs(targetCol - king.getPreCol()) + Math.abs(targetRow - king.getPreRow()) == 1
					|| Math.abs(targetCol - king.getPreCol()) * Math.abs(targetRow - king.getPreRow()) == 1) {
				if (king.isValidSquare(targetCol, targetRow)) {
					return true;
				}
			}
		}

		// Castling
		if (king.isMoved() == false) {
			// Right castling
			if (targetCol == king.getPreCol() + 2 && targetRow == king.getPreRow()
					&& !king.pieceIsOnStraightLine(targetCol, targetRow)) {
				for (Piece piece : ChessModel.getSimpieces()) {
					if (piece.getCol() == king.getPreCol() + 3 && piece.getRow() == king.getPreRow()
							&& piece.isMoved() == false) {
						ChessModel.setCastlingP(piece);
						return true;
					}
				}
			}

			// Left castling
			if (targetCol == king.getPreCol() - 2 && targetRow == king.getPreRow()
					&& !king.pieceIsOnStraightLine(targetCol, targetRow)) {
				Piece[] p = new Piece[2];
				for (Piece piece : ChessModel.getSimpieces()) {
					if (piece.getCol() == king.getPreCol() - 3 && piece.getRow() == targetRow) {
						p[0] = piece;
					}
					if (piece.getCol() == king.getPreCol() - 4 && piece.getRow() == targetRow) {
						p[1] = piece;
					}
					if (p[0] == null && p[1] != null && p[1].isMoved() == false) {
						ChessModel.setCastlingP(p[1]);
						return true;
					}
				}
			}
		}
		return false;
	}

}
