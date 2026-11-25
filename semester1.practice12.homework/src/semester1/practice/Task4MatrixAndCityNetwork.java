package semester1.practice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;

abstract class Transport {
    private final String name;
    private final double speedKmH;

    public Transport(String name, double speedKmH) {
        this.name = name;
        this.speedKmH = speedKmH;
    }

    public String getName() {
        return name;
    }

    public double getSpeedKmH() {
        return speedKmH;
    }

    public double calculateTimeHours(int distanceKm) {
        return distanceKm / speedKmH;
    }

    public abstract double calculateCostUah(int distanceKm);
}

class Bus extends Transport {

    public Bus() {
        super("Bus", 80.0);
    }

    @Override
    public double calculateCostUah(int distanceKm) {
        return distanceKm * 2.5;
    }
}

class Train extends Transport {

    public Train() {
        super("Train", 100.0);
    }

    @Override
    public double calculateCostUah(int distanceKm) {
        return distanceKm * 1.8 + 150.0;
    }
}

class DeliveryTruck extends Transport {

    public DeliveryTruck() {
        super("DeliveryTruck", 70.0);
    }

    @Override
    public double calculateCostUah(int distanceKm) {
        return distanceKm * 4.0 + 300.0;
    }
}

class TripOrder {
    private final String orderId;
    private final String fromCity;
    private final String toCity;
    private final Transport transport;

    public TripOrder(String orderId, String fromCity,
                     String toCity, Transport transport) {
        this.orderId = orderId;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.transport = transport;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getFromCity() {
        return fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public Transport getTransport() {
        return transport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof TripOrder)) {
            return false;
        }

        TripOrder order = (TripOrder) o;

        return Objects.equals(orderId, order.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }
}

class RouteDispatcher {
    private ArrayList<String> cities;
    private int[][] distances;
    private HashSet<String> closedCities;
    private ArrayList<TripOrder> orders = new ArrayList<>();

    public RouteDispatcher(ArrayList<String> cities,
                           int[][] distances,
                           HashSet<String> closedCities) {

        this.cities = new ArrayList<>(cities);

        this.distances = new int[distances.length][];

        for (int i = 0; i < distances.length; i++) {
            this.distances[i] = distances[i].clone();
        }

        this.closedCities = new HashSet<>(closedCities);
    }

    public void addOrder(TripOrder order) {
        orders.add(order);
    }

    public int getDistance(String from, String to) {
        int i = cities.indexOf(from);
        int j = cities.indexOf(to);

        if (i == -1 || j == -1) {
            return 0;
        }

        return distances[i][j];
    }

    public HashSet<String> getDirectNeighbors(String city) {
        HashSet<String> neighbors = new HashSet<>();

        int index = cities.indexOf(city);

        if (index == -1) {
            return neighbors;
        }

        for (int i = 0; i < distances[index].length; i++) {
            if (distances[index][i] > 0) {
                neighbors.add(cities.get(i));
            }
        }

        return neighbors;
    }

    public HashSet<String> getCommonNeighbors(String cityA, String cityB) {
        HashSet<String> result = getDirectNeighbors(cityA);

        result.retainAll(getDirectNeighbors(cityB));

        return result;
    }

    public HashSet<String> getExclusiveNeighbors(String cityA, String cityB) {
        HashSet<String> result = getDirectNeighbors(cityA);

        result.removeAll(getDirectNeighbors(cityB));

        return result;
    }

    public int filterInvalidOrders() {
        int removed = 0;

        HashSet<String> seenIds = new HashSet<>();

        Iterator<TripOrder> it = orders.iterator();

        while (it.hasNext()) {
            TripOrder order = it.next();

            if (!seenIds.add(order.getOrderId())) {
                System.out.println("  [ДУБЛІКАТ] " + order.getOrderId()
                        + " — видалено!");

                it.remove();
                removed++;

            } else if (closedCities.contains(order.getFromCity())
                    || closedCities.contains(order.getToCity())) {

                System.out.println("  [МІСТО ЗАКРИТЕ] " + order.getOrderId()
                        + " — видалено!");

                it.remove();
                removed++;

            } else if (getDistance(order.getFromCity(),
                    order.getToCity()) <= 0) {

                System.out.println("  [НЕМАЄ ДОРОГИ] " + order.getOrderId()
                        + " — видалено!");

                it.remove();
                removed++;
            }
        }

        return removed;
    }

    public void printReport() {
        int totalDistance = 0;
        double totalCost = 0;

        System.out.println("\n--- Схвалені рейси ---");

        for (TripOrder order : orders) {
            int distance = getDistance(
                    order.getFromCity(), order.getToCity());

            Transport transport = order.getTransport();

            double time = transport.calculateTimeHours(distance);
            double cost = transport.calculateCostUah(distance);

            totalDistance += distance;
            totalCost += cost;

            System.out.println(order.getOrderId()
                    + " | " + order.getFromCity()
                    + " -> " + order.getToCity()
                    + " | " + distance + " км"
                    + " | " + transport.getName()
                    + " | Час: " + String.format("%.2f", time) + " год"
                    + " | Вартість: " + String.format("%.2f", cost) + " грн");
        }

        System.out.println("\nЗагальна відстань: " + totalDistance + " км");
        System.out.println("Загальна вартість: "
                + String.format("%.2f", totalCost) + " грн");
    }

    public int getOrdersCount() {
        return orders.size();
    }
}

public class Task4MatrixAndCityNetwork {

    public static void main(String[] args) {

        System.out.println("=============== ЛОГІСТИЧНИЙ ДИСПЕТЧЕР ===============");

        ArrayList<String> cities = new ArrayList<>();

        cities.add("Київ");
        cities.add("Львів");
        cities.add("Одеса");
        cities.add("Дніпро");

        int[][] distances = {
                {0, 540, 480, 450},
                {540, 0, 790, 0},
                {480, 790, 0, 460},
                {450, 0, 460, 0}
        };

        HashSet<String> closedCities = new HashSet<>();
        closedCities.add("Одеса");

        RouteDispatcher dispatcher =
                new RouteDispatcher(cities, distances, closedCities);

        System.out.println("\n--- Аналіз мережі доріг ---");

        System.out.println("Сусіди Києва: "
                + dispatcher.getDirectNeighbors("Київ"));

        System.out.println("Сусіди Львова: "
                + dispatcher.getDirectNeighbors("Львів"));

        System.out.println("Спільні сусіди: "
                + dispatcher.getCommonNeighbors("Київ", "Львів"));

        System.out.println("Тільки сусіди Києва: "
                + dispatcher.getExclusiveNeighbors("Київ", "Львів"));

        dispatcher.addOrder(new TripOrder(
                "ORD-1", "Київ", "Львів", new Train()));

        dispatcher.addOrder(new TripOrder(
                "ORD-1", "Київ", "Львів", new Bus()));

        dispatcher.addOrder(new TripOrder(
                "ORD-2", "Київ", "Одеса", new Bus()));

        dispatcher.addOrder(new TripOrder(
                "ORD-3", "Львів", "Дніпро", new DeliveryTruck()));

        dispatcher.addOrder(new TripOrder(
                "ORD-4", "Київ", "Дніпро", new DeliveryTruck()));

        System.out.println("\n--- Фільтрація заявок через Iterator ---");

        int removed = dispatcher.filterInvalidOrders();

        dispatcher.printReport();

        System.out.println("\n--- Підсумок ---");

        System.out.println("Видалено заявок: " + removed);
        System.out.println("Схвалено заявок: "
                + dispatcher.getOrdersCount());

        System.out.println("======================================================");
    }
}