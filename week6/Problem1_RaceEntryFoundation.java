package week6;

/**
 * Problem 1: Race Entry Foundation & Batch Bib Validator
 * Establishes RaceEntry base class and RunnerEntry subclass with constructor-level validation.
 */
public class Problem1_RaceEntryFoundation {

    public static class RaceEntry {
        private final String bibNumber;
        private final double entryFee;
        private double balanceDue;

        public RaceEntry(String bibNumber, double entryFee) {
            if (bibNumber == null || bibNumber.trim().length() < 4) {
                throw new IllegalArgumentException("construction rejected");
            }
            this.bibNumber = bibNumber.trim();
            this.entryFee = entryFee;
            this.balanceDue = entryFee;
        }

        public void pay(double amount) {
            this.balanceDue -= amount;
        }

        public double getBalanceDue() {
            return balanceDue;
        }

        public String getBibNumber() {
            return bibNumber;
        }

        public double getEntryFee() {
            return entryFee;
        }

        protected void setBalanceDue(double balanceDue) {
            this.balanceDue = balanceDue;
        }
    }

    public static class RunnerEntry extends RaceEntry {
        private final String category;

        public RunnerEntry(String bibNumber, double entryFee, String category) {
            super(bibNumber, entryFee);
            this.category = category;
        }

        public String getCategory() {
            return category;
        }
    }

    public static String registerBatch(String[] bibNumbers, double entryFee) {
        if (bibNumbers == null) {
            return "Registered: 0 | Rejected: 0";
        }

        int registered = 0;
        int rejected = 0;

        for (String bib : bibNumbers) {
            try {
                new RaceEntry(bib, entryFee);
                registered++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        return "Registered: " + registered + " | Rejected: " + rejected;
    }

    public static void main(String[] args) {
        // Test 1: Rejection of short bib
        try {
            new RaceEntry("B1", 50);
        } catch (Exception e) {
            System.out.println("new RaceEntry(\"B1\", 50) -> " + e.getMessage());
        }

        // Test 2: RunnerEntry payment & balance due
        RunnerEntry r = new RunnerEntry("BIB2001", 80, "Open 10K");
        r.pay(30);
        System.out.println("RunnerEntry balance due: " + r.getBalanceDue());

        // Test 3: Batch registration
        String[] batch = {"BIB1", "B1", "BIB2"};
        System.out.println(registerBatch(batch, 80));
    }
}
