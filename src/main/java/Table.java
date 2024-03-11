import java.text.Normalizer;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Table {
    private JFrame app;

    public Table() {

        // array com os dados do excel
        ArrayList<LineSchedule> data = ReadCSV.readScheduleCSV("csv/HorarioDeExemplo.csv");
        // classe que cria e adiciona os filtros
        TableFilters tabFilter = new TableFilters();
        // Cria uma tabela
        DefaultTableModel model = new DefaultTableModel();
        addColumns(model);

        // adiciona as linhas do CSV a tabela
        for (LineSchedule ls : data) {
            Object[] row = { normalizeValue(ls.getCurso()), normalizeValue(ls.getUnidadeCurricular()),
                    normalizeValue(ls.getTurno()), normalizeValue(ls.getTurma()), ls.getInscritos(),
                    ls.getDiaSemana(), ls.getHoraInicio(), ls.getHoraFim(), ls.getDataAula(),
                    ls.getCaracteristicasSala(), ls.getSala(), countWeeksBetween("02/09/2022", ls.getDataAula()),getWeekOfYear(ls.getDataAula()) };
            model.addRow(row);
        }

        // cria tabela
        JTable table = new JTable(model);
        app = tabFilter.addFilter(app, table);
        app.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // cria a opção de scroll se necessario
        JScrollPane scrollPane = new JScrollPane(table);

        app.getContentPane().add(scrollPane);
        app.setVisible(true);

    }

    private void addColumns(DefaultTableModel model) {
        model.addColumn("Curso");
        model.addColumn("Unidade Curricular");
        model.addColumn("Turno");
        model.addColumn("Turma");
        model.addColumn("Inscritos no turno");
        model.addColumn("Dia da Semana");
        model.addColumn("Hora Início da Aula");
        model.addColumn("Hora Fim da Aula");
        model.addColumn("Data da Aula");
        model.addColumn("Características da Sala");
        model.addColumn("Sala atribuida à sala");
        model.addColumn("Semana Semestre");
        model.addColumn("Semana Ano");
    }

    private String normalizeValue(String value) {
        if (value != null) {
            value = Normalizer.normalize(value, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
            return value.toUpperCase();
        }
        return "";
    }

    public static int getWeekOfYear(String dateString) {
        if (!dateString.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(dateString, formatter);
            return date.get(java.time.temporal.ChronoField.ALIGNED_WEEK_OF_YEAR);
        }
        return -1;
    }

    public static int countWeeksBetween(String startDateString, String endDateString) {

        if (!endDateString.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate startDate = LocalDate.parse(startDateString, formatter);
            LocalDate endDate = LocalDate.parse(endDateString, formatter);
            return (int) ChronoUnit.WEEKS.between(startDate, endDate);
        }
        return -1;
    }

    public static void main(String[] args) {
        new Table();
    }

}
