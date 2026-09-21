package main.java.oop_fundamentals.assignment_problems;
interface RemoteControllable {
    String connect(String appId);
}

interface EnergyTrackable {
    double getConsumptionWatts();
}

abstract class HomeDevice {
    private static int counter = 1000;
    private final String serialNumber;

    public HomeDevice() {
        counter++;
        this.serialNumber = "HD-" + counter;
    }

    public abstract String activate();

    public String getSerialNumber() {
        return serialNumber;
    }

    public static double getConsumptionIfTrackable(HomeDevice d) {
        if (d instanceof EnergyTrackable) {
            EnergyTrackable trackableDevice = (EnergyTrackable) d;
            return trackableDevice.getConsumptionWatts();
        }
        return 0.0;
    }
}

class WashingMachine extends HomeDevice implements RemoteControllable, EnergyTrackable {
    private double consumptionWatts;

    public WashingMachine(double consumptionWatts) {
        super();
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

class Refrigerator extends HomeDevice implements EnergyTrackable {
    private double consumptionWatts;

    public Refrigerator(double consumptionWatts) {
        super();
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

class MobileApp implements RemoteControllable {
    private String appName;

    public MobileApp(String appName) {
        this.appName = appName;
    }

    public String getAppName() {
        return appName;
    }

    @Override
    public String connect(String appId) {
        return appName + " linked with target app " + appId;
    }
}

public class HomeControlPanel {
    public static void connectAll(RemoteControllable[] items, String appId) {
        for (RemoteControllable item : items) {
            System.out.println(item.connect(appId));
        }
    }

    public static void main(String[] args) {
        WashingMachine wm = new WashingMachine(500.0);
        System.out.println(wm.activate());
        System.out.println(wm.connect("HomeConnect"));

        System.out.println("-----------------------------------");

        Refrigerator fridge = new Refrigerator(150.0);
        System.out.println(fridge.activate());
        System.out.println("Refrigerator Consumption: " + HomeDevice.getConsumptionIfTrackable(fridge));

        System.out.println("-----------------------------------");

        MobileApp app = new MobileApp("SmartLife App");
        System.out.println(app.connect("HomeConnect"));

        System.out.println("-----------------------------------");

        RemoteControllable[] controllableItems = new RemoteControllable[]{wm, app};
        connectAll(controllableItems, "HomeConnect");
    }
}
