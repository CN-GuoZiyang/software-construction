package P3.implementation;

import P3.abstraction.Piece;

/**
 * the abstraction of a position on board
 * 
 * @author Guo Ziyang
 */
public class Position {
	
	private Piece piece;
	/**
	 * the x of the position
	 */
	private final int x;
	/**
	 * the y of the position
	 */
	private final int y;
	
	public Position(final int x, final int y) {
		this.x = x;
		this.y = y;
	}
	
	public void checkRep() {
		assert x >= 0;
		assert y >= 0;
	}
	
	/**
	 * set the piece on the position
	 * 
	 * @param piece the piece to be replaced
	 */
	public void setPiece(Piece piece) {
		this.piece = piece;
	}
	
	/**
	 * get the piece on the position
	 * 
	 * @return the piece on the position
	 */
	public Piece getPiece() {
		return piece;
	}
	
	/**
	 * remove the piece on the position
	 */
	public void removePiece() {
		this.piece = null;
	}
	
	@Override public String toString() {
		if(piece == null) {
			return "空";
		} else {
			return piece.toString();
		}
	}

	/**
	 * get the x of the position
	 * 
	 * @return the x of the position
	 */
	public int getX() {
		return x;
	}

	/**
	 * get the y of the position
	 * 
	 * @return the y of the position
	 */
	public int getY() {
		return y;
	}
}
