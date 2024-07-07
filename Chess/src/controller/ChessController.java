package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import model.Bishop;
import model.Board;
import model.ChessModel;
import model.Knight;
import model.Move;
import model.Piece;
import model.Queen;
import model.Rook;
import view.ChessView;

public class ChessController extends JFrame implements Observer, Runnable {

	public ChessModel model;
	public ChessView view;

	private final int FPS = 60;
	public Thread gameThread;

	// History
	private JTextArea moveHistoryArea;
	private JScrollPane scrollPane;

	public Mouse mouse = new Mouse();

	public ChessController() {

		setTitle("Simple Chess");
		model = ChessModel.getInstance();
		view = new ChessView(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		add(view);

		moveHistoryArea = new JTextArea();
		moveHistoryArea.setEditable(false);
		scrollPane = new JScrollPane(moveHistoryArea);
		scrollPane.setPreferredSize(new Dimension(250, ChessView.HEIGHT));
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		add(scrollPane, BorderLayout.EAST);

		pack();

		setLocationRelativeTo(null);
		setVisible(true);

		model.addObserver(this);
	}

	public void launchGame() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {

		// GAME LOOP
		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;

		while (gameThread != null) {
			currentTime = System.nanoTime();

			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;

			if (delta >= 1) {
				update();
				view.repaint();
				delta--;
			}
		}
		
	}

	private void update() {
		if (model.isPromotion()) {
			promoting();
		} else if (model.isGameover() == false && model.isStalemate() == false) {
			// MOUSE BUTTON PRESSED //
			if (mouse.pressed) {
				if (model.getActiveP() == null) {
					// If activeP is null, check if you can pick up a piece
					for (Piece piece : ChessModel.getSimpieces()) {
						// If the mouse is on an ally piece, pick it up as the activeP
						if (piece.getColor() == model.getCurrentColor() && piece.getCol() == mouse.x / Board.SQUARE_SIZE
								&& piece.getRow() == mouse.y / Board.SQUARE_SIZE) {

							model.setActiveP(piece);
						}
					}
				} else {
					// If the player is holding a piece, simulate the move
					simulate();
				}
			}

			// MOUSE BUTTON RElEASED //
			if (!mouse.pressed) {
				if (model.getActiveP() != null) {
					if (model.isValidSquare()) {

						// Move confirmed

						// Update the piece list in case a piece has been captured and removed during
						// the simulation
						model.copyPieces(ChessModel.getSimpieces(), ChessModel.getPieces());
						model.getActiveP().updatePosition();
						model.addMoveHistory(new Move(model.getActiveP(), model.getActiveP().getPreCol(), model.getActiveP().getPreRow(),
								model.getActiveP().getCol(mouse.x), model.getActiveP().getRow(mouse.y)));
						if (ChessModel.getCastlingP() != null) {
							ChessModel.getCastlingP().updatePosition();
						}
						if (model.isKingInCheck() && model.isCheckMate()) {
							model.setGameover(true);
						} else if (model.isKingInCheck() == false && model.isStalemate()) {
							model.setStalemate(true);
						} else { // the game is still going on
							if (model.canPromote()) {
								model.setPromotion(true);
							} else {
								updateMoveHistory();
								model.changePlayer();
							}
						}
					} else {
						// The move is not valid so reset everything
						model.copyPieces(ChessModel.getPieces(), ChessModel.getSimpieces());
						model.getActiveP().resetPosition();
						model.setActiveP(null);
					}
				}
			}
		}
	}

	private void simulate() {

		Piece pieceAc = model.getActiveP();

		model.setCanMove(false);
		model.setValidSquare(false);

		// Reset the piece list in every loop
		// This is basically for restoring the removed piece during the simulation
		model.copyPieces(ChessModel.getPieces(), ChessModel.getSimpieces());

		// Reset the castling piece's position
		if (ChessModel.getCastlingP() != null) {
			ChessModel.getCastlingP().setCol(ChessModel.getCastlingP().getPreCol());
			ChessModel.getCastlingP().setX(ChessModel.getCastlingP().getX(ChessModel.getCastlingP().getCol()));
			ChessModel.setCastlingP(null);
		}

		// If a piece is being held, update its position
		pieceAc.setX(mouse.x - Board.HALF_SQUARE_SIZE);
		pieceAc.setY(mouse.y - Board.HALF_SQUARE_SIZE);
		pieceAc.setCol(pieceAc.getCol(pieceAc.getX()));
		pieceAc.setRow(pieceAc.getRow(pieceAc.getY()));

		if (pieceAc.canMove(pieceAc.getCol(), pieceAc.getRow())) {
			model.setCanMove(true);

			if (model.getActiveP().getHittingP() != null) {
				ChessModel.getSimpieces().remove(model.getActiveP().getHittingP().getIndex());
			}

			model.checkCastling();

			if (model.isIllegal(model.getActiveP()) == false && model.opponentCanCaptureKing() == false) {
				model.setValidSquare(true);
			}
		}
	}

	private void promoting() {

		if (mouse.pressed) {
			for (Piece piece : model.getPromoPieces()) {
				if (piece.getCol() == mouse.x / Board.SQUARE_SIZE && piece.getRow() == mouse.y / Board.SQUARE_SIZE) {
					Piece actP = model.getActiveP();
					switch (piece.getType()) {
					case ROOK:
						ChessModel.getSimpieces().add(new Rook(model.getCurrentColor(), actP.getCol(), actP.getRow()));
						break;
					case KNIGHT:
						ChessModel.getSimpieces().add(new Knight(model.getCurrentColor(), actP.getCol(), actP.getRow()));
						break;
					case BISHOP:
						ChessModel.getSimpieces().add(new Bishop(model.getCurrentColor(), actP.getCol(), actP.getRow()));
						break;
					case QUEEN:
						ChessModel.getSimpieces().add(new Queen(model.getCurrentColor(), actP.getCol(), actP.getRow()));
						break;
					default:
						break;
					}
					ChessModel.getSimpieces().remove(actP.getIndex());
					model.copyPieces(ChessModel.getSimpieces(), ChessModel.getPieces());
					actP = null;
					model.setPromotion(false);
					model.changePlayer();
				}
			}
		}
	}

	private void updateMoveHistory() {
		moveHistoryArea.setText("");
		for (Move move : model.getMoveHistory()) {
			moveHistoryArea.append(move.toString() + "\n");
		}
		moveHistoryArea.setCaretPosition(moveHistoryArea.getDocument().getLength());
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if (o instanceof ChessModel) {
			view.repaint();
			updateMoveHistory();
		}
	}

}
