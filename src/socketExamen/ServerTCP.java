package socketExamen;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP {

    private ServerSocket serverSocket;
    private int port;
    private static long acceptedSockets = 0;

    public ServerTCP(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.port = port;
    }

    public static long getAcceptedSockets() {
        return acceptedSockets;
    }

    public static void main(String[] args) {
        ServerTCP server = null;
        try {
            server = new ServerTCP(8081);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        while (true){
            Socket socket = null;
            try {
                socket = server.serverSocket.accept();
                System.out.println("[Server] Socket aceptado");
                acceptedSockets++;
                new Thread(new GestorSocketServer(socket)).start();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}
