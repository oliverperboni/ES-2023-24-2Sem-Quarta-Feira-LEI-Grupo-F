package structures;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

/**
* The SchedulePeriod class hosts a series of constant values referring to ISCTE-IUL schedule time slots (start and
* end time), time periods (periods of a day, with start and end times), and days of the week. They are used to
* represent a user's time preferences when rescheduling a class, or when scheduling an entire new course.
*/
public class SchedulePeriod implements Comparable<SchedulePeriod> {

    // Time slots
    public static SchedulePeriod _08H00_09H30 =
            new SchedulePeriod(LocalTime.parse("08:00"), LocalTime.parse("09:30"));
    public static SchedulePeriod _09H30_11H00 =
            new SchedulePeriod(LocalTime.parse("09:30"), LocalTime.parse("11:00"));
    public static SchedulePeriod _11H00_12H30 =
            new SchedulePeriod(LocalTime.parse("11:00"), LocalTime.parse("12:30"));
    public static SchedulePeriod _13H00_14H30 =
            new SchedulePeriod(LocalTime.parse("13:00"), LocalTime.parse("14:30"));
    public static SchedulePeriod _14H30_16H00 =
            new SchedulePeriod(LocalTime.parse("14:30"), LocalTime.parse("16:00"));
    public static SchedulePeriod _16H00_17H30 =
            new SchedulePeriod(LocalTime.parse("16:00"), LocalTime.parse("17:30"));
    public static SchedulePeriod _18H00_19H30 =
            new SchedulePeriod(LocalTime.parse("18:00"), LocalTime.parse("19:30"));
    public static SchedulePeriod _19H30_21H00 =
            new SchedulePeriod(LocalTime.parse("19:30"), LocalTime.parse("21:00"));
    public static SchedulePeriod _21H00_22H30 =
            new SchedulePeriod(LocalTime.parse("21:00"), LocalTime.parse("22:30"));

    // Time periods
    public static SchedulePeriod MANHA = new SchedulePeriod(LocalTime.parse("08:00"),
            LocalTime.parse("12:30"), List.of(_08H00_09H30, _09H30_11H00, _11H00_12H30));
    public static SchedulePeriod TARDE = new SchedulePeriod(LocalTime.parse("13:00"),
            LocalTime.parse("17:30"), List.of(_13H00_14H30, _14H30_16H00, _16H00_17H30));
    public static SchedulePeriod NOITE = new SchedulePeriod(LocalTime.parse("18:00"),
            LocalTime.parse("22:30"), List.of(_18H00_19H30, _19H30_21H00, _21H00_22H30));

    // Week days
    public static SchedulePeriod SEGUNDA_FEIRA = new SchedulePeriod(DayOfWeek.MONDAY);
    public static SchedulePeriod TERCA_FEIRA = new SchedulePeriod(DayOfWeek.TUESDAY);
    public static SchedulePeriod QUARTA_FEIRA = new SchedulePeriod(DayOfWeek.WEDNESDAY);
    public static SchedulePeriod QUINTA_FEIRA = new SchedulePeriod(DayOfWeek.THURSDAY);
    public static SchedulePeriod SEXTA_FEIRA = new SchedulePeriod(DayOfWeek.FRIDAY);


    /**
     * Indicates if the SchedulePeriod instance refers to a time slot (start and end times).
     */
    final private boolean isTimeSlot;

    /**
     * LocalTime object containing the time slot or time period start time.
     */
    final private LocalTime startTime;

    /**
     * LocalTime object containing the time slot or time period end time.
     */
    final private LocalTime endTime;

    /**
     * Indicates if the SchedulePeriod instance refers to a time slot (period of a day with start and end times).
     */
    final private boolean isTimePeriod;

    /**
     * List of SchedulePeriod objects consisting of the time slots that make up a time period.
     */
    final private List<SchedulePeriod> timeSlotList;

    /**
     * Indicates if the SchedulePeriod instance refers to a day of the week.
     */
    final private boolean isWeekDay;

    /**
     * DayOfWeek object referring to the day of the week represented by the SchedulePeriod object.
     */
    final private DayOfWeek preferredDay;
    private String diaSemana="";


    /**
	* Constructor for a SchedulePeriod object representing a time slot.
    *
	* @param startTime LocalTime object containing the time slot's start time
	* @param endTime LocalTime object containing the time slot's end time
	*/
    public SchedulePeriod(LocalTime startTime, LocalTime endTime) {
        this.isTimeSlot = true;
        this.isTimePeriod = false;
        this.isWeekDay = false;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeSlotList = null;
        this.preferredDay = null;
    }

    /**
	* Constructor for a SchedulePeriod object representing a time period.
    *
	* @param startTime LocalTime object containing the time period's start time
	* @param endTime LocalTime object containing the time period's end time
	* @param timeSlots List of SchedulePeriod objects consisting of the time slots that make up the time period
	*/
    public SchedulePeriod(LocalTime startTime, LocalTime endTime, List<SchedulePeriod> timeSlots) {
        this.isTimeSlot = false;
        this.isTimePeriod = true;
        this.isWeekDay = false;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeSlotList = timeSlots;
        this.preferredDay = null;
    }

    /**
	* Constructor for a SchedulePeriod object representing a week day
    *
	* @param day DayOfWeek object referring to the week day represented by the SchedulePeriod object
	*/
    public SchedulePeriod(DayOfWeek day) {
        this.isTimeSlot = false;
        this.isTimePeriod = false;
        this.isWeekDay = true;
        this.startTime = null;
        this.endTime = null;
        this.timeSlotList = null;
        this.preferredDay = day;
    }


    public boolean getIsTimeSlot() {
        return isTimeSlot;
    }
    public boolean getIsTimePeriod() {
        return isTimePeriod;
    }
    public boolean getIsWeekDay() {
        return isWeekDay;
    }

    public LocalTime getStartTime() {
        return startTime;
    }
    public LocalTime getEndTime() {
        return endTime;
    }
    public List<SchedulePeriod> getTimeSlotList() {
        return timeSlotList;
    }
    public DayOfWeek getPreferredDay() {
        return preferredDay;
    }

    @Override
    public int compareTo(SchedulePeriod o) {
        if (isTimeSlot) {
            int startComparison = this.startTime.compareTo(o.startTime);

            if (startComparison == 0) return this.endTime.compareTo(o.endTime);
            else return startComparison;
        } else if (isTimePeriod) {
            if (this.startTime.compareTo(o.startTime) < 0 && this.endTime.compareTo(o.endTime) < 0)
                return -1;
            else if (this.startTime.compareTo(o.startTime) == 0 && this.endTime.compareTo(o.endTime) == 0)
                return 0;
            else return 1;
        } else {
            return this.preferredDay.compareTo(o.preferredDay);
        }
    }

	/**
	* Returns distinct string representations depending on if the SchedulePeriod object represents a timeslot, a
	* time period, or a week day. The week day representation matches the format found in ISCTE-IUL schedule files.
    *
	* @return String representing start and end time for time slots and periods, or first three letters of day name
	* for week days
	* @since 1.0
	*/
    @Override
    public String toString() {
        if (this.isTimeSlot)
            return this.startTime + " " + this.endTime;
        if (this.isTimePeriod)
            return "Período:" + this.startTime + " " + this.endTime;
        if (this.isWeekDay)
            return this.preferredDay.toString().substring(0, 2);
        return "Nenhum dos 3";
    }

}
