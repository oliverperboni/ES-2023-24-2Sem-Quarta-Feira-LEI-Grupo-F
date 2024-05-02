import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import core.ScheduleDataModel;
import org.junit.Test;
import structures.LineSchedule;
import structures.ScheduleInstant;

import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

public class ReadCSVTest {

//    @Test
//    public void testReadScheduleCSV() {
//        String csvFile = "csv/HorarioDeExemplo.csv";
//
//        // Chama o método readScheduleCSV
//        TreeMap<ScheduleInstant, List<LineSchedule>> result = ScheduleDataModel.readScheduleCSV(csvFile);
//
//        // Verifica se o mapa resultante não é null
//        assertNotNull(result);
//
//        // Verifica se o número de objetos structures.LineSchedule criados está correto
//        int totalSchedules = result.values().stream().mapToInt(List::size).sum();
//        assertEquals(26019, totalSchedules); // Número real de linhas no ficheiro no primeiro argumento
//
//        // Obtém a primeira LineSchedule
//        List<LineSchedule> firstDaySchedules = result.firstEntry().getValue();
//        LineSchedule firstSchedule = firstDaySchedules.getFirst();
//
//        // Verifica os atributos da primeira LineSchedule
//        assertEquals("ME", firstSchedule.getCurso());
//        assertEquals("TEORIA DOS JOGOS E DOS CONTRATOS", firstSchedule.getUnidadeCurricular());
//        assertEquals("01789TP01", firstSchedule.getTurno());
//        assertEquals("MEA1", firstSchedule.getTurma());
//        assertEquals(Optional.of(30), firstSchedule.getInscritos());
//        assertEquals("Sex", firstSchedule.getDiaSemana());
//        assertEquals("13:00:00", firstSchedule.getHoraInicio());
//        assertEquals("14:30:00", firstSchedule.getHoraFim());
//        assertEquals("02/12/2022", firstSchedule.getDataAula());
//        assertEquals("Sala Aulas Mestrado", firstSchedule.getCaracteristicasSala());
//        assertEquals("AA2.25", firstSchedule.getSala());
//    }

}
