package core;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import structures.LineSchedule;
import structures.Room;
import structures.ScheduleInstant;
import structures.Tuple2;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

/**
* The core.ScheduleDataModel class represents the data model object associated with the scheduling application at any
* given time. It contains structures that hold information about the currently loaded schedule and list of rooms
* and their specifications.
* @author Daniel Ferreira
* @author António Pombeiro
*/
public class ScheduleDataModel {

    private ArrayList<LineSchedule> scheduleEntries;
    private ArrayList<Room> roomEntries;
   	private TreeMap<ScheduleInstant, LineSchedule> scheduleMap;
    private String scheduleFilePath;
    private boolean scheduleFileRemote;
    private String roomsFilePath;
    private boolean roomsFileRemote;

    private List<String> roomColumnHeaders;

    public ScheduleDataModel(String scheduleFilePath, boolean scheduleRemote,
                             String roomsFilePath, boolean roomsRemote) {
        try {
            if (scheduleRemote) {
                this.scheduleEntries = readGitScheduleCSV(scheduleFilePath);
                this.scheduleFileRemote = true;
            } else {
                this.scheduleEntries = readScheduleCSV(scheduleFilePath);
                this.scheduleFileRemote = false;
            }
            if (roomsRemote) {
                Tuple2<ArrayList<Room>, List<String>> tuple = readRoomsCSV(roomsFilePath);
                this.roomEntries = tuple.getRoomLineArray();
                this.roomColumnHeaders = tuple.getRoomColumnHeaders();
                this.roomsFileRemote = true;
            } else {
                Tuple2<ArrayList<Room>, List<String>> tuple = readRoomsCSV(roomsFilePath);
                this.roomEntries = tuple.getRoomLineArray();
                this.roomColumnHeaders = tuple.getRoomColumnHeaders();
                this.roomsFileRemote = false;
            }
            this.scheduleFilePath = scheduleFilePath;
            this.roomsFilePath = roomsFilePath;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
		
    public ArrayList<LineSchedule> getScheduleEntries() {
        return scheduleEntries;
    }
		
    public ArrayList<Room> getRoomEntries() {
        return roomEntries;
    }

    public String getScheduleFilePath() {
        return scheduleFilePath;
    }

    public void setScheduleFilePath(String newScheduleFilePath) {
        this.scheduleFilePath = newScheduleFilePath;
    }

    public boolean isScheduleFileRemote() {
        return scheduleFileRemote;
    }

    public String getRoomsFilePath() {
        return roomsFilePath;
    }

    public boolean isRoomsFileRemote() {
        return roomsFileRemote;
    }

    public List<String> getRoomColumnHeaders() {
        return roomColumnHeaders;
    }

    /**
	* Reads a schedule CSV file, and returns an ArrayList of LineSchedule objects representing every schedule entry
	* present in it. 
	* @param csvFile file path to schedule CSV file
	* @return ArrayList of all schedule entries in the file
	* @since 1.0
	*/
    public static ArrayList<LineSchedule> readScheduleCSV(String csvFile) {
        ArrayList<LineSchedule> lineScheduleArray = new ArrayList<>();
        try (FileReader fileReader = new FileReader(csvFile); CSVParser csvParser = CSVFormat.DEFAULT.withDelimiter(';').withHeader().parse(fileReader)) {
            for (CSVRecord csvRecord : csvParser) {
                LineSchedule schedule = new LineSchedule(csvRecord.get("Curso"), csvRecord.get("Unidade Curricular"), csvRecord.get("Turno"), csvRecord.get("Turma"), Integer.parseInt(csvRecord.get("Inscritos no turno")), csvRecord.get("Dia da semana"), csvRecord.get("Hora início da aula"), csvRecord.get("Hora fim da aula"), csvRecord.get("Data da aula"), csvRecord.get("Características da sala pedida para a aula"), csvRecord.get("Sala atribuída à aula"));
                lineScheduleArray.add(schedule);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineScheduleArray;
    }

	/**
	* Reads a rooms CSV file, and returns an ArrayList of Room objects representing every room entry present in it.
	* @param csvFile file path to rooms CSV file
	* @return ArrayList of all room entries in the file
	* @since 1.0
	*/
    public static Tuple2<ArrayList<Room>, List<String>> readRoomsCSV(String csvFile) {
        ArrayList<Room> roomLineArray = new ArrayList<>();
        List<String> columnHeaders = new ArrayList<>();
        try (FileReader fileReader = new FileReader(csvFile); CSVParser csvParser = CSVFormat.DEFAULT.withDelimiter(';').withHeader().parse(fileReader)) {
            columnHeaders = csvParser.getHeaderNames();


            for (CSVRecord csvRecord : csvParser) {
                String edificio = csvRecord.get("Edifício");
                String nomeSala = csvRecord.get("Nome sala");
                int capacidadeNormal = Integer.parseInt(csvRecord.get("Capacidade Normal"));
                int capacidadeExame = Integer.parseInt(csvRecord.get("Capacidade Exame"));
                String numCaracteristicas = csvRecord.get("N¼ caracterásticas");

                List<String> roomSpecs = new ArrayList<>();
                for (int i = 4; i < columnHeaders.size(); i++)
                    if (csvRecord.get(i).equals("X"))
                        roomSpecs.add(columnHeaders.get(i));

                Room roomLine = new Room(edificio, nomeSala, capacidadeNormal, capacidadeExame, numCaracteristicas, roomSpecs);
                roomLineArray.add(roomLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Tuple2<>(roomLineArray, columnHeaders);
    }

	/**
	* Reads a schedule CSV file hosted on a GitHub repository, and returns an ArrayList of LineSchedule objects
	* representing every schedule entry present in it. 
	* @param fileURL schedule CSV file URL
	* @return ArrayList of all schedule entries in the file
	* @since 1.0
	*/
    //Para ler ficheiros CSV do GitHub
    public static ArrayList<LineSchedule> readGitScheduleCSV(String fileURL) throws IOException {
        URL fileUrl = new URL(fileURL);
        InputStream inputStream = fileUrl.openStream();

        ArrayList<LineSchedule> lineSchedules = new ArrayList<>();
        try (Scanner scanner = new Scanner(inputStream)) {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // Process each line as needed
                String[] fields = line.split(";"); // Assumindo que os campos no CSV são separados por ponto e vírgula
                if (fields.length >= 11) { // Certifique-se de que há pelo menos 11 campos
                    LineSchedule schedule = new LineSchedule(
                            fields[0], fields[1], fields[2], fields[3],
                            Integer.parseInt(fields[4]), fields[5], fields[6],
                            fields[7], fields[8], fields[9], fields[10]
                    );
                    lineSchedules.add(schedule);
                }
            }
        }
        return lineSchedules;
    }
		
}
