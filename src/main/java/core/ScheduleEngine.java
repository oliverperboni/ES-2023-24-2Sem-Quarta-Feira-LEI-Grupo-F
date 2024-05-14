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
     * Generates a list of potential schedules for a new course based on provided constraints and preferences.
     *
     * @param courseName           The name of the course.
     * @param curricularUnit       The curricular unit associated with the course.
     * @param classTurn            The turn of the class (e.g., morning, afternoon, evening).
     * @param className            The name of the class.
     * @param studentCount         The number of students enrolled in the course.
     * @param weekCount            The number of weeks to generate schedules for.
     * @param excludedPeriods      List of periods to be excluded from scheduling.
     * @param allowedPeriods       List of periods allowed for scheduling.
     * @param roomTypePreferences List of preferences for room types.
     * @param roomTypeExclusions  List of room types to be excluded from scheduling.
     * @param startDate            The start date for scheduling.
     * @return                     An ArrayList of LineSchedule objects representing potential schedules.
     */
    public ArrayList<LineSchedule> suggestNewCourse(String courseName, String curricularUnit, String classTurn, String className, int studentCount, int weekCount, ArrayList<SchedulePeriod> excludedPeriods,
                                                    ArrayList<SchedulePeriod> allowedPeriods, ArrayList<RoomPreference> roomTypePreferences, ArrayList<RoomPreference> roomTypeExclusions, Date startDate) {

        // Get the day of the week for the start date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate x = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String day = workDate(x);

        // Create a base class schedule
        LineSchedule classSchedule = new LineSchedule(courseName, curricularUnit, classTurn, className,
                studentCount, day, "08:00:00", "09:30:00", dtf.format(x),
                "", "");

        // Initialize possibility list and schedule map
        ArrayList<LineSchedule> possibilityList = new ArrayList<>();
        TreeMap<ScheduleInstant, List<LineSchedule>> scheduleMap = dataModel.getScheduleMap();

        // Generate schedules for each week
        for (int i = 1; i <= weekCount; i++) {
            for (SchedulePeriod sp1 : allowedPeriods) {
                if (sp1.getIsWeekDay()) { // For each weekday preference
                    for (RoomPreference rp : roomTypePreferences) { // For each room type preference
                        List<Room> resultRoomList = roomsByPreference(rp);
                        for (Room resultRoom : resultRoomList)
                            for (SchedulePeriod sp2 : allowedPeriods){
                                if (sp2.getIsTimePeriod()) // For each time period preference (morning, afternoon, evening)
                                    for (SchedulePeriod timeSlot : sp2.getTimeSlotList()) // For each time slot in the period
                                        if (createSchedulePossibility2(classSchedule, sp1, resultRoom, timeSlot, excludedPeriods, roomTypeExclusions,rp, i) != null)
                                            possibilityList.add(createSchedulePossibility2(classSchedule, sp1, resultRoom, timeSlot, excludedPeriods, roomTypeExclusions,rp, i));
                            }}
                }
            }
        }

        // Remove schedules that conflict with existing schedules
        List<LineSchedule> removeList = new ArrayList<>();
        for (LineSchedule possibility : possibilityList) {
            if (scheduleMap.containsKey(possibility.getScheduleInstant()))
                for (LineSchedule scheduled : scheduleMap.get(possibility.getScheduleInstant()))
                    if (scheduled.getScheduleInstant().getScheduleTime().compareTo(possibility.getScheduleInstant().getScheduleTime()) == 0 &&
                            scheduled.getSala().equals(possibility.getSala()))
                        removeList.add(possibility);
        }
        possibilityList.removeAll(removeList);

        // Print the size and details of the possibility list
        System.out.println(possibilityList.size());
        for (LineSchedule possibility : possibilityList)
            System.out.println(possibility.toString());

        return possibilityList;
    }

    /**
     * Creates a new LineSchedule object representing a possible schedule option based on provided parameters.
     *
     * @param classSchedule   The base LineSchedule object representing the original class schedule.
     * @param dayPeriod       The preferred day period (e.g., Monday, Tuesday) for scheduling.
     * @param resultRoom      The selected room for scheduling.
     * @param timeSlot        The preferred time slot for scheduling.
     * @param roomPreference  The room preference for the scheduling.
     * @param ExcludedTime    List of time periods to be excluded from scheduling.
     * @param ExcludedRooms   List of rooms to be excluded from scheduling.
     * @return                A LineSchedule object representing the possible schedule option, or null if not possible.
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
     * Creates a new LineSchedule object representing a possible schedule option based on provided parameters.
     *
     * @param classSchedule   The base LineSchedule object representing the original class schedule.
     * @param dayPeriod       The preferred day period (e.g., Monday, Tuesday) for scheduling.
     * @param resultRoom      The selected room for scheduling.
     * @param timeSlot        The preferred time slot for scheduling.
     * @param ExcludedTime    List of time periods to be excluded from scheduling.
     * @param ExcludedRooms   List of rooms to be excluded from scheduling.
     * @param rp              The room preference for the scheduling.
     * @param i               The week index for scheduling.
     * @return                A LineSchedule object representing the possible schedule option, or null if not possible.
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
    /**
     * Determines the abbreviated Portuguese day of the week for the given date.
     *
     * @param d The LocalDate object representing the date for which the day of the week is to be determined.
     * @return A String representing the abbreviated Portuguese day of the week (Seg, Ter, Qua, Qui, Sex).
     */
    public String workDate(LocalDate d) {
        String day = "";
        switch(d.getDayOfWeek()) {
            case MONDAY:
                day = "Seg";
                break;
            case TUESDAY:
                day = "Ter";
                break;
            case WEDNESDAY:
                day = "Qua";
                break;
            case THURSDAY:
                day = "Qui";
                break;
            case FRIDAY:
                day = "Sex";
                break;
        }
        return day;
    }

    public static void main(String[] args) {

    }
}