import core.ScheduleDataModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import structures.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ScheduleDataModelTest {

    @Test
    public void getScheduleMapTest() {
        ScheduleDataModel dataModel = new ScheduleDataModel("csv/HorarioDeExemplo.csv",
                false, "csv/CaracterizaçãoDasSalas.csv", false);

        Assertions.assertNotNull(dataModel.getScheduleMap());
        Assertions.assertInstanceOf(TreeMap.class, dataModel.getScheduleMap());
    }

    @Test
    public void getScheduleEntriesTest() {
        ScheduleDataModel dataModel = new ScheduleDataModel("csv/HorarioDeExemplo.csv",
                false, "csv/CaracterizaçãoDasSalas.csv", false);

        assertNotNull(dataModel.getScheduleEntries());
        Assertions.assertInstanceOf(List.class, dataModel.getScheduleEntries());
        Assertions.assertFalse(dataModel.getScheduleEntries().isEmpty());
    }

    @Test
    public void getRoomEntriesTest() {
        ScheduleDataModel dataModel = new ScheduleDataModel("csv/HorarioDeExemplo.csv",
                false, "csv/CaracterizaçãoDasSalas.csv", false);

        assertNotNull(dataModel.getRoomEntries());
        Assertions.assertInstanceOf(List.class, dataModel.getRoomEntries());
        Assertions.assertFalse(dataModel.getRoomEntries().isEmpty());
    }

    @Test
    public void getRoomColumnHeadersTest() {
        ScheduleDataModel dataModel = new ScheduleDataModel("csv/HorarioDeExemplo.csv",
                false, "csv/CaracterizaçãoDasSalas.csv", false);

        assertNotNull(dataModel.getRoomColumnHeaders());
        Assertions.assertInstanceOf(List.class, dataModel.getRoomColumnHeaders());
        Assertions.assertFalse(dataModel.getRoomColumnHeaders().isEmpty());
    }

    @Test
    public void getScheduleFilePathTest() {
        ScheduleDataModel dataModel = new ScheduleDataModel("csv/HorarioDeExemplo.csv",
                false, "csv/CaracterizaçãoDasSalas.csv", false);

        Assertions.assertNotNull(dataModel.getScheduleFilePath());
        Assertions.assertInstanceOf(String.class, dataModel.getScheduleFilePath());
        Assertions.assertFalse(dataModel.getScheduleFilePath().isEmpty());
    }

    @Test
    public void setScheduleFilePathTest() {
        ScheduleDataModel dataModel = new ScheduleDataModel("csv/HorarioDeExemplo.csv",
                false, "csv/CaracterizaçãoDasSalas.csv", false);

        dataModel.setScheduleFilePath("testdirectory/testfile.csv");

        Assertions.assertNotNull(dataModel.getScheduleFilePath());
        Assertions.assertInstanceOf(String.class, dataModel.getScheduleFilePath());
        Assertions.assertFalse(dataModel.getScheduleFilePath().isEmpty());
        Assertions.assertEquals("testdirectory/testfile.csv", dataModel.getScheduleFilePath());
    }

    @Test
    public void isScheduleFileRemoteTest() {
        ScheduleDataModel dataModel = new ScheduleDataModel("csv/HorarioDeExemplo.csv",
                true, "csv/CaracterizaçãoDasSalas.csv", false);

        Assertions.assertTrue(dataModel.isScheduleFileRemote());
    }

    @Test
    public void getRoomsFilePathTest() {
        ScheduleDataModel dataModel = new ScheduleDataModel("csv/HorarioDeExemplo.csv",
                false, "csv/CaracterizaçãoDasSalas.csv", false);

        assertNotNull(dataModel.getRoomsFilePath());
        Assertions.assertInstanceOf(String.class, dataModel.getRoomsFilePath());
        Assertions.assertFalse(dataModel.getRoomsFilePath().isEmpty());
    }

    @Test
    public void isRoomsFileRemoteTest() {
        ScheduleDataModel dataModel = new ScheduleDataModel("csv/HorarioDeExemplo.csv",
                false, "csv/CaracterizaçãoDasSalas.csv", true);

        Assertions.assertTrue(dataModel.isRoomsFileRemote());
    }


    @Test
    public void testReadScheduleCSV() {
        String csvFile = "csv/HorarioDeExemplo.csv";

        // Chama o método readScheduleCSV
        TreeMap<ScheduleInstant, List<LineSchedule>> testMap = ScheduleDataModel.readScheduleCSV(csvFile);

        // Verifica se o mapa resultante não é null
        assertNotNull(testMap);

        // Verifica se o número de objetos structures.LineSchedule criados está correto
        int totalSchedules = testMap.values().stream().mapToInt(List::size).sum();
        assertEquals(26019, totalSchedules); // Número real de linhas no ficheiro no primeiro argumento

        // Obtém a primeira LineSchedule
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate firstDayDate = LocalDate.parse("12/09/2022", formatter);
        SchedulePeriod firstDayFirstPeriod = new SchedulePeriod(LocalTime.parse("08:00:00"), LocalTime.parse("09:30:00"));
        ScheduleInstant firstDayFirstInstant = new ScheduleInstant(firstDayDate, firstDayFirstPeriod);

        List<LineSchedule> firstInstantSchedules = testMap.get(firstDayFirstInstant);
        LineSchedule firstSchedule = firstInstantSchedules.getFirst();

        // Verifica os atributos da primeira LineSchedule
        Assertions.assertEquals("LGIL", firstSchedule.getCurso());
        Assertions.assertEquals("GESTAO INTEGRADA DA QUALIDADE", firstSchedule.getUnidadeCurricular());
        Assertions.assertEquals("00489TP01", firstSchedule.getTurno());
        Assertions.assertEquals("GILC1", firstSchedule.getTurma());
        Assertions.assertEquals(27, firstSchedule.getInscritos());
        Assertions.assertEquals("Seg", firstSchedule.getDiaSemana());
        Assertions.assertEquals("08:00:00", firstSchedule.getHoraInicio());
        Assertions.assertEquals("09:30:00", firstSchedule.getHoraFim());
        Assertions.assertEquals("12/09/2022", firstSchedule.getDataAula());
        Assertions.assertEquals("Sala de Aulas normal", firstSchedule.getCaracteristicasSala());
        Assertions.assertEquals("AA3.28", firstSchedule.getSala());
    }

    @Test
    public void testReadRoomsCSV() {
        String csvFile = "csv/CaracterizaçãoDasSalas.csv";

        // Chama o método readRoomsCSV
        RoomsTuple<ArrayList<Room>, List<String>> testTuple = ScheduleDataModel.readRoomsCSV(csvFile);

        // Verifica se o RoomsTuple resultante não é null
        assertNotNull(testTuple);

        // Verifica se o número de objetos structures.Room criados está correto
        int totalSchedules = testTuple.getRoomLineArray().size();
        Assertions.assertEquals(131, totalSchedules); // Número real de linhas no ficheiro

        // Obtém a primeira Room
        List<Room> rooms = testTuple.getRoomLineArray();
        Room firstRoom = rooms.getFirst();

        // Verifica os atributos da primeira Room
        Assertions.assertEquals("Ala Aut�noma (ISCTE-IUL)", firstRoom.getEdificio());
        Assertions.assertEquals("Audit�rio Afonso de Barros", firstRoom.getNomeSala());
        Assertions.assertEquals(80, firstRoom.getCapacidadeNormal());
        Assertions.assertEquals(39, firstRoom.getCapacidadeExame());
        Assertions.assertEquals("4", firstRoom.getNumCaracteristicas());
        Assertions.assertEquals(Arrays.asList("Horário sala visível portal público", "Sala Aulas Mestrado",
                "Sala Aulas Mestrado Plus", "Sala de Aulas normal"), firstRoom.getRoomSpecs());
    }

    @Test
    public void testReadGitScheduleCSV() {
        String fileURL = "https://raw.githubusercontent.com/oliverperboni/ES-2023-24-2Sem-Quarta-Feira-LEI-Grupo-F/main/csv/HorarioDeExemplo.csv?token=GHSAT0AAAAAACSCMVIQN5GISKTDKN7KQV6QZR43AYA";

        // Chama o método readGitScheduleCSV
        TreeMap<ScheduleInstant, List<LineSchedule>> testMap = null;
        try {
            testMap = ScheduleDataModel.readGitScheduleCSV(fileURL);
        } catch (IOException e) {
            System.out.println("Erro na ligacão ao servidor do ficheiro remoto.");
            e.printStackTrace();
        }

        // Verifica se o mapa resultante não é null
        assertNotNull(testMap);

        // Verifica se o número de objetos structures.LineSchedule criados está correto
        int totalSchedules = testMap.values().stream().mapToInt(List::size).sum();
        assertEquals(26019, totalSchedules); // Número real de linhas no ficheiro no primeiro argumento

        // Obtém a primeira LineSchedule
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate firstDayDate = LocalDate.parse("12/09/2022", formatter);
        SchedulePeriod firstDayFirstPeriod = new SchedulePeriod(LocalTime.parse("08:00:00"), LocalTime.parse("09:30:00"));
        ScheduleInstant firstDayFirstInstant = new ScheduleInstant(firstDayDate, firstDayFirstPeriod);

        List<LineSchedule> firstInstantSchedules = testMap.get(firstDayFirstInstant);
        LineSchedule firstSchedule = firstInstantSchedules.getFirst();

        // Verifica os atributos da primeira LineSchedule
        Assertions.assertEquals("LGIL", firstSchedule.getCurso());
        Assertions.assertEquals("GESTAO INTEGRADA DA QUALIDADE", firstSchedule.getUnidadeCurricular());
        Assertions.assertEquals("00489TP01", firstSchedule.getTurno());
        Assertions.assertEquals("GILC1", firstSchedule.getTurma());
        Assertions.assertEquals(27, firstSchedule.getInscritos());
        Assertions.assertEquals("Seg", firstSchedule.getDiaSemana());
        Assertions.assertEquals("08:00:00", firstSchedule.getHoraInicio());
        Assertions.assertEquals("09:30:00", firstSchedule.getHoraFim());
        Assertions.assertEquals("12/09/2022", firstSchedule.getDataAula());
        Assertions.assertEquals("Sala de Aulas normal", firstSchedule.getCaracteristicasSala());
        Assertions.assertEquals("AA3.28", firstSchedule.getSala());
    }

}
