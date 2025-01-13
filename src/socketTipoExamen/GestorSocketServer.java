package socketTipoExamen;

import java.io.*;
import java.net.Socket;

public class GestorSocketServer implements Runnable{

    private Socket socket;
    private OutputStream out;
    private InputStream in;
    private PrintWriter pw;
    private BufferedReader br;
    private InputStreamReader isr;
    private GestorPeticiones peticiones;

    public GestorSocketServer(Socket socket) {
        this.socket = socket;
        this.peticiones = new GestorPeticiones();
    }

    @Override
    public void run() {
        try{
            in = socket.getInputStream();
            out = socket.getOutputStream();
            isr = new InputStreamReader(in);
            br = new BufferedReader(isr);
            pw = new PrintWriter(out, true);
        }catch (IOException e){
            e.printStackTrace();
        }

        while(true){
            String datoLeido = "";
            try {
                datoLeido = br.readLine();
                System.out.println("Petición recibida: " + datoLeido);
                if (datoLeido != null){
                    String respuesta = peticiones.responderPeticion(datoLeido);
                    pw.println(respuesta);
                    System.out.println("Respuesta enviada: " + respuesta);
                    if (respuesta.equals("#Finalizado#")){
                        break;
                    }
                }else {
                    break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            pw.close();
            br.close();
            isr.close();
            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
