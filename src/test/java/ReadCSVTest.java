import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class ReadCSVTest {

    @Test
    void testReadScheduleCSV() {
        String csvFile = "csv/HorarioDeExemplo.csv";

        //Chama o método readScheduleCSV
        ArrayList<LineSchedule> result = ReadCSV.readScheduleCSV(csvFile);

        //Verifica se a lista resultante não é nula
        assertNotNull(result);

        //Verifica se o número de objetos LineSchedule criados está correto
        assertEquals(26019, result.size()); //Número real de linhas no arquivo no primeiro argumento

        // Verifica se os objetos LineSchedule foram criados corretamente
        LineSchedule firstSchedule = result.get(0);
        assertEquals("ME", firstSchedule.getCurso());
        assertEquals("Teoria dos Jogos e dos Contratos", firstSchedule.getUnidadeCurricular());
        assertEquals("01789TP01", firstSchedule.getTurno());
        assertEquals("MEA1", firstSchedule.getTurma());
        assertEquals(30, firstSchedule.getInscritos());
        assertEquals("Sex", firstSchedule.getDiaSemana());
        assertEquals("13:00:00", firstSchedule.getHoraInicio());
        assertEquals("14:30:00", firstSchedule.getHoraFim());
        assertEquals("02/12/2022", firstSchedule.getDataAula());
        assertEquals("Sala Aulas Mestrado", firstSchedule.getCaracteristicasSala());
        assertEquals("AA2.25", firstSchedule.getSala());

    }

}