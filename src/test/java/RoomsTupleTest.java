import structures.ScheduleDataModel;
import org.junit.Test;
import structures.Room;
import structures.RoomsTuple;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RoomsTupleTest {

    @Test
    public void getRoomLineArrayTest() {
        RoomsTuple<ArrayList<Room>, List<String>> roomsTuple = ScheduleDataModel.readRoomsCSV("csv/CaracterizaçãoDasSalas.csv");
        assertNotNull(roomsTuple.getRoomLineArray());
        assertTrue(roomsTuple.getRoomLineArray() instanceof ArrayList<Room>);
        assertFalse(roomsTuple.getRoomLineArray().isEmpty());
    }

    @Test
    public void getRoomColumnHeadersTest() {
        RoomsTuple<ArrayList<Room>, List<String>> roomsTuple = ScheduleDataModel.readRoomsCSV("csv/CaracterizaçãoDasSalas.csv");
        assertNotNull(roomsTuple.getRoomColumnHeaders());
        assertTrue(roomsTuple.getRoomColumnHeaders() instanceof List<String>);
        assertFalse(roomsTuple.getRoomColumnHeaders().isEmpty());
    }

    @Test
    public void setRoomLineArrayTest() {
        RoomsTuple<ArrayList<Room>, List<String>> roomsTuple = ScheduleDataModel.readRoomsCSV("csv/CaracterizaçãoDasSalas.csv");

        Room r1 = new Room("Test Building", "Test Room", 0, 0, "0", List.of("Test Room"));
        ArrayList<Room> newRoomLineArray = new ArrayList<>();
        newRoomLineArray.add(r1);

        roomsTuple.setRoomLineArray(newRoomLineArray);
        assertEquals(roomsTuple.getRoomLineArray().getFirst().getEdificio(), r1.getEdificio());
        assertEquals(roomsTuple.getRoomLineArray().getFirst().getNomeSala(), r1.getNomeSala());
        assertEquals(roomsTuple.getRoomLineArray().getFirst().getCapacidadeNormal(), r1.getCapacidadeNormal());
        assertEquals(roomsTuple.getRoomLineArray().getFirst().getCapacidadeExame(), r1.getCapacidadeExame());
        assertEquals(roomsTuple.getRoomLineArray().getFirst().getNumCaracteristicas(), r1.getNumCaracteristicas());
        assertEquals(roomsTuple.getRoomLineArray().getFirst().getRoomSpecs(), r1.getRoomSpecs());
    }

    @Test
    public void setRoomColumnHeadersTest() {
        RoomsTuple<ArrayList<Room>, List<String>> roomsTuple = ScheduleDataModel.readRoomsCSV("csv/CaracterizaçãoDasSalas.csv");

        List<String> newRoomColumnHeaders = new ArrayList<>();
        newRoomColumnHeaders.add("Test Header");

        roomsTuple.setRoomColumnHeaders(newRoomColumnHeaders);
        assertEquals(roomsTuple.getRoomColumnHeaders().getFirst(), "Test Header");
    }

}
