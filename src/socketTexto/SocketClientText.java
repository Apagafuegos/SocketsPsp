package socketTexto;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SocketClientText {

    private String address;
    private int port;
    private InputStream in;
    private OutputStream os;
    private Socket socket;
    private PrintWriter pw;
    private InputStreamReader isr;
    private BufferedReader br;

    public SocketClientText(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public void start() throws IOException{
        System.out.printf("[Client] Starting client in %s:%d\n", address, port);
        socket = new Socket(address, port);
        System.out.printf("[Client] Connected to server in %s:%d\n", address, port);
        in = socket.getInputStream();
        os = socket.getOutputStream();
    }

    public void stop() throws IOException{
        System.out.printf("[Client] Closing connection to server in %s:%d\n", address, port);
        in.close();
        os.close();
        socket.close();
        System.out.printf("[Client] Connection closed at %s:%d\n", address, port);
    }

    public void openTextChannels(){
        System.out.println("[Client] Opening text channels");
        pw = new PrintWriter(os, true);
        isr = new InputStreamReader(in);
        br = new BufferedReader(isr);
        System.out.println("[Client] Text channels opened");
    }

    public void closeTextChannels() throws IOException{
        System.out.println("[Client] Closing text channels");
        pw.close();
        br.close();
        isr.close();
        System.out.println("[Client] Text channels closed");
    }

    public static void main(String[] args) {
        SocketClientText client = new SocketClientText("localhost", 8081);
        //client.loopClient();
        client.inputClient();
    }

    private void loopClient() {
        try{
            start();
            openTextChannels();
            String message;
            for (int i = 0; i < 200; i++) {
                message = ("<datomandado>" + (i + 1));
                System.out.printf("[Client] Sent %s message to server %n", message);
                pw.println(message);
                String received = br.readLine();
                System.out.printf("[Client] Received message from server: %s%n", received);
            }
            message = "fin";
            System.out.println("[Client] Sending final message to server");
            pw.println(message);
            closeTextChannels();
            stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    private void inputClient(){
        Scanner sc = new Scanner(System.in);
        try {
            start();
            openTextChannels();
            String message = "";
            boolean isFinished = false;
            while (!isFinished){
                System.out.println("[Client] Enter a message to send to server or 'fin' to finish");
                message = sc.nextLine();
                isFinished = message.equalsIgnoreCase("fin");
                if (!isFinished){
                    System.out.printf("[Client] Sent %s message to server %n", message);
                    pw.println(message);
                    String received = br.readLine();
                    System.out.printf("[Client] Received message from server: %s%n", received);
                }else {
                    System.out.println("[Client] Sending final message to server");
                    pw.println(message);
                }
            }
            closeTextChannels();
            stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
