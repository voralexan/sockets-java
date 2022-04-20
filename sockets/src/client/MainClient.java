package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MainClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 28000;

    private static Socket sock;
    private static Scanner in;
    private static PrintWriter out;
    static ClientWindow cW;
    public static void main(String[] args) {
        try {
            sock = new Socket(SERVER_HOST, SERVER_PORT);
            in = new Scanner(sock.getInputStream());
            out = new PrintWriter(sock.getOutputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        cW = new ClientWindow();
        while (true) {
            if (in.hasNext()) {
                String inMsg = in.nextLine();
                switch (inMsg) {
                    case "1":
                        cW.setPlayerFirst(true);
                        break;

                    case "2":
                        cW.setPlayerFirst(false);
                        break;

                    case "start":
                        cW.start();
                        break;

                    default:
                        String[] in = inMsg.split("/");
                        cW.move(Integer.parseInt(in[0]), Integer.parseInt(in[1]), Integer.parseInt(in[2]));
                        break;
                }
            }
        }
    }

    public static void send(String m) {
        out.println(m);
        out.flush();
    }

}