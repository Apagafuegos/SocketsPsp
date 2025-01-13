package socketTipoExamen;

import java.io.IOException;
import java.net.ServerSocket;

public class SocketServer {
    private ServerSocket server;
    private int port;


    public SocketServer(int port) throws IOException {
        this.server = new ServerSocket(port);
        this.port = port;
    }


    public static void main(String[] args) {
        SocketServer server = null;
        try {
            server = new SocketServer(8081);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        while (true){
            try {
                new Thread(new GestorSocketServer(server.server.accept())).start();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}
