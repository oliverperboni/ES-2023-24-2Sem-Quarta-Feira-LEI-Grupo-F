/**
 * The RoomFilterFrame class is a graphical user interface (GUI) that allows filtering rooms based on different criteria.
 * The filter criteria include room name, capacity, building, and date range.
 * This class displays a table of rooms and provides input fields to apply filters.
 * When filters are applied, the table is updated to display only the rooms that match the specified filter criteria.
 */

package filters;

import structures.Room;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RoomFilterFrame {
    private JFrame frame;
    private List<Room> roomList;
    private JTable roomsTable;
    private DefaultTableModel tableModel;

    /**
     * Constructor for the RoomFilterFrame class.
     *
     * @param roomList List of rooms to be filtered.
     */
    public RoomFilterFrame(List<Room> roomList) {
        this.roomList = roomList;
        initialize();
    }

    /**
     * Initializes the graphical user interface and configures the components.
     */
    private void initialize() {
        frame = new JFrame("Filtrar Salas");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null); // Centralizar na tela

        // Obter nomes de todas as colunas dos quartos
        String[] columnNames = getRoomColumnNames();

        tableModel = new DefaultTableModel(columnNames, 0);

        for (Room room : roomList) {
            Object[] rowData = getRoomRowData(room);
            tableModel.addRow(rowData);
        }

        roomsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(roomsTable);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new FlowLayout());

        // Campos de filtro para características das salas
        JLabel typeLabel = new JLabel("Nome Sala:");
        JTextField typeTextField = new JTextField(10);
        filterPanel.add(typeLabel);
        filterPanel.add(typeTextField);

        JLabel edificeLabel = new JLabel("Edificio:");
        JTextField edificeTextField = new JTextField(10);
        filterPanel.add(edificeLabel);
        filterPanel.add(edificeTextField);

        JLabel capacityLabel = new JLabel("Capacidade maxima:");
        JTextField capacityTextField = new JTextField(10);
        filterPanel.add(capacityLabel);
        filterPanel.add(capacityTextField);

        JLabel capacityLabel1 = new JLabel("Capacidade minima:");
        JTextField capacityTextField1 = new JTextField(10);
        filterPanel.add(capacityLabel1);
        filterPanel.add(capacityTextField1);

        JLabel locationLabel = new JLabel("Localização:");
        JTextField locationTextField = new JTextField(10);
        filterPanel.add(locationLabel);
        filterPanel.add(locationTextField);

        JRadioButton jRadioButton1 = new JRadioButton();
        JRadioButton jRadioButton2 = new JRadioButton();
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("E");
        jRadioButton2.setText("OU");
        ButtonGroup G1 = new ButtonGroup();
        G1.add(jRadioButton1);
        G1.add(jRadioButton2);

        // Campos de filtro para critérios temporais
        JLabel startDateLabel = new JLabel("Data/Hora Início (yyyy-MM-dd HH:mm):");
        JTextField startDateTextField = new JTextField(16);
        filterPanel.add(startDateLabel);
        filterPanel.add(startDateTextField);

        JLabel endDateLabel = new JLabel("Data/Hora Fim (yyyy-MM-dd HH:mm):");
        JTextField endDateTextField = new JTextField(16);
        filterPanel.add(endDateLabel);
        filterPanel.add(endDateTextField);

        JButton filterButton = new JButton("Aplicar Filtro");
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String typeFilter = typeTextField.getText().toLowerCase();
                int capacityFilter = Integer.parseInt(capacityTextField.getText());
                int capacityFilter2 = Integer.parseInt(capacityTextField1.getText());
                String locationFilter = locationTextField.getText().toLowerCase();
                String startDateFilter = startDateTextField.getText();
                String endDateFilter = endDateTextField.getText();
                Boolean select = jRadioButton1.isSelected();

                filterRooms(typeFilter, capacityFilter, capacityFilter2 ,locationFilter, startDateFilter, endDateFilter, select);
            }
        });
        filterPanel.add(filterButton);
        filterPanel.add(jRadioButton1);
        filterPanel.add(jRadioButton2);

        frame.add(filterPanel, BorderLayout.SOUTH);
    }

    /**
     * Retrieves column names based on the attributes of the Room object.
     *
     * @return An array of column names.
     */
    private String[] getRoomColumnNames() {
        // Obtenha nomes das colunas baseados nos atributos do objeto Rooms
        return new String[] {
                "Edifício", "Nome Sala", "Capacidade Normal", "Capacidade Exame",
                "N¼ caracterásticas", "Anfiteatro aulas", "Apoio tcnico eventos",
                "Arq 1", "Arq 2", "Arq 3", "Arq 4", "Arq 5", "Arq 6", "Arq 9",
                "BYOD (Bring Your Own Device)", "Focus Group", "Horário sala visível portal público",
                "Laboratório de Arquitectura de Computadores I", "Laboratório de Arquitectura de Computadores II",
                "Laboratório de Bases de Engenharia", "Laboratório de Electrónica", "Laboratório de Informática",
                "Laboratório de Jornalismo", "Laboratório de Redes de Computadores I",
                "Laboratório de Redes de Computadores II", "Laboratório de Telecomunicações",
                "Sala Aulas Mestrado", "Sala Aulas Mestrado Plus", "Sala NEE", "Sala Provas", "Sala Reunião",
                "Sala de Arquitectura", "Sala de Aulas normal", "Videoconferência", "çtrio"
        };
    }

    /**
     * Retrieves row data for the Room object.
     *
     * @param room The Room object for which row data is retrieved.
     * @return An array of row data.
     */
    private Object[] getRoomRowData(Room room) {
        // Obter dados da linha para o objeto structures.Room
        return new Object[] {
                room.getEdificio(), room.getNomeSala(), room.getCapacidadeNormal(), room.getCapacidadeExame(),
                room.getNumCaracteristicas(), room.isAnfiteatroAulas(), room.isApoioTecnicoEventos(),
                room.isArq1(), room.isArq2(), room.isArq3(), room.isArq4(), room.isArq5(),
                room.isArq6(), room.isArq9(), room.isByod(), room.isFocusGroup(),
                room.isHorarioSalaVisivelPortalPublico(), room.isLaboratorioArquiteturaComputadoresI(),
                room.isLaboratorioArquiteturaComputadoresII(), room.isLaboratorioBasesEngenharia(),
                room.isLaboratorioEletronica(), room.isLaboratorioInformatica(), room.isLaboratorioJornalismo(),
                room.isLaboratorioRedesComputadoresI(), room.isLaboratorioRedesComputadoresII(),
                room.isLaboratorioTelecomunicacoes(), room.isSalaAulasMestrado(), room.isSalaAulasMestradoPlus(),
                room.isSalaNEE(), room.isSalaProvas(), room.isSalaReuniao(), room.isSalaArquitetura(),
                room.isSalaAulasNormal(), room.isVideoconferencia(), room.isAtrio()
        };
    }

    /**
     * Filters rooms based on the specified criteria.
     *
     * @param typeFilter      The filter for room name.
     * @param capacityFilter  The filter for normal capacity.
     * @param capacityFilter2 The filter for normal capacity 2.
     * @param locationFilter  The filter for building location.
     * @param startDateFilter The filter for start date/time.
     * @param endDateFilter   The filter for end date/time.
     * @param logic           The filter logic (AND or OR).
     */
    private void filterRooms(String typeFilter, int capacityFilter, int capacityFilter2, String locationFilter, String startDateFilter,
                             String endDateFilter, Boolean logic) {
        tableModel.setRowCount(0);
        for (Room room : roomList) {
            // Verifique os critérios de filtro
            // "E"
            if (logic || logic == null) {

                if (room.getNomeSala().toLowerCase().contains(typeFilter) &&
                        room.getCapacidadeNormal() > capacityFilter && room.getCapacidadeNormal() < capacityFilter2&&
                        room.getEdificio().toLowerCase().contains(locationFilter)) {
                    // Adicione lógica de filtragem para critérios temporais aqui
                    // Neste exemplo, estamos apenas verificando se o nome da sala contém o filtro
                    // de tipo
                    Object[] rowData = getRoomRowData(room);
                    tableModel.addRow(rowData);
                }

            }else{
                if (room.getNomeSala().toLowerCase().contains(typeFilter) ||
                        room.getCapacidadeNormal() > capacityFilter ||room.getCapacidadeNormal() < capacityFilter2||
                        room.getEdificio().toLowerCase().contains(locationFilter)) {
                    // Adicione lógica de filtragem para critérios temporais aqui
                    // Neste exemplo, estamos apenas verificando se o nome da sala contém o filtro
                    // de tipo
                    Object[] rowData = getRoomRowData(room);
                    tableModel.addRow(rowData);
                }
            }
            // "OU"

        }
    }

    /**
     * Displays the GUI frame.
     */
    public void show() {
        frame.setVisible(true);
    }
}
