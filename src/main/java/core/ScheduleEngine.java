package core;

import structures.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

/**
 * The ScheduleEngine class hosts methods that drive the scheduling application's automatic operations over the
 * currently loaded schedule and room entries.
 *
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
     *
     * @param classSchedule LineSchedule of class to reschedule
     * @param excludedPeriods (remove)
     * @param allowedPeriods list of SchedulePeriod objects describing the user's prefered time periods for rescheduling
     * @param roomTypePreferences list of RoomPreference objects describing the user's prefered room types for rescheduling
     * @param roomTypeExclusions (remove)
     * @return ArrayList<LineSchedule> list of schedule suggestions, based on user defined rules
     * @since 1.0
     */
    public ArrayList<LineSchedule>
    suggestCompensation(LineSchedule classSchedule, ArrayList<SchedulePeriod> excludedPeriods,
                        ArrayList<SchedulePeriod> allowedPeriods, ArrayList<RoomPreference> roomTypePreferences,
                        ArrayList<RoomPreference> roomTypeExclusions) {

//      classSchedule é uma structures.LineSchedule com a aula a remarcar
//      Lista de possibilidades de "slots" para remarcação
        ArrayList<LineSchedule> possibilityList = new ArrayList<>();
        TreeMap<ScheduleInstant, List<LineSchedule>> scheduleMap = dataModel.getScheduleMap();

//      Por cada preferência indicada
        if (!allowedPeriods.isEmpty()) {
            for (SchedulePeriod sp1 : allowedPeriods)
                if (sp1.getIsWeekDay()) // Por cada preferência do tipo "dia da semana"
                    for (RoomPreference rp : roomTypePreferences) { // Por cada preferência de tipo de sala
                        List<Room> resultRoomList = roomsByPreference(rp); // Salas com o tipo procurado
                        for (Room resultRoom : resultRoomList) // Por cada sala do tipo procurado encontrada
                            for (SchedulePeriod sp2 : allowedPeriods)
                                if (sp2.getIsTimePeriod()) { // Por cada preferência do tipo "período do dia" (manhã, tarde, noite)
                                    for (SchedulePeriod timeSlot : sp2.getTimeSlotList()) // Por cada horário desse "período do dia"
                                        if(createSchedulePossibility(classSchedule, sp1, resultRoom, timeSlot, rp, excludedPeriods, roomTypeExclusions)!=null)
                                            possibilityList.add(createSchedulePossibility(classSchedule, sp1, resultRoom, timeSlot, rp, excludedPeriods, roomTypeExclusions));
                                /*} else if (sp2.getIsTimeSlot()) {
                                    possibilityList.add(createSchedulePossibility(classSchedule, sp1, resultRoom, sp2, rp, excludedPeriods, roomTypeExclusions));
                                }*/}

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
     * Given a schedule entry that's to be rescheduled, a list of allowed time periods for its compensation, as well as
     * a list of allowed room types, this function returns a list of possible schedules, with different start time,
     * end time, and room, as suggestions for the user to choose, and reschedule.
     *
     * @param courseName (change)
     * @param weekCount (change)
     * @param excludedPeriods (change)
     * @param allowedPeriods (change)
     * @param roomTypePreferences (change)

     * @return ArrayList<LineSchedule> list of schedule suggestions, based on user defined rules
     * @since 1.0
     */
    public ArrayList<LineSchedule>
    suggestNewCourse(String courseName, String curricularUnit, String classTurn, String className, int studentCount, int weekCount, ArrayList<SchedulePeriod> excludedPeriods,
                     ArrayList<SchedulePeriod> allowedPeriods, ArrayList<RoomPreference> roomTypePreferences, ArrayList<RoomPreference> roomTypeExclusions, Date startDate) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate x = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String day = workDate(x);


        LineSchedule classSchedule = new LineSchedule(courseName, curricularUnit, classTurn, className,
                studentCount, day, "08:00:00", "09:30:00", dtf.format(x),
                "", "");
        ArrayList<LineSchedule> possibilityList = new ArrayList<>();
        TreeMap<ScheduleInstant, List<LineSchedule>> scheduleMap = dataModel.getScheduleMap();



        for (int i = 1; i <= weekCount; i++) {
            for (SchedulePeriod sp1 : allowedPeriods) {
                if (sp1.getIsWeekDay()) { // Por cada preferência do tipo "dia da semana"

                    for (RoomPreference rp : roomTypePreferences) { // Por cada preferência de tipo de sala
                        List<Room> resultRoomList = roomsByPreference(rp);
                        for (Room resultRoom : resultRoomList)
                            for (SchedulePeriod sp2 : allowedPeriods){
                                if (sp2.getIsTimePeriod()) // Por cada preferência do tipo "período do dia" (manhã, tarde, noite)
                                    for (SchedulePeriod timeSlot : sp2.getTimeSlotList()) // Por cada horário desse "período do dia"
                                        if (createSchedulePossibility2(classSchedule, sp1, resultRoom, timeSlot, excludedPeriods, roomTypeExclusions,rp, i) != null)
                                            possibilityList.add(createSchedulePossibility2(classSchedule, sp1, resultRoom, timeSlot, excludedPeriods, roomTypeExclusions,rp, i));
                            }}
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
     * @param classSchedule  LineSchedule object of the schedule entry to be rescheduled
     * @param dayPeriod      SchedulePeriod object for the new day of the week for the schedule entry
     * @param resultRoom     Room object for the new room
     * @param timeSlot       SchedulePeriod object for the new time slot, and start and end times
     * @param roomPreference RoomPreference object to obtain the new room's specfication
     * @return LineSchedule object representing a copy of the original, but with schedule and room altered
     * @since 1.0
     */
    public LineSchedule
    createSchedulePossibility(LineSchedule classSchedule, SchedulePeriod dayPeriod, Room resultRoom,
                              SchedulePeriod timeSlot, RoomPreference roomPreference, ArrayList<SchedulePeriod> ExcludedTime,
                              ArrayList<RoomPreference> ExcludedRooms) {

        LineSchedule auxSchedule = new LineSchedule(classSchedule);
        LocalDate classDate = classSchedule.getScheduleInstant().getScheduleDate();
        LocalDate auxDate = classDate.with(TemporalAdjusters.nextOrSame(dayPeriod.getPreferredDay()));

        ScheduleInstant auxInstant = new ScheduleInstant(auxDate, timeSlot);
        auxSchedule.setScheduleInstant(auxInstant);

        for (SchedulePeriod a : ExcludedTime)
            if (a.equals(auxSchedule.getScheduleInstant().getScheduleTime()) || a.getWeekDay().equals(auxInstant.weekDayToString())) {
                return null;
            }

        auxSchedule.setDia_semana(auxInstant.weekDayToString());
        auxSchedule.setHora_inicio(auxInstant.getScheduleTime().getStartTime().toString());
        auxSchedule.setHora_fim(auxInstant.getScheduleTime().getEndTime().toString());
        auxSchedule.setData_aula(auxInstant.getScheduleDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        auxSchedule.setSala(resultRoom.getNomeSala());
        auxSchedule.setCaracteristicasSala(roomPreference.toString());

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
    createSchedulePossibility2(LineSchedule classSchedule, SchedulePeriod dayPeriod, Room resultRoom,
                               SchedulePeriod timeSlot,ArrayList<SchedulePeriod> ExcludedTime, ArrayList<RoomPreference> ExcludedRooms, RoomPreference rp, int i) {

        LineSchedule auxSchedule = new LineSchedule(classSchedule);
        LocalDate classDate = classSchedule.getScheduleInstant().getScheduleDate();
        LocalDate auxDate = classDate.with(TemporalAdjusters.dayOfWeekInMonth(i, dayPeriod.getPreferredDay()));

        ScheduleInstant auxInstant = new ScheduleInstant(auxDate, timeSlot);
        auxSchedule.setScheduleInstant(auxInstant);

        for (SchedulePeriod a : ExcludedTime)
            if (a.equals(auxSchedule.getScheduleInstant().getScheduleTime()) || a.getWeekDay().equals(auxInstant.weekDayToString())) {
                return null;
            }
        for(RoomPreference b: ExcludedRooms){
            List<Room> resultRoomList = roomsByPreference(b);
            for(Room c : resultRoomList){
                if(c.equals(resultRoom)){
                    return null;
                }
            }
        }


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
    public String workDate(LocalDate d){

        String day="";
        switch(d.getDayOfWeek()){
            case MONDAY -> day="Seg";
            case TUESDAY -> day="Ter";
            case WEDNESDAY -> day="Qua";
            case THURSDAY -> day="Qui";
            case FRIDAY -> day="Sex";
        }
        return day;

    }

    public static void main(String[] args) {

    }
}