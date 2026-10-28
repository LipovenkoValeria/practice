package semester1.practice;

import java.util.Scanner;

public class Task5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("/************/");
        System.out.println("|      ПРИГОДА: ВТЕЧА З ТЕМНИЦІ ЧАКЛУНА        |");
        System.out.println("/************/");

        System.out.print("Введіть ім'я вашого героя: ");
        String heroName = scanner.nextLine();

        System.out.println("Оберіть клас героя:");
        System.out.println("1 - Воїн");
        System.out.println("2 - Маг");
        System.out.println("3 - Розбійник");
        System.out.print("Ваш вибір: ");
        int heroClass = scanner.nextInt();

        String className;
        int playerHp;
        int strength;
        int mana;
        int agility;

        switch (heroClass) {
            case 1:
                className = "Воїн";
                playerHp = 120;
                strength = 15;
                mana = 10;
                agility = 8;
                break;
            case 2:
                className = "Маг";
                playerHp = 75;
                strength = 6;
                mana = 50;
                agility = 10;
                break;
            case 3:
                className = "Розбійник";
                playerHp = 90;
                strength = 10;
                mana = 15;
                agility = 18;
                break;
            default:
                className = "Волоцюга";
                playerHp = 60;
                strength = 8;
                mana = 5;
                agility = 8;
                break;
        }

        boolean hasSpellBook = heroClass == 2;
        boolean passedObstacle = false;
        String artifact = "Немає";

        System.out.println();
        System.out.println("Створено персонажа: " + className + " " + heroName);
        System.out.println("HP: " + playerHp + ", Сила: " + strength
                + ", Мана: " + mana + ", Спритність: " + agility);

        scanner.nextLine();

        System.out.println();
        System.out.println("Ви прокидаєтеся в сирій камері.");
        System.out.println("Перед вами три проходи: північ, схід та захід.");
        System.out.print("Куди ви прямуєте? ");
        String direction = scanner.nextLine();

        if (direction.equalsIgnoreCase("північ")) {
            System.out.println();
            System.out.println("=== КІМНАТА СТАРОДАВНЬОЇ ЗАГАДКИ ===");
            System.out.println("Магічний кристал запитує: скільки буде 3 + 4?");
            System.out.print("Ваша відповідь: ");
            String answer = scanner.nextLine();

            boolean isCorrect = answer.equalsIgnoreCase("7");

            playerHp = isCorrect
                    ? playerHp + 20
                    : playerHp - 30;

            if (isCorrect) {
                System.out.println("Правильно! Кристал відновив 20 HP.");
                artifact = "Магічний кристал";
                passedObstacle = true;
            } else {
                System.out.println("Неправильно! Ви втратили 30 HP.");
            }

        } else if (direction.equalsIgnoreCase("схід")) {
            System.out.println();
            System.out.println("=== АРСЕНАЛ ВАРТОВОГО ===");
            System.out.println("Перед вами стоїть кована скриня.");

            if (agility >= 16 || (mana >= 25 && hasSpellBook)) {
                System.out.println("Вам вдалося відкрити скриню!");
                System.out.println("Ви знайшли Амулет Світла (+20 HP).");

                playerHp = playerHp + 20;
                strength = strength + 5;
                artifact = "Амулет Світла та легендарний бонус сили";
                passedObstacle = true;
            } else {
                System.out.println("Пастка! На вас полетіли стріли.");
                playerHp = playerHp - 25;
            }
        } else if (direction.equalsIgnoreCase("захід")) {
            System.out.println();
            System.out.println("=== ПЕЧЕРА КАМ'ЯНОГО ГОЛЕМА ===");
            System.out.println("Перед вами кам'яний голем!");
            System.out.println("Оберіть тактику:");
            System.out.println("1 - Лобова атака");
            System.out.println("2 - Магічний бар'єр");
            System.out.println("3 - Спроба прослизнути повз");
            System.out.print("Ваш вибір: ");
            int tactic = scanner.nextInt();

            switch (tactic) {
                case 1:
                    if (strength >= 12) {
                        System.out.println("Ви здолали голема силою!");
                        passedObstacle = true;
                        artifact = "Кам'яний уламок";
                    } else {
                        System.out.println("Вам не вистачило сили!");
                        playerHp = playerHp - 30;
                    }
                    break;

                case 2:
                    if (mana >= 25) {
                        System.out.println("Магічний бар'єр зупинив голема!");
                        passedObstacle = true;
                        artifact = "Магічний бар'єр";
                    } else {
                        System.out.println("Недостатньо мани!");
                        playerHp = playerHp - 25;
                    }
                    break;

                case 3:
                    if (agility >= 12) {
                        System.out.println("Ви спритно прослизнули повз голема!");
                        passedObstacle = true;
                        artifact = "Знайдений ключ";
                    } else {
                        System.out.println("Голем помітив вас!");
                        playerHp = playerHp - 20;
                    }
                    break;

                default:
                    System.out.println("Ви зволікали, і голем атакував!");
                    playerHp = playerHp - 20;
                    break;
            }

        } else {
            System.out.println("Ви не знаєте, куди йти, і повертаєтеся до камери.");
        }

        System.out.println();
        System.out.println("============== ФІНАЛ ПРИГОДИ ==============");
        System.out.println("Герой: " + heroName);
        System.out.println("Клас: " + className);
        System.out.println("Залишок здоров'я: " + playerHp + " HP");
        System.out.println("Здобутий артефакт: " + artifact);

        if (playerHp <= 0) {
            System.out.println("КІНЦІВКА: ПОРАЗКА");
            System.out.println("Герой не зміг пережити небезпеки підземелля.");
        } else if (passedObstacle) {
            System.out.println("КІНЦІВКА: ТРІУМФАЛЬНА ПЕРЕМОГА!");
            System.out.println("Ворота розчиняються, і герой виходить на світло сонця!");
        } else {
            System.out.println("КІНЦІВКА: ШЛЯХ ЗАКРИТО");
            System.out.println("Ви вижили, але поки не змогли вибратися з темниці.");
        }

        String rank = playerHp <= 0
                ? "F"
                : (passedObstacle && playerHp >= 80
                ? "S"
                : (passedObstacle ? "A" : "C"));

        System.out.println("Фінальний ранг: " + rank);
        System.out.println("============================================");

        scanner.close();
    }
}