
package semester1.practice11.homework;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ConcurrentModificationException;

class Artifact {
    private String name;
    private int costKarbovanets;
    private double radiationLevel;

    public Artifact(String name, int costKarbovanets, double radiationLevel) {
        this.name = name;
        this.costKarbovanets = costKarbovanets;
        this.radiationLevel = radiationLevel;
    }

    public String getName() {
        return name;
    }

    public int getCostKarbovanets() {
        return costKarbovanets;
    }

    public double getRadiationLevel() {
        return radiationLevel;
    }

    public boolean isRadioactiveDud(double maxSafeRadiation) {
        return radiationLevel > maxSafeRadiation
                || costKarbovanets <= 0;
    }

    @Override
    public String toString() {
        return "[Артефакт: '" + name
                + "' | Цена: " + costKarbovanets
                + " крб | Радиация: " + radiationLevel
                + " мкЗв/год]";
    }
}

public class Task5StalkerBackpackAndSidorovichLoot {

    private static final double MAX_SAFE_RADIATION = 50.0;

    public static void demonstrateFailFastOnBackpack(List<Artifact> backpack) {
        List<Artifact> testBackpack = new ArrayList<>(backpack);

        try {
            for (Artifact artifact : testBackpack) {
                if (artifact.isRadioactiveDud(MAX_SAFE_RADIATION)) {
                    testBackpack.remove(artifact);
                }
            }
        } catch (ConcurrentModificationException e) {
            System.out.println(
                    "  ⚡ [Проверка КПК]: Попытка прямого удаления "
                            + "в цикле for-each вызвала ConcurrentModificationException!"
            );
        }
    }

    public static int purgeRadioactiveDudsWithIterator(
            List<Artifact> backpack, double maxSafeRadiation) {

        Iterator<Artifact> iterator = backpack.iterator();
        int removedDuds = 0;

        while (iterator.hasNext()) {
            Artifact current = iterator.next();

            if (current.isRadioactiveDud(maxSafeRadiation)) {
                System.out.println(
                        "  ☢️ [Iterator.remove] Викинуто: "
                                + current.getName()
                                + " (Радиация: "
                                + current.getRadiationLevel()
                                + " мкЗв/год)"
                );

                iterator.remove();
                removedDuds++;
            }
        }

        return removedDuds;
    }

    public static void distributeAndRankForSidorovich(
            List<Artifact> cleanLoot,
            List<Artifact> leadContainer,
            List<Artifact> standardPouch) {

        for (Artifact artifact : cleanLoot) {
            if (artifact.getCostKarbovanets() >= 15000) {
                leadContainer.add(artifact);
            } else {
                standardPouch.add(artifact);
            }
        }

        // Сортировка чистого хабару по убыванию цены
        for (int i = 0; i < cleanLoot.size() - 1; i++) {
            int maxIndex = i;

            for (int j = i + 1; j < cleanLoot.size(); j++) {
                if (cleanLoot.get(j).getCostKarbovanets()
                        > cleanLoot.get(maxIndex).getCostKarbovanets()) {
                    maxIndex = j;
                }
            }

            Artifact temp = cleanLoot.get(i);
            cleanLoot.set(i, cleanLoot.get(maxIndex));
            cleanLoot.set(maxIndex, temp);
        }
    }

    public static void findTopArtifactAndPrintReceipt(List<Artifact> cleanLoot) {
        int maxCost = Integer.MIN_VALUE;
        Artifact mostValuable = null;
        int totalCost = 0;

        for (Artifact artifact : cleanLoot) {
            totalCost += artifact.getCostKarbovanets();

            if (artifact.getCostKarbovanets() > maxCost) {
                maxCost = artifact.getCostKarbovanets();
                mostValuable = artifact;
            }
        }

        System.out.println();
        System.out.println("  📋 Фінальний чек та ранжований список хабару:");

        for (int i = 0; i < cleanLoot.size(); i++) {
            Artifact artifact = cleanLoot.get(i);

            System.out.println(
                    "    #" + (i + 1) + ". "
                            + artifact.getName()
                            + " — " + artifact.getCostKarbovanets()
                            + " крб (Фон: "
                            + artifact.getRadiationLevel()
                            + " мкЗв/год)"
            );
        }

        System.out.println("----------------------------------------------");

        if (mostValuable != null) {
            System.out.println(
                    "  👑 Найдорожчий артефакт: "
                            + mostValuable.getName()
                            + " (" + maxCost + " крб)"
            );
        }

        System.out.println(
                "  💰 ПІДСУМКОВА ВАРТІСТЬ ЧИСТОГО ХАБАРУ: "
                        + totalCost + " крб"
        );

        System.out.println(
                "  💬 Сидорович: Непоганий улов, Скіфе! "
                        + "Тримай свої " + totalCost + " карбованців!"
        );
    }

    public static void main(String[] args) {

        System.out.println(
                "================ S.T.A.L.K.E.R. 2: РЕЙД ТА ЛАВКА СИДОРОВИЧА ================"
        );

        System.out.println(
                "Сталкер: Скіф | Допустима норма радіації: "
                        + MAX_SAFE_RADIATION + " мкЗв/год"
        );

        ArrayList<Artifact> backpack = new ArrayList<>();

        backpack.add(new Artifact("Бенгальський вогонь", 9500, 14.5));
        backpack.add(new Artifact("Фоняча Пустушка", 0, 92.0));
        backpack.add(new Artifact("Крівник", 18500, 26.0));
        backpack.add(new Artifact("Аномальний Шлак", 1200, 135.4));
        backpack.add(new Artifact("Виверт", 11000, 18.0));
        backpack.add(new Artifact("Іржава Пустушка", 500, 78.5));
        backpack.add(new Artifact("Компас", 45000, 32.0));

        System.out.println();
        System.out.println("[1. ВМІСТ РЮКЗАКА ДО ЧИСТКИ]:");

        double totalRadiation = 0;

        for (int i = 0; i < backpack.size(); i++) {
            System.out.println((i + 1) + ". " + backpack.get(i));
            totalRadiation += backpack.get(i).getRadiationLevel();
        }

        System.out.println("Всього знахідок: " + backpack.size());
        System.out.println("Сумарний радіаційний фон: "
                + totalRadiation + " мкЗв/год");

        System.out.println();
        System.out.println("[2. ДЕЗАКТИВАЦІЯ РЮКЗАКА]:");

        demonstrateFailFastOnBackpack(backpack);

        System.out.println("  -> Запускаємо безпечне очищення через Iterator...");

        int removed = purgeRadioactiveDudsWithIterator(
                backpack, MAX_SAFE_RADIATION
        );

        System.out.println("  Вилучено пустушок: " + removed);
        System.out.println("  Залишилося артефактів: " + backpack.size());

        double safeRadiation = 0;

        for (Artifact artifact : backpack) {
            safeRadiation += artifact.getRadiationLevel();
        }

        System.out.println("  Безпечний фон: "
                + safeRadiation + " мкЗв/год");

        System.out.println();
        System.out.println("[3. КОНТЕЙНЕРИ ТА РАНЖУВАННЯ]:");

        ArrayList<Artifact> leadSpecialContainer = new ArrayList<>();
        ArrayList<Artifact> standardBeltContainer = new ArrayList<>();

        distributeAndRankForSidorovich(
                backpack,
                leadSpecialContainer,
                standardBeltContainer
        );

        System.out.println("  Свинцевий спецконтейнер:");

        for (Artifact artifact : leadSpecialContainer) {
            System.out.println("    " + artifact.getName()
                    + " — " + artifact.getCostKarbovanets() + " крб");
        }

        System.out.println("  Стандартний контейнер:");

        for (Artifact artifact : standardBeltContainer) {
            System.out.println("    " + artifact.getName()
                    + " — " + artifact.getCostKarbovanets() + " крб");
        }

        findTopArtifactAndPrintReceipt(backpack);

        System.out.println(
                "=========================================================================="
        );
    }
}
