import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;

public class TableFilters {

    public JFrame panel;

    public JFrame addFilter(JFrame panel, JTable tabela) {
        panel = new JFrame();

        panel.setSize(900, 800);
        panel.setLayout(new BorderLayout());
        panel.setUndecorated(false);
        panel.getRootPane().setBorder(BorderFactory.createMatteBorder(20, 20, 20, 20, panel.getBackground()));
        panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Paneis
        JPanel filterPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        //TextField
        textFieldsCreation(filterPanel);

        //Botoes
        JButton filtrarbtn = new JButton("Filtrar");
        btnCreation(filtrarbtn, tabela, filterPanel);
        buttonPanel.add(filtrarbtn);

        panel.add(filterPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }


    //Criação dos labels e textFields para os filtros
    public void textFieldsCreation(JPanel filterPanel) {
        filterPanel.setLayout(new GridLayout(0, 2, 5, 5)); // 0 indica número indefinido de linhas
        addLabelAndTextField(filterPanel, "Curso");
        addLabelAndTextField(filterPanel, "UC");
        addLabelAndTextField(filterPanel, "Turno");
        addLabelAndTextField(filterPanel, "Turma");
        addLabelAndTextField(filterPanel, "N.º de inscritos no turno");
        addLabelAndTextField(filterPanel, "Dia da Semana");
        addLabelAndTextField(filterPanel, "Hora de Início");
        addLabelAndTextField(filterPanel, "Hora de Fim");
        addLabelAndTextField(filterPanel, "Data da Aula");
        addLabelAndTextField(filterPanel, "Tipo de Sala");
        addLabelAndTextField(filterPanel, "Sala atribuida");
        addLabelAndTextField(filterPanel, "Semana do semestre");
        addLabelAndTextField(filterPanel, "Semana do ano");
    }

    //Adicionar á tabela a Label e o Textfield
    private void addLabelAndTextField(JPanel panel, String label) {
        JLabel jLabel = new JLabel(label);
        JTextField jTextField = new JTextField("");
        panel.add(jLabel);
        panel.add(jTextField);
    }

    //Criação do Botão Filtrar
    private void btnCreation(JButton filtrarbtn, JTable tabela, JPanel filterPanel) {

        //Estilo do Botão
        filtrarbtn.setForeground(Color.WHITE);
        filtrarbtn.setBackground(new Color(46, 139, 87)); // Cor verde
        filtrarbtn.setFocusPainted(false); // Remove a borda de foco
        filtrarbtn.setFont(new Font("Arial", Font.BOLD, 14));
        //Acao do Button
        filtrarbtn.addActionListener(e -> function_filtrarBtn(tabela, filterPanel));

    }

    public void function_filtrarBtn(JTable tabela, JPanel filterPanel) {
        int countFilterPreenchido = 0;
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
        tabela.setRowSorter(sorter);

        ArrayList<RowFilter<Object, Object>> filters = new ArrayList<>();

        for (int i = 0; i < filterPanel.getComponentCount(); i += 2) {
            Component component = filterPanel.getComponent(i + 1);
            if (component instanceof JTextField textField) {
                String filterText = textField.getText().toUpperCase();

                int columnIndex = i / 2;

                if (!filterText.isEmpty()) {
                    countFilterPreenchido++;
                    filters.add(RowFilter.regexFilter("(?i)\\b" + filterText + "\\b", columnIndex));
                }
            }
        }

        if (countFilterPreenchido == 1) {
            RowFilter<Object, Object> compoundRowFilter = RowFilter.orFilter(filters);
            sorter.setRowFilter(compoundRowFilter);
        } else {
            RowFilter<Object, Object> compoundRowFilter = RowFilter.andFilter(filters);
            sorter.setRowFilter(compoundRowFilter);
        }
    }

    public static void main(String[] args) {
        new TableFilters();
    }

}
