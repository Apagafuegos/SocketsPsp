package socketExamen;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientTCP {
    private String serverIP;
    private int serverPort;
    private Socket socket;
    private OutputStream os;
    private InputStream is;
    private PrintWriter pw;
    private InputStreamReader isr;
    private BufferedReader br;
    private int intentos;

    public ClientTCP(String serverIP, int serverPort) throws UnknownHostException, IOException {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        socket = new Socket(serverIP, serverPort);
        os = socket.getOutputStream();
        is = socket.getInputStream();
        pw = new PrintWriter(os, true);
        isr = new InputStreamReader(is);
        br = new BufferedReader(isr);
    }

    public static void main(String[] args) {
        ClientTCP cliente;

        for (int lanzamientoClientes = 0; lanzamientoClientes < 10; lanzamientoClientes++) {
            try {
                cliente = new ClientTCP("localhost", 8081);
                for (int i = 1; i <= 1000; i++) {
                    String datoEnviado = Integer.toString(i);
                    cliente.pw.println(datoEnviado);
                    String datoRecibido = cliente.br.readLine();
                    cliente.intentos++;
                    if ("Acertado".equals(datoRecibido)) {
                        System.out.printf("(Cliente) Dato acertado después de %d intentos%n", cliente.intentos);
                        break;
                    }
                }
                // Cierre de los streams del cliente
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
}
