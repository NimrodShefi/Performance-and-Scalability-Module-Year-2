public class Work {
    static volatile int n = 4000;

    public static void main(String[] args) {
        Work test = new Work();
        while(true) {
            ArrayUtils.useDoubleMat(test.work(n));
        }
    }

    public double[][] work(int size) {
        double[][] m1 = ArrayUtils.createDoubleMat(size);
        double[][] m2 = ArrayUtils.createDoubleMat(size);
        return work3(work1(size), ArrayUtils.matAdd(m1,m2));
    }


    private double[][] work3(double[] array, double[][] mat) {
        return ArrayUtils.scaleTransform(mat, array);
    }


    private double[] work1(int size) {
        double[] a1 = ArrayUtils.createDoubleArray(size);
        double[] a2 = ArrayUtils.createDoubleArray(size);
        return ArrayUtils.sumDoubleArrays(a1,a2);
    }


}
