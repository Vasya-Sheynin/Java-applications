package PT4;

public class test
{
    static final int M = 5;

    public static void main(String[] args)
    {
        System.out.println("---empty matrix test---");
        System.out.println(new Matrix());
        System.out.println();

        Matrix sizeSetOnlyMatrix = new Matrix(M);
        System.out.println("---matrix with only size set to " + M + "---");
        sizeSetOnlyMatrix.printMatrix();
        sizeSetOnlyMatrix.printSubMatrix(1, 1, M - 2, M - 2);

        System.out.println("---array of matrices test---");
        Matrix[] arr = new Matrix[M];

        for (int i = 0; i < M; i++) {
            arr[i] = new Matrix(i + 1, 2 * i + 1);
            arr[i].printMatrix();
        }

        System.out.println("---matrices with modified numbers of rows and columns---");
        System.out.println("---for even i rows count = 2 * (i + 1)---");
        System.out.println("---for odd i columns count = 2 * i + 1---");

        for (int i = 0; i < M; i += 2) {
            arr[i].setRowsCount(2 * (i + 1));
        }

        for (int i = 1; i < M; i += 2) {
            arr[i].setColumnsCount(2 * i + 1);
        }

        for (int i = 0; i < M; i++) {
            arr[i].printMatrix();
        }

        System.out.println("---matrices after swapping two specific rows according to column i / 2---");

        for (int i = 0; i < M; i++) {
            arr[i].changeRows(i / 2);
        }
    }
}