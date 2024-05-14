package core;

import structures.LineSchedule;
import structures.RoomPreference;
import structures.SchedulePeriod;
import visualize.TableSubstitution;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.util.*;
import java.util.List;

import static java.lang.System.out;

public class ScheduleTableEngine {

    private final TableSubstitution scheduleData;
    private final ScheduleEngine engine;
    private final int rowSelected;
    private String aulaSelecionada;
    private Boolean modify;

    public ScheduleTableEngine(TableSubstitution scheduleData, int rowSelected, JTable table){//, boolean modify){
        this.scheduleData = scheduleData;
        this.engine = new ScheduleEngine(scheduleData.getDataModel());
        this.rowSelected = table.convertRowIndexToModel(rowSelected);
        this.aulaSelecionada = "";
        this.modify=modify;

        initialize();
    }

    public void initialize() {

        List<LineSchedule> possibilityList = setupInform();

        JFrame frame = new JFrame();
        frame.setSize(900, 800);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JPanel sugestionPanel = new JPanel();
        sugestionPanel.setLayout(new BorderLayout());

        JPanel sugestionallocation = new JPanel();

        // Ajuste o layout para GridLayout com 5 linhas e 1 coluna
        sugestionallocation.setLayout(new GridLayout(0, 1, 0, 10)); // 10 pixels de espaço vertical entre os componentes

        // Adicione margens ao JPanel
        sugestionallocation.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margens de 10 pixels em todas as direções

        ButtonGroup teste = new ButtonGroup();

        int initialElementsToShow = 5; // Número inicial de elementos a serem mostrados
        int totalElements = possibilityList.size(); // Número total de elementos

        // Adicione os elementos iniciais ao sugestionallocation
        for (int i = 0; i < Math.min(initialElementsToShow, totalElements); i++) {
            JRadioButton radioButton = new JRadioButton(possibilityList.get(i).toString());
            teste.add(radioButton);
            sugestionallocation.add(radioButton);
            if(radioButton.isSelected()){
                aulaSelecionada = radioButton.getText();
                out.println("Aula de Substituição Selecionada: " + aulaSelecionada);
            }
        }

        JScrollPane scrollPane = new JScrollPane(sugestionallocation);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Adicione um ouvinte de rolagem ao JScrollPane
        scrollPane.getVerticalScrollBar().addAdjustmentListener(e -> {
            JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
            int value = verticalBar.getValue();
            int extent = verticalBar.getVisibleAmount();
            int maximum = verticalBar.getMaximum();

            // Se o usuário estiver no final do JScrollPane
            if (value + extent == maximum) {
                int currentCount = sugestionallocation.getComponentCount();
                int remainingElements = totalElements - currentCount;

                // Adicione mais elementos ao sugestionallocation conforme necessário
                for (int i = currentCount; i < Math.min(currentCount + 5, totalElements); i++) {
                    JRadioButton radioButton = new JRadioButton(possibilityList.get(i).toString());
                    teste.add(radioButton);
                    sugestionallocation.add(radioButton);
                }

                // Atualize o layout e a exibição
                sugestionallocation.revalidate();
                sugestionallocation.repaint();
            }
        });

        JPanel buttonAllocation = new JPanel();
        buttonAllocation.setLayout(new FlowLayout());

        JButton cancelButton = new JButton("Cancelar");
        JButton submitButton = new JButton("Submeter");

        cancelButton.addActionListener(e -> frame.dispose());
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(teste.getSelection().isSelected()){
                    JRadioButton selectedButton = getSelectedRadioButton(teste);
                    assert selectedButton != null;
                    aulaSelecionada = selectedButton.getText();
                    out.println("Aula de Substituição Selecionada: " + aulaSelecionada);

                    String[] arrays = aulaSelecionada.split("-");
                    LineSchedule newLineSchedule = new LineSchedule(arrays[0], arrays[1], arrays[2], arrays[3], Integer.parseInt(arrays[4]), arrays[5], arrays[6], arrays[7], arrays[8], arrays[9], arrays[10]);


                    out.println("Aula Selecionada Removida: " + scheduleData.getDataModel().getScheduleEntries().get(rowSelected));

                    scheduleData.getJTable().getModel().setValueAt(newLineSchedule.getCurso(), rowSelected, 0);
                    scheduleData.getJTable().getModel().setValueAt(newLineSchedule.getUnidadeCurricular(), rowSelected, 1);
                    scheduleData.getJTable().getModel().setValueAt(newLineSchedule.getTurno(), rowSelected, 2);
                    scheduleData.getJTable().getModel().setValueAt(newLineSchedule.getTurma(), rowSelected, 3);
                    scheduleData.getJTable().getModel().setValueAt(newLineSchedule.getInscritos(), rowSelected, 4);
                    scheduleData.getJTable().getModel().setValueAt(newLineSchedule.getDiaSemana(), rowSelected, 5);
                    scheduleData.getJTable().getModel().setValueAt(newLineSchedule.getHoraInicio(), rowSelected, 6);
                    scheduleData.getJTable().getModel().setValueAt(newLineSchedule.getHoraFim(), rowSelected, 7);
                    scheduleData.getJTable().getModel().setValueAt(newLineSchedule.getDataAula(), rowSelected, 8);
                    scheduleData.getJTable().getModel().setValueAt(newLineSchedule.getCaracteristicasSala(), rowSelected, 9);
                    scheduleData.getJTable().getModel().setValueAt(newLineSchedule.getSala(), rowSelected, 10);

                    out.println("Aula de Substituição Adicionada e Original Removida da JTable");

                    frame.dispose(); // Fecha o frame atual após a submissão

                    out.println("Salvar as mudanças");
                    scheduleData.getTable().saveChanges();
                }
            }
        });

        buttonAllocation.add(cancelButton);
        buttonAllocation.add(submitButton);

        sugestionPanel.add(scrollPane, BorderLayout.NORTH);
        sugestionPanel.add(buttonAllocation, BorderLayout.SOUTH);
        frame.add(sugestionPanel);
        frame.setVisible(true);
    }

    private List<LineSchedule> setupInform(){

        String courseName= scheduleData.getCursoSelec().toString();
        String curricularUnit=scheduleData.getUcSelec().toString();
        String classTurn= scheduleData.getTurnoSelec().toString();
        String className= scheduleData.getTurmaSelec().toString();
        String studentCount = scheduleData.getInscritosSelec().toString();
        //out.println(classTurn);

        Date startDate = scheduleData.getDateI();
        int weekCount =Integer.parseInt(scheduleData.getWeekCount());


        ArrayList<SchedulePeriod> allowedPeriods = extratedSchedulePeriodAllowed();
        ArrayList<SchedulePeriod> excludePeriods = extratedSchedulePeriodExclude();
        ArrayList<RoomPreference> roomIChoose = extratedRoomPreference();
        ArrayList<RoomPreference> excludeRoom = extratedRoomExclude();
        out.println(allowedPeriods);
        out.println(excludePeriods);
        out.println(roomIChoose);
        out.println(excludeRoom);

        if(true){
            return engine.suggestNewCourse(courseName,curricularUnit,classTurn,className, 30,weekCount,excludePeriods,allowedPeriods,roomIChoose, excludeRoom,startDate);
        }else{
            return engine.suggestCompensation(possibilidadeHorario(), excludePeriods, allowedPeriods, roomIChoose, excludeRoom);
        }

    }

    private LineSchedule possibilidadeHorario(){

        return scheduleData.getDataModel().getScheduleEntries().get(rowSelected);
    }

    private ArrayList<SchedulePeriod> extratedSchedulePeriodAllowed(){

        ArrayList<SchedulePeriod> allowedPeriods = new ArrayList<>();

        for (Map.Entry<SchedulePeriod, JCheckBox> pair : scheduleData.getCheckBoxMapDaysPreference().entrySet()){
            SchedulePeriod period = pair.getKey();
            JCheckBox checkBox = pair.getValue();
            if (checkBox.isSelected()){
                allowedPeriods.add(period);
            }
        }

        for (Map.Entry<SchedulePeriod, JCheckBox> pair : scheduleData.getCheckBoxMapPeriodPreference().entrySet()){
            SchedulePeriod period = pair.getKey();
            JCheckBox checkBox = pair.getValue();
            if (checkBox.isSelected()){
                allowedPeriods.add(period);
            }
        }

        return allowedPeriods;
    }

    private  ArrayList<SchedulePeriod> extratedSchedulePeriodExclude(){

        ArrayList<SchedulePeriod> excludePeriods = new ArrayList<>();

        for (Map.Entry<SchedulePeriod, JCheckBox> pair : scheduleData.getCheckBoxMapDaysExclude().entrySet()){
            SchedulePeriod period = pair.getKey();
            JCheckBox checkBox = pair.getValue();
            if (checkBox.isSelected()){
                excludePeriods.add(period);
            }
        }

        for (Map.Entry<SchedulePeriod, JCheckBox> pair : scheduleData.getCheckBoxMapPeriodExclude().entrySet()){
            SchedulePeriod period = pair.getKey();
            JCheckBox checkBox = pair.getValue();
            if (checkBox.isSelected()){
                excludePeriods.add(period);
            }
        }

        return excludePeriods;
    }

    private  ArrayList<RoomPreference> extratedRoomPreference(){

        ArrayList<RoomPreference> preferenceRoom = new ArrayList<>();

        for (Map.Entry<RoomPreference, JCheckBox> pair : scheduleData.getCheckBoxMapRoomPreference().entrySet()){
            RoomPreference period = pair.getKey();
            JCheckBox checkBox = pair.getValue();
            if (checkBox.isSelected()){
                preferenceRoom.add(period);
            }
        }

        return preferenceRoom;
    }

    private  ArrayList<RoomPreference> extratedRoomExclude(){

        ArrayList<RoomPreference> excludeRoom = new ArrayList<>();

        for (Map.Entry<RoomPreference, JCheckBox> pair : scheduleData.getCheckBoxMapRoomExclude().entrySet()){
            RoomPreference period = pair.getKey();
            JCheckBox checkBox = pair.getValue();
            if (checkBox.isSelected()){
                excludeRoom.add(period);
            }
        }

        return excludeRoom;
    }

    private static JRadioButton getSelectedRadioButton(ButtonGroup group){
        Enumeration<AbstractButton> buttons = group.getElements();
        while(buttons.hasMoreElements()){
            JRadioButton button = (JRadioButton) buttons.nextElement();
            if(button.isSelected()) return button;
        }
        return null;
    }
}
