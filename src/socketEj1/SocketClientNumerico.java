package socketEj1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketClientNumerico {
    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private int port;
    private String address;

    public SocketClientNumerico(int port, String address) {
        this.port = port;
        this.address = address;
    }

    public void start() throws IOException {
        System.out.println("[Client] Abriendo conexión...");
        socket = new Socket(address, port);
        in = socket.getInputStream();
        out = socket.getOutputStream();
        System.out.println("[Client] Conexión establecida!");
    }

    public void stop() throws IOException {
        System.out.println("[Client] Terminando...");
        in.close();
        out.close();
        socket.close();
        System.out.println("[Client] Terminado!");
    }

    public static void main(String[] args) {
        SocketClientNumerico client = new SocketClientNumerico(8081, "127.0.0.1");
        try {
            client.start();
            int datoAEnviar = 0;
            for (int i = 0; i < 255; i++) {
                datoAEnviar = i;
                client.out.write(datoAEnviar);
                System.out.printf("[Client] Enviando %d a %s:%d...%n", datoAEnviar, client.address, client.port);
                int datoRecibido = client.in.read();
                System.out.printf("[Client] Se ha recibido el dato %d de %s:%d %n", datoRecibido, client.address, client.port);

            }
            client.stop();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
