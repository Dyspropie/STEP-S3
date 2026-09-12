package week3;

/**
 * Problem 4: Designing the Instance/Static Boundary for a Library Membership System
 * Demonstrates the hazard of improperly using static fields and fixes it with proper boundaries.
 */
public class Problem4_LibraryMembershipBoundary {

    /*
     * Broken version where every field is incorrectly marked static.
     */
    public static class BrokenLibraryMember {
        public static String name;
        public static String memberId;
        public static int booksIssued;

        /*
         * EXPLANATION FOR EACH FIELD:
         * 1. name: Marking name static means there is only one shared name for the entire class.
         *    Creating a second member silently overwrites the first member's name.
         * 2. memberId: Marking memberId static means every member shares the same identifier,
         *    destroying uniqueness and traceability.
         * 3. booksIssued: Marking booksIssued static means every book checkout is pooled together,
         *    corrupting individual member quotas and loan tracking.
         */
        public BrokenLibraryMember(String name, String memberId, int booksIssued) {
            BrokenLibraryMember.name = name;
            BrokenLibraryMember.memberId = memberId;
            BrokenLibraryMember.booksIssued = booksIssued;
        }
    }

    /*
     * Redesigned correct version with proper instance/static boundary.
     */
    public static class LibraryMember {
        private String name;
        private String memberId;
        private int booksIssued;

        private static String libraryName = "City Central Library";
        private static int memberCount = 1000;

        public LibraryMember(String name) {
            this.name = name;
            memberCount++;
            this.memberId = "LM-" + memberCount;
            this.booksIssued = 0;
        }

        public void printMemberCard() {
            System.out.println(name + " | " + memberId);
        }

        public static void printTotalMembers() {
            System.out.println("Total members: " + (memberCount - 1000));
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Broken Version (Demonstrating the bug) ---");
        BrokenLibraryMember m1 = new BrokenLibraryMember("Aditi", "LM-001", 2);
        BrokenLibraryMember m2 = new BrokenLibraryMember("Rohan", "LM-002", 1);
        System.out.println(BrokenLibraryMember.name);
        System.out.println(BrokenLibraryMember.name);
        System.out.println("(Aditi's data was overwritten — both members now show \"Rohan\")");

        System.out.println("\n--- Fixed Version ---");
        // Reset counter base for clean run
        LibraryMember fixed1 = new LibraryMember("Aditi");
        LibraryMember fixed2 = new LibraryMember("Rohan");
        fixed1.printMemberCard();
        fixed2.printMemberCard();
        LibraryMember.printTotalMembers();
    }
}
