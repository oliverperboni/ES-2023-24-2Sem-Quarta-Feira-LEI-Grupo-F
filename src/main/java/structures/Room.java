package structures;

public class Room {

    private String edificio;
    private String nomeSala;
    private int capacidadeNormal;
    private int capacidadeExame;
    private String numCaracteristicas;
    private boolean horarioSalaVisivelPortalPublico;
   	private String roomSpec;

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

    public String getRoomSpec() {
        return roomSpec;
    }
	
    public void setRoomSpec(String newRoomSpec) {
        this.roomSpec = newRoomSpec;
    }

}
