package socketExamen;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

public class GestorSocketServer implements Runnable{

    private Socket socket;
    private JuegoAdivinaNumero juegoAdivinaNumero;
    private OutputStream out;
    private InputStream in;
    private PrintWriter pw;
    private BufferedReader br;
    private InputStreamReader isr;
    private static AtomicLong peticionesServidor = new AtomicLong(0);

    public GestorSocketServer(Socket socket) {
        this.socket = socket;
        juegoAdivinaNumero = new JuegoAdivinaNumero();
    }

    @Override
    public void run() {
        try {
            in = socket.getInputStream();
            out = socket.getOutputStream();
            isr = new InputStreamReader(in);
            br = new BufferedReader(isr);
            pw = new PrintWriter(out, true);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        while(true){
            String datoLeido = "";
            try {
                datoLeido = br.readLine();
                peticionesServidor.getAndIncrement();
                if(datoLeido != null){
                    String respuesta = juegoAdivinaNumero.verificarIntento(datoLeido);
                    pw.println(respuesta);
                    if (respuesta.equals("Acertado")){
                        System.out.printf("(Gestor sockets) %d sockets aceptados y %d peticiones recibidas%n",
                                ServerTCP.getAcceptedSockets(), peticionesServidor.get());
                        break;
                    }
                }else{
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error al leer el dato");
                break;
            }
        }

        try{
            pw.close();
            br.close();
            isr.close();
            in.close();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
