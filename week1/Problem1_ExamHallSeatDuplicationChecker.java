package week1;

/**
 * Problem 1: The Exam Hall Seat Duplication Checker
 * Detects duplicate seat numbers using nested loops and primitive arrays only.
 */
public class Problem1_ExamHallSeatDuplicationChecker {

    public static void checkDuplicateSeats(int[] seatNumbers) {
        if (seatNumbers == null || seatNumbers.length == 0) {
            System.out.println("No Duplicate Seats Found");
            return;
        }

        boolean foundDuplicate = false;
        // Keep track of duplicates we've already printed so we don't repeat them
        boolean[] printed = new boolean[seatNumbers.length];

        for (int i = 0; i < seatNumbers.length; i++) {
            if (printed[i]) continue;
            boolean isDup = false;
            for (int j = i + 1; j < seatNumbers.length; j++) {
                if (seatNumbers[i] == seatNumbers[j]) {
                    isDup = true;
                    printed[j] = true;
                }
            }
            if (isDup) {
                System.out.println("Duplicate Seat Number Found: " + seatNumbers[i]);
                foundDuplicate = true;
            }
        }

        if (!foundDuplicate) {
            System.out.println("No Duplicate Seats Found");
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Test Case 1 ---");
        int[] input1 = {101, 102, 103, 102, 105};
        checkDuplicateSeats(input1);

        System.out.println("\n--- Test Case 2 ---");
        int[] input2 = {101, 102, 103, 104, 105};
        checkDuplicateSeats(input2);
    }
}
