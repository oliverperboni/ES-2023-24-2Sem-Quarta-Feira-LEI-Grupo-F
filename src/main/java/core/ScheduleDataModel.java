package core;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import structures.LineSchedule;
import structures.Room;
import structures.RoomsTuple;
import structures.ScheduleInstant;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

/**
 * The ScheduleDataModel class represents the data model object associated with the scheduling application at any
 * given time. It contains structures that hold information about the currently loaded schedule and list of rooms
 * and their specifications.
 */
public class ScheduleDataModel {

    private ArrayList<Room> roomEntries;
    private TreeMap<ScheduleInstant, List<LineSchedule>> scheduleMap;
    private List<String> roomColumnHeaders;

    private String scheduleFilePath;
    private boolean scheduleFileRemote;
    private String roomsFilePath;
    private boolean roomsFileRemote;

    public ScheduleDataModel(String scheduleFilePath, boolean scheduleRemote,
                             String roomsFilePath, boolean roomsRemote) {
        if (scheduleRemote) {
            this.scheduleFileRemote = true;
        } else {
            this.scheduleMap = readScheduleCSV(scheduleFilePath);
            this.scheduleFileRemote = false;
        }
        if (roomsRemote) {
            RoomsTuple<ArrayList<Room>, List<String>> tuple = readRoomsCSV(roomsFilePath);
            this.roomEntries = tuple.getRoomLineArray();
            this.roomColumnHeaders = tuple.getRoomColumnHeaders();
            this.roomsFileRemote = true;
        } else {
            RoomsTuple<ArrayList<Room>, List<String>> tuple = readRoomsCSV(roomsFilePath);
            this.roomEntries = tuple.getRoomLineArray();
            this.roomColumnHeaders = tuple.getRoomColumnHeaders();
            this.roomsFileRemote = false;
        }
        this.scheduleFilePath = scheduleFilePath;
        this.roomsFilePath = roomsFilePath;
    }

    public TreeMap<ScheduleInstant, List<LineSchedule>> getScheduleMap() {
        return scheduleMap;
    }

    public List<LineSchedule> getScheduleEntries() {
        return scheduleMap.values().stream().flatMap(List::stream).toList();
    }

    public List<Room> getRoomEntries() {
        return roomEntries;
    }

    public List<String> getRoomColumnHeaders() {
        return roomColumnHeaders;
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

    /**
     * Reads a schedule CSV file, and returns a TreeMap with keys of type ScheduleInstant and values of type List<LineSchedule>.
     * ScheduleInstant keys correspond to a time slot and date pair. List<LineSchedule> values correspond to lists
     * of all classes scheduled on that day and time slot.
     *
     * @param csvFile file path to schedule CSV file
     * @return TreeMap<ScheduleInstant, List<LineSchedule>> with all ScheduleInstants as keys, and lists of all classes
     * scheduled for each ScheduleInstant as values
     * @since 1.0
     */
    public static TreeMap<ScheduleInstant, List<LineSchedule>> readScheduleCSV(String csvFile) {
        TreeMap<ScheduleInstant, List<LineSchedule>> treeMap = new TreeMap<>(Comparator.nullsFirst(Comparator.naturalOrder()));

        try (FileReader fileReader = new FileReader(csvFile); CSVParser csvParser = CSVFormat.DEFAULT.withDelimiter(';').withHeader().parse(fileReader)) {
            for (CSVRecord csvRecord : csvParser) {
                LineSchedule schedule = new LineSchedule(csvRecord.get("Curso"), csvRecord.get("Unidade Curricular"), csvRecord.get("Turno"), csvRecord.get("Turma"), Integer.parseInt(csvRecord.get("Inscritos no turno")), csvRecord.get("Dia da semana"), csvRecord.get("Hora início da aula"), csvRecord.get("Hora fim da aula"), csvRecord.get("Data da aula"), csvRecord.get("Características da sala pedida para a aula"), csvRecord.get("Sala atribuída à aula"));
                ScheduleInstant scheduleInstant = schedule.getScheduleInstant();

                if (!treeMap.containsKey(scheduleInstant)) {
                    List<LineSchedule> lineScheduleList = new ArrayList<>();
                    lineScheduleList.add(schedule);
                    treeMap.put(scheduleInstant, lineScheduleList);
                } else {
                    List<LineSchedule> lineScheduleList = treeMap.get(scheduleInstant);
                    lineScheduleList.add(schedule);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return treeMap;
    }

    /**
     * Reads a rooms CSV file, and returns an ArrayList of Room objects representing every room entry present in it.
     *
     * @param csvFile file path to rooms CSV file
     * @return ArrayList of all room entries in the file
     * @since 1.0
     */
    public static RoomsTuple<ArrayList<Room>, List<String>> readRoomsCSV(String csvFile) {
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
        return new RoomsTuple<>(roomLineArray, columnHeaders);
    }

    /**
     * Reads a schedule CSV file, and returns a TreeMap with keys of type ScheduleInstant and values of type List<LineSchedule>.
     * ScheduleInstant keys correspond to a time slot and date pair. List<LineSchedule> values correspond to lists
     * of all classes scheduled on that day and time slot.
     *
     * @param fileURL schedule CSV file URL
     * @return TreeMap<ScheduleInstant, List < LineSchedule>> with all ScheduleInstants as keys, and lists of all classes
     * scheduled for each ScheduleInstant as values
     * @since 1.0
     */
    public static TreeMap<ScheduleInstant, List<LineSchedule>> readGitScheduleCSV(String fileURL) throws IOException {
        URL fileUrl = new URL(fileURL);
        InputStream inputStream = fileUrl.openStream();

        TreeMap<ScheduleInstant, List<LineSchedule>> treeMap = new TreeMap<>(Comparator.nullsFirst(Comparator.naturalOrder()));
        try (Scanner scanner = new Scanner(inputStream)) {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(";");
                if (fields.length >= 11) { // Verificar que existem pelo menos 11 campos
                    LineSchedule schedule = new LineSchedule(fields[0], fields[1], fields[2], fields[3], Integer.parseInt(fields[4]),
                            fields[5], fields[6], fields[7], fields[8], fields[9], fields[10]
                    );
                    ScheduleInstant scheduleInstant = schedule.getScheduleInstant();

                    if (!treeMap.containsKey(scheduleInstant)) {
                        List<LineSchedule> lineScheduleList = new ArrayList<>();
                        lineScheduleList.add(schedule);
                        treeMap.put(scheduleInstant, lineScheduleList);
                    } else {
                        List<LineSchedule> lineScheduleList = treeMap.get(scheduleInstant);
                        lineScheduleList.add(schedule);
                    }
                }
            }
        }
        return treeMap;
    }
}