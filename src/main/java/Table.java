import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Table extends JFrame {

    public Table(){

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
            Object[] row = {ls.getCurso(),ls.getUnidadeCurricular(),ls.getTurno(),ls.getTurma(),ls.getInscritos(),
            ls.getDiaSemana(),ls.getHoraInicio(),ls.getHoraFim(),ls.getDataAula(),ls.getCaracteristicasSala(),ls.getSala()};
            model.addRow(row);
        }

        //cria tabela
        JTable table = new JTable(model);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        

        //cria a opção de scroll se necessario 
        JScrollPane scrollPane = new JScrollPane(table);
       
        getContentPane().add(scrollPane);
        setVisible(true);

    }

    public static void main(String[] args) {
        new Table();
    }
    
}
