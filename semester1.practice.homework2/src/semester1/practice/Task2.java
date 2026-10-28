package semester1.practice;

import java.util.Scanner;

public class Task2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== ТАРИФІКАТОР СЛУЖБИ ДОСТАВКИ ===");

        System.out.print("Введіть вагу відправлення (кг): ");
        double weight = scanner.nextDouble();

        System.out.print("Введіть відстань доставки (км): ");
        int distance = scanner.nextInt();

        System.out.println("Оберіть тип доставки:");
        System.out.println("1 - Відділення");
        System.out.println("2 - Поштомат");
        System.out.println("3 - Кур'єр");
        System.out.print("Ваш вибір: ");
        int deliveryType = scanner.nextInt();

        scanner.nextLine();

        System.out.print("Чи є у вас карта Premium? (так/ні): ");
        String premiumAnswer = scanner.nextLine();

        if (weight <= 0 || weight > 50.0) {
            System.out.println("Помилка: вага має бути більшою за 0 та не перевищувати 50 кг.");
            scanner.close();
            return;
        }

        if (distance <= 0) {
            System.out.println("Помилка: відстань має бути більшою за 0 км.");
            scanner.close();
            return;
        }

        double baseTariff = switch (deliveryType) {
            case 1 -> 50.0;
            case 2 -> {
                if (weight > 15.0) {
                    System.out.println("Поштомат не підтримує вантажі понад 15 кг!");
                    yield -1;
                } else {
                    yield 60.0;
                }
            }
            case 3 -> 100.0;
            default -> -1;
        };

        if (baseTariff == -1) {
            if (deliveryType != 2) {
                System.out.println("Помилка: невідомий тип доставки.");
            }
            scanner.close();
            return;
        }

        String deliveryName = switch (deliveryType) {
            case 1 -> "Відділення";
            case 2 -> "Поштомат";
            case 3 -> "Кур'єрська доставка";
            default -> "Невідомо";
        };

        double distanceFee;

        if (distance <= 50) {
            distanceFee = 0;
        } else if (distance <= 200) {
            distanceFee = 35.0;
        } else {
            distanceFee = 80.0;
        }

        double totalBeforeDiscount = baseTariff + distanceFee;

        boolean isPremium = premiumAnswer.equalsIgnoreCase("так");

        double finalCost = isPremium
                ? totalBeforeDiscount * 0.80
                : totalBeforeDiscount;

        System.out.println();
        System.out.println("------------- НАКЛАДНА ДОСТАВКИ -------------");
        System.out.println("Тип доставки: " + deliveryName);
        System.out.println("Базовий тариф: " + baseTariff + " грн");
        System.out.println("Доплата за відстань: " + distanceFee + " грн");
        System.out.println("Сума до знижки: " + totalBeforeDiscount + " грн");

        if (isPremium) {
            System.out.println("Статус клієнта: Premium (-20%)");
        } else {
            System.out.println("Статус клієнта: звичайний");
        }

        System.out.println("---------------------------------------------");
        System.out.println("РАЗОМ ДО СПЛАТИ: " + finalCost + " грн");

        scanner.close();
    }
}