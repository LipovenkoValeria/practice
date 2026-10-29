package semester1.practice06.homework;

public class Asta {
    private String name = "Аста";
    private int hp = 1000;
    private int maxHp = 1000;
    private int antiMagicEnergy = 0;
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
        System.out.println("Здоров'я: " + hp + "/" + maxHp);

        int filled = hp * 10 / maxHp;
        System.out.print("[");

        for (int i = 0; i < 10; i++) {
            if (i < filled) {
                System.out.print("█");
            } else {
                System.out.print(".");
            }
        }

        System.out.println("] " + (hp * 100 / maxHp) + "%");
        System.out.println("Антимагія: " + antiMagicEnergy + "/100");
        System.out.println("Чорна форма: "
                + (isBlackFormActive ? "Активна" : "Неактивна"));
    }

    public int basicSwordAttack() {
        int damage = baseAttack;

        if (isBlackFormActive) {
            damage = damage * 2;
        }

        antiMagicEnergy = Math.min(100, antiMagicEnergy + 20);

        System.out.println("Аста атакує мечем Демоноборця!");
        System.out.println("Завдано " + damage + " шкоди.");
        System.out.println("Антимагія: " + antiMagicEnergy + "/100");

        return damage;
    }

    public int useSkill(int skillIndex) {
        if (skillIndex < 0 || skillIndex >= skills.length) {
            System.out.println("Неправильний номер навички!");
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

        System.out.println("Аста використовує навичку: "
                + skills[skillIndex] + "!");

        if (skillIndex == 3) {
            isBlackFormActive = true;
            hp = Math.min(maxHp, hp + 300);
            System.out.println("Форма Чорного Демона активована!");
            System.out.println("Аста відновив здоров'я.");
            return 0;
        }

        if (isBlackFormActive) {
            damage = damage * 2;
        }

        System.out.println("Завдано " + damage + " шкоди.");
        return damage;
    }

    public void takeDamage(int incomingDmg) {
        int realDamage = Math.max(15, incomingDmg - defense);
        hp = hp - realDamage;

        if (hp <= 0) {
            hp = 0;
        }

        System.out.println("Аста отримує " + realDamage + " шкоди.");
    }

    public void pushLimits() {
        if (!limitsUsed && hp > 0 && hp < maxHp * 0.25) {
            limitsUsed = true;
            hp = Math.min(maxHp, hp + 150);
            antiMagicEnergy = Math.min(100, antiMagicEnergy + 50);

            System.out.println("Моя магія — ніколи не здаватися!");
            System.out.println("Аста відновлює 150 HP та отримує 50 енергії антимагії!");
        }
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public int getHp() {
        return hp;
    }
}