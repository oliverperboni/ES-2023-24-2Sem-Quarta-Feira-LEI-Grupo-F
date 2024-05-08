package core;

import structures.LineSchedule;
import structures.RoomPreference;
import structures.SchedulePeriod;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.util.ArrayList;
import java.util.Map;

import static java.lang.System.out;

public class ScheduleTableEngine {

    private final TableSubstitution scheduleData;
    private final ScheduleEngine engine;
    private final int rowSelected;
    private final JTable table;

    public ScheduleTableEngine(TableSubstitution scheduleData, int rowSelected, JTable table){
        this.scheduleData = scheduleData;
        this.engine = new ScheduleEngine(scheduleData.dataModel);
        this.rowSelected = table.convertRowIndexToModel(rowSelected);
        this.table = table;

        setupInform();
        initialize();
    }

    public void initialize() {
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
        int totalElements = engine.getPossibilityList().size(); // Número total de elementos

        // Adicione os elementos iniciais ao sugestionallocation
        for (int i = 0; i < Math.min(initialElementsToShow, totalElements); i++) {
            JRadioButton radioButton = new JRadioButton(engine.getPossibilityList().get(i).toString());
            teste.add(radioButton);
            sugestionallocation.add(radioButton);
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
                    JRadioButton radioButton = new JRadioButton(engine.getPossibilityList().get(i).toString());
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
        submitButton.addActionListener(e -> frame.dispose());

        buttonAllocation.add(cancelButton);
        buttonAllocation.add(submitButton);

        sugestionPanel.add(scrollPane, BorderLayout.NORTH);
        sugestionPanel.add(buttonAllocation, BorderLayout.SOUTH);
        frame.add(sugestionPanel);
        frame.setVisible(true);
    }

    private void setupInform(){
        ArrayList<SchedulePeriod> allowedPeriods = extratedSchedulePeriodAllowed();
        ArrayList<SchedulePeriod> excludePeriods = extratedSchedulePeriodExclude();
        ArrayList<RoomPreference> roomIChoose = extratedRoomPreference();
        ArrayList<RoomPreference> excludeRoom = extratedRoomExclude();

        out.println(allowedPeriods);
        out.println(excludePeriods);
        out.println(roomIChoose);
        out.println(excludeRoom);
        engine.suggestCompensation(possibilidadeHorario(), excludePeriods, allowedPeriods, roomIChoose, excludeRoom);

    }

    private LineSchedule possibilidadeHorario(){

        return scheduleData.dataModel.getScheduleEntries().get(rowSelected);
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
}
