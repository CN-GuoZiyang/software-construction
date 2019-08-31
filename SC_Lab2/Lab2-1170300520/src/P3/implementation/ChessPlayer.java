package P3.implementation;

import P3.abstraction.Player;

/**
 * the chess player implementation
 * 
 * @author Guo Ziyang
 */
public class ChessPlayer implements Player{
	
	/**
	 * the name of the player
	 */
	private final String playerName;
	
	public ChessPlayer(final String playerName) {
		this.playerName = playerName;
		checkRep();
	}
	
	public void checkRep() {
		assert playerName != null;
	}
	
	@Override public String getPlayerName() {
		checkRep();
		return playerName;
	}

}
