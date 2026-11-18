package week7;

/**
 * Problem 2: One-Click Data Export
 * Shared Exportable interface implemented by unrelated classes with a single shared export counter.
 */
public class Problem2_OneClickDataExport {

    public interface Exportable {
        String exportData();
    }

    private static int totalExports = 0;

    public static synchronized void recordExport() {
        totalExports++;
    }

    public static int getTotalExports() {
        return totalExports;
    }

    public static class ReportGenerator implements Exportable {
        private final String reportName;

        public ReportGenerator(String reportName) {
            if (reportName == null || reportName.trim().isEmpty()) {
                throw new IllegalArgumentException("Report name cannot be blank");
            }
            this.reportName = reportName.trim();
        }

        @Override
        public String exportData() {
            recordExport();
            return "Exported report: " + reportName;
        }
    }

    public static class UserProfile implements Exportable {
        private final String username;

        public UserProfile(String username) {
            if (username == null || username.trim().isEmpty()) {
                throw new IllegalArgumentException("Username cannot be blank");
            }
            this.username = username.trim();
        }

        @Override
        public String exportData() {
            recordExport();
            return "Exported profile: " + username;
        }
    }

    public static void exportAll(Exportable[] items) {
        if (items == null) return;
        for (Exportable item : items) {
            if (item != null) {
                System.out.println(item.exportData());
            }
        }
    }

    public static void main(String[] args) {
        ReportGenerator r = new ReportGenerator("Sales Q1");
        UserProfile u = new UserProfile("jane_doe");

        Exportable ref = r; // upcasting to interface type
        exportAll(new Exportable[]{ref, u});

        System.out.println("Total Exports: " + getTotalExports());
    }
}
