import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class ReadCSV {

    public static ArrayList<LineSchedule> readScheduleCSV(String csvFile) {
        ArrayList<LineSchedule> lineSchedules = new ArrayList<>();
        try (FileReader fileReader = new FileReader(csvFile); CSVParser csvParser = CSVFormat.DEFAULT.withDelimiter(';').withHeader().parse(fileReader)){
            for (CSVRecord csvRecord : csvParser) {
                LineSchedule schedule = new LineSchedule(csvRecord.get("Curso"), csvRecord.get("Unidade Curricular"), csvRecord.get("Turno"), csvRecord.get("Turma"), Integer.parseInt(csvRecord.get("Inscritos no turno")), csvRecord.get("Dia da semana"), csvRecord.get("Hora início da aula"), csvRecord.get("Hora fim da aula"), csvRecord.get("Data da aula"), csvRecord.get("Características da sala pedida para a aula"), csvRecord.get("Sala atribuída à aula"));
                lineSchedules.add(schedule);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineSchedules;
    }

    public ArrayList<LineSchedule> getCsvData(){
            return ReadCSV.readScheduleCSV("csv/HorarioDeExemplo.csv");
    }

    public static void main(String[] args) {
        String localFile = "csv/HorarioDeExemplo.csv";
        ArrayList<LineSchedule> lineSchedules = readScheduleCSV(localFile);
        for (LineSchedule schedule : lineSchedules) {
            System.out.println(schedule);
        }
        
        /// FEITO DO GIT ///
        String gitFile = "https://raw.githubusercontent.com/oliverperboni/ES-2023-24-2Sem-Quarta-Feira-LEI-Grupo-F/Load-csv/csv/HorarioDeExemplo.csv";
        ArrayList<LineSchedule> lineSchedules2 = readScheduleCSV(localFile);
        for (LineSchedule schedule : lineSchedules2) {
            System.out.println(schedule);
        }
    }
}
