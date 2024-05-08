package core;

import org.knowm.xchart.HeatMapChart;
import org.knowm.xchart.HeatMapChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.Styler;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class RoomOccupancyMap {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Heatmap");

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
            for(int y = 0; y < yData.length; y++) {
                heatData[i][y] = countOccurrences(data, i, y);
            }
        }

        // Cria o gráfico de mapa de calor
        chart.addSeries("Basic HeatMap", xData, yData, heatData);

        // Adiciona o gráfico a um painel
        JPanel chartPanel = new XChartPanel<>(chart);
        frame.getContentPane().add(chartPanel);

        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private static List<String[]> readCSV(String filename) {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            // Ignorar a primeira linha (cabeçalhos das colunas)
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

    private static int convertDayOfWeekToInt(String dayOfWeek) {
        switch (dayOfWeek) {
            case "Dom":
                return 0;
            case "Seg":
                return 1;
            case "Ter":
                return 2;
            case "Qua":
                return 3;
            case "Qui":
                return 4;
            case "Sex":
                return 5;
            case "Sáb":
                return 6;
            default:
                throw new IllegalArgumentException("Dia da semana inválido: " + dayOfWeek);
        }
    }

    private static int countOccurrences(List<String[]> data, int dayOfWeek, int startTime) {
        int count = 0;
        for (String[] row : data) {
            int rowDayOfWeek = convertDayOfWeekToInt(row[5]);
            int rowStartTime =  convertDateOfDayToInt(row[6]);
            if (rowDayOfWeek == dayOfWeek && rowStartTime == startTime) {
                count++;
            }
        }
        return count;
    }

    private static int convertDateOfDayToInt(String dateOfDay) {
        switch (dateOfDay) {
            case "08:00:00":
                return 0;
            case "08:30:00":
                return 1;
            case "09:00:00":
                return 2;
            case "09:30:00":
                return 3;
            case "10:00:00":
                return 4;
            case "10:30:00":
                return 5;
            case "11:00:00":
                return 6;
            case "11:30:00":
                return 7;
            case "12:00:00":
                return 8;
            case "12:30:00":
                return 9;
            case "13:00:00":
                return 10;
            case "13:30:00":
                return 11;
            case "14:00:00":
                return 12;
            case "14:30:00":
                return 13;
            case "15:00:00":
                return 14;
            case "15:30:00":
                return 15;
            case "16:00:00":
                return 16;
            case "16:30:00":
                return 17;
            case "17:00:00":
                return 18;
            case "17:30:00":
                return 19;
            case "18:00:00":
                return 20;
            case "18:30:00":
                return 21;
            case "19:00:00":
                return 22;
            case "19:30:00":
                return 23;
            case "20:00:00":
                return 24;
            case "20:30:00":
                return 25;
            case "21:00:00":
                return 26;
            case "21:30:00":
                return 27;
            default:
                throw new IllegalArgumentException("Dia da semana inválido: " + dateOfDay);
        }
    }
}
