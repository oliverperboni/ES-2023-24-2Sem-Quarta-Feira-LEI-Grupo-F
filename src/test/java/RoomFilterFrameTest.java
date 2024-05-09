public class RoomFilterFrameTest {
//    private List<Rooms> roomsList;
//    private RoomFilterFrame roomFilterFrame;
//
//    @BeforeEach
//    void setUp() {
//        roomsList = new ArrayList<>();
//        // Add sample rooms for testing
//        roomsList.add(new Rooms("Building A", "Room 101", 50, 0, "", false, false,
//                false, false, false, false, false, false, false, false, false,
//                false, false, false, false, false, false, false, false, false,
//                false, false, false, false, false, false, false, false, false, false));
//        roomsList.add(new Rooms("Building B", "Room 201", 100, 0, "", false, false,
//                false, false, false, false, false, false, false, false, false,
//                false, false, false, false, false, false, false, false, false,
//                false, false, false, false, false, false, false, false, false, false));
//        roomFilterFrame = new RoomFilterFrame(roomsList);
//    }
//
//
//    @Test
//    void filterRoomsTest() {
//        // Simulate filter input
//        String typeFilter = "Room 101";
//        int capacityFilter = 0;
//        int capacityFilter2 = 100;
//        String locationFilter = "Building A";
//        String edificeFilter = "Building A";
//        String startDateFilter = "";
//        String endDateFilter = "";
//        Boolean select = false;
//
//        // Perform filtering
//        roomFilterFrame.filterRooms(typeFilter, capacityFilter, capacityFilter2,
//                locationFilter, edificeFilter, startDateFilter, endDateFilter, select);
//
//        // Get filtered table model
//        DefaultTableModel tableModel = roomFilterFrame.getTableModel();
//
//        // Ensure only one room matches the filter criteria
//        assertEquals(1, tableModel.getRowCount());
//    }

}
