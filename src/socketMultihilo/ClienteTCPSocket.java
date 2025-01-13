package socketMultihilo;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClienteTCPSocket {

    private InputStream is;
    private Socket socket;
    String serverIP;
    int serverPort;
    public ClienteTCPSocket(String serverIp, int serverPort) {
        super();
        this.serverIP = serverIp;
        this.serverPort = serverPort;
    }

    public void start() throws UnknownHostException, IOException {
        System.out.printf("[Cliente] Estableciendo conexión socket ...%n");
        socket = new Socket(serverIP, serverPort);
        System.out.printf("[Cliente %s:%d] Conexión socket establecida ...%n",
                serverIP, serverPort);
        is = socket.getInputStream();
    }

    public void stop() throws IOException {
        System.out.printf("[Cliente %s:%d] Cerrando conexión socket ...%n",
                serverIP, serverPort);
        is.close();
        socket.close();
        System.out.printf("[Cliente %s:%d] Conexión socket cerrada.%n",
                serverIP, serverPort);
    }

    public static void main(String[] args) {
        ClienteTCPSocket cliente = new ClienteTCPSocket("127.0.0.1", 8081);

        try {
            cliente.start();

            int datoRecibido = cliente.is.read();
            System.out.printf("[Cliente %s:%d] Mensaje recibido: %d%n",
                    cliente.serverIP, cliente.serverPort, datoRecibido);
            cliente.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}