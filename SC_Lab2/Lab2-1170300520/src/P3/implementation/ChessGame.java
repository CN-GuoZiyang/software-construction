package P3.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import P3.abstraction.Board;
import P3.abstraction.ChessAction;
import P3.abstraction.Game;
import P3.abstraction.Piece;
import P3.abstraction.Player;

/**
 * the chess game implementation
 * 
 * @author Guo Ziyang
 */
public class ChessGame implements Game, ChessAction{
	
	private Board board;
	private Player[] players;
	private List<String> history;
	
	public ChessGame() {
		board = new ChessBoard();
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
		players[0] = new ChessPlayer(player1);
		players[1] = new ChessPlayer(player2);
		initPiece();
	}
	
	@Override public ChessPlayer getPlayer(int player) {
		checkRep();
		return (ChessPlayer) players[player];
	}
	
	@Override public ChessBoard getBoard() {
		checkRep();
		return (ChessBoard) board;
	}
	
	@Override public boolean movePiece(Player player, Position sourcePosition, Position targetPosition) {
		checkRep();
		if(sourcePosition == targetPosition) {
			System.out.println("两个位置相同！");
			return false;
		}
		if(sourcePosition.getPiece() == null) {
			System.out.println("初始位置尚无可移动的棋子");
			return false;
		}
		if(targetPosition.getPiece() != null) {
			System.out.println("目的位置已有其他棋子");
			return false;
		}
		if(sourcePosition.getPiece() != null && player != sourcePosition.getPiece().getOwner()) {
			System.out.println("初始位置的棋子并非该棋手所有");
			return false;
		}
		history.add(player.getPlayerName() + " 将棋子 " + sourcePosition.getPiece().toString() + "从(" + sourcePosition.getX() + "," + sourcePosition.getY() + ")移动到了(" + targetPosition.getX() + "," + targetPosition.getY() + ")");
		targetPosition.setPiece(sourcePosition.getPiece());
		sourcePosition.removePiece();
		return true;
	}

	@Override public boolean eatPiece(Player player, Position sourcePosition, Position targetPosition) {
		checkRep();
		if(sourcePosition.getPiece() == null) {
			System.out.println("初始位置无棋子");
			return false;
		}
		if(targetPosition.getPiece() == null) {
			System.out.println("目标位置无棋子");
			return false;
		}
		if(sourcePosition == targetPosition) {
			System.out.println("两个位置相同");
			return false;
		}
		if(player != sourcePosition.getPiece().getOwner()) {
			System.out.println("初始位置的棋子并非该棋手所有");
			return false;
		}
		if(player == targetPosition.getPiece().getOwner()) {
			System.out.println("目标位置的棋子并非对手棋手所有");
			return false;
		}
		history.add(player.getPlayerName() + " 将棋子" + sourcePosition.getPiece().toString() + "从(" + sourcePosition.getX() + "," + sourcePosition.getY() + ")移动到了(" + targetPosition.getX() + "," + targetPosition.getY() + ")，吃掉了对手的" + targetPosition.getPiece().toString());
		targetPosition.setPiece(sourcePosition.getPiece());
		sourcePosition.removePiece();
		return true;
	}
	
	/**
	 * init the piece of the chess board
	 */
	public void initPiece() {
		checkRep();
		for(int player = 0; player < 2; player ++) {
			Piece rook1 = new ChessPiece(players[player], ChessPieceType.ROOK);
			Piece rook2 = new ChessPiece(players[player], ChessPieceType.ROOK);
			board.getPosition(7 * player, 0).setPiece(rook1);
			board.getPosition(7 * player, 7).setPiece(rook2);
			Piece knight1 = new ChessPiece(players[player], ChessPieceType.KNIGHT);
			Piece knight2 = new ChessPiece(players[player], ChessPieceType.KNIGHT);
			board.getPosition(7 * player, 1).setPiece(knight1);
			board.getPosition(7 * player, 6).setPiece(knight2);
			Piece bishop1 = new ChessPiece(players[player], ChessPieceType.BISHOP);
			Piece bishop2 = new ChessPiece(players[player], ChessPieceType.BISHOP);
			board.getPosition(7 * player, 2).setPiece(bishop1);
			board.getPosition(7 * player, 5).setPiece(bishop2);
			Piece king = new ChessPiece(players[player], ChessPieceType.KING);
			board.getPosition(7 * player, 3).setPiece(king);
			Piece queen = new ChessPiece(players[player], ChessPieceType.QUEEN);
			board.getPosition(7 * player, 4).setPiece(queen);
			for(int i = 0; i < 8; i ++) {
				Piece pawn = new ChessPiece(players[player], ChessPieceType.PAWN);
				board.getPosition(1 + 5 * player, i).setPiece(pawn);
			}
		}
	}
	
	@Override public void showHistory() {
		checkRep();
		System.out.println(history.stream().collect(Collectors.joining("\n")));
	}
	
}
