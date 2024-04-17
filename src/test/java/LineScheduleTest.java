import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LineScheduleTest {
    LineSchedule ls = new LineSchedule("Curso Teste", "UC Teste", "Turno Teste", "Turma Teste", 20, "Segunda-feira",
            "08:00", "10:00", "01/01/2022", "Características Sala Teste", "Sala Teste");

//    @BeforeEach
//    public void setUpHeadlessMode() {
//        System.setProperty("java.awt.headless", "true");
//    }

    @Test
    public void getCurso() {
        assertEquals("Curso Teste", ls.getCurso());
    }

    @Test
    public void getUnidadeCurricular() {
        assertEquals("UC Teste", ls.getUnidadeCurricular());

    }

    @Test
    public void getTurno() {
        assertEquals("Turno Teste", ls.getTurno());

    }

    @Test
    public void getTurma() {
        assertEquals("Turma Teste", ls.getTurma());

    }

    @Test
    public void getInscritos() {
        assertEquals(20, ls.getInscritos());

    }

    @Test
    public void getDiaSemana() {
        assertEquals("Segunda-feira", ls.getDiaSemana());

    }

    @Test
    public void getHoraInicio() {
        assertEquals("08:00", ls.getHoraInicio());

    }

    @Test
    public void getHoraFim() {
        assertEquals("10:00", ls.getHoraFim());

    }

    @Test
    public void getDataAula() {
        assertEquals("01/01/2022", ls.getDataAula());

    }

    @Test
    public void getCaracteristicasSala() {
        assertEquals("Características Sala Teste", ls.getCaracteristicasSala());

    }

    @Test
    public void getSala() {
        assertEquals("Sala Teste", ls.getSala());

    }
}