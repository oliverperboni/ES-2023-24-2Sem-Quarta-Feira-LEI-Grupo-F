import java.util.ArrayList;

import static java.time.temporal.ChronoUnit.MINUTES;

public class ScheduleEngine {

    private ScheduleDataModel dataModel;

    public ScheduleEngine(ScheduleDataModel dataModel) {
        dataModel = dataModel;
    }

    public LineSchedule
    scheduleNewSlot(LineSchedule classSchedule,
                    ArrayList<PreferencePeriod> excludedPeriods,
                    ArrayList<PreferencePeriod> allowedPeriods,
                    ArrayList<Rooms> roomTypePreferences,
                    ArrayList<Rooms> roomTypeExclusions) {

//      classSchedule é uma LineSchedule com a aula a remarcar
//      Dela é calculada a duração da aula a remarcar
        int classDuration = (int) MINUTES.between(classSchedule.getStartTime(), classSchedule.getEndTime());

//      Lista de possibilidades de "slots" para remarcação
        ArrayList<LineSchedule> possibilityList = new ArrayList<>();
        ArrayList<LineSchedule> alreadyScheduledList = dataModel.getScheduleEntries();

//      Caminho seguido para aulas padrão, de 90 minutos
        if (classDuration == 90) {
//          Por cada preferência indicada
            for (PreferencePeriod p1 : allowedPeriods) {
                if (p1.getIsWeekDay())
//                  Por cada preferência do tipo "dia da semana"
                    for (PreferencePeriod p2 : allowedPeriods)
                        if (p2.getIsTimePeriod()) {
//                          Por cada preferência do tipo "período do dia" (manhã, tarde, noite)
                            LineSchedule auxSchedule = classSchedule;
                            auxSchedule.setDia_semana(p1.toString());
                            auxSchedule.setWeekDay(p1.getPreferredDay());

                            auxSchedule.setStartTime(p2.getStartTime());
                            auxSchedule.setEndTime(p2.getEndTime());

                            possibilityList.add(auxSchedule);
                        }
            }

            for (LineSchedule possibility : possibilityList)
                for (LineSchedule scheduled : alreadyScheduledList)
//                    if (possibility.getWeekDay().equals())






        } else {
//          Caminho seguido para aulas com duração diferente de 90 minutos
//          EM CONSTRUÇÃO
        }

        return classSchedule;
        return classSchedule;
    }


}
