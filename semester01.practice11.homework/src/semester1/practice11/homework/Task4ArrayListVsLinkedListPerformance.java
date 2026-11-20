
package semester1.practice11.homework;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;

public class Task4ArrayListVsLinkedListPerformance {

    public static double benchmarkAddAtBeginning(List<Integer> list, int count) {
        long startTime = System.nanoTime();

        for (int i = 0; i < count; i++) {
            list.add(0, i);
        }

        long endTime = System.nanoTime();

        return (endTime - startTime) / 1_000_000.0;
    }

    public static double benchmarkAddInMiddle(List<Integer> list, int count) {
        long startTime = System.nanoTime();

        for (int i = 0; i < count; i++) {
            int middle = list.size() / 2;
            list.add(middle, i);
        }

        long endTime = System.nanoTime();

        return (endTime - startTime) / 1_000_000.0;
    }

    public static void main(String[] args) {
        System.out.println("========== ARRAYLIST VS LINKEDLIST ==========");

        int count = 20000;

        // Вставка на початок
        List<Integer> arrayBeginning = new ArrayList<>();
        List<Integer> linkedBeginning = new LinkedList<>();

        double arrayBeginningTime =
                benchmarkAddAtBeginning(arrayBeginning, count);

        double linkedBeginningTime =
                benchmarkAddAtBeginning(linkedBeginning, count);

        System.out.println("\n[1. ВСТАВКА НА ПОЧАТОК]");
        System.out.println("Кількість елементів: " + count);

        System.out.printf("ArrayList: %.2f мс%n", arrayBeginningTime);
        System.out.printf("LinkedList: %.2f мс%n", linkedBeginningTime);

        // Вставка в середину
        List<Integer> arrayMiddle = new ArrayList<>();
        List<Integer> linkedMiddle = new LinkedList<>();

        double arrayMiddleTime =
                benchmarkAddInMiddle(arrayMiddle, count);

        double linkedMiddleTime =
                benchmarkAddInMiddle(linkedMiddle, count);

        System.out.println("\n[2. ВСТАВКА В СЕРЕДИНУ]");
        System.out.printf("ArrayList: %.2f мс%n", arrayMiddleTime);
        System.out.printf("LinkedList: %.2f мс%n", linkedMiddleTime);

        System.out.println("\n[3. СОРТУВАННЯ СПИСКУ]");

        List<Integer> numbers = new ArrayList<>();

        numbers.add(45);
        numbers.add(12);
        numbers.add(89);
        numbers.add(3);
        numbers.add(22);
        numbers.add(67);

        System.out.println("До сортування: " + numbers);

        Collections.sort(numbers);

        System.out.println("Після сортування: " + numbers);

        System.out.println("\n[4. РОБОТА З subList()]");

        List<Integer> subNumbers = numbers.subList(1, 4);

        System.out.println("Оригінальний список: " + numbers);
        System.out.println("Підсписок: " + subNumbers);

        System.out.println("Змінюємо перший елемент підсписку на 999");

        subNumbers.set(0, 999);

        System.out.println("Підсписок після зміни: " + subNumbers);
        System.out.println("Оновлений оригінальний список: " + numbers);
    }
}
