import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.regex.Pattern;

public class ScheduleInstant {

    private LocalDate scheduleDate;
    private SchedulePeriod scheduleTime;

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
    public String toString() {
        return scheduleTime.toString() + " " + scheduleDate.toString();
    }

    public String weekDayToString() {
        Locale prtLocal = new Locale.Builder().setLanguage("pt").setScript("Latn").setRegion("PT").build();
        String smallDisplayName =
                scheduleDate.getDayOfWeek().getDisplayName(TextStyle.SHORT_STANDALONE, prtLocal).substring(0,3);
        return Pattern.compile("^.").matcher(smallDisplayName).replaceFirst(m -> m.group().toUpperCase());
    }

}
