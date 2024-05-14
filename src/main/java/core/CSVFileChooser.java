package core;

import com.formdev.flatlaf.FlatLightLaf;
import structures.ScheduleDataModel;
import visualize.Table;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 * The core.CSVFileChooser class represents the initial window of the JFrame where we indicate whether we want to load from local files or from Git.
 */
public class CSVFileChooser {

    private JFrame fileChooserFrame;
    private JTextField roomsFileField;
    private JTextField scheduleFileField;
    private String scheduleFilePath;
    private String roomsFilePath;

    /**
     * Constructs a new core.CSVFileChooser object and initializes the GUI components.
     */
    public CSVFileChooser() {
        fileChooserFrame = new JFrame("GestHor - carregar ficheiros");
        fileChooserFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JLabel scheduleFileLabel = new JLabel("Ficheiro horários (CSV)");
        scheduleFileField = new JTextField("csv/HorarioDeExemplo.csv", 30);
        JCheckBox scheduleGitCheckbox = new JCheckBox("Carregar do Git");

        JLabel roomsFileLabel = new JLabel("Ficheiro salas (CSV)");
        roomsFileField = new JTextField("csv/CaracterizaçãoDasSalas.csv", 30);
        JCheckBox roomsGitCheckbox = new JCheckBox("Carregar do Git");

        JButton button1 = new JButton("Iniciar");

        button1.addActionListener(e -> {
            scheduleFilePath = scheduleFileField.getText();
            roomsFilePath = roomsFileField.getText();
            openTableWindow(scheduleFilePath, scheduleGitCheckbox.isSelected(),
                    roomsFilePath, roomsGitCheckbox.isSelected());
        });

        panel.add(scheduleFileLabel);
        panel.add(scheduleFileField);
        panel.add(scheduleGitCheckbox);
        panel.add(roomsFileLabel);
        panel.add(roomsFileField);
        panel.add(roomsGitCheckbox);
        panel.add(button1);

        fileChooserFrame.getContentPane().add(BorderLayout.CENTER, panel);
        fileChooserFrame.setSize(400, 200);
        fileChooserFrame.setVisible(true);
    }

    /**
     * Opens the table window with the provided file paths and loading options.
     *
     * @param scheduleFile   The path to the schedule file.
     * @param scheduleRemote Whether to load the schedule file from remote (Git) or not.
     * @param roomsFile      The path to the rooms file.
     * @param roomsRemote    Whether to load the rooms file from remote (Git) or not.
     */
    public void openTableWindow(String scheduleFile, boolean scheduleRemote, String roomsFile, boolean roomsRemote) {
        try {
            ScheduleDataModel dataModel = new ScheduleDataModel(scheduleFile, scheduleRemote, roomsFile, roomsRemote);
            new Table(dataModel);
        } catch (IOException e) {
            // Skip
        }
        fileChooserFrame.dispose(); // Fecha a janela logo depois de abrir a tabela
    }

    /**
     * The main method to start the application.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        FlatLightLaf.setup();
        new CSVFileChooser();
    }

    /**
     * Returns the file path of the schedule file.
     *
     * @return The file path of the schedule file.
     */
    public String getScheduleFilePath() {return scheduleFilePath; }

    /**
     * Returns the file path of the rooms file.
     *
     * @return The file path of the rooms file.
     */
    public String getRoomsFilePath() {
        return roomsFilePath;
    }

}