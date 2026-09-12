package week4;

/**
 * Problem 1: Ghost Order Validator
 * Validates food order fields at construction time and tracks delivery state.
 */
public class Problem1_GhostOrderValidator {

    public static class FoodOrder {
        private final String studentName;
        private final String dishName;
        private boolean delivered;

        // No usable no-argument constructor
        private FoodOrder() {
            throw new UnsupportedOperationException("No-argument constructor is not allowed");
        }

        public FoodOrder(String studentName, String dishName) {
            if (studentName == null || studentName.trim().isEmpty()) {
                throw new IllegalArgumentException("Student name cannot be null, empty, or whitespace");
            }
            if (dishName == null || dishName.trim().isEmpty()) {
                throw new IllegalArgumentException("Dish name cannot be null, empty, or whitespace");
            }
            this.studentName = studentName.trim();
            this.dishName = dishName.trim();
            this.delivered = false;
        }

        public void markDelivered() {
            if (!delivered) {
                delivered = true;
                System.out.println("Order delivered to " + studentName + " (" + dishName + ").");
            } else {
                System.out.println("Warning: Order for " + studentName + " was already marked delivered!");
            }
        }
    }

    public static void processBatch(String[][] rawOrders) {
        if (rawOrders == null) {
            System.out.println("Valid: 0 | Rejected: 0");
            return;
        }

        int valid = 0;
        int rejected = 0;

        for (String[] attempt : rawOrders) {
            if (attempt == null || attempt.length < 2) {
                rejected++;
                continue;
            }
            try {
                new FoodOrder(attempt[0], attempt[1]);
                valid++;
            } catch (Exception e) {
                rejected++;
            }
        }

        System.out.printf("Valid: %d | Rejected: %d%n", valid, rejected);
    }

    public static void main(String[] args) {
        String[][] rawOrders = {
            {"Ravi", "Paneer Butter Masala"},
            {"", "Chole Bhature"},
            {"Meera", " "},
            {"Divya", "Veg Biryani"}
        };
        processBatch(rawOrders);
    }
}
