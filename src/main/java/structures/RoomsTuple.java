package structures;

/**
 * The RoomsTuple class represents a tuple containing two elements:
 * a list of room line data and a list of room column headers.
 * @since 1.0
 */
public class RoomsTuple<ArrayList, List> {

    /**
     * An array list containing room line data.
     */
    ArrayList roomLineArray;

    /**
     * A list containing room column headers.
     */
    List roomColumnHeaders;

    /**
     * Constructs a RoomsTuple object with the specified room line array and room column headers list.
     * @param roomLineArray the array list of room line data
     * @param roomColumnHeaders the list of room column headers
     */
    public RoomsTuple(ArrayList roomLineArray, List roomColumnHeaders) {
        this.roomLineArray = roomLineArray;
        this.roomColumnHeaders = roomColumnHeaders;
    }

    public ArrayList getRoomLineArray() {
        return roomLineArray;
    }

    public List getRoomColumnHeaders() {
        return roomColumnHeaders;
    }

    public void setRoomLineArray(ArrayList newRoomLineArray) {
        this.roomLineArray = newRoomLineArray;
    }

    public void setRoomColumnHeaders(List roomColumnHeaders) {
        this.roomColumnHeaders = roomColumnHeaders;
    }

}
