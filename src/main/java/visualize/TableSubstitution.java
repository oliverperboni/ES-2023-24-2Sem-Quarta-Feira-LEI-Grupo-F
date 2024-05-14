package visualize;

import core.ScheduleTableEngine;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import structures.RoomPreference;
import structures.ScheduleDataModel;
import structures.SchedulePeriod;

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

    private boolean isPreference = true;
    private boolean modify = false;
    private final int rowSelected;
    protected ScheduleDataModel dataModel;
    private final JTable table;
    private final Table tabela;
    private JFrame frame;

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



        this.tabela = tabela;
        this.rowSelected = rowSelected;
        this.dataModel = tabela.getDataModel();
        this.table = tabela.getJTable();
        this.modify = modify;


        initialize(this.modify);
    }

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
                new ScheduleTableEngine(this, rowSelected, table);//, modify);
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
    private long weekCount(Date d1, Date d2){
        // Calculate the difference in milliseconds
        long timeDifferenceMillis = d2.getTime() - d1.getTime();

        // Convert milliseconds to weeks
        double weeksDifference = (double) timeDifferenceMillis / (7 * 24 * 60 * 60 * 1000);

        // Round up to the nearest whole number
        long roundedWeeksDifference = (long) Math.ceil(weeksDifference);
        return roundedWeeksDifference;
    }
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

    private JPanel createTimePeriodsPanel() {
        JPanel timePeriodsPanel = new JPanel(new GridLayout(3, 1));
        timePeriodsPanel.setBorder(new TitledBorder("Horário"));


        if(isPreference){
            for (SchedulePeriod period : timePeriodsPreferences){
                JCheckBox checkbox = new JCheckBox(period.toString());
                timePeriodsPanel.add(checkbox);

                checkBoxMapPeriodPreference.put(period, checkbox);

            }
        }else{
            for (SchedulePeriod period : timeSlotPreferences){
                JCheckBox checkbox = new JCheckBox(period.toString());
                timePeriodsPanel.add(checkbox);

                checkBoxMapPeriodExclude.put(period, checkbox);

            }
        }



        return timePeriodsPanel;
    }
    private JPanel createHorarioPanel() {
        JPanel horarioPanel = new JPanel(new GridLayout(3, 1));
        horarioPanel.setBorder(new TitledBorder("Horário"));


        for (SchedulePeriod period : timeSlotPreferences){
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

        for (SchedulePeriod day : weekdayPreferences) {
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
        excludePanel.add(createTimePeriodsPanel());
        //excludePanel.add(createDayPanel());
        //excludePanel.add(createSalaTypePanel());

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
    public Date getDateI() {
        return dataInic;
    }
    public Date getDateF() {
        return dataFim;
    }
    public Object getCursoSelec() {
        return cursoSelec;
    }
    public Object getUcSelec() {
        return ucSelec;
    }
    public Object getTurmaSelec() {
        return turmaSelec;
    }
    public Object getTurnoSelec() {
        return turnoSelec;
    }
    public Object getInscritosSelec() {

        return inscritosSelec;
    }
    public String getWeekCount() {
        return weekCount;
    }

    public ScheduleDataModel getDataModel() {
        return dataModel;
    }

    public JTable getJTable() {
        return table;
    }

    public Table getTable() {
        return tabela;
    }

    public JFrame getFrame(){

        return frame;
    }
}
