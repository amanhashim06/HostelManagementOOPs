// Inheritance - extends Person
public class Student extends Person {

    private String  rollNumber;
    private String  status;
    private int     allocatedRoomNumber;
    private String  allocatedHostel;
    private boolean applied;

    // Constructor chaining - calls Person constructor
    public Student(String name, String rollNumber) {
        super(name, rollNumber);
        this.rollNumber          = rollNumber;
        this.status              = "none";
        this.allocatedRoomNumber = -1;
        this.allocatedHostel     = "";
        this.applied             = false;
    }

    public String  getRollNumber()               { return rollNumber; }
    public String  getStatus()                   { return status; }
    public void    setStatus(String s)           { this.status = s; }
    public int     getAllocatedRoomNumber()       { return allocatedRoomNumber; }
    public void    setAllocatedRoomNumber(int r) { this.allocatedRoomNumber = r; }
    public String  getAllocatedHostel()           { return allocatedHostel; }
    public void    setAllocatedHostel(String h)  { this.allocatedHostel = h; }
    public boolean isApplied()                   { return applied; }
    public void    setApplied(boolean a)         { this.applied = a; }

    public void applyForRoom() {
        if (status.equals("none")) {
            status  = "pending";
            applied = true;
            System.out.println("Application submitted. Status: pending.");
        } else {
            System.out.println("Already applied. Current status: " + status);
        }
    }

    public void checkStatus() {
        System.out.println("Status: " + status);
        if (status.equals("assigned")) {
            System.out.println("Hostel: " + allocatedHostel + "  Room: " + allocatedRoomNumber);
        }
    }

    @Override
    public void displayRole() { // Polymorphism - Overriding
        System.out.println("I am a student.");
    }

    @Override
    public String toString() {
        String loc = status.equals("assigned")
                   ? allocatedHostel + ", Room " + allocatedRoomNumber
                   : "Not assigned";
        return "Name: " + getName() + "  Roll: " + rollNumber
             + "  Status: " + status + "  Location: " + loc;
    }
}
