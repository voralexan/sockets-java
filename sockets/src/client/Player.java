package client;

public class Player {
    int isReady = 0;
    private final String sign;
    private GameField gameField;

    public Player(String sign) {
        this.sign = sign;
    }

    boolean set(int x, int y) {
        gameField = GameField.getInstance();
        if (!gameField.isCellTaken(x, y)) {
            gameField.cell[x][y] = sign;
            return true;
        }
        return false;
    }


    boolean win() {
        gameField = GameField.getInstance();
        return gameField.checkWin(this.sign);
    }
}
