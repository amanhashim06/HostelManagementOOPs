public class Allocation {

    private String studentRoll;
    private String hostelName;
    private int    roomNumber;

    public Allocation(String studentRoll, String hostelName, int roomNumber) {
        this.studentRoll = studentRoll;
        this.hostelName  = hostelName;
        this.roomNumber  = roomNumber;
    }

    public String getStudentRoll() { return studentRoll; }
    public String getHostelName()  { return hostelName; }
    public int    getRoomNumber()  { return roomNumber; }

    public final void printReceipt() { // final method
        System.out.println("=== Allocation Receipt ===");
        System.out.println("Student Roll : " + studentRoll);
        System.out.println("Hostel       : " + hostelName);
        System.out.println("Room Number  : " + roomNumber);
        System.out.println("==========================");
    }
}
