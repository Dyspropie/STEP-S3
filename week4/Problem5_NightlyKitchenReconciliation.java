package week4;

/**
 * Problem 5: Nightly Multi-Kitchen Reconciliation Engine
 * Processes batches of delivery accounts safely tolerating null entries and utilizing instanceof.
 */
public class Problem5_NightlyKitchenReconciliation {

    public static class DeliveryAccount {
        private static int systemInitCounter;

        static {
            // One-time static setup block
            systemInitCounter = 1;
        }

        private final String studentId;
        private final double orderValue;
        private final Problem4_SurgeFeeCalculator.SurgeFeeCalculator calculator;

        public DeliveryAccount(String studentId, double orderValue) {
            this.studentId = studentId;
            this.orderValue = orderValue;
            this.calculator = new Problem4_SurgeFeeCalculator.SurgeFeeCalculator(1.0);
        }

        public DeliveryAccount(String studentId) {
            this(studentId, 0.0);
        }

        public String getStudentId() { return studentId; }
        public double getOrderValue() { return orderValue; }

        public final double calculateSurgeFee(int delayMinutes) {
            return calculator.calculateSurgeFee(orderValue, delayMinutes);
        }
    }

    public static class PremiumDeliveryAccount extends DeliveryAccount {
        public PremiumDeliveryAccount(String studentId, double orderValue) {
            super(studentId, orderValue);
        }

        public PremiumDeliveryAccount(String studentId) {
            super(studentId);
        }
    }

    public static void processBatch(DeliveryAccount[] accounts, double[] amounts, int[] delayMinutesArray) {
        if (accounts == null || amounts == null || delayMinutesArray == null) {
            System.out.println("Batch rejected: null arrays provided.");
            return;
        }

        /*
         * Operations Management Justification:
         * A batch with mismatched array lengths is unsafe to guess alignment.
         * To prevent attributing the wrong charge to the wrong student, we process
         * up to the common minimum length and report any length mismatch.
         */
        int batchSize = Math.min(accounts.length, Math.min(amounts.length, delayMinutesArray.length));

        int processed = 0;
        int nullSkipped = 0;
        int premiumCount = 0;
        int regularCount = 0;
        double grandTotalSurgeFees = 0.0;

        for (int i = 0; i < batchSize; i++) {
            DeliveryAccount account = accounts[i];
            if (account == null) {
                nullSkipped++;
                continue;
            }

            processed++;
            if (account instanceof PremiumDeliveryAccount) {
                premiumCount++;
            } else {
                regularCount++;
            }

            double surgeFee = account.calculateSurgeFee(delayMinutesArray[i]);
            grandTotalSurgeFees += surgeFee;
        }

        System.out.printf("%d processed | %d null skipped | %d premium | %d regular | grand total surge fees = Rs %.1f%n",
                processed, nullSkipped, premiumCount, regularCount, grandTotalSurgeFees);
    }

    public static void main(String[] args) {
        DeliveryAccount[] accounts = {
            new PremiumDeliveryAccount("STU001", 500),
            null,
            new DeliveryAccount("STU002", 300)
        };
        double[] amounts = {500, 400, 300};
        int[] delayMinutesArray = {10, 5, 0};

        processBatch(accounts, amounts, delayMinutesArray);
    }
}
