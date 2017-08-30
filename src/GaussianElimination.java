import java.util.Arrays;

/**
 * Created by Raymond on 8/30/2017.
 */
public class GaussianElimination {
    int rows;
    int cols;
    int[][] matrix;
    public GaussianElimination(int[][] matrix) {
        if (matrix == null) {
            throw new NullPointerException("matrix cannot be null");
        }
        this.rows = matrix.length;
        this.cols = matrix[0].length;
        this.matrix = new int[rows][cols];
        copyMatrix(this.matrix, matrix);
    }

    /*Row reduces our rows*cols=rows*(n+1) matrix into row reduced
    echelon form with Gaussian elimination. Where our matrix is a
    system of m equations with n variables.*/
    private void gaussianEliminate() {
        for (int i = 0; i < (cols - 1); i++) {
            System.out.println("Outer loop number: " + i);
            if (matrix[i][i] == 0) {
                boolean restColZero = true;
                int firstNonzeroRow = i;
                for (int ip = i; i < rows; ip++) {
                    if (matrix[ip][i] != 0) {
                        restColZero = false;
                        firstNonzeroRow = ip;
                        break;
                    }
                }
                if (restColZero) {
                    continue;
                } else {
                    swapRows(matrix, i, firstNonzeroRow);
                    System.out.println("Swapping rows " + i + " and " + firstNonzeroRow);
                }
            }
            int divider = matrix[i][i];
            System.out.println("Dividing row " + i + " by " + divider);
            printArray(matrix);
            for (int ip = 0; ip < (cols); ip++) {
                matrix[i][ip] /= divider;
            }
            for (int k = i + 1; k < rows; k++) {
                System.out.println("Subtracting " + matrix[k][i] + " times row " + i + " from row " + k);
                scaleSubtract(matrix, matrix[i], k, matrix[k][i]);
                printArray(matrix);
            }
        }
        System.out.println("Starting back substitution");
        for (int u = cols - 2; u >= 0; u--) {
            if (matrix[u][u] != 0) {
                for (int v = u - 1; v >= 0; v--) {
                    scaleSubtract(matrix, matrix[u], v, matrix[v][u]);
                    System.out.println("Subtracting " + matrix[v][u] + " times row " + u + " from row " + v);
                    printArray(matrix);
                }
            }
        }
    }

    /*Copies matrix b into matrix a*/
    private void copyMatrix(int[][] a, int[][] b) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                a[i][j] = b[i][j];
            }
        }
    }

    /*Swaps the rows rowNum and rowNump in a*/
    private void swapRows(int[][] a, int rowNum, int rowNump) {
        for (int i = 0; i < a.length; i++) {
            int temp = a[rowNum][i];
            a[rowNum][i] = a[rowNump][i];
            a[rowNump][i] = temp;
        }
    }

    /*Subtracts an array as a row from a at rowNum,
    with the array multiplied by factor*/
    private void scaleSubtract(int[][] a, int[] row, int rowNum, int factor) {
        for (int i = 0; i < cols; i++) {
            a[rowNum][i] -= row[i] * factor;
        }
    }

    public void printArray(int[][] arr) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[][] myMatrix = {{2,4,2,8}, {1,1,1,6}, {1,-1,-1,4}};
        //int[][] myMatrix = {{49,7,49}, {42,6,42}};
        GaussianElimination reducer = new GaussianElimination(myMatrix);
        System.out.println("Original matrix: ");
        reducer.printArray(reducer.matrix);
        reducer.gaussianEliminate();
        System.out.println("Result matrix: ");
        reducer.printArray(reducer.matrix);
    }
}
