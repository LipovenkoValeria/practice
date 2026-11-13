package semester1.practice10.homework;

interface FlyingMonster {
    boolean isAirborne();
    void knockDownToGround();
}

interface RegeneratingMonster {
    int regenerateHealth();
    void applyMoonDustBomb();
}

interface SpectralPhantom {
    boolean isIncorporeal();
    void bindWithYrdenSign();
}

abstract class AbstractMonster {

    private final String name, category;
    protected int hp;
    protected final int maxHp;

    AbstractMonster(String n, String c, int h) {
        name = n;
        category = c;
        hp = maxHp = h;
    }

    String getName() {
        return name;
    }

    int getHp() {
        return hp;
    }

    int getMaxHp() {
        return maxHp;
    }

    abstract int calculateMonsterAttack();

    abstract String getBattleCry();

    public final boolean isAlive() {
        return hp > 0;
    }

    public void receiveDamage(int d) {
        hp = Math.max(0, hp - Math.max(0, d));
    }

    public String toString() {
        return name + " [" + category + " | HP: " + hp + "/" + maxHp + "]";
    }
}

class RoyalGriffin extends AbstractMonster implements FlyingMonster {

    private boolean air = true;

    RoyalGriffin() {
        super("Королівський Грифон", "Гібриди", 400);
    }

    int calculateMonsterAttack() {
        return 100;
    }

    String getBattleCry() {
        return "Грифон кричить";
    }

    public boolean isAirborne() {
        return air;
    }

    public void knockDownToGround() {
        air = false;
        System.out.println("Аард збиває грифона на землю!");
    }
}

class Noonwraith extends AbstractMonster implements SpectralPhantom {

    private boolean ghost = true;

    Noonwraith() {
        super("Полудниця з Білого Саду", "Примари", 300);
    }

    int calculateMonsterAttack() {
        return 75;
    }

    String getBattleCry() {
        return "Полудниця стогне";
    }

    public boolean isIncorporeal() {
        return ghost;
    }

    public void bindWithYrdenSign() {
        ghost = false;
        System.out.println("Ірден матеріалізує примару!");
    }
}

class HigherVampire extends AbstractMonster
        implements FlyingMonster, RegeneratingMonster {

    private boolean air = true;
    private boolean blocked;

    HigherVampire() {
        super("Вищий Вампір Детлафф", "Вампіри", 650);
    }

    int calculateMonsterAttack() {
        return 160;
    }

    String getBattleCry() {
        return "Детлафф реве";
    }

    public boolean isAirborne() {
        return air;
    }

    public void knockDownToGround() {
        air = false;
        System.out.println("Аард перериває піке вампіра!");
    }

    public int regenerateHealth() {

        if (blocked) {
            return 0;
        }

        int n = Math.min(120, maxHp - hp);
        hp += n;

        return n;
    }

    public void applyMoonDustBomb() {
        blocked = true;
        System.out.println("Місячний пил блокує регенерацію!");
    }
}

final class AncientLeshen extends AbstractMonster
        implements RegeneratingMonster {

    private boolean blocked;

    AncientLeshen() {
        super("Прадавній Лісовик", "Релікти", 700);
    }

    int calculateMonsterAttack() {
        return 140;
    }

    String getBattleCry() {
        return "Лісовик кличе коріння";
    }

    public int regenerateHealth() {

        if (blocked) {
            return 0;
        }

        int n = Math.min(80, maxHp - hp);
        hp += n;

        return n;
    }

    public void applyMoonDustBomb() {
        blocked = true;
    }
}

class WitcherHunter {

    private final int sword = 220;
    private final int sign = 150;

    void applyBestiaryTactics(AbstractMonster t) {

        if (t instanceof SpectralPhantom p && p.isIncorporeal()) {
            p.bindWithYrdenSign();
        }

        if (t instanceof FlyingMonster f && f.isAirborne()) {
            f.knockDownToGround();
        }

        if (t instanceof RegeneratingMonster r) {
            r.applyMoonDustBomb();
        }
    }

    public final int executeSilverStrike(AbstractMonster t) {
        applyBestiaryTactics(t);

        int d = (int) Math.round(sword * sign / 100.0);

        if (t instanceof RoyalGriffin f && f.isAirborne()) {
            d /= 2;
        }

        if (t instanceof SpectralPhantom p && p.isIncorporeal()) {
            d = (int) (d * 0.2);
        }

        t.receiveDamage(d);

        System.out.println("Срібний меч завдає " + d
                + " шкоди; HP: " + t.getHp() + "/" + t.getMaxHp());

        return d;
    }

    void printInfo() {
        System.out.println("Ґеральт із Рівії | Школа Вовка | Срібний меч: "
                + sword + " | Знаки: " + sign + "%");
    }
}

public class Task5WitcherBestiaryAndMutagenSim {

    public static void main(String[] z) {

        AbstractMonster[] contracts = {
                new RoyalGriffin(),
                new Noonwraith(),
                new HigherVampire(),
                new AncientLeshen()
        };

        WitcherHunter g = new WitcherHunter();

        System.out.println("=== WITCHER 3: BESTIARY & POLYMORPHIC HUNT ===");

        g.printInfo();

        for (int i = 0; i < contracts.length; i++) {

            System.out.println("\nКОНТРАКТ №" + (i + 1) + ": "
                    + contracts[i]);

            g.executeSilverStrike(contracts[i]);
        }
    }
}