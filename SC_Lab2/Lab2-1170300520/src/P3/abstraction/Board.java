package P3.abstraction;

import P3.implementation.Position;

/**
 * the abstraction of a game board
 * 
 * @author Guo Ziyang
 */
public interface Board {
	
	/**
	 * get the Position Object of a certain position
	 * 
	 * @param x the x of the position
	 * @param y the y of the position
	 * @return the position object
	 */
	public Position getPosition(int x, int y);
	
	/**
	 * get the board's length
	 * 
	 * @return the board's length
	 */
	public int getEdgeLength();
}
