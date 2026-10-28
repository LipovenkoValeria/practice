package semester1.practice;

public class Task1 {
    public static void main(String[] args) {
        double usdAmount = 250.0;
        final double EXCHANGE_RATE = 41.25;
        final double COMMISSION_PERCENT = 1.5;

        double totalUah = usdAmount * EXCHANGE_RATE;
        double commission = totalUah * COMMISSION_PERCENT / 100;
        double finalAmount = totalUah - commission;
        long cashAmount = Math.round(finalAmount);

        System.out.println("================ КВИТАНЦІЯ ОБМІНУ ================");
        System.out.println("Сума до обміну:             " + usdAmount + " USD");
        System.out.println("Курс валют:                 " + EXCHANGE_RATE + " UAH/USD");
        System.out.println("Нараховано до комісії:      " + totalUah + " UAH");
        System.out.println("Комісія банку (1.5%):       " + commission + " UAH");
        System.out.println("--------------------------------------------------");
        System.out.println("Чиста сума до видачі:       " + finalAmount + " UAH");
        System.out.println("До видачі готівкою:         " + cashAmount + " UAH");
        System.out.println("==================================================");
    }
}
