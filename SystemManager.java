import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SystemManager {

    static List<Student>    students    = new ArrayList<>();
    static List<Warden>     wardens     = new ArrayList<>();
    static List<Hostel>     hostels     = new ArrayList<>();
    static List<Allocation> allocations = new ArrayList<>();
    static Admin            admin;

    static void initializeData() {
        hostels.add(new Hostel("Hostel1", "Campus North"));
        hostels.add(new Hostel("Hostel2", "Campus South"));
        wardens.add(new Warden("warden1", "Hostel1"));
        wardens.add(new Warden("warden2", "Hostel2"));
        students.add(new Student("Alice", "S001"));
        students.add(new Student("Bob",   "S002"));
        admin = new Admin(101, "Admin", "admin123");
    }

    static Student studentLogin(String rollNumber) {
        for (Student s : students)
            if (s.getRollNumber().equals(rollNumber)) return s;
        return null;
    }

    static Warden wardenLogin(String wardenName) {
        for (Warden w : wardens)
            if (w.getWardenName().equals(wardenName)) return w;
        return null;
    }

    static boolean adminLogin(int id, String password) {
        return (id == 101 && password.equals("admin123"));
    }

    static void registerStudent(Scanner sc) {
        System.out.print("Enter name       : ");
        String name = sc.nextLine().trim();
        System.out.print("Enter roll number: ");
        String roll = sc.nextLine().trim();
        for (Student s : students) {
            if (s.getRollNumber().equals(roll)) {
                System.out.println("Roll number already exists.");
                return;
            }
        }
        students.add(new Student(name, roll));
        System.out.println("Student registered successfully.");
    }

    static Hostel getHostelByName(String name) {
        for (Hostel h : hostels)
            if (h.getHostelName().equalsIgnoreCase(name)) return h;
        return null;
    }

    static Allocation getAllocationByRoll(String roll) {
        for (Allocation a : allocations)
            if (a.getStudentRoll().equals(roll)) return a;
        return null;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        initializeData();

        boolean running = true;
        while (running) {
            System.out.println("\n=== HOSTEL MANAGEMENT SYSTEM ===");
            System.out.println("1. Student Login");
            System.out.println("2. Warden Login");
            System.out.println("3. Admin Login");
            System.out.println("4. Register New Student");
            System.out.println("5. Exit");
            System.out.print("Choose: ");
            String choice = sc.nextLine().trim();

            if (choice.equals("1")) {
                System.out.print("Enter roll number: ");
                Student s = studentLogin(sc.nextLine().trim());
                if (s == null) { System.out.println("Student not found."); continue; }
                s.displayRole();
                System.out.println("Welcome, " + s.getName() + "!");

                boolean go = true;
                while (go) {
                    System.out.println("\n--- Student Menu ---");
                    System.out.println("1. Apply for Room");
                    System.out.println("2. Check Status");
                    System.out.println("3. Logout");
                    System.out.print("Choose: ");
                    String c = sc.nextLine().trim();
                    if      (c.equals("1")) s.applyForRoom();
                    else if (c.equals("2")) s.checkStatus();
                    else if (c.equals("3")) { go = false; System.out.println("Logged out."); }
                    else System.out.println("Invalid option.");
                }

            } else if (choice.equals("2")) {
                System.out.print("Enter warden name: ");
                Warden w = wardenLogin(sc.nextLine().trim());
                if (w == null) { System.out.println("Warden not found."); continue; }
                w.displayRole();
                System.out.println("Welcome, " + w.getWardenName() + "! Managing: " + w.getManagedHostelName());

                Hostel myHostel = getHostelByName(w.getManagedHostelName());
                if (myHostel == null) { System.out.println("Hostel not found. Contact admin."); continue; }

                boolean go = true;
                while (go) {
                    System.out.println("\n--- Warden Menu ---");
                    System.out.println("1. View Empty Rooms");
                    System.out.println("2. Allot Room to Student");
                    System.out.println("3. Cancel Allotment");
                    System.out.println("4. View Allocated Students");
                    System.out.println("5. Logout");
                    System.out.print("Choose: ");
                    String c = sc.nextLine().trim();

                    if (c.equals("1")) {
                        w.viewEmptyRooms(myHostel);

                    } else if (c.equals("2")) {
                        System.out.print("Enter student roll number: ");
                        Student st = studentLogin(sc.nextLine().trim());
                        if (st == null) {
                            System.out.println("Student not found.");
                        } else if (!st.getStatus().equals("pending")) {
                            System.out.println("Student is not in pending status.");
                        } else if (!myHostel.isAvailable()) {
                            System.out.println("No rooms available.");
                        } else {
                            int freeRoom = -1;
                            for (Room r : myHostel.getRooms())
                                if (r.isAvailable()) { freeRoom = r.getRoomNumber(); break; }
                            myHostel.bookRoom(freeRoom);
                            st.setStatus("assigned");
                            st.setAllocatedRoomNumber(freeRoom);
                            st.setAllocatedHostel(myHostel.getHostelName());
                            Allocation alloc = new Allocation(st.getRollNumber(), myHostel.getHostelName(), freeRoom);
                            allocations.add(alloc);
                            alloc.printReceipt();
                        }

                    } else if (c.equals("3")) {
                        System.out.print("Enter student roll number: ");
                        Student st = studentLogin(sc.nextLine().trim());
                        if (st == null) {
                            System.out.println("Student not found.");
                        } else if (!st.getAllocatedHostel().equals(myHostel.getHostelName())) {
                            System.out.println("Student not in your hostel.");
                        } else {
                            Allocation alloc = getAllocationByRoll(st.getRollNumber());
                            if (alloc != null) allocations.remove(alloc);
                            w.cancelAllotment(st, myHostel);
                        }

                    } else if (c.equals("4")) {
                        w.viewAllocatedStudents(students, allocations);

                    } else if (c.equals("5")) {
                        go = false; System.out.println("Logged out.");
                    } else {
                        System.out.println("Invalid option.");
                    }
                }

            } else if (choice.equals("3")) {
                System.out.print("Enter admin ID  : ");
                int id;
                try { id = Integer.parseInt(sc.nextLine().trim()); }
                catch (NumberFormatException e) { System.out.println("Invalid ID."); continue; }
                System.out.print("Enter password  : ");
                if (!adminLogin(id, sc.nextLine().trim())) {
                    System.out.println("Wrong credentials.");
                    continue;
                }
                System.out.println("Welcome, Admin!");

                boolean go = true;
                while (go) {
                    System.out.println("\n--- Admin Menu ---");
                    System.out.println("1. Add Hostel");
                    System.out.println("2. Remove Hostel");
                    System.out.println("3. View All Students");
                    System.out.println("4. View All Wardens");
                    System.out.println("5. Logout");
                    System.out.print("Choose: ");
                    String c = sc.nextLine().trim();
                    if      (c.equals("1")) admin.addHostel(hostels, wardens, sc);
                    else if (c.equals("2")) admin.removeHostel(hostels, wardens, sc);
                    else if (c.equals("3")) admin.viewAllStudents(students, allocations);
                    else if (c.equals("4")) admin.viewAllWardens(wardens);
                    else if (c.equals("5")) { go = false; System.out.println("Logged out."); }
                    else System.out.println("Invalid option.");
                }

            } else if (choice.equals("4")) {
                registerStudent(sc);

            } else if (choice.equals("5")) {
                System.out.println("Goodbye!");
                running = false;

            } else {
                System.out.println("Invalid option.");
            }
        }
        sc.close();
    }
}
