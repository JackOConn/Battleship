public class Destroyer {

    private int destroyerCount = 0, destroyerMax = 2;

    public boolean checkForValidShip(boolean[][] gameBoard, int x, int y, String direction) throws Exception {
        if (destroyerCount == destroyerMax) {
            System.out.println("** already placed all destroyers **");
            throw new Exception();
        }
        if (direction.equals("Horizontal")) {
            if (gameBoard[x][y] || gameBoard[x + 1][y]) {
                System.out.println("** spot taken **");
                throw new Exception();
            } else {
                destroyerCount++;
                gameBoard[x][y] = true;
                gameBoard[x + 1][y] = true;
                return true;
            }
        } else {
            if (gameBoard[x][y] || gameBoard[x][y + 1]) {
                System.out.println("** spot taken **");
                throw new Exception();
            } else {
                destroyerCount++;
                gameBoard[x][y] = true;
                gameBoard[x][y + 1] = true;
                return true;
            }
        }
    }

    public boolean getDeathStatus(int x, int y, String dir, Player player) {

        if (dir.equals("Horizontal")) {
            if (player.getBombMap()[x][y] && player.getBombMap()[x + 1][y]) {
                return true;
            }
        } else {
            if (player.getBombMap()[x][y] && player.getBombMap()[x][y + 1]) {
                return true;
            }
        }

        return false;

    }
}
