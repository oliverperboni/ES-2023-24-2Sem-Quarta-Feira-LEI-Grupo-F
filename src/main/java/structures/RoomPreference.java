package structures;

/**
 * The RoomPreference class hosts a series of constant values referring to ISCTE-IUL room specifications. They are
 * used to represent a user's room specification preferences when rescheduling a class, or scheduling an entire new
 * course.
 * @since 1.0
 */
public enum RoomPreference {

	ANFITEATRO_AULAS("Anfiteatro aulas"), APOIO_TECNICO_EVENTOS("Apoio técnico eventos"), ARQ1("Arq 1"),
	ARQ2("Arq 2"), ARQ3("Arq 3"), ARQ4("Arq 4"), ARQ5("Arq 5"), ARQ6("Arq 6"), ARQ9("Arq 9"),
	BYOD("BYOD (Bring Your Own Device)"), FOCUS_GROUP("Focus Group"),
	LABORATORIO_ARQUITETURA_COMPUTADORES_I("Laboratório de Arquitectura de Computadores I"),
	LABORATORIO_ARQUITETURA_COMPUTADORES_II("Laboratório de Arquitectura de Computadores II"),
	LABORATORIO_BASES_ENGENHARIA("Laboratório de Bases de Engenharia"),
	LABORATORIO_ELETRONICA("Laboratório de Electrónica"), LABORATORIO_INFORMATICA("Laboratório de Informática"),
	LABORATORIO_JORNALISMO("Laboratório de Jornalismo"),
	LABORATORIO_REDES_COMPUTADORES_I("Laboratório de Redes de Computadores I"),
	LABORATORIO_REDES_COMPUTADORES_II("Laboratório de Redes de Computadores II"),
	LABORATORIO_TELECOMUNICACOES("Laboratório de Telecomunicações"), SALA_AULAS_MESTRADO("Sala Aulas Mestrado"),
	SALA_AULAS_MESTRADO_PLUS("Sala Aulas Mestrado Plus"), SALA_NEE("Sala NEE"), SALA_PROVAS("Sala Provas"),
	SALA_REUNIAO("Sala Reunião"), SALA_ARQUITETURA("Sala de Arquitectura"), SALA_AULAS_NORMAL("Sala de Aulas normal"),
	VIDEOCONFERENCIA("videoconferéncia"), ATRIO("Atrio");

	/**
	 * Name of the specfication of the room type represented by this preference.
	 */
	private String stringValue;

	/**
	 * Constructor for the RoomPreference class.
	 *
	 * @param stringValue        name of the specfication of the room type represented by this preference
	 */
	RoomPreference(String stringValue) {
		this.stringValue = stringValue;
	}

	/**
	 * Returns a string containing the name of the room's type/specification.
	 *
	 * @return String of the room's specification name
	 */
	@Override
	public String toString() {
		return stringValue;
	}

}