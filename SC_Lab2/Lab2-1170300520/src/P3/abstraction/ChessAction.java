package P3.abstraction;

import P3.implementation.Position;

/**
 * the abstraction of the action a chess player can take
 * 
 * @author Guo Ziyang
 */
public interface ChessAction extends Action {
	
	/**
	 * move a piece from the source position to another
	 * 
	 * @param player the player doing the action
	 * @param sourcePosition the position of your piece
	 * @param targetPosition the target position
	 * @return the action succeed or not
	 */
	public boolean movePiece(Player player, Position sourcePosition, Position targetPosition);
	
	/**
	 * eat a piece using the player's own piece
	 * 
	 * @param player the player doing the action
	 * @param sourcePosition the position of your piece
	 * @param targetPosition the position of the target piece
	 * @return
	 */
	public boolean eatPiece(Player player, Position sourcePosition, Position targetPosition);
}
