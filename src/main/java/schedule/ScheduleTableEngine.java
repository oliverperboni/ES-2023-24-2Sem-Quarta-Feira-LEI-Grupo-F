package schedule;

import structures.LineSchedule;
import structures.RoomPreference;
import structures.SchedulePeriod;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

import static java.lang.System.*;

public class ScheduleTableEngine {

    private final TableSubstitution scheduleData;
    private final ScheduleEngine engine;
    private final int rowSelected;

    public ScheduleTableEngine(TableSubstitution scheduleData, int rowSelected){
        this.scheduleData = scheduleData;
        this.engine = new ScheduleEngine(scheduleData.dataModel);
        this.rowSelected = rowSelected;

        out.println(scheduleData.dataModel.getScheduleEntries().get(rowSelected).toString());

        initialize();
        setupInform();
    }

    public void initialize(){
        JFrame frame = new JFrame();
        frame.setSize(900, 800);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JPanel sugestionPanel = new JPanel();
        sugestionPanel.setLayout(new BorderLayout());

        JPanel sugestionallocation = new JPanel();
        JPanel buttonAllocation = new JPanel();
        buttonAllocation.setLayout(new FlowLayout());

        JButton cancelButton = new JButton("Cancelar");
        JButton submitButton = new JButton("Submeter");

        cancelButton.addActionListener(e-> frame.dispose());

        submitButton.addActionListener(e -> frame.dispose());

        buttonAllocation.add(cancelButton);
        buttonAllocation.add(submitButton);


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
