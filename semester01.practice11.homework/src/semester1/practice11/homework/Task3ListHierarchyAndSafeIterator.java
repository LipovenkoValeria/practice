
package semester1.practice11.homework;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.ConcurrentModificationException;

public class Task3ListHierarchyAndSafeIterator {

    public static void demonstrateFailFastCrash(List<String> logs) {
        System.out.println("\nСпроба видалення через for-each:");

        try {
            for (String log : logs) {
                if (log != null && log.contains("CORRUPTED")) {
                    logs.remove(log);
                }
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("Перехоплено ConcurrentModificationException!");
            System.out.println("Причина: modCount != expectedModCount.");
            System.out.println("Список був змінений напряму під час обходу.");
        }
    }

    public static int purgeLogsByKeywordSafely(
            List<String> logs, String keyword) {

        Iterator<String> it = logs.iterator();
        int removedCount = 0;

        while (it.hasNext()) {
            String log = it.next();

            if (log == null || log.isEmpty() || log.contains(keyword)) {
                it.remove();
                removedCount++;
            }
        }

        return removedCount;
    }

    public static void enrichCriticalLogsWithListIterator(List<String> logs) {
        ListIterator<String> listIt = logs.listIterator();

        while (listIt.hasNext()) {
            String log = listIt.next();

            String trimmed = log.trim();

            listIt.set(trimmed);

            if (trimmed.startsWith("ERROR:")) {
                listIt.add("[ALERT-TRIGGERED]");
            }
        }
    }

    public static List<String> collectReverseOrder(List<String> logs) {
        List<String> reversed = new ArrayList<>();

        ListIterator<String> listIt = logs.listIterator(logs.size());

        while (listIt.hasPrevious()) {
            reversed.add(listIt.previous());
        }

        return reversed;
    }

    public static void main(String[] args) {
        System.out.println("========== ІЄРАРХІЯ LIST ТА ITERATOR ==========");

        List<String> rawLogs = new ArrayList<>();

        rawLogs.add("INFO: Auth OK");
        rawLogs.add("CORRUPTED_PACKET_01");
        rawLogs.add("   ERROR: DB Timeout  ");
        rawLogs.add("DEBUG: Ping");
        rawLogs.add("CORRUPTED_PACKET_02");
        rawLogs.add(null);
        rawLogs.add("ERROR: Disk Full");
        rawLogs.add("INFO: Logout");

        System.out.println("Початковий список: " + rawLogs);

        // Окремий список для демонстрації помилки
        List<String> testLogs = new ArrayList<>(rawLogs);

        System.out.println("\n[1. ДЕМОНСТРАЦІЯ FAIL-FAST]");

        demonstrateFailFastCrash(testLogs);

        // Створення двох списків з однаковими даними
        List<String> arrayLogs = new ArrayList<>(rawLogs);
        List<String> linkedLogs = new LinkedList<>(rawLogs);

        System.out.println("\n[2. ОБРОБКА ARRAYLIST]");

        int removedArray = purgeLogsByKeywordSafely(
                arrayLogs, "CORRUPTED");

        System.out.println("Безпечно видалено записів: " + removedArray);
        System.out.println("Після очищення: " + arrayLogs);

        enrichCriticalLogsWithListIterator(arrayLogs);

        System.out.println("Після ListIterator: " + arrayLogs);

        System.out.println("Зворотний порядок: "
                + collectReverseOrder(arrayLogs));

        System.out.println("\n[3. ОБРОБКА LINKEDLIST]");

        int removedLinked = purgeLogsByKeywordSafely(
                linkedLogs, "CORRUPTED");

        System.out.println("Безпечно видалено записів: " + removedLinked);

        enrichCriticalLogsWithListIterator(linkedLogs);

        System.out.println("Після ListIterator: " + linkedLogs);

        System.out.println("Зворотний порядок: "
                + collectReverseOrder(linkedLogs));

        System.out.println("\nРезультати ArrayList та LinkedList збігаються: "
                + arrayLogs.equals(linkedLogs));

        System.out.println("Кількість елементів: " + arrayLogs.size());
    }
}
