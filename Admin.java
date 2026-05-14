import java.util.List;
import java.util.Scanner;

public class Admin {

    private int    adminId;
    private String name;
    private String password;

    public Admin(int adminId, String name, String password) {
        this.adminId  = adminId;
        this.name     = name;
        this.password = password;
    }

    public void addHostel(List<Hostel> hostels, List<Warden> wardens, Scanner sc) {
        System.out.print("Enter hostel name: ");
        String hName = sc.nextLine().trim();
        System.out.print("Enter hostel address: ");
        String hAddr = sc.nextLine().trim();

        for (Hostel h : hostels) {
            if (h.getHostelName().equalsIgnoreCase(hName)) {
                System.out.println("Hostel already exists.");
                return;
            }
        }

        hostels.add(new Hostel(hName, hAddr));
        String newWardenName = "warden" + (wardens.size() + 1);
        wardens.add(new Warden(newWardenName, hName));
        System.out.println("Hostel added. Warden '" + newWardenName + "' created.");
    }

    public void removeHostel(List<Hostel> hostels, List<Warden> wardens, Scanner sc) {
        System.out.print("Enter hostel name to remove: ");
        String hName = sc.nextLine().trim();

        Hostel toRemove = null;
        for (Hostel h : hostels) {
            if (h.getHostelName().equalsIgnoreCase(hName)) { toRemove = h; break; }
        }
        if (toRemove == null) { System.out.println("Hostel not found."); return; }
        hostels.remove(toRemove);

        Warden wToRemove = null;
        for (Warden w : wardens) {
            if (w.getManagedHostelName().equalsIgnoreCase(hName)) { wToRemove = w; break; }
        }
        if (wToRemove != null) wardens.remove(wToRemove);
        System.out.println("Hostel '" + hName + "' removed.");
    }

    public void viewAllStudents(List<Student> students, List<Allocation> allocations) {
        System.out.println("--- All Students ---");
        if (students.isEmpty()) { System.out.println("  No students registered."); return; }
        for (Student s : students) System.out.println("  " + s);
    }

    public void viewAllWardens(List<Warden> wardens) {
        System.out.println("--- All Wardens ---");
        for (Warden w : wardens)
            System.out.println("  " + w.getWardenName() + " -> " + w.getManagedHostelName());
    }
}
