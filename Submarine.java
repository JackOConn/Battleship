public class Submarine{

    private int subCount = 0, subMax = 3;


    public boolean checkForValidShip(boolean[][] gameBoard, int x, int y) throws Exception {
        if (subCount == subMax) {
            System.out.println("** already placed all submarines **");
            throw new Exception();
        }
        if (gameBoard[x][y]) {
            System.out.println("** spot taken **");
            throw new Exception();
        } else {
            subCount++;
            gameBoard[x][y] = true;
            return true;
        }
    }

    public boolean getDeathStatus(int x, int y, String dir, Player player) {
        if (player.getBombMap()[x][y]) {
            return true;
        } else {
            return false;
        }
    }




}
