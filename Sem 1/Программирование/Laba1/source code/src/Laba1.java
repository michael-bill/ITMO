import java.lang.Math;

public class Laba1 {
    public static void main(String[] args) {

        short[] p = new short[10];
        short count = 24;
        double[] x = new double[16];
        double[][] p2 = new double[10][16];
        short[] values = new short[] { 6, 16, 18, 20, 22 };

        for (int i = 0; i < p.length; i++) {
            p[i] = count;
            count -= 2;
        }

        for (int i = 0; i < x.length; i++) {
            x[i] = getRandom(-8, 15);
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 16; j++) {
                if (p[i] == 24)
                    p2[i][j] = function1(x[j]);
                else if (contains(values, p[i]))
                    p2[i][j] = function2(x[j]);
                else
                    p2[i][j] = function3(x[j]);
            }
        }
        printArray(p2);
    }

    private static double function1(double x) {
        return Math.pow((2 * Math.pow(Math.sin(x), Math.pow((1 / 4) / (4 + x), 3) / 3)), 2);
    }

    private static double function2(double x) {
        return Math.cos(Math.exp(Math.log(Math.abs(x))));
    }

    private static double function3(double x) {
        return Math.atan(Math.cos(Math.cos(Math.asin((x + 3.5) / 23))))
                / (Math.PI + Math.sin(Math.log(Math.exp(x))));
    }

    private static void printArray(double[][] a) {
        for (int i = 0; i < 10; i++) {
            System.out.print("[");
            for (int j = 0; j < 16; j++) {
                if (j == 15)
                    System.out.printf("%.3f", a[i][j]);
                else
                    System.out.printf("%.3f,\t", a[i][j]);
            }
            System.out.print("]\n");
        }
    }

    private static boolean contains(short[] a, short value) {
        for (int i = 0; i < a.length; i++)
            if (a[i] == value)
                return true;
        return false;
    }

    private static double getRandom(int start, int end) {
        double randomValue = start + Math.random() * (end - start);
        return randomValue;
    }
}