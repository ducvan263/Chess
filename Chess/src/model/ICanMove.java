package model;

public interface ICanMove {
	boolean canMove(Piece king, int targetCol, int targetRow);
}
