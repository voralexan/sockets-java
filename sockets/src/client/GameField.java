package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static client.MainClient.sendMsg;

public class GameField extends JPanel {
    private static final int FIELD_SIZE = 456;
    private static final int linesCount = 19;
    private static final int countToWin = 6;
    private static GameField instance = null;
    private final String NOT_A_SIGN = "*";
    private final Player[] players = new Player[2];
    public String[][] cell;
    private boolean end = false;
    private String endMessage = "";
    private int cellSize;
    private int x, y;
    private int turn;

    private GameField() {
        setVisible(false);
        players[0] = new Player("X");
        players[1] = new Player("O");
        players[0].isReady = 1;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                x = e.getX() / cellSize;
                y = e.getY() / cellSize;

                sendMsg(x + "/" + y + "/" + turn);
            }
        });
    }

    public static synchronized GameField getInstance() {
        if (instance == null)
            instance = new GameField();
        return instance;
    }

    public void setCoord(int _x, int _y) {
        x = _x;
        y = _y;
    }

    public int getPlayer() {
        return turn;
    }

    public void setPlayerNum(int num) {
        turn = num;
    }

    void newGame() {
        end = false;
        endMessage = "";
        cellSize = FIELD_SIZE / linesCount;
        cell = new String[linesCount][linesCount];
        repaint();
        for (int i = 0; i < linesCount; i++) {
            for (int j = 0; j < linesCount; j++) {
                cell[i][j] = NOT_A_SIGN;
            }
        }
        setVisible(true);
    }

    public int ready() {
        return players[turn].isReady;
    }

    private boolean spotIsEmpty(int x, int y) {
        return cell[x][y].equals(NOT_A_SIGN);
    }

    public void game(int playerNum) {
        int anotherPlayerNum = 2;
        switch (playerNum) {
            case 0:
                anotherPlayerNum = 1;
                break;
            case 1:
                anotherPlayerNum = 0;
                break;
        }

        if (players[playerNum].isReady > 0 & players[anotherPlayerNum].isReady == 0)
            if (spotIsEmpty(x, y)) {
                players[playerNum].set(x, y);
                players[playerNum].isReady--;
                if (players[playerNum].isReady == 0)
                    players[anotherPlayerNum].isReady = 2;
            }

        if (players[playerNum].win()) {
            end = true;
            endMessage = "Player #" + (playerNum + 1) + " WIN!";
        }

        repaint();
    }

    boolean isCellTaken(int x, int y) {
        if (x < 0 || y < 0 || x > linesCount - 1 || y > linesCount - 1) {
            return false;
        }
        return !cell[x][y].equals(NOT_A_SIGN);
    }

    private boolean checkLine(int start_x, int start_y, int dx, int dy, String sign) {
        int count = 0;
        for (int i = 0; i < countToWin; i++)
            if (cell[start_x + i * dx][start_y + i * dy].equals(sign)) {
                count++;
                if (count == countToWin)
                    return true;
            }
        return false;
    }

    public boolean checkWin(String sign) {
        for (int i = 0; i <= linesCount - countToWin; i++)
            for (int j = 0; j < linesCount; j++) {
                // строки
                if (checkLine(i, j, 1, 0, sign)) return true;
            }

        for (int i = 0; i < linesCount; i++)
            for (int j = 0; j <= linesCount - countToWin; j++) {
                // столбцы
                if (checkLine(i, j, 0, 1, sign)) return true;
            }

        for (int i = 0; i <= linesCount - countToWin; i++)
            for (int j = 0; j <= linesCount - countToWin; j++) {
                // y=x
                if (checkLine(i, j, 1, 1, sign)) return true;
            }

        for (int i = countToWin - 1; i < linesCount; i++)
            for (int j = 0; j <= linesCount - countToWin; j++) {
                // y=-x
                if (checkLine(i, j, -1, 1, sign)) return true;
            }

        return false;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        for (int i = 0; i <= linesCount; i++) {
            graphics.drawLine(0, i * this.cellSize, FIELD_SIZE, i * this.cellSize);
            graphics.drawLine(i * this.cellSize, 0, i * this.cellSize, FIELD_SIZE);
        }
        for (int i = 0; i < linesCount; i++) {
            for (int j = 0; j < linesCount; j++) {
                if (!cell[i][j].equals(NOT_A_SIGN)) {
                    if (cell[i][j].equals("X")) {
                        graphics.setColor(Color.BLACK);
                        graphics.fillOval(i * cellSize, j * cellSize, cellSize, cellSize);
                    }
                    if (cell[i][j].equals("O")) {
                        graphics.setColor(Color.BLUE);
                        graphics.fillOval((i * cellSize), (j * cellSize), cellSize, cellSize);
                    }
                }
            }
        }

        if (end) {
            graphics.setColor(Color.BLACK);
            graphics.fillRect(0, FIELD_SIZE / 2, FIELD_SIZE, FIELD_SIZE / 8);
            graphics.setColor(Color.RED);
            graphics.drawString(endMessage, 0, 19 * FIELD_SIZE / 32);
        }
    }
}
