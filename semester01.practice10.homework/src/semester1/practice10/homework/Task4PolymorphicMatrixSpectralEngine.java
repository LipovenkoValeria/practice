package semester1.practice10.homework;

import java.util.Locale;

final class ImmutableMatrix2D {

    private final int rows, cols;
    private final double[][] d;

    ImmutableMatrix2D(double[][] a) {

        if (a == null || a.length == 0 || a[0].length == 0) {
            throw new IllegalArgumentException();
        }

        rows = a.length;
        cols = a[0].length;
        d = new double[rows][cols];

        for (int i = 0; i < rows; i++) {

            if (a[i].length != cols) {
                throw new IllegalArgumentException("Non rectangular");
            }

            d[i] = a[i].clone();
        }
    }

    int getRows() {
        return rows;
    }

    int getCols() {
        return cols;
    }

    double get(int r, int c) {
        return d[r][c];
    }

    double[][] getDataCopy() {

        double[][] a = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            a[i] = d[i].clone();
        }

        return a;
    }

    double getTrace() {

        double s = 0;

        for (int i = 0; i < Math.min(rows, cols); i++) {
            s += d[i][i];
        }

        return s;
    }

    double getFrobeniusNorm() {

        double s = 0;

        for (double[] r : d) {
            for (double v : r) {
                s += v * v;
            }
        }

        return Math.sqrt(s);
    }

    double getDeterminant() {

        if (rows != cols) {
            throw new IllegalStateException();
        }

        if (rows == 2) {
            return d[0][0] * d[1][1] - d[0][1] * d[1][0];
        }

        if (rows == 3) {

            return d[0][0] * d[1][1] * d[2][2]
                    + d[0][1] * d[1][2] * d[2][0]
                    + d[0][2] * d[1][0] * d[2][1]
                    - d[0][2] * d[1][1] * d[2][0]
                    - d[0][1] * d[1][0] * d[2][2]
                    - d[0][0] * d[1][2] * d[2][1];
        }

        throw new IllegalStateException("Only 2x2 and 3x3 supported");
    }

    double maxAbsDifference(ImmutableMatrix2D b) {

        if (rows != b.rows || cols != b.cols) {
            return Double.POSITIVE_INFINITY;
        }

        double m = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                m = Math.max(m, Math.abs(d[i][j] - b.d[i][j]));
            }
        }

        return m;
    }

    public String toString() {

        StringBuilder s = new StringBuilder();

        for (double[] r : d) {

            s.append("[");

            for (double v : r) {
                s.append(String.format(Locale.US, " %9.4f", v));
            }

            s.append(" ]\n");
        }

        return s.toString();
    }
}

interface InvertibleOperator {
    ImmutableMatrix2D applyInverse(ImmutableMatrix2D m);
}

interface OrthogonalOperator extends InvertibleOperator {

    default boolean verifyNormInvariance(
            ImmutableMatrix2D a, ImmutableMatrix2D b) {

        return Math.abs(
                a.getFrobeniusNorm() - b.getFrobeniusNorm()) < 1e-6;
    }
}

abstract class AbstractMatrixOperator {

    private final String name;

    AbstractMatrixOperator(String n) {
        name = n;
    }

    String getName() {
        return name;
    }

    abstract boolean canApply(ImmutableMatrix2D m);

    protected abstract ImmutableMatrix2D transform(ImmutableMatrix2D m);

    public final ImmutableMatrix2D execute(ImmutableMatrix2D m) {

        if (m == null || !canApply(m)) {
            throw new IllegalArgumentException("Cannot apply " + name);
        }

        ImmutableMatrix2D r = transform(m);

        if (this instanceof OrthogonalOperator o) {
            System.out.println("   norm preserved: "
                    + o.verifyNormInvariance(m, r));
        }

        return r;
    }
}

class NonZeroScaleOperator extends AbstractMatrixOperator
        implements InvertibleOperator {

    private final double k;

    NonZeroScaleOperator(double k) {
        super("Scale(" + k + ")");

        if (k == 0) {
            throw new IllegalArgumentException();
        }
        this.k = k;
    }

    boolean canApply(ImmutableMatrix2D m) {
        return true;
    }

    protected ImmutableMatrix2D transform(ImmutableMatrix2D m) {

        double[][] a = m.getDataCopy();

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                a[i][j] *= k;
            }
        }

        return new ImmutableMatrix2D(a);
    }

    public ImmutableMatrix2D applyInverse(ImmutableMatrix2D m) {

        double[][] a = m.getDataCopy();

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                a[i][j] /= k;
            }
        }

        return new ImmutableMatrix2D(a);
    }
}

class TransposeOperator extends AbstractMatrixOperator
        implements OrthogonalOperator {

    TransposeOperator() {
        super("Transpose");
    }

    boolean canApply(ImmutableMatrix2D m) {
        return true;
    }

    protected ImmutableMatrix2D transform(ImmutableMatrix2D m) {

        double[][] a = new double[m.getCols()][m.getRows()];

        for (int i = 0; i < m.getRows(); i++) {
            for (int j = 0; j < m.getCols(); j++) {
                a[j][i] = m.get(i, j);
            }
        }

        return new ImmutableMatrix2D(a);
    }

    public ImmutableMatrix2D applyInverse(ImmutableMatrix2D m) {
        return transform(m);
    }
}

class GivensRotationOperator extends AbstractMatrixOperator
        implements OrthogonalOperator {

    private final double t;

    GivensRotationOperator(double deg) {
        super("GivensRotation(" + deg + "°)");
        t = Math.toRadians(deg);
    }

    boolean canApply(ImmutableMatrix2D m) {
        return m.getRows() == 2;
    }

    private ImmutableMatrix2D rot(ImmutableMatrix2D m, double a) {

        double c = Math.cos(a);
        double s = Math.sin(a);

        double[][] x = new double[2][m.getCols()];

        for (int j = 0; j < m.getCols(); j++) {
            x[0][j] = c * m.get(0, j) - s * m.get(1, j);
            x[1][j] = s * m.get(0, j) + c * m.get(1, j);
        }

        return new ImmutableMatrix2D(x);
    }

    protected ImmutableMatrix2D transform(ImmutableMatrix2D m) {
        return rot(m, t);
    }

    public ImmutableMatrix2D applyInverse(ImmutableMatrix2D m) {
        return rot(m, -t);
    }
}

class UpperTriangularProjection extends AbstractMatrixOperator {

    UpperTriangularProjection() {
        super("UpperTriangularProjection");
    }

    boolean canApply(ImmutableMatrix2D m) {
        return m.getRows() == m.getCols();
    }

    protected ImmutableMatrix2D transform(ImmutableMatrix2D m) {

        double[][] a = m.getDataCopy();

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < i; j++) {
                a[i][j] = 0;
            }
        }

        return new ImmutableMatrix2D(a);
    }
}

public class Task4PolymorphicMatrixSpectralEngine {

    public static void main(String[] z) {

        ImmutableMatrix2D x = new ImmutableMatrix2D(
                new double[][]{
                        {3, 4},
                        {0, 5}
                });

        AbstractMatrixOperator[] ops = {
                new NonZeroScaleOperator(2),
                new TransposeOperator(),
                new GivensRotationOperator(60)
        };

        ImmutableMatrix2D y = x;

        System.out.println("=== MATRIX SPECTRAL PIPELINE ===\nX0:\n" + x);

        for (AbstractMatrixOperator op : ops) {

            y = op.execute(y);

            System.out.printf(Locale.US,
                    "%s: norm %.4f det %.4f%n",
                    op.getName(),
                    y.getFrobeniusNorm(),
                    y.getDeterminant());
        }

        System.out.println("Y:\n" + y);

        for (int i = ops.length - 1; i >= 0; i--) {

            if (ops[i] instanceof InvertibleOperator inv) {
                y = inv.applyInverse(y);
            }
        }

        System.out.println("Restored:\n" + y);
        System.out.printf(Locale.US,
                "Max error: %.3e%n",
                x.maxAbsDifference(y));
    }
}