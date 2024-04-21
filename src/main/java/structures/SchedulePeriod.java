package structures;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public class SchedulePeriod {

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


    final private boolean isTimeSlot;
    final private LocalTime startTime;
    final private LocalTime endTime;

    final private boolean isTimePeriod;
    final private List<SchedulePeriod> timeSlotList;

    final private boolean isWeekDay;
    final private DayOfWeek preferredDay;


    // Constructor for time slot
    public SchedulePeriod(LocalTime startTime, LocalTime endTime) {
        this.isTimeSlot = true;
        this.isTimePeriod = false;
        this.isWeekDay = false;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeSlotList = null;
        this.preferredDay = null;
    }

    // Constructor for time period
    public SchedulePeriod(LocalTime startTime, LocalTime endTime, List<SchedulePeriod> timeSlots) {
        this.isTimeSlot = false;
        this.isTimePeriod = true;
        this.isWeekDay = false;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeSlotList = timeSlots;
        this.preferredDay = null;
    }

    // Constructor for week day
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
