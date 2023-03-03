/*
	Jack O'Connor

	COS 161, summer 2021, Prof.Amorelli

	Project 4

	File Name: BattleshipEngine.java
*/


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.*;

public class BattleshipEngine {

	static DrawingPanel panel = new DrawingPanel(1920, 1920);
	static Graphics g = panel.getGraphics();
	static Player playerOne = new Player("player1");
	static Player playerTwo = new Player("player2");
	static Random random = new Random();
	static boolean gameOver = false;

	public static void main(String[] args) throws InterruptedException {
		System.out.println("\n\n  ** Welcome to BATTLESHIP! **\n");
		System.out.println(
				"to make your moves use the format: \n\n <SHIP NAME> <A-J> <1-10> <H/V> \n\n (H = horizontal, V = vertical)\n");
		System.out.println("--------------------------------\n");
		displayBoard();
		fillBoard();
		playGame(0);
		playerOne.updateBoard();
		playerTwo.updateBoard();
	}

	public static void fillBoard() throws InterruptedException {

		Scanner scnr = new Scanner(System.in);
		String currPlayer = "player1";
		boolean validMove;
		// loop 8 times twice for each player to choose their ships positions
		for (int i = 0; i < 2; i++) {
			if (i == 1) {
				// after player 1's choices wait 3 seconds then hide ships and move terminal
				// down
				Thread.sleep(3000);
				playerOne.setHideShips(true);
				playerOne.updateBoard();
				System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
			}
			for (int j = 0; j < 8; j++) {
				validMove = false;
				if (i == 1) {
					currPlayer = "player2";
				}
				while (!validMove) {
					try {
						System.out.print(currPlayer + " | make your move: ");
						String type = scnr.next();
						char x = scnr.next().charAt(0);
						int y = scnr.nextInt();
						char dir = scnr.next().charAt(0);
						y--;

						if (currPlayer == "player1") {
							playerOne.makeNewShip(type, x, y, dir, j);
							playerOne.updateBoard();
							validMove = true;
						} else {
							playerTwo.makeNewShip(type, x, y, dir, j);
							playerTwo.updateBoard();
							validMove = true;
						}

					} catch (Exception e) {
						scnr.nextLine();
					}
				}
				// print empty line for readability in terminal
				System.out.println();

				if (i == 1 && j == 7) {
					// after player 2's choices wait 3 seconds then hide ships and move terminal
					// down
					Thread.sleep(3000);
					playerTwo.setHideShips(true);
					playerTwo.updateBoard();
					System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
				}
			}
		}
	}

	public static void takeTurn(Player player) {
		Scanner scnr = new Scanner(System.in);
		boolean validMove = false;

		while (!validMove) {
			try {
				System.out.print(player.getName() + " | place your bomb: ");
				char xCoord = scnr.next().charAt(0);
				int x = changeCharToInt(xCoord);
				int y = scnr.nextInt();
				// y-- to make it work with internal array structure
				y--;
				if (player.equals(playerOne)) {
					// check if bomb is a hit or miss, then update board and move to next turn
					checkForHit(playerTwo, x, y);
					playerTwo.updateBoard();
					validMove = true;
				} else {
					checkForHit(playerOne, x, y);
					playerOne.updateBoard();
					validMove = true;
				}

			} catch (Exception e) {
				scnr.nextLine();
			}
		}
		// for readability in the terminal
		System.out.println("-------------Next Turn-------------\n");
		System.out.println();
		player.printSunkenShips();
		System.out.println();

	}

	public static boolean hasAliveShips(Player player) {
		int aliveShips = 0;

		// loop through all player's ships to see if there are any alive
		for (int i = 0; i < 8; i++) {
			Ship currShip = player.getShipsArr()[i];
			if (!currShip.isDead()) {
				aliveShips++;
			} else {
				// add to sunkenShips array if it's not already in it
				if (!player.getSunkenShips().contains(currShip)) {
					player.getSunkenShips().add(currShip);
				}
				System.out.println();
				// if player's sunkenShips array is full, then they have no alive ships left
				if (player.getSunkenShips().size() == 8) {
					return false;
				}
			}
		}
		// if there are any alive ships return true
		if (aliveShips > 0) {
			return true;
		}

		return false;
	}

	public static void checkForHit(Player player, int x, int y) throws Exception {
		if (player.getGameBoard()[x][y] && !player.getBombMap()[x][y]) {
			// if there is a ship and not already a bomb on it, successful hit and place
			// bomb
			System.out.println("\n   " + player.getName() + "'s ship was HIT!\n\n");
			player.getBombMap()[x][y] = true;
		} else if (player.getBombMap()[x][y]) {
			System.out.println("** bomb is already placed there **");
			throw new Exception();
		} else {
			System.out.println("\nbomb MISSED! " + player.getName() + "'s ships\n\n");
			player.getBombMap()[x][y] = true;
		}
	}

	public static boolean playGame(int turnCount) {
		boolean playerOneHasShips = hasAliveShips(playerOne);
		boolean playerTwoHasShips = hasAliveShips(playerTwo);
		System.out.println();
		if (!playerOneHasShips && playerTwoHasShips && turnCount % 2 == 1) {
			// if player one has no alive ships and player two does and its player two's
			// turn, player two wins
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
			System.out.println("** Player 2 Wins **");
			gameOver = true;
			return true;
		} else if (!playerTwoHasShips && playerOneHasShips && turnCount % 2 == 0) {
			// if player two has no alive ships and player one does and its the start of a
			// new round, player one wins
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
			System.out.println("** Player 1 Wins **");
			gameOver = true;
			return true;
		} else if (!playerOneHasShips && !playerTwoHasShips && turnCount % 2 == 0) {
			// if both player's have no ships remaining at the start of a round, draw
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
			System.out.println("** Draw **");
			gameOver = true;
			return true;
		}
		if (turnCount % 2 == 0) {
			takeTurn(playerOne);
			playGame(turnCount + 1);
		} else {
			takeTurn(playerTwo);
			playGame(turnCount + 1);
		}

		return false;

	}

	public static void displayBoard() {
		// clear board
		g.setColor(Color.WHITE);
		g.fillRect(1, 1, 600, 600);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Courier", Font.PLAIN, 62));
		// left side
		g.drawString("A B C D E F G H I J", 122, 80);
		g.drawString("1                 ", 47, 155);
		g.drawString("2                 ", 47, 230);
		g.drawString("3                 ", 47, 305);
		g.drawString("4                 ", 47, 380);
		g.drawString("5                 ", 47, 455);
		g.drawString("6                 ", 47, 530);
		g.drawString("7                 ", 47, 605);
		g.drawString("8                 ", 47, 680);
		g.drawString("9                 ", 47, 755);
		g.drawString("10                 ", 27, 830);
		// right side
		g.drawString("A B C D E F G H I J", 1060, 80);
		g.drawString("1                 ", 985, 155);
		g.drawString("2                 ", 985, 230);
		g.drawString("3                 ", 985, 305);
		g.drawString("4                 ", 985, 380);
		g.drawString("5                 ", 985, 455);
		g.drawString("6                 ", 985, 530);
		g.drawString("7                 ", 985, 605);
		g.drawString("8                 ", 985, 680);
		g.drawString("9                 ", 985, 755);
		g.drawString("10                 ", 965, 830);

		g.setFont(new Font("Courier", Font.BOLD, 25));
		g.drawString("Player 1's Board", 367, 920);
		g.drawString("Player 2's Board", 1307, 920);

		// Draws left board's squares
		playerOne.updateBoard();
		// Draws right board's squares
		playerTwo.updateBoard();

	}

	public static int changeCharToInt(char character) throws Exception {
		//needed a couple times when taking input
		switch (character) {
			case 'A':
				return 0;
			case 'B':
				return 1;
			case 'C':
				return 2;
			case 'D':
				return 3;
			case 'E':
				return 4;
			case 'F':
				return 5;
			case 'G':
				return 6;
			case 'H':
				return 7;
			case 'I':
				return 8;
			case 'J':
				return 9;
			default:
				System.out.println("** input outside range (A-J) **");
				throw new Exception();
		}
	}

}