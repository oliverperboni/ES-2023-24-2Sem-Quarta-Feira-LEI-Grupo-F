package structures;

import java.util.ArrayList;
import java.util.List;

/**
* The RoomPreference class hosts a series of constant values referring to ISCTE-IUL room specifications. They are
* used to represent a user's room specification preferences when rescheduling a class, or scheduling an entire new
* course.
*/
public class RoomPreference {
	
	/**
	* Number of specfications of the room type represented by this preference.
	*/
	private String numCaracteristicas;
    
	/**
	* Name of the specfication of the room type represented by this preference.
	*/
	private String stringValue;

	public static final RoomPreference ANFITEATRO_AULAS = new RoomPreference("", "Anfiteatro aulas");
	public static final RoomPreference APOIO_TECNICO_EVENTOS = new RoomPreference("", "Apoio técnico eventos");
	public static final RoomPreference ARQ1 = new RoomPreference("", "Arq 1");
	public static final RoomPreference ARQ2 = new RoomPreference("", "Arq 2");
	public static final RoomPreference ARQ3 = new RoomPreference("", "Arq 3");
	public static final RoomPreference ARQ4 = new RoomPreference("", "Arq 4");
	public static final RoomPreference ARQ5 = new RoomPreference("", "Arq 5");
	public static final RoomPreference ARQ6 = new RoomPreference("", "Arq 6");
	public static final RoomPreference ARQ9 = new RoomPreference("", "Arq 9");
	public static final RoomPreference BYOD = new RoomPreference("", "BYOD (Bring Your Own Device");
	public static final RoomPreference FOCUS_GROUP = new RoomPreference("", "Focus Group");
	
	public static final RoomPreference LABORATORIO_ARQUITETURA_COMPUTADORES_I = 
	new RoomPreference("",  "Laboratório de Arquitectura de Computadores I");

	public static final RoomPreference LABORATORIO_ARQUITETURA_COMPUTADORES_II = 
	new RoomPreference("",  "Laboratório de Arquitectura de Computadores II");

	public static final RoomPreference LABORATORIO_BASES_ENGENHARIA = 
	new RoomPreference("", "Laboratório de Bases de Engenharia");

	public static final RoomPreference LABORATORIO_ELETRONICA = 
	new RoomPreference("", "Laboratório de Electrónica");

	public static final RoomPreference LABORATORIO_INFORMATICA = 
	new RoomPreference("", "Laboratório de Informática");

	public static final RoomPreference LABORATORIO_JORNALISMO = 
	new RoomPreference("", "Laboratório de Jornalismo");

	public static final RoomPreference LABORATORIO_REDES_COMPUTADORES_I = 
	new RoomPreference("", "Laboratório de Redes de Computadores I");

	public static final RoomPreference LABORATORIO_REDES_COMPUTADORES_II = 
	new RoomPreference("", "Laboratório de Redes de Computadores II");

	public static final RoomPreference LABORATORIO_TELECOMUNICACOES = 
	new RoomPreference("", "Laboratório de Telecomunicações");

	public static final RoomPreference SALA_AULAS_MESTRADO = new RoomPreference("", "Sala Aulas Mestrado");
	public static final RoomPreference SALA_AULAS_MESTRADO_PLUS = new RoomPreference("", "Sala Aulas Mestrado Plus");
	public static final RoomPreference SALA_NEE = new RoomPreference("",  "Sala NEE");
	public static final RoomPreference SALA_PROVAS = new RoomPreference("", "Sala Provas");
	public static final RoomPreference SALA_REUNIAO = new RoomPreference("", "Sala Reunião");
	public static final RoomPreference SALA_ARQUITETURA = new RoomPreference("", "Sala de Arquitectura");
	public static final RoomPreference SALA_AULAS_NORMAL = new RoomPreference("", "Sala de Aulas normal");
	public static final RoomPreference VIDEOCONFERENCIA = new RoomPreference("", "videoconferéncia");
	public static final RoomPreference ATRIO = new RoomPreference("",  "Atrio");

	
	/**
	* Constructor for the RoomPreference class.
	* @param numCaracteristicas number of specfications of the room type represented by this preference
	* @param stringValue name of the specfication of the room type represented by this preference
	*/
    public RoomPreference (String numCaracteristicas, String stringValue) {
        this.numCaracteristicas = numCaracteristicas;
        this.stringValue = stringValue;
    }

	
    public String getNumCaracteristicas() {
        return numCaracteristicas;
    }
	
    public void setNumCaracteristicas(String numCaracteristicas) {
        this.numCaracteristicas = numCaracteristicas;
    }

	/**
	* Returns a string containing the name of the room's type/specification.
	* @return String of the room's specification name
	* @since 1.0
	*/
    @Override
    public String toString() {
        return stringValue;
    }
	
    public void setStringValue(String newStringValue) {
        this.stringValue = newStringValue;
    }

	public static ArrayList<RoomPreference> getAllRoomPreference(){
		ArrayList<RoomPreference> preferences = new ArrayList<>();
		preferences.add(ANFITEATRO_AULAS);
		preferences.add(APOIO_TECNICO_EVENTOS);
		preferences.add(ARQ1);
		preferences.add(ARQ2);
		preferences.add(ARQ3);
		preferences.add(ARQ4);
		preferences.add(ARQ5);
		preferences.add(ARQ6);
		preferences.add(ARQ9);
		preferences.add(BYOD);
		preferences.add(FOCUS_GROUP);
		preferences.add(LABORATORIO_ARQUITETURA_COMPUTADORES_I);
		preferences.add(LABORATORIO_ARQUITETURA_COMPUTADORES_II);
		preferences.add(LABORATORIO_BASES_ENGENHARIA);
		preferences.add(LABORATORIO_ELETRONICA);
		preferences.add(LABORATORIO_INFORMATICA);
		preferences.add(LABORATORIO_JORNALISMO);
		preferences.add(LABORATORIO_REDES_COMPUTADORES_I);
		preferences.add(LABORATORIO_REDES_COMPUTADORES_II);
		preferences.add(LABORATORIO_TELECOMUNICACOES);
		preferences.add(SALA_AULAS_MESTRADO);
		preferences.add(SALA_AULAS_MESTRADO_PLUS);
		preferences.add(SALA_NEE);
		preferences.add(SALA_PROVAS);
		preferences.add(SALA_REUNIAO);
		preferences.add(SALA_ARQUITETURA);
		preferences.add(SALA_AULAS_NORMAL);
		preferences.add(VIDEOCONFERENCIA);
		preferences.add(ATRIO);
		return preferences;
	}

}
