import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class PreferencePeriod {

    public PreferencePeriod MANHA = new PreferencePeriod(false, true, null,
            LocalTime.parse("08:00"),
            LocalTime.parse("12:30"));

    public PreferencePeriod TARDE = new PreferencePeriod(false, true, null,
            LocalTime.parse("13:00"),
            LocalTime.parse("17:30"));

    public PreferencePeriod NOITE = new PreferencePeriod(false, true, null,
            LocalTime.parse("18:00"),
            LocalTime.parse("22:30"));

    public PreferencePeriod SEGUNDA_FEIRA = new PreferencePeriod(true, false,
            DayOfWeek.MONDAY, null, null);
    public PreferencePeriod TERÇA_FEIRA = new PreferencePeriod(true, false,
            DayOfWeek.TUESDAY, null, null);
    public PreferencePeriod QUARTA_FEIRA = new PreferencePeriod(true, false,
            DayOfWeek.WEDNESDAY, null, null);
    public PreferencePeriod QUINTA_FEIRA = new PreferencePeriod(true, false,
            DayOfWeek.THURSDAY, null, null);
    public PreferencePeriod SEXTA_FEIRA = new PreferencePeriod(true, false,
            DayOfWeek.FRIDAY, null, null);

    final private boolean isWeekDay;
    final private boolean isTimePeriod;
    final private DayOfWeek preferredDay;
    final private LocalTime startTime;
    final private LocalTime endTime;

    public PreferencePeriod(boolean isWeekDay, boolean isTimePeriod, DayOfWeek day, LocalTime startTime, LocalTime endTime) {
        this.isWeekDay = isWeekDay;
        this.isTimePeriod = isTimePeriod;
        this.preferredDay = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public boolean getIsWeekDay() {
        return isWeekDay;
    }

    public boolean getIsTimePeriod() {
        return isTimePeriod;
    }

    public DayOfWeek getPreferredDay() {
        return preferredDay;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        if (this.isWeekDay) {
            Locale portugal = new Locale.Builder().setLanguage("por").setScript("Latn").setRegion("PRT").build();
            return this.preferredDay.getDisplayName(TextStyle.SHORT, portugal);
        } else return toString();
    }

}
