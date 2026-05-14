import java.util.ArrayList;
import java.util.List;

public class Hostel extends HostelEntity implements RoomOperations {

    private String     hostelName;
    private String     address;
    private List<Room> rooms;

    public Hostel(String hostelName, String address) {
        this.hostelName = hostelName;
        this.address    = address;
        this.rooms      = new ArrayList<>();
        for (int i = 1; i <= 10; i++)
            rooms.add(new Room(i, hostelName));
    }

    public String     getHostelName() { return hostelName; }
    public String     getAddress()    { return address; }
    public List<Room> getRooms()      { return rooms; }

    public int getFreeRoomsCount() {
        int count = 0;
        for (Room r : rooms) if (r.isAvailable()) count++;
        return count;
    }

    
    @Override
    public void bookRoom() {
        for (Room r : rooms) {
            if (r.isAvailable()) { r.bookRoom(); return; }
        }
    }

    public boolean bookRoom(int roomNumber) {
        Room r = getRoomByNumber(roomNumber);
        if (r != null && r.isAvailable()) { r.bookRoom(); return true; }
        return false;
    }

    public void vacateRoom(int roomNumber) {
        Room r = getRoomByNumber(roomNumber);
        if (r != null) r.vacateRoom();
    }

    @Override
    public void vacateRoom() {
        for (Room r : rooms) { if (!r.isAvailable()) { r.vacateRoom(); return; } }
    }

    @Override
    public boolean isAvailable() { return getFreeRoomsCount() > 0; }

    public Room getRoomByNumber(int roomNumber) {
        for (Room r : rooms) if (r.getRoomNumber() == roomNumber) return r;
        return null;
    }
    @Override
    public void showInfo() { 
        System.out.println("[" + TYPE + "] " + hostelName + " | " + address
                         + " | Free rooms: " + getFreeRoomsCount());
    }
}
