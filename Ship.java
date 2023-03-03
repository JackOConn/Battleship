public class Ship {

    private char charX;
    private int x;
    private int y;
    private int yCoord;
    private String type;
    private String dir;
    private Player player;

    public Ship(String type, char x, int y, String dir, Player player) throws Exception {
        this.charX = x;
        this.yCoord = y + 1;
        this.dir = dir;
        this.type = type;
        this.x = BattleshipEngine.changeCharToInt(x);
        this.y = y;
        this.player = player;
    }

    public boolean isDead() {
        switch (type) {
            case "submarine":
                if (player.submarine.getDeathStatus(x, y, dir, player)) {
                    return true;
                }
                return false;

            case "destroyer":
                if (player.destroyer.getDeathStatus(x, y, dir, player)) {
                    return true;
                }
                return false;

            case "cruiser":
                if (player.cruiser.getDeathStatus(x, y, dir, player)) {
                    return true;
                }
                return false;

            case "battleship":
                if (player.battleship.getDeathStatus(x, y, dir, player)) {
                    return true;
                }
                return false;

            case "carrier":
                if (player.carrier.getDeathStatus(x, y, dir, player)) {
                    return true;
                }
                return false;

            default:
                return false;
        }
    }

    public String toString() {
        return type + " @ (" + charX + ", " + yCoord + ") | ";
    }

}
