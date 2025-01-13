package socketTexto;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServerText {

    private final ServerSocket serverSocket;
    private Socket socket;
    private int port;
    private InputStream in;
    private OutputStream out;
    private InputStreamReader isr;
    private BufferedReader br;
    private PrintWriter pw;

    public SocketServerText(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.port = port;
        while(true) {
            Socket socket = serverSocket.accept();
            System.out.println("[Server] Connection established");
            new GestorProcesos(socket).start();
        }
    }

    public void start() throws IOException{
            System.out.printf("[Server] Started running in port %d\n", port);
            socket = serverSocket.accept();
            System.out.printf("[Server] Client connected in %s\n", socket.getLocalPort());
            in = socket.getInputStream();
            out = socket.getOutputStream();
    }

    public void stop() throws IOException{
        System.out.printf("[Server] Closing connection at %d\n", port);
        in.close();
        out.close();
        socket.close();
        serverSocket.close();
        System.out.printf("[Server] Connection closed at %d\n", port);
    }

    public void openTextChannels(){
        System.out.println("[Server] Opening text channels");
        pw = new PrintWriter(out, true);
        isr = new InputStreamReader(in);
        br = new BufferedReader(isr);
        System.out.println("[Server] Text channels opened");
    }

    public void closeTextChannels() throws IOException{
        System.out.println("[Server] Closing text channels");
        pw.close();
        br.close();
        isr.close();
        System.out.println("[Server] Text channels closed");
    }

    private void run(){
        try {
            start();
            openTextChannels();
            String received;
            boolean isFinished;
            do{
                received = br.readLine();
                isFinished = received.equalsIgnoreCase("fin");
                if(!isFinished){
                    System.out.printf("[Server] Received: %s\n", received);
                    pw.println(received.toUpperCase());
                    System.out.printf("[Server] Sending: %s\n", received.toUpperCase());
                }else {
                    System.out.println("[Server] Recieved finishing message");
                }
            }while(!isFinished);
            closeTextChannels();
            stop();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        SocketServerText server;
        try {
            server = new SocketServerText(8081);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
