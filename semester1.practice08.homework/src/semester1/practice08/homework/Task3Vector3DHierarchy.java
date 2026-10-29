package semester1.practice08.homework;

class Vector2D {
    private double x;
    private double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D() {
        this(0.0, 0.0);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getLength() {
        return Math.sqrt(x * x + y * y);
    }

    public double dotProduct(Vector2D other) {
        return x * other.x + y * other.y;
    }

    @Override
    public String toString() {
        return String.format(
                "Vector2D(%.2f; %.2f) |L|=%.2f",
                x, y, getLength()
        );
    }
}

class Vector3D extends Vector2D {
    private double z;

    public Vector3D(double x, double y, double z) {
        super(x, y);
        this.z = z;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    @Override
    public double getLength() {
        return Math.hypot(super.getLength(), z);
    }

    public double dotProduct(Vector3D other) {
        return super.dotProduct(other) + z * other.z;
    }

    public Vector3D crossProduct(Vector3D other) {
        double x = getY() * other.z - z * other.getY();
        double y = z * other.getX() - getX() * other.z;
        double zResult = getX() * other.getY() - getY() * other.getX();

        return new Vector3D(x, y, zResult);
    }

    @Override
    public String toString() {
        return String.format(
                "Vector3D(%.2f; %.2f; %.2f) |L|=%.2f",
                getX(), getY(), z, getLength()
        );
    }
}

class ForceVector3D extends Vector3D {
    private String forceName;

    public ForceVector3D(String forceName, double fx, double fy, double fz) {
        super(fx, fy, fz);
        this.forceName = forceName;
    }

    public double calculateWork(Vector3D displacement) {
        return super.dotProduct(displacement);
    }

    public double calculateAcceleration(double massKg) {
        if (massKg <= 0) {
            return 0.0;
        }

        return getLength() / massKg;
    }

    @Override
    public String toString() {
        return String.format(
                "Force[name='%s', Vector3D(%.2f; %.2f; %.2f) |F|=%.2f Н]",
                forceName, getX(), getY(), getZ(), getLength()
        );
    }
}

public class Task3Vector3DHierarchy {
    public static void main(String[] args) {
        Vector2D vector2D = new Vector2D(3, 4);
        Vector3D vector3D = new Vector3D(3, 4, 12);
        Vector3D displacement = new Vector3D(2, 0, 5);

        ForceVector3D force = new ForceVector3D(
                "Тяга ракети", 3, 4, 12
        );

        System.out.println("================ ІЄРАРХІЯ ВЕКТОРІВ (2D -> 3D -> FORCE) ================");
        System.out.println("1. Рівень 1 (2D): " + vector2D);
        System.out.println("2. Рівень 2 (3D): " + vector3D);
        System.out.println("   Вектор переміщення S: " + displacement);

        System.out.println();
        System.out.println("3. Рівень 3 (Фізична сила):");
        System.out.println("   " + force);
        System.out.printf(
                "   -> Виконана механічна робота A = F · S = %.2f Дж%n",
                force.calculateWork(displacement)
        );
        System.out.printf(
                "   -> Прискорення тіла масою 2.5 кг: a = %.2f м/с²%n",
                force.calculateAcceleration(2.5)
        );

        System.out.println("=======================================================================");
    }
}
