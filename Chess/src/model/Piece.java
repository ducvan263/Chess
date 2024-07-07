package model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Piece {

	private TypePieces type;
	private BufferedImage image;
	private int x, y;
	private int col, row, preCol, preRow;
	private int color;
	private Piece hittingP;
	private boolean moved, twoStepped;
	protected ICanMove canMove;
	
	public Piece(int color, int col, int row) {
		this.color = color;
		this.col = col;
		this.row = row;
		x = getX(col);
		y = getY(row);
		preCol = col;
		preRow = row;
	}

	public BufferedImage getImage(String imagePath) {
		
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	public int getX(int col) {
		return col * Board.SQUARE_SIZE;
	}

	public int getY(int row) {
		return row * Board.SQUARE_SIZE;
	}

	public int getCol(int x) {
		return (x + Board.HALF_SQUARE_SIZE) / Board.SQUARE_SIZE;
	}

	public int getRow(int y) {
		return (y + Board.HALF_SQUARE_SIZE) / Board.SQUARE_SIZE;
	}
	
	public boolean canMove(int targetCol, int targetRow) {
		return canMove.canMove(this, targetCol, targetRow);
	}
	
	public boolean isWhithinBoard(int targetRow, int targetCol) {
		if (targetCol >= 0 && targetCol <= 7 && targetRow >= 0 && targetRow <= 7) {
			return true;
		}
		return false;
	}
	
	public void updatePosition() {
		
		// To check En Passant
		if(type == TypePieces.PAWN) {
			if(Math.abs(row - preRow) == 2) {
				setTwoStepped(true);
			}
		}
		
		x = getX(col);
		y = getY(row);
		preCol = getCol(x);
		preRow = getRow(y);
		setMoved(true);
	}
	
	public void resetPosition() {
		col = preCol;
		row = preRow;
		x = getX(col);
		y = getY(row);
	}
	
	public Piece getHittingP(int targetCol, int targetRow) {
		for (Piece piece : ChessModel.getSimpieces()) {
			if (piece.col == targetCol && piece.row == targetRow && piece != this) {
				return piece;
			}
		}
		return null;
	}
	
	public boolean isValidSquare(int targetCol, int targetRow) {
		hittingP = getHittingP(targetCol, targetRow);
		if (hittingP == null) { // This square is Vacant
			return true;
		} else { // This square is Occupied
			if (hittingP.color != this.color) { // If the color is different, it can be captured
				return true;
			} else {
				hittingP = null;
			}
		}
		return false;
	}
	
	public boolean isSameSquare(int targetCol, int targetRow) {
		if (targetRow == preRow && targetCol == preCol) {
			return true;
		}
		return false;
	}
	
	public boolean pieceIsOnStraightLine(int targetCol, int targetRow) {
		// moving to the left
		for (int c = preCol - 1; c > targetCol; c--) {
			for (Piece p : ChessModel.getSimpieces()) {
				if (p.col == c && p.row == targetRow) {
					hittingP = p;
					return true;
				}
			}
		}
		// moving to the right
		for (int c = preCol + 1; c < targetCol; c++) {
			for (Piece p : ChessModel.getSimpieces()) {
				if (p.col == c && p.row == targetRow) {
					hittingP = p;
					return true;
				}
			}
		}
		// moving up
		for (int r = preRow - 1; r > targetRow; r--) {
			for (Piece p : ChessModel.getSimpieces()) {
				if (p.row == r && p.col == targetCol) {
					hittingP = p;
					return true;
				}
			}
		}
		// moving down
		for (int r = preRow + 1; r < targetRow; r++) {
			for (Piece p : ChessModel.getSimpieces()) {
				if (p.row == r && p.col == targetCol) {
					hittingP = p;
					return true;
				}
			}
		}
		return false;
	}

	public boolean pieceIsOnDiagonalLine(int targetCol, int targetRow) {

		if (targetRow < preRow) {
			// up - left
			for (int c = preCol - 1; c > targetCol; c--) {
				int diff = Math.abs(c - preCol);
				for (Piece p : ChessModel.getSimpieces()) {
					if (p.col == c && p.row == preRow - diff) {
						hittingP = p;
						return true;
					}
				}
			}
			// up - right
			for (int c = preCol + 1; c < targetCol; c++) {
				int diff = Math.abs(c - preCol);
				for (Piece p : ChessModel.getSimpieces()) {
					if (p.col == c && p.row == preRow - diff) {
						hittingP = p;
						return true;
					}
				}
			}
		}

		if (targetRow > preRow) {
			// down - left
			for (int c = preCol - 1; c > targetCol; c--) {
				int diff = Math.abs(c - preCol);
				for (Piece p : ChessModel.getSimpieces()) {
					if (p.col == c && p.row == preRow + diff) {
						hittingP = p;
						return true;
					}
				}
			}
			// down - right
			for (int c = preCol + 1; c < targetCol; c++) {
				int diff = Math.abs(c - preCol);
				for (Piece p : ChessModel.getSimpieces()) {
					if (p.col == c && p.row == preRow + diff) {
						hittingP = p;
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public int getIndex() {
		for (int index = 0; index < ChessModel.getSimpieces().size(); index++) {
			if(ChessModel.getSimpieces().get(index) == this) {
				return index;
			}
		}
		return 0;
	}
	
	public void draw(Graphics2D g2) {
		g2.drawImage(image, x, y, Board.SQUARE_SIZE, Board.SQUARE_SIZE, null);
	}

	public TypePieces getType() {
		return type;
	}

	public void setType(TypePieces type) {
		this.type = type;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getPreCol() {
		return preCol;
	}

	public void setPreCol(int preCol) {
		this.preCol = preCol;
	}

	public int getPreRow() {
		return preRow;
	}

	public void setPreRow(int preRow) {
		this.preRow = preRow;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public Piece getHittingP() {
		return hittingP;
	}

	public void setHittingP(Piece hittingP) {
		this.hittingP = hittingP;
	}

	public boolean isMoved() {
		return moved;
	}

	public void setMoved(boolean moved) {
		this.moved = moved;
	}

	public boolean isTwoStepped() {
		return twoStepped;
	}

	public void setTwoStepped(boolean twoStepped) {
		this.twoStepped = twoStepped;
	}
}
