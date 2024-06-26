package PT4;

import java.util.Random;
import java.util.Date;
public class Matrix
{
    public static Random rand = new Random((new Date()).getTime());
    static final int MAX_A = 10;

    private int[][] matx;

    public Matrix() {
        matx = null;
    }

    public Matrix(int n) {
        this(n, MAX_A);
    }

    public Matrix(int n, int max_val) {
        assert (n > 0);
        assert (max_val > 0);
        matx = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matx[i][j] = rand.nextInt(max_val);
            }
        }
    }

    public void setRowsCount(int count)
    {
        assert (matx != null);

        if (count <= 0)
        {
            throw new IndexOutOfBoundsException();
        }

        int[][] tmpMatrix = new int[count][matx[0].length];

        if (count < matx.length) {
            for (int i = 0; i < count; i++) {
                System.arraycopy(matx[i], 0, tmpMatrix[i], 0, matx[i].length);
            }
        }
        else {
            for (int i = 0; i < matx.length; i++) {
                System.arraycopy(matx[i], 0, tmpMatrix[i], 0, matx[i].length);
            }

            for (int i = matx.length; i < count; i++) {
                for (int j = 0; j < matx[0].length; j++) {
                    tmpMatrix[i][j] = rand.nextInt(MAX_A);
                }
            }
        }

        matx = tmpMatrix;
    }

    public void setColumnsCount(int count)
    {
        assert (matx != null);

        if (count <= 0)
        {
            throw new IndexOutOfBoundsException();
        }

        int[][] tmpMatrix = new int[matx.length][count];

        if (count < matx[0].length) {
            for (int i = 0; i < matx.length; i++) {
                System.arraycopy(matx[i], 0, tmpMatrix[i], 0, count);
            }
        }
        else {
            for (int i = 0; i < matx.length; i++) {
                System.arraycopy(matx[i], 0, tmpMatrix[i], 0, matx[i].length);
            }

            for (int i = 0; i < matx.length; i++) {
                for (int j = matx[0].length; j < count; j++) {
                    tmpMatrix[i][j] = rand.nextInt(MAX_A);
                }
            }
        }

        matx = tmpMatrix;
    }

    public void printSubMatrix(int posI, int posJ, int rows, int cols) {
        assert (matx != null);

        if (posI < 0 || posI >= matx.length || posJ < 0 || posJ >= matx[0].length ||
                rows < 0 || posI + rows > matx.length || cols < 0 || posJ + cols > matx[0].length) {
            throw new IndexOutOfBoundsException();
        }

        System.out.println("Matrix from position (" + posI + " , " + posJ + ") with size " + rows + " x " + cols + ":");

        for (int i = posI; i < posI + rows; i++) {
            for (int j = posJ; j < posJ + cols; j++) {
                System.out.print(matx[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void printMatrix() {
        assert (matx != null);

        printSubMatrix(0, 0, matx.length, matx[0].length);
    }

    public void changeRows(int k) {
        assert (matx != null);

        if (k < 0 || k >= matx[0].length) {
            throw new IndexOutOfBoundsException();
        }

        int minInColumn = Integer.MAX_VALUE;
        int posMin = 0;
        int maxInColumn = Integer.MIN_VALUE;
        int posMax = 0;

        for (int i = 0; i < matx.length; i++) {
            if (matx[i][k] > maxInColumn) {
                maxInColumn = matx[i][k];
                posMax = i;
            }

            if (matx[i][k] < minInColumn) {
                minInColumn = matx[i][k];
                posMin = i;
            }
        }

        int[] tmp;

        tmp = matx[posMax];
        matx[posMax] = matx[posMin];
        matx[posMin] = tmp;

        System.out.println("Swapped rows " + posMin + " and " + posMax);
        printMatrix();
    }
}
