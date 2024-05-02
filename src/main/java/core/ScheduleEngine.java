package core;

import structures.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.time.temporal.ChronoUnit.MINUTES;

/**
 * The ScheduleEngine class hosts methods that drive the scheduling application's automatic operations over the
 * currently loaded schedule and room entries.
 * @author António Pombeiro
 */
public class ScheduleEngine {
    private ScheduleDataModel dataModel;

    public ScheduleEngine(ScheduleDataModel dataModel) {
        this.dataModel = dataModel;
    }

    public ArrayList<LineSchedule> suggestClass(String UC, int quantidade_aulas, ArrayList<SchedulePeriod> excludedPeriods,
                                                ArrayList<SchedulePeriod> allowedPeriods, ArrayList<RoomPreference> roomTypePreferences,
                                                ArrayList<Room> roomTypeExclusions) {

        LineSchedule classSchedule = null;
        ArrayList<LineSchedule> possibilityList = new ArrayList<>();
        ArrayList<LineSchedule> alreadyScheduledList = dataModel.getScheduleEntries();


        for(LineSchedule t : alreadyScheduledList){
            if(t.getUnidadeCurricular().equals(UC)){
                classSchedule = new LineSchedule(t);
                System.out.println(classSchedule.getDataAula());
                break;
            }
        }

        for(int i =1; i<=quantidade_aulas; i++) {
            //System.out.println("oi");
            for (SchedulePeriod sp1 : allowedPeriods) {

                if (sp1.getIsWeekDay()){// Por cada preferência do tipo "dia da semana"


                    for (RoomPreference rp : roomTypePreferences) { // Por cada preferência de tipo de sala
                        List<Room> resultRoomList = roomTypeSearch(rp);
                        for (Room resultRoom : resultRoomList)
                            for (SchedulePeriod sp2 : allowedPeriods)
                                if (sp2.getIsTimePeriod()) // Por cada preferência do tipo "período do dia" (manhã, tarde, noite)
                                    for (SchedulePeriod timeSlot : sp2.getTimeSlotList()) { // Por cada horário desse "período do dia"
                                        if (createSchedulePossibility2(classSchedule, sp1, resultRoom, timeSlot, rp, excludedPeriods, roomTypeExclusions, i) != null) {
                                            possibilityList.add(createSchedulePossibility2(classSchedule, sp1, resultRoom, timeSlot, rp, excludedPeriods, roomTypeExclusions, i));
                                        }else{
                                        }
                                    }
                    }
                }
            }
        }

        for(LineSchedule t : possibilityList){
            System.out.println(t);
        }
        return possibilityList;


    }


    public ArrayList<LineSchedule> suggestCompensation(LineSchedule classSchedule, ArrayList<SchedulePeriod> excludedPeriods,
                                                       ArrayList<SchedulePeriod> allowedPeriods, ArrayList<RoomPreference> roomTypePreferences,
                                                       ArrayList<Room> roomTypeExclusions) {

//      classSchedule é uma structures.LineSchedule com a aula a remarcar
//      Dela é calculada a duração da aula a remarcar
        int classDuration = (int) MINUTES.between(classSchedule.getScheduleInstant().getScheduleTime().getStartTime(),
                classSchedule.getScheduleInstant().getScheduleTime().getEndTime());

//      Lista de possibilidades de "slots" para remarcação
        ArrayList<LineSchedule> possibilityList = new ArrayList<>();
        ArrayList<LineSchedule> alreadyScheduledList = dataModel.getScheduleEntries();

//      Caminho seguido para aulas padrão, de 90 minutos
        if (classDuration == 90) {
//          Por cada preferência indicada
            for (SchedulePeriod sp1 : allowedPeriods) {
                if (sp1.getIsWeekDay()) // Por cada preferência do tipo "dia da semana"
                    for (RoomPreference rp : roomTypePreferences) { // Por cada preferência de tipo de sala
                        List<Room> resultRoomList = roomTypeSearch(rp);
                        for (Room resultRoom : resultRoomList)
                            for (SchedulePeriod sp2 : allowedPeriods)
                                if (sp2.getIsTimePeriod()) // Por cada preferência do tipo "período do dia" (manhã, tarde, noite)
                                    for (SchedulePeriod timeSlot : sp2.getTimeSlotList()){ // Por cada horário desse "período do dia"
                                        if(createSchedulePossibility(classSchedule, sp1, resultRoom, timeSlot, rp, excludedPeriods, roomTypeExclusions) != null){
                                            possibilityList.add(createSchedulePossibility(classSchedule, sp1, resultRoom, timeSlot, rp, excludedPeriods, roomTypeExclusions));
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
        return possibilityList;

    }

    /**
     * Given a schedule entry that's to be rescheduled, this method creates a copy of it, and then replaces its
     * date, start and end times, week day, room and room type.
     * @param classSchedule LineSchedule object of the schedule entry to be rescheduled
     * @param dayPeriod SchedulePeriod object for the new day of the week for the schedule entry
     * @param resultRoom Room object for the new room
     * @param timeSlot SchedulePeriod object for the new time slot, and start and end times
     * @return LineSchedule object representing a copy of the original, but with schedule and room altered
     * @since 1.0
     */
    public LineSchedule createSchedulePossibility
    (LineSchedule classSchedule, SchedulePeriod dayPeriod, Room resultRoom, SchedulePeriod timeSlot, RoomPreference rp, ArrayList<SchedulePeriod> ExcludedTime,ArrayList<Room> ExcludedRooms ) {
        LineSchedule auxSchedule = new LineSchedule(classSchedule);
        LocalDate classDate = classSchedule.getScheduleInstant().getScheduleDate();
        LocalDate auxDate = classDate.with(TemporalAdjusters.nextOrSame(dayPeriod.getPreferredDay()));

        ScheduleInstant auxInstant = new ScheduleInstant(auxDate, timeSlot);
        auxSchedule.setScheduleInstant(auxInstant);

        for(SchedulePeriod a : ExcludedTime) {

            if (a.equals(auxSchedule.getScheduleInstant().getScheduleTime()) || a.getWeekDay().equals(auxInstant.weekDayToString())) {
                return null;
            }
        }
        /*for(String b : ExcludedRooms){
            System.out.println(b);
            System.out.println(resultRoom.getNomeSala());
            if (b.equals(auxSchedule.getSala())) {
                return null;
            }
        }*/




        auxSchedule.setDia_semana(auxInstant.weekDayToString());
        auxSchedule.setHora_inicio(auxInstant.getScheduleTime().getStartTime().toString());
        auxSchedule.setHora_fim(auxInstant.getScheduleTime().getEndTime().toString());
        auxSchedule.setData_aula(auxInstant.getScheduleDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        auxSchedule.setSala(resultRoom.getNomeSala());
        auxSchedule.setCaracteristicasSala(rp.toString());




        return auxSchedule;
    }
    public LineSchedule createSchedulePossibility2
            (LineSchedule classSchedule, SchedulePeriod dayPeriod, Room resultRoom, SchedulePeriod timeSlot, RoomPreference rp, ArrayList<SchedulePeriod> ExcludedTime,ArrayList<Room> ExcludedRooms, int i) {


        System.out.println("ola");
        LineSchedule auxSchedule = new LineSchedule(classSchedule);
        LocalDate classDate = classSchedule.getScheduleInstant().getScheduleDate();
        LocalDate auxDate = classDate.with(TemporalAdjusters.dayOfWeekInMonth(i,dayPeriod.getPreferredDay()));


        ScheduleInstant auxInstant = new ScheduleInstant(auxDate, timeSlot);
        auxSchedule.setScheduleInstant(auxInstant);

        for(SchedulePeriod a : ExcludedTime) {

            if (a.equals(auxSchedule.getScheduleInstant().getScheduleTime()) || a.getWeekDay().equals(auxInstant.weekDayToString())) {
                return null;
            }
        }
        /*for(String b : ExcludedRooms){
            System.out.println(b);
            System.out.println(resultRoom.getNomeSala());
            if (b.equals(auxSchedule.getSala())) {
                return null;
            }
        }*/




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
     * @param roomPreference RoomPreference object representing desired room specification
     * @return List of all rooms matching the criteria, represented by Room objects
     * @since 1.0
     */
    public List<Room> roomTypeSearch (RoomPreference roomPreference) {
        List<Room> resultRoomList = new ArrayList<>();
        for (Room room : dataModel.getRoomEntries())
            for (String roomSpec : room.getRoomSpecs())
                if (roomSpec.equals(roomPreference.toString()) && !resultRoomList.contains(room))
                    resultRoomList.add(room);
        return resultRoomList;
    }


    public static void main(String[] args) {
        LineSchedule reSchedule = new LineSchedule(
                "Curso", "UnidadeCurricular",
                "Turno", "Turma", 0, "Seg",
                "13:00:00", "14:30:00", "01/12/2022",
                "CaracterísticasSala", "Sala");

        ScheduleDataModel dataModel = new ScheduleDataModel("csv/HorarioDeExemplo.csv", false,
                "csv/CaracterizaçãoDasSalas.csv", false);
        ScheduleEngine engine = new ScheduleEngine(dataModel);

        ArrayList<SchedulePeriod> allowedPeriods = new ArrayList<SchedulePeriod>();
        ArrayList<SchedulePeriod> excludedPeriods = new ArrayList<SchedulePeriod>();
        ArrayList<Room> excludedRoom = new ArrayList<Room>();

        allowedPeriods.add(SchedulePeriod.NOITE);
        allowedPeriods.add(SchedulePeriod.SEGUNDA_FEIRA);
        allowedPeriods.add(SchedulePeriod.TERCA_FEIRA);
        //allowedPeriods.add(SchedulePeriod.QUARTA_FEIRA);
        //allowedPeriods.add(SchedulePeriod.QUINTA_FEIRA);
        //allowedPeriods.add(SchedulePeriod.SEXTA_FEIRA);


        //excludedPeriods.add(SchedulePeriod.SEGUNDA_FEIRA);
        //excludedPeriods.add(SchedulePeriod._21H00_22H30);
        //excludedRoom.add("B1.04");

        ArrayList<RoomPreference> roomTypePreferences = new ArrayList<RoomPreference>();
        roomTypePreferences.add(RoomPreference.ANFITEATRO_AULAS);

        engine.suggestCompensation(reSchedule, new ArrayList<SchedulePeriod>(), allowedPeriods,
                roomTypePreferences, new ArrayList<Room>());
        engine.suggestClass("TEORIA DOS JOGOS E DOS CONTRATOS", 5, excludedPeriods, allowedPeriods,
                roomTypePreferences, excludedRoom);

    }
}

