package socketTipoExamen;

import java.util.Random;

public class GestorPeticiones {
    public String responderPeticion(String peticion){
        String[] partesPeticion = peticion.split("#");
        for (String s : partesPeticion) {
            System.out.println(s.trim());
        }
        switch (partesPeticion[1].trim()){
            case "Listado números" -> {
                StringBuffer sb = new StringBuffer();
                for (int i = Integer.parseInt(partesPeticion[2]); i <= Integer.parseInt(partesPeticion[3]); i++) {
                    if (i == Integer.parseInt(partesPeticion[3]))
                        sb.append(i);
                    else
                        sb.append(i).append("|");
                }
                return sb.toString();
            }
            case "Numero aleatorio" ->{
                Random random = new Random();
                int num = random.nextInt(Integer.parseInt(partesPeticion[2]), Integer.parseInt(partesPeticion[3]));
                return Integer.toString(num);
            }
            case "Fin" -> {
                return "#Finalizado#";
            }
            default -> {
                return "#Error#";
            }
        }
    }
}
