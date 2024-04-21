import structures.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.MINUTES;

public class ScheduleEngine {
    private ScheduleDataModel dataModel;

    public ScheduleEngine(ScheduleDataModel dataModel) {
        this.dataModel = dataModel;
    }

    public void suggestCompensation(LineSchedule classSchedule, ArrayList<SchedulePeriod> excludedPeriods,
                                    ArrayList<SchedulePeriod> allowedPeriods, ArrayList<RoomPreference> roomTypePreferences,
                                    ArrayList<Room> roomTypeExclusions) {

//      classSchedule é uma structures.LineSchedule com a aula a remarcar
//      Dela é calculada a duração da aula a remarcar
        int classDuration = (int) MINUTES.between(classSchedule.getScheduleInstant().getScheduleTime().getStartTime(),
                classSchedule.getScheduleInstant().getScheduleTime().getEndTime());

//      Lista de possibilidades de "slots" para remarcação
        ArrayList<LineSchedule> possibilityList = new ArrayList<>();
        ArrayList<LineSchedule> alreadyScheduledList = dataModel.getScheduleEntries();

//      Caminho seguido para aulas padrão, de 90 minutos
        if (classDuration == 90) {
//          Por cada preferência indicada
            for (SchedulePeriod sp1 : allowedPeriods)
                if (sp1.getIsWeekDay()) // Por cada preferência do tipo "dia da semana"
                    for (RoomPreference rp : roomTypePreferences) { // Por cada preferência de tipo de sala
                        List<Room> resultRoomList = dataModel.roomTypeSearch(rp);
                        for (Room resultRoom : resultRoomList)
                            for (SchedulePeriod sp2 : allowedPeriods)
                                if (sp2.getIsTimePeriod()) // Por cada preferência do tipo "período do dia" (manhã, tarde, noite)
                                    for (SchedulePeriod timeSlot : sp2.getTimeSlotList()) // Por cada horário desse "período do dia"
                                        possibilityList.add(createSchedulePossibility(classSchedule, sp1, resultRoom, timeSlot));
                    }

            for (LineSchedule t : possibilityList)
                System.out.println(t.toString());


        } else {
//          Caminho seguido para aulas com duração diferente de 90 minutos
//          EM CONSTRUÇÃO
        }

    }

    public LineSchedule createSchedulePossibility
            (LineSchedule classSchedule, SchedulePeriod dayPeriod, Room resultRoom, SchedulePeriod timeSlot ) {
        LineSchedule auxSchedule = new LineSchedule(classSchedule);
        LocalDate classDate = classSchedule.getScheduleInstant().getScheduleDate();
        LocalDate auxDate = classDate.with(TemporalAdjusters.nextOrSame(dayPeriod.getPreferredDay()));

        ScheduleInstant auxInstant = new ScheduleInstant(auxDate, timeSlot);
        auxSchedule.setScheduleInstant(auxInstant);

        auxSchedule.setDia_semana(auxInstant.weekDayToString());
        auxSchedule.setHora_inicio(auxInstant.getScheduleTime().getStartTime().toString());
        auxSchedule.setHora_fim(auxInstant.getScheduleTime().getEndTime().toString());
        auxSchedule.setData_aula(auxInstant.getScheduleDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        auxSchedule.setSala(resultRoom.getNomeSala());
        auxSchedule.setCaracteristicasSala(resultRoom.getRoomSpec());

        return auxSchedule;
    }

    public List<Room> roomTypeSearch (RoomPreference roomPreference) {
        List<Room> resultRoomList = new ArrayList<>();
        for (Room room : dataModel.getRoomEntries())
            if (room.getRoomSpec().equals(roomPreference.toString())) resultRoomList.add(room);
        return resultRoomList;
    }

    public static void main(String[] args) {
        LineSchedule reSchedule = new LineSchedule(
                "Curso", "UnidadeCurricular",
                "Turno", "Turma", 0, "Seg",
                "13:00:00", "14:30:00", "01/12/2022",
                "CaracterísticasSala", "Sala");

        ScheduleDataModel dataModel = new ScheduleDataModel("csv/HorarioDeExemplo.csv", false,
                "csv/CaracterizaçãoDasSalas.csv", false);
        ScheduleEngine engine = new ScheduleEngine(dataModel);

        ArrayList<SchedulePeriod> allowedPeriods = new ArrayList<SchedulePeriod>();
        allowedPeriods.add(SchedulePeriod.NOITE);
        allowedPeriods.add(SchedulePeriod.SEGUNDA_FEIRA);
        allowedPeriods.add(SchedulePeriod.TERCA_FEIRA);

        ArrayList<RoomPreference> roomTypePreferences = new ArrayList<RoomPreference>();
        roomTypePreferences.add(RoomPreference.SALA_REUNIAO);

        engine.suggestCompensation(reSchedule, new ArrayList<SchedulePeriod>(), allowedPeriods,
                roomTypePreferences, new ArrayList<Room>());
    }

}
