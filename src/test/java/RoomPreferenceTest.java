import org.junit.jupiter.api.Test;
import structures.RoomPreference;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoomPreferenceTest {

    @Test
    public void testToString() {
        assertAll(
                () -> assertEquals(RoomPreference.ANFITEATRO_AULAS.toString(), "Anfiteatro aulas"),
                () -> assertEquals(RoomPreference.APOIO_TECNICO_EVENTOS.toString(), "Apoio técnico eventos"),
                () -> assertEquals(RoomPreference.ARQ1.toString(), "Arq 1"),
                () -> assertEquals(RoomPreference.ARQ2.toString(), "Arq 2"),
                () -> assertEquals(RoomPreference.ARQ3.toString(), "Arq 3"),
                () -> assertEquals(RoomPreference.ARQ4.toString(), "Arq 4"),
                () -> assertEquals(RoomPreference.ARQ5.toString(), "Arq 5"),
                () -> assertEquals(RoomPreference.ARQ6.toString(), "Arq 6"),
                () -> assertEquals(RoomPreference.ARQ9.toString(), "Arq 9"),
                () -> assertEquals(RoomPreference.BYOD.toString(), "BYOD (Bring Your Own Device)"),
                () -> assertEquals(RoomPreference.FOCUS_GROUP.toString(), "Focus Group"),
                () -> assertEquals(RoomPreference.LABORATORIO_ARQUITETURA_COMPUTADORES_I.toString(), "Laboratório de Arquitectura de Computadores I"),
                () -> assertEquals(RoomPreference.LABORATORIO_ARQUITETURA_COMPUTADORES_II.toString(), "Laboratório de Arquitectura de Computadores II"),
                () -> assertEquals(RoomPreference.LABORATORIO_BASES_ENGENHARIA.toString(), "Laboratório de Bases de Engenharia"),
                () -> assertEquals(RoomPreference.LABORATORIO_ELETRONICA.toString(), "Laboratório de Electrónica"),
                () -> assertEquals(RoomPreference.LABORATORIO_INFORMATICA.toString(), "Laboratório de Informática"),
                () -> assertEquals(RoomPreference.LABORATORIO_JORNALISMO.toString(), "Laboratório de Jornalismo"),
                () -> assertEquals(RoomPreference.LABORATORIO_REDES_COMPUTADORES_I.toString(), "Laboratório de Redes de Computadores I"),
                () -> assertEquals(RoomPreference.LABORATORIO_REDES_COMPUTADORES_II.toString(), "Laboratório de Redes de Computadores II"),
                () -> assertEquals(RoomPreference.LABORATORIO_TELECOMUNICACOES.toString(), "Laboratório de Telecomunicações"),
                () -> assertEquals(RoomPreference.SALA_AULAS_MESTRADO.toString(), "Sala Aulas Mestrado"),
                () -> assertEquals(RoomPreference.SALA_AULAS_MESTRADO_PLUS.toString(), "Sala Aulas Mestrado Plus"),
                () -> assertEquals(RoomPreference.SALA_NEE.toString(), "Sala NEE"),
                () -> assertEquals(RoomPreference.SALA_PROVAS.toString(), "Sala Provas"),
                () -> assertEquals(RoomPreference.SALA_REUNIAO.toString(), "Sala Reunião"),
                () -> assertEquals(RoomPreference.SALA_ARQUITETURA.toString(), "Sala de Arquitectura"),
                () -> assertEquals(RoomPreference.SALA_AULAS_NORMAL.toString(), "Sala de Aulas normal"),
                () -> assertEquals(RoomPreference.VIDEOCONFERENCIA.toString(), "videoconferéncia"),
                () -> assertEquals(RoomPreference.ATRIO.toString(), "Atrio")
        );
    }
}
