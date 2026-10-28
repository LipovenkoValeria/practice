package semester1.practice05.homework;

public class Task2WarehouseInventory {
    public static void main(String[] args) {
        String[] items = {
                "Ноутбук",
                "Смартфон",
                "Бездротові навушники",
                "Механічна клавіатура",
                "Монітор 27 дюймів"
        };

        int[] quantities = {8, 3, 25, 4, 12};

        double[] prices = {
                28500.0, 19200.0, 1850.0, 3100.0, 9400.0
        };

        double[] totalValues = new double[items.length];
        double totalWarehouseValue = 0;

        int mostValuableItem = 0;

        for (int i = 0; i < items.length; i++) {
            totalValues[i] = quantities[i] * prices[i];
            totalWarehouseValue += totalValues[i];

            if (totalValues[i] > totalValues[mostValuableItem]) {
                mostValuableItem = i;
            }
        }

        System.out.println("========== ЗВІТ СКЛАДУ ЕЛЕКТРОНІКИ ==========");
        System.out.printf(
                "%-3s | %-25s | %-10s | %-12s | %-15s%n",
                "№", "Назва товару", "Кількість",
                "Ціна (грн)", "Вартість (грн)"
        );

        System.out.println(
                "--------------------------------------------------------------------------"
        );

        for (int i = 0; i < items.length; i++) {
            System.out.printf(
                    "%-3d | %-25s | %-10d | %-12.2f | %-15.2f%n",
                    i + 1, items[i], quantities[i], prices[i], totalValues[i]
            );
        }

        System.out.println(
                "--------------------------------------------------------------------------"
        );

        System.out.printf(
                "Загальна вартість складу: %.2f грн%n",
                totalWarehouseValue
        );

        System.out.printf(
                "Найбільша вартість запасів: %s (%.2f грн)%n",
                items[mostValuableItem], totalValues[mostValuableItem]
        );

        System.out.println("\n=== ТОВАРИ З МАЛИМ ЗАЛИШКОМ ===");

        boolean shortageFound = false;

        for (int i = 0; i < items.length; i++) {
            if (quantities[i] < 5) {
                System.out.printf(
                        "%s (залишилося %d шт.) - УВАГА: терміново замовити у постачальника!%n",
                        items[i], quantities[i]
                );
                shortageFound = true;
            }
        }

        if (!shortageFound) {
            System.out.println("Товарів із залишком менше ніж 5 одиниць немає.");
        }
    }
}
