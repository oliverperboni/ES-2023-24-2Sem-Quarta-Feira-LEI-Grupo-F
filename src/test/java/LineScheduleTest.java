import org.junit.jupiter.api.Test;
import structures.LineSchedule;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LineScheduleTest {
    LineSchedule ls = new LineSchedule("Curso Teste", "UC Teste", "Turno Teste", "Turma Teste", 20, "Segunda-feira",
            "08:00", "10:00", "01/01/2022", "Características Sala Teste", "Sala Teste");

    @Test
    void getCurso() {
        assertEquals("Curso Teste", ls.getCurso());
    }

    @Test
    void getUnidadeCurricular() {
        assertEquals("UC Teste", ls.getUnidadeCurricular());

    }

    @Test
    void getTurno() {
        assertEquals("Turno Teste", ls.getTurno());

    }

    @Test
    void getTurma() {
        assertEquals("Turma Teste", ls.getTurma());

    }

    @Test
    void getInscritos() {
        assertEquals(20, ls.getInscritos());

    }

    @Test
    void getDiaSemana() {
        assertEquals("Segunda-feira", ls.getDiaSemana());

    }

    @Test
    void getHoraInicio() {
        assertEquals("08:00", ls.getHoraInicio());

    }

    @Test
    void getHoraFim() {
        assertEquals("10:00", ls.getHoraFim());

    }

    @Test
    void getDataAula() {
        assertEquals("01/01/2022", ls.getDataAula());

    }

    @Test
    void getCaracteristicasSala() {
        assertEquals("Características Sala Teste", ls.getCaracteristicasSala());

    }

    @Test
    void getSala() {
        assertEquals("Sala Teste", ls.getSala());

    }
}