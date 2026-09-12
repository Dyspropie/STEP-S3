package week1;

/**
 * Problem 2: The Typing Speed Test Accuracy Checker
 * Compares two strings character-by-character and calculates typing accuracy.
 */
public class Problem2_TypingSpeedAccuracyChecker {

    public static void checkTypingAccuracy(String original, String typed) {
        if (original == null || typed == null || original.length() != typed.length()) {
            throw new IllegalArgumentException("Original and typed strings must be non-null and equal length.");
        }

        int total = original.length();
        int matched = 0;
        int firstMismatchPos = -1;
        char origChar = ' ';
        char typedChar = ' ';

        for (int i = 0; i < total; i++) {
            char o = original.charAt(i);
            char t = typed.charAt(i);
            if (o == t) {
                matched++;
            } else if (firstMismatchPos == -1) {
                firstMismatchPos = i + 1; // 1-based index
                origChar = o;
                typedChar = t;
            }
        }

        double accuracy = total == 0 ? 100.0 : ((double) matched / total) * 100.0;

        if (firstMismatchPos != -1) {
            System.out.printf("Matched: %d/%d | Accuracy: %.2f%% | First Mismatch at position %d ('%c' vs '%c')%n",
                    matched, total, accuracy, firstMismatchPos, origChar, typedChar);
        } else {
            System.out.printf("Matched: %d/%d | Accuracy: %.2f%% | No Mismatches%n",
                    matched, total, accuracy);
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Test Case 1 ---");
        checkTypingAccuracy("hello world", "hello worlt");

        System.out.println("\n--- Test Case 2 ---");
        checkTypingAccuracy("coding", "coding");
    }
}
