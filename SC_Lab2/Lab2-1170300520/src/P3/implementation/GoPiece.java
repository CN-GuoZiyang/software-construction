package P3.implementation;

import P3.abstraction.Piece;
import P3.abstraction.Player;

/**
 * the go piece implementation
 * 
 * @author Guo Ziyang
 */
public class GoPiece implements Piece {
	
	/**
	 * the owner of the piece
	 */
	private final Player owner;
	
	public GoPiece(final Player owner) {
		this.owner = owner;
		checkRep();
	}
	
	public void checkRep() {
		assert owner != null;
	}
	
	@Override public Player getOwner() {
		checkRep();
		return owner;
	}
	
}
