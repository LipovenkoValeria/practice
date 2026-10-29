package semester1.practice06.homework;

import java.util.Scanner;

public class Task5AnimeHeroBattle {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Asta asta = new Asta();

        String bossName = "Патри";
        int bossHp = 1200;
        int bossMaxHp = 1200;
        int round = 1;

        System.out.println("=============== АРЕНА КОРОЛІВСТВА КОНЮШИНИ ===============");
        System.out.println("Бос: " + bossName + " (Маг Світла) [HP: "
                + bossHp + "/" + bossMaxHp + "]");

        while (asta.isAlive() && bossHp > 0) {
            System.out.println("\n--- Раунд " + round + " ---");
            asta.showStatus();

            System.out.println("\nОберіть дію Асти:");
            System.out.println("[1] Удар мечем Демоноборця");
            System.out.println("[2] Застосувати суперздібність");
            System.out.println("[3] Захисна стійка");
            System.out.println("[4] Переглянути статус");
            System.out.print("Ваш вибір: ");

            int choice;

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
            } else {
                System.out.println("Введіть число від 1 до 4.");
                scanner.next();
                continue;
            }

            boolean defensiveStance = false;
            boolean actionTaken = true;

            if (choice == 1) {
                bossHp = bossHp - asta.basicSwordAttack();
            } else if (choice == 2) {
                System.out.println("Оберіть навичку:");
                System.out.println("[1] Удар Демоноборця (25 енергії)");
                System.out.println("[2] Чорний Ураган (50 енергії)");
                System.out.println("[3] Метеорит Демона (75 енергії)");
                System.out.println("[4] Форма Чорного Демона (100 енергії)");
                System.out.print("Ваш вибір: ");

                if (scanner.hasNextInt()) {
                    int skillChoice = scanner.nextInt();
                    int damage = asta.useSkill(skillChoice - 1);
                    bossHp = bossHp - damage;
                } else {
                    System.out.println("Введіть номер навички.");
                    scanner.next();
                    actionTaken = false;
                }
            } else if (choice == 3) {
                defensiveStance = true;
                System.out.println("Аста займає захисну стійку мечем!");
            } else if (choice == 4) {
                asta.showStatus();
                System.out.println("Бос " + bossName + ": "
                        + Math.max(0, bossHp) + "/" + bossMaxHp + " HP");
                actionTaken = false;
            } else {
                System.out.println("Неправильний вибір.");
                actionTaken = false;
            }

            if (bossHp <= 0) {
                bossHp = 0;
                break;
            }

            if (!actionTaken) {
                continue;
            }

            System.out.println("\n" + bossName
                    + " атакує променями світла!");

            int bossDamage = 120;

            if (defensiveStance) {
                bossDamage = 50;
                System.out.println("Захисна стійка зменшує шкоду!");
            }

            asta.takeDamage(bossDamage);
            asta.pushLimits();

            System.out.println("\n[СТАТУС] Аста: " + asta.getHp()
                    + "/1000 HP | Бос " + bossName + ": "
                    + bossHp + "/" + bossMaxHp + " HP");

            round++;
        }

        System.out.println("\n================ РЕЗУЛЬТАТ БОЮ ================");

        if (asta.isAlive() && bossHp <= 0) {
            System.out.println("Перемога! Аста переміг Патри!");
        } else {
            System.out.println("Аста програв бій. Спробуйте ще раз!");
        }

        scanner.close();
    }
}