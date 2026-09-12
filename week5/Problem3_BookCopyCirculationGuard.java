package week5;

/**
 * Problem 3: Book Copy Circulation Guard
 * Enforces encapsulation with private fields and boundary-safe checkout and checkin operations.
 */
public class Problem3_BookCopyCirculationGuard {

    public static class BookInventory {
        private final int copiesTotal;
        private int copiesAvailable;

        public BookInventory(int copiesTotal) {
            if (copiesTotal <= 0) {
                throw new IllegalArgumentException("construction rejected");
            }
            this.copiesTotal = copiesTotal;
            this.copiesAvailable = copiesTotal;
        }

        public void checkOut() {
            // Silently reject if no copies are available
            if (copiesAvailable > 0) {
                copiesAvailable--;
            }
        }

        public void checkIn() {
            // Silently reject if inventory is already full
            if (copiesAvailable < copiesTotal) {
                copiesAvailable++;
            }
        }

        public int getCopiesAvailable() {
            return copiesAvailable;
        }
    }

    public static void main(String[] args) {
        try {
            new BookInventory(0);
        } catch (Exception e) {
            System.out.println("new BookInventory(0) -> " + e.getMessage());
        }

        BookInventory b = new BookInventory(3);
        b.checkOut();
        b.checkOut();
        b.checkOut();
        b.checkOut(); // 4th checkout silently rejected
        System.out.println("After 4 checkouts from 3: " + b.getCopiesAvailable());

        b.checkIn();
        b.checkIn();
        b.checkIn();
        b.checkIn(); // 4th check-in silently rejected
        System.out.println("After 4 check-ins: " + b.getCopiesAvailable());
    }
}
