package semester1.practice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;

abstract class Payment {
    private final String id;
    private final String shopName;
    private final double amount;

    public Payment(String id, String shopName, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Сума повинна бути додатною!");
        }

        this.id = id;
        this.shopName = shopName;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public String getShopName() {
        return shopName;
    }

    public double getAmount() {
        return amount;
    }

    public abstract double calculateFee();

    public double getNetAmount() {
        return amount - calculateFee();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Payment)) {
            return false;
        }

        Payment payment = (Payment) o;

        return Objects.equals(id, payment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

class CardPayment extends Payment {

    public CardPayment(String id, String shopName, double amount) {
        super(id, shopName, amount);
    }

    @Override
    public double calculateFee() {
        return getAmount() * 0.015;
    }
}

class TransferPayment extends Payment {

    public TransferPayment(String id, String shopName, double amount) {
        super(id, shopName, amount);
    }

    @Override
    public double calculateFee() {
        return getAmount() * 0.02 + 20.0;
    }
}

class PaymentProcessor {
    private ArrayList<Payment> payments = new ArrayList<>();
    private HashSet<String> blockedShops;
    private double maxLimit;

    public PaymentProcessor(HashSet<String> blockedShops, double maxLimit) {
        this.blockedShops = new HashSet<>(blockedShops);
        this.maxLimit = maxLimit;
    }

    public void addPayment(Payment p) {
        payments.add(p);
    }

    public HashSet<String> getBlockedShopsCopy() {
        return new HashSet<>(blockedShops);
    }

    public ArrayList<Payment> getPaymentsCopy() {
        return new ArrayList<>(payments);
    }

    public int cleanPayments() {
        int removed = 0;

        HashSet<String> seenIds = new HashSet<>();

        Iterator<Payment> it = payments.iterator();

        while (it.hasNext()) {
            Payment p = it.next();

            if (!seenIds.add(p.getId())) {
                System.out.println("  [ДУБЛІКАТ] " + p.getId()
                        + " | " + p.getShopName() + " — видалено!");

                it.remove();
                removed++;

            } else if (blockedShops.contains(p.getShopName())) {
                System.out.println("  [ЧОРНИЙ СПИСОК] " + p.getId()
                        + " | " + p.getShopName() + " — видалено!");

                it.remove();
                removed++;

            } else if (p.getAmount() > maxLimit) {
                System.out.println("  [ПОНАД ЛІМІТ] " + p.getId()
                        + " | " + p.getShopName() + " — видалено!");

                it.remove();
                removed++;
            }
        }

        return removed;
    }

    public double getTotalGross() {
        double total = 0;

        for (Payment p : payments) {
            total += p.getAmount();
        }

        return total;
    }

    public double getTotalFees() {
        double total = 0;

        for (Payment p : payments) {
            total += p.calculateFee();
        }

        return total;
    }

    public HashSet<String> getApprovedShops() {
        HashSet<String> shops = new HashSet<>();

        for (Payment p : payments) {
            shops.add(p.getShopName());
        }

        return shops;
    }
}

public class Task2PaymentFilterAndAudit {

    public static void main(String[] args) {

        System.out.println("=============== БАНКІВСЬКИЙ ПЛАТІЖНИЙ ФІЛЬТР ===============");

        HashSet<String> blockedShops = new HashSet<>();

        blockedShops.add("ScamShop");
        blockedShops.add("FakeStore");

        PaymentProcessor processor =
                new PaymentProcessor(blockedShops, 20000.0);

        processor.addPayment(new CardPayment("P-01", "Rozetka", 4000.0));
        processor.addPayment(new TransferPayment("P-02", "Comfy", 10000.0));
        processor.addPayment(new CardPayment("P-03", "ScamShop", 5000.0));
        processor.addPayment(new CardPayment("P-04", "LuxWatch", 25000.0));
        processor.addPayment(new CardPayment("P-05", "Rozetka", 2000.0));
        processor.addPayment(new TransferPayment("P-06", "Allo", 5000.0));
        processor.addPayment(new CardPayment("P-01", "Rozetka", 4000.0));

        System.out.println("Чорний список магазинів: "
                + processor.getBlockedShopsCopy());

        System.out.println("Ліміт одного платежу: 20000.00 грн");
        System.out.println("Вхідних платежів: "
                + processor.getPaymentsCopy().size());

        System.out.println("\n--- Очищення через Iterator.remove() ---");

        int removed = processor.cleanPayments();

        ArrayList<Payment> approved = processor.getPaymentsCopy();

        System.out.println("\n--- Схвалені платежі ---");

        for (Payment p : approved) {
            System.out.println("  " + p.getId()
                    + " | " + p.getShopName()
                    + " | Сума: " + p.getAmount()
                    + " | Комісія: " + p.calculateFee()
                    + " | Нетто: " + p.getNetAmount() + " грн");
        }

        double gross = processor.getTotalGross();
        double fees = processor.getTotalFees();
        double net = gross - fees;

        System.out.println("\n--- Фінансовий підсумок ---");

        System.out.println("Видалено платежів: " + removed);
        System.out.println("Схвалено: " + approved.size());
        System.out.println("Загальна сума: " + gross + " грн");
        System.out.println("Загальна комісія: " + fees + " грн");
        System.out.println("Чиста сума: " + net + " грн");
        System.out.println("Унікальні магазини: "
                + processor.getApprovedShops());

        System.out.println("============================================================");
    }
}