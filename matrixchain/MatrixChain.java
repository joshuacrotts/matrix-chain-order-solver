package matrixchain;

import java.util.Scanner;

/**
 * Given an array of dimensions, we want to find the minimum amount of operations
 * to multiply n matrices together. As we have seen there are a few different ways
 * to do this, with the native approach being O(n!), the native recursive solution
 * being O(3^n), and now, the dynamic programming solution is O(n^3). 
 * 
 * The idea is to continuously store previous calculations in a [growing] table, 
 * so we don't have to keep re-calculating them when we have to re-use them.
 * 
 * To use this, enter the number of dimensions you have, and then the number 
 * of combined dimensions.
 * 
 * So, example:
 * 
 * 5 2 5 4 1 10
 * 
 * This suggests we have FOUR matrices, with the dimensions being:
 * 
 * 2 x 5  = A1
 * 5 x 4  = A2
 * 4 x 1  = A3
 * 1 x 10 = A4
 *
 * @author Joshua
 */
public class MatrixChain {

    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {

        //  # of matrix dimensions entered (thus, the number of MATRICES is this - 1).
        final int size = in.nextInt();

        //  Matrix dimensions entered by user.
        int[] matrixDimensions = new int[size];

        for (int i = 0; i < matrixDimensions.length; i++) {
            matrixDimensions[i] = in.nextInt();
        }

        printDimensions(matrixDimensions);
        
        System.out.println(findMinOperations(matrixDimensions, true, true));
    }

    /**
     * Finds the minimum number of operations to multiply n matrices together.
     * 
     * @param matrixDimensions - array of dimensions {A, B, C, D, ...}
     * @param printOptimalParenthesis - boolean for if the user wants to be able to see the optimal set of parenthesis.
     * @param printMat - boolean for if the user wants to see the DP matrix.
     * @return min number of operations.
     */
    public static int findMinOperations(int[] matrixDimensions, boolean printOptimalParenthesis, boolean printMat) {
        int numMatrices = matrixDimensions.length - 1;

        //  DP matrix.
        int[][] m = new int[numMatrices][numMatrices];
        
        //  Values of k that, at any given cell in the DP matrix, minimize the overall cost for that particular
        //  multiplication. 
        int[][] kValues = new int[numMatrices][numMatrices];
        
        //  r0-r_n values.
        int[] rValues = new int[matrixDimensions.length];
        System.arraycopy(matrixDimensions, 0, rValues, 0, matrixDimensions.length);

        //  When i = j, this is the base case (this is done by default in Java
        //  but it's fine).
        for (int i = 0; i < m.length; i++) {
            m[i][i] = 0;
        }

        //  When j < i, the spaces are invalid.
        //  We technically don't need to do this, but eh.
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m.length; j++) {
                if (j < i) {
                    m[i][j] = -1;
                    continue;
                }
            }
        }

        //  L is the chain length (remember we have to offset everything by 
        //  1 to account for CLRS.
        //
        //  We iterate L times to account for each "type" of matrix multiplication.
        //  For instance, at first, we have two matrices multiplied together.
        //  Then three, then four, then n.
        //
        for (int L = 1; L < numMatrices; L++) {
            
            // 
            //  i <= k <= j - 1.
            //
            for (int i = 0; i < numMatrices - L; i++) {
                int j = i + L;

                m[i][j] = Integer.MAX_VALUE;
                for (int k = i; k < j; k++) {
                    
                    //  Perform the matrix multiplication 
                    //  q = m[i][k] + m[k + 1][j] + (r[i] * r[k + 1] * r[j + 1]
                    int mult = m[i][k] + m[k + 1][j] + (rValues[i] * rValues[k + 1] * rValues[j + 1]);
                    if (mult < m[i][j]) {
                        m[i][j] = mult;
                        kValues[i][j] = k;
                    }
                }
            }
        }

        if (printOptimalParenthesis) {
            System.out.println(printParenthesis(kValues, 0, numMatrices - 1));
        }
        
        if(printMat) { 
            printMatrix(m);
        }
        
        return m[0][m.length - 1];
    }

    /**
     * Prints the matrix.
     * @param m 
     */
    public static void printMatrix(int[][] m) {
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m.length; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println("");
        }
    }

    /**
     * Prints the optimal arrangement of parenthesis for a
     * matrix given the k-values that minimized the overall cost for a
     * specific i and j value.
     * 
     * @param kValues
     * @param i
     * @param j
     * @return 
     */
    public static String printParenthesis(int[][] kValues, int i, int j) {
        if(i == j) {
            return String.format("A" + (i + 1));
        } else {
            StringBuilder sb = new StringBuilder();
            
            sb.append("(");
            sb.append(printParenthesis(kValues, i, kValues[i][j]));
            sb.append(" * ");
            sb.append(printParenthesis(kValues, kValues[i][j] + 1, j));
            sb.append(")");
            return sb.toString();
        }
    }

    /**
     * Prints the dimensions for the given matrix in the form
     * 
     * {A, B, C, D}
     * 
     * where A.rows = A, A.columns = B
     *       B.rows = B, B.columns = C
     * 
     * so on and so forth.
     * @param matrices 
     */
    public static void printDimensions(int[] matrices) {
        for (int a = 0; a < matrices.length - 1; a++) {
            System.out.println("A-" + (a + 1) + " rows: " + matrices[a] + ", columns: " + matrices[a + 1] + ".");
        }
    }
}
