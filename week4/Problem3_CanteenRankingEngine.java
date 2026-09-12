package week4;

import java.util.Arrays;

/**
 * Problem 3: Canteen Trust-Score Ranking Engine
 * Implements constructor chaining, custom comparison logic, and an O(n^2) sort without built-ins.
 */
public class Problem3_CanteenRankingEngine {

    public static class Canteen implements Comparable<Canteen> {
        private String canteenCode;
        private String canteenName;
        private int trustScore;

        public Canteen(String canteenCode, String canteenName, int trustScore) {
            this.canteenCode = canteenCode;
            this.canteenName = canteenName;
            this.trustScore = trustScore;
        }

        public Canteen(String canteenCode, String canteenName) {
            this(canteenCode, canteenName, 3);
        }

        public String getCanteenCode() { return canteenCode; }
        public String getCanteenName() { return canteenName; }
        public int getTrustScore() { return trustScore; }

        @Override
        public int compareTo(Canteen other) {
            // 1. Higher trust score ranks first (descending)
            int scoreCmp = Integer.compare(other.trustScore, this.trustScore);
            if (scoreCmp != 0) return scoreCmp;

            // 2. Case-insensitive canteenCode ordering (ascending)
            int codeCmp = this.canteenCode.compareToIgnoreCase(other.canteenCode);
            if (codeCmp != 0) return codeCmp;

            // 3. Canteen name length (ascending)
            return Integer.compare(this.canteenName.length(), other.canteenName.length());
        }

        @Override
        public String toString() {
            return canteenCode;
        }
    }

    public static Canteen[] rankCanteens(Canteen[] canteens) {
        if (canteens == null || canteens.length <= 1) return canteens;

        Canteen[] arr = canteens.clone();
        // Custom O(n^2) bubble sort without using any built-in sort utility
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j].compareTo(arr[j + 1]) > 0) {
                    Canteen temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        return arr;
    }

    public static void main(String[] args) {
        Canteen[] list = {
            new Canteen("HB3-C", "Spice Junction", 3),
            new Canteen("hb1-c", "Grand Mess", 5),
            new Canteen("HB2-C", "Southern Treats") // defaults to trustScore 3
        };

        Canteen[] ranked = rankCanteens(list);
        String[] codes = new String[ranked.length];
        for (int i = 0; i < ranked.length; i++) {
            codes[i] = ranked[i].getCanteenCode();
        }
        System.out.println(Arrays.toString(codes));
    }
}
