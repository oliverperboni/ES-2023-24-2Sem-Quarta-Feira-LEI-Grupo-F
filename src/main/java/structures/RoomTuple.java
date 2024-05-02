package structures;

public class RoomTuple<ArrayList, List> {

    ArrayList roomLineArray;
    List roomColumnHeaders;

    public RoomTuple(ArrayList roomLineArray, List roomColumnHeaders) {
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
