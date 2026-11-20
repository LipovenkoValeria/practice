
package semester1.practice11.homework;

import java.util.ArrayList;

class CustomArrayCapacityEmulator<E> {

    private static final int DEFAULT_CAPACITY = 10;

    private Object[] elementData;
    private int size = 0;
    private int resizeCount = 0;
    private int totalElementsCopied = 0;

    public CustomArrayCapacityEmulator() {
        elementData = new Object[DEFAULT_CAPACITY];
    }

    private void ensureCapacityInternal() {
        if (size == elementData.length) {
            int oldCapacity = elementData.length;
            int newCapacity = oldCapacity + (oldCapacity >> 1);

            if (newCapacity == 0) {
                newCapacity = 1;
            }

            Object[] newBuffer = new Object[newCapacity];

            System.arraycopy(elementData, 0, newBuffer, 0, size);

            totalElementsCopied += size;
            elementData = newBuffer;
            resizeCount++;

            System.out.println("GROW #" + resizeCount
                    + ": " + oldCapacity + " -> " + newCapacity
                    + " | Скопійовано елементів: " + size);
        }
    }

    public void add(E element) {
        ensureCapacityInternal();

        elementData[size] = element;
        size++;
    }

    public void add(int index, E element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Неправильний індекс: " + index);
        }

        ensureCapacityInternal();

        int elementsToMove = size - index;

        if (elementsToMove > 0) {
            System.arraycopy(
                    elementData,
                    index,
                    elementData,
                    index + 1,
                    elementsToMove
            );

            totalElementsCopied += elementsToMove;
        }

        elementData[index] = element;
        size++;
    }

    @SuppressWarnings("unchecked")
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Неправильний індекс: " + index);
        }

        E removedElement = (E) elementData[index];

        int numMoved = size - index - 1;

        if (numMoved > 0) {
            System.arraycopy(
                    elementData,
                    index + 1,
                    elementData,
                    index,
                    numMoved
            );

            totalElementsCopied += numMoved;
        }

        elementData[--size] = null;

        return removedElement;
    }

    @SuppressWarnings("unchecked")
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Неправильний індекс: " + index);
        }

        return (E) elementData[index];
    }

    @SuppressWarnings("unchecked")
    public E set(int index, E element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Неправильний індекс: " + index);
        }

        E oldElement = (E) elementData[index];
        elementData[index] = element;

        return oldElement;
    }

    public int size() {
        return size;
    }

    public int getCapacity() {
        return elementData.length;
    }

    public void trimToSize() {
        if (elementData.length != size) {
            Object[] newBuffer = new Object[size];

            System.arraycopy(elementData, 0, newBuffer, 0, size);

            totalElementsCopied += size;
            elementData = newBuffer;
        }
    }

    public int getResizeCount() {
        return resizeCount;
    }

    public int getTotalElementsCopied() {
        return totalElementsCopied;
    }
}

public class Task2ArrayListCapacityAndDynamicGrowth {

    public static void main(String[] args) {
        System.out.println("========== ЕМУЛЯТОР ARRAYLIST ==========");

        var emulator = new CustomArrayCapacityEmulator<String>();

        System.out.println("Створено CustomArrayCapacityEmulator<String>");
        System.out.println("size = " + emulator.size()
                + " | capacity = " + emulator.getCapacity());

        // Небажаний варіант:
        // var raw = new ArrayList<>();
        // У такому випадку тип елементів визначається як Object.

        System.out.println("\n--- Додавання 35 елементів ---");

        for (int i = 1; i <= 35; i++) {
            emulator.add("Block-" + i);
        }

        System.out.println("Після додавання:");
        System.out.println("size = " + emulator.size());
        System.out.println("capacity = " + emulator.getCapacity());

        System.out.println("\n--- Вставка та видалення ---");

        emulator.add(0, "HEADER");

        System.out.println("Додано HEADER на початок списку");

        String removed = emulator.remove(5);

        System.out.println("Видалено елемент з індексу 5: " + removed);

        System.out.println("Перший елемент: " + emulator.get(0));
        System.out.println("Останній елемент: "
                + emulator.get(emulator.size() - 1));

        System.out.println("\n--- Підсумкова статистика ---");

        System.out.println("size = " + emulator.size());
        System.out.println("capacity = " + emulator.getCapacity());
        System.out.println("Кількість розширень: " + emulator.getResizeCount());
        System.out.println("Кількість скопійованих елементів: "
                + emulator.getTotalElementsCopied());

        System.out.println("\n--- trimToSize() ---");

        emulator.trimToSize();

        System.out.println("Після trimToSize():");
        System.out.println("size = " + emulator.size());
        System.out.println("capacity = " + emulator.getCapacity());
    }
}
