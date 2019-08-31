package P3.implementation;

import P3.abstraction.Board;

/**
 * the chess board implementation
 * 
 * @author Guo Ziyang
 */
public class ChessBoard implements Board {
	
	private int gridNumber;
	private Position[][] positions;
	
	public ChessBoard() {
		gridNumber = 8;
		positions = new Position[gridNumber][gridNumber];
		for(int i = 0; i < gridNumber; i ++) {
			for(int j = 0; j < gridNumber; j ++) {
				positions[i][j] = new Position(i, j);
			}
		}
		checkRep();
	}
	
	public void checkRep() {
		assert gridNumber >= 0;
		assert positions != null;
		for(int i = 0; i < gridNumber; i ++) {
			for(int j = 0; j < gridNumber; j ++) {
				assert positions[i][j] != null;
			}
		}
	}

	@Override public Position getPosition(int x, int y) {
		checkRep();
		return positions[x][y];
	}
	
	@Override public int getEdgeLength() {
		checkRep();
		return gridNumber;
	}

}
