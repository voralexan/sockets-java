package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MainClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 28000;

    private static Socket clientSocket;
    private static Scanner in;
    private static PrintWriter out;
    static MainForm clientWindow;
    public static void main(String[] args) {
        try {
            clientSocket = new Socket(SERVER_HOST, SERVER_PORT);
            in = new Scanner(clientSocket.getInputStream());
            out = new PrintWriter(clientSocket.getOutputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        clientWindow = new MainForm();
        while (true) {
            if (in.hasNext()) {
                String inMsg = in.nextLine();
                switch (inMsg) {
                    case "1":
                        clientWindow.setPlayerFirst(true);
                        break;

                    case "2":
                        clientWindow.setPlayerFirst(false);
                        break;

                    case "start":
                        clientWindow.start();
                        break;

                    default:
                        String[] data = inMsg.split("/");
                        int x = Integer.parseInt(data[0]);
                        int y = Integer.parseInt(data[1]);
                        int playerNum = Integer.parseInt(data[2]);
                        clientWindow.move(x, y, playerNum);
                        break;
                }
            }
        }
    }

    public static void sendMsg(String msg) {
        out.println(msg);
        out.flush();
    }

}