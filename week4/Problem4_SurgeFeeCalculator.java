package week4;

/**
 * Problem 4: Exam-Week Surge Fee Calculator
 * Calculates tiered surge fees with an immutable, final class and method.
 */
public class Problem4_SurgeFeeCalculator {

    public static final class SurgeFeeCalculator {
        private final double minimumSurgePercent;

        public SurgeFeeCalculator(double minimumSurgePercent) {
            if (minimumSurgePercent < 0) {
                throw new IllegalArgumentException("Minimum surge percent cannot be negative");
            }
            this.minimumSurgePercent = minimumSurgePercent;
        }

        public final double calculateSurgeFee(double orderValue, int delayMinutes) {
            if (orderValue < 0 || delayMinutes < 0) {
                throw new IllegalArgumentException("Negative orderValue or delayMinutes rejected");
            }

            // Delay of 0 minutes must never trigger any surge fee or floor
            if (delayMinutes == 0) {
                return 0.0;
            }

            // Bracket 1: minutes 1-5 (up to 5 min) @ 0.5% per minute
            int b1 = Math.min(delayMinutes, 5);

            // Bracket 2: minutes 6-15 (up to 10 min) @ 1.0% per minute
            int b2 = Math.max(0, Math.min(delayMinutes - 5, 10));

            // Bracket 3: minutes 16+ @ 2.0% per minute
            int b3 = Math.max(0, delayMinutes - 15);

            double tieredFee = (b1 * 0.005 + b2 * 0.01 + b3 * 0.02) * orderValue;
            double floorFee = (minimumSurgePercent / 100.0) * orderValue;

            return Math.max(tieredFee, floorFee);
        }
    }

    public static void main(String[] args) {
        SurgeFeeCalculator calc = new SurgeFeeCalculator(1.0); // 1.0% floor

        System.out.println("orderValue = 500, delayMinutes = 0  -> Rs " + calc.calculateSurgeFee(500, 0));
        System.out.println("orderValue = 500, delayMinutes = 1  -> Rs " + calc.calculateSurgeFee(500, 1));
        System.out.println("orderValue = 500, delayMinutes = 16 -> Rs " + calc.calculateSurgeFee(500, 16));
    }
}
