package P3.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import P3.abstraction.Board;
import P3.abstraction.Game;
import P3.abstraction.GoAction;
import P3.abstraction.Piece;
import P3.abstraction.Player;

/**
 * the go game implementation
 * 
 * @author Guo Ziyang
 */
public class GoGame implements Game, GoAction{
	
	private Board board;
	private Player[] players;
	private List<String> history;
	
	public GoGame() {
		board = new GoBoard();
		players = new Player[2];
		history = new ArrayList<>();
		checkRep();
	}
	
	public void checkRep() {
		assert board != null;
		assert players != null;
		assert history != null;
	}
	
	@Override public void setPlayerName(String player1, String player2) {
		checkRep();
		players[0] = new GoPlayer(player1);
		players[1] = new GoPlayer(player2);
	}
	
	@Override public GoPlayer getPlayer(int player) {
		checkRep();
		return (GoPlayer) players[player];
	}
	
	@Override public GoBoard getBoard() {
		checkRep();
		return (GoBoard) board;
	}
	
	@Override public boolean placePiece(Player player, Piece piece, Position targetPosition) {
		checkRep();
		if(player != piece.getOwner()) {
			System.out.println("该棋子并非该棋手所有");
			return false;
		}
		if(targetPosition.getPiece() != null) {
			System.out.println("目标位置已有其它棋子");
			return false;
		}
		history.add(player.getPlayerName() + " 将棋子放置在(" + targetPosition.getX() + "," + targetPosition.getY() + ")处");
		targetPosition.setPiece(piece);
		return true;
	}

	@Override public boolean removePiece(Player player, Position targetPosition) {
		checkRep();
		if(targetPosition.getPiece() == null) {
			System.out.println("该位置无棋子可提");
			return false;
		}
		if(player == targetPosition.getPiece().getOwner()) {
			System.out.println("所提棋子不是对方棋子");
			return false;
		}
		history.add(player.getPlayerName() + " 提去放置在(" + targetPosition.getX() + "," + targetPosition.getY() + ")的对方棋子");
		targetPosition.removePiece();
		return true;
	}
	
	@Override public void showHistory() {
		checkRep();
		System.out.println(history.stream().collect(Collectors.joining("\n")));
	}
}
