package semester1.practice.homework;

public class Task1 {
    public static void main(String[] args) {
        double usdAmount = 250.0;
        final double EXCANGE_RATE = 41.25;
        final double COMMISION_PRECENT = 1.5;
        double totlUah = usdAmount * EXCANGE_RATE;
        double commission = totlUah * COMMISION_PRECENT / 100;
        double netAmount = totlUah - commission;
        long cashAmount = Math.round(netAmount);
        System.out.println("Сума до обміну: " + usdAmount + " USD");
    }
}
