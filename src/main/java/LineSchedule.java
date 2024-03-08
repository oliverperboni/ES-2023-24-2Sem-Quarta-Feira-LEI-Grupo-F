public class LineSchedule {
    String curso;
    String unidade_curricular;
    String turno;
    String turma;
    Integer inscritos;
    String dia_semana;
    String hora_inicio;
    String hora_fim;
    String data_aula;
    String caracteristicas_sala;
    String sala;

    public LineSchedule(String curso, String unidade_curricular, String turno, String turma, Integer inscritos, String dia_semana, String hora_inicio, String hora_fim,  String data_aula, String caracteristicas_sala, String sala) {
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

    public String getUnidade_curricular() {
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

    public String getDia_semana() {
        return dia_semana;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public String getHora_fim() {
        return hora_fim;
    }

    public String getData_aula() {
        return data_aula;
    }

    public String getCaracteristicas_sala() {
        return caracteristicas_sala;
    }

    public String getSala() {
        return sala;
    }
}
