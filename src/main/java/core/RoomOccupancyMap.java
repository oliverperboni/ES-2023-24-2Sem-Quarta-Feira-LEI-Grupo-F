import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class RoomOccupancyMap extends JFrame {
    private JLabel startDateLabel;
    private JLabel endDateLabel;
    private JLabel minOccupancyLabel;
    private JTextField startDateField;
    private JTextField endDateField;
    private JTextField minOccupancyField;
    private JButton filterButton;
    private DefaultCategoryDataset dataset;
    private JFreeChart chart;
    private ChartPanel chartPanel;

    public RoomOccupancyMap(Map<String, Integer> roomOccupancyData) {
        setTitle("Room Occupancy Chart");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Initialize components
        startDateLabel = new JLabel("Start Date:");
        startDateField = new JTextField(10);
        endDateLabel = new JLabel("End Date:");
        endDateField = new JTextField(10);
        minOccupancyLabel = new JLabel("Min Occupancy:");
        minOccupancyField = new JTextField(5);
        filterButton = new JButton("Filter");
        filterButton.addActionListener(e -> filterData());

        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.add(startDateLabel);
        controlPanel.add(startDateField);
        controlPanel.add(endDateLabel);
        controlPanel.add(endDateField);
        controlPanel.add(minOccupancyLabel);
        controlPanel.add(minOccupancyField);
        controlPanel.add(filterButton);

        getContentPane().add(controlPanel, BorderLayout.NORTH);

        // Create dataset and chart
        dataset = createDataset(roomOccupancyData);
        chart = ChartFactory.createBarChart("Room Occupancy", "Date", "Occupied Rooms", dataset);
        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 500));
        getContentPane().add(chartPanel, BorderLayout.CENTER);
    }

    private DefaultCategoryDataset createDataset(Map<String, Integer> roomOccupancyData) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Ordenar o mapa por chaves (datas) em ordem crescente
        TreeMap<LocalDate, Integer> sortedData = new TreeMap<>();
        roomOccupancyData.forEach((key, value) -> {
            if (!key.isEmpty()) {
                LocalDate date = LocalDate.parse(key, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                sortedData.put(date, value);
            }
        });

        // Adicionar os valores ordenados ao conjunto de dados
        sortedData.forEach((key, value) -> {
            dataset.addValue(value, "Occupied Rooms", key.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        });

        return dataset;
    }

    private static Map<String, Integer> readCSVAndGetOccupancy(String csvFile) {
        String line;
        String cvsSplitBy = ";";
        Map<String, Integer> roomOccupancyData = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Skip header line
            br.readLine();
            while ((line = br.readLine()) != null) {
                // Split the line into fields using the separator ";"
                String[] data = line.split(cvsSplitBy);
                // Extract the date of the class (index 8 in the data array)
                String date = data[8];
                // Increment the room occupancy count for the corresponding date
                roomOccupancyData.put(date, roomOccupancyData.getOrDefault(date, 0) + 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return roomOccupancyData;
    }

    private void filterData() {
        String startDateStr = startDateField.getText();
        String endDateStr = endDateField.getText();
        String minOccupancyStr = minOccupancyField.getText();

        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate startDate = LocalDate.parse(startDateStr, dateFormatter);
            LocalDate endDate = LocalDate.parse(endDateStr, dateFormatter);
            Integer minOccupancy = Integer.parseInt(minOccupancyStr);

            Map<String, Integer> filteredData = readCSVAndGetOccupancy("C:\\Users\\danie\\IdeaProjects\\ES-2023-24-2Sem-Quarta-Feira-LEI-Grupo-F\\csv\\HorarioDeExemplo.csv", startDate, endDate, minOccupancy);
            updateChart(filteredData);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please use dd/MM/yyyy.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid occupancy value. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Map<String, Integer> readCSVAndGetOccupancy(String csvFile, LocalDate startDate, LocalDate endDate, Integer minOccupancy) {
        String line;
        String cvsSplitBy = ";";
        Map<String, Integer> roomOccupancyData = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Skip header line
            br.readLine();
            while ((line = br.readLine()) != null) {
                // Split the line into fields using the separator ";"
                String[] data = line.split(cvsSplitBy);
                // Extract the date of the class (index 8 in the data array)
                String dateStr = data[8];
                LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                if (date.isAfter(startDate.minusDays(1)) && date.isBefore(endDate.plusDays(1))) {
                    int occupancy = Integer.parseInt(data[9]); // Assuming occupancy is in the 9th column
                    if (occupancy >= minOccupancy) {
                        roomOccupancyData.put(dateStr, roomOccupancyData.getOrDefault(dateStr, 0) + 1);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return roomOccupancyData;
    }

    private void updateChart(Map<String, Integer> roomOccupancyData) {
        dataset.clear();
        for (Map.Entry<String, Integer> entry : roomOccupancyData.entrySet()) {
            dataset.addValue(entry.getValue(), "Occupied Rooms", entry.getKey());
        }
    }

    public static void displayChart(String csvFile) {
        SwingUtilities.invokeLater(() -> {
            Map<String, Integer> roomOccupancyData = readCSVAndGetOccupancy(csvFile);
            RoomOccupancyMap chart = new RoomOccupancyMap(roomOccupancyData);
            chart.setVisible(true);
        });
    }

    public static void main(String[] args) {
        // Specify the path to your CSV file
        String csvFile = "C:\\Users\\danie\\IdeaProjects\\ES-2023-24-2Sem-Quarta-Feira-LEI-Grupo-F\\csv\\HorarioDeExemplo.csv";

        // Display the chart
        RoomOccupancyMap.displayChart(csvFile);
    }
}
