package structures;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.regex.Pattern;

/**
* The ScheduleInstant class represents an ISCTE-IUL time slot and date, a schedule time slot at a precise point in 
* time.
*
* @author António Pombeiro
*/
public class ScheduleInstant implements Comparable<ScheduleInstant> {

	/**
	* LocalDate object containing this ScheduleInstant's date.
	*/
    private LocalDate scheduleDate;
	
	/**
	* SchedulePeriod object representing this ScheduleInstant's time slot.
	*/
    private SchedulePeriod scheduleTime;

	/**
	* Constructor for a ScheduleInstant object representing a time slot and date.
    *
	* @param scheduleDate LocalDate object containing this ScheduleInstant's date
	* @param scheduleTime SchedulePeriod object representing this ScheduleInstant's time slot
	*/
    public ScheduleInstant(LocalDate scheduleDate, SchedulePeriod scheduleTime) {
        this.scheduleDate = scheduleDate;
        if (scheduleTime.getIsTimeSlot()) {
            this.scheduleTime = scheduleTime;
        } else {
            throw new IllegalArgumentException("Período inserido não corresponde a um intervalo entre horas.");
        }
    }

    public LocalDate getScheduleDate() {
        return scheduleDate;
    }

    public SchedulePeriod getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleDate(LocalDate scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public void setScheduleTime(SchedulePeriod scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    @Override
    public int compareTo(ScheduleInstant o) {
        if (!this.scheduleDate.isEqual(o.getScheduleDate())) return this.scheduleDate.compareTo(o.getScheduleDate());
        else return this.scheduleTime.getStartTime().compareTo(o.getScheduleTime().getStartTime());
    }

	/**
	* Returns a customized string representation for the ScheduleInstant object.
    *
	* @return String with string representations of SchedulePeriod time slot object and LocalDate object
	* @since 1.0
	*/
    @Override
    public String toString() {
        return scheduleTime.toString() + " " + scheduleDate.toString();
    }

	/**
	* Returns a three-letter, first letter in uppercase, portuguese (Portugal) locale formatted, string
	* representation this ScheduleInstant's date day of the week.
    *
	* @return String first three letters of week day name in portuguese
	* @since 1.0
	*/
    public String weekDayToString() {
        Locale prtLocal = new Locale.Builder().setLanguage("pt").setScript("Latn").setRegion("PT").build();
        String smallDisplayName =
                scheduleDate.getDayOfWeek().getDisplayName(TextStyle.SHORT_STANDALONE, prtLocal).substring(0,3);
        return Pattern.compile("^.").matcher(smallDisplayName).replaceFirst(m -> m.group().toUpperCase());
    }

}
