package socketRepaso;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    private ServerSocket server;
    private Socket socket;
    private int port;
    private OutputStream out;
    private InputStream in;

    public SocketServer(int port) throws IOException {
        this.port = port;
        this.server = new ServerSocket(port);
    }

    public void start() throws IOException {
        System.out.printf("Server started running in port %d\n", port);
        socket = server.accept();
        System.out.printf("Client connected in %s\n", socket.getLocalPort());
        out = socket.getOutputStream();
        in = socket.getInputStream();
    }

    public void close() throws IOException {
        System.out.printf("[Server] Closing connection at %d\n", port);
        in.close();
        out.close();
        socket.close();
        server.close();
        System.out.printf("[Server] Connection closed at %d\n", port);
    }

    public static void main(String[] args) {
        try {
            SocketServer server = new SocketServer(8081);
            server.start();
            int received;
            do {
                received = server.in.read();
                System.out.printf("Received: %d\n", received);
                server.out.write(received + 1);
                System.out.printf("Sending: %d\n", received + 1);
            } while (received != -1);
            server.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
