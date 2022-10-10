import java.util.concurrent.ThreadLocalRandom;

public class ArrayUtils {

    public static double[] sumDoubleArrays(double[] a1, double[] a2) {
        int size = a1.length;
        if (a2.length < size){
            size = a2.length;
        }
        double[] array = new double[size];
        for (int i=0; i < size; i++){
            array[i] = a1[i]+a2[i];
        }
        return array;
    }

    public static double arrayAverage(double[] a1) {
        double ave = 0;
        for (int i=0; i < a1.length; i++){
            ave += a1[i];
        }
        return ave/a1.length;
    }

    public static double[][] scaleTransform(double[][] mat, double scalar) {
        int n = mat.length;
        int m = mat[0].length;
        double[][] result = new double[n][m];
        for (int i=0; i < m; i++){
            for (int j=0; j < mat.length; j++) {   /// mat.length
                result[j][i] = mat[j][i] * scalar;  ///[j][i]
            }
        }
        return result;
    }

    public static double[][] scaleTransform1(double[][] mat, double scalar) {
        int n = mat.length;
        int m = mat[0].length;
        double[][] result = new double[n][m];
        for (int i=0; i < m; i++){
            for (int j=0; j < n; j++) {
                result[j][i] = mat[j][i] * scalar;   ///[j][i]
            }
        }
        return result;
    }

    public static double[][] scaleTransform2(double[][] mat, double scalar) {
        int n = mat.length;
        int m = mat[0].length;
        double[][] result = new double[n][m];
        for (int i=0; i < n; i++){
            for (int j=0; j < m; j++) {
                result[i][j] = mat[i][j] * scalar;  ///all good
            }
        }
        return result;
    }

    public static double[][] scaleTransform(double[][] mat, double[] array) {
        int n = mat.length;
        int m = mat[0].length;
        if (n!= array.length) return mat;
        double[][] result = new double[n][m];
        for (int i=0; i < m; i++){
            for (int j=0; j < mat.length; j++) {
                result[j][i] = mat[j][i] * array[j];
            }
        }
        return result;
    }

    public static double[][] scaleTransform2(double[][] mat, double[] array) {
        int n = mat.length;
        int m = mat[0].length;
        if (n!= array.length) return mat;
        double[][] result = new double[n][m];
        for (int i=0; i < n; i++){
            for (int j=0; j < m; j++) {
                result[i][j] = mat[i][j] * array[i];
            }
        }
        return result;
    }



    public static double[][] matMult(double[][] a1, double[][] a2) {
        double [][] result = new double[a1.length][a2[0].length];
        for (int i = 0; i < a1.length; i++) {
            for (int j = 0; j < a2[0].length; j++) {
                for (int k = 0; k < a1[0].length; k++) {
                    result[i][j] += a1[i][k] * a2[k][j];
                }
            }
        }
        return result;
    }

    public static double[][] matAdd(double[][] m1, double[][] m2) {
        int n = m1.length;
        int m = m1[0].length;
        boolean result = true;
        if(m1.length != m2.length) result= false;
        if(m1[0].length != m2[0].length) result= false;

        double [][] mR = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                mR[i][j] = m1[i][j] + m2[i][j];
            }
        }
        return mR;
    }

    public static double[] createDoubleArray(int size) {
        double[] array = new double[size];
        for (int i=0; i < size; i++){
            array[i] = ThreadLocalRandom.current().nextDouble();
        }
        return array;
    }

    public static double[][] createDoubleMat(int size) {
        double[][] array = new double[size][size];
        for (int i=0; i < array.length; i++){
            for (int j=0; j < array[0].length; j++) {
                array[i][j] = ThreadLocalRandom.current().nextDouble();
            }
        }
        return array;
    }

    public static void useDoubleMat(double[][] m1) {
        int n = ThreadLocalRandom.current().nextInt(0,m1.length);
        int m = ThreadLocalRandom.current().nextInt(0,m1[0].length);
        if(m1[n][m] == 1.1 && m1[n][m] == 1.2 ){
            System.out.println("error");
        }
    }

    public static void printDoubleMat(double[][] array) {
        int n = array.length;
        int m = array[0].length;
        for (int i=0; i < n; i++){
            for (int j=0; j < m; j++) {
                System.out.print(array[i][j] +", ");
            }
            System.out.println();
        }
    }

    public static boolean doubleMatEquals(double[][] m1, double[][] m2) {
        boolean result = true;
        if(m1.length != m2.length) return false;
        if(m1[0].length != m2[0].length) return false;
        int n = m1.length;
        int m = m1[0].length;
        for (int i=0; i < n; i++){
            for (int j=0; j < m; j++) {
                if (m1[i][j] != m2[i][j]) return false;
            }
        }
        return result;
    }

    public static void printDoubleMat(double[] array) {
        int n = array.length;
        for (int i=0; i < n; i++){
            System.out.print(array[i] +", ");
        }
        System.out.println();
    }
}
