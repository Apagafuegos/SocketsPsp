package socketEj1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServerNumerico {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private InputStream in;
    private OutputStream out;
    private int port;

    public SocketServerNumerico(int port) throws IOException {
        this.port = port;
        this.serverSocket = new ServerSocket(port);
    }

    public void start() throws IOException {
        System.out.println("[Server] Esperando conexión...");
        clientSocket = serverSocket.accept();
        System.out.println("[Server] Conexión establecida!");
        in = clientSocket.getInputStream();
        out = clientSocket.getOutputStream();
    }

    public void stop() throws IOException {
        System.out.println("[Server] Terminando conexión...");
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
        System.out.println("[Server] Conexión cerrada!");
    }

    public static void main(String[] args) {
        SocketServerNumerico server;
        try {
            server = new SocketServerNumerico(8081);
            server.start();
            while(true){
                int datoLeido = server.in.read();
                System.out.printf("[Server] Recibido el dato %d%n", datoLeido);
                if(datoLeido == -1){
                    server.stop();
                    break;
                }
                int datoADevolver = datoLeido + 1;
                System.out.printf("[Server] Se envía el dato %d%n", datoADevolver);
                server.out.write(datoADevolver);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
