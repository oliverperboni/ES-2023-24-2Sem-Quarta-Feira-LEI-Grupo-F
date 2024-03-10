import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.text.Normalizer;
import java.util.ArrayList;

public class TableFilters{

    public JFrame panel;

    public TableFilters(){
        panel = new JFrame();

        panel.setSize(900, 800);
        panel.setLayout(new BorderLayout());
        panel.setUndecorated(false);
        panel.getRootPane().setBorder(BorderFactory.createMatteBorder(20,20,20,20, panel.getBackground()));
        panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Paneis
        JPanel filterPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        //TextField
        textFieldsCreation(filterPanel);

        //Tabelas
        JTable tabela = Table();
        //Scroll Option
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBorder(BorderFactory.createMatteBorder(20,20,20,20, panel.getBackground()));

        //Botoes
        JButton filtrarbtn = new JButton("Filtrar");
        btnCreation(filtrarbtn, tabela, filterPanel);
        buttonPanel.add(filtrarbtn);

        panel.add(filterPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        panel.setVisible(true);
    }

    //Criação dos labels e textFields para os filtros
    private void textFieldsCreation(JPanel filterPanel) {
        filterPanel.setLayout(new GridLayout(0, 2, 5, 5)); // 0 indica número indefinido de linhas
        addLabelAndTextField(filterPanel, "Curso");
        addLabelAndTextField(filterPanel, "UC");
        addLabelAndTextField(filterPanel, "Turno");
        addLabelAndTextField(filterPanel, "Turma");
        addLabelAndTextField(filterPanel, "Inscritos no Turno");
        addLabelAndTextField(filterPanel, "Dia de Semana");
        addLabelAndTextField(filterPanel, "Hora de Inicio");
        addLabelAndTextField(filterPanel, "Hora de Fim");
        addLabelAndTextField(filterPanel, "Data da Aula");
        addLabelAndTextField(filterPanel, "Caracteristicas da Sala");
        addLabelAndTextField(filterPanel, "Sala atribuida à Sala");
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

    public void function_filtrarBtn(JTable tabela, JPanel filterPanel){
        int countFilterPreenchido = 0;
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
        tabela.setRowSorter(sorter);

        ArrayList<RowFilter<Object, Object>> filters = new ArrayList<>();

        for(int i = 0; i < filterPanel.getComponentCount(); i+=2){
            Component component = filterPanel.getComponent(i + 1);
            if(component instanceof JTextField textField){
                String filterText = textField.getText().toUpperCase();

                int columnIndex = i / 2;

                if(!filterText.isEmpty()){
                    countFilterPreenchido++;
                    filters.add(RowFilter.regexFilter("(?i)\\b" + filterText + "\\b", columnIndex));
                }
            }
        }

        if(countFilterPreenchido == 1){
            RowFilter<Object, Object> compoundRowFilter = RowFilter.orFilter(filters);
            sorter.setRowFilter(compoundRowFilter);
        }else {
            RowFilter<Object, Object> compoundRowFilter = RowFilter.andFilter(filters);
            sorter.setRowFilter(compoundRowFilter);
        }
    }

    //Função para criar a tabela passei o que o Oliver fez para aqui para ficar mais fácil
    public JTable Table(){
        // array com os dados do excel
        ArrayList<LineSchedule> data = ReadCSV.readScheduleCSV("csv/HorarioDeExemplo.csv");

        // Cria uma tabela
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Curso");
        model.addColumn("Unidade Curricular");
        model.addColumn("Turno");
        model.addColumn("Turma");
        model.addColumn("Inscritos no turno");
        model.addColumn("Dia da Semana");
        model.addColumn("Hora Início da Aula");
        model.addColumn("Hora Fim da Aula");
        model.addColumn("Data da Aula");
        model.addColumn("Características da Sala");
        model.addColumn("Sala atribuida à sala");

        //adiciona as linhas do CSV a tabela
        for (LineSchedule ls : data) {
            Object[] row = {normalizeValue(ls.getCurso()),normalizeValue(ls.getUnidadeCurricular()),normalizeValue(ls.getTurno()),normalizeValue(ls.getTurma()),ls.getInscritos(),
                    ls.getDiaSemana(),ls.getHoraInicio(),ls.getHoraFim(),ls.getDataAula(),ls.getCaracteristicasSala(),ls.getSala()};
            model.addRow(row);
        }

        //Retorna a tabela
        return new JTable(model);
    }

    //Funcao para normalizar e tirar os acentos e caracteres especiais da string
    private String normalizeValue(String value) {
        if (value != null) {
            value = Normalizer.normalize(value, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
            return value.toUpperCase();
        }
        return "";
    }

    public static void main(String[] args) {
        new TableFilters();
    }



}
