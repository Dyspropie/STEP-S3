package week6;

import java.util.Arrays;

/**
 * Problem 3: The Late-Withdrawal Penalty Override & Audit Trail
 * Overrides late fee calculation, calls super.applyLateFee with doubled penalty, and protects internal history.
 */
public class Problem3_LateWithdrawalPenaltyAudit {

    public static class RaceEntry {
        private final String bibNumber;
        private final double entryFee;
        private double balanceDue;
        private final double[] lateFeeHistory;
        private int lateFeeCount;

        public RaceEntry(String bibNumber, double entryFee) {
            if (bibNumber == null || bibNumber.trim().length() < 4) {
                throw new IllegalArgumentException("construction rejected");
            }
            this.bibNumber = bibNumber.trim();
            this.entryFee = entryFee;
            this.balanceDue = entryFee;
            this.lateFeeHistory = new double[10];
            this.lateFeeCount = 0;
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

        protected void applyLateFee(double amount) {
            this.balanceDue += amount;
            if (lateFeeCount < lateFeeHistory.length) {
                lateFeeHistory[lateFeeCount++] = amount;
            }
        }

        // Returns a defensive copy so internal audit trail cannot be modified
        public double[] getLateFeeHistory() {
            double[] copy = new double[lateFeeCount];
            System.arraycopy(lateFeeHistory, 0, copy, 0, lateFeeCount);
            return copy;
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

        @Override
        public void applyLateFee(double amount) {
            // Doubles the penalty and forwards to parent's recording & deduction logic
            super.applyLateFee(amount * 2);
        }
    }

    public static void main(String[] args) {
        RunnerEntry r = new RunnerEntry("BIB2001", 80, "Open 10K");
        r.pay(30);
        r.applyLateFee(20);
        System.out.println("Balance due: " + r.getBalanceDue());

        double[] history = r.getLateFeeHistory();
        System.out.println("History: " + Arrays.toString(history));

        // Attempt to tamper with the returned array
        history[0] = 999;
        System.out.println("History after tampering attempt: " + Arrays.toString(r.getLateFeeHistory()));
    }
}
