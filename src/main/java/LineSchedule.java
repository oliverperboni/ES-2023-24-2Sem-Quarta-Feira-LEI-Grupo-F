import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
    private LocalDate date;
    private DayOfWeek weekDay;
    private LocalTime startTime;
    private LocalTime endTime;

    public LineSchedule(String curso, String unidade_curricular, String turno, String turma, Integer inscritos, String dia_semana, String hora_inicio, String hora_fim, String data_aula, String caracteristicas_sala, String sala) {
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

        if (!hora_inicio.isEmpty()) this.startTime = LocalTime.parse(hora_inicio);

        if (!hora_fim.isEmpty()) this.endTime = LocalTime.parse(hora_fim);

        if (!data_aula.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            this.date = LocalDate.parse(data_aula, formatter);
            this.weekDay = date.getDayOfWeek();
        }

    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getUnidadeCurricular() {
        return unidade_curricular;
    }

    public void setUnidade_curricular(String unidade_curricular) {
        this.unidade_curricular = unidade_curricular;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public Integer getInscritos() {
        return inscritos;
    }

    public void setInscritos(Integer inscritos) {
        this.inscritos = inscritos;
    }

    public String getDiaSemana() {
        return dia_semana;
    }

    public void setDia_semana(String dia_semana) {
        this.dia_semana = dia_semana;
    }

    public String getHoraInicio() {
        return hora_inicio;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public String getHoraFim() {
        return hora_fim;
    }

    public void setHora_fim(String hora_fim) {
        this.hora_fim = hora_fim;
    }

    public String getDataAula() {
        return data_aula;
    }

    public void setData_aula(String data_aula) {
        this.data_aula = data_aula;
    }

    public String getCaracteristicasSala() {
        return caracteristicas_sala;
    }

    public void setCaracteristicasSala(String caracteristicas_sala) {
        this.caracteristicas_sala = caracteristicas_sala;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public DayOfWeek getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(DayOfWeek weekDay) {
        this.weekDay = weekDay;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

}
