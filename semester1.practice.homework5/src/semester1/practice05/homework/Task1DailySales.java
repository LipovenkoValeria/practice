package semester1.practice05.homework;

import java.util.Scanner;

public class Task1DailySales {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] days = {
                "Понеділок", "Вівторок", "Середа", "Четвер",
                "П'ятниця", "Субота", "Неділя"
        };

        double[] sales = new double[7];
        double total = 0;

        for (int i = 0; i < sales.length; i++) {
            System.out.print("Введіть виторг за " + days[i] + " (грн): ");
            sales[i] = scanner.nextDouble();
            total += sales[i];
        }

        double average = total / sales.length;

        int bestDay = 0;
        int worstDay = 0;

        for (int i = 1; i < sales.length; i++) {
            if (sales[i] > sales[bestDay]) {
                bestDay = i;
            }

            if (sales[i] < sales[worstDay]) {
                worstDay = i;
            }
        }

        System.out.println("\n=== ФІНАНСОВИЙ ЗВІТ КАВ'ЯРНІ ===");
        System.out.printf("Загальний виторг за тиждень: %.2f грн%n", total);
        System.out.printf("Середній виторг за день: %.2f грн%n", average);

        System.out.printf(
                "Найприбутковіший день: %s (%.2f грн)%n",
                days[bestDay], sales[bestDay]
        );

        System.out.printf(
                "Найменш прибутковий день: %s (%.2f грн)%n",
                days[worstDay], sales[worstDay]
        );

        System.out.println("\nДні з виторгом вище середнього:");

        for (int i = 0; i < sales.length; i++) {
            if (sales[i] > average) {
                System.out.printf(
                        "%s: %.2f грн%n", days[i], sales[i]
                );
            }
        }

        scanner.close();
    }
}