package socketMultihilo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketTCP {
    private final ServerSocket serverSocket;

    ServerSocketTCP(int puerto) throws IOException {
        serverSocket = new ServerSocket(puerto);
        while(true) {
            Socket socket = serverSocket.accept();
            System.out.println("(Server) Conexión establecida.");
            new GestorProcesos(socket).start();
        }
    }

    public void stop() throws IOException {
        serverSocket.close();
    }

    public static void main(String[] args) {
        try {
            ServerSocketTCP servidor = new ServerSocketTCP(8081);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}