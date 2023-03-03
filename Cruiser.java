public class Cruiser {

    private int cruiserCount = 0, cruiserMax = 1;

    public boolean checkForValidShip(boolean[][] gameBoard, int x, int y, String direction) throws Exception {
        if (cruiserCount == cruiserMax) {
            System.out.println("** already placed all cruisers **");
            throw new Exception();
        }
        if (direction.equals("Horizontal")) {
            if (gameBoard[x][y] || gameBoard[x + 1][y] || gameBoard[x + 2][y]) {
                System.out.println("** spot taken **");
                throw new Exception();
            } else {
                cruiserCount++;
                gameBoard[x][y] = true;
                gameBoard[x + 1][y] = true;
                gameBoard[x + 2][y] = true;
                return true;
            }
        } else {
            if (gameBoard[x][y] || gameBoard[x][y + 1] || gameBoard[x][y + 2]) {
                System.out.println("** spot taken **");
                throw new Exception();
            } else {
                cruiserCount++;
                gameBoard[x][y] = true;
                gameBoard[x][y + 1] = true;
                gameBoard[x][y + 2] = true;
                return true;
            }
        }
    }

    public boolean getDeathStatus(int x, int y, String dir, Player player) {

        if (dir.equals("Horizontal")) {
            if (player.getBombMap()[x][y] && player.getBombMap()[x + 1][y] && player.getBombMap()[x + 2][y]) {
                return true;
            }
        } else {
            if (player.getBombMap()[x][y] && player.getBombMap()[x][y + 1] && player.getBombMap()[x][y + 2]) {
                return true;
            }
        }

        return false;

    }
}
