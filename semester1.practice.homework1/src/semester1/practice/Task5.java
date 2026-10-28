package semester1.practice;

import java.util.Scanner;

class Asta {
    private String name = "Аста";
    private int hp = 1000;
    private int maxHp = 1000;
    private int antiMagicEnergy = 20;
    private int baseAttack = 120;
    private int defense = 35;
    private boolean isBlackFormActive = false;
    private boolean limitsUsed = false;

    private String[] skills = {
            "Удар Демоноборця",
            "Чорний Ураган",
            "Метеорит Демона",
            "Форма Чорного Демона"
    };

    public void showStatus() {
        System.out.println("Герой: " + name);
        System.out.print("Здоров'я: [");

        int filled = hp * 10 / maxHp;

        for (int i = 0; i < 10; i++) {
            if (i < filled) {
                System.out.print("#");
            } else {
                System.out.print(".");
            }
        }

        System.out.println("] " + hp + "/" + maxHp);
        System.out.println("Антимагія: " + antiMagicEnergy + "/100");
        System.out.println("Чорна форма активна: " + isBlackFormActive);
    }

    public int basicSwordAttack() {
        int damage = baseAttack;

        if (isBlackFormActive) {
            damage = damage * 2;
        }

        antiMagicEnergy = antiMagicEnergy + 20;

        if (antiMagicEnergy > 100) {
            antiMagicEnergy = 100;
        }

        return damage;
    }

    public int useSkill(int skillIndex) {
        if (skillIndex < 0 || skillIndex >= skills.length) {
            System.out.println("Помилка: такої навички немає!");
            return 0;
        }

        int requiredEnergy;
        int damage;

        if (skillIndex == 0) {
            requiredEnergy = 25;
            damage = 220;
        } else if (skillIndex == 1) {
            requiredEnergy = 50;
            damage = 380;
        } else if (skillIndex == 2) {
            requiredEnergy = 75;
            damage = 550;
        } else {
            requiredEnergy = 100;
            damage = 0;
        }

        if (antiMagicEnergy < requiredEnergy) {
            System.out.println("Недостатньо енергії антимагії!");
            return 0;
        }

        antiMagicEnergy = antiMagicEnergy - requiredEnergy;

        System.out.println(name + " використовує навичку: " + skills[skillIndex]);

        if (skillIndex == 3) {
            isBlackFormActive = true;
            hp = hp + 300;

            if (hp > maxHp) {
                hp = maxHp;
            }

            System.out.println("Форма Чорного Демона активована!");
            System.out.println("Сила подальших атак подвоєна!");
            return 0;
        }

        if (isBlackFormActive) {
            damage = damage * 2;
        }

        return damage;
    }

    public void takeDamage(int incomingDmg) {
        int realDamage = incomingDmg - defense;

        if (realDamage < 15) {
            realDamage = 15;
        }

        hp = hp - realDamage;

        if (hp <= 0) {
            hp = 0;
        }

        System.out.println(name + " отримує " + realDamage + " шкоди!");
    }

    public void pushLimits() {
        if (hp < maxHp * 0.25 && !limitsUsed) {
            limitsUsed = true;

            hp = hp + 150;

            if (hp > maxHp) {
                hp = maxHp;
            }

            antiMagicEnergy = antiMagicEnergy + 50;

            if (antiMagicEnergy > 100) {
                antiMagicEnergy = 100;
            }

            System.out.println("Моя магія — ніколи не здаватися!");
            System.out.println("Аста відновлює здоров'я та енергію!");
        }
    }

    public boolean isAlive() {
        return hp > 0;
    }
}

public class Task5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Asta asta = new Asta();

        String bossName = "Патри";
        int bossHp = 1200;
        final int BOSS_ATTACK = 120;

        int round = 1;
        System.out.println("=============== АРЕНА КОРОЛІВСТВА КОНЮШИНИ ===============");
        System.out.println("Бос: " + bossName + " [HP: " + bossHp + "/1200]");
        asta.showStatus();

        while (asta.isAlive() && bossHp > 0) {
            System.out.println();
            System.out.println("--- Раунд " + round + " ---");

            System.out.println("Оберіть дію Асти:");
            System.out.println("[1] Удар мечем");
            System.out.println("[2] Застосувати суперздібність");
            System.out.println("[3] Захисна стійка");
            System.out.println("[4] Переглянути статус");
            System.out.print("Ваш вибір: ");

            int choice = scanner.nextInt();
            boolean defensiveStance = false;

            if (choice == 1) {
                int damage = asta.basicSwordAttack();
                bossHp = bossHp - damage;

                if (bossHp < 0) {
                    bossHp = 0;
                }

                System.out.println("Аста атакує мечем Демоноборця!");
                System.out.println("Завдано шкоди: " + damage);
            } else if (choice == 2) {
                System.out.println("Оберіть навичку:");

                System.out.println("[0] Удар Демоноборця — 25 енергії");
                System.out.println("[1] Чорний Ураган — 50 енергії");
                System.out.println("[2] Метеорит Демона — 75 енергії");
                System.out.println("[3] Форма Чорного Демона — 100 енергії");
                System.out.print("Номер навички: ");

                int skillIndex = scanner.nextInt();
                int damage = asta.useSkill(skillIndex);

                bossHp = bossHp - damage;

                if (bossHp < 0) {
                    bossHp = 0;
                }

                if (damage > 0) {
                    System.out.println("Завдано шкоди: " + damage);
                }
            } else if (choice == 3) {
                defensiveStance = true;
                System.out.println("Аста займає захисну стійку!");
            } else if (choice == 4) {
                asta.showStatus();
            } else {
                System.out.println("Невірний вибір!");
            }

            if (bossHp > 0 && asta.isAlive()) {
                System.out.println();
                System.out.println(bossName + " атакує магічними променями!");

                int incomingDamage = BOSS_ATTACK;

                if (defensiveStance) {
                    incomingDamage = incomingDamage / 2;
                    System.out.println("Захисна стійка зменшує силу удару!");
                }

                asta.takeDamage(incomingDamage);
                asta.pushLimits();
            }

            System.out.println();
            System.out.println("Аста: ");
            asta.showStatus();
            System.out.println(bossName + ": HP " + bossHp + "/1200");

            round++;
        }

        System.out.println();
        System.out.println("================== КІНЕЦЬ БОЮ ==================");

        if (asta.isAlive()) {
            System.out.println("Перемога! Аста здолав " + bossName + "!");
        } else {
            System.out.println("Аста програв бій...");
        }

        scanner.close();
    }
}