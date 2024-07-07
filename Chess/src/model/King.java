package model;

public class King extends Piece {

	public King(int color, int col, int row) {
		super(color, col, row);

		canMove = new KingMove();
		
		setType(TypePieces.KING);

		if (color == ChessModel.getWhite()) {
			setImage(getImage("/pieces/White_king"));
		} else {
			setImage(getImage("/pieces/Black_king"));
		}
	}

//	public boolean canMove(int targetCol, int targetRow) {
//
//		// Move
//		if (isWhithinBoard(targetRow, targetCol)) {
//			if (Math.abs(targetCol - getPreCol()) + Math.abs(targetRow - getPreRow()) == 1
//					|| Math.abs(targetCol - getPreCol()) * Math.abs(targetRow - getPreRow()) == 1) {
//				if (isValidSquare(targetCol, targetRow)) {
//					return true;
//				}
//			}
//		}
//
//		// Castling
//		if (isMoved() == false) {
//			// Right castling
//			if (targetCol == getPreCol() + 2 && targetRow == getPreRow() && !pieceIsOnStraightLine(targetCol, targetRow)) {
//				for (Piece piece : ChessModel.getSimpieces()) {
//					if (piece.getCol() == getPreCol() + 3 && piece.getRow() == getPreRow() && piece.isMoved() == false) {
//						ChessModel.setCastlingP(piece);
//						return true;
//					}
//				}
//			}
//
//			// Left castling
//			if(targetCol == getPreCol() - 2 && targetRow == getPreRow() && !pieceIsOnStraightLine(targetCol, targetRow)) {
//				Piece[] p = new Piece[2];
//				for(Piece piece : ChessModel.getSimpieces()) {
//					if(piece.getCol() == getPreCol() - 3 && piece.getRow() == targetRow) {
//						p[0] = piece;
//					}
//					if(piece.getCol() == getPreCol() - 4 && piece.getRow() == targetRow) {
//						p[1] = piece;
//					}
//					if(p[0] == null && p[1] != null && p[1].isMoved() == false) {
//						ChessModel.setCastlingP(p[1]);
//						return true;
//					}
//				}
//			}
//		}
//		return false;
//	}

}
