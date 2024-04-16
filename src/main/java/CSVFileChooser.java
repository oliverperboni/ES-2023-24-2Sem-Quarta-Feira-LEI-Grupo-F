import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

//Esta classe é referente à janela inicial do JFrame onde indicamos se pretendemos carregar dos ficheiros locais ou do git
public class CSVFileChooser {

    private JFrame fileChooserFrame;
    private JTextField roomsFileField;
    private JTextField scheduleFileField;
    private String scheduleFilePath;
    private String roomsFilePath;

    public CSVFileChooser() {
        fileChooserFrame = new JFrame("Escolher ficheiros CSV");
        fileChooserFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JLabel scheduleFileLabel = new JLabel("Ficheiro horários (CSV)");
        scheduleFileField = new JTextField(30);
        JCheckBox scheduleGitCheckbox = new JCheckBox("Carregar do Git");

        JLabel roomsFileLabel = new JLabel("Ficheiro salas (CSV)");
        roomsFileField = new JTextField(30);
        JCheckBox roomsGitCheckbox = new JCheckBox("Carregar do Git");

        JButton button1 = new JButton("Iniciar");

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scheduleFilePath = scheduleFileField.getText();
                roomsFilePath = roomsFileField.getText();
                openTableWindow(scheduleFilePath, scheduleGitCheckbox.isSelected(),
                        roomsFilePath, roomsGitCheckbox.isSelected());
            }
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

    public void openTableWindow(String scheduleFile, boolean scheduleRemote, String roomsFile, boolean roomsRemote) {
        try {
            ScheduleDataModel dataModel = new ScheduleDataModel(scheduleFile, scheduleRemote, roomsFile, roomsRemote);
            new Table(dataModel);
        } catch (IOException e) {}
        fileChooserFrame.dispose(); // Fecha a janela logo depois de abrir a tabela
    }

    public static void main(String[] args) {
        new CSVFileChooser();
    }

    public String getScheduleFilePath() {
        return scheduleFilePath;
    }

    public String getRoomsFilePath() {
        return roomsFilePath;
    }

}