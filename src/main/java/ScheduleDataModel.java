import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class ScheduleDataModel {

    private ArrayList<LineSchedule> scheduleEntries;
    private ArrayList<Rooms> roomEntries;
    private LocalDate startDate;
    private LocalDate endDate;

    private TreeMap<ScheduleInstant, LineSchedule> scheduleMap;


    public ScheduleDataModel(String scheduleFile, boolean scheduleRemote, String roomsFile, boolean roomsRemote) {
        try {
            if (scheduleRemote) scheduleEntries = readGitScheduleCSV(scheduleFile);
            else scheduleEntries = readScheduleCSV(scheduleFile);
            if (roomsRemote) roomEntries = readRoomsCSV(roomsFile);
            else roomEntries = readRoomsCSV(roomsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<LineSchedule> getScheduleEntries() {
        return scheduleEntries;
    }

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

    public static ArrayList<Rooms> readRoomsCSV(String csvFile) {
        ArrayList<Rooms> lineRooms = new ArrayList<>();
        try (FileReader fileReader = new FileReader(csvFile); CSVParser csvParser = CSVFormat.DEFAULT.withDelimiter(';').withHeader().parse(fileReader)) {
            for (CSVRecord csvRecord : csvParser) {
                Rooms Rooms = new Rooms(csvRecord);
                lineRooms.add(Rooms);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineRooms;
    }

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
