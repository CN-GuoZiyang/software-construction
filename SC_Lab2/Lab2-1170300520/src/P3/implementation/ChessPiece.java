package P3.implementation;

import P3.abstraction.Piece;
import P3.abstraction.Player;

/**
 * the chess piece implementation
 * 
 * @author Guo Ziyang
 */
public class ChessPiece implements Piece {
	
	/**
	 * the owner of the player
	 */
	private final Player owner;
	/**
	 * the type of the piece type
	 */
	private final ChessPieceType chessPieceType;
	
	public ChessPiece(final Player owner, final ChessPieceType chessPieceType) {
		this.owner = owner;
		this.chessPieceType = chessPieceType;
		checkRep();
	}
	
	public void checkRep() {
		assert owner != null;
		assert chessPieceType != null;
	}
	
	@Override public Player getOwner() {
		checkRep();
		return owner;
	}
	
	@Override public String toString() {
		checkRep();
		String pieceTypeString = null;
		if(chessPieceType.equals(ChessPieceType.BISHOP)) {
			pieceTypeString = "象";
		} else if(chessPieceType.equals(ChessPieceType.KING)) {
			pieceTypeString = "国王";
		} else if(chessPieceType.equals(ChessPieceType.KNIGHT)) {
			pieceTypeString = "马";
		} else if(chessPieceType.equals(ChessPieceType.PAWN)) {
			pieceTypeString = "兵";
		} else if(chessPieceType.equals(ChessPieceType.QUEEN)) {
			pieceTypeString = "王后";
		} else {
			pieceTypeString = "车";
		}
		return owner.getPlayerName() + ": " + pieceTypeString;
	}
	
}
