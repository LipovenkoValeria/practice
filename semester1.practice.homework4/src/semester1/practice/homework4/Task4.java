package semester1.practice.homework4;

import java.util.Scanner;

public class Task4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n;

        do {
            System.out.print("Введіть верхню межу N (не менше 2): ");
            n = scanner.nextInt();

            if (n < 2) {
                System.out.println("Число має бути не меншим за 2!");
            }
        } while (n < 2);

        int primeCount = 0;
        int primeSum = 0;

        System.out.println("Прості числа від 2 до " + n + ":");

        for (int number = 2; number <= n; number++) {
            boolean isPrime = true;

            for (int d = 2; d * d <= number; d++) {
                if (number % d == 0) {
                    isPrime = false;
                    break;
                }
            }

            if (isPrime) {
                System.out.print(number + " ");
                primeCount++;
                primeSum += number;
            }
        }

        int totalNumbers = n - 1;
        double percentage = (double) primeCount / totalNumbers * 100;

        System.out.println();
        System.out.println("Усього чисел у діапазоні: " + totalNumbers);
        System.out.println("Знайдено простих чисел: " + primeCount);
        System.out.println("Сума простих чисел: " + primeSum);
        System.out.printf("Частка простих чисел: %.2f%%%n", percentage);

        scanner.close();
    }
}