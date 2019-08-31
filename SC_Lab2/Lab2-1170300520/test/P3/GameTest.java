package P3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import P3.abstraction.Board;
import P3.implementation.ChessGame;
import P3.implementation.ChessPlayer;
import P3.implementation.GoGame;
import P3.implementation.GoPiece;
import P3.implementation.GoPlayer;

/**
 * test the ChessGame and the GoGame
 * 
 * @author Guo Ziyang
 */
public class GameTest {
	
	/**
	 * test the chess game
	 */
	@Test
	public void testChessGame() {
		ChessGame game = new ChessGame();
		Board board = game.getBoard();

		game.setPlayerName("zhang", "wang");
		ChessPlayer[] players = { game.getPlayer(0), game.getPlayer(1) };
		game.initPiece();

		// 吃子的方法测试
		assertTrue(game.eatPiece(players[0], board.getPosition(1, 0), board.getPosition(7, 0)));
		assertFalse(game.eatPiece(players[0], board.getPosition(3, 3), board.getPosition(7, 2)));
		assertFalse(game.eatPiece(players[1], board.getPosition(1, 0), board.getPosition(5, 0)));
		assertTrue(game.eatPiece(players[1], board.getPosition(7, 5), board.getPosition(1, 3)));

		// 移动棋子的测试
		assertTrue(game.movePiece(players[0], board.getPosition(0, 2), board.getPosition(5, 0)));
		assertFalse(game.movePiece(players[0], board.getPosition(1, 0), board.getPosition(1, 1)));
		assertFalse(game.movePiece(players[1], board.getPosition(7, 5), board.getPosition(5, 0)));
		assertTrue(game.eatPiece(players[1], board.getPosition(7, 3), board.getPosition(1, 4)));
		assertFalse(game.movePiece(players[0], board.getPosition(0, 0), board.getPosition(0, 0)));
		assertTrue(game.eatPiece(players[0], board.getPosition(5, 0), board.getPosition(1, 4)));
		assertEquals("zhang: 象", board.getPosition(1, 4).toString());
		assertEquals("空" ,board.getPosition(5, 5).toString());
		
	}

	/**
	 * test the go game
	 */
	@Test
	public void testGoGame() {
		GoGame game = new GoGame();
		Board board = game.getBoard();

		game.setPlayerName("zhang", "wang");
		GoPlayer[] players = { game.getPlayer(0), game.getPlayer(1) };

		// 放置棋子的方法测试
		assertTrue(game.placePiece(players[0], new GoPiece(players[0]), board.getPosition(2, 5)));
		assertFalse(game.placePiece(players[0], new GoPiece(players[0]), board.getPosition(2, 5)));
		assertTrue(game.placePiece(players[1], new GoPiece(players[1]), board.getPosition(3, 5)));
		assertFalse(game.placePiece(players[0], new GoPiece(players[0]), board.getPosition(2, 5)));
		assertFalse(game.placePiece(players[0], board.getPosition(3, 5).getPiece(), board.getPosition(5, 5)));
		
		// 提子的测试
		assertFalse(game.removePiece(players[1], board.getPosition(3, 5)));
		assertTrue(game.removePiece(players[0], board.getPosition(3, 5)));
		assertFalse(game.removePiece(players[0], board.getPosition(4, 5)));
		assertTrue(game.removePiece(players[1], board.getPosition(2, 5)));
		assertFalse(game.removePiece(players[0], board.getPosition(3, 5)));
	}
}
