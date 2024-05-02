package core;

import structures.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

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

    /**
     * Given a schedule entry that's to be rescheduled, a list of allowed time periods for its compensation, as well as
     * a list of allowed room types, this function returns a list of possible schedules, with different start time,
     * end time, and room, as suggestions for the user to choose, and reschedule.
     * @param classSchedule
     * @param excludedPeriods
     * @param allowedPeriods
     * @param roomTypePreferences
     * @param roomTypeExclusions
     * @return ArrayList<LineSchedule> list of schedule suggestions, based on user defined rules
     */
    public ArrayList<LineSchedule>
    suggestCompensation(LineSchedule classSchedule, ArrayList<SchedulePeriod> excludedPeriods,
                        ArrayList<SchedulePeriod> allowedPeriods, ArrayList<RoomPreference> roomTypePreferences,
                        ArrayList<Room> roomTypeExclusions) {

//      classSchedule é uma structures.LineSchedule com a aula a remarcar

//      Dela é calculada a duração da aula a remarcar
//        int classDuration = (int) MINUTES.between(classSchedule.getScheduleInstant().getScheduleTime().getStartTime(),
//                classSchedule.getScheduleInstant().getScheduleTime().getEndTime());

//      Lista de possibilidades de "slots" para remarcação
        ArrayList<LineSchedule> possibilityList = new ArrayList<>();
        TreeMap<ScheduleInstant, List<LineSchedule>> scheduleMap = dataModel.getScheduleMap();

//      Por cada preferência indicada
        if (!allowedPeriods.isEmpty()) {
            for (SchedulePeriod sp1 : allowedPeriods) {
                if (sp1.getIsWeekDay()) // Por cada preferência do tipo "dia da semana"
                    for (RoomPreference rp : roomTypePreferences) { // Por cada preferência de tipo de sala
                        List<Room> resultRoomList = roomsByPreference(rp);
                        for (Room resultRoom : resultRoomList)
                            for (SchedulePeriod sp2 : allowedPeriods)
                                if (sp2.getIsTimePeriod()) // Por cada preferência do tipo "período do dia" (manhã, tarde, noite)
                                    for (SchedulePeriod timeSlot : sp2.getTimeSlotList()) { // Por cada horário desse "período do dia"
                                        if (createSchedulePossibility(classSchedule, sp1, resultRoom, timeSlot, rp, excludedPeriods, roomTypeExclusions) != null) {
                                            possibilityList.add(createSchedulePossibility(classSchedule, sp1, resultRoom, timeSlot, rp, excludedPeriods, roomTypeExclusions));
                                        }
                                    }


                    }
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

        return possibilityList;
    }

    /**
     *
     * @param courseName
     * @param classCount
     * @param excludedPeriods
     * @param allowedPeriods
     * @param roomTypePreferences
     * @param roomTypeExclusions
     * @return
     */
    public ArrayList<LineSchedule>
    suggestNewCourse(String courseName, int classCount, ArrayList<SchedulePeriod> excludedPeriods,
                     ArrayList<SchedulePeriod> allowedPeriods, ArrayList<RoomPreference> roomTypePreferences,
                     ArrayList<Room> roomTypeExclusions) {

        LineSchedule classSchedule = new LineSchedule("", "", "", "",
                0, "", "08:00:00", "09:30:00", "11/11/2022",
                "", "");
        ArrayList<LineSchedule> possibilityList = new ArrayList<>();
        TreeMap<ScheduleInstant, List<LineSchedule>> scheduleMap = dataModel.getScheduleMap();

        for (LineSchedule t : dataModel.getScheduleEntries()) {
            if (t.getUnidadeCurricular().equals(courseName)) {
                classSchedule = new LineSchedule(t);
                System.out.println(classSchedule.getDataAula());
                break;
            }
        }

        for (int i = 1; i <= classCount; i++) {
            for (SchedulePeriod sp1 : allowedPeriods) {
                if (sp1.getIsWeekDay()) { // Por cada preferência do tipo "dia da semana"
                    for (RoomPreference rp : roomTypePreferences) { // Por cada preferência de tipo de sala
                        List<Room> resultRoomList = roomsByPreference(rp);
                        for (Room resultRoom : resultRoomList)
                            for (SchedulePeriod sp2 : allowedPeriods)
                                if (sp2.getIsTimePeriod()) // Por cada preferência do tipo "período do dia" (manhã, tarde, noite)
                                    for (SchedulePeriod timeSlot : sp2.getTimeSlotList()) // Por cada horário desse "período do dia"
                                        if (createSchedulePossibility2(classSchedule, sp1, resultRoom, timeSlot, rp, excludedPeriods, roomTypeExclusions, i) != null)
                                            possibilityList.add(createSchedulePossibility2(classSchedule, sp1, resultRoom, timeSlot, rp, excludedPeriods, roomTypeExclusions, i));
                    }
                }
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

        return possibilityList;
    }

    /**
     * Given a schedule entry that's to be rescheduled, this method creates a copy of it, and then replaces its
     * date, start and end times, week day, room and room type.
     *
     * @param classSchedule LineSchedule object of the schedule entry to be rescheduled
     * @param dayPeriod     SchedulePeriod object for the new day of the week for the schedule entry
     * @param resultRoom    Room object for the new room
     * @param timeSlot      SchedulePeriod object for the new time slot, and start and end times
     * @param rp            RoomPreference object to obtain the new room's specfication
     * @return LineSchedule object representing a copy of the original, but with schedule and room altered
     * @since 1.0
     */
    public LineSchedule
    createSchedulePossibility (LineSchedule classSchedule, SchedulePeriod dayPeriod, Room resultRoom,
                               SchedulePeriod timeSlot, RoomPreference rp, ArrayList<SchedulePeriod> ExcludedTime,
                               ArrayList<Room> ExcludedRooms) {

        LineSchedule auxSchedule = new LineSchedule(classSchedule);
        LocalDate classDate = classSchedule.getScheduleInstant().getScheduleDate();
        LocalDate auxDate = classDate.with(TemporalAdjusters.nextOrSame(dayPeriod.getPreferredDay()));

        ScheduleInstant auxInstant = new ScheduleInstant(auxDate, timeSlot);
        auxSchedule.setScheduleInstant(auxInstant);

//        for (SchedulePeriod a : ExcludedTime)
//            if (a.equals(auxSchedule.getScheduleInstant().getScheduleTime()) || a.getWeekDay().equals(auxInstant.weekDayToString()))
//                return null;
        /*for(String b : ExcludedRooms){
            System.out.println(b);
            System.out.println(resultRoom.getNomeSala());
            if (b.equals(auxSchedule.getSala())) {
                return null;
            }
        }*/

        auxSchedule.setDia_semana(auxInstant.weekDayToString());
        auxSchedule.setHora_inicio(auxInstant.getScheduleTime().getStartTime().toString());
        auxSchedule.setHora_fim(auxInstant.getScheduleTime().getEndTime().toString());
        auxSchedule.setData_aula(auxInstant.getScheduleDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        auxSchedule.setSala(resultRoom.getNomeSala());
        auxSchedule.setCaracteristicasSala(rp.toString());

        return auxSchedule;
    }

    /**
     * Given a schedule entry that's to be rescheduled, this method creates a copy of it, and then replaces its
     * date, start and end times, week day, room and room type.
     *
     * @param classSchedule LineSchedule object of the schedule entry to be rescheduled
     * @param dayPeriod     SchedulePeriod object for the new day of the week for the schedule entry
     * @param resultRoom    Room object for the new room
     * @param timeSlot      SchedulePeriod object for the new time slot, and start and end times
     * @param rp            RoomPreference object to obtain the new room's specfication
     * @return LineSchedule object representing a copy of the original, but with schedule and room altered
     * @since 1.0
     */
    public LineSchedule
    createSchedulePossibility2 (LineSchedule classSchedule, SchedulePeriod dayPeriod, Room resultRoom,
                                SchedulePeriod timeSlot, RoomPreference rp, ArrayList<SchedulePeriod> ExcludedTime,
                                ArrayList<Room> ExcludedRooms, int i) {

        LineSchedule auxSchedule = new LineSchedule(classSchedule);
        LocalDate classDate = classSchedule.getScheduleInstant().getScheduleDate();
        LocalDate auxDate = classDate.with(TemporalAdjusters.dayOfWeekInMonth(i, dayPeriod.getPreferredDay()));

        ScheduleInstant auxInstant = new ScheduleInstant(auxDate, timeSlot);
        auxSchedule.setScheduleInstant(auxInstant);

//        for (SchedulePeriod a : ExcludedTime)
//            if (a.equals(auxSchedule.getScheduleInstant().getScheduleTime()) || a.getPreferredDay().equals(auxInstant.getScheduleDate().getDayOfWeek()))
//                return null;
        /*for(String b : ExcludedRooms){
            System.out.println(b);
            System.out.println(resultRoom.getNomeSala());
            if (b.equals(auxSchedule.getSala())) {
                return null;
            }
        }*/

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

    public static void main(String[] args) {
        LineSchedule reSchedule = new LineSchedule(
                "Curso", "UnidadeCurricular",
                "Turno", "Turma", 0, "Seg",
                "13:00:00", "14:30:00", "01/12/2022",
                "CaracterísticasSala", "Sala");

        ScheduleDataModel dataModel = new ScheduleDataModel("csv/HorarioDeExemplo.csv", false,
                "csv/CaracterizaçãoDasSalas.csv", false);
        ScheduleEngine engine = new ScheduleEngine(dataModel);

        ArrayList<SchedulePeriod> allowedPeriods1 = new ArrayList<SchedulePeriod>();
        ArrayList<SchedulePeriod> allowedPeriods2 = new ArrayList<SchedulePeriod>();
//        ArrayList<SchedulePeriod> excludedPeriods = new ArrayList<SchedulePeriod>();
//        ArrayList<Room> excludedRoom = new ArrayList<Room>();

        allowedPeriods1.add(SchedulePeriod.NOITE);
        allowedPeriods1.add(SchedulePeriod.SEGUNDA_FEIRA);
        allowedPeriods1.add(SchedulePeriod.TERCA_FEIRA);

        allowedPeriods2.add(SchedulePeriod.QUARTA_FEIRA);
        allowedPeriods2.add(SchedulePeriod.QUINTA_FEIRA);
        allowedPeriods2.add(SchedulePeriod.SEXTA_FEIRA);

        //excludedPeriods.add(SchedulePeriod.SEGUNDA_FEIRA);
        //excludedPeriods.add(SchedulePeriod._21H00_22H30);
        //excludedRoom.add("B1.04");

        ArrayList<RoomPreference> roomTypePreferences1 = new ArrayList<RoomPreference>();
        roomTypePreferences1.add(RoomPreference.SALA_AULAS_NORMAL);
        roomTypePreferences1.add(RoomPreference.SALA_AULAS_MESTRADO);

        ArrayList<RoomPreference> roomTypePreferences2 = new ArrayList<RoomPreference>();
        roomTypePreferences2.add(RoomPreference.SALA_AULAS_NORMAL);
        roomTypePreferences2.add(RoomPreference.SALA_AULAS_MESTRADO);

//        engine.suggestCompensation(reSchedule, new ArrayList<SchedulePeriod>(), allowedPeriods1,
//                roomTypePreferences1, new ArrayList<Room>());
        engine.suggestNewCourse("Unidade curricular de teste", 7, new ArrayList<SchedulePeriod>(), allowedPeriods2,
                roomTypePreferences2, new ArrayList<Room>());

    }

}

