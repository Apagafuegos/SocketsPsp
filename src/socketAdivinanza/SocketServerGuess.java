package socketAdivinanza;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServerGuess {
    private final ServerSocket server;
    private Socket socket;
    private final int port;
    private OutputStream out;
    private InputStream in;
    private int numberGuess;

    public SocketServerGuess(int port) throws IOException {
        this.port = port;
        this.server = new ServerSocket(port);
    }

    public void start() throws IOException {
        System.out.printf("[Server] started running in port %d\n", port);
        numberGuess = (int) (Math.random() * 100);
        System.out.printf("[Server] Number to guess generated!: %d\n", numberGuess);
        socket = server.accept();
        System.out.printf("[Server] Client connected in %d\n", socket.getPort());
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
            SocketServerGuess server = new SocketServerGuess(8081);
            server.start();
            int received;
            boolean isFinished;
            do {
                received = server.in.read();
                System.out.printf("[Server] Received: %d\n", received);
                //101 para menor, 102 para adivinado, 103 para mayor
                isFinished = received == server.numberGuess;
                if (!isFinished){
                    int numberToSend = received > server.numberGuess ? 101 : 103;
                    System.out.printf("[Server] Sending: %d\n", numberToSend);
                    server.out.write(numberToSend);
                }else{
                    System.out.println("[Server] Number guessed!\n");
                    server.out.write(102);
                }
            } while (!isFinished);
            server.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
