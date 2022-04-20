package client;

import javax.swing.*;
import java.awt.*;

public class ClientWindow extends JFrame {


    private final JLabel PN, m, w;
    private final ConnectF connectF;

    public ClientWindow() {

        connectF = ConnectF.retPointer();
        add(connectF, BorderLayout.CENTER);

        setBounds(300, 300, 475, 525);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        JPanel p = new JPanel(new GridLayout());
        add(p, BorderLayout.SOUTH);
        PN = new JLabel("Player - ");
        m = new JLabel("");
        w = new JLabel("Another player turn");
        p.add(PN);
        p.add(m);
        p.add(w);
        setVisible(true);
    }

    public void setPlayerFirst(boolean first) {
        if (first) {
            connectF.setTurn(0);
            PN.setText("Player - " + 1);
            m.setText("Your turn");
        } else {
            connectF.setTurn(1);
            PN.setText("Player - " + 2);
            m.setText("Opponent's turn");
        }
    }

    public void start() {
        connectF.newGame();
        w.setVisible(false);
    }

    public void move(int row, int column, int PN) {
        connectF.setCord(row, column);
        connectF.process(PN);

        if (connectF.getTurn() == 0 & connectF.ready() != 0)
            m.setText("Your turn");
        if (connectF.getTurn() == 1 & connectF.ready() == 0)
            m.setText("Opponent's turn");

        if (connectF.getTurn() == 1 & connectF.ready() != 0)
            m.setText("Your turn");
        if (connectF.getTurn() == 0 & connectF.ready() == 0)
            m.setText("Opponent's turn");
    }
}
