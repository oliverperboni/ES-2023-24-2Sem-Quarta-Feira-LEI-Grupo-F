import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class TableFiltersTest {

    @BeforeEach
    public void setUpHeadlessMode() {
        System.setProperty("java.awt.headless", "true");
    }

    @Test
    public void addFilter() {

        TableFilters  tf = new TableFilters();
         // Criação de objetos necessários
        JFrame panel = new JFrame();
        JTable tabela = new JTable();

        // Chama o método addFilter
        JFrame resultFrame = tf.addFilter(panel, tabela);

        // Verificações

        // Verifica se o JFrame retornado não é nulo
        assertNotNull(resultFrame);

        // Verifica se o tamanho do JFrame é 900x800
        assertEquals(900, resultFrame.getWidth());
        assertEquals(800, resultFrame.getHeight());

        // Verifica se o layout do JFrame é BorderLayout
        assertTrue(resultFrame.getLayout() instanceof BorderLayout);

        // Verifica se o JFrame não é decorado
        assertFalse(resultFrame.isUndecorated());

        // Verifica se a borda do JFrame está correta
        assertTrue(resultFrame.getRootPane().getBorder() instanceof MatteBorder);
        MatteBorder matteBorder = (MatteBorder) resultFrame.getRootPane().getBorder();
        assertEquals(20, matteBorder.getBorderInsets().top);
        assertEquals(20, matteBorder.getBorderInsets().left);
        assertEquals(20, matteBorder.getBorderInsets().bottom);
        assertEquals(20, matteBorder.getBorderInsets().right);

        // Verifica se os painéis foram adicionados corretamente
        assertTrue(resultFrame.getContentPane().getComponent(0) instanceof JPanel);
        assertTrue(resultFrame.getContentPane().getComponent(1) instanceof JPanel);

        // Verifica se o botão de filtragem foi adicionado corretamente
        JButton filtrarBtn = (JButton) ((JPanel) resultFrame.getContentPane().getComponent(1)).getComponent(0);
        assertEquals("Filtrar", filtrarBtn.getText());
    }
    
    

    @Test
    public void textFieldsCreation() {
            TableFilters  tf = new TableFilters();


           // Criando um JPanel para testar
           JPanel filterPanel = new JPanel();
        
           // Chama o método textFieldsCreation
           tf.textFieldsCreation(filterPanel);
   
           // Verificações
   
           // Verifica se o layout do painel é GridLayout
           assertTrue(filterPanel.getLayout() instanceof GridLayout);
   
           // Verifica se o número de componentes adicionados ao painel está correto
           assertEquals(26, filterPanel.getComponentCount());
   
           // Verifica se os componentes adicionados são JLabels seguidos de JTextFields
           for (int i = 0; i < filterPanel.getComponentCount(); i += 2) {
               assertTrue(filterPanel.getComponent(i) instanceof JLabel);
               assertTrue(filterPanel.getComponent(i + 1) instanceof JTextField);
           }
   
           // Verifica se os textos dos JLabels estão corretos
           String[] expectedLabels = {"Curso", "UC", "Turno", "Turma", "N.º de inscritos no turno",
                                      "Dia da Semana", "Hora de Início", "Hora de Fim", "Data da Aula",
                                      "Tipo de Sala", "Sala atribuida", "Semana do semestre", "Semana do ano"};
           for (int i = 0; i < expectedLabels.length; i++) {
               JLabel label = (JLabel) filterPanel.getComponent(i * 2);
               assertEquals(expectedLabels[i], label.getText());
           }
       }
    

    @Test
    public void function_filtrarBtn() {

        JTable tabela = new JTable();
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Nome");
        modelo.addColumn("Categoria");
        Object[] row0 = {"Item1", "A"};
        Object[] row1 = {"Item2", "B"};
        Object[] row2 = {"Item3", "C"};
        modelo.addRow(row0);
        modelo.addRow(row1);
        modelo.addRow(row2);
        tabela.setModel(modelo);
        JPanel filterPanel = new JPanel();
        
        TableFilters tableFilters = new TableFilters(); 

        // Adicionando filtros ao filterPanel para teste
        JTextField textField1 = new JTextField("Item1");
        filterPanel.add(new JLabel("Nome"));
        filterPanel.add(textField1);
   

        // Chamando a função a ser testada
        tableFilters.function_filtrarBtn(tabela, filterPanel);

        // Verificando se os filtros foram aplicados corretamente
        assertEquals(1, tabela.getRowCount()); // Espera-se apenas uma linha após a filtragem
        assertEquals("Item1", tabela.getValueAt(0, 0)); // Espera-se que o item1 seja exibido
        assertEquals("A", tabela.getValueAt(0, 1)); // Espera-se que a categoria A seja exibida
    }

    @Test
    public void testBtnCreation() {
        TableFilters tableFilters = new TableFilters();
        JButton button = new JButton();

        // Chama o método
        tableFilters.btnCreation(button, new JTable(), new JPanel());

        // Verifica se o botão tem os atributos definidos
        assertEquals(Color.WHITE, button.getForeground());
        assertEquals(new Color(46, 139, 87), button.getBackground());
        assertFalse(button.isFocusPainted());
        assertEquals(new Font("Arial", Font.BOLD, 14), button.getFont());
    }

    @Test
    public void testFunctionEsconderBtn() {
        TableFilters tableFilters = new TableFilters();
        JTable table = new JTable(new DefaultTableModel(2, 2));
        table.changeSelection(1, 1, false, false);
        TableColumn column = table.getColumnModel().getColumn(1);

        // Chama o método
        tableFilters.function_esconderBtn(table);

        // Verifica se a coluna foi removida da tabela
        assertEquals(1, table.getColumnCount());
        assertNotEquals(column, table.getColumnModel().getColumn(0));
    }

    @Test
    public void testFunctionRevelarBtn() {
        TableFilters tableFilters = new TableFilters();
        JTable table = new JTable(new DefaultTableModel(2, 2));
        table.changeSelection(1, 1, false, false);
        TableColumn column = table.getColumnModel().getColumn(1);

        // Chama o método function_esconderBtn(), de forma a ocultar uma coluna
        tableFilters.function_esconderBtn(table);

        // Chama o método a ser testado
        tableFilters.function_revelarBtn(table);

        // Verifica se a coluna volta a ser visível novamente
        assertEquals(2, table.getColumnCount());
        table.changeSelection(1, 1, false, false);
        column = table.getColumnModel().getColumn(1);
        assertEquals(column, table.getColumnModel().getColumn(1));
    }
}