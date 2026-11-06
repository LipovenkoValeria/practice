package semester1.practice09.homework;

class Motherboard {
    private final String model, socketType;
    private final int maxExpansionSlots;

    Motherboard(String m, String s, int n) {
        if (n < 1 || n > 8) throw new IllegalArgumentException();
        model = m;
        socketType = s;
        maxExpansionSlots = n;
    }

    boolean isSocketCompatible(String s) {
        return socketType.equalsIgnoreCase(s);
    }

    String getModel() { return model; }
    String getSocketType() { return socketType; }
    int getMaxExpansionSlots() { return maxExpansionSlots; }
}

class PowerSupplyUnit {
    private final String model;
    private final int maxOutputWatts;
    private final double efficiencyCoefficient;

    PowerSupplyUnit(String m, int w, double e) {
        if (w <= 0 || e < .8 || e > .96)
        throw new IllegalArgumentException();

        model = m;
        maxOutputWatts = w;
        efficiencyCoefficient = e;
    }

    int getSafeContinuousWatts() {
        return (int) (maxOutputWatts * .85);
    }

    double calculateWallPowerDraw(int load) {
        return load / efficiencyCoefficient;
    }

    String getModel() { return model; }
    double getEfficiencyCoefficient() {
        return efficiencyCoefficient;
    }
}

abstract class AbstractHardwareComponent {
    private final String modelName;
    private final double priceUah;

    AbstractHardwareComponent(String n, double p) {
        if (p < 0) throw new IllegalArgumentException();
        modelName = n;
        priceUah = p;
    }

    String getModelName() { return modelName; }
    double getPriceUah() { return priceUah; }

    abstract int getPowerWatts();
    abstract double getBenchmarkScore();
    abstract String getCategory();

    public String toString() {
        return "[" + getCategory() + "] " + modelName
                + " (" + getPowerWatts() + " Вт | Score: "
                + getBenchmarkScore() + ")";
    }
}

class ProcessorUnit extends AbstractHardwareComponent {
    private final String socket;
    private final int cores, tdpWatts;
    private final double boostClockGhz;

    ProcessorUnit(String n, double p, String s,
                  int c, double ghz, int t) {
        super(n, p);
        socket = s;
        cores = c;
        boostClockGhz = ghz;
        tdpWatts = t;
    }

    String getSocket() { return socket; }

    int getPowerWatts() { return tdpWatts; }

    double getBenchmarkScore() {
        return cores * boostClockGhz * 120;
    }

    String getCategory() { return "CPU"; }
}

class GraphicsAccelerator extends AbstractHardwareComponent {
    private final int vramGb, tensorCores, boardPowerWatts;

    GraphicsAccelerator(String n, double p, int v, int t, int w) {
        super(n, p);
        vramGb = v;
        tensorCores = t;
        boardPowerWatts = w;
    }

    int getPowerWatts() { return boardPowerWatts; }

    double getBenchmarkScore() {
        return vramGb * 250.0 + tensorCores * 1.5;
    }

    String getCategory() { return "GPU"; }
}

class NvmeSsdDrive extends AbstractHardwareComponent {
    private final int capacityGb, readSpeedMbSec;
    private final int[] speedSamples;

    NvmeSsdDrive(String n, double p, int c, int r, int[] a) {
        super(n, p);
        capacityGb = c;
        readSpeedMbSec = r;
        speedSamples = a.clone();
    }

    int[] getSpeedSamples() {
        return speedSamples.clone();
    }

    int getPowerWatts() { return 12; }

    double getBenchmarkScore() {
        return readSpeedMbSec * .25;
    }

    String getCategory() { return "NVMe SSD"; }
}

class WorkstationPC {
    private final String buildName;
    private final Motherboard motherboard;
    private final PowerSupplyUnit psu;
    private final AbstractHardwareComponent[] components;
    private int componentCount;

    WorkstationPC(String n, Motherboard m, PowerSupplyUnit p) {
        buildName = n;
        motherboard = m;
        psu = p;
        components = new AbstractHardwareComponent[
                m.getMaxExpansionSlots()];
    }
    boolean installComponent(AbstractHardwareComponent c) {
        if (c == null || componentCount == components.length)
            return false;

        if (c instanceof ProcessorUnit
                && !motherboard.isSocketCompatible(
                ((ProcessorUnit) c).getSocket())) {
            System.out.println("Несумісний сокет CPU!");
            return false;
        }

        if (getTotalPowerWatts() + c.getPowerWatts()
                > psu.getSafeContinuousWatts()) {
            System.out.println("Перевищено ліміт БЖ!");
            return false;
        }

        components[componentCount++] = c;
        System.out.println("Встановлено: " + c);
        return true;
    }

    int getTotalPowerWatts() {
        int s = 0;
        for (int i = 0; i < componentCount; i++)
            s += components[i].getPowerWatts();
        return s;
    }

    double getTotalSystemScore() {
        double s = 0;
        for (int i = 0; i < componentCount; i++)
            s += components[i].getBenchmarkScore();
        return s;
    }

    double getTotalBuildPrice() {
        double s = 0;
        for (int i = 0; i < componentCount; i++)
            s += components[i].getPriceUah();
        return s;
    }

    public String toString() {
        return String.format(
                "Збірка %s%nПлата: %s (%s), слотів %d/%d%n"
                        + "БЖ: %s, ліміт %d Вт%n"
                        + "Споживання: %d Вт (розетка %.2f Вт)%n"
                        + "Score: %.2f | Ціна: %.2f грн",
                buildName,
                motherboard.getModel(),
                motherboard.getSocketType(),
                componentCount,
                components.length,
                psu.getModel(),
                psu.getSafeContinuousWatts(),
                getTotalPowerWatts(),
                psu.calculateWallPowerDraw(getTotalPowerWatts()),
                getTotalSystemScore(),
                getTotalBuildPrice());
    }
}

public class Task2WorkstationConfigurator {
    public static void main(String[] args) {
        Motherboard m = new Motherboard("ASUS ROG B650", "AM5", 4);
        PowerSupplyUnit p =
                new PowerSupplyUnit("Seasonic 750W Gold", 750, .90);

        WorkstationPC pc =
                new WorkstationPC("DeepLearning-Rig", m, p);

        pc.installComponent(new ProcessorUnit(
                "Intel Core i9", 50000, "LGA1700", 24, 5, 250));

        pc.installComponent(new ProcessorUnit(
                "AMD Ryzen 9 7950X", 22000, "AM5", 16, 5.7, 170));

        pc.installComponent(new GraphicsAccelerator(
                "NVIDIA RTX 4080 Super", 50000, 16, 552, 320));

        int[] a = {6900, 7000, 6950};

        NvmeSsdDrive s = new NvmeSsdDrive(
                "Samsung 990 Pro 2TB", 6900, 2000, 7450, a);

        a[0] = 0;

        System.out.println("Захисна копія: " + s.getSpeedSamples()[0]);

        pc.installComponent(s);

        System.out.println(pc);
    }
}