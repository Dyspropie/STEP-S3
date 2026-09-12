package week3;

/**
 * Problem 3: Object References, Null Safety, and a Mutating Method
 * Prevents NullPointerExceptions and demonstrates Java reference passing.
 */
public class Problem3_ParkingAllocation {

    public static class ParkingSlot {
        private String slotNo;
        private int capacity;
        private int occupiedCount;

        public ParkingSlot(String slotNo, int capacity, int occupiedCount) {
            this.slotNo = slotNo;
            this.capacity = capacity;
            this.occupiedCount = occupiedCount;
        }

        public String getSlotNo() { return slotNo; }
        public int getCapacity() { return capacity; }
        public int getOccupiedCount() { return occupiedCount; }

        public boolean allot(String vehicleNo) {
            if (occupiedCount < capacity) {
                occupiedCount++;
                return true;
            }
            return false;
        }
    }

    public static ParkingSlot findAvailableSlot(ParkingSlot[] slots) {
        if (slots == null) return null;
        for (ParkingSlot slot : slots) {
            if (slot != null && slot.getOccupiedCount() < slot.getCapacity()) {
                return slot;
            }
        }
        return null;
    }

    /*
     * JUSTIFICATION COMMENT:
     * In Java, object references are passed by value. When the ParkingSlot[] array is passed
     * to findAvailableSlot or safeAllot, the memory address referencing the array and its
     * constituent objects is copied, NOT the objects themselves. Both the caller and the method
     * point to the identical ParkingSlot instances in heap memory, so mutations (such as allot)
     * are reflected on the original objects without copying overhead.
     */
    public static void safeAllot(ParkingSlot[] slots, String vehicleNo) {
        ParkingSlot slot = findAvailableSlot(slots);
        if (slot != null) {
            slot.allot(vehicleNo);
            System.out.println(vehicleNo + " allotted to slot " + slot.getSlotNo());
        } else {
            System.out.println("No slots available for " + vehicleNo);
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Test 1: Available Slot ---");
        ParkingSlot[] slots1 = {
            new ParkingSlot("A1", 4, 3),
            new ParkingSlot("A2", 5, 5)
        };
        safeAllot(slots1, "TN09AB1234");

        System.out.println("\n--- Test 2: All Slots Full ---");
        ParkingSlot[] slots2 = {
            new ParkingSlot("A1", 4, 4),
            new ParkingSlot("A2", 5, 5)
        };
        safeAllot(slots2, "TN09AB1234");
    }
}
