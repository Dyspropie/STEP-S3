package week2;

/**
 * Problem 1: ATM PIN Length Validator
 * Validates that an entered PIN string is exactly 4 characters long.
 */
public class Problem1_AtmPinValidator {

    public static void checkPinLength(String pin) {
        if (pin != null && pin.length() == 4) {
            System.out.println("PIN length OK.");
        } else {
            System.out.println("Invalid PIN — must be exactly 4 digits.");
        }
    }

    public static void main(String[] args) {
        checkPinLength("482");
        checkPinLength("4820");
    }
}
