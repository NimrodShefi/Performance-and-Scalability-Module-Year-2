import java.util.concurrent.ThreadLocalRandom;

public class Ex3Main {

    public static void main(String[] args) {
        runTheApp(100);
    }

    private static void runTheApp(int i) {
        Ex3ScreenImage image = new Ex3ScreenImage(i);
        while (true){
            int[][][] adjustMatrix = createAdjustmentMatrix(3, i);
            int[][] adjustArray = createAdjustmentArray(3, i);
            image.adjustImageX(adjustArray);
            image.adjustImageY(adjustArray);
            Ex3Visualiser.rgb(image);
        }
    }

    private static int[][] createAdjustmentArray(int max, int size) {
        int[][] adjustment = new int[size][3];
        for (int i = 0; i < size; i++){
                adjustment[i][0] = ThreadLocalRandom.current().nextInt(max*2) - max;
                adjustment[i][1] = ThreadLocalRandom.current().nextInt(max*2) - max;
                adjustment[i][2] = ThreadLocalRandom.current().nextInt(max*2) - max;

        }
        return adjustment;
    }

    private static int[][][] createAdjustmentMatrix(int max, int size) {
        int[][][] adjustment = new int[size][size][3];
        for (int i = 0; i < size; i++){
            for (int j = 0; j<size; j++) {
                adjustment[i][j][0] = ThreadLocalRandom.current().nextInt(max*2) - max;
                adjustment[i][j][1] = ThreadLocalRandom.current().nextInt(max*2) - max;
                adjustment[i][j][2] = ThreadLocalRandom.current().nextInt(max*2) - max;
            }
        }
        return adjustment;
    }


}
