package P3.abstraction;

import P3.implementation.Position;

/**
 * the abstraction of the action a go player can take
 * 
 * @author Guo Ziyang
 */
public interface GoAction extends Action {
	
	/**
	 * place a piece on the board
	 * 
	 * @param player the player placing the piece
	 * @param piece the piece to be placed
	 * @param targetPosition the target position
	 * @return the action succeed or not
	 */
	public boolean placePiece(Player player, Piece piece, Position targetPosition);
	
	/**
	 * remove a piece from another player
	 * 
	 * @param player the player doing the action
	 * @param targetPosition the position the target piece is in
	 * @return the actio succeed or not
	 */
	public boolean removePiece(Player player, Position targetPosition);
	
}
