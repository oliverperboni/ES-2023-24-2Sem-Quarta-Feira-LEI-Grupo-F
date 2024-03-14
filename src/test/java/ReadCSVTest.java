import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class ReadCSVTest {

    @Test
    void readScheduleCSV() {
        // Caminho para o arquivo CSV de teste
        String csvFile = "csv/HorarioDeExemplo.csv"; 

        // ME;Teoria dos Jogos e dos Contratos;01789TP01;MEA1;30;Sex;13:00:00;14:30:00;02/12/2022;Sala Aulas Mestrado;AA2.25


        // Chama o método readScheduleCSV
        ArrayList<LineSchedule> result = ReadCSV.readScheduleCSV(csvFile);

        // Verificações

        // Verifica se a lista resultante não é nula
        assertNotNull(result);

        // Verifica se o número de objetos LineSchedule criados está correto
        assertEquals(26019, result.size()); // Substitua pelo número real de linhas no arquivo

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