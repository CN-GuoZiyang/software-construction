package P3;

import java.util.Scanner;

import P3.abstraction.Game;
import P3.abstraction.Piece;
import P3.implementation.ChessGame;
import P3.implementation.GoGame;
import P3.implementation.GoPiece;

/**
 * the client of the chess and go game
 * 
 * @author Guo Ziyang
 */
public class MyChessAndGoGame {
	
	/**
	 * the name of the chess name
	 */
	public static final String CHESS_STRING = "chess";
	/**
	 * the name of the go game
	 */
	public static final String GO_STRING = "go";

	public static Game game;
	public static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.print("请选择游戏类型（chess 或 go）：");
		while (true) {
			String gameTypeString = scanner.next();
			if (CHESS_STRING.equals(gameTypeString)) {
				game = new ChessGame();
				break;
			} else if (GO_STRING.equals(gameTypeString)) {
				game = new GoGame();
				break;
			} else {
				System.out.print("无此游戏类型！请重新选择：");
			}
		}
		System.out.println("请输入两个玩家的名字：");
		String player1 = scanner.next();
		String player2 = scanner.next();
		game.setPlayerName(player1, player2);
		System.out.println("开始游戏！");
		gameMenu();
	}

	/**
	 * the menu of the game
	 */
	public static void gameMenu() {
		game: while (true) {
			cross: for (int i = 0; i < 2; i++) {
				System.out.println("\n当前是" + game.getPlayer(i).getPlayerName() + "的回合");
				if (game.getClass().equals(GoGame.class)) {
					System.out.println("1. 放置新的棋子");
					System.out.println("2. 提子");
				} else {
					System.out.println("1. 移动棋子");
					System.out.println("2. 吃子");
				}
				System.out.println("3. 查询占用");
				System.out.println("4. 计算棋子总数");
				System.out.println("5. 跳过本轮");
				System.out.print("你的选择：");
				scanner = new Scanner(System.in);
				String choice = scanner.nextLine();
				switch (choice) {
				case "1":
					if (game.getClass().equals(GoGame.class)) {
						do {
							System.out.print("请输入新棋子的位置（x 与 y）：");
							int x = scanner.nextInt();
							int y = scanner.nextInt();
							if (x >= game.getBoard().getEdgeLength() || y >= game.getBoard().getEdgeLength()) {
								System.out.println("指定的位置超出棋盘的范围");
								continue;
							}
							if (((GoGame) game).placePiece(game.getPlayer(i), new GoPiece(game.getPlayer(i)),
									game.getBoard().getPosition(x, y))) {
								break;
							} else {
								continue;
							}
						} while (true);
					} else {
						do {
							System.out.print("请输入被移动棋子的位置（x 与 y）：");
							int sourceX = scanner.nextInt();
							int sourceY = scanner.nextInt();
							if (sourceX >= game.getBoard().getEdgeLength()
									|| sourceY >= game.getBoard().getEdgeLength()) {
								System.out.println("指定的位置超出棋盘的范围");
								continue;
							}
							System.out.print("请输入目标位置（x 与 y）：");
							int targetX = scanner.nextInt();
							int targetY = scanner.nextInt();
							if (targetX >= game.getBoard().getEdgeLength()
									|| targetY >= game.getBoard().getEdgeLength()) {
								System.out.println("指定的位置超出棋盘的范围");
								continue;
							}
							if (((ChessGame) game).movePiece(game.getPlayer(i),
									game.getBoard().getPosition(sourceX, sourceY),
									game.getBoard().getPosition(targetX, targetY))) {
								break;
							} else {
								continue;
							}
						} while (true);
					}
					break;
				case "2":
					if (game.getClass().equals(GoGame.class)) {
						do {
							System.out.print("请输入被提棋子的位置（x 与 y）：");
							int x = scanner.nextInt();
							int y = scanner.nextInt();
							if (x >= game.getBoard().getEdgeLength() || y >= game.getBoard().getEdgeLength()) {
								System.out.println("指定的位置超出棋盘的范围");
								continue;
							}
							if (((GoGame) game).removePiece(game.getPlayer(i), game.getBoard().getPosition(x, y))) {
								break;
							} else {
								continue;
							}
						} while (true);
					} else {
						do {
							System.out.print("请输入你的棋子的位置（x 与 y）：");
							int sourceX = scanner.nextInt();
							int sourceY = scanner.nextInt();
							if (sourceX >= game.getBoard().getEdgeLength()
									|| sourceY >= game.getBoard().getEdgeLength()) {
								System.out.println("指定的位置超出棋盘的范围");
								continue;
							}
							System.out.print("请输入对方棋子的位置（x 与 y）：");
							int targetX = scanner.nextInt();
							int targetY = scanner.nextInt();
							if (targetX >= game.getBoard().getEdgeLength()
									|| targetY >= game.getBoard().getEdgeLength()) {
								System.out.println("指定的位置超出棋盘的范围");
								continue;
							}
							if (((ChessGame) game).eatPiece(game.getPlayer(i),
									game.getBoard().getPosition(sourceX, sourceY),
									game.getBoard().getPosition(targetX, targetY))) {
								break;
							} else {
								continue;
							}
						} while (true);
					}
					break;
				case "3":
					do {
						System.out.print("请输入你想查询的位置（x 与 y）：");
						int x = scanner.nextInt();
						int y = scanner.nextInt();
						if (x >= game.getBoard().getEdgeLength() || y >= game.getBoard().getEdgeLength()) {
							System.out.println("指定的位置超出棋盘的范围");
							continue;
						}
						System.out.println(game.getBoard().getPosition(x, y).toString());
						break;
					} while (true);
					break;
				case "4":
					int player0 = 0;
					int player1 = 0;
					for (int x = 0; x < game.getBoard().getEdgeLength(); x++) {
						for (int y = 0; y < game.getBoard().getEdgeLength(); y++) {
							Piece piece = game.getBoard().getPosition(x, y).getPiece();
							if (piece == null) {
								continue;
							} else {
								if (piece.getOwner() == game.getPlayer(0)) {
									player0++;
								} else {
									player1++;
								}
							}
						}
					}
					System.out.println("当前棋盘状况：");
					System.out.println("玩家" + game.getPlayer(0).getPlayerName() + "共有棋子" + player0 + "颗");
					System.out.println("玩家" + game.getPlayer(1).getPlayerName() + "共有棋子" + player1 + "颗");
					break;
				case "5":
					continue cross;
				case "end":
					break game;
				default:
					System.out.println("请重新选择！");
					i--;
					break;
				}
			}
		}
		System.out.print("游戏结束！是否要查看走棋历史（y or n）？");
		scanner = new Scanner(System.in);
		String choice = scanner.next();
		switch(choice) {
		case "y":
			game.showHistory();
			break;
		case "n":
			
		default:
			;
		}
	}
}
