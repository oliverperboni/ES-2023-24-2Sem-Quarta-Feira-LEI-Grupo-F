import core.ScheduleDataModel;
import core.ScheduleEngine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import structures.LineSchedule;
import structures.Room;
import structures.RoomPreference;
import structures.SchedulePeriod;

import java.util.ArrayList;
import java.util.Arrays;

public class ScheduleEngineTest {

    @Test
    public void suggestCompensationTest() {
        LineSchedule reSchedule = new LineSchedule("LCD, LCD-PL", "ANALISE DE REDES (1CICLO)",
                "03604TP01", "CDC1", 27, "Sáb", "09:30:00",
                "11:00:00", "03/12/2022", "BYOD (Bring Your Own Device)", "D1.07");

        ScheduleDataModel dataModel = new ScheduleDataModel("csv/HorarioDeExemplo.csv",
                false, "csv/CaracterizaçãoDasSalas.csv", false);
        ScheduleEngine engine = new ScheduleEngine(dataModel);

        ArrayList<SchedulePeriod> allowedPeriodsTest = new ArrayList<SchedulePeriod>();
        allowedPeriodsTest.add(SchedulePeriod._14H30_16H00);
        allowedPeriodsTest.add(SchedulePeriod._18H00_19H30);
        allowedPeriodsTest.add(SchedulePeriod.SEGUNDA_FEIRA);
        allowedPeriodsTest.add(SchedulePeriod.TERCA_FEIRA);

        ArrayList<RoomPreference> roomTypePreferencesTest = new ArrayList<RoomPreference>();
        roomTypePreferencesTest.add(RoomPreference.SALA_AULAS_NORMAL);
        roomTypePreferencesTest.add(RoomPreference.SALA_AULAS_MESTRADO);

       Assertions.assertNotNull(engine.suggestCompensation(reSchedule, new ArrayList<SchedulePeriod>(),
               allowedPeriodsTest, roomTypePreferencesTest, new ArrayList<RoomPreference>()));
       Assertions.assertInstanceOf(ArrayList.class, engine.suggestCompensation(reSchedule, new ArrayList<SchedulePeriod>(),
               allowedPeriodsTest, roomTypePreferencesTest, new ArrayList<RoomPreference>()) );
    }

    @Test
    public void suggestNewCourseTest() {
        Assertions.assertTrue(true);
    }

    @Test
    public void createSchedulePossibilityTest() {
        LineSchedule testSchedule = new LineSchedule("LCD, LCD-PL", "ANALISE DE REDES (1CICLO)",
                "03604TP01", "CDC1", 27, "Sáb", "09:30:00",
                "11:00:00", "03/12/2022", "BYOD (Bring Your Own Device)", "D1.07");

        ScheduleDataModel dataModel = new ScheduleDataModel("csv/HorarioDeExemplo.csv",
                false, "csv/CaracterizaçãoDasSalas.csv", false);
        ScheduleEngine engine = new ScheduleEngine(dataModel);

        Room testRoom = new Room("Ala Autónoma (ISCTE-IUL)", "Auditório Afonso de Barros",
                80 ,39, "4", Arrays.asList("Sala Aulas Mestrado",
                "Sala Aulas Mestrado Plus", "Sala de Aulas normal"));

        Assertions.assertNotNull(engine.createSchedulePossibility(testSchedule, SchedulePeriod.SEGUNDA_FEIRA, testRoom,
                SchedulePeriod._08H00_09H30, RoomPreference.SALA_AULAS_NORMAL, new ArrayList<>(), new ArrayList<>()));
        Assertions.assertInstanceOf(LineSchedule.class, engine.createSchedulePossibility(testSchedule, SchedulePeriod.SEGUNDA_FEIRA,
                testRoom, SchedulePeriod._08H00_09H30, RoomPreference.SALA_AULAS_NORMAL, new ArrayList<>(), new ArrayList<>()));

        Assertions.assertEquals(testSchedule.getCurso(), engine.createSchedulePossibility(testSchedule, SchedulePeriod.SEGUNDA_FEIRA,
                testRoom, SchedulePeriod._08H00_09H30, RoomPreference.SALA_AULAS_NORMAL, new ArrayList<>(), new ArrayList<>()).getCurso());

        Assertions.assertEquals(testSchedule.getUnidadeCurricular(), engine.createSchedulePossibility(testSchedule, SchedulePeriod.SEGUNDA_FEIRA,
                testRoom, SchedulePeriod._08H00_09H30, RoomPreference.SALA_AULAS_NORMAL, new ArrayList<>(), new ArrayList<>()).getUnidadeCurricular());

        Assertions.assertEquals(testSchedule.getTurno(), engine.createSchedulePossibility(testSchedule, SchedulePeriod.SEGUNDA_FEIRA,
                testRoom, SchedulePeriod._08H00_09H30, RoomPreference.SALA_AULAS_NORMAL, new ArrayList<>(), new ArrayList<>()).getTurno());

        Assertions.assertEquals(testSchedule.getTurma(), engine.createSchedulePossibility(testSchedule, SchedulePeriod.SEGUNDA_FEIRA,
                testRoom, SchedulePeriod._08H00_09H30, RoomPreference.SALA_AULAS_NORMAL, new ArrayList<>(), new ArrayList<>()).getTurma());

        Assertions.assertEquals(testSchedule.getInscritos(), engine.createSchedulePossibility(testSchedule, SchedulePeriod.SEGUNDA_FEIRA,
                testRoom, SchedulePeriod._08H00_09H30, RoomPreference.SALA_AULAS_NORMAL, new ArrayList<>(), new ArrayList<>()).getInscritos());
    }

    @Test
    public void roomsByPreferenceTest() {
        ScheduleDataModel dataModel = new ScheduleDataModel("csv/HorarioDeExemplo.csv",
                false, "csv/CaracterizaçãoDasSalas.csv", false);
        ScheduleEngine engine = new ScheduleEngine(dataModel);

        for (RoomPreference rp : RoomPreference.values()) {
            Assertions.assertNotNull(engine.roomsByPreference(rp));
            Assertions.assertInstanceOf(ArrayList.class, engine.roomsByPreference(rp));
            for (Room room : engine.roomsByPreference(rp)) {
                Assertions.assertTrue(room.getRoomSpecs().contains(rp.toString()));
            }
        }
    }

}
