package week4;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Problem 2: ASAP or Scheduled — Delivery Slot Booking
 * Implements constructor chaining with this(...) and flags peak hour slots.
 */
public class Problem2_DeliverySlotBooking {

    public static class DeliverySlot {
        private final String orderId;
        private final String timeSlot;

        // "ASAP" is only typed once in the entire class
        private static final String DEFAULT_SLOT = "ASAP";

        private static final Set<String> PEAK_HOURS = new HashSet<>(Arrays.asList(
            "12:00-13:00", "13:00-14:00", "19:00-20:00", "20:00-21:00"
        ));

        public DeliverySlot(String orderId, String timeSlot) {
            this.orderId = orderId;
            this.timeSlot = timeSlot;
        }

        public DeliverySlot(String orderId) {
            this(orderId, DEFAULT_SLOT);
        }

        public boolean isPeakHour() {
            return PEAK_HOURS.contains(timeSlot);
        }

        public String getTimeSlot() {
            return timeSlot;
        }
    }

    public static void main(String[] args) {
        DeliverySlot slot1 = new DeliverySlot("ORD101", "13:00-14:00");
        System.out.println("slot1 isPeakHour: " + slot1.isPeakHour());

        DeliverySlot slot2 = new DeliverySlot("ORD102");
        System.out.println("slot2 (defaults to \"" + slot2.getTimeSlot() + "\") isPeakHour: " + slot2.isPeakHour());
    }
}
