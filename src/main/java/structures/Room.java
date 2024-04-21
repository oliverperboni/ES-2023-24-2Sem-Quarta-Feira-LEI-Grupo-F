package structures;

public class Room {

    private String edificio;
    private String nomeSala;
    private int capacidadeNormal;
    private int capacidadeExame;
    private String numCaracteristicas;
    private boolean anfiteatroAulas;
    private boolean apoioTecnicoEventos;
    private boolean arq1;
    private boolean arq2;
    private boolean arq3;
    private boolean arq4;
    private boolean arq5;
    private boolean arq6;
    private boolean arq9;
    private boolean byod;
    private boolean focusGroup;
    private boolean horarioSalaVisivelPortalPublico;
    private boolean laboratorioArquiteturaComputadoresI;
    private boolean laboratorioArquiteturaComputadoresII;
    private boolean laboratorioBasesEngenharia;
    private boolean laboratorioEletronica;
    private boolean laboratorioInformatica;
    private boolean laboratorioJornalismo;
    private boolean laboratorioRedesComputadoresI;
    private boolean laboratorioRedesComputadoresII;
    private boolean laboratorioTelecomunicacoes;
    private boolean salaAulasMestrado;
    private boolean salaAulasMestradoPlus;
    private boolean salaNEE;
    private boolean salaProvas;
    private boolean salaReuniao;
    private boolean salaArquitetura;
    private boolean salaAulasNormal;
    private boolean videoconferencia;
    private boolean atrio;

    private String roomSpec;


    public Room(String edificio, String nomeSala, int capacidadeNormal, int capacidadeExame, String numCaracteristicas,
                boolean anfiteatroAulas, boolean apoioTecnicoEventos, boolean arq1, boolean arq2, boolean arq3,
                boolean arq4, boolean arq5, boolean arq6, boolean arq9, boolean byod, boolean focusGroup,
                boolean horarioSalaVisivelPortalPublico, boolean laboratorioArquiteturaComputadoresI,
                boolean laboratorioArquiteturaComputadoresII, boolean laboratorioBasesEngenharia,
                boolean laboratorioEletronica, boolean laboratorioInformatica, boolean laboratorioJornalismo,
                boolean laboratorioRedesComputadoresI, boolean laboratorioRedesComputadoresII,
                boolean laboratorioTelecomunicacoes, boolean salaAulasMestrado, boolean salaAulasMestradoPlus,
                boolean salaNEE, boolean salaProvas, boolean salaReuniao, boolean salaArquitetura,
                boolean salaAulasNormal, boolean videoconferencia, boolean atrio, String roomSpec) {
        this.edificio = edificio;
        this.nomeSala = nomeSala;
        this.capacidadeNormal = capacidadeNormal;
        this.capacidadeExame = capacidadeExame;
        this.numCaracteristicas = numCaracteristicas;
        this.anfiteatroAulas = anfiteatroAulas;
        this.apoioTecnicoEventos = apoioTecnicoEventos;
        this.arq1 = arq1;
        this.arq2 = arq2;
        this.arq3 = arq3;
        this.arq4 = arq4;
        this.arq5 = arq5;
        this.arq6 = arq6;
        this.arq9 = arq9;
        this.byod = byod;
        this.focusGroup = focusGroup;
        this.horarioSalaVisivelPortalPublico = horarioSalaVisivelPortalPublico;
        this.laboratorioArquiteturaComputadoresI = laboratorioArquiteturaComputadoresI;
        this.laboratorioArquiteturaComputadoresII = laboratorioArquiteturaComputadoresII;
        this.laboratorioBasesEngenharia = laboratorioBasesEngenharia;
        this.laboratorioEletronica = laboratorioEletronica;
        this.laboratorioInformatica = laboratorioInformatica;
        this.laboratorioJornalismo = laboratorioJornalismo;
        this.laboratorioRedesComputadoresI = laboratorioRedesComputadoresI;
        this.laboratorioRedesComputadoresII = laboratorioRedesComputadoresII;
        this.laboratorioTelecomunicacoes = laboratorioTelecomunicacoes;
        this.salaAulasMestrado = salaAulasMestrado;
        this.salaAulasMestradoPlus = salaAulasMestradoPlus;
        this.salaNEE = salaNEE;
        this.salaProvas = salaProvas;
        this.salaReuniao = salaReuniao;
        this.salaArquitetura = salaArquitetura;
        this.salaAulasNormal = salaAulasNormal;
        this.videoconferencia = videoconferencia;
        this.atrio = atrio;

        this.roomSpec = roomSpec;
    }

    public Room(String edificio, String nomeSala, int capacidadeNormal,
                int capacidadeExame, String numCaracteristicas, String roomSpec) {
        this.edificio = edificio;
        this.nomeSala = nomeSala;
        this.capacidadeNormal = capacidadeNormal;
        this.capacidadeExame = capacidadeExame;
        this.numCaracteristicas = numCaracteristicas;
        this.roomSpec = roomSpec;
    }

    public Boolean parseBoolean(String bool){
        return "X".equals(bool);
    }

    public String getEdificio() {
        return edificio;
    }
    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    public String getNomeSala() {
        return nomeSala;
    }
    public void setNomeSala(String nomeSala) {
        this.nomeSala = nomeSala;
    }

    public int getCapacidadeNormal() {
        return capacidadeNormal;
    }
    public void setCapacidadeNormal(int capacidadeNormal) {
        this.capacidadeNormal = capacidadeNormal;
    }

    public int getCapacidadeExame() {
        return capacidadeExame;
    }
    public void setCapacidadeExame(int capacidadeExame) {
        this.capacidadeExame = capacidadeExame;
    }

    public String getNumCaracteristicas() {
        return numCaracteristicas;
    }
    public void setNumCaracteristicas(String numCaracteristicas) {
        this.numCaracteristicas = numCaracteristicas;
    }

    public boolean isAnfiteatroAulas() {
        return anfiteatroAulas;
    }
    public void setAnfiteatroAulas(boolean anfiteatroAulas) {
        this.anfiteatroAulas = anfiteatroAulas;
    }

    public boolean isApoioTecnicoEventos() {
        return apoioTecnicoEventos;
    }
    public void setApoioTecnicoEventos(boolean apoioTecnicoEventos) {
        this.apoioTecnicoEventos = apoioTecnicoEventos;
    }

    public boolean isArq1() {
        return arq1;
    }
    public void setArq1(boolean arq1) {
        this.arq1 = arq1;
    }

    public boolean isArq2() {
        return arq2;
    }
    public void setArq2(boolean arq2) {
        this.arq2 = arq2;
    }

    public boolean isArq3() {
        return arq3;
    }
    public void setArq3(boolean arq3) {
        this.arq3 = arq3;
    }

    public boolean isArq4() {
        return arq4;
    }
    public void setArq4(boolean arq4) {
        this.arq4 = arq4;
    }

    public boolean isArq5() {
        return arq5;
    }
    public void setArq5(boolean arq5) {
        this.arq5 = arq5;
    }

    public boolean isArq6() {
        return arq6;
    }
    public void setArq6(boolean arq6) {
        this.arq6 = arq6;
    }

    public boolean isArq9() {
        return arq9;
    }
    public void setArq9(boolean arq9) {
        this.arq9 = arq9;
    }

    public boolean isByod() {
        return byod;
    }
    public void setByod(boolean byod) {
        this.byod = byod;
    }

    public boolean isFocusGroup() {
        return focusGroup;
    }
    public void setFocusGroup(boolean focusGroup) {
        this.focusGroup = focusGroup;
    }

    public boolean isHorarioSalaVisivelPortalPublico() {
        return horarioSalaVisivelPortalPublico;
    }
    public void setHorarioSalaVisivelPortalPublico(boolean horarioSalaVisivelPortalPublico) {
        this.horarioSalaVisivelPortalPublico = horarioSalaVisivelPortalPublico;
    }

    public boolean isLaboratorioArquiteturaComputadoresI() {
        return laboratorioArquiteturaComputadoresI;
    }
    public void setLaboratorioArquiteturaComputadoresI(boolean laboratorioArquiteturaComputadoresI) {
        this.laboratorioArquiteturaComputadoresI = laboratorioArquiteturaComputadoresI;
    }

    public boolean isLaboratorioArquiteturaComputadoresII() {
        return laboratorioArquiteturaComputadoresII;
    }
    public void setLaboratorioArquiteturaComputadoresII(boolean laboratorioArquiteturaComputadoresII) {
        this.laboratorioArquiteturaComputadoresII = laboratorioArquiteturaComputadoresII;
    }

    public boolean isLaboratorioBasesEngenharia() {
        return laboratorioBasesEngenharia;
    }
    public void setLaboratorioBasesEngenharia(boolean laboratorioBasesEngenharia) {
        this.laboratorioBasesEngenharia = laboratorioBasesEngenharia;
    }

    public boolean isLaboratorioEletronica() {
        return laboratorioEletronica;
    }
    public void setLaboratorioEletronica(boolean laboratorioEletronica) {
        this.laboratorioEletronica = laboratorioEletronica;
    }

    public boolean isLaboratorioInformatica() {
        return laboratorioInformatica;
    }
    public void setLaboratorioInformatica(boolean laboratorioInformatica) {
        this.laboratorioInformatica = laboratorioInformatica;
    }

    public boolean isLaboratorioJornalismo() {
        return laboratorioJornalismo;
    }
    public void setLaboratorioJornalismo(boolean laboratorioJornalismo) {
        this.laboratorioJornalismo = laboratorioJornalismo;
    }

    public boolean isLaboratorioRedesComputadoresI() {
        return laboratorioRedesComputadoresI;
    }
    public void setLaboratorioRedesComputadoresI(boolean laboratorioRedesComputadoresI) {
        this.laboratorioRedesComputadoresI = laboratorioRedesComputadoresI;
    }

    public boolean isLaboratorioRedesComputadoresII() {
        return laboratorioRedesComputadoresII;
    }
    public void setLaboratorioRedesComputadoresII(boolean laboratorioRedesComputadoresII) {
        this.laboratorioRedesComputadoresII = laboratorioRedesComputadoresII;
    }

    public boolean isLaboratorioTelecomunicacoes() {
        return laboratorioTelecomunicacoes;
    }
    public void setLaboratorioTelecomunicacoes(boolean laboratorioTelecomunicacoes) {
        this.laboratorioTelecomunicacoes = laboratorioTelecomunicacoes;
    }

    public boolean isSalaAulasMestrado() {
        return salaAulasMestrado;
    }
    public void setSalaAulasMestrado(boolean salaAulasMestrado) {
        this.salaAulasMestrado = salaAulasMestrado;
    }

    public boolean isSalaAulasMestradoPlus() {
        return salaAulasMestradoPlus;
    }
    public void setSalaAulasMestradoPlus(boolean salaAulasMestradoPlus) {
        this.salaAulasMestradoPlus = salaAulasMestradoPlus;
    }

    public boolean isSalaNEE() {
        return salaNEE;
    }
    public void setSalaNEE(boolean salaNEE) {
        this.salaNEE = salaNEE;
    }

    public boolean isSalaProvas() {
        return salaProvas;
    }
    public void setSalaProvas(boolean salaProvas) {
        this.salaProvas = salaProvas;
    }

    public boolean isSalaReuniao() {
        return salaReuniao;
    }
    public void setSalaReuniao(boolean salaReuniao) {
        this.salaReuniao = salaReuniao;
    }

    public boolean isSalaArquitetura() {
        return salaArquitetura;
    }
    public void setSalaArquitetura(boolean salaArquitetura) {
        this.salaArquitetura = salaArquitetura;
    }

    public boolean isSalaAulasNormal() {
        return salaAulasNormal;
    }
    public void setSalaAulasNormal(boolean salaAulasNormal) {
        this.salaAulasNormal = salaAulasNormal;
    }

    public boolean isVideoconferencia() {
        return videoconferencia;
    }
    public void setVideoconferencia(boolean videoconferencia) {
        this.videoconferencia = videoconferencia;
    }

    public boolean isAtrio() {
        return atrio;
    }
    public void setAtrio(boolean atrio) {
        this.atrio = atrio;
    }


    public String getRoomSpec() {
        return roomSpec;
    }
    public void setRoomSpec(String newRoomSpec) {
        this.roomSpec = newRoomSpec;
    }

}
