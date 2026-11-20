
package semester1.practice11.homework;

import java.util.ArrayList;
import java.util.List;

public class Task1WrapperAndAutoboxingMemory {

    public static void inspectIntegerCache(int[] testValues) {
        System.out.println("[1. INTEGER CACHE [-128..127]]");

        for (int val : testValues) {
            Integer first = Integer.valueOf(val);
            Integer second = Integer.valueOf(val);

            System.out.println("Значення: " + val
                    + " | a == b: " + (first == second)
                    + " | a.equals(b): " + first.equals(second));
        }
    }

    public static void demonstrateAutoboxingUnderTheHood() {
        System.out.println("\n[2. AUTOBOXING / UNBOXING]");

        // Неявне пакування
        Integer number = 42;

        // Явне пакування
        Integer explicitNumber = Integer.valueOf(42);

        // Неявне розпакування
        int value = number;

        // Явне розпакування
        int explicitValue = explicitNumber.intValue();

        System.out.println("Integer: " + number + " | " + explicitNumber);
        System.out.println("Unboxing: " + value + " | " + explicitValue);

        Double decimal = 12.5;
        Double explicitDecimal = Double.valueOf(12.5);

        double decimalValue = decimal;
        double explicitDecimalValue = explicitDecimal.doubleValue();

        System.out.println("Double: " + decimal + " | " + explicitDecimal);
        System.out.println("Unboxing: " + decimalValue + " | " + explicitDecimalValue);

        Boolean flag = true;
        Boolean explicitFlag = Boolean.valueOf(true);

        boolean flagValue = flag;
        boolean explicitFlagValue = explicitFlag.booleanValue();

        System.out.println("Boolean: " + flag + " | " + explicitFlag);
        System.out.println("Unboxing: " + flagValue + " | " + explicitFlagValue);

        // Перевірка списку з null
        List<Integer> numbers = new ArrayList<>();

        numbers.add(45);
        numbers.add(null);
        numbers.add(-12);
        numbers.add(88);
        numbers.add(null);
        numbers.add(102);

        int sum = 0;

        System.out.println("\nСписок датчиків: " + numbers);

        for (int i = 0; i < numbers.size(); i++) {
            Integer current = numbers.get(i);

            if (current != null) {
                int currentValue = current.intValue();
                sum += currentValue;
            } else {
                System.out.println("Пропущено null на індексі " + i);
            }
        }

        System.out.println("Сума валідних значень: " + sum);
    }

    public static List<Integer> parseTelemetryStream(String[] rawPackets) {
        List<Integer> validData = new ArrayList<>();

        for (String packet : rawPackets) {
            if (packet == null || packet.trim().isEmpty()) {
                System.out.println("Порожній або null-пакет відхилено");
                continue;
            }

            try {
                int value = Integer.parseInt(packet.trim());
                validData.add(value);
            } catch (NumberFormatException e) {
                System.out.println("Пакет '" + packet
                        + "' відхилено (NumberFormatException)");
            }
        }

        return validData;
    }

    public static void printExtremesAndStats(List<Integer> validData) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        long sum = 0;

        System.out.println("\n[3. ПАРСИНГ ТА ПОШУК ЕКСТРЕМУМІВ]");

        System.out.println("Початковий min: " + min);
        System.out.println("Початковий max: " + max);

        for (Integer number : validData) {
            if (number != null) {
                if (number < min) {
                    min = number;
                }

                if (number > max) {
                    max = number;
                }

                sum += number;
            }
        }

        if (validData.isEmpty()) {
            System.out.println("Список не містить валідних даних");
        } else {
            System.out.println("Валідний список: " + validData);
            System.out.println("Мінімум: " + min);
            System.out.println("Максимум: " + max);
            System.out.println("Сума: " + sum);
        }

        // Перевірка різниці між remove(index) та remove(object)
        List<Integer> testList = new ArrayList<>(validData);

        if (!testList.isEmpty()) {
            System.out.println("\nПочатковий список для remove: " + testList);

            Integer removedByIndex = testList.remove(0);
            System.out.println("remove(0) видалив елемент за індексом: "
                    + removedByIndex);

            if (testList.contains(Integer.valueOf(-30))) {
                testList.remove(Integer.valueOf(-30));
                System.out.println("remove(Integer.valueOf(-30)) видалив число -30");
            }

            System.out.println("Список після видалення: " + testList);
        }
    }

    public static void main(String[] args) {
        System.out.println("========== WRAPPER CLASSES ТА INTEGER CACHE ==========");

        int[] testValues = {-128, -50, 0, 127, 128, 500, 2026};

        inspectIntegerCache(testValues);
        demonstrateAutoboxingUnderTheHood();

        String[] rawPackets = {
                "  145 ", "-30", "ERR_404", "980",
                "", null, "2147483647", "-85"
        };

        List<Integer> validData = parseTelemetryStream(rawPackets);

        printExtremesAndStats(validData);
    }
}
