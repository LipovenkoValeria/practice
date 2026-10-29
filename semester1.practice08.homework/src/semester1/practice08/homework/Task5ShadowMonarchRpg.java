package semester1.practice08.homework;

class DungeonEntity {
    private String name;
    private int level;
    protected int hp;
    protected int maxHp;
    protected int baseAttack;
    protected int defense;

    public DungeonEntity(String name, int level, int maxHp,
                         int baseAttack, int defense) {
        this.name = name == null || name.isBlank() ? "Невідомий" : name;
        this.level = Math.max(1, level);
        this.maxHp = Math.max(1, maxHp);
        this.hp = this.maxHp;
        this.baseAttack = Math.max(1, baseAttack);
        this.defense = Math.max(0, defense);
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getBaseAttack() {
        return baseAttack;
    }

    public int getDefense() {
        return defense;
    }

    public int takeDamage(int rawDamage) {
        int actual = Math.max(5, rawDamage - defense);
        hp = Math.max(0, hp - actual);
        return actual;
    }

    public int performAttack() {
        return baseAttack;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    protected String buildHealthBar() {
        int filled = (int) Math.round(10.0 * hp / maxHp);
        filled = Math.max(0, Math.min(10, filled));

        StringBuilder bar = new StringBuilder("[");

        for (int i = 0; i < 10; i++) {
            bar.append(i < filled ? "█" : "░");
        }

        bar.append("]");
        return bar.toString();
    }

    protected void increaseLevel() {
        level++;
    }

    @Override
    public String toString() {
        return String.format(
                "DungeonEntity{name='%s', lvl=%d, HP=%s %d/%d, ATK=%d, DEF=%d}",
                name, level, buildHealthBar(), hp, maxHp, baseAttack, defense
        );
    }
}

class Hunter extends DungeonEntity {
    private char rank;
    protected int mana;
    protected int maxMana;

    public Hunter(String name, int level, int maxHp, int baseAttack,
                  int defense, char rank, int maxMana) {
        super(name, level, maxHp, baseAttack, defense);
        this.rank = rank;
        this.maxMana = Math.max(0, maxMana);
        this.mana = this.maxMana;
    }

    public char getRank() {
        return rank;
    }

    public int getMana() {
        return mana;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public int useManaSkill(String skillName, int manaCost, double multiplier) {
        if (manaCost >= 0 && mana >= manaCost) {
            mana -= manaCost;
            System.out.println("✨ Використано навичку: " + skillName);
            return (int) (performAttack() * multiplier);
        }

        System.out.println("Недостатньо мани! Виконується звичайна атака.");
        return performAttack();
    }

    @Override
    public String toString() {
        return String.format(
                "Hunter{%s | Ранг: [%c] | MP: %d/%d}",
                super.toString(), rank, mana, maxMana
        );
    }
}

class DungeonBoss extends DungeonEntity {
    private String bossTitle;
    private boolean enraged;

    public DungeonBoss(String name, int level, int maxHp, int baseAttack,
                       int defense, String bossTitle) {
        super(name, level, maxHp, baseAttack, defense);
        this.bossTitle = bossTitle;
        this.enraged = false;
    }

    @Override
    public int takeDamage(int rawDamage) {
        int actual = super.takeDamage(rawDamage);

        if (hp > 0 && hp <= maxHp * 0.4 && !enraged) {
            enraged = true;
            System.out.println(
                    "🔥 БОС ВПАДАЄ У ФАЗУ ЛЮТІ! Атака зросла на 50%!"
            );
        }

        return actual;
    }

    @Override
    public int performAttack() {
        if (enraged) {
            return (int) (super.performAttack() * 1.5);
        }

        return super.performAttack();
    }
    @Override
    public String toString() {
        return String.format(
                "DungeonBoss{%s | Титул: '%s', Лють: %b}",
                super.toString(), bossTitle, enraged
        );
    }
}

class ShadowMonarch extends Hunter {
    private String[] shadowNames = new String[5];
    private int[] shadowBonuses = new int[5];
    private int shadowCount = 0;

    public ShadowMonarch(String name, int level, int maxHp, int maxMana,
                         int baseAttack, int defense) {
        super(name, level, maxHp, baseAttack, defense, 'S', maxMana);
    }

    public boolean arise(String shadowName, int bonusAtk) {
        if (mana < 30) {
            System.out.println("Недостатньо мани для призову тіні!");
            return false;
        }

        if (shadowCount >= shadowNames.length) {
            System.out.println("Тіньова Армія вже заповнена!");
            return false;
        }

        if (shadowName == null || shadowName.isBlank() || bonusAtk < 0) {
            System.out.println("Неправильні дані тіні.");
            return false;
        }

        mana -= 30;
        shadowNames[shadowCount] = shadowName;
        shadowBonuses[shadowCount] = bonusAtk;
        shadowCount++;

        System.out.printf(
                "🌑 Тінь [%s (+%d ATK)] приєдналася до легіону! Тіней: %d/5%n",
                shadowName, bonusAtk, shadowCount
        );

        return true;
    }

    public int getTotalShadowBonus() {
        int total = 0;

        for (int i = 0; i < shadowCount; i++) {
            total += shadowBonuses[i];
        }

        return total;
    }

    @Override
    public int performAttack() {
        return super.performAttack() + getTotalShadowBonus();
    }

    public void levelUp() {
        increaseLevel();
        maxHp += 150;
        hp = maxHp;
        maxMana += 50;
        mana = maxMana;
        baseAttack += 25;

        System.out.println("[СИСТЕМА: РІВЕНЬ ПІДВИЩЕНО!]");
        System.out.println("Новий рівень: " + getLevel());
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("ShadowMonarch{").append(super.toString()).append("}\n");
        result.append("  Тіньовий Легіон (")
                .append(shadowCount).append("/5): ");

        if (shadowCount == 0) {
            result.append("поки що порожній");
        } else {
            result.append("[");

            for (int i = 0; i < shadowCount; i++) {
                if (i > 0) {
                    result.append(", ");
                }

                result.append(i + 1).append(". ")
                        .append(shadowNames[i])
                        .append(" (+").append(shadowBonuses[i]).append(" ATK)");
            }

            result.append("]");
        }

        result.append(" | Сумарний бонус тіней: +")
                .append(getTotalShadowBonus()).append(" ATK");

        return result.toString();
    }
}

public class Task5ShadowMonarchRpg {
    public static void main(String[] args) {
        ShadowMonarch monarch = new ShadowMonarch(
                "Сон Джін-Ву", 45, 1200, 300, 180, 45
        );

        DungeonBoss knight = new DungeonBoss(
                "Лицар Ігріс", 35, 450, 100, 20, "Елітний лицар"
        );

        DungeonBoss boss = new DungeonBoss(
                "Король Мурах Беру", 80, 2500, 260, 60,
                "Кошмар Острова Чеджу"
        );

        System.out.println("================ ⚔️ SOLO LEVELING: ПІДЗЕМЕЛЛЯ S-РАНГУ ⚔️ ================");
        System.out.println("[ДОСЬЄ МИСЛИВЦЯ]:");
        System.out.println(monarch);
        System.out.println();
        System.out.println("[ЕЛІТНИЙ ЛИЦАР]:");
        System.out.println(knight);
        System.out.println();
        System.out.println("[ГОЛОВНИЙ БОС]:");
        System.out.println(boss);

        System.out.println("\n--- БИТВА З ЕЛІТНИМ ЛИЦАРЕМ ---");

        int round = 1;

        while (knight.isAlive() && monarch.isAlive()) {
            System.out.println("--- Раунд " + round + " ---");
            int damage = knight.takeDamage(monarch.performAttack());
            System.out.printf(
                    "🗡️ Монарх атакує Ігріса! Шкода: %d, HP лицаря: %d/%d%n",
                    damage, knight.getHp(), knight.getMaxHp()
            );

            if (knight.isAlive()) {
                int received = monarch.takeDamage(knight.performAttack());
                System.out.printf(
                        "💥 Ігріс атакує у відповідь! Шкода: %d, HP Монарха: %d/%d%n",
                        received, monarch.getHp(), monarch.getMaxHp()
                );
            }

            round++;
        }

        if (!monarch.isAlive()) {
            System.out.println("Монарх не зміг завершити рейд.");
            return;
        }

        System.out.println("🏆 Елітного лицаря переможено!");
        monarch.arise("Лицар Ігріс", 90);

        System.out.println("\n--- БИТВА З ГОЛОВНИМ БОСОМ ---");
        round = 1;

        while (boss.isAlive() && monarch.isAlive()) {
            System.out.println("--- Раунд " + round + " ---");

            int attackDamage = monarch.useManaSkill(
                    "Удар Монарха", 20, 1.5
            );

            int damage = boss.takeDamage(attackDamage);
            System.out.printf(
                    "🗡️ Монарх та його легіон завдали %d шкоди. HP боса: %d/%d%n",
                    damage, boss.getHp(), boss.getMaxHp()
            );

            if (boss.isAlive()) {
                int received = monarch.takeDamage(boss.performAttack());
                System.out.printf(
                        "💥 Бос атакує! Монарх отримав %d шкоди. HP: %d/%d%n",
                        received, monarch.getHp(), monarch.getMaxHp()
                );
            }

            round++;
        }

        if (monarch.isAlive() && !boss.isAlive()) {
            System.out.println("\n🏆 ПЕРЕМОГА! Головного боса подолано!");
            monarch.levelUp();
            monarch.arise("Король Мурах Беру", 180);
        } else {
            System.out.println("\nРейд завершився поразкою.");
        }

        System.out.println("\n[ФІНАЛЬНЕ ДОСЬЄ МОНАРХА]:");
        System.out.println(monarch);
        System.out.println("=========================================================================");
    }
}