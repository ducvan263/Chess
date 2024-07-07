package view;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import controller.ChessController;
import model.Board;
import model.ChessModel;
import model.Piece;

public class ChessView extends JPanel {

	public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
	
//	public static final int WIDTH = 1100;
//	public static final int HEIGHT = 800;
	public ChessModel model;
	public ChessController controller;

	public ChessView(ChessController controller) {

		this.model = ChessModel.getInstance();
		this.controller = controller;

		addMouseListener(controller.mouse);
		addMouseMotionListener(controller.mouse);

		setPreferredSize(new Dimension(WIDTH, HEIGHT));
//		setBackground(Color.black);
		setBackground(Color.darkGray);

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		model.drawBoard(g2);
		model.drawPieces(g2);

		if (model.getActiveP() != null) {
			if (model.isCanMove()) {
				if (model.isIllegal(model.getActiveP()) || model.opponentCanCaptureKing()) {
					g2.setColor(Color.gray);
					g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
					g2.fillRect(model.getActiveP().getCol() * Board.SQUARE_SIZE,
							model.getActiveP().getRow() * Board.SQUARE_SIZE, Board.SQUARE_SIZE, Board.SQUARE_SIZE);
					g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
				} else {
					g2.setColor(Color.white);
					g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
					g2.fillRect(model.getActiveP().getCol() * Board.SQUARE_SIZE,
							model.getActiveP().getRow() * Board.SQUARE_SIZE, Board.SQUARE_SIZE, Board.SQUARE_SIZE);
					g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
				}
			}

			// Draw the activeP in the end so it won't be hidden by the board or the colored
			// square
			model.getActiveP().draw(g2);
		}

		// STATUS MESSAGES
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setFont(new Font("Book Antiqua", Font.PLAIN, 30));
		g2.setColor(Color.white);

		if (model.isPromotion()) {
			g2.drawString("Promote to: ", 620, 50);
			for (Piece piece : model.getPromoPieces()) {
				g2.drawImage(piece.getImage(), piece.getX(piece.getCol()), piece.getY(piece.getRow()),
						Board.SQUARE_SIZE, Board.SQUARE_SIZE, null);
			}
		} else {
			if (model.getCurrentColor() == ChessModel.getWhite()) {
				g2.drawString("White's turn", 620, 450);
				if (model.getCheckingP() != null
						&& model.getCheckingP().getColor() == ChessModel.getBlack()) {
					g2.setColor(Color.red);
					g2.drawString("The King", 640, 500);
					g2.drawString("is in check!", 640, 550);
				}
			} else {
				g2.drawString("Black's turn", 620, 150);
				if (model.getCheckingP() != null
						&& model.getCheckingP().getColor() == ChessModel.getWhite()) {
					g2.setColor(Color.red);
					g2.drawString("The King", 640, 200);
					g2.drawString("is in check!", 640, 250);
				}
			}
		}

		if (model.isGameover()) {
			String s = "";
			if (model.getCurrentColor() == ChessModel.getWhite()) {
				s = "White Wins";
			} else {
				s = "Black Wins";
			}
			g2.setFont(new Font("Arial", Font.PLAIN, 60));
			g2.setColor(Color.green);
			g2.drawString(s, 100, 320);
		}

		if (model.isStalemate()) {
			g2.setFont(new Font("Arial", Font.PLAIN, 60));
			g2.setColor(Color.lightGray);
			g2.drawString("Stalemate", 100, 320);
		}
	}

}
