package week3;

/**
 * Problem 1: From Procedural Mess to a Working Library Fine System
 * Implements BookIssue with instance methods for individual fines and static aggregation.
 */
public class Problem1_LibraryFineSystem {

    public static class BookIssue {
        private String title;
        private String borrowerName;
        private int daysOverdue;

        public BookIssue(String title, String borrowerName, int daysOverdue) {
            this.title = title;
            this.borrowerName = borrowerName;
            this.daysOverdue = daysOverdue;
        }

        public String getTitle() {
            return title;
        }

        public int getDaysOverdue() {
            return daysOverdue;
        }

        public double fineAmount() {
            return daysOverdue > 0 ? daysOverdue * 5.0 : 0.0;
        }

        public boolean isSeverelyOverdue() {
            return daysOverdue > 14;
        }

        /*
         * JUSTIFICATION COMMENT:
         * totalFineCollected is defined as static because it operates over an aggregate collection
         * (an array of BookIssue objects) to compute a library-wide total fine. This utility belongs
         * to the BookIssue concept/class as a whole, rather than reflecting the state or behavior
         * of any single individual book.
         * In contrast, fineAmount() is an instance method because it relies entirely on the internal
         * state (daysOverdue) of a specific BookIssue instance.
         */
        public static double totalFineCollected(BookIssue[] issues) {
            if (issues == null) return 0.0;
            double total = 0.0;
            for (BookIssue issue : issues) {
                if (issue != null) {
                    total += issue.fineAmount();
                }
            }
            return total;
        }
    }

    public static void main(String[] args) {
        BookIssue[] issues = {
            new BookIssue("Clean Code", "Student 1", 18),
            new BookIssue("Effective Java", "Student 2", 5),
            new BookIssue("Refactoring", "Student 3", 0),
            new BookIssue("DSA Handbook", "Student 4", 21),
            new BookIssue("Design Patterns", "Student 5", 9)
        };

        for (BookIssue issue : issues) {
            String status = issue.isSeverelyOverdue() ? "Severely overdue" : "OK";
            System.out.println(issue.getTitle() + " - " + issue.getDaysOverdue() + " days - " + status);
        }

        double totalFine = BookIssue.totalFineCollected(issues);
        System.out.println("Total fine collected: Rs " + totalFine);
    }
}
