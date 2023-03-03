import java.awt.Color;
import java.util.ArrayList;

public class Player {

    private String direction;
    private String playerName;
    private boolean[][] gameBoard = new boolean[10][10];
    private boolean[][] bombMap = new boolean[10][10];
    private ArrayList<Ship> sunkenShips = new ArrayList<Ship>();
    private Ship[] shipsArr = new Ship[8];
    private boolean hideShips = false;
    Submarine submarine = new Submarine();
    Destroyer destroyer = new Destroyer();
    Cruiser cruiser = new Cruiser();
    Battleship battleship = new Battleship();
    Carrier carrier = new Carrier();

    public Player(String name) {
        playerName = name;
    }

    public void makeNewShip(String type, char xCoord, int y, char dir, int count) throws Exception {
        type.toLowerCase();
        xCoord = Character.toUpperCase(xCoord);
        dir = Character.toUpperCase(dir);
        int x = BattleshipEngine.changeCharToInt(xCoord);

        if (dir == 'H') {
            direction = "Horizontal";
        } else if (dir == 'V') {
            direction = "Vertical";
        } else {
            System.out.println("** incorrect direction **");
            throw new Exception();
        }

        switch (type) {

            case "submarine":
                submarine.checkForValidShip(gameBoard, x, y);
                break;

            case "destroyer":
                destroyer.checkForValidShip(gameBoard, x, y, direction);
                break;

            case "cruiser":
                cruiser.checkForValidShip(gameBoard, x, y, direction);
                break;

            case "battleship":
                battleship.checkForValidShip(gameBoard, x, y, direction);
                break;

            case "carrier":
                carrier.checkForValidShip(gameBoard, x, y, direction);
                break;

            default:
                System.out.println("** incorrect ship name **");
                throw new Exception();
        }

        // passed all the tests, make new ship object
        if (playerName.equals("player1")) {
            shipsArr[count] = new Ship(type, xCoord, y, direction, BattleshipEngine.playerOne);
        } else {
            shipsArr[count] = new Ship(type, xCoord, y, direction, BattleshipEngine.playerTwo);
        }

    }

    public void printSunkenShips() {
        System.out.print("| " + playerName + "'s sunken ships: ");
        for (int i = 0; i < sunkenShips.size(); i++) {
            System.out.print(sunkenShips.get(i).toString());
        }
    }

    // color for water background
    Color sky_blue = new Color(135, 206, 235);

    public void updateBoard() {
        int x = 100;
        if (playerName.equals("player2")) {
            x = 1038;
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (BattleshipEngine.gameOver) {
                    // if game is over, reveal all ships with bombs and misses
                    if (gameBoard[i][j]) {
                        BattleshipEngine.g.setColor(Color.GRAY);
                        BattleshipEngine.g.fillRect(x + 75 * i, 100 + 75 * j, 75, 75);
                    }
                    if (bombMap[i][j] && gameBoard[i][j]) {
                        BattleshipEngine.g.setColor(Color.RED);
                        BattleshipEngine.g.fillOval(25 + x + 75 * i, 25 + 100 + 75 * j, 25, 25);
                    }
                    if (!gameBoard[i][j] && bombMap[i][j]) {
                        BattleshipEngine.g.setColor(Color.BLACK);
                        BattleshipEngine.g.fillOval(25 + x + 75 * i, 25 + 100 + 75 * j, 25, 25);
                    }
                } else {
                    if (gameBoard[i][j] && !hideShips) {
                        // if hideShips is false, show ships
                        BattleshipEngine.g.setColor(Color.GRAY);
                        BattleshipEngine.g.fillRect(x + 75 * i, 100 + 75 * j, 75, 75);
                    } else if (gameBoard[i][j] && bombMap[i][j] && hideShips) {
                        // if there is a HIT
                        BattleshipEngine.g.setColor(Color.RED);
                        BattleshipEngine.g.fillOval(25 + x + 75 * i, 25 + 100 + 75 * j, 25, 25);
                    } else if (!gameBoard[i][j] && bombMap[i][j] && hideShips) {
                        // if there is a MISS
                        BattleshipEngine.g.setColor(Color.BLACK);
                        BattleshipEngine.g.fillOval(25 + x + 75 * i, 25 + 100 + 75 * j, 25, 25);
                    } else {
                        BattleshipEngine.g.setColor(sky_blue);
                        BattleshipEngine.g.fillRect(x + 75 * i, 100 + 75 * j, 75, 75);
                    }
                }
                BattleshipEngine.g.setColor(Color.BLACK);
                BattleshipEngine.g.drawRect(x + 75 * i, 100 + 75 * j, 75, 75);
            }
        }
    }

    public void setHideShips(boolean bool) {
        hideShips = bool;
    }

    public Ship[] getShipsArr() {
        return shipsArr;
    }

    public String getName() {
        return playerName;
    }

    public boolean[][] getGameBoard() {
        return gameBoard;
    }

    public boolean[][] getBombMap() {
        return bombMap;
    }

    public ArrayList<Ship> getSunkenShips() {
        return sunkenShips;
    }

}
