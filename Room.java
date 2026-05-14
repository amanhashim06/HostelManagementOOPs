// Implements RoomOperations interface
public class Room implements RoomOperations {

    private int     roomNumber;
    private String  hostelName;
    private boolean isOccupied;

    public Room(int roomNumber, String hostelName) {
        this.roomNumber = roomNumber;
        this.hostelName = hostelName;
        this.isOccupied = false;
    }

    public int    getRoomNumber() { return roomNumber; }
    public String getHostelName() { return hostelName; }

    @Override
    public void bookRoom()         { isOccupied = true; }

    @Override
    public void vacateRoom()       { isOccupied = false; }

    @Override
    public boolean isAvailable()   { return !isOccupied; }
}
