package week6;

/**
 * Problem 2: Three Shapes of One Race Family
 * Multilevel and hierarchical inheritance with polymorphic balance calculation and generation classification.
 */
public class Problem2_ThreeShapesOfRaceFamily {

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

        public String announce() {
            return String.format("Race Entry | Bib: %s | Balance: %.1f", bibNumber, balanceDue);
        }
    }

    // Single-inheritance
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
        public String announce() {
            return String.format("Runner Entry | Bib: %s | Category: %s | Balance: %.1f",
                    getBibNumber(), category, getBalanceDue());
        }
    }

    // Multilevel inheritance: EliteRunnerEntry -> RunnerEntry -> RaceEntry
    public static class EliteRunnerEntry extends RunnerEntry {
        private final double sponsorBonus;

        public EliteRunnerEntry(String bibNumber, double entryFee, String category, double sponsorBonus) {
            super(bibNumber, entryFee, category);
            this.sponsorBonus = sponsorBonus;
        }

        public double getSponsorBonus() {
            return sponsorBonus;
        }

        @Override
        public String announce() {
            return String.format("Elite Runner | Bib: %s | Category: %s | Sponsor Bonus: %.1f | Balance: %.1f",
                    getBibNumber(), getCategory(), sponsorBonus, getBalanceDue());
        }
    }

    // Hierarchical sibling: RelayTeamEntry -> RaceEntry
    public static class RelayTeamEntry extends RaceEntry {
        private final int teamSize;

        public RelayTeamEntry(String bibNumber, double entryFee, int teamSize) {
            super(bibNumber, entryFee);
            this.teamSize = teamSize;
        }

        public int getTeamSize() {
            return teamSize;
        }

        @Override
        public String announce() {
            return String.format("Relay Team | Bib: %s | Team Size: %d | Balance: %.1f",
                    getBibNumber(), teamSize, getBalanceDue());
        }
    }

    public static String classifyGeneration(RaceEntry entry) {
        if (entry instanceof EliteRunnerEntry) {
            return "Multilevel descendant (3 generations deep)";
        } else if (entry instanceof RelayTeamEntry) {
            return "Hierarchical sibling (independent branch)";
        } else if (entry instanceof RunnerEntry) {
            return "Single-inheritance specialization";
        } else {
            return "Base root entry";
        }
    }

    public static double getTotalBalanceDue(RaceEntry[] entries) {
        if (entries == null) return 0.0;
        double total = 0.0;
        for (RaceEntry entry : entries) {
            if (entry != null) {
                total += entry.getBalanceDue();
            }
        }
        return total;
    }

    public static void main(String[] args) {
        RunnerEntry runner = new RunnerEntry("BIB2001", 80, "Open 10K");
        EliteRunnerEntry elite = new EliteRunnerEntry("BIB3001", 150, "Elite Full Marathon", 500);
        RelayTeamEntry relay = new RelayTeamEntry("BIB4001", 300, 4);

        System.out.println(runner.announce());
        System.out.println(elite.announce());
        System.out.println(relay.announce());

        System.out.println(classifyGeneration(elite));
        System.out.println(classifyGeneration(relay));

        RaceEntry[] entries = {runner, elite, relay};
        System.out.println("Total Balance Due: " + getTotalBalanceDue(entries));
    }
}
