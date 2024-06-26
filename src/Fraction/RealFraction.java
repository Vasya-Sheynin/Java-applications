package Fraction;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RealFraction implements Comparable<RealFraction>, Iterable<String> {
    private int numerator;
    private int denominator;
    private final int[] fields = new int[2];
    private static final String[] fieldNames = {"Numerator", "Denominator"};
    private int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }

        return gcd(b, a % b);
    }

    public static String getSortTypeByIndex(int index){
        if (index < 0 || index >= fieldNames.length){
            throw new IndexOutOfBoundsException("No such field in class");
        }

        return fieldNames[index];
    }

    public RealFraction(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Division by zero");
        }

        numerator = a;
        denominator = b;

        if (denominator < 0) {
            denominator *= -1;
            numerator *= -1;
        }

        fields[0] = numerator;
        fields[1] = denominator;
    }

    public RealFraction(String str) {
        if (str == null) {
            throw new IllegalArgumentException("String was null");
        }

        if (str.isEmpty()) {
            throw new IllegalArgumentException("String was empty");
        }

        this.numerator = Integer.parseInt(str.split("/")[0]);
        this.denominator = Integer.parseInt(str.split("/")[1]);
        fields[0] = numerator;
        fields[1] = denominator;
    }

    public RealFraction add(RealFraction a) {
        RealFraction tmp = new RealFraction
                (this.numerator * a.denominator + a.numerator * this.denominator, this.denominator * a.denominator);
        int gcd = gcd(Math.abs(tmp.numerator), Math.abs(tmp.denominator));

        tmp.numerator /= gcd;
        tmp.denominator /= gcd;
        return tmp;
    }

    public RealFraction sub(RealFraction a) {
        return this.add(new RealFraction(-a.numerator, a.denominator));
    }

    public RealFraction mul(RealFraction a) {
        RealFraction tmp = new RealFraction(this.numerator * a.numerator, this.denominator * a.denominator);
        int gcd = gcd(Math.abs(tmp.numerator), Math.abs(tmp.denominator));

        tmp.numerator /= gcd;
        tmp.denominator /= gcd;
        return tmp;
    }

    public RealFraction div(RealFraction a) {
        if (a.numerator == 0) {
            throw new IllegalArgumentException("Division by zero");
        }

        RealFraction tmp = mul(new RealFraction(a.denominator, a.numerator));
        tmp = (tmp.numerator < 0 && tmp.denominator < 0) ? new RealFraction(-tmp.numerator, -tmp.denominator) : tmp;
        return tmp;
    }

    public void print() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return Integer.toString(this.numerator) + "/" + Integer.toString(this.denominator);
    }


    @Override
    public int compareTo(RealFraction rf) {
        double thisRf = (double) this.numerator / this.denominator;
        double Rf = (double) rf.numerator / rf.denominator;

        return Double.compare(thisRf, Rf);
    }

    public static Comparator<RealFraction> getComparator(int sortBy) {
        if (sortBy >= 2 || sortBy < 0) {
            throw new IndexOutOfBoundsException("No such field index in class");
        }
        return new Comparator<RealFraction>() {
            @Override
            public int compare(RealFraction rf1, RealFraction rf2) {
                return Integer.compare(Math.abs(rf1.fields[sortBy]), Math.abs(rf2.fields[sortBy]));
            }
        };
    }

    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>() {
            private int iterator_idx = -1;

            @Override
            public boolean hasNext() {
                return iterator_idx < (fields.length - 1);
            }

            @Override
            public String next() {
                if (iterator_idx < (fields.length - 1)) {
                    return Integer.toString(fields[++iterator_idx]);
                }

                throw new NoSuchElementException();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}