package semester1.practice;

import java.util.Scanner;

public class Task1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== КАЛЬКУЛЯТОР ПОЇЗДКИ ТА ПАЛЬНОГО ===");

        System.out.print("Введіть відстань поїздки (км): ");
        double distance = scanner.nextDouble();

        System.out.print("Введіть середню витрату на 100 км (л): ");
        double fuelConsumption = scanner.nextDouble();

        if (distance <= 0 || fuelConsumption <= 0) {
            System.out.println("Помилка: відстань та витрата мають бути більшими за 0.");
            scanner.close();
            return;
        }

        System.out.println("Оберіть тип пального:");
        System.out.println("1 - Бензин А-95");
        System.out.println("2 - Дизель");
        System.out.println("3 - Газ");
        System.out.print("Ваш вибір: ");
        int fuelType = scanner.nextInt();

        double pricePerLiter = 0;
        String fuelName = "";

        switch (fuelType) {
            case 1:
                pricePerLiter = 56.50;
                fuelName = "Бензин А-95";
                break;
            case 2:
                pricePerLiter = 52.80;
                fuelName = "Дизель";
                break;
            case 3:
                pricePerLiter = 29.40;
                fuelName = "Газ";
                break;
            default:
                System.out.println("Помилка: невідомий тип пального.");
                scanner.close();
                return;
        }

        double neededFuel = distance * fuelConsumption / 100.0;
        double baseCost = neededFuel * pricePerLiter;

        double finalCost = baseCost >= 2000.0
                ? baseCost * 0.95
                : baseCost;

        double discount = baseCost - finalCost;

        System.out.println();
        System.out.println("------------- ЗВІТ ПОЇЗДКИ -------------");
        System.out.println("Обране пальне: " + fuelName);
        System.out.println("Ціна за літр: " + pricePerLiter + " грн");
        System.out.println("Необхідно пального: " + neededFuel + " л");
        System.out.println("Базова вартість: " + baseCost + " грн");
        System.out.println("Знижка АЗС: " + discount + " грн");
        System.out.println("----------------------------------------");
        System.out.println("Підсумкова сума до сплати: " + finalCost + " грн");

        scanner.close();
    }
}