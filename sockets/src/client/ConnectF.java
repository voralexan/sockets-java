package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static client.MainClient.send;

public class ConnectF extends JPanel {
    private static final int TOTAL_F = 456;
    private static final int total = 19;
    private static final int winCondition = 6;
    private static ConnectF instance = null;
    private final String NOT_A_SIGN = "*";
    private final User[] pl = new User[2];
    public String[][] c;
    private boolean end = false;
    private String endMessage = "";
    private int cz;
    private int row, column;
    private int t;

    public void setCord(int _row, int _column) {
        row = _row;
        column = _column;
    }

    public int getTurn() {
        return t;
    }

    public void setTurn(int turn) {
        t = turn;
    }


    private ConnectF() {
        setVisible(false);
        pl[0] = new User("1");
        pl[0].rdy = 1;

        pl[1] = new User("2");




        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                super.mouseClicked(event);
                row = event.getX() / cz;
                column = event.getY() / cz;

                send(row + "/" + column + "/" + t);
            }
        });
    }

    public static synchronized ConnectF retPointer() {
        if (instance == null)
            instance = new ConnectF();
        return instance;
    }


    public int ready() {
        return pl[t].rdy;
    }

    private boolean checkPoint(int row, int column) {
        return c[row][column].equals(NOT_A_SIGN);
    }

    void newGame() {
        end = false;
        endMessage = "";
        cz = TOTAL_F / total;
        c = new String[total][total];
        repaint();
        for (int i = 0; i < total; i++) {
            for (int j = 0; j < total; j++) {
                c[i][j] = NOT_A_SIGN;
            }
        }
        setVisible(true);
    }


    private boolean verify(int row_s, int column_s, int row_f, int column_f, String sign) {
        int number = 0;
        for (int i = 0; i < winCondition; i++)
            if (c[row_s + i * row_f][column_s + i * column_f].equals(sign)) {
                number++;

                if (number == winCondition)
                    return true;
            }

        return false;
    }

    public void process(int PN) {
        int aN = 2;
        switch (PN) {
            case 0:
                aN = 1;
                break;
            case 1:
                aN = 0;
                break;
        }

        if (pl[PN].rdy > 0 & pl[aN].rdy == 0)
            if (checkPoint(row, column)) {
                pl[PN].set(row, column);
                pl[PN].rdy--;
                if (pl[PN].rdy == 0)
                    pl[aN].rdy = 2;
            }

        if (pl[PN].win()) {
            end = true;
            endMessage = "Player #" + (PN + 1) + " WIN!";
        }

        repaint();
    }

    boolean isBusy(int row, int column) {
        if (row < 0 || column < 0 || row > total - 1 || column > total - 1) {
            return false;
        }
        return !c[row][column].equals(NOT_A_SIGN);
    }


    public boolean verifyEnd(String point) {
        for (int row = 0; row <= total - winCondition; row++)
            for (int col = 0; col <= total - winCondition; col++) {
                if (verify(row, col, 1, 1, point)) return true;
            }

        for (int row = winCondition - 1; row < total; row++)
            for (int col = 0; col <= total - winCondition; col++) {
                if (verify(row, col, -1, 1, point)) return true;
            }


        for (int row = 0; row <= total - winCondition; row++)
            for (int col = 0; col < total; col++) {
                if (verify(row, col, 1, 0, point)) return true;
            }

        for (int row = 0; row < total; row++)
            for (int col = 0; col <= total - winCondition; col++) {
                if (verify(row, col, 0, 1, point)) return true;
            }


        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i <= total; i++) {
            g.drawLine(0, i * this.cz, TOTAL_F, i * this.cz);
            g.drawLine(i * this.cz, 0, i * this.cz, TOTAL_F);
        }

        for (int i = 0; i < total; i++) {
            for (int j = 0; j < total; j++) {

                if (!c[i][j].equals(NOT_A_SIGN)) {
                    if (c[i][j].equals("1"))
                        g.setColor(Color.YELLOW);
                    else
                        g.setColor(Color.RED);

                    g.fillRect(i * cz, j * cz, cz, cz);
                }
            }
        }

        if (end) {
            g.setColor(Color.BLACK);
            g.drawString(endMessage, 0, 19 * TOTAL_F / 32);
        }
    }
}
