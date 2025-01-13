package socketTipoExamen;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient {
    private Socket socket;
    private OutputStream os;
    private InputStream is;
    private PrintWriter pw;
    private InputStreamReader isr;
    private BufferedReader br;

    public SocketClient(String serverIP, int serverPort) throws IOException {
        socket = new Socket(serverIP, serverPort);
        os = socket.getOutputStream();
        is = socket.getInputStream();
        pw = new PrintWriter(os, true);
        isr = new InputStreamReader(is);
        br = new BufferedReader(isr);
    }

    public static void main(String[] args) {
        SocketClient cliente;
        Scanner sc = new Scanner(System.in);
        try {
            cliente = new SocketClient("localhost", 8081);
            String respuesta = "";
            do {
                System.out.println("Que petición desea realizar?");
                cliente.pw.println(sc.nextLine());
                respuesta = cliente.br.readLine();
                System.out.println("Respuesta del servidor: " + respuesta);


            }while (!respuesta.equals("#Finalizado#"));
            cliente.pw.close();
            cliente.br.close();
            cliente.isr.close();
            cliente.os.close();
            cliente.is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
