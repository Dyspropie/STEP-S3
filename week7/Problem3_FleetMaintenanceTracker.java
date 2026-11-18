package week7;

/**
 * Problem 3: Fleet Maintenance Tracker
 * Abstract ServiceableVehicle with encapsulated mileage, Insurable interface, and 3-level hierarchy.
 */
public class Problem3_FleetMaintenanceTracker {

    public static abstract class ServiceableVehicle {
        private double mileage = 0.0;
        private final String assetTag;

        public ServiceableVehicle(String assetTag) {
            this.assetTag = assetTag;
        }

        public abstract String performMaintenance();

        public double getMileage() {
            return mileage;
        }

        public void addMileage(double km) {
            if (km < 0) {
                throw new IllegalArgumentException("Mileage cannot be negative");
            }
            this.mileage += km;
        }

        public String getAssetTag() {
            return assetTag;
        }
    }

    public interface Insurable {
        String getInsuranceInfo();
    }

    public static class Forklift extends ServiceableVehicle implements Insurable {
        public Forklift(String assetTag) {
            super(assetTag);
        }

        @Override
        public String performMaintenance() {
            return "Forklift " + getAssetTag() + ": hydraulic and fork inspection complete";
        }

        @Override
        public String getInsuranceInfo() {
            return "Insured under fleet policy - Asset " + getAssetTag();
        }
    }

    // Multilevel hierarchy: HeavyDutyForklift -> Forklift -> ServiceableVehicle
    public static class HeavyDutyForklift extends Forklift {
        public HeavyDutyForklift(String assetTag) {
            super(assetTag);
        }

        @Override
        public String performMaintenance() {
            return super.performMaintenance() + " | high-pressure hydraulic check complete";
        }
    }

    public static String getInsuranceIfApplicable(ServiceableVehicle v) {
        if (v instanceof Insurable) {
            return ((Insurable) v).getInsuranceInfo();
        }
        return "No insurance record exists";
    }

    public static void main(String[] args) {
        Forklift f = new Forklift("FL-22");
        f.addMileage(120);
        System.out.println("Forklift Mileage: " + f.getMileage());
        System.out.println(f.performMaintenance());

        HeavyDutyForklift hd = new HeavyDutyForklift("HD-9");
        System.out.println(hd.performMaintenance());

        System.out.println(getInsuranceIfApplicable(f));
    }
}
