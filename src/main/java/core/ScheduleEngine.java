package core;

import structures.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static java.time.temporal.ChronoUnit.MINUTES;

/**
 * The ScheduleEngine class hosts methods that drive the scheduling application's automatic operations over the
 * currently loaded schedule and room entries.
 *
 * @author António Pombeiro
 */
public class ScheduleEngine {
    private ScheduleDataModel dataModel;

    public ScheduleEngine(ScheduleDataModel dataModel) {
        this.dataModel = dataModel;
    }

    public void suggestCompensation(LineSchedule classSchedule, ArrayList<SchedulePeriod> excludedPeriods,
                                    ArrayList<SchedulePeriod> allowedPeriods, ArrayList<RoomPreference> roomTypePreferences,
                                    ArrayList<Room> roomTypeExclusions) {

//      classSchedule é uma LineSchedule com a aula a remarcar
//      Dela é calculada a duração da aula a remarcar
//        int classDuration = (int) MINUTES.between(classSchedule.getScheduleInstant().getScheduleTime().getStartTime(),
//                classSchedule.getScheduleInstant().getScheduleTime().getEndTime());

//      Lista de possibilidades de "slots" para remarcação
        ArrayList<LineSchedule> possibilityList = new ArrayList<>();
        TreeMap<ScheduleInstant, List<LineSchedule>> scheduleMap = dataModel.getScheduleMap();

//      Por cada preferência indicada
        if (!allowedPeriods.isEmpty()) {
            for (SchedulePeriod sp1 : allowedPeriods)
                if (sp1.getIsWeekDay()) // Por cada preferência do tipo "dia da semana"
                    for (RoomPreference rp : roomTypePreferences) { // Por cada preferência de tipo de sala
                        List<Room> resultRoomList = roomsByPreference(rp);
                        for (Room resultRoom : resultRoomList)
                            for (SchedulePeriod sp2 : allowedPeriods)
                                if (sp2.getIsTimePeriod()) // Por cada preferência do tipo "período do dia" (manhã, tarde, noite)
                                    for (SchedulePeriod timeSlot : sp2.getTimeSlotList()) // Por cada horário desse "período do dia"
                                        possibilityList.add(createSchedulePossibility(classSchedule, sp1, resultRoom, timeSlot, rp));
                    }
        }

        List<LineSchedule> removeList = new ArrayList<>();
        for (LineSchedule possibility : possibilityList) {
            if (scheduleMap.containsKey(possibility.getScheduleInstant()))
                for (LineSchedule scheduled : scheduleMap.get(possibility.getScheduleInstant()))
                    if (scheduled.getScheduleInstant().getScheduleTime().compareTo(possibility.getScheduleInstant().getScheduleTime()) == 0 &&
                            scheduled.getSala().equals(possibility.getSala()))
                        removeList.add(possibility);
        }

        possibilityList.removeAll(removeList);

        System.out.println(possibilityList.size());
        for (LineSchedule possibility : possibilityList)
            System.out.println(possibility.toString());
    }

//    public ArrayList<LineSchedule>
//    suggestClass(String UC, int quantidade_aulas, ArrayList<SchedulePeriod> excludedPeriods,
//                 ArrayList<SchedulePeriod> allowedPeriods, ArrayList<RoomPreference> roomTypePreferences,
//                 ArrayList<Room> roomTypeExclusions) {
//
//        LineSchedule classSchedule = null;
//        ArrayList<LineSchedule> possibilityList = new ArrayList<>();
////        ArrayList<LineSchedule> alreadyScheduledList = dataModel.getScheduleEntries();
//
//
//        for (LineSchedule t : alreadyScheduledList) {
//            if (t.getUnidadeCurricular().equals(UC)) {
//                classSchedule = new LineSchedule(t);
//                System.out.println(classSchedule.getDataAula());
//                break;
//            }
//        }
//
//        for (int i = 1; i <= quantidade_aulas; i++) {
//            //System.out.println("oi");
//            for (SchedulePeriod sp1 : allowedPeriods) {
//
//                if (sp1.getIsWeekDay()) {// Por cada preferência do tipo "dia da semana"
//
//
//                    for (RoomPreference rp : roomTypePreferences) { // Por cada preferência de tipo de sala
//                        List<Room> resultRoomList = roomTypeSearch(rp);
//                        for (Room resultRoom : resultRoomList)
//                            for (SchedulePeriod sp2 : allowedPeriods)
//                                if (sp2.getIsTimePeriod()) // Por cada preferência do tipo "período do dia" (manhã, tarde, noite)
//                                    for (SchedulePeriod timeSlot : sp2.getTimeSlotList()) { // Por cada horário desse "período do dia"
//                                        if (createSchedulePossibility2(classSchedule, sp1, resultRoom, timeSlot, rp, excludedPeriods, roomTypeExclusions, i) != null) {
//                                            possibilityList.add(createSchedulePossibility2(classSchedule, sp1, resultRoom, timeSlot, rp, excludedPeriods, roomTypeExclusions, i));
//                                        } else {
//                                        }
//                                    }
//                    }
//                }
//            }
//        }
//
//        for (LineSchedule t : possibilityList) {
//            System.out.println(t);
//        }
//        return possibilityList;
//
//    }

    /**
     * Given a schedule entry that's to be rescheduled, this method creates a copy of it, and then replaces its
     * date, start and end times, week day, room and room type.
     *
     * @param classSchedule LineSchedule object of the schedule entry to be rescheduled
     * @param dayPeriod     SchedulePeriod object for the new day of the week for the schedule entry
     * @param resultRoom    Room object for the new room
     * @param timeSlot      SchedulePeriod object for the new time slot, and start and end times
     * @return LineSchedule object representing a copy of the original, but with schedule and room altered
     * @since 1.0
     */
    public LineSchedule createSchedulePossibility
    (LineSchedule classSchedule, SchedulePeriod dayPeriod, Room resultRoom, SchedulePeriod timeSlot, RoomPreference rp) {
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
        auxSchedule.setCaracteristicasSala(rp.toString());

        return auxSchedule;
    }

    /**
     * Searches the currently loaded room entries for rooms matching a provided RoomPreference, and returns them in
     * a list.
     *
     * @param roomPreference RoomPreference object representing desired room specification
     * @return List of all rooms matching the criteria, represented by Room objects
     * @since 1.0
     */
    public List<Room> roomsByPreference(RoomPreference roomPreference) {
        List<Room> resultRoomList = new ArrayList<>();
        for (Room room : dataModel.getRoomEntries())
            for (String roomSpec : room.getRoomSpecs())
                if (roomSpec.equals(roomPreference.toString()) && !resultRoomList.contains(room))
                    resultRoomList.add(room);
        return resultRoomList;
    }

    public List<String> specsFromRoomName(String roomName) {
        for (Room auxRoom : dataModel.getRoomEntries())
            if (auxRoom.getNomeSala().equals(roomName)) return auxRoom.getRoomSpecs();
        return null;
    }

    public static void main(String[] args) {
        LineSchedule reSchedule = new LineSchedule(
                "Curso", "UnidadeCurricular",
                "Turno", "Turma", 0, "Seg",
                "13:00:00", "14:30:00", "14/11/2022",
                "CaracterísticasSala", "Sala");

        ScheduleDataModel dataModel = new ScheduleDataModel("csv/HorarioDeExemplo.csv", false,
                "csv/CaracterizaçãoDasSalas.csv", false);
        ScheduleEngine engine = new ScheduleEngine(dataModel);

        ArrayList<SchedulePeriod> allowedPeriods = new ArrayList<SchedulePeriod>();
        allowedPeriods.add(SchedulePeriod.NOITE);
        allowedPeriods.add(SchedulePeriod.SEGUNDA_FEIRA);
        allowedPeriods.add(SchedulePeriod.TERCA_FEIRA);

        ArrayList<RoomPreference> roomTypePreferences = new ArrayList<RoomPreference>();
        roomTypePreferences.add(RoomPreference.SALA_AULAS_NORMAL);

        engine.suggestCompensation(reSchedule, new ArrayList<SchedulePeriod>(), allowedPeriods,
                roomTypePreferences, new ArrayList<Room>());
    }

}
