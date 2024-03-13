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
    }

    public String getCurso() {
        return curso;
    }

    public String getUnidadeCurricular() {
        return unidade_curricular;
    }

    public String getTurno() {
        return turno;
    }

    public String getTurma() {
        return turma;
    }

    public Integer getInscritos() {
        return inscritos;
    }

    public String getDiaSemana() {
        return dia_semana;
    }

    public String getHoraInicio() {
        return hora_inicio;
    }

    public String getHoraFim() {
        return hora_fim;
    }

    public String getDataAula() {
        return data_aula;
    }

    public String getCaracteristicasSala() {
        return caracteristicas_sala;
    }

    public String getSala() {
        return sala;
    }
}
