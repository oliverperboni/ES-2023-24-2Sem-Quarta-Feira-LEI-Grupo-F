import java.time.LocalDate;

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
        return scheduleDate.toString() + " " + scheduleTime.toString();
    }

    public String weekDayToString() {
        return scheduleDate.getDayOfWeek().toString().substring(0,2);
    }

}
