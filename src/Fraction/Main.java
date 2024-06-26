package Fraction;

import java.util.Arrays;

class Main {

    static void sortAndPrint(RealFraction[] rfArr) {
        System.out.println("----- Sorted in natural order: ");
        Arrays.sort(rfArr);
        for (RealFraction rfValue : rfArr) {
            System.out.println(rfValue.toString());
        }
    }

    static void sortAndPrint(RealFraction[] rfArr, int sortBy) {
        System.out.println("----- Sorted by: Absolute " + RealFraction.getSortTypeByIndex(sortBy));
        Arrays.sort(rfArr, RealFraction.getComparator(sortBy));
        for (RealFraction rfValue : rfArr) {
            System.out.println(rfValue.toString());
        }
    }

    public static void main(String[] args) {
        try {
            RealFraction rf1 = new RealFraction(-2, 3);
            RealFraction rf2 = new RealFraction(6, 13);

            rf1.add(rf2).print();
            rf1.sub(rf2).print();
            rf1.mul(rf2).print();
            rf1.div(rf2).print();

            RealFraction[] rfArr = new RealFraction[5];
            rfArr[0] = new RealFraction(-1, 3);
            rfArr[1] = new RealFraction(-3, -5);
            rfArr[2] = new RealFraction(4, 23);
            rfArr[3] = new RealFraction(-7, 5);

            //Recreating object from its .toString() representation;
            rfArr[4] = new RealFraction(rf1.toString());

            sortAndPrint(rfArr);

            sortAndPrint(rfArr, 0);
            sortAndPrint(rfArr, 1);

            System.out.println("Test: invalid index for sorting");
            sortAndPrint(rfArr, 2);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
        }
    }
}
