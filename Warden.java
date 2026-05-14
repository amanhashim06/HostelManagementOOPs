import java.util.List;

// Inheritance - extends Person
public class Warden extends Person {

    private String wardenName;
    private String managedHostelName;

    // Constructor chaining - calls Person constructor
    public Warden(String wardenName, String managedHostelName) {
        super(wardenName, wardenName);
        this.wardenName        = wardenName;
        this.managedHostelName = managedHostelName;
    }

    public String getWardenName()                { return wardenName; }
    public String getManagedHostelName()         { return managedHostelName; }
    public void   setManagedHostelName(String h) { this.managedHostelName = h; }

    public void viewEmptyRooms(Hostel hostel) {
        System.out.println("--- Empty Rooms in " + hostel.getHostelName() + " ---");
        boolean found = false;
        for (Room r : hostel.getRooms()) {
            if (r.isAvailable()) {
                System.out.println("  Room " + r.getRoomNumber());
                found = true;
            }
        }
        if (!found) System.out.println("  No empty rooms.");
    }

    public void allotRoom(Student student, Hostel hostel) {
        if (!student.getStatus().equals("pending")) {
            System.out.println("Student is not pending.");
            return;
        }
        if (!hostel.isAvailable()) {
            System.out.println("No rooms available in " + hostel.getHostelName());
            return;
        }
        int freeRoom = -1;
        for (Room r : hostel.getRooms()) {
            if (r.isAvailable()) { freeRoom = r.getRoomNumber(); break; }
        }
        hostel.bookRoom(freeRoom); // Polymorphism - Overloading (with room number)
        student.setStatus("assigned");
        student.setAllocatedRoomNumber(freeRoom);
        student.setAllocatedHostel(hostel.getHostelName());
        System.out.println("Room " + freeRoom + " allotted to " + student.getName());
    }

    public void cancelAllotment(Student student, Hostel hostel) {
        if (!student.getStatus().equals("assigned")) {
            System.out.println("Student has no active allotment.");
            return;
        }
        int roomNum = student.getAllocatedRoomNumber();
        hostel.vacateRoom(roomNum);
        student.setStatus("none");
        student.setAllocatedRoomNumber(-1);
        student.setAllocatedHostel("");
        student.setApplied(false);
        System.out.println("Allotment cancelled. Room " + roomNum + " is now free.");
    }

    public void viewAllocatedStudents(List<Student> allStudents, List<Allocation> allocations) {
        System.out.println("--- Allocated Students in " + managedHostelName + " ---");
        boolean found = false;
        for (Allocation a : allocations) {
            if (a.getHostelName().equals(managedHostelName)) {
                for (Student s : allStudents) {
                    if (s.getRollNumber().equals(a.getStudentRoll())) {
                        System.out.println("  " + s.getName() + " (Roll: "
                            + s.getRollNumber() + ") -> Room " + a.getRoomNumber());
                        found = true;
                    }
                }
            }
        }
        if (!found) System.out.println("  No students assigned yet.");
    }

    @Override
    public void displayRole() { // Polymorphism - Overriding
        System.out.println("I am a warden.");
    }
}
