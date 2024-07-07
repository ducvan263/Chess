package model;

public class Pawn extends Piece {

	public Pawn(int color, int col, int row) {
		super(color, col, row);

		canMove = new PawnMove();
		
		setType(TypePieces.PAWN);
		
		if (color == ChessModel.getWhite()) {
//			image = getImage("/pieces/White_pawn");
			setImage(getImage("/pieces/White_pawn"));
		} else {
//			image = getImage("/pieces/Black_pawn");
			setImage(getImage("/pieces/Black_pawn"));
		}
	}

//	public boolean canMove(int targetCol, int targetRow) {
//
//		if (isWhithinBoard(targetRow, targetCol) && !isSameSquare(targetCol, targetRow)) {
//			// Define the move value
//			int moveValue;
//			if (getColor() == ChessModel.getWhite()) {
//				moveValue = -1;
//			} else
//				moveValue = 1;
//
//			// Check the hitting piece
//			setHittingP(getHittingP(targetCol, targetRow));
//
//			// 1 square move
//			if (targetCol == getPreCol() && targetRow == getPreRow() + moveValue && getHittingP() == null) {
//				return true;
//			}
//
//			// 2 square move
//			if (targetCol == getPreCol() && targetRow == getPreRow() + moveValue * 2 && getHittingP() == null && isMoved() == false
//					&& !pieceIsOnStraightLine(targetCol, targetRow)) {
//				return true;
//			}
//
//			// Diagonal movement & Capture
//			if (Math.abs(targetCol - getPreCol()) == 1 && targetRow == getPreRow() + moveValue && getHittingP() != null
//					&& getHittingP().getColor() != getColor()) {
//				return true;
//			}
//			
//			// En Passant
//			if(Math.abs(targetCol - getPreCol()) == 1 && targetRow == getPreRow() + moveValue) {
//				for(Piece piece : ChessModel.getSimpieces()) {
//					if(piece.getCol() == targetCol && piece.getRow() == getPreRow() && piece.isTwoStepped()) {
//						setHittingP(piece);
//						return true;
//					}
//				}
//			}
//		}
//		return false;
//	}

}
