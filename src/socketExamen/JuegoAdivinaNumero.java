package socketExamen;

import java.util.Random;

public class JuegoAdivinaNumero {

    private int numeroAdivinar;

    public JuegoAdivinaNumero() {
        this.numeroAdivinar = new Random().nextInt(1000) + 1;
    }

    public String verificarIntento(String intento){
        try{
            int numero = Integer.parseInt(intento);
            return numero == numeroAdivinar ? "Acertado" : "No acertado";
        }catch (NumberFormatException e){
            return "No acertado";
        }
    }

}
