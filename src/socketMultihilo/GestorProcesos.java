package socketMultihilo;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GestorProcesos extends Thread {
    private Socket socket;
    private OutputStream os;

    public GestorProcesos(Socket socket) {
        this.socket = socket;
    }

    public void lanzarProceso() throws IOException {
        os = socket.getOutputStream();

        int tiempoEspera = new Random().nextInt(10) + 1;

        try {
            TimeUnit.SECONDS.sleep(tiempoEspera);
            System.out.println("Enviando desde gestor de proceso: " + tiempoEspera);
            os.write(tiempoEspera);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            os.close();
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