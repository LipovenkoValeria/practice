package semester1.practice.homework4;

import java.util.Scanner;

public class Task1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n;

        do {
            System.out.print("Введіть число N: ");
            n = scanner.nextInt();

            if (n <= 0) {
                System.out.println("Помилка: N має бути більшим за 0!");
            }
        } while (n <= 0);

        int totalSum = 0;
        int evenSum = 0;
        int oddSum = 0;
        int evenCount = 0;
        int oddCount = 0;

        for (int i = 1; i <= n; i++) {
            totalSum += i;

            if (i % 2 == 0) {
                evenSum += i;
                evenCount++;
            } else {
                oddSum += i;
                oddCount++;
            }
        }

        double average = (double) totalSum / n;

        System.out.println("Загальна сума чисел: " + totalSum);
        System.out.println("Сума парних чисел: " + evenSum);
        System.out.println("Кількість парних чисел: " + evenCount);
        System.out.println("Сума непарних чисел: " + oddSum);
        System.out.println("Кількість непарних чисел: " + oddCount);
        System.out.printf("Середнє арифметичне: %.2f%n", average);

        scanner.close();
    }
}