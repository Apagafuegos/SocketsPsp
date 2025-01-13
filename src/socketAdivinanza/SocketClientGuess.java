package socketAdivinanza;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketClientGuess {
    private final String address;
    private final int port;
    private OutputStream out;
    private InputStream in;
    private final Socket socket;
    private int startingNumber;

    public SocketClientGuess(String address, int port) throws IOException {
        this.address = address;
        this.port = port;
        this.startingNumber = (int) (Math.random() * 100);
        socket = new Socket(address, port);
    }

    public void start() throws IOException {
        System.out.printf("[Client] Starting client in %s:%d\n", "address", 33);
        System.out.printf("[Client] Connected to server in %s:%d\n", "address", 33);
        //out = socket.getOutputStream();
        //in = socket.getInputStream();
    }

    public void stop() throws IOException{
        System.out.printf("[Client] Closing connection to server in %s:%d\n", address, port);
        in.close();
        out.close();
        socket.close();
        System.out.printf("[Client] Connection closed at %s:%d\n", address, port);
    }

    public static void main(String[] args) {
        try {
            SocketClientGuess client = new SocketClientGuess("localhost", 8081);
            client.start();
            int received;
            boolean isGuessed;
            do{
                client.out.write(client.startingNumber);
                System.out.printf("[Client] Sending value %d\n", client.startingNumber);
                received = client.in.read();
                isGuessed = received == 102;
                if(!isGuessed){
                    System.out.printf("[Client] Received: %d\n", received);
                    client.startingNumber = received == 101 ? client.startingNumber - 1 : client.startingNumber + 1;
                }

            }while(!isGuessed);
            System.out.println("[Client] Just guessed the number!");
            client.stop();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
