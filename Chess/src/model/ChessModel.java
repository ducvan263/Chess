package model;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ChessModel extends Observable {

	// Color
	private static final int WHITE = 0;
	private static final int BLACK = 1;
	private int currentColor = WHITE;

	// Board
	private Board board = new Board();

	// Piece
	private static final ArrayList<Piece> pieces = new ArrayList<>();
	private static final ArrayList<Piece> simpieces = new ArrayList<>();
	private ArrayList<Piece> promoPieces = new ArrayList<>();
	private Piece activeP, checkingP;
	private static Piece castlingP;

	// History
	private List<Move> moveHistory = new ArrayList<>();

	// Booleans
	private boolean canMove;
	private boolean validSquare;
	private boolean promotion;
	private boolean gameover;
	private boolean stalemate;

	// Model
	private static ChessModel chessModel;
	
	private ChessModel() {
		setPieces();
		copyPieces(pieces, simpieces);
	}

	public static ChessModel getInstance() {
		if(chessModel == null) {
			synchronized (ChessModel.class) {
				if(chessModel == null) {
					chessModel = new ChessModel();
				}
			}
		}
		return chessModel;
	}
	
	private void testCastling() {
		pieces.add(new Rook(WHITE, 0, 7));
		pieces.add(new Rook(WHITE, 7, 7));
		pieces.add(new King(WHITE, 4, 7));
	}

	private void testPromotion() {
		pieces.add(new King(WHITE, 7, 7));
		pieces.add(new Pawn(WHITE, 3, 3));
		pieces.add(new King(BLACK, 0, 0));
		pieces.add(new Pawn(BLACK, 4, 4));
	}

	private void testIllegal_Check_CheckMate() {
		pieces.add(new Pawn(WHITE, 7, 6));
		pieces.add(new King(WHITE, 3, 7));
		pieces.add(new King(BLACK, 0, 3));
		pieces.add(new Bishop(BLACK, 1, 4));
		pieces.add(new Queen(BLACK, 4, 5));
	}

	private void testGameOver_Stalemate() {
		pieces.add(new King(WHITE, 2, 4));
		pieces.add(new King(BLACK, 0, 3));
		pieces.add(new Queen(WHITE, 2, 1));
	}

	public void setPieces() {

		// WHITE Pieces
		for (int i = 0; i < 8; i++) {
//			pieces.add(new Pawn(WHITE, i, 6));
		}
//		vertically
//		pieces.add(new Rook(WHITE, 5, 6));
//		pieces.add(new King(WHITE, 4, 7));
//		pieces.add(new Queen(WHITE, 3, 7));

//		horizontally
//		pieces.add(new Rook(WHITE, 6, 7));
//		pieces.add(new King(WHITE, 4, 7));
//		pieces.add(new Queen(WHITE, 3, 7));
		
//		diagonally
//		pieces.add(new Rook(WHITE, 6, 7));
//		pieces.add(new King(WHITE, 4, 7));
//		pieces.add(new Queen(WHITE, 7, 6));

//		win
		pieces.add(new Rook(WHITE, 7, 1));
		pieces.add(new King(WHITE, 4, 7));
		pieces.add(new Queen(WHITE, 6, 1));
		
//		stalemate
//		pieces.add(new Rook(WHITE, 6, 6));
//		pieces.add(new King(WHITE, 4, 7));
//		pieces.add(new Queen(WHITE, 1, 4));
		
		
		pieces.add(new Rook(WHITE, 0, 7));
		pieces.add(new Knight(WHITE, 1, 7));
		pieces.add(new Knight(WHITE, 6, 7));
		pieces.add(new Bishop(WHITE, 2, 7));
		pieces.add(new Bishop(WHITE, 5, 7));

		// BLACK Pieces
		for (int i = 0; i < 8; i++) {
//			pieces.add(new Pawn(BLACK, i, 1));
		}
//		vertically
//		pieces.add(new Rook(BLACK, 7, 6));
//		pieces.add(new King(BLACK, 4, 0));
//		pieces.add(new Queen(BLACK, 3, 0));

//		horizontally
//		pieces.add(new Rook(BLACK, 5, 0));
//		pieces.add(new King(BLACK, 3, 4));
//		pieces.add(new Queen(BLACK, 2, 0));
		
//		diagonally
//		pieces.add(new Rook(BLACK, 5, 0));
//		pieces.add(new Queen(BLACK, 7, 0));
//		pieces.add(new King(BLACK, 4, 4));

//		win		
		pieces.add(new King(BLACK, 1, 0));
		pieces.add(new Pawn(BLACK, 0, 1));

//		stalemate
//		pieces.add(new King(BLACK, 2, 6));
		
		
//		pieces.add(new Rook(BLACK, 0, 0));
//		pieces.add(new Knight(BLACK, 1, 0));
//		pieces.add(new Knight(BLACK, 6, 0));
//		pieces.add(new Bishop(BLACK, 2, 0));
//		pieces.add(new Bishop(BLACK, 5, 0));
	}

	public void copyPieces(ArrayList<Piece> sourse, ArrayList<Piece> target) {
		target.clear();
		target.addAll(sourse);
	}

	public void changePlayer() {
		if (currentColor == WHITE) {
			currentColor = BLACK;
			// Reset black's two stepped status
//			for (Piece piece : pieces) {
			for (int i = 0; i < simpieces.size(); i++) {
				Piece piece = simpieces.get(i);
				if (piece.getColor() == BLACK) {
					piece.setTwoStepped(false);
				}
			}
		} else {
			currentColor = WHITE;
			// Reset white's two stepped status
//			for (Piece piece : pieces) {
			for (int i = 0; i < simpieces.size(); i++) {
				Piece piece = simpieces.get(i);
				if (piece.getColor() == WHITE) {
					piece.setTwoStepped(false);
				}
			}
		}
		activeP = null;
	}

	public void checkCastling() {
		if (castlingP != null) {
			if (castlingP.getCol() == 0) {
				castlingP.setCol(castlingP.getCol() + 3);
			} else if (castlingP.getCol() == 7) {
				castlingP.setCol(castlingP.getCol() - 2);
			}
			castlingP.setX(castlingP.getX(castlingP.getCol()));
		}
	}

	public boolean canPromote() {

		if (activeP.getType() == TypePieces.PAWN) {
			if (currentColor == WHITE && activeP.getRow() == 0 || currentColor == BLACK && activeP.getRow() == 7) {
				promoPieces.clear();
				promoPieces.add(new Rook(currentColor, 9, 1));
				promoPieces.add(new Knight(currentColor, 9, 3));
				promoPieces.add(new Bishop(currentColor, 9, 5));
				promoPieces.add(new Queen(currentColor, 9, 7));
				
//				promoPieces.add(new Rook(currentColor, 9, 2));
//				promoPieces.add(new Knight(currentColor, 9, 3));
//				promoPieces.add(new Bishop(currentColor, 9, 4));
//				promoPieces.add(new Queen(currentColor, 9, 5));
				
				return true;
			}
		}

		return false;
	}

	public boolean isKingInCheck() {

		Piece king = getKing(true);

		if (activeP.canMove(king.getCol(), king.getRow())) {
			checkingP = activeP;
			return true;
		} else {
			checkingP = null;
		}
		return false;
	}

	private Piece getKing(boolean opponent) {
		Piece king = null;

//		for (Piece piece : simpieces) {
		for (int i = 0; i < simpieces.size(); i++) {
			Piece piece = simpieces.get(i);
			if (opponent) {
				if (piece.getType() == TypePieces.KING && piece.getColor() != currentColor) {
					king = piece;
				}
			} else {
				if (piece.getType() == TypePieces.KING && piece.getColor() == currentColor) {
					king = piece;
				}
			}
		}
		return king;
	}

	public boolean isIllegal(Piece king) {
		if (king.getType() == TypePieces.KING) {
//			for (Piece piece : simpieces) {
			for (int i = 0; i < simpieces.size(); i++) {
				Piece piece = simpieces.get(i);
				if (piece != king && piece.getColor() != king.getColor()
						&& piece.canMove(king.getCol(), king.getRow())) {
					return true;
				}
			}
		}
		return false;
	}

	// The King is in check, other pieces are restricted from moving.
	public boolean opponentCanCaptureKing() {

		Piece king = getKing(false);

//		for (Piece piece : simpieces) {
		for (int i = 0; i < simpieces.size(); i++) {
			Piece piece = simpieces.get(i);
			if (piece.getColor() != king.getColor() && piece.canMove(king.getCol(), king.getRow())) {
				return true;
			}
		}
		return false;
	}

	public boolean kingCanMove(Piece king) {

		// Simulate if there is any square where the king can move to
		if (isValidMove(king, -1, -1) || isValidMove(king, 0, -1) || isValidMove(king, 1, -1)
				|| isValidMove(king, -1, 1) || isValidMove(king, 0, 1) || isValidMove(king, 1, 1)
				|| isValidMove(king, -1, 0) || isValidMove(king, 1, 0)) {
			return true;
		}

		return false;
	}

	public boolean isValidMove(Piece king, int colPlus, int rowPlus) {

		boolean isValidMove = false;

		king.setCol(king.getCol() + colPlus);
		king.setRow(king.getRow() + rowPlus);

		if (king.canMove(king.getCol(), king.getRow())) {
			if (king.getHittingP() != null) {
				simpieces.remove(king.getHittingP().getIndex());
			}
			if (isIllegal(king) == false) {
				isValidMove = true;
			}
		}

		king.resetPosition();
		copyPieces(pieces, simpieces);

		return isValidMove;
	}

	public boolean isCheckMate() {

		Piece king = getKing(true);

		if (kingCanMove(king)) {
			return false;
		} else {

			// But you still have a chance!!!
			// Check if you can block the attack with your piece

			// Check the position of the checking piece and the king in check
			int colDiff = Math.abs(checkingP.getCol() - king.getCol());
			int rowDiff = Math.abs(checkingP.getRow() - king.getRow());

			if (colDiff == 0) { // The checking piece is attacking vertically
				if (checkingP.getRow() < king.getRow()) { // The checking piece is above the king
					for (int row = checkingP.getRow(); row < king.getRow(); row++) {
//						for (Piece piece : simpieces) {
						for (int i = 0; i < simpieces.size(); i++) {
							Piece piece = simpieces.get(i);
							if (piece != king && piece.getColor() != currentColor
									&& piece.canMove(checkingP.getCol(), row)) {
								return false;
							}
						}
					}
				}
				if (checkingP.getRow() > king.getRow()) { // The checking piece is below the king
					for (int row = checkingP.getRow(); row > king.getRow(); row--) {
//						for (Piece piece : simpieces) {
						for (int i = 0; i < simpieces.size(); i++) {
							Piece piece = simpieces.get(i);
							if (piece != king && piece.getColor() != currentColor
									&& piece.canMove(checkingP.getCol(), row)) {
								return false;
							}
						}
					}
				}
			} else if (rowDiff == 0) { // The checking piece is attacking horizontally
				if (checkingP.getCol() < king.getCol()) { // The checking piece is the left
					for (int col = checkingP.getCol(); col < king.getCol(); col++) {
//						for (Piece piece : simpieces) {
						for (int i = 0; i < simpieces.size(); i++) {
							Piece piece = simpieces.get(i);
							if (piece != king && piece.getColor() != currentColor
									&& piece.canMove(col, checkingP.getRow())) {
								return false;
							}
						}
					}
				}
				if (checkingP.getCol() > king.getCol()) { // The checking piece is the right
					for (int col = checkingP.getCol(); col > king.getCol(); col--) {
//						for (Piece piece : simpieces) {
						for (int i = 0; i < simpieces.size(); i++) {
							Piece piece = simpieces.get(i);
							if (piece != king && piece.getColor() != currentColor
									&& piece.canMove(col, checkingP.getRow())) {
								return false;
							}
						}
					}
				}
			} else if (colDiff == rowDiff) { // The checking piece is attacking diagonally
				if (checkingP.getRow() < king.getRow()) { // The checking piece is above the king
					if (checkingP.getCol() < king.getCol()) { // The checking piece is un the upper left
						for (int col = checkingP.getCol(),
								row = checkingP.getRow(); col < king.getCol(); col++, row++) {
//							for (Piece piece : simpieces) {
							for (int i = 0; i < simpieces.size(); i++) {
								Piece piece = simpieces.get(i);
								if (piece != king && piece.getColor() != currentColor && piece.canMove(col, row)) {
									return false;
								}
							}
						}
					}
					if (checkingP.getCol() > king.getCol()) { // The checking piece is un the upper right
						for (int col = checkingP.getCol(),
								row = checkingP.getRow(); col > king.getCol(); col--, row++) {
//							for (Piece piece : simpieces) {
							for (int i = 0; i < simpieces.size(); i++) {
								Piece piece = simpieces.get(i);
								if (piece != king && piece.getColor() != currentColor && piece.canMove(col, row)) {
									return false;
								}
							}
						}
					}
				}
				if (checkingP.getRow() > king.getRow()) { // The checking piece is below the king
					if (checkingP.getCol() < king.getCol()) { // The checking piece is un the lower left
						for (int col = checkingP.getCol(),
								row = checkingP.getRow(); col < king.getCol(); col++, row--) {
//							for (Piece piece : simpieces) {
							for (int i = 0; i < simpieces.size(); i++) {
								Piece piece = simpieces.get(i);
								if (piece != king && piece.getColor() != currentColor && piece.canMove(col, row)) {
									return false;
								}
							}
						}
					}
					if (checkingP.getCol() > king.getCol()) { // The checking piece is un the lower right
						for (int col = checkingP.getCol(),
								row = checkingP.getRow(); col > king.getCol(); col--, row--) {
//							for (Piece piece : simpieces) {
							for (int i = 0; i < simpieces.size(); i++) {
								Piece piece = simpieces.get(i);
								if (piece != king && piece.getColor() != currentColor && piece.canMove(col, row)) {
									return false;
								}
							}
						}
					}
				}
			}
//			else {
//				// The checking piece is Knight
//			}
		}
		return true;
	}

	public boolean isStalemate() {

		int count = 0;

		// Count the number of pieces
		for (int i = 0; i < simpieces.size(); i++) {
			if (simpieces.get(i).getColor() != currentColor) {
				count++;
			}
		}

		// If only one piece (the king) is left
		if (count == 1) {
			if (kingCanMove(getKing(true)) == false) {
				return true;
			}
		}

		return false;
	}

	// DRAW
	public void drawBoard(Graphics2D g2) {
		board.draw(g2);
	}


	public void drawPieces(Graphics2D g2) {
		for (int i = 0; i < simpieces.size(); i++) {
			Piece piece = simpieces.get(i);
			piece.draw(g2);
		}
	}

	public void addMoveHistory(Move move) {
		moveHistory.add(move);
		notifyChanger();
	}

	// OBSERVER

	public void addObserver(Observer ob) {
		super.addObserver(ob);
	}

	public void removeObserver(Observer ob) {
		super.deleteObserver(ob);
	}

	public void notifyChanger() {
		setChanged();
		notifyObservers();
	}

	// GETTER && SETTER

	public int getCurrentColor() {
		return currentColor;
	}

	public void setCurrentColor(int currentColor) {
		this.currentColor = currentColor;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public ArrayList<Piece> getPromoPieces() {
		return promoPieces;
	}

	public void setPromoPieces(ArrayList<Piece> promoPieces) {
		this.promoPieces = promoPieces;
	}

	public Piece getActiveP() {
		return activeP;
	}

	public void setActiveP(Piece activeP) {
		this.activeP = activeP;
	}

	public Piece getCheckingP() {
		return checkingP;
	}

	public void setCheckingP(Piece checkingP) {
		this.checkingP = checkingP;
	}

	public static Piece getCastlingP() {
		return castlingP;
	}

	public static void setCastlingP(Piece castlingP) {
		ChessModel.castlingP = castlingP;
	}

	public List<Move> getMoveHistory() {
		return moveHistory;
	}

	public void setMoveHistory(List<Move> moveHistory) {
		this.moveHistory = moveHistory;
	}

	public boolean isCanMove() {
		return canMove;
	}

	public void setCanMove(boolean canMove) {
		this.canMove = canMove;
	}

	public boolean isValidSquare() {
		return validSquare;
	}

	public void setValidSquare(boolean validSquare) {
		this.validSquare = validSquare;
	}

	public boolean isPromotion() {
		return promotion;
	}

	public void setPromotion(boolean promotion) {
		this.promotion = promotion;
	}

	public boolean isGameover() {
		return gameover;
	}

	public void setGameover(boolean gameover) {
		this.gameover = gameover;
	}

	public static int getWhite() {
		return WHITE;
	}

	public static int getBlack() {
		return BLACK;
	}

	public static ArrayList<Piece> getPieces() {
		return pieces;
	}

	public static ArrayList<Piece> getSimpieces() {
		return simpieces;
	}

	public void setStalemate(boolean stalemate) {
		this.stalemate = stalemate;
	}

}
