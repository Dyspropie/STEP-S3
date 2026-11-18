package week7;

/**
 * Problem 5: Connected Home Control Panel
 * Interfaces RemoteControllable & EnergyTrackable, abstract HomeDevice, hierarchical siblings, and independent MobileApp.
 */
public class Problem5_ConnectedHomeControlPanel {

    public static abstract class HomeDevice {
        private static int deviceCounter = 1000;
        private final String serialNumber;

        public HomeDevice() {
            deviceCounter++;
            this.serialNumber = "HD-" + deviceCounter;
        }

        public abstract String activate();

        public String getSerialNumber() {
            return serialNumber;
        }
    }

    public interface RemoteControllable {
        String connect(String appId);
    }

    public interface EnergyTrackable {
        double getConsumptionWatts();
    }

    public static class WashingMachine extends HomeDevice implements RemoteControllable, EnergyTrackable {
        private final double consumptionWatts;

        public WashingMachine(double consumptionWatts) {
            super();
            if (consumptionWatts <= 0) {
                throw new IllegalArgumentException("Consumption watts must be positive");
            }
            this.consumptionWatts = consumptionWatts;
        }

        @Override
        public String activate() {
            return "Washing machine " + getSerialNumber() + " started a cycle";
        }

        @Override
        public String connect(String appId) {
            return getSerialNumber() + " connected to " + appId;
        }

        @Override
        public double getConsumptionWatts() {
            return consumptionWatts;
        }
    }

    // Hierarchical sibling: implements only EnergyTrackable, not RemoteControllable
    public static class Refrigerator extends HomeDevice implements EnergyTrackable {
        private final double consumptionWatts;

        public Refrigerator(double consumptionWatts) {
            super();
            if (consumptionWatts <= 0) {
                throw new IllegalArgumentException("Consumption watts must be positive");
            }
            this.consumptionWatts = consumptionWatts;
        }

        @Override
        public String activate() {
            return "Refrigerator " + getSerialNumber() + " cooling activated";
        }

        @Override
        public double getConsumptionWatts() {
            return consumptionWatts;
        }
    }

    // Unrelated to HomeDevice: implements only RemoteControllable
    public static class MobileApp implements RemoteControllable {
        private final String appName;

        public MobileApp(String appName) {
            if (appName == null || appName.trim().isEmpty()) {
                throw new IllegalArgumentException("App name cannot be blank");
            }
            this.appName = appName.trim();
        }

        @Override
        public String connect(String appId) {
            return appName + " connected to " + appId;
        }
    }

    public static void connectAll(RemoteControllable[] items, String appId) {
        if (items == null) return;
        for (RemoteControllable item : items) {
            if (item != null) {
                System.out.println(item.connect(appId));
            }
        }
    }

    public static double getConsumptionIfTrackable(HomeDevice d) {
        if (d instanceof EnergyTrackable) {
            return ((EnergyTrackable) d).getConsumptionWatts();
        }
        return 0.0;
    }

    public static void main(String[] args) {
        WashingMachine wm = new WashingMachine(500.0);
        System.out.println(wm.activate());
        System.out.println(wm.connect("HomeConnect"));

        Refrigerator fridge = new Refrigerator(150.0);
        System.out.println("Fridge consumption: " + getConsumptionIfTrackable(fridge));

        MobileApp app = new MobileApp("HomeConnect App");
        System.out.println(app.connect("HomeConnect"));

        HomeDevice ref = wm; // upcasting to HomeDevice
        System.out.println("WashingMachine via ref: " + getConsumptionIfTrackable(ref));
    }
}
