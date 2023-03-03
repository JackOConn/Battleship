public class Battleship {

    private int battleshipCount = 0, battleshipMax = 1;

    public boolean checkForValidShip(boolean[][] gameBoard, int x, int y, String direction) throws Exception {

        if (battleshipCount == battleshipMax) {
            System.out.println("** already placed all battleships **");
            throw new Exception();
        }
        if (direction.equals("Horizontal")) {
            if (gameBoard[x][y] || gameBoard[x + 1][y] || gameBoard[x + 2][y] || gameBoard[x + 3][y]) {
                System.out.println("** spot taken **");
                throw new Exception();
            } else {
                battleshipCount++;
                gameBoard[x][y] = true;
                gameBoard[x + 1][y] = true;
                gameBoard[x + 2][y] = true;
                gameBoard[x + 3][y] = true;
                return true;
            }
        } else {
            if (gameBoard[x][y] || gameBoard[x][y + 1] || gameBoard[x][y + 2] || gameBoard[x][y + 3]) {
                System.out.println("** spot taken **");
                throw new Exception();
            } else {
                battleshipCount++;
                gameBoard[x][y] = true;
                gameBoard[x][y + 1] = true;
                gameBoard[x][y + 2] = true;
                gameBoard[x][y + 3] = true;
                return true;
            }
        }

    }

    public boolean getDeathStatus(int x, int y, String dir, Player player) {

        if (dir.equals("Horizontal")) {
            if (player.getBombMap()[x][y] && player.getBombMap()[x + 1][y] && player.getBombMap()[x + 2][y]
                    && player.getBombMap()[x + 3][y]) {
                return true;
            }
        } else {
            if (player.getBombMap()[x][y] && player.getBombMap()[x][y + 1] && player.getBombMap()[x][y + 2]
                    && player.getBombMap()[x][y + 3]) {
                return true;
            }
        }

        return false;

    }
}
