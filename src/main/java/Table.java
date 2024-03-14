import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.text.Normalizer;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Table {
    private JFrame app;

    public Table(String filePath, boolean isGit) throws IOException {
        ArrayList<LineSchedule> data;

        if (isGit) {
            // Se estiver carregando do Git, use a função getInputStreamFromURL
            InputStream inputStream = ReadCSV.getInputStreamFromURL(filePath);
            data = ReadCSV.readScheduleCSV(inputStream);
        } else {
            // Se estiver carregando localmente, use a função readScheduleCSV
            data = ReadCSV.readScheduleCSV(filePath);
        }
        // classe que cria e adiciona os filtros
        TableFilters tabFilter = new TableFilters();
        // Cria uma tabela
        DefaultTableModel model = new DefaultTableModel();
        addColumns(model);

        // adiciona as linhas do CSV a tabela
        for (LineSchedule ls : data) {
            Object[] row = {normalizeValue(ls.getCurso()), normalizeValue(ls.getUnidadeCurricular()),
                    normalizeValue(ls.getTurno()), normalizeValue(ls.getTurma()), ls.getInscritos(),
                    ls.getDiaSemana(), ls.getHoraInicio(), ls.getHoraFim(), ls.getDataAula(),
                    ls.getCaracteristicasSala(), ls.getSala(), countWeeksBetween("02/09/2022", ls.getDataAula()), getWeekOfYear(ls.getDataAula())};
            model.addRow(row);
        }

        // cria tabela
        JTable table = new JTable(model);
        table.setAutoCreateRowSorter(true); // Creates a TableRowSorter for the table
        addColumnSorting(table); // Method enables "sort by column" functionality for every column on the table

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
        model.addColumn("N.º Inscritos Turno");
        model.addColumn("Dia da Semana");
        model.addColumn("Hora de Início");
        model.addColumn("Hora de Fim");
        model.addColumn("Data");
        model.addColumn("Tipo de Sala");
        model.addColumn("Sala Atribuída");
        model.addColumn("Semana do Semestre");
        model.addColumn("Semana do Ano");
    }

    private void addColumnSorting(JTable table) {
        // Gets the table's sorter and casts it to a DefaultRowSorter
        DefaultRowSorter sorter = ((DefaultRowSorter)table.getRowSorter());
        // Enables immediate sorting after an insert operation
        sorter.setSortsOnUpdates(true);

        // ArrayList to store SortKeys for the every column in the table
        ArrayList<RowSorter.SortKey> sortKeyList = new ArrayList<>();
        for (int i = 0; i < table.getColumnCount(); i++)
            // Creates a SortKey for every column, ascending order
            sortKeyList.add(new RowSorter.SortKey(i, SortOrder.ASCENDING) );

        // Adds SortKeys to sorter, and sorts
        sorter.setSortKeys(sortKeyList);
        sorter.sort();
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
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate date = LocalDate.parse(dateString, formatter);
                return date.get(java.time.temporal.ChronoField.ALIGNED_WEEK_OF_YEAR);
            }catch (DateTimeParseException e){
                return -1;
            }
        }
        return -1;
    }

    public static int countWeeksBetween(String startDateString, String endDateString) {
        if (!endDateString.isEmpty() && !startDateString.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate startDate = LocalDate.parse(startDateString, formatter);
            LocalDate endDate = LocalDate.parse(endDateString, formatter);
           if (startDate.isEqual(endDate)){
               return 0;
           }
           if ((int) ChronoUnit.WEEKS.between(startDate, endDate)> 0){
               return (int) ChronoUnit.WEEKS.between(startDate, endDate);
           }
        }
        return -1;
    }
}
