package semester1.practice.homework4;

import java.util.Scanner;

public class Task5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int choice;

        System.out.println("==================================");
        System.out.println("       BRAWL STARS: БИТВА З БОСОМ");
        System.out.println("==================================");
        System.out.println("Оберіть бійця:");
        System.out.println("1. Шеллі     - HP: 4000 | Атака: 1200 | Супер: 2800");
        System.out.println("2. Кольт     - HP: 3000 | Атака: 1600 | Супер: 3600");
        System.out.println("3. Ель Прімо - HP: 6000 | Атака: 1100 | Супер: 2200");

        do {
            System.out.print("Ваш вибір (1-3): ");
            choice = scanner.nextInt();

            if (choice < 1 || choice > 3) {
                System.out.println("Оберіть число від 1 до 3!");
            }
        } while (choice < 1 || choice > 3);

        String playerName = "";
        int maxPlayerHp = 0;
        int playerDamage = 0;
        int superDamage = 0;

        switch (choice) {
            case 1:
                playerName = "Шеллі";
                maxPlayerHp = 4000;
                playerDamage = 1200;
                superDamage = 2800;
                break;
            case 2:
                playerName = "Кольт";
                maxPlayerHp = 3000;
                playerDamage = 1600;
                superDamage = 3600;
                break;
            case 3:
                playerName = "Ель Прімо";
                maxPlayerHp = 6000;
                playerDamage = 1100;
                superDamage = 2200;
                break;
        }

        int playerHp = maxPlayerHp;
        int bossHp = 10000;
        int ammo = 3;
        int superCharge = 0;
        int powerCubes = 0;
        int round = 0;

        boolean bossWeakened = false;
        boolean bossStunned = false;

        System.out.println("Ви обрали бійця: " + playerName);
        System.out.println("Битва починається!");

        while (playerHp > 0 && bossHp > 0) {
            round++;

            System.out.println();
            System.out.println("----------- РАУНД " + round + " -----------");
            System.out.println(playerName + ": " + playerHp + "/" + maxPlayerHp + " HP");
            System.out.println("Робо-Бос: " + bossHp + "/10000 HP");
            System.out.println("Набої: " + ammo + "/3");
            System.out.println("Заряд супера: " + superCharge + "%");
            System.out.println("Енергетичні куби: " + powerCubes);

            System.out.println("Оберіть дію:");
            System.out.println("1 - Основна атака");
            System.out.println("2 - Суператака");
            System.out.println("3 - Захист, перезаряджання та лікування");
            System.out.println("4 - Пошук енергетичного куба");
            System.out.print("Ваш хід: ");

            int action = scanner.nextInt();
            double damageMultiplier = 1 + powerCubes * 0.15;

            switch (action) {
                case 1:
                    if (ammo > 0) {
                        int damage = (int) (playerDamage * damageMultiplier);
                        bossHp -= damage;
                        ammo--;
                        superCharge += 35;

                        if (superCharge > 100) {
                            superCharge = 100;
                        }

                        System.out.println("Ви завдали " + damage + " шкоди!");
                    } else {
                        System.out.println("Немає набоїв! Перезарядіться.");
                    }
                    break;

                case 2:
                    if (superCharge >= 100) {
                        int damage = (int) (superDamage * damageMultiplier);
                        bossHp -= damage;
                        superCharge = 0;

                        System.out.println("СУПЕРАТАКА! Шкода: " + damage);
                        if (choice == 1) {
                            bossWeakened = true;
                            System.out.println("Наступна атака боса буде слабшою!");
                        } else if (choice == 3) {
                            bossStunned = true;
                            System.out.println("Бос оглушений і пропустить хід!");
                        }
                    } else {
                        System.out.println("Супер ще не заряджений!");
                    }
                    break;

                case 3:
                    ammo += 2;

                    if (ammo > 3) {
                        ammo = 3;
                    }

                    playerHp += (int) (maxPlayerHp * 0.15);

                    if (playerHp > maxPlayerHp) {
                        playerHp = maxPlayerHp;
                    }

                    System.out.println("Набої та здоров'я відновлено!");
                    break;

                case 4:
                    if (Math.random() < 0.6) {
                        powerCubes++;
                        System.out.println("Ви знайшли енергетичний куб!");
                    } else {
                        System.out.println("Куб не знайдено.");
                    }
                    break;

                default:
                    System.out.println("Такої дії немає. Хід втрачено.");
                    break;
            }

            if (bossHp < 0) {
                bossHp = 0;
            }

            if (bossHp > 0) {
                System.out.println("Хід Робо-Боса:");

                if (bossStunned) {
                    System.out.println("Бос пропускає хід!");
                    bossStunned = false;
                } else {
                    int bossDamage = 700;

                    if (bossHp <= 6000 && bossHp >= 3000) {
                        bossDamage = (int) (700 * 1.3);
                        System.out.println("Фаза ANGRY!");
                    } else if (bossHp < 3000) {
                        bossDamage = (int) (700 * 1.7);
                        System.out.println("Фаза ENRAGED!");
                    }

                    if (bossWeakened) {
                        bossDamage = (int) (bossDamage * 0.5);
                        bossWeakened = false;
                    }

                    if (Math.random() < 0.15) {
                        System.out.println("Ви ухилилися від атаки!");
                    } else {
                        playerHp -= bossDamage;
                        System.out.println("Бос завдав " + bossDamage + " шкоди!");
                    }
                }

                if (playerHp < 0) {
                    playerHp = 0;
                }

                if (ammo < 3) {
                    ammo++;
                }
            }
        }

        System.out.println();

        if (bossHp <= 0) {
            System.out.println("ПЕРЕМОГА! РОБО-БОСА ЗНИЩЕНО!");
            System.out.println("Кількість раундів: " + round);
            System.out.println("Зібрано кубів: " + powerCubes);
            System.out.println("Нагорода: +10 трофеїв та 100 Starr Drops!");
        } else {
            System.out.println("ПОРАЗКА! Ваш боєць програв.");
        }

        scanner.close();
    }
}