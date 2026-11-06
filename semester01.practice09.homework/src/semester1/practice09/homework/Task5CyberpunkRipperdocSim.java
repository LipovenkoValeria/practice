package semester1.practice09.homework;

class BioMonitor {
    private int hp;
    private final int maxHp;
    private int humanity = 100;
    private boolean cyberpsychosis = false;

    BioMonitor(int max) {
        hp = maxHp = max;
    }

    void reduceHumanity(int c) {
        humanity = Math.max(0, humanity - c);

        if (humanity < 20)
            cyberpsychosis = true;
    }

    void injectImmunoblockers(int n) {
        humanity = Math.min(100, humanity + n);

        if (humanity >= 20)
            cyberpsychosis = false;
    }

    int applyDamage(int raw, int armor) {
        int d = Math.max(5, raw - armor);
        hp = Math.max(0, hp - d);
        return d;
    }

    boolean isCyberpsychosis() {
        return cyberpsychosis;
    }

    int getHp() {
        return hp;
    }

    void overload() {
        if (cyberpsychosis)
            hp = Math.max(0, hp - (int) Math.ceil(maxHp * .10));
    }

    public String toString() {
        return "HP=" + hp + "/" + maxHp
                + " Humanity=" + humanity + "/100 "
                + (cyberpsychosis ? "CYBERPSYCHOSIS" : "Stable");
    }
}

abstract class AbstractCyberImplant {
    private final String name, slot;
    private final int humanityCost;

    AbstractCyberImplant(String n, String s, int c) {
        name = n;
        slot = s;
        humanityCost = c;
    }

    String getName() { return name; }
    String getSlot() { return slot; }
    int getHumanityCost() { return humanityCost; }

    abstract int getAttackBonus();
    abstract int getArmorBonus();
    abstract String activateSpecialAbility();

    public String toString() {
        return slot + ": " + name
                + " +" + getAttackBonus() + " ATK +"
                + getArmorBonus() + " DEF";
    }
}

class SandevistanOs extends AbstractCyberImplant {
    SandevistanOs() {
        super("Militech Sandevistan Apogee", "Нейро-ОС", 35);
    }

    int getAttackBonus() { return 90; }
    int getArmorBonus() { return 20; }

    String activateSpecialAbility() {
        return "САНДЕВІСТАН АКТИВОВАНО: час сповільнено!";
    }
}

class MantisBlades extends AbstractCyberImplant {
    MantisBlades() {
        super("Термальні Клинки Богомола", "Руки", 25);
    }

    int getAttackBonus() { return 110; }
    int getArmorBonus() { return 5; }

    String activateSpecialAbility() {
        return "Клинки Богомола активовано!";
    }
}

class SubdermalTitaniumArmor extends AbstractCyberImplant {
    SubdermalTitaniumArmor() {
        super("Підшкірна Титанова Броня", "Шкіра", 20);
    }

    int getAttackBonus() { return 10; }
    int getArmorBonus() { return 65; }

    String activateSpecialAbility() {
        return "Нано-пластини поглинають удар!";
    }
}

class CyberMercenary {
    private final String nickname;
    private final int baseAttack;
    private final BioMonitor bioMonitor;
    private final AbstractCyberImplant[] implants =
            new AbstractCyberImplant[4];

    private int implantCount;

    CyberMercenary(String n, int atk, int hp) {
        nickname = n;
        baseAttack = atk;
        bioMonitor = new BioMonitor(hp);
    }

    BioMonitor getBioMonitor() {
        return bioMonitor;
    }

    boolean installImplant(AbstractCyberImplant x) {
        if (x == null || implantCount == 4)
            return false;

        for (int i = 0; i < implantCount; i++) {
            if (implants[i].getSlot().equals(x.getSlot())) {
                System.out.println("Слот зайнятий: " + x.getSlot());
                return false;
            }
        }

        implants[implantCount++] = x;
        bioMonitor.reduceHumanity(x.getHumanityCost());

        System.out.println("Встановлено: " + x);
        return true;
    }

    int getTotalAttack() {
        int s = baseAttack;

        for (int i = 0; i < implantCount; i++)
            s += implants[i].getAttackBonus();

        return bioMonitor.isCyberpsychosis()
                ? (int) (s * 1.5) : s;
    }

    int getTotalArmor() {
        int s = 0;
        for (int i = 0; i < implantCount; i++)
            s += implants[i].getArmorBonus();

        return s;
    }

    void triggerFullOverdrive() {
        for (int i = 0; i < implantCount; i++)
            System.out.println(implants[i].activateSpecialAbility());
    }

    void endTurn() {
        bioMonitor.overload();
    }

    public String toString() {
        StringBuilder s = new StringBuilder(
                nickname + " ATK=" + getTotalAttack()
                        + " DEF=" + getTotalArmor() + "\n"
                        + bioMonitor + "\n");

        for (int i = 0; i < implantCount; i++)
            s.append(implants[i]).append('\n');

        return s.toString();
    }
}

public class Task5CyberpunkRipperdocSim {
    public static void main(String[] args) {
        CyberMercenary d = new CyberMercenary(
                "Девід Мартінес", 120, 1000);

        System.out.println(d.getBioMonitor());

        d.installImplant(new SandevistanOs());
        d.installImplant(new MantisBlades());
        d.installImplant(new SandevistanOs());
        d.installImplant(new SubdermalTitaniumArmor());

        d.getBioMonitor().reduceHumanity(1);

        System.out.println(d);

        int enemy = 1500;

        for (int r = 1;
             r <= 4 && enemy > 0 && d.getBioMonitor().getHp() > 0;
             r++) {

            System.out.println("Раунд " + r);

            if (r == 1)
                d.triggerFullOverdrive();

            enemy = Math.max(0, enemy - d.getTotalAttack());

            d.getBioMonitor().applyDamage(
                    180, d.getTotalArmor());

            d.endTurn();

            System.out.println(
                    "Adam Smasher HP=" + enemy
                            + " | " + d.getBioMonitor());

            if (r == 2)
                d.getBioMonitor().injectImmunoblockers(30);
        }
    }
}
