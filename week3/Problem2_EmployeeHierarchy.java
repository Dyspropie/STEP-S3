package week3;

/**
 * Problem 2: Extending Employee Without Touching It
 * Demonstrates inheritance and runtime polymorphism using instanceof.
 */
public class Problem2_EmployeeHierarchy {

    public static class Employee {
        private String empId;
        private String empName;
        private double salary;

        public Employee(String empId, String empName, double salary) {
            this.empId = empId;
            this.empName = empName;
            this.salary = salary;
        }

        public String getEmpId() { return empId; }
        public String getEmpName() { return empName; }
        public double getSalary() { return salary; }
    }

    public static class ManagerEmployee extends Employee {
        private double teamBonus;

        public ManagerEmployee(String empId, String empName, double salary, double teamBonus) {
            super(empId, empName, salary);
            this.teamBonus = teamBonus;
        }

        public double effectiveSalary() {
            return getSalary() + teamBonus;
        }
    }

    public static class InternEmployee extends Employee {
        private double stipendCap;

        public InternEmployee(String empId, String empName, double salary, double stipendCap) {
            super(empId, empName, salary);
            this.stipendCap = stipendCap;
        }

        public double effectiveSalary() {
            return Math.min(getSalary(), stipendCap);
        }
    }

    public static void main(String[] args) {
        Employee plain = new Employee("EMP001", "Plain Staff", 40000);
        Employee manager = new ManagerEmployee("MGR001", "Manager Staff", 70000, 8000);
        Employee intern = new InternEmployee("INT001", "Intern Staff", 12000, 10000);

        Employee[] staff = {plain, manager, intern};

        for (Employee e : staff) {
            if (e instanceof ManagerEmployee) {
                ManagerEmployee mgr = (ManagerEmployee) e;
                System.out.println("Manager effective pay: Rs " + mgr.effectiveSalary());
            } else if (e instanceof InternEmployee) {
                InternEmployee internEmp = (InternEmployee) e;
                System.out.println("Intern effective pay: Rs " + internEmp.effectiveSalary());
            } else {
                System.out.println("Plain employee pay: Rs " + e.getSalary());
            }
        }
    }
}
