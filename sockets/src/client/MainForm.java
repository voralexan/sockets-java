package client;

import javax.swing.*;
import java.awt.*;

public class MainForm extends JFrame {

    private final GameField gameField;
    private final JLabel playerNumber;
    private final JLabel move;
    private final JLabel wait;

    public MainForm() {

        setTitle("Game");
        setBounds(300, 300, 475, 525);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        gameField = GameField.getInstance();
        add(gameField, BorderLayout.CENTER);

        JPanel panel = new JPanel(new GridLayout());
        add(panel, BorderLayout.SOUTH);
        playerNumber = new JLabel("Player - ");
        move = new JLabel("");
        panel.add(playerNumber);
        panel.add(move);
        wait = new JLabel("Another player turn");
        panel.add(wait);
        setVisible(true);
    }

    public void setPlayerFirst(boolean first) {
        if (first) {
            gameField.setPlayerNum(0);
            playerNumber.setText("Player - " + 1);
            move.setText("Your turn");
        } else {
            gameField.setPlayerNum(1);
            playerNumber.setText("Player - " + 2);
            move.setText("Opponent's turn");
        }
    }

    public void start() {
        gameField.newGame();
        wait.setVisible(false);
    }

    public void move(int x, int y, int playerNum) {
        gameField.setCoord(x, y);
        gameField.game(playerNum);

        if (gameField.getPlayer() == 0 & gameField.ready() != 0)
            move.setText("Your turn");
        if (gameField.getPlayer() == 1 & gameField.ready() == 0)
            move.setText("Opponent's turn");

        if (gameField.getPlayer() == 1 & gameField.ready() != 0)
            move.setText("Your turn");
        if (gameField.getPlayer() == 0 & gameField.ready() == 0)
            move.setText("Opponent's turn");
    }
}
