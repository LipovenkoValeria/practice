package semester1.practice10.homework;

import java.util.Locale;

interface CashbackCapable {
    double MAX_CASHBACK_UAH = 500.0;

    double getCashbackRate();

    default double calculateCashback(double a) {
        return Math.min(MAX_CASHBACK_UAH, a * getCashbackRate());
    }
}

interface Refundable {
    boolean requestRefund(String reason);
    boolean isRefunded();
}

interface BlockchainVerifiable {
    String getTxHash();
    int getConfirmations();

    default boolean isFinalized() {
        return getConfirmations() >= 12;
    }
}

abstract class AbstractPaymentTransaction {
    private final String id;
    private final double amount;
    private boolean processed;

    AbstractPaymentTransaction(String id, double a) {
        this.id = id;
        amount = a;
    }

    public String getTxId() {
        return id;
    }

    public double getBaseAmountUah() {
        return amount;
    }

    public boolean isProcessed() {
        return processed;
    }

    public abstract double calculateFee();

    public abstract String getPaymentChannel();

    public final boolean process() {
        if (amount <= 0 || processed) {
            return false;
        }

        processed = true;
        return true;
    }

    public final double getTotalCharged() {
        return amount + calculateFee();
    }
}

class PremiumCardPayment extends AbstractPaymentTransaction
        implements CashbackCapable, Refundable {

    private boolean refunded;

    PremiumCardPayment(String id, double a) {
        super(id, a);
    }

    public double calculateFee() {
        return getBaseAmountUah() * 0.01;
    }

    public String getPaymentChannel() {
        return "Visa Infinite";
    }

    public double getCashbackRate() {
        return 0.03;
    }

    public boolean requestRefund(String r) {
        if (!isProcessed() || refunded) {
            return false;
        }

        refunded = true;
        System.out.println("  Повернення " + getTxId()
                + " (" + r + "): успішно");

        return true;
    }

    public boolean isRefunded() {
        return refunded;
    }
}

class CryptoUsdtPayment extends AbstractPaymentTransaction
        implements CashbackCapable, BlockchainVerifiable {

    CryptoUsdtPayment(String id, double a) {
        super(id, a);
    }

    public double calculateFee() {
        return 40;
    }

    public String getPaymentChannel() {
        return "USDT TRC-20";
    }

    public double getCashbackRate() {
        return 0.01;
    }

    public String getTxHash() {
        return "0x9f8a...c4";
    }

    public int getConfirmations() {
        return 18;
    }
}

final class TaxTreasuryPayment extends AbstractPaymentTransaction
        implements Refundable {

    private boolean refunded;

    TaxTreasuryPayment(String id, double a) {
        super(id, a);
    }

    public double calculateFee() {
        return 0;
    }

    public String getPaymentChannel() {
        return "ДержКазначейство";
    }

    public boolean requestRefund(String r) {
        if (!isProcessed() || refunded) {
            return false;
        }

        refunded = true;
        return true;
    }

    public boolean isRefunded() {
        return refunded;
    }
}

public class Task2SmartPaymentAndAuditGateway {

    public static void auditLedger(AbstractPaymentTransaction[] ledger) {

        double turnover = 0;
        double fees = 0;
        double cashbackSum = 0;

        System.out.println("[ПРОВЕДЕННЯ ТРАНЗАКЦІЙ]");

        for (AbstractPaymentTransaction t : ledger) {

            if (t.process()) {

                turnover += t.getBaseAmountUah();
                fees += t.calculateFee();

                System.out.printf(Locale.US,
                        "%s | %s | сума %.2f | комісія %.2f | всього %.2f%n",
                        t.getTxId(),
                        t.getPaymentChannel(),
                        t.getBaseAmountUah(),
                        t.calculateFee(),
                        t.getTotalCharged());
            }
        }
        System.out.println("[АУДИТ instanceof]");

        for (AbstractPaymentTransaction t : ledger) {

            if (t instanceof CashbackCapable c) {

                double v = c.calculateCashback(t.getBaseAmountUah());
                cashbackSum += v;

                System.out.printf(Locale.US,
                        "Cashback %s: %.2f грн%n",
                        t.getTxId(), v);
            }

            if (t instanceof BlockchainVerifiable b) {

                System.out.println("Blockchain " + t.getTxId()
                        + ": " + b.getTxHash()
                        + ", confirmations=" + b.getConfirmations()
                        + ", finalized=" + b.isFinalized());
            }

            if (t instanceof PremiumCardPayment p) {
                p.requestRefund("Подвійне списання");
            }
        }

        System.out.printf(Locale.US,
                "Оборот: %.2f грн; комісії: %.2f грн; кешбек: %.2f грн%n",
                turnover, fees, cashbackSum);
    }

    public static void main(String[] x) {

        System.out.println("=== FINTECH PAYMENT GATEWAY ===");

        auditLedger(new AbstractPaymentTransaction[]{
                new PremiumCardPayment("TX-101", 20000),
                new CryptoUsdtPayment("TX-102", 8500),
                new TaxTreasuryPayment("TX-103", 5000)
        });
    }
}