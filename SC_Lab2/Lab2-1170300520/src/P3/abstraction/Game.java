package P3.abstraction;

/**
 * the abstraction of a game
 * 
 * @author Guo Ziyang
 */
public interface Game {
	/**
	 * set the player's name
	 * 
	 * @param player1 player1's name
	 * @param player2 player2's name
	 */
	public void setPlayerName(String player1, String player2);
	
	/**
	 * get the given Player Object
	 * 
	 * @param player the player index
	 * @return player object
	 */
	public Player getPlayer(int player);
	
	/**
	 * get the board of the game
	 * 
	 * @return the board
	 */
	public Board getBoard();
	
	/**
	 * show the history of the map
	 */
	public void showHistory();
}
