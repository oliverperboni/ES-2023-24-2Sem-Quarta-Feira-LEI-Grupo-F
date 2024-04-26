package schedule;

import structures.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.*;
import static java.time.temporal.ChronoUnit.MINUTES;

/**
* The ScheduleEngine class hosts methods that drive the scheduling application's automatic operations over the
* currently loaded schedule and room entries.
* @author António Pombeiro
*/
public class ScheduleEngine {

    private ScheduleDataModel dataModel;

    public ScheduleEngine(ScheduleDataModel dataModel) {
        this.dataModel = dataModel;
    }

    public void suggestCompensation(LineSchedule classSchedule, ArrayList<SchedulePeriod> excludedPeriods,
                                    ArrayList<SchedulePeriod> allowedPeriods, ArrayList<RoomPreference> roomTypePreferences,
                                    ArrayList<RoomPreference> roomTypeExclusions) {

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
                        List<Room> resultRoomList = roomTypeSearch(rp);
                        for (Room resultRoom : resultRoomList)
                            for (SchedulePeriod sp2 : allowedPeriods)
                                if (sp2.getIsTimePeriod()) // Por cada preferência do tipo "período do dia" (manhã, tarde, noite)
                                    for (SchedulePeriod timeSlot : sp2.getTimeSlotList()) // Por cada horário desse "período do dia"
                                        possibilityList.add(createSchedulePossibility(classSchedule, sp1, resultRoom, timeSlot));
                    }

            for (LineSchedule t : possibilityList)
                out.println(t.toString());
        } else {
//          Caminho seguido para aulas com duração diferente de 90 minutos
//          EM CONSTRUÇÃO
        }

    }

	/**
	* Given a schedule entry that's to be rescheduled, this method creates a copy of it, and then replaces its
	* date, start and end times, week day, room and room type. 
	* @param classSchedule LineSchedule object of the schedule entry to be rescheduled
	* @param dayPeriod SchedulePeriod object for the new day of the week for the schedule entry
	* @param resultRoom Room object for the new room
	* @param timeSlot SchedulePeriod object for the new time slot, and start and end times
	* @return LineSchedule object representing a copy of the original, but with schedule and room altered
	* @since 1.0
	*/
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
//        auxSchedule.setCaracteristicasSala(resultRoom.getRoomSpecs());

        return auxSchedule;
    }

	/**
	* Searches the currently loaded room entries for rooms matching a provided RoomPreference, and returns them in
	* a list.
	* @param roomPreference RoomPreference object representing desired room specification 
	* @return List of all rooms matching the criteria, represented by Room objects
	* @since 1.0
	*/
    public List<Room> roomTypeSearch (RoomPreference roomPreference) {
        List<Room> resultRoomList = new ArrayList<>();
        for (Room room : dataModel.getRoomEntries())
            if (room.getRoomSpecs().equals(roomPreference.toString())) resultRoomList.add(room);
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
                roomTypePreferences, new ArrayList<RoomPreference>());
    }

}
