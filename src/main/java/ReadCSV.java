import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;


public class ReadCSV {

    //PARA LER FICHEIROS LOCAIS
    public static ArrayList<LineSchedule> readScheduleCSV(String csvFile) {
        ArrayList<LineSchedule> lineSchedules = new ArrayList<>();
        try (FileReader fileReader = new FileReader(csvFile); CSVParser csvParser = CSVFormat.DEFAULT.withDelimiter(';').withHeader().parse(fileReader)) {
            for (CSVRecord csvRecord : csvParser) {
                LineSchedule schedule = new LineSchedule(csvRecord.get("Curso"), csvRecord.get("Unidade Curricular"), csvRecord.get("Turno"), csvRecord.get("Turma"), Integer.parseInt(csvRecord.get("Inscritos no turno")), csvRecord.get("Dia da semana"), csvRecord.get("Hora início da aula"), csvRecord.get("Hora fim da aula"), csvRecord.get("Data da aula"), csvRecord.get("Características da sala pedida para a aula"), csvRecord.get("Sala atribuída à aula"));
                lineSchedules.add(schedule);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineSchedules;
    }

    //PARA LER FICHEIROS CSV DO GIT
    public static ArrayList<LineSchedule> readScheduleCSV(InputStream inputStream) {
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

    public static InputStream getInputStreamFromURL(String url) throws IOException {
        URL fileUrl = new URL(url);
        return fileUrl.openStream();
    }

    //ESTA MAIN E APENAS PARA VERIFICAR QUE ERA POSSIVEL LER OS FICHEIROS CSV
    /*public static void main(String[] args) {
        String localFile = "csv/HorarioDeExemplo.csv";
        ArrayList<LineSchedule> lineSchedules = readScheduleCSV(localFile);
        for (LineSchedule schedule : lineSchedules) {
            System.out.println(schedule);
        }

        System.out.println("GITTTTTTT");
        String gitFileURL = "https://raw.githubusercontent.com/oliverperboni/ES-2023-24-2Sem-Quarta-Feira-LEI-Grupo-F/main/csv/HorarioDeExemplo.csv?token=GHSAT0AAAAAACOQ4UILKTZY3WHI357PEWPGZPR5IHA";
        try {
            InputStream inputStream = getInputStreamFromURL(gitFileURL);
            ArrayList<LineSchedule> lineSchedules2 = readScheduleCSV(inputStream);
            for (LineSchedule schedule : lineSchedules2) {
                System.out.println(schedule);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    } */
}
