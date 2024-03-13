import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

//Esta classe é referente à janela inicial do JFrame onde indicamos se pretendemos carregar dos ficheiros locais ou do git
public class CSVFileChooser {
    private JFrame fileChooserFrame;
    private JTextField filePathTextField;

    public CSVFileChooser() {
        fileChooserFrame = new JFrame("Escolher Arquivo CSV");
        fileChooserFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JLabel filePathLabel = new JLabel("Caminho do arquivo CSV:");
        filePathTextField = new JTextField(30);
        JButton addButton = new JButton("Adicionar");
        JCheckBox gitCheckBox = new JCheckBox("Carregar do Git"); //Checkbox para escolher entre Git e local

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openTableWindow(filePathTextField.getText(), gitCheckBox.isSelected());
            }
        });

        panel.add(filePathLabel);
        panel.add(filePathTextField);
        panel.add(gitCheckBox);
        panel.add(addButton);

        fileChooserFrame.getContentPane().add(BorderLayout.CENTER, panel);
        fileChooserFrame.setSize(400, 200);
        fileChooserFrame.setVisible(true);
    }

    private void openTableWindow(String filePath, boolean isGit) {
        try {
            new Table(filePath, isGit);
        }catch (IOException e) {}
        //Fecha a janela após abrir a próxima janela (a tabela)
        fileChooserFrame.dispose();
    }
    public static void main(String[] args) {


                new CSVFileChooser();


    }
}