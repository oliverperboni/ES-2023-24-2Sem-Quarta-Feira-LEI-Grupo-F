import java.time.LocalTime;

public enum SchedulePeriod {
    _08H00_09H30(LocalTime.parse("08:00"), LocalTime.parse("09:30")),
    _09H30_11H00(LocalTime.parse("09:30"), LocalTime.parse("11:00")),
    _11H00_12H30(LocalTime.parse("11:00"), LocalTime.parse("12:30")),
    _13H00_14H30(LocalTime.parse("13:00"), LocalTime.parse("14:30")),
    _14H30_16H00(LocalTime.parse("14:30"), LocalTime.parse("16:00")),
    _16H00_17H30(LocalTime.parse("16:00"), LocalTime.parse("17:30")),
    _18H00_19H30(LocalTime.parse("18:00"), LocalTime.parse("19:30")),
    _19H30_21H00(LocalTime.parse("19:30"), LocalTime.parse("21:00")),
    _21H00_22H30(LocalTime.parse("21:00"), LocalTime.parse("22:30"));

    final private LocalTime startTime;
    final private LocalTime endTime;

    SchedulePeriod(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

}
