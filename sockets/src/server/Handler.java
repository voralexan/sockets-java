package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Handler implements Runnable {
    private Server server;
    private PrintWriter out;
    private Scanner in;

    public Handler(Socket socket, Server server) {
        try {
            this.server = server;
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream());

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (in.hasNext()) {
                    String clientMessage = in.nextLine();
                    server.send(clientMessage);
                }
                Thread.sleep(1000);
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void send(String msg) {
        try {
            out.println(msg);
            out.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
