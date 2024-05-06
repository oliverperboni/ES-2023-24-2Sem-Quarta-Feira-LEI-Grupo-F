package structures;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * The LineSchedule class represents a line in a schedule, containing information about a particular class or event.
 */
public class LineSchedule {

    private String curso;
    private String unidade_curricular;
    private String turno;
    private String turma;
    private Integer inscritos;
    private String dia_semana;
    private String hora_inicio;
    private String hora_fim;
    private String data_aula;
    private String caracteristicas_sala;
    private String sala;

    private ScheduleInstant scheduleInstant;

    /**
     * Constructs a new LineSchedule object with the provided parameters.
     *
     * @param curso                The course name.
     * @param unidade_curricular   The name of the course unit.
     * @param turno                The class or event shift.
     * @param turma                The class group.
     * @param inscritos            The number of participants or enrolled students.
     * @param dia_semana           The day of the week.
     * @param hora_inicio          The start time of the class or event.
     * @param hora_fim             The end time of the class or event.
     * @param data_aula            The date of the class or event.
     * @param caracteristicas_sala The characteristics of the room.
     * @param sala                 The room where the class or event takes place.
     */
    public LineSchedule(String curso, String unidade_curricular, String turno, String turma, Integer inscritos,
                        String dia_semana, String hora_inicio, String hora_fim, String data_aula,
                        String caracteristicas_sala, String sala) {

        this.curso = curso;
        this.unidade_curricular = unidade_curricular;
        this.turno = turno;
        this.turma = turma;
        this.inscritos = inscritos;
        this.dia_semana = dia_semana;
        this.hora_inicio = hora_inicio;
        this.hora_fim = hora_fim;
        this.data_aula = data_aula;
        this.caracteristicas_sala = caracteristicas_sala;
        this.sala = sala;

        if (!hora_inicio.isEmpty() && !hora_fim.isEmpty() && !data_aula.isEmpty()) {
            SchedulePeriod schedulePeriod = new SchedulePeriod(LocalTime.parse(hora_inicio), LocalTime.parse(hora_fim));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate scheduleDate = LocalDate.parse(data_aula, formatter);

            this.scheduleInstant = new ScheduleInstant(scheduleDate, schedulePeriod);
        }
    }

    /**
     * Copy constructor to create a new LineSchedule object from an existing one.
     *
     * @param schedule The LineSchedule object to be copied.
     */
    public LineSchedule(LineSchedule schedule) {
        this.curso = schedule.curso;
        this.unidade_curricular = schedule.unidade_curricular;
        this.turno = schedule.turno;
        this.turma = schedule.turma;
        this.inscritos = Integer.valueOf(schedule.inscritos);
        this.dia_semana = schedule.dia_semana;
        this.hora_inicio = schedule.hora_inicio;
        this.hora_fim = schedule.hora_fim;
        this.data_aula = schedule.data_aula;
        this.caracteristicas_sala = schedule.caracteristicas_sala;
        this.sala = schedule.sala;

        if (!hora_inicio.isEmpty() && !hora_fim.isEmpty() && !data_aula.isEmpty()) {
            SchedulePeriod schedulePeriod = new SchedulePeriod(LocalTime.parse(hora_inicio), LocalTime.parse(hora_fim));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate scheduleDate = LocalDate.parse(data_aula, formatter);

            this.scheduleInstant = new ScheduleInstant(scheduleDate, schedulePeriod);
        }
    }

    // Getters and setters

    /**
     * Returns the course name.
     *
     * @return The course name.
     */
    public String getCurso() {
        return curso;
    }

    /**
     * Sets the course name.
     *
     * @param curso The course name to set.
     */
    public void setCurso(String curso) {
        this.curso = curso;
    }

    /**
     * Returns the name of the course unit.
     *
     * @return The name of the course unit.
     */
    public String getUnidadeCurricular() {
        return unidade_curricular;
    }

    /**
     * Sets the name of the course unit.
     *
     * @param unidade_curricular The name of the course unit to set.
     */
    public void setUnidade_curricular(String unidade_curricular) {
        this.unidade_curricular = unidade_curricular;
    }

    /**
     * Returns the class or event shift.
     *
     * @return The class or event shift.
     */
    public String getTurno() {
        return turno;
    }

    /**
     * Sets the class or event shift.
     *
     * @param turno The class or event shift to set.
     */
    public void setTurno(String turno) {
        this.turno = turno;
    }

    /**
     * Returns the class group.
     *
     * @return The class group.
     */
    public String getTurma() {
        return turma;
    }

    /**
     * Sets the class group.
     *
     * @param turma The class group to set.
     */
    public void setTurma(String turma) {
        this.turma = turma;
    }

    /**
     * Returns the number of enrolled students.
     *
     * @return The number of enrolled students.
     */
    public Integer getInscritos() {
        return inscritos;
    }

    /**
     * Sets the number of participants or enrolled students.
     *
     * @param inscritos The number of participants or enrolled students to set.
     */
    public void setInscritos(Integer inscritos) {
        this.inscritos = inscritos;
    }

    /**
     * Returns the day of the week.
     *
     * @return The day of the week.
     */
    public String getDiaSemana() {
        return dia_semana;
    }

    /**
     * Sets the day of the week.
     *
     * @param dia_semana The day of the week to set.
     */
    public void setDia_semana(String dia_semana) {
        this.dia_semana = dia_semana;
    }

    /**
     * Returns the start time of the class.
     *
     * @return The start time of the class.
     */
    public String getHoraInicio() {
        return hora_inicio;
    }

    /**
     * Sets the start time of the class.
     *
     * @param hora_inicio The start time of the class to set.
     */
    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    /**
     * Returns the end time of the class.
     *
     * @return The end time of the class.
     */
    public String getHoraFim() {
        return hora_fim;
    }

    /**
     * Sets the end time of the class.
     *
     * @param hora_fim The end time of the class to set.
     */
    public void setHora_fim(String hora_fim) {
        this.hora_fim = hora_fim;
    }

    /**
     * Returns the date of the class.
     *
     * @return The date of the class.
     */
    public String getDataAula() {
        return data_aula;
    }

    /**
     * Sets the date of the class or event.
     *
     * @param data_aula The date of the class.
     */
    public void setData_aula(String data_aula) {
        this.data_aula = data_aula;
    }

    /**
     * Returns the characteristics of the room.
     *
     * @return The characteristics of the room.
     */
    public String getCaracteristicasSala() {
        return caracteristicas_sala;
    }

    /**
     * Sets the characteristics of the room.
     *
     * @param caracteristicas_sala The characteristics of the room to set.
     */
    public void setCaracteristicasSala(String caracteristicas_sala) {
        this.caracteristicas_sala = caracteristicas_sala;
    }

    /**
     * Returns the room where the class or event takes place.
     *
     * @return The room where the class or event takes place.
     */
    public String getSala() {
        return sala;
    }

    /**
     * Sets the room where the class or event takes place.
     *
     * @param sala The room where the class or event takes place to set.
     */
    public void setSala(String sala) {
        this.sala = sala;
    }

    /**
     * Returns the schedule instant associated with this LineSchedule.
     *
     * @return The schedule instant.
     */
    public ScheduleInstant getScheduleInstant() {
        return scheduleInstant;
    }

    /**
     * Sets the schedule instant associated with this LineSchedule.
     *
     * @param scheduleInstant The schedule instant to set.
     */
    public void setScheduleInstant(ScheduleInstant scheduleInstant) {
        this.scheduleInstant = scheduleInstant;
    }


    /**
     * Overrides the toString method to return a string representation of the LineSchedule object.
     *
     * @return A string representation of the LineSchedule object.
     */
    @Override
    public String toString() {
        return this.curso + " " + this.unidade_curricular + " " + this.turno + " " + this.turma +
                " " + this.inscritos + " " + this.dia_semana + " " + this.hora_inicio + " " + this.hora_fim +
                " " + this.data_aula + " " + this.caracteristicas_sala + " " + this.sala;
    }
}