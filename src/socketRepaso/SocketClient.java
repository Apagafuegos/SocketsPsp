package socketRepaso;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;

public class SocketClient {
    private int port;
    private InputStream in;
    private OutputStream out;
    private Socket socket;
    private String address;

    public SocketClient(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public void start() throws IOException {
        System.out.printf("[Client] Starting client in %s:%d\n", address, port);
        socket = new Socket(address, port);
        System.out.printf("[Client] Connected to server in %s:%d\n", address, port);
        in = socket.getInputStream();
        out = socket.getOutputStream();
    }

    public void stop() throws IOException{
        System.out.printf("[Client] Closing connection to server in %s:%d\n", address, port);
        in.close();
        out.close();
        socket.close();
        System.out.printf("[Client] Connection closed at %s:%d\n", address, port);
    }

    public static void main(String[] args) {
        SocketClient cliente = new SocketClient("localhost", 8081);
        startAndSend(cliente);
    }

    private static void startAndSend(SocketClient cliente) {
        try {
            cliente.start();
            int env = Random.from(() -> new Random().nextInt(255)).nextInt();
            do{
                System.out.printf("[Client] Sending value %d\n", env);
                cliente.out.write(env);
                env = cliente.in.read();
            }while(env < 255);
            cliente.stop();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
