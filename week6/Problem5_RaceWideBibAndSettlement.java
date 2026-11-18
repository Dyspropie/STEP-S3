package week6;

/**
 * Problem 5: Race-Wide Bib Issuance, Discount Codes & Nightly Settlement Engine
 * Shared static counter, character-by-character discount validation, overloaded payment, and null-safe batch reconciliation.
 */
public class Problem5_RaceWideBibAndSettlement {

    public static class RaceEntry {
        private static int bibCounter = 0;

        private final String bibNumber;
        private final double entryFee;
        private double balanceDue;
        private final String entryCode;

        public RaceEntry(String bibNumber, double entryFee) {
            if (bibNumber == null || bibNumber.trim().length() < 4) {
                throw new IllegalArgumentException("construction rejected");
            }
            this.bibNumber = bibNumber.trim();
            this.entryFee = entryFee;
            this.balanceDue = entryFee;
            bibCounter++;
            this.entryCode = "ENTRY-" + bibCounter;
        }

        public void pay(double amount) {
            this.balanceDue -= amount;
        }

        public void pay(double amount, String mode) {
            System.out.println("Paying via " + mode);
            pay(amount);
        }

        public double getBalanceDue() {
            return balanceDue;
        }

        public String getBibNumber() {
            return bibNumber;
        }

        public String getEntryCode() {
            return entryCode;
        }

        public static int getBibCounter() {
            return bibCounter;
        }

        public static boolean isValidDiscountCode(String code) {
            if (code == null || code.length() != 5) {
                return false;
            }

            // Must match format "M" + 3 digits + 1 uppercase letter without using regex
            if (code.charAt(0) != 'M') {
                return false;
            }

            for (int i = 1; i <= 3; i++) {
                if (!Character.isDigit(code.charAt(i))) {
                    return false;
                }
            }

            return Character.isUpperCase(code.charAt(4));
        }
    }

    public static class RelayTeamEntry extends RaceEntry {
        private final int teamSize;

        public RelayTeamEntry(String bibNumber, double entryFee, int teamSize) {
            super(bibNumber, entryFee);
            this.teamSize = teamSize;
        }

        public int getTeamSize() {
            return teamSize;
        }
    }

    public static class EliteRunnerEntry extends RaceEntry {
        private final double sponsorBonus;

        public EliteRunnerEntry(String bibNumber, double entryFee, double sponsorBonus) {
            super(bibNumber, entryFee);
            this.sponsorBonus = sponsorBonus;
        }

        public double getSponsorBonus() {
            return sponsorBonus;
        }
    }

    public static String settleNight(RaceEntry[] entries) {
        if (entries == null) {
            return "0 processed | 0 null skipped | 0 relay | 0 individual";
        }

        int processed = 0;
        int nullSkipped = 0;
        int relayCount = 0;
        int individualCount = 0;

        for (RaceEntry entry : entries) {
            if (entry == null) {
                nullSkipped++;
                continue;
            }

            processed++;
            if (entry instanceof RelayTeamEntry) {
                relayCount++;
            } else {
                individualCount++;
            }
        }

        return String.format("%d processed | %d null skipped | %d relay | %d individual",
                processed, nullSkipped, relayCount, individualCount);
    }

    public static void main(String[] args) {
        // Test Discount Code validation
        System.out.println("isValidDiscountCode(\"M123A\"): " + RaceEntry.isValidDiscountCode("M123A"));
        System.out.println("isValidDiscountCode(\"M12A\"):  " + RaceEntry.isValidDiscountCode("M12A"));
        System.out.println("isValidDiscountCode(\"X123A\"): " + RaceEntry.isValidDiscountCode("X123A"));

        // Overloaded pay
        EliteRunnerEntry elite = new EliteRunnerEntry("BIB3001", 150, 500);
        elite.pay(10, "UPI");

        RelayTeamEntry relay = new RelayTeamEntry("BIB4001", 300, 4);
        RaceEntry[] batch = {elite, null, relay};

        System.out.println(settleNight(batch));
        System.out.println("RaceEntry.getBibCounter(): " + RaceEntry.getBibCounter());
    }
}
