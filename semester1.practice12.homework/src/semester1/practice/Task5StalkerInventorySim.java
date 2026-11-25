package semester1.practice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;

abstract class ZoneItem {
    private final String id;
    private final String name;
    private final double weightKg;

    public ZoneItem(String id, String name, double weightKg) {
        if (weightKg <= 0) {
            throw new IllegalArgumentException("Вага повинна бути додатною!");
        }

        this.id = id;
        this.name = name;
        this.weightKg = weightKg;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getWeightKg() {
        return weightKg;
    }

    public abstract String getItemType();

    public abstract boolean isUsable();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof ZoneItem)) {
            return false;
        }

        ZoneItem item = (ZoneItem) o;

        return Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

class Artifact extends ZoneItem {
    private double radiation;
    private int protection;

    public Artifact(String id, String name, double weightKg,
                    double radiation, int protection) {
        super(id, name, weightKg);

        this.radiation = radiation;
        this.protection = protection;
    }

    public double getRadiation() {
        return radiation;
    }

    public int getProtection() {
        return protection;
    }

    @Override
    public String getItemType() {
        return "Артефакт";
    }

    @Override
    public boolean isUsable() {
        return true;
    }
}

class Medkit extends ZoneItem {
    private int usesLeft;

    public Medkit(String id, String name, double weightKg, int usesLeft) {
        super(id, name, weightKg);

        if (usesLeft < 0) {
            throw new IllegalArgumentException("Кількість зарядів не може бути від'ємною!");
        }

        this.usesLeft = usesLeft;
    }

    public boolean use() {
        if (usesLeft > 0) {
            usesLeft--;
            return true;
        }

        return false;
    }

    public int getUsesLeft() {
        return usesLeft;
    }

    @Override
    public String getItemType() {
        return "Аптечка";
    }

    @Override
    public boolean isUsable() {
        return usesLeft > 0;
    }
}

class Detector extends ZoneItem {
    private int batteryPercent;

    public Detector(String id, String name,
                    double weightKg, int batteryPercent) {
        super(id, name, weightKg);

        if (batteryPercent < 0 || batteryPercent > 100) {
            throw new IllegalArgumentException("Некоректний заряд батареї!");
        }

        this.batteryPercent = batteryPercent;
    }

    public int getBatteryPercent() {
        return batteryPercent;
    }

    @Override
    public String getItemType() {
        return "Детектор";
    }

    @Override
    public boolean isUsable() {
        return batteryPercent > 0;
    }
}

class Stalker {
    private String callSign;

    private HashSet<Artifact> artifactBelt = new HashSet<>();
    private ArrayList<ZoneItem> backpack = new ArrayList<>();
    private HashSet<String> visitedLocations = new HashSet<>();

    public Stalker(String callSign, HashSet<String> visitedLocations) {
        this.callSign = callSign;
        this.visitedLocations = new HashSet<>(visitedLocations);
    }

    public boolean equipArtifact(Artifact art) {
        if (art == null || artifactBelt.size() >= 4) {
            return false;
        }

        return artifactBelt.add(art);
    }

    public void addToBackpack(ZoneItem item) {
        if (item != null) {
            backpack.add(item);
        }
    }

    public int cleanBackpack() {
        int removed = 0;

        Iterator<ZoneItem> it = backpack.iterator();

        while (it.hasNext()) {
            ZoneItem item = it.next();

            if (!item.isUsable()) {
                System.out.println("  Викинуто [" + item.getItemType()
                        + "]: " + item.getName());

                it.remove();
                removed++;
            }
        }

        return removed;
    }

    public double getTotalWeightKg() {
        double total = 0;

        for (Artifact art : artifactBelt) {
            total += art.getWeightKg();
        }

        for (ZoneItem item : backpack) {
            total += item.getWeightKg();
        }

        return total;
    }

    public double getNetRadiation() {
        double total = 0;

        for (Artifact art : artifactBelt) {
            total += art.getRadiation();
        }

        return total;
    }

    public int getTotalProtection() {
        int total = 0;

        for (Artifact art : artifactBelt) {
            total += art.getProtection();
        }

        return total;
    }

    public HashSet<String> getVisitedLocationsCopy() {
        return new HashSet<>(visitedLocations);
    }

    public HashSet<String> getCommonLocations(HashSet<String> ally) {
        HashSet<String> result = new HashSet<>(visitedLocations);

        result.retainAll(ally);

        return result;
    }

    public HashSet<String> getNewLocationsFromAlly(HashSet<String> ally) {
        HashSet<String> result = new HashSet<>(ally);

        result.removeAll(visitedLocations);

        return result;
    }

    public int getArtifactCount() {
        return artifactBelt.size();
    }

    public ArrayList<ZoneItem> getBackpackCopy() {
        return new ArrayList<>(backpack);
    }

    public String getCallSign() {
        return callSign;
    }
}

public class Task5StalkerInventorySim {

    public static void main(String[] args) {

        System.out.println("=============== S.T.A.L.K.E.R. 2 ===============");

        HashSet<String> locations = new HashSet<>();

        locations.add("Залісся");
        locations.add("Кордон");
        locations.add("Звалище");
        locations.add("Янтар");

        Stalker stalker = new Stalker("Скіф", locations);

        System.out.println("Сталкер: " + stalker.getCallSign());

        System.out.println("\n--- 1. Пояс артефактів ---");

        Artifact medusa = new Artifact(
                "ART-1", "Медуза", 0.4, -2.0, 15);

        Artifact soul = new Artifact(
                "ART-2", "Душа", 0.5, 3.5, 25);

        Artifact medusaCopy = new Artifact(
                "ART-1", "Медуза", 0.4, -2.0, 15);

        Artifact compass = new Artifact(
                "ART-3", "Компас", 0.6, 2.5, 40);

        System.out.println("Медуза: " + stalker.equipArtifact(medusa));
        System.out.println("Душа: " + stalker.equipArtifact(soul));
        System.out.println("Дублікат Медузи: "
                + stalker.equipArtifact(medusaCopy));
        System.out.println("Компас: " + stalker.equipArtifact(compass));

        System.out.println("\n--- 2. Рюкзак ---");

        Medkit regularMedkit = new Medkit(
                "MED-1", "Звичайна аптечка", 0.3, 1);

        Medkit armyMedkit = new Medkit(
                "MED-2", "Армійська аптечка", 0.4, 2);

        Detector echo = new Detector(
                "DET-1", "Детектор Відгук", 0.5, 0);

        Detector veles = new Detector(
                "DET-2", "Детектор Велес", 0.7, 85);

        stalker.addToBackpack(regularMedkit);
        stalker.addToBackpack(armyMedkit);
        stalker.addToBackpack(echo);
        stalker.addToBackpack(veles);

        regularMedkit.use();

        System.out.println("Перевірка рюкзака через Iterator:");

        int removed = stalker.cleanBackpack();

        System.out.println("Видалено предметів: " + removed);
        System.out.println("Залишилось предметів: "
                + stalker.getBackpackCopy().size());

        System.out.println("\n--- 3. Загальний стан сталкера ---");

        System.out.println("Кількість артефактів: "
                + stalker.getArtifactCount());

        System.out.println("Сумарний захист: "
                + stalker.getTotalProtection() + "%");

        System.out.println("Радіація: "
                + stalker.getNetRadiation() + " мЗв/хв");

        System.out.println("Предмети в рюкзаку:");

        for (ZoneItem item : stalker.getBackpackCopy()) {
            System.out.println("  " + item.getName());
        }

        System.out.println("Загальна вага спорядження: "
                + String.format("%.2f", stalker.getTotalWeightKg()) + " кг");

        System.out.println("\n--- 4. Синхронізація КПК ---");

        HashSet<String> allyLocations = new HashSet<>();

        allyLocations.add("Залісся");
        allyLocations.add("Янтар");
        allyLocations.add("Росток");
        allyLocations.add("Прип'ять");

        System.out.println("Локації Скіфа: "
                + stalker.getVisitedLocationsCopy());

        System.out.println("Локації союзника: " + allyLocations);

        System.out.println("Спільні локації: "
                + stalker.getCommonLocations(allyLocations));

        System.out.println("Нові локації союзника: "
                + stalker.getNewLocationsFromAlly(allyLocations));

        System.out.println("===============================================");
    }
}