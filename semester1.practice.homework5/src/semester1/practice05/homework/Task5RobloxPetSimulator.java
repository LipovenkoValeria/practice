package semester1.practice05.homework;

import java.util.Scanner;

public class Task5RobloxPetSimulator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] petNames = {
                "Dog", "Cat", "Dragon", "Unicorn", "Huge Titanic Cat"
        };

        int[] petPowers = {15, 25, 120, 350, 2000};
        int[] dropChances = {45, 30, 15, 8, 2};

        String[] inventoryNames = new String[8];
        int[] inventoryPowers = new int[8];

        int petCount = 0;
        int coins = 300;
        int choice = -1;

        while (choice != 0) {
            System.out.println("\n==================================================");
            System.out.println("           ROBLOX: СИМУЛЯТОР ВИХОВАНЦІВ");
            System.out.println("==================================================");
            System.out.println("Баланс: " + coins + " монет | Вихованців: "
                    + petCount + "/" + inventoryNames.length);
            System.out.println("1 - Відкрити яйце (100 монет)");
            System.out.println("2 - Відкрити рюкзак");
            System.out.println("3 - Оцінити силу команди");
            System.out.println("4 - Заробити монети (+50..150)");
            System.out.println("0 - Вийти з гри");
            System.out.print("Ваш вибір: ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    if (coins < 100) {
                        System.out.println("Недостатньо монет для відкриття яйця.");
                    } else if (petCount >= inventoryNames.length) {
                        System.out.println(
                                "Рюкзак переповнений! Продайте або видаліть зайвих вихованців."
                        );
                    } else {
                        coins -= 100;

                        int roll = (int) (Math.random() * 100) + 1;
                        int chanceSum = 0;
                        int petIndex = petNames.length - 1;

                        for (int i = 0; i < dropChances.length; i++) {
                            chanceSum += dropChances[i];

                            if (roll <= chanceSum) {
                                petIndex = i;
                                break;
                            }
                        }

                        inventoryNames[petCount] = petNames[petIndex];
                        inventoryPowers[petCount] = petPowers[petIndex];
                        petCount++;

                        System.out.println("\nЯйце розбивається...");
                        if (petIndex == 4) {
                            System.out.println("НЕЙМОВІРНО! СУПЕР-ДЖЕКПОТ!");
                        }

                        System.out.println(
                                "ВАМ ВИПАВ: " + petNames[petIndex]
                                        + " (Сила: " + petPowers[petIndex] + ")"
                        );

                        System.out.println("Вихованця додано до рюкзака!");
                        System.out.println("Залишок монет: " + coins);
                    }
                    break;

                case 2:
                    System.out.println("\n=== РЮКЗАК ===");

                    if (petCount == 0) {
                        System.out.println("Рюкзак поки що порожній.");
                    } else {
                        for (int i = 0; i < petCount; i++) {
                            System.out.println(
                                    "[Комірка " + (i + 1) + "] "
                                            + inventoryNames[i]
                                            + " (Сила: " + inventoryPowers[i] + ")"
                            );
                        }
                    }
                    break;

                case 3:
                    System.out.println("\n=== СИЛА ВАШОЇ КОМАНДИ ===");
                    if (petCount == 0) {
                        System.out.println("У вас поки що немає вихованців.");
                    } else {
                        int totalPower = 0;
                        int bestPetIndex = 0;

                        for (int i = 0; i < petCount; i++) {
                            totalPower += inventoryPowers[i];

                            if (inventoryPowers[i] > inventoryPowers[bestPetIndex]) {
                                bestPetIndex = i;
                            }
                        }

                        System.out.println("Кількість вихованців: " + petCount);
                        System.out.println("Загальна сила команди: " + totalPower);
                        System.out.println(
                                "Найсильніший вихованець: "
                                        + inventoryNames[bestPetIndex]
                                        + " (Сила: " + inventoryPowers[bestPetIndex] + ")"
                        );
                    }
                    break;

                case 4:
                    int earnedCoins = (int) (Math.random() * 101) + 50;
                    coins += earnedCoins;

                    System.out.println(
                            "Ви заробили " + earnedCoins + " монет!"
                    );
                    System.out.println("Поточний баланс: " + coins);
                    break;

                case 0:
                    System.out.println("Дякуємо за гру!");
                    break;

                default:
                    System.out.println("Неправильний пункт меню. Спробуйте ще раз.");
                    break;
            }
        }

        scanner.close();
    }
}