package core;

import structures.LineSchedule;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ConflitosGUI extends JFrame {
    private JTextField salaField;
    private JTextField horaInicioField;
    private JTextField horaFimField;
    private JTextField dataField;
    private List<LineSchedule> lineSchedules;


    public ConflitosGUI(List<LineSchedule> list) {
        this.lineSchedules = list;
        setTitle("Ver Conflitos");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        JLabel salaLabel = new JLabel("Sala:");
        salaField = new JTextField();
        JLabel horaInicioLabel = new JLabel("Hora Início:");
        horaInicioField = new JTextField();
        JLabel horaFimLabel = new JLabel("Hora Fim:");
        horaFimField = new JTextField();
        JLabel dataLabel = new JLabel("Data:");
        dataField = new JTextField();

        JButton verConflitosButton = new JButton("Ver Conflitos");
        verConflitosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sala = salaField.getText();
                String horaInicio = horaInicioField.getText();
                String horaFim = horaFimField.getText();
                String data = dataField.getText();

                if (sala.isEmpty() || horaInicio.isEmpty() || horaFim.isEmpty() || data.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos.");
                    return;
                }

                // Criar o objeto GrafoGui com os parâmetros fornecidos
                GrafoGUI grafo = new GrafoGUI(list,sala, horaInicio, horaFim, data);
                grafo.setVisible(true);
                
            }
        });

        panel.add(salaLabel);
        panel.add(salaField);
        panel.add(horaInicioLabel);
        panel.add(horaInicioField);
        panel.add(horaFimLabel);
        panel.add(horaFimField);
        panel.add(dataLabel);
        panel.add(dataField);
        panel.add(verConflitosButton);

        add(panel);
        setVisible(true);
    }

   
}

