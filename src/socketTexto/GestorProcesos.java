package socketTexto;

import java.io.*;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GestorProcesos extends Thread{

    private Socket socket;
    private OutputStream os;
    private InputStream in;

    public GestorProcesos(Socket socket) {
        this.socket = socket;
    }

    public void lanzarProceso() throws IOException {
        os = socket.getOutputStream();
        in = socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(in);
        PrintWriter pw = new PrintWriter(os, true);
        BufferedReader br = new BufferedReader(isr);

        int tiempoEspera = new Random().nextInt(5) + 1;

        try {
            String recieved = br.readLine();
            while(!recieved.equalsIgnoreCase("fin")){
                System.out.println("Recieved from client: " + recieved);
                TimeUnit.SECONDS.sleep(tiempoEspera);
                System.out.println("Enviando desde gestor de proceso: " + recieved.toUpperCase());
                pw.println(recieved.toUpperCase());
                recieved = br.readLine();
            }
            System.out.println("Connection ended");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            pw.close();
            br.close();
            isr.close();
            os.close();
            in.close();
        }
    }

    @Override
    public void run() {
        try {
            lanzarProceso();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
