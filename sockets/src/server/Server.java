package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private static final int PORT = 28000;
    private final ArrayList<Handler> clients = new ArrayList<Handler>();

    public Server() {
        Socket clientSocket = null;
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server initialized");

            while (true) {
                clientSocket = serverSocket.accept();
                Handler client = new Handler(clientSocket, this);
                clients.add(client);

                if (clients.size() == 2) {
                    new Thread(clients.get(0)).start();
                    clients.get(0).send("1");
                    new Thread(clients.get(1)).start();
                    clients.get(1).send("2");
                    send("start");
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                clientSocket.close();
                serverSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
    }

    public void send(String message) {
        for (Handler handle : clients)
            handle.send(message);
    }
}
