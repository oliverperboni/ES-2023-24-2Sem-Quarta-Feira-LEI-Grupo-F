package filters;

import visualize.RoomOccupancyMap;
import structures.LineSchedule;
import structures.ScheduleDataModel;
import visualize.ConflitosGUI;
import visualize.Table;
import visualize.TableSubstitution;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 * The TableFilters class provides functionality to add filters to a JTable and perform various operations like hiding columns, revealing hidden columns, and saving changes.
 */

public class TableFilters {

    /** List to store column indexes of hidden columns */
    List<Integer> a = new ArrayList<>();

    public JFrame panel;

    private Table table;

    public TableFilters(Table table) {
        this.table = table;
    }

    /**
     * Adds filter components to the JFrame panel.
     *
     * @param panel  The JFrame panel.
     * @param tabela The JTable to which filters will be added.
     * @return The updated JFrame panel with added filters.
     */
    public JFrame addFilter(JFrame panel, JTable tabela, ScheduleDataModel dataModel) {
        panel = new JFrame();

        panel.setSize(900, 800);
        panel.setLayout(new BorderLayout());
        panel.setUndecorated(false);
        panel.getRootPane().setBorder(BorderFactory.createMatteBorder(20, 20, 20, 20, panel.getBackground()));
        panel.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Paneis
        JPanel filterPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        //TextField
        textFieldsCreation(filterPanel);

        //Botoes
        JButton salasbtn = new JButton("Salas");
        JButton conflitosbtn = new JButton("Conflitos");
        JButton heatmapbtn = new JButton("Mapa Ocupação");
        JButton filtrarbtn = new JButton("Filtrar");
        JButton esconderbtn = new JButton("Esconder Coluna");
        JButton revelarbtn = new JButton("Revelar Colunas");
        JButton substituitionButton = new JButton("Marcar Substituição");
        JButton aulaButton = new JButton("Marcar Nova UC");
        JButton saveButton = new JButton("Guardar");

        saveButton.addActionListener(e -> table.saveChanges());

        btnCreation(filtrarbtn, tabela, filterPanel);
        btnCreation(esconderbtn, tabela, filterPanel);
        btnCreation(revelarbtn, tabela, filterPanel);

        buttonPanel.add(salasbtn);
        buttonPanel.add(conflitosbtn);
        buttonPanel.add(heatmapbtn);
        buttonPanel.add(filtrarbtn);
        buttonPanel.add(esconderbtn);
        buttonPanel.add(revelarbtn);
        buttonPanel.add(substituitionButton);
        buttonPanel.add(aulaButton);
        buttonPanel.add(saveButton);

        //Ações dos Botões
        conflitosbtn.addActionListener(e -> function_conflitos(dataModel.getScheduleEntries()));
        filtrarbtn.addActionListener(e -> function_filtrarBtn(tabela, filterPanel));
        esconderbtn.addActionListener(e -> function_esconderBtn(tabela));
        revelarbtn.addActionListener(e -> function_revelarBtn(tabela));
        substituitionButton.addActionListener(e -> function_MarcarSubs(tabela));
        aulaButton.addActionListener(e -> function_MarcarAulas(tabela));
        salasbtn.addActionListener(e -> function_listaSalas());
        heatmapbtn.addActionListener(e -> function_heatMap());

        panel.add(filterPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }


    private void function_conflitos(List<LineSchedule> list) {

        ConflitosGUI gui = new ConflitosGUI(list);
     
    }



    /**
     * Creates labels and text fields for filters and adds them to the provided panel.
     *
     * @param filterPanel The panel to which labels and text fields will be added.
     */
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

    /**
     * Adds a label and a text field to the provided panel.
     *
     * @param panel The panel to which the label and text field will be added.
     * @param label The text for the label.
     */
    private void addLabelAndTextField(JPanel panel, String label) {
        JLabel jLabel = new JLabel(label);
        JTextField jTextField = new JTextField("");
        panel.add(jLabel);
        panel.add(jTextField);
    }

    /**
     * Creates a button with specified style settings.
     *
     * @param btn         The button to be styled.
     * @param tabela      The JTable associated with the button.
     * @param filterPanel The panel containing the filters.
     */
    public void btnCreation(JButton btn, JTable tabela, JPanel filterPanel) {

        //Estilo do Botão
//        btn.setForeground(Color.WHITE);
//        btn.setBackground(new Color(46, 139, 87)); // Cor verde
//        btn.setFocusPainted(false); // Remove a borda de foco
//        btn.setFont(new Font("Arial", Font.BOLD, 14));

    }

    /**
     * Opens a dialog for marking substitution classes based on the selected row in the table.
     *
     * @param tabela The table containing the schedule data.
     */
    public void function_MarcarSubs(JTable tabela) {
                int selectedRow = tabela.getSelectedRow();
                // Faça algo com a linha selecionada, por exemplo, imprimir os dados da linha
                if (selectedRow != -1) {
                    // Verifica se uma linha está selecionada
                    new TableSubstitution(selectedRow,table,false);
                }

    }
    /**
     * Opens a dialog for marking regular classes based on the selected row in the table.
     *
     * @param tabela The table containing the schedule data.
     */
    public void function_MarcarAulas(JTable tabela) {

        int selectedRow = tabela.getSelectedRow();
        // Faça algo com a linha selecionada, por exemplo, imprimir os dados da linha
        if (selectedRow != -1) {
            // Verifica se uma linha está selecionada
            new TableSubstitution(selectedRow,table,true);
        }

    }

    /**
     * Hides the selected column from the JTable.
     *
     * @param tabela The JTable from which the column will be hidden.
     */
    public void function_esconderBtn(JTable tabela) {
        int columnIndex = tabela.getSelectedColumn();
        TableColumn d = tabela.getColumnModel().getColumn(columnIndex);
        a.add(d.getModelIndex());
        tabela.removeColumn(d);
    }

    /**
     * Reveals previously hidden columns in the JTable.
     *
     * @param tabela The JTable in which hidden columns will be revealed.
     */
    public void function_revelarBtn(JTable tabela) {
            for(int x :a){
                TableColumn c = new TableColumn(x);
                tabela.addColumn(c);
                tabela.moveColumn(tabela.getColumnCount()-1,x);
            }
        a.clear();
    }

    /**
     * Creates an instance of the RoomFilterFrame class and displays its window.
     */
    public void function_listaSalas() {
        RoomFilterFrame filterFrame = new RoomFilterFrame(table.getDataModel().getRoomEntries(),
                table.getDataModel().getRoomColumnHeaders());
        filterFrame.show();
    }

    /**
     * Creates an instance of the RoomOccupancyMap class and displays its window.
     */
    public void function_heatMap() {
        RoomOccupancyMap.main(new String[0]);
    }

    /**
     * Filters the JTable based on the input provided in the filter panel.
     *
     * @param tabela       The JTable to be filtered.
     * @param filterPanel  The panel containing the filter components.
     */
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

    public Table getTable() {
        return table;
    }

}
