package model;

public class PawnMove implements ICanMove {

	@Override
	public boolean canMove(Piece pawn, int targetCol, int targetRow) {
		if (pawn.isWhithinBoard(targetRow, targetCol) && !pawn.isSameSquare(targetCol, targetRow)) {
			// Define the move value
			int moveValue;
			if (pawn.getColor() == ChessModel.getWhite()) {
				moveValue = -1;
			} else
				moveValue = 1;

			// Check the hitting piece
			pawn.setHittingP(pawn.getHittingP(targetCol, targetRow));

			// 1 square move
			if (targetCol == pawn.getPreCol() && targetRow == pawn.getPreRow() + moveValue && pawn.getHittingP() == null) {
				return true;
			}

			// 2 square move
			if (targetCol == pawn.getPreCol() && targetRow == pawn.getPreRow() + moveValue * 2 && pawn.getHittingP() == null && pawn.isMoved() == false
					&& !pawn.pieceIsOnStraightLine(targetCol, targetRow)) {
				return true;
			}

			// Diagonal movement & Capture
			if (Math.abs(targetCol - pawn.getPreCol()) == 1 && targetRow == pawn.getPreRow() + moveValue && pawn.getHittingP() != null
					&& pawn.getHittingP().getColor() != pawn.getColor()) {
				return true;
			}
			
			// En Passant
			if(Math.abs(targetCol - pawn.getPreCol()) == 1 && targetRow == pawn.getPreRow() + moveValue) {
				for(Piece piece : ChessModel.getSimpieces()) {
					if(piece.getCol() == targetCol && piece.getRow() == pawn.getPreRow() && piece.isTwoStepped()) {
						pawn.setHittingP(piece);
						return true;
					}
				}
			}
		}
		return false;
	}

}
