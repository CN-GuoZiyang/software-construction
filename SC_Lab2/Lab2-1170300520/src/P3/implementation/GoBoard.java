package P3.implementation;

import P3.abstraction.Board;

/**
 * the go board implementation
 * 
 * @author Guo Ziyang
 */
public class GoBoard implements Board{
	private int crossNumber;
	private Position[][] positions;
	
	public GoBoard() {
		crossNumber = 19;
		positions = new Position[crossNumber][crossNumber];
		for(int i = 0; i < crossNumber; i ++) {
			for(int j = 0; j < crossNumber; j ++) {
				positions[i][j] = new Position(i, j);
			}
		}
		checkRep();
	}
	
	public void checkRep() {
		assert crossNumber > 0;
		assert positions != null;
		for(int i = 0; i < crossNumber; i ++) {
			for(int j = 0; j < crossNumber; j ++) {
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
		return crossNumber;
	}
}
