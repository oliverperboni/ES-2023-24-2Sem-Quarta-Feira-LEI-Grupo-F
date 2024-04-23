package structures;

/**
 * The Room class represents a physical room in a building.
 */
public class Room {

    private String edificio;
    private String nomeSala;
    private int capacidadeNormal;
    private int capacidadeExame;
    private String numCaracteristicas;
    private boolean horarioSalaVisivelPortalPublico;
   	private String roomSpec;

    /**
     * Constructs a new Room object with the given parameters.
     *
     * @param edificio           The building where the room is located.
     * @param nomeSala           The name of the room.
     * @param capacidadeNormal   The normal capacity of the room.
     * @param capacidadeExame    The exam capacity of the room.
     * @param numCaracteristicas The number of characteristics of the room.
     * @param roomSpec           Special specifications of the room.
     */
    public Room(String edificio, String nomeSala, int capacidadeNormal,
                int capacidadeExame, String numCaracteristicas, String roomSpec) {
        this.edificio = edificio;
        this.nomeSala = nomeSala;
        this.capacidadeNormal = capacidadeNormal;
        this.capacidadeExame = capacidadeExame;
        this.numCaracteristicas = numCaracteristicas;
        this.roomSpec = roomSpec;
    }

    /**
     * Parses the given string to a Boolean value.
     *
     * @param bool The string to parse.
     * @return The Boolean value.
     */
    public Boolean parseBoolean(String bool){
        return "X".equals(bool);
    }

    /**
     * Returns the building where the room is located.
     *
     * @return The building.
     */
    public String getEdificio() {
        return edificio;
    }

    /**
     * Sets the building where the room is located.
     *
     * @param edificio The building to set.
     */
    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    /**
     * Returns the name of the room.
     *
     * @return The name of the room.
     */
    public String getNomeSala() {
        return nomeSala;
    }

    /**
     * Sets the name of the room.
     *
     * @param nomeSala The name of the room to set.
     */
    public void setNomeSala(String nomeSala) {
        this.nomeSala = nomeSala;
    }

    /**
     * Returns the normal capacity of the room.
     *
     * @return The normal capacity of the room.
     */
    public int getCapacidadeNormal() {
        return capacidadeNormal;
    }

    /**
     * Sets the normal capacity of the room.
     *
     * @param capacidadeNormal The normal capacity of the room to set.
     */
    public void setCapacidadeNormal(int capacidadeNormal) {
        this.capacidadeNormal = capacidadeNormal;
    }

    /**
     * Returns the exam capacity of the room.
     *
     * @return The exam capacity of the room.
     */
    public int getCapacidadeExame() {
        return capacidadeExame;
    }

    /**
     * Sets the exam capacity of the room.
     *
     * @param capacidadeExame The exam capacity of the room to set.
     */
    public void setCapacidadeExame(int capacidadeExame) {
        this.capacidadeExame = capacidadeExame;
    }

    /**
     * Returns the number of characteristics of the room.
     *
     * @return The number of characteristics of the room.
     */
    public String getNumCaracteristicas() {
        return numCaracteristicas;
    }

    /**
     * Sets the number of characteristics of the room.
     *
     * @param numCaracteristicas The number of characteristics of the room to set.
     */
    public void setNumCaracteristicas(String numCaracteristicas) {
        this.numCaracteristicas = numCaracteristicas;
    }

    /**
     * Returns the special specifications of the room.
     *
     * @return The special specifications of the room.
     */
    public String getRoomSpec() {
        return roomSpec;
    }

    /**
     * Sets the special specifications of the room.
     *
     * @param newRoomSpec The special specifications of the room to set.
     */
    public void setRoomSpec(String newRoomSpec) {
        this.roomSpec = newRoomSpec;
    }
}