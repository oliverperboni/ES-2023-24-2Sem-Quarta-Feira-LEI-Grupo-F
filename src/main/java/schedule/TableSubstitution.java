package schedule;

import structures.RoomPreference;
import structures.SchedulePeriod;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TableSubstitution {

    private final ArrayList<RoomPreference> roomPreferences;
    private final ArrayList<SchedulePeriod> weekdaysPreferences;
    private final ArrayList<SchedulePeriod> periodsHoursPreferences;

    private final Map<RoomPreference, JCheckBox> checkBoxMapRoomPreference;
    private final Map<RoomPreference, JCheckBox> checkBoxMapRoomExclude;
    private final Map<SchedulePeriod, JCheckBox> checkBoxMapDaysPreference;
    private final Map<SchedulePeriod, JCheckBox> checkBoxMapDaysExclude;
    private final Map<SchedulePeriod, JCheckBox> checkBoxMapPeriodPreference;
    private final Map<SchedulePeriod, JCheckBox> checkBoxMapPeriodExclude;

    private boolean isPreference = true;
    private final int rowSelected;
    protected ScheduleDataModel dataModel;


    public TableSubstitution(int rowSelected, ScheduleDataModel dataModel) {
        roomPreferences = RoomPreference.getAllRoomPreference();
        weekdaysPreferences = (ArrayList<SchedulePeriod>) SchedulePeriod.getAllweekDays();
        periodsHoursPreferences = (ArrayList<SchedulePeriod>) SchedulePeriod.getAllPeriodHours();

        checkBoxMapRoomPreference = new HashMap<>();
        checkBoxMapRoomExclude = new HashMap<>();
        checkBoxMapDaysPreference = new HashMap<>();
        checkBoxMapDaysExclude = new HashMap<>();
        checkBoxMapPeriodPreference = new HashMap<>();
        checkBoxMapPeriodExclude = new HashMap<>();

        this.rowSelected = rowSelected;
        this.dataModel = dataModel;

        initialize();

        int i = 0;
        while(i < 3000) i++;
    }

    public void initialize() {
        JFrame frame;
        frame = new JFrame();
        frame.setSize(900, 800);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel allocationSettings = new JPanel(new GridLayout(1,2));
        JPanel buttonAllocation = new JPanel();

        JButton cancelButton = new JButton("Cancelar");
        JButton submitButton = new JButton("Submeter");

        cancelButton.addActionListener(e -> frame.dispose());

        submitButton.addActionListener(e -> {
            try {
                new ScheduleTableEngine(this, rowSelected);
            }catch (Exception ignored){};
            frame.dispose();
        });

        JPanel preferencePanel = createPreferencePanel();

        JPanel excludePanel = createExcludePanel();

        buttonAllocation.add(cancelButton);
        buttonAllocation.add(submitButton);

        allocationSettings.add(preferencePanel);
        allocationSettings.add(excludePanel);

        frame.add(buttonAllocation, BorderLayout.SOUTH);
        frame.add(allocationSettings, BorderLayout.NORTH);

        frame.setVisible(true);



    }

    private JPanel createPreferencePanel() {
        JPanel preferencePanel = new JPanel(new GridLayout(5,1));
        preferencePanel.setBorder(new TitledBorder("Preferencia"));

        JPanel horarioPanel = createHorarioPanel();
        JPanel dayPanel = createDayPanel();
        JPanel salaTypePanel = createSalaTypePanel();

        preferencePanel.add(horarioPanel);
        preferencePanel.add(dayPanel);
        preferencePanel.add(salaTypePanel);

        if(isPreference) isPreference = false;
        return preferencePanel;
    }

    private JPanel createHorarioPanel() {
        JPanel horarioPanel = new JPanel(new GridLayout(3, 1));
        horarioPanel.setBorder(new TitledBorder("Horário"));


        for (SchedulePeriod period : periodsHoursPreferences){
            JCheckBox checkbox = new JCheckBox(period.toString());
            horarioPanel.add(checkbox);
            if(isPreference) {
                checkBoxMapPeriodPreference.put(period, checkbox);
            }else{
                checkBoxMapPeriodExclude.put(period, checkbox);
            }
        }
        return horarioPanel;
    }

    private JPanel createDayPanel() {
        JPanel dayPanel = new JPanel(new GridLayout(4, 1));
        dayPanel.setBorder(new TitledBorder("Dia da Semana"));

        for (SchedulePeriod day : weekdaysPreferences) {
            JCheckBox radioButton = new JCheckBox(day.toString());
            dayPanel.add(radioButton);
            if(isPreference) {
                checkBoxMapDaysPreference.put(day,radioButton);
            }else{
                checkBoxMapDaysExclude.put(day,radioButton);
            }
        }

        return dayPanel;
    }

    private JPanel createSalaTypePanel() {
        JPanel salaTypePanel = new JPanel(new GridLayout(9, 4));

        salaTypePanel.setBorder(new TitledBorder("Tipos de Sala"));

        for (RoomPreference roomPreference : roomPreferences) {
            JCheckBox checkBox = new JCheckBox(roomPreference.toString());
            salaTypePanel.add(checkBox);
            if(isPreference) {
                checkBoxMapRoomPreference.put(roomPreference, checkBox);
            }else{
                checkBoxMapRoomExclude.put(roomPreference, checkBox);
            }
        }

        return salaTypePanel;
    }

    private JPanel createExcludePanel() {
        JPanel excludePanel = new JPanel(new GridLayout(5,1));
        excludePanel.setBorder(new TitledBorder("Excluir"));
        excludePanel.add(createHorarioPanel());
        excludePanel.add(createDayPanel());
        excludePanel.add(createSalaTypePanel());

        if(!isPreference) isPreference = true;
        return excludePanel;
    }

    public Map<RoomPreference, JCheckBox> getCheckBoxMapRoomPreference() {
        return checkBoxMapRoomPreference;
    }

    public Map<RoomPreference, JCheckBox> getCheckBoxMapRoomExclude() {
        return checkBoxMapRoomExclude;
    }

    public Map<SchedulePeriod, JCheckBox> getCheckBoxMapDaysPreference() {
        return checkBoxMapDaysPreference;
    }

    public Map<SchedulePeriod, JCheckBox> getCheckBoxMapDaysExclude() {
        return checkBoxMapDaysExclude;
    }

    public Map<SchedulePeriod, JCheckBox> getCheckBoxMapPeriodPreference() {
        return checkBoxMapPeriodPreference;
    }

    public Map<SchedulePeriod, JCheckBox> getCheckBoxMapPeriodExclude() {
        return checkBoxMapPeriodExclude;
    }
}
