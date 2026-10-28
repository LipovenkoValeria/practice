package semester1.practice;

import java.util.Scanner;

public class Task3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== СИСТЕМА БАНКІВСЬКОГО СКОРИНГУ ===");

        System.out.print("Введіть вік позичальника: ");
        int age = scanner.nextInt();

        System.out.print("Введіть офіційний місячний дохід (грн): ");
        double monthlyIncome = scanner.nextDouble();

        System.out.print("Чи є негативна кредитна історія? (true/false): ");
        boolean hasBadCreditHistory = scanner.nextBoolean();

        System.out.print("Введіть запитувану суму кредиту (грн): ");
        double loanAmount = scanner.nextDouble();

        System.out.print("Введіть бажаний термін (місяців): ");
        int loanMonths = scanner.nextInt();

        boolean isAgeValid = age >= 21 && age <= 65;
        boolean isCreditHistoryGood = !hasBadCreditHistory;

        boolean isPaymentAffordable = (loanMonths > 0)
                && ((loanAmount / loanMonths) <= (monthlyIncome * 0.5));

        double monthlyPayment = 0;

        if (loanMonths > 0) {
            monthlyPayment = loanAmount / loanMonths;
        }

        double maxPayment = monthlyIncome * 0.5;

        double interestRate = monthlyIncome >= 35000
                ? 14.5
                : 21.0;

        double incomePercentage = monthlyIncome != 0
                ? monthlyPayment / monthlyIncome * 100
                : 0;

        System.out.println();
        System.out.println("------------- РІШЕННЯ СКОРИНГУ -------------");

        if (isAgeValid && isCreditHistoryGood && isPaymentAffordable) {
            System.out.println("Статус заявки: СХВАЛЕНО");
        } else {
            System.out.println("Статус заявки: ВІДХИЛЕНО");

            if (!isAgeValid) {
                System.out.println("Відмова: вік не відповідає критеріям (21-65 років)");
            } else if (!isCreditHistoryGood) {
                System.out.println("Відмова: виявлено негативну кредитну історію");
            } else if (!isPaymentAffordable) {
                System.out.println("Відмова: термін кредиту некоректний або щомісячний платіж перевищує 50% доходу");
            }
        }

        System.out.println("Орієнтовний платіж/міс: " + monthlyPayment + " грн");
        System.out.println("Платіж від доходу: " + incomePercentage + "%");
        System.out.println("Персональна ставка: " + interestRate + "% річних");
        System.out.println("Максимально допустимий платіж: " + maxPayment + " грн/міс");
        System.out.println("============================================");

        scanner.close();
    }
}