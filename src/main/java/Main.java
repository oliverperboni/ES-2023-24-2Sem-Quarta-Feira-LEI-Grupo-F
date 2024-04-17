import javax.swing.*;
import java.util.List;

public class Main {

   public static void main(String[] args) {
        // Crie uma lista de salas (Rooms) para testar
       List<Rooms>roomsList = ReadCSV.readRoomsCSV("csv/CaracterizaçãoDasSalas.csv");
        // Crie uma instância do RoomFilterFrame e exiba a janela de filtragem
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                RoomFilterFrame filterFrame = new RoomFilterFrame(roomsList);
                filterFrame.show();
            }
        });
        for (Rooms rooms : roomsList) {
            System.out.println(rooms.getEdificio());
        }
    }

}
