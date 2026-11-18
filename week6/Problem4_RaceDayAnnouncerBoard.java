package week6;

/**
 * Problem 4: The Race-Day Announcer Board
 * Polymorphic announcement reporting with StringBuilder and safe downcast using instanceof.
 */
public class Problem4_RaceDayAnnouncerBoard {

    public static String announceAll(Problem2_ThreeShapesOfRaceFamily.RaceEntry[] entries) {
        if (entries == null || entries.length == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        for (Problem2_ThreeShapesOfRaceFamily.RaceEntry entry : entries) {
            if (entry != null) {
                sb.append(entry.announce());
                if (entry instanceof Problem2_ThreeShapesOfRaceFamily.RelayTeamEntry) {
                    Problem2_ThreeShapesOfRaceFamily.RelayTeamEntry relay =
                            (Problem2_ThreeShapesOfRaceFamily.RelayTeamEntry) entry;
                    sb.append(String.format(" [Team size via downcast: %d]", relay.getTeamSize()));
                }
                sb.append(" | ");
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        Problem2_ThreeShapesOfRaceFamily.RunnerEntry runner =
                new Problem2_ThreeShapesOfRaceFamily.RunnerEntry("BIB2001", 90, "Open 10K");
        Problem2_ThreeShapesOfRaceFamily.RelayTeamEntry relay =
                new Problem2_ThreeShapesOfRaceFamily.RelayTeamEntry("BIB4001", 300, 4);

        Problem2_ThreeShapesOfRaceFamily.RaceEntry[] fleet = {runner, relay};
        System.out.println(announceAll(fleet));

        // Demonstration of runtime ClassCastException when downcasting without instanceof guard
        Problem2_ThreeShapesOfRaceFamily.RaceEntry plain =
                new Problem2_ThreeShapesOfRaceFamily.RaceEntry("BIB5001", 50);
        try {
            Problem2_ThreeShapesOfRaceFamily.RelayTeamEntry bad =
                    (Problem2_ThreeShapesOfRaceFamily.RelayTeamEntry) plain;
            System.out.println("Cast succeeded: " + bad);
        } catch (ClassCastException e) {
            System.out.println("Expected ClassCastException caught: " + e.getMessage());
        }
    }
}
