import java.util.*;

interface Capability {
    String getName();
    boolean applyValue(Object value);
}

class PowerCapability implements Capability {
    private boolean state = false;

    @Override
    public String getName() {
        return "Power";
    }

    @Override
    public boolean applyValue(Object value) {
        if (value instanceof Boolean) {
            this.state = (Boolean) value;
            return true;
        } else if (value instanceof String) {
            String val = ((String) value).toUpperCase();
            if (val.equals("ON") || val.equals("OFF")) {
                this.state = val.equals("ON");
                return true;
            }
        }
        return false;
    }

    public boolean getState() {
        return state;
    }
}

class BrightnessCapability implements Capability {
    private int brightness = 0;

    @Override
    public String getName() {
        return "Brightness";
    }

    @Override
    public boolean applyValue(Object value) {
        if (value instanceof Integer) {
            int val = (Integer) value;
            if (val >= 0 && val <= 100) {
                this.brightness = val;
                return true;
            }
        }
        return false;
    }

    public int getBrightness() {
        return brightness;
    }
}

class TemperatureCapability implements Capability {
    private int temperature = 20;

    @Override
    public String getName() {
        return "Temperature";
    }

    @Override
    public boolean applyValue(Object value) {
        if (value instanceof Integer) {
            int val = (Integer) value;
            if (val >= 16 && val <= 30) {
                this.temperature = val;
                return true;
            }
        }
        return false;
    }

    public int getTemperature() {
        return temperature;
    }
}

class Device {
    private String name;
    private Map<String, Capability> capabilities = new LinkedHashMap<>();

    public Device(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addCapability(Capability capability) {
        capabilities.put(capability.getName(), capability);
    }

    public boolean hasCapability(String capabilityName) {
        return capabilities.containsKey(capabilityName);
    }

    public Capability getCapability(String capabilityName) {
        return capabilities.get(capabilityName);
    }

    public boolean applyCapabilityValue(String capabilityName, Object value) {
        Capability cap = capabilities.get(capabilityName);
        if (cap != null) {
            return cap.applyValue(value);
        }
        return false;
    }
}

class SceneStep {
    private String capabilityName;
    private Object value;

    public SceneStep(String capabilityName, Object value) {
        this.capabilityName = capabilityName;
        this.value = value;
    }

    public String getCapabilityName() {
        return capabilityName;
    }

    public Object getValue() {
        return value;
    }
}

class Scene {
    private String name;
    private List<SceneStep> steps = new ArrayList<>();

    public Scene(String name) {
        this.name = name;
    }

    public void addStep(String capabilityName, Object value) {
        steps.add(new SceneStep(capabilityName, value));
    }

    public void execute(List<Device> devices) {
        System.out.println("Scene '" + name + "' started.");
        int actionsApplied = 0;

        for (SceneStep step : steps) {
            for (Device device : devices) {
                if (device.hasCapability(step.getCapabilityName())) {
                    boolean success = device.applyCapabilityValue(step.getCapabilityName(), step.getValue());
                    if (success) {
                        actionsApplied++;
                        if (step.getCapabilityName().equals("Power")) {
                            System.out.println(device.getName() + ": " + step.getValue() + ".");
                        } else if (step.getCapabilityName().equals("Brightness")) {
                            System.out.println(device.getName() + ": brightness set to " + step.getValue() + "%.");
                        } else if (step.getCapabilityName().equals("Temperature")) {
                            System.out.println(device.getName() + ": temperature set to " + step.getValue() + "°C.");
                        }
                    }
                }
            }
        }

        System.out.println("Scene '" + name + "' completed: " + actionsApplied + " actions applied.");
    }
}

public class Labcontrol {
    public static void main(String[] args) {
        Device labAC = new Device("Lab AC");
        labAC.addCapability(new PowerCapability());
        labAC.addCapability(new TemperatureCapability());

        Device ceilingLights = new Device("Ceiling Lights");
        ceilingLights.addCapability(new PowerCapability());
        ceilingLights.addCapability(new BrightnessCapability());

        Device projector = new Device("Projector");
        projector.addCapability(new PowerCapability());

        List<Device> devices = Arrays.asList(labAC, ceilingLights, projector);

        Scene lectureMode = new Scene("Lecture Mode");
        lectureMode.addStep("Power", "ON");
        lectureMode.addStep("Brightness", 40);
        lectureMode.addStep("Temperature", 24);

        lectureMode.execute(devices);

        boolean acTempSuccess = labAC.applyCapabilityValue("Temperature", 12);
        if (!acTempSuccess) {
            System.out.println("Rejected: Lab AC temperature must be between 16°C and 30°C.");
        }

        projector.addCapability(new BrightnessCapability());
        System.out.println("Projector: Brightness capability added.");

        boolean projBrightSuccess = projector.applyCapabilityValue("Brightness", 70);
        if (projBrightSuccess) {
            System.out.println("Projector: brightness set to 70%.");
        }
    }
}