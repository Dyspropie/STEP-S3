package week3;

/**
 * Problem 5: Capstone: A Small HR + Parking Allocation Mini-System
 * Combines Employee, ParkingSlot, and CompanyEmployeeRecord into a cohesive composed structure.
 */
public class Problem5_CompanyEmployeeRecordSystem {

    public static class CompanyEmployeeRecord {
        private String name;
        private String empId;
        private Problem2_EmployeeHierarchy.Employee employee;
        private Problem3_ParkingAllocation.ParkingSlot slot;

        public static int totalRecords = 0;

        public CompanyEmployeeRecord(String name, String empId,
                                     Problem2_EmployeeHierarchy.Employee employee,
                                     Problem3_ParkingAllocation.ParkingSlot slot) {
            this.name = name;
            this.empId = empId;
            this.employee = employee;
            this.slot = slot;
            totalRecords++;
        }

        public String fullProfile() {
            double pay = 0.0;
            if (employee instanceof Problem2_EmployeeHierarchy.ManagerEmployee) {
                pay = ((Problem2_EmployeeHierarchy.ManagerEmployee) employee).effectiveSalary();
            } else if (employee instanceof Problem2_EmployeeHierarchy.InternEmployee) {
                pay = ((Problem2_EmployeeHierarchy.InternEmployee) employee).effectiveSalary();
            } else if (employee != null) {
                pay = employee.getSalary();
            }

            String slotStr = (slot != null) ? slot.getSlotNo() : "no parking assigned";
            return String.format("%s | Pay: Rs %.1f | Slot: %s", name, pay, slotStr);
        }
    }

    public static void main(String[] args) {
        Problem3_ParkingAllocation.ParkingSlot slotA1 = new Problem3_ParkingAllocation.ParkingSlot("A1", 1, 0);
        Problem3_ParkingAllocation.ParkingSlot slotA2 = new Problem3_ParkingAllocation.ParkingSlot("A2", 1, 0);

        Problem2_EmployeeHierarchy.Employee divya =
                new Problem2_EmployeeHierarchy.ManagerEmployee("E01", "Divya", 70000, 8000);
        Problem2_EmployeeHierarchy.Employee karan =
                new Problem2_EmployeeHierarchy.Employee("E02", "Karan", 40000);
        Problem2_EmployeeHierarchy.Employee meera =
                new Problem2_EmployeeHierarchy.InternEmployee("E03", "Meera", 12000, 10000);

        CompanyEmployeeRecord r1 = new CompanyEmployeeRecord("Divya", "E01", divya, slotA1);
        CompanyEmployeeRecord r2 = new CompanyEmployeeRecord("Karan", "E02", karan, slotA2);
        CompanyEmployeeRecord r3 = new CompanyEmployeeRecord("Meera", "E03", meera, null);

        System.out.println(r1.fullProfile());
        System.out.println(r2.fullProfile());
        System.out.println(r3.fullProfile());
        System.out.println("Total records: " + CompanyEmployeeRecord.totalRecords);
    }
}
