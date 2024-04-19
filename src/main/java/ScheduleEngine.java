import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;

import static java.time.temporal.ChronoUnit.MINUTES;

public class ScheduleEngine {

    private ScheduleDataModel dataModel;

    public ScheduleEngine(ScheduleDataModel dataModel) {
        this.dataModel = dataModel;
    }

    public LineSchedule
    scheduleNewSlot(LineSchedule classSchedule,
                    ArrayList<SchedulePeriod> excludedPeriods,
                    ArrayList<SchedulePeriod> allowedPeriods,
                    ArrayList<Rooms> roomTypePreferences,
                    ArrayList<Rooms> roomTypeExclusions) {

//      classSchedule é uma LineSchedule com a aula a remarcar
//      Dela é calculada a duração da aula a remarcar
        int classDuration = (int) MINUTES.between(classSchedule.getScheduleInstant().getScheduleTime().getStartTime(),
                classSchedule.getScheduleInstant().getScheduleTime().getEndTime());
        LocalDate classDate = classSchedule.getScheduleInstant().getScheduleDate();

//      Lista de possibilidades de "slots" para remarcação
        ArrayList<LineSchedule> possibilityList = new ArrayList<>();
        ArrayList<LineSchedule> alreadyScheduledList = dataModel.getScheduleEntries();

//      Caminho seguido para aulas padrão, de 90 minutos
        if (classDuration == 90) {
//          Por cada preferência indicada
            for (SchedulePeriod sp1 : allowedPeriods) {
                if (sp1.getIsWeekDay()) { // Por cada preferência do tipo "dia da semana"
                    for (SchedulePeriod sp2 : allowedPeriods) {
                        if (sp2.getIsTimePeriod()) { // Por cada preferência do tipo "período do dia" (manhã, tarde, noite)
                            for (SchedulePeriod timeSlot : sp2.getTimeSlotList()) { // Por cada horário desse "período do dia"

                                LineSchedule auxSchedule = new LineSchedule(classSchedule);
                                LocalDate auxDate = classDate.with(TemporalAdjusters.nextOrSame(sp1.getPreferredDay()));
                                ScheduleInstant auxInstant = new ScheduleInstant(auxDate, timeSlot);
                                auxSchedule.setDia_semana(auxInstant.weekDayToString());

                                auxSchedule.setScheduleInstant(auxInstant);
                                possibilityList.add(auxSchedule);
                            }
                        }
                    }
                }
            }

            for (LineSchedule t : possibilityList)
                System.out.println(t.toString());

        } else {
//          Caminho seguido para aulas com duração diferente de 90 minutos
//          EM CONSTRUÇÃO
        }

        return classSchedule;
    }

    public static void main(String[] args) {
        LineSchedule reSchedule = new LineSchedule(
                "Curso", "Unidade Curricular",
                "Turno", "Turma", 0, "Seg",
                "13:00:00", "14:30:00", "01/12/2022",
                "Características sala", "C6.10");

        ScheduleDataModel dataModel = new ScheduleDataModel("csv/HorarioDeExemplo.csv", false,
                "csv/CaracterizaçãoDasSalas.csv", false);
        ScheduleEngine engine = new ScheduleEngine(dataModel);

        ArrayList<SchedulePeriod> allowedPeriods = new ArrayList<SchedulePeriod>();
        allowedPeriods.add(SchedulePeriod.MANHA);
        allowedPeriods.add(SchedulePeriod.SEGUNDA_FEIRA);
        allowedPeriods.add(SchedulePeriod.TERCA_FEIRA);

        engine.scheduleNewSlot(reSchedule, new ArrayList<SchedulePeriod>(), allowedPeriods,
                new ArrayList<Rooms>(), new ArrayList<Rooms>());

    }

}
