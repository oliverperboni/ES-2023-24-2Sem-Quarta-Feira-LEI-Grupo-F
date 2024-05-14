package visualize;

import core.ScheduleTableEngine;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import structures.Room;
import structures.RoomPreference;
import structures.ScheduleDataModel;
import structures.SchedulePeriod;
import visualize.Table;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class TableSubstitution {

    private List<RoomPreference> roomPreferences;
    private final List<SchedulePeriod> weekdayPreferences;
    private final List<SchedulePeriod> timeSlotPreferences;

    private final List<SchedulePeriod> timePeriodsPreferences;
    private  Object cursoSelec;
    private  Object ucSelec;
    private  Object turmaSelec;
    private  Object turnoSelec;
    private Object inscritosSelec;
    private Room salaSelec;
    private Room salaSelec2;
    private Room salaSelec3;

    private  Date dataInic;
    private  Date dataFim;
    private String weekCount;


    private final Map<RoomPreference, JCheckBox> checkBoxMapRoomPreference;
    private final Map<RoomPreference, JCheckBox> checkBoxMapRoomExclude;
    private final Map<SchedulePeriod, JCheckBox> checkBoxMapDaysPreference;
    private final Map<SchedulePeriod, JCheckBox> checkBoxMapDaysExclude;
    private final Map<SchedulePeriod, JCheckBox> checkBoxMapPeriodPreference;
    private final Map<SchedulePeriod, JCheckBox> checkBoxMapPeriodExclude;
    private final JComboBox<Object> curso;
    private final JComboBox<Object> UC;
    private final JComboBox<Object> turma;
    private final JComboBox<Object> sala;
    private final JComboBox<Object> sala2;
    private final JComboBox<Object> sala3;
    private final ArrayList<Room> rExcluded = new ArrayList<>();

    private boolean isPreference = true;
    private boolean modify = false;
    private final int rowSelected;
    public ScheduleDataModel dataModel;
    private final JTable table;
    private final Table tabela;
    private JFrame frame;

    /**
     * Initializes a TableSubstitution object with the specified parameters.
     *
     * @param rowSelected The index of the selected row in the table.
     * @param tabela      The table object containing the schedule data.
     * @param modify      Indicates whether the initialization is for modifying an existing schedule.
     */
    public TableSubstitution(int rowSelected, Table tabela, boolean modify) {
        roomPreferences = Arrays.asList(RoomPreference.values());
        weekdayPreferences = SchedulePeriod.getAllWeekDays();
        timeSlotPreferences = SchedulePeriod.getAllTimeSlots();
        timePeriodsPreferences = SchedulePeriod.getAllTimePeriods();

        checkBoxMapRoomPreference = new HashMap<>();
        checkBoxMapRoomExclude = new HashMap<>();
        checkBoxMapDaysPreference = new HashMap<>();
        checkBoxMapDaysExclude = new HashMap<>();
        checkBoxMapPeriodPreference = new HashMap<>();
        checkBoxMapPeriodExclude = new HashMap<>();
        cursoSelec="";
        ucSelec="";
        turmaSelec="";
        turnoSelec="";
        inscritosSelec="";
        dataInic=null;
        dataFim=null;
        weekCount="";

        curso = new JComboBox<>();
        UC = new JComboBox<>();
        turma = new JComboBox<>();
        sala = new JComboBox<>();
        sala2 = new JComboBox<>();
        sala3 = new JComboBox<>();



        this.tabela = tabela;
        this.rowSelected = rowSelected;
        this.dataModel = tabela.getDataModel();
        this.table = tabela.getJTable();
        this.modify = modify;


        initialize(this.modify);
    }

    /**
     * Initializes the schedule table engine with the provided settings.
     *
     * @param modify Indicates whether the initialization is for modifying an existing schedule.
     */
    public void initialize(boolean modify) {
        this.frame = new JFrame();
        this.frame.setSize(1000, 1000);
        this.frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.frame.setLayout(new BorderLayout());

        JPanel allocationSettings = new JPanel(new GridLayout(1,3));
        JPanel buttonAllocation = new JPanel();

        JButton cancelButton = new JButton("Cancelar");
        JButton submitButton = new JButton("Submeter");

        cancelButton.addActionListener(e -> frame.dispose());

        submitButton.addActionListener(e -> {
            try {
                new ScheduleTableEngine(this, rowSelected, table, modify);
            }catch (Exception ignored){
                System.out.println("boas");
            }
            System.out.println("Teste");
            this.frame.dispose();
        });


        JPanel cursoPanel = createCursoPanel();

        JPanel preferencePanel = createPreferencePanel();

        JPanel excludePanel = createExcludePanel();

        buttonAllocation.add(cancelButton);
        buttonAllocation.add(submitButton);

        if (modify){
            allocationSettings.add(cursoPanel);
        }

        allocationSettings.add(preferencePanel);
        allocationSettings.add(excludePanel);

        this.frame.add(buttonAllocation, BorderLayout.SOUTH);
        this.frame.add(allocationSettings, BorderLayout.NORTH);

        this.frame.setVisible(true);
    }



    /**
     * Creates a panel for selecting course-related options.
     *
     * @return The JPanel containing course selection components.
     */
    private JPanel createCursoPanel() {

        JPanel cursoPanel = new JPanel(new GridLayout(24,1));
        JPanel x1 = new JPanel(new FlowLayout());
        JPanel x2 = new JPanel(new FlowLayout());
        JPanel x3 = new JPanel(new FlowLayout());
        JPanel x4 = new JPanel(new FlowLayout());
        JPanel x5 = new JPanel(new FlowLayout());
        JPanel x6 = new JPanel(new FlowLayout());
        JPanel x7 = new JPanel(new FlowLayout());
        JPanel x8 = new JPanel(new FlowLayout());


        cursoPanel.setBorder(new TitledBorder("Escolher Aulas"));



        List<Object> curs = tabela.getColuna(table,0);
        List<Object> uc =  tabela.getColuna(table,1);
        List<Object> turm = tabela.getColuna(table,3);

        for (Object item : curs) {
            curso.addItem(item);
        }for (Object item : uc) {
            UC.addItem(item);
        }
        for (Object item : turm) {
            turma.addItem(item);
        }
        curso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cursoSelec = curso.getSelectedItem();
            }
        });
        UC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ucSelec = UC.getSelectedItem();
            }
        });

        turma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                turmaSelec = turma.getSelectedItem();
            }
        });



        JLabel jLabel = new JLabel("Curso");
        x1.add(jLabel);
        x1.add(curso);

        JLabel jLabel2 = new JLabel("UC");
        x2.add(jLabel2);
        x2.add(UC);

        JLabel jLabel3 = new JLabel("Turno");
        JTextField t1 = new JTextField("");
        t1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                turnoSelec = t1.getText();

            }
        });
        x3.add(jLabel3);
        x3.add(t1);

        JLabel jLabel4 = new JLabel("Inscritos");
        JTextField t2 = new JTextField("");
        t2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inscritosSelec = t2.getText();
            }
        });
        x4.add(jLabel4);
        x4.add(t2);

        JLabel jLabel5 = new JLabel("Turma");
        x5.add(jLabel5);
        x5.add(turma);

        UtilDateModel model1 = new UtilDateModel();
        UtilDateModel model2 = new UtilDateModel();

        JDatePanelImpl datePanel1 = new JDatePanelImpl(model1);
        JDatePanelImpl datePanel2 = new JDatePanelImpl(model2);
        JDatePickerImpl datePicker1 = new JDatePickerImpl(datePanel1);
        JDatePickerImpl datePicker2 = new JDatePickerImpl(datePanel2);


        datePicker1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dataInic = (Date) datePicker1.getModel().getValue();

            }
        });

        datePicker2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dataFim = (Date) datePicker2.getModel().getValue();

            }
        });

        JLabel jLabel6 = new JLabel("Data Inicio");
        x6.add(jLabel6);
        x6.add(datePicker1);

        JLabel jLabel7 = new JLabel("Data Fim");
        x7.add(jLabel7);
        x7.add(datePicker2);

        JTextField textArea = new JTextField("");
        JButton b = new JButton("Calcular");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(dataInic!=null && dataFim!=null){
                    weekCount = String.valueOf(weekCount(dataInic,dataFim));
                    textArea.setText(weekCount);
                }

            }
        });
        JLabel jLabel8 = new JLabel("Número de Semanas");


        x8.add(jLabel8);
        x8.add(textArea);
        x8.add(b);



        cursoPanel.add(x1);
        cursoPanel.add(x2);
        cursoPanel.add(x3);
        cursoPanel.add(x4);
        cursoPanel.add(x5);
        cursoPanel.add(x6);
        cursoPanel.add(x7);
        cursoPanel.add(x8);

        // Define um tamanho menor para os JComboBox
        Dimension comboBoxSize = new Dimension(150, 25);
        curso.setPreferredSize(comboBoxSize);
        UC.setPreferredSize(comboBoxSize);
        turma.setPreferredSize(comboBoxSize);

        return cursoPanel;
    }

    /**
     * Calculates the number of weeks between two dates.
     *
     * @param d1 The start date.
     * @param d2 The end date.
     * @return The number of weeks between the two dates.
     */
    private long weekCount(Date d1, Date d2){
        // Calculate the difference in milliseconds
        long timeDifferenceMillis = d2.getTime() - d1.getTime();

        // Convert milliseconds to weeks
        double weeksDifference = (double) timeDifferenceMillis / (7 * 24 * 60 * 60 * 1000);

        // Round up to the nearest whole number
        long roundedWeeksDifference = (long) Math.ceil(weeksDifference);
        return roundedWeeksDifference;
    }
    /**
     * Creates a panel for specifying scheduling preferences.
     *
     * @return The JPanel containing scheduling preference components.
     */
    private JPanel createPreferencePanel() {
        JPanel preferencePanel = new JPanel(new GridLayout(5,1));
        preferencePanel.setBorder(new TitledBorder("Preferencia"));


        JPanel horarioPanel = createTimePeriodsPanel();
        JPanel dayPanel = createDayPanel();
        JPanel salaTypePanel = createSalaTypePanel();

        preferencePanel.add(horarioPanel);
        preferencePanel.add(dayPanel);
        preferencePanel.add(salaTypePanel);

        if(isPreference) isPreference = false;
        return preferencePanel;
    }

    /**
     * Creates a panel containing time periods checkboxes.
     *
     * @return The JPanel containing the time periods checkboxes.
     */
    private JPanel createTimePeriodsPanel() {
        JPanel timePeriodsPanel = new JPanel(new GridLayout(3, 1));
        timePeriodsPanel.setBorder(new TitledBorder("Horário"));

        if (isPreference) {
            for (SchedulePeriod period : timePeriodsPreferences) {
                JCheckBox checkbox = new JCheckBox(period.toString());
                timePeriodsPanel.add(checkbox);
                checkBoxMapPeriodPreference.put(period, checkbox);
            }
        } else {
            for (SchedulePeriod period : timeSlotPreferences) {
                JCheckBox checkbox = new JCheckBox(period.toString());
                timePeriodsPanel.add(checkbox);
                checkBoxMapPeriodExclude.put(period, checkbox);
            }
        }

        return timePeriodsPanel;
    }

    /**
     * Creates a panel containing checkboxes for selecting days of the week.
     *
     * @return The JPanel containing the day selection checkboxes.
     */
    private JPanel createDayPanel() {
        JPanel dayPanel = new JPanel(new GridLayout(4, 1));
        dayPanel.setBorder(new TitledBorder("Dia da Semana"));

        for (SchedulePeriod day : weekdayPreferences) {
            JCheckBox radioButton = new JCheckBox(day.toString());
            dayPanel.add(radioButton);
            if (isPreference) {
                checkBoxMapDaysPreference.put(day, radioButton);
            }
        }

        return dayPanel;
    }

    /**
     * Creates a panel containing checkboxes for selecting room types.
     *
     * @return The JPanel containing the room type selection checkboxes.
     */
    private JPanel createSalaTypePanel() {
        JPanel salaTypePanel = new JPanel(new GridLayout(9, 4));
        salaTypePanel.setBorder(new TitledBorder("Tipos de Sala"));

        for (RoomPreference roomPreference : roomPreferences) {
            JCheckBox checkBox = new JCheckBox(roomPreference.toString());
            salaTypePanel.add(checkBox);
            if (isPreference) {
                checkBoxMapRoomPreference.put(roomPreference, checkBox);
            }
        }

        return salaTypePanel;
    }

    /**
     * Creates a panel for excluding rooms from the scheduling.
     *
     * @return The JPanel for excluding rooms.
     */
    private JPanel createSalaExcludePanel() {
        JPanel salaExcludePanel = new JPanel(new GridLayout(9, 4));
        salaExcludePanel.setBorder(new TitledBorder("Tipos de Sala"));

        // Code for adding room selection JComboBoxes and listeners...

        return salaExcludePanel;
    }

    /**
     * Creates a panel for excluding time periods and rooms.
     *
     * @return The JPanel for excluding time periods and rooms.
     */
    private JPanel createExcludePanel() {
        JPanel excludePanel = new JPanel(new GridLayout(5, 1));
        excludePanel.setBorder(new TitledBorder("Excluir"));
        excludePanel.add(createTimePeriodsPanel());
        excludePanel.add(createSalaExcludePanel());

        if (!isPreference) isPreference = true;
        return excludePanel;
    }

    /**
     * Retrieves the map containing room preferences and their corresponding check boxes.
     *
     * @return A map associating each room preference with its check box.
     */
    public Map<RoomPreference, JCheckBox> getCheckBoxMapRoomPreference() {
        return checkBoxMapRoomPreference;
    }

    /**
     * Retrieves the map containing excluded room preferences and their corresponding check boxes.
     *
     * @return A map associating each excluded room preference with its check box.
     */
    public Map<RoomPreference, JCheckBox> getCheckBoxMapRoomExclude() {
        return checkBoxMapRoomExclude;
    }

    /**
     * Retrieves the map containing day preferences and their corresponding check boxes.
     *
     * @return A map associating each day preference with its check box.
     */
    public Map<SchedulePeriod, JCheckBox> getCheckBoxMapDaysPreference() {
        return checkBoxMapDaysPreference;
    }

    /**
     * Retrieves the map containing excluded day preferences and their corresponding check boxes.
     *
     * @return A map associating each excluded day preference with its check box.
     */
    public Map<SchedulePeriod, JCheckBox> getCheckBoxMapDaysExclude() {
        return checkBoxMapDaysExclude;
    }

    /**
     * Retrieves the map containing period preferences and their corresponding check boxes.
     *
     * @return A map associating each period preference with its check box.
     */
    public Map<SchedulePeriod, JCheckBox> getCheckBoxMapPeriodPreference() {
        return checkBoxMapPeriodPreference;
    }

    /**
     * Retrieves the map containing excluded period preferences and their corresponding check boxes.
     *
     * @return A map associating each excluded period preference with its check box.
     */
    public Map<SchedulePeriod, JCheckBox> getCheckBoxMapPeriodExclude() {
        return checkBoxMapPeriodExclude;
    }

    /**
     * Retrieves the initial date for the scheduling.
     *
     * @return The initial date.
     */
    public Date getDateI() {
        return dataInic;
    }

    /**
     * Retrieves the final date for the scheduling.
     *
     * @return The final date.
     */
    public Date getDateF() {
        return dataFim;
    }

    /**
     * Retrieves the selected course.
     *
     * @return The selected course.
     */
    public Object getCursoSelec() {
        return cursoSelec;
    }

    /**
     * Retrieves the selected curricular unit.
     *
     * @return The selected curricular unit.
     */
    public Object getUcSelec() {
        return ucSelec;
    }

    /**
     * Retrieves the selected class.
     *
     * @return The selected class.
     */
    public Object getTurmaSelec() {
        return turmaSelec;
    }

    /**
     * Retrieves the selected turn.
     *
     * @return The selected turn.
     */
    public Object getTurnoSelec() {
        return turnoSelec;
    }

    /**
     * Retrieves the selected number of enrolled students.
     *
     * @return The selected number of enrolled students.
     */
    public Object getInscritosSelec() {
        return inscritosSelec;
    }

    /**
     * Retrieves the selected number of weeks for scheduling.
     *
     * @return The selected number of weeks.
     */
    public String getWeekCount() {
        return weekCount;
    }

    /**
     * Retrieves the excluded rooms for scheduling.
     *
     * @return An ArrayList containing the excluded rooms.
     */
    public ArrayList<Room> getSalasExc() {
        return rExcluded;
    }

    /**
     * Retrieves the JTable used for scheduling.
     *
     * @return The JTable object.
     */
    public JTable getJTable() {
        return table;
    }

    /**
     * Retrieves the scheduling table.
     *
     * @return The scheduling table.
     */
    public Table getTable() {
        return tabela;
    }

    /**
     * Retrieves the frame used for scheduling.
     *
     * @return The frame object.
     */
    public JFrame getFrame() {
        return frame;
    }
}
