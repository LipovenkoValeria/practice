package semester1.practice10.homework;

import java.util.Locale;

interface Evaluable1D {
    double EPSILON = 1e-7;

    double evaluate(double x);
}

interface Integrable1D extends Evaluable1D {

    default double integrateSimpson(double a, double b, int n) {

        if (n < 2) {
            n = 100;
        }

        if (n % 2 != 0) {
            n++;
        }

        double h = (b - a) / n;
        double s = evaluate(a) + evaluate(b);

        for (int i = 1; i < n; i++) {
            s += (i % 2 == 0 ? 2 : 4) * evaluate(a + i * h);
        }

        return s * h / 3;
    }
}

interface Differentiable1D extends Evaluable1D {

    double derivative(double x);

    default double numericalDerivative(double x) {

        double h = 1e-5;

        return (evaluate(x + h) - evaluate(x - h)) / (2 * h);
    }
}

interface NewtonRootFindable extends Differentiable1D {

    default double findRootNewton(double x, int max) {

        for (int i = 0; i < max; i++) {

            double y = evaluate(x);
            double d = derivative(x);

            if (Math.abs(y) <= EPSILON || Math.abs(d) < 1e-12) {
                return x;
            }

            x -= y / d;
        }

        return x;
    }
}

abstract class AbstractMathFunction implements Integrable1D {

    private final String title;

    AbstractMathFunction(String t) {
        title = t;
    }

    public final String getFormulaTitle() {
        return title;
    }
}

final class PolynomialFunction extends AbstractMathFunction
        implements NewtonRootFindable {

    private final double[] c;

    PolynomialFunction(double[] c) {
        super("P(x)=-5-2x+x^3");
        this.c = c.clone();
    }

    public double evaluate(double x) {

        double y = 0;

        for (int i = c.length - 1; i >= 0; i--) {
            y = y * x + c[i];
        }

        return y;
    }

    public double derivative(double x) {

        double y = 0;

        for (int i = c.length - 1; i >= 1; i--) {
            y = y * x + i * c[i];
        }

        return y;
    }
}

class DampedHarmonyFunction extends AbstractMathFunction
        implements NewtonRootFindable {

    private final double a, g, w;

    DampedHarmonyFunction(double a, double g, double w) {
        super("D(x)=4 exp(-0.5x) cos(2x)");

        this.a = a;
        this.g = g;
        this.w = w;
    }

    public double evaluate(double x) {
        return a * Math.exp(-g * x) * Math.cos(w * x);
    }

    public double derivative(double x) {
        return -a * Math.exp(-g * x)
                * (g * Math.cos(w * x) + w * Math.sin(w * x));
    }
}

class StepHeavisideFunction extends AbstractMathFunction {

    StepHeavisideFunction() {
        super("Step(x)=2 if x<1.5; 5 otherwise");
    }

    public double evaluate(double x) {
        return x < 1.5 ? 2 : 5;
    }
}

public class Task3NumericalCalculusAndRootPolymorphism {

    public static void main(String[] z) {

        AbstractMathFunction[] f = {
                new PolynomialFunction(new double[]{-5, -2, 0, 1}),
                new DampedHarmonyFunction(4, 0.5, 2),
                new StepHeavisideFunction()
        };

        double[] starts = {2, 0.7, 0};

        System.out.println("=== NUMERICAL CALCULUS & ROOT POLYMORPHISM ===");

        for (int i = 0; i < f.length; i++) {

            AbstractMathFunction q = f[i];

            int n = i == 2 ? 1000 : 100;

            System.out.println("\n" + (i + 1) + ". " + q.getFormulaTitle());

            System.out.printf(Locale.US,
                    "Integral [0,3], N=%d: %.6f%n",
                    n, q.integrateSimpson(0, 3, n));

            if (q instanceof NewtonRootFindable r) {

                double x = i == 0 ? 2 : 1;

                System.out.printf(Locale.US,
                        "Derivative at %.1f: exact %.6f; numerical %.6f%n",
                        x,
                        r.derivative(x),
                        r.numericalDerivative(x));

                double root = r.findRootNewton(starts[i], 100);
                System.out.printf(Locale.US,
                        "Newton root: %.7f; f(root)=%.2e%n",
                        root,
                        q.evaluate(root));

            } else {

                System.out.println(
                        "Discontinuous function: derivative/root search skipped.");
            }
        }
    }
}