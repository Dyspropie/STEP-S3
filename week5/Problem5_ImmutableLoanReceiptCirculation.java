package week5;

/**
 * Problem 5: Immutable Loan Receipt & Nightly Circulation Ledger
 * Demonstrates immutability with defensive copying, wither pattern, and instanceof batch reconciliation.
 */
public class Problem5_ImmutableLoanReceiptCirculation {

    public static class LoanReceipt {
        private final String memberId;
        private final String[] bookIds;

        public LoanReceipt(String memberId, String[] bookIds) {
            if (bookIds == null) {
                throw new IllegalArgumentException("construction rejected");
            }

            // Validate every book ID must match "BK-" followed by exactly 3 digits
            for (String bid : bookIds) {
                if (bid == null || !bid.matches("^BK-\\d{3}$")) {
                    throw new IllegalArgumentException("construction rejected");
                }
            }

            this.memberId = memberId;
            // Defensive copy on the way in
            this.bookIds = bookIds.clone();
        }

        public String getMemberId() {
            return memberId;
        }

        // Defensive copy on the way out
        public String[] getBookIds() {
            return bookIds.clone();
        }

        public LoanReceipt withCorrectedBookId(int index, String newId) {
            if (index < 0 || index >= bookIds.length) {
                throw new IndexOutOfBoundsException("Invalid book index");
            }
            if (newId == null || !newId.matches("^BK-\\d{3}$")) {
                throw new IllegalArgumentException("Invalid replacement book ID format");
            }
            String[] updated = bookIds.clone();
            updated[index] = newId;
            return new LoanReceipt(this.memberId, updated);
        }
    }

    public static class ReferenceOnlyLoanReceipt extends LoanReceipt {
        private final String roomNumber;

        public ReferenceOnlyLoanReceipt(String memberId, String[] bookIds, String roomNumber) {
            super(memberId, bookIds);
            this.roomNumber = roomNumber;
        }

        public String getRoomNumber() {
            return roomNumber;
        }
    }

    // Static block for one-time shared setup
    private static final int INITIAL_LEDGER_CODE;
    static {
        INITIAL_LEDGER_CODE = 2026;
    }

    public static String processNightlyCirculation(LoanReceipt[] receipts) {
        if (receipts == null) {
            return "0 processed | 0 null skipped | 0 reference-only | 0 regular";
        }

        int processed = 0;
        int nullSkipped = 0;
        int referenceOnlyCount = 0;
        int regularCount = 0;

        for (LoanReceipt receipt : receipts) {
            if (receipt == null) {
                nullSkipped++;
                continue;
            }

            processed++;
            if (receipt instanceof ReferenceOnlyLoanReceipt) {
                referenceOnlyCount++;
            } else {
                regularCount++;
            }
        }

        return String.format("%d processed | %d null skipped | %d reference-only | %d regular",
                processed, nullSkipped, referenceOnlyCount, regularCount);
    }

    public static void main(String[] args) {
        // Test 1: Construction rejection on invalid book format
        try {
            new LoanReceipt("LIB-8841", new String[]{"BK-100", "bad"});
        } catch (Exception e) {
            System.out.println("Construction invalid ID -> " + e.getMessage());
        }

        // Test 2: Defensive copying verification
        LoanReceipt r = new LoanReceipt("LIB-8841", new String[]{"BK-100", "BK-101"});
        String[] ids = r.getBookIds();
        ids[0] = "HACKED";
        System.out.println("Defensive copy check: " + r.getBookIds()[0]);

        // Test 3: Batch reconciliation
        LoanReceipt[] batch = {
            new ReferenceOnlyLoanReceipt("LIB-001", new String[]{"BK-200"}, "Reading Room 3"),
            null,
            new LoanReceipt("LIB-002", new String[]{"BK-201"})
        };
        System.out.println(processNightlyCirculation(batch));
    }
}
