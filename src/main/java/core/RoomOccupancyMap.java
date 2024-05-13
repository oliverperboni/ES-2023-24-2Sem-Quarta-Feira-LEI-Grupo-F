/**
 * A class representing a room occupancy heatmap with filtering capabilities.
 */
package core;
import com.formdev.flatlaf.FlatLightLaf;
import org.knowm.xchart.HeatMapChart;
import org.knowm.xchart.HeatMapChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.Styler;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.*;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class RoomOccupancyMap {

    private static String filterDayOfWeek; // Seg, Ter, Qua, Qui, Sex, Sáb, Dom
    private static String filterHour; // 08:00:00
    private static String filterRoom; // D1.09

    private static String filterDayOfYear; // 20/10/2022
    private static String filterWeekOfYear; // 1, 10, 13

    /**
     * Main method to initiate the room occupancy map GUI and functionalities.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        FlatLightLaf.setup();
        // Criar JFrame para selecionar filtro
        JFrame filterFrame = new JFrame("Filtro");
        filterFrame.setLayout(new FlowLayout());
        JCheckBox applyFilterCheckbox = new JCheckBox("Aplicar Filtro");
        JLabel dayOfWeekLabel = new JLabel("Dia da Semana (Abreviado): ");
        JTextField dayOfWeekField = new JTextField(5);
        JLabel hourLabel = new JLabel("Hora (Formato HH:MM:SS): ");
        JTextField hourField = new JTextField(8);
        JLabel roomLabel = new JLabel("Nome da Sala: ");
        JTextField roomField = new JTextField(5);
        JLabel dayOfYearLabel = new JLabel("Dia (Formato dd/MM/yyyy): ");
        JTextField dayOfYearField = new JTextField(10);
        JLabel weekOfYearLabel = new JLabel("Número da Semana do Ano: ");
        JTextField weekOfYearField = new JTextField(5);
        JButton showChartButton = new JButton("Mostrar Gráfico");

        filterFrame.add(applyFilterCheckbox);
        filterFrame.add(dayOfWeekLabel);
        filterFrame.add(dayOfWeekField);
        filterFrame.add(hourLabel);
        filterFrame.add(hourField);
        filterFrame.add(roomLabel);
        filterFrame.add(roomField);
        filterFrame.add(dayOfYearLabel);
        filterFrame.add(dayOfYearField);
        filterFrame.add(weekOfYearLabel);
        filterFrame.add(weekOfYearField);
        filterFrame.add(showChartButton);
        filterFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        filterFrame.pack();
        filterFrame.setVisible(true);

        showChartButton.addActionListener(e -> {
            boolean applyFilter = applyFilterCheckbox.isSelected();
            filterDayOfWeek = dayOfWeekField.getText().trim();
            filterHour = hourField.getText().trim();
            filterRoom = roomField.getText().trim();
            filterDayOfYear = dayOfYearField.getText().trim();
            filterWeekOfYear = weekOfYearField.getText().trim();

            HeatMapChart chart =
                    new HeatMapChartBuilder().xAxisTitle("Dia da Semana").yAxisTitle("Hora de Início").theme(Styler.ChartTheme.Matlab).build();

            chart.getStyler().setShowWithinAreaPoint(true);
            chart.getStyler().setLegendPosition(Styler.LegendPosition.OutsideS);
            chart.getStyler().setLegendLayout(Styler.LegendLayout.Horizontal);

            chart.getStyler().setPlotContentSize(1);
            chart.getStyler().setShowValue(true);

            // Lê os dados do arquivo CSV
            List<String[]> data = readCSV("csv/HorarioDeExemplo.csv");

            // Prepara os dados para o gráfico de mapa de calor
            int[] xData = {1, 2, 3, 4, 5, 6, 7}; // Dias da semana
            int[] yData = {800, 830, 900, 930, 1000, 1030, 1100, 1130, 1200, 1230, 1300, 1330, 1400, 1430, 1500, 1530, 1600, 1630, 1700, 1730, 1800, 1830, 1900, 1930, 2000, 2030, 2100, 2130}; // Horas de início
            int[][] heatData = new int[xData.length][yData.length];

            for (int i = 0; i < xData.length; i++) {
                for (int y = 0; y < yData.length; y++) {
                    if (applyFilter) {
                        heatData[i][y] = haveFilter(data, i, y);
                    } else {
                        heatData[i][y] = countOccurrences(data, i, y);
                    }
                }
            }

            // Cria o gráfico de mapa de calor
            chart.addSeries("Basic HeatMap", xData, yData, heatData);

            // Cria um novo JFrame para exibir o gráfico
            JFrame frame = new JFrame("Heatmap");
            JPanel chartPanel = new XChartPanel<>(chart);
            frame.getContentPane().add(chartPanel);
            frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        });
    }

    /**
     * Read CSV file and return data as a list of arrays of strings.
     *
     * @param filename The filename of the CSV file
     * @return List of arrays of strings representing data from the CSV file
     */
    public static List<String[]> readCSV(String filename) {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            // Ignora primeira linha (cabeçalhos das colunas)
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                data.add(values);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    private static final Map<String, Integer> DAY_OF_WEEK_MAP = new HashMap<>();
    static {
        DAY_OF_WEEK_MAP.put("Dom", 0);
        DAY_OF_WEEK_MAP.put("Seg", 1);
        DAY_OF_WEEK_MAP.put("Ter", 2);
        DAY_OF_WEEK_MAP.put("Qua", 3);
        DAY_OF_WEEK_MAP.put("Qui", 4);
        DAY_OF_WEEK_MAP.put("Sex", 5);
        DAY_OF_WEEK_MAP.put("Sáb", 6);
    }

    /**
     * Convert a day of the week string to an integer.
     *
     * @param dayOfWeek The day of the week string (Abbreviated)
     * @return Integer representing the day of the week
     * @throws IllegalArgumentException if the input day of the week is invalid
     */
    public static int convertDayOfWeekToInt(String dayOfWeek) {
        if (DAY_OF_WEEK_MAP.containsKey(dayOfWeek)) {
            return DAY_OF_WEEK_MAP.get(dayOfWeek);
        } else {
            throw new IllegalArgumentException("Dia da semana inválido: " + dayOfWeek);
        }
    }

    /**
     * Count occurrences of a particular day of the week and start time combination.
     *
     * @param data       The data from the CSV file
     * @param dayOfWeek  The day of the week index
     * @param startTime  The start time index
     * @return Number of occurrences
     */
    public static int countOccurrences(List<String[]> data, int dayOfWeek, int startTime) {
        int count = 0;
        for (String[] row : data) {
            int rowDayOfWeek = convertDayOfWeekToInt(row[5]);
            int rowStartTime = convertDateOfDayToInt(row[6]);
            if (rowDayOfWeek == dayOfWeek && rowStartTime == startTime) {
                count++;
            }
        }
        return count;
    }

    /**
     * Check if a particular combination meets the applied filter criteria.
     *
     * @param data       The data from the CSV file
     * @param dayOfWeek  The day of the week index
     * @param startTime  The start time index
     * @return Number of occurrences meeting the criteria
     */
    public static int haveFilter(List<String[]> data, int dayOfWeek, int startTime) {
        int count = 0;
        for (String[] row : data) {
            int rowDayOfWeek = convertDayOfWeekToInt(row[5]);
            int rowStartTime = convertDateOfDayToInt(row[6]);
            String room = row[10];
            String day = row[8];
            int week = getWeekOfYear(day);

            boolean meetsCriteria = true;
            if (!filterDayOfWeek.isBlank()) {
                int filtroDataInt = convertDayOfWeekToInt(filterDayOfWeek);
                if (rowDayOfWeek != filtroDataInt) {
                    meetsCriteria = false;
                }
            }

            if (!filterHour.isBlank()) {
                int filtroHoraInt = convertDateOfDayToInt(filterHour);
                if (rowStartTime != filtroHoraInt) {
                    meetsCriteria = false;
                }
            }

            if (!filterRoom.isBlank() && !room.equals(filterRoom)) {
                meetsCriteria = false;
            }

            if (!filterDayOfYear.isBlank() && !day.equals(filterDayOfYear)) {
                meetsCriteria = false;
            }

            if (!filterWeekOfYear.isBlank() && week != Integer.parseInt(filterWeekOfYear)) {
                meetsCriteria = false;
            }
            if (meetsCriteria && rowDayOfWeek == dayOfWeek && rowStartTime == startTime) {
                count++;
            }
        }
        return count;
    }

    private static final Map<String, Integer> DATE_OF_DAY_MAP = new HashMap<>();
    static {
        DATE_OF_DAY_MAP.put("08:00:00", 0);
        DATE_OF_DAY_MAP.put("08:30:00", 1);
        DATE_OF_DAY_MAP.put("09:00:00", 2);
        DATE_OF_DAY_MAP.put("09:30:00", 3);
        DATE_OF_DAY_MAP.put("10:00:00", 4);
        DATE_OF_DAY_MAP.put("10:30:00", 5);
        DATE_OF_DAY_MAP.put("11:00:00", 6);
        DATE_OF_DAY_MAP.put("11:30:00", 7);
        DATE_OF_DAY_MAP.put("12:00:00", 8);
        DATE_OF_DAY_MAP.put("12:30:00", 9);
        DATE_OF_DAY_MAP.put("13:00:00", 10);
        DATE_OF_DAY_MAP.put("13:30:00", 11);
        DATE_OF_DAY_MAP.put("14:00:00", 12);
        DATE_OF_DAY_MAP.put("14:30:00", 13);
        DATE_OF_DAY_MAP.put("15:00:00", 14);
        DATE_OF_DAY_MAP.put("15:30:00", 15);
        DATE_OF_DAY_MAP.put("16:00:00", 16);
        DATE_OF_DAY_MAP.put("16:30:00", 17);
        DATE_OF_DAY_MAP.put("17:00:00", 18);
        DATE_OF_DAY_MAP.put("17:30:00", 19);
        DATE_OF_DAY_MAP.put("18:00:00", 20);
        DATE_OF_DAY_MAP.put("18:30:00", 21);
        DATE_OF_DAY_MAP.put("19:00:00", 22);
        DATE_OF_DAY_MAP.put("19:30:00", 23);
        DATE_OF_DAY_MAP.put("20:00:00", 24);
        DATE_OF_DAY_MAP.put("20:30:00", 25);
        DATE_OF_DAY_MAP.put("21:00:00", 26);
        DATE_OF_DAY_MAP.put("21:30:00", 27);
    }

    /**
     * Convert a time of day string to an integer.
     *
     * @param dateOfDay The time of day string (HH:MM:SS Format)
     * @return Integer representing the time of day
     */
    public static int convertDateOfDayToInt(String dateOfDay) {
        if (DATE_OF_DAY_MAP.containsKey(dateOfDay)) {
            return DATE_OF_DAY_MAP.get(dateOfDay);
        } else {
            return -1;
        }
    }

    /**
     * Get the week of the year from a given date string.
     *
     * @param dateString The date string (dd/MM/yyyy Format)
     * @return The week of the year
     */
    public static int getWeekOfYear(String dateString) {
        if (!dateString.isEmpty()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate date = LocalDate.parse(dateString, formatter);
                WeekFields weekFields = WeekFields.of(Locale.getDefault());
                return date.get(weekFields.weekOfWeekBasedYear());
            } catch (DateTimeParseException e) {
                return -1;
            }
        }
        return -1;
    }
}
