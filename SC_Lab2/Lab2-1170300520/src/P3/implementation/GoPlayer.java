package P3.implementation;

import P3.abstraction.Player;

/**
 * the go player implementation
 * 
 * @author Guo Ziyang
 */
public class GoPlayer implements Player{
	
	private final String playerName;
	
	public GoPlayer(final String playerName) {
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
