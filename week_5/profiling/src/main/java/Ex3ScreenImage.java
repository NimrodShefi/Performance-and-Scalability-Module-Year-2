public class Ex3ScreenImage {

    int[][][] image;
    int size;

    public Ex3ScreenImage(int size) {
        this.size = size;
        image = new int[size][size][3];
        for (int i = 0; i < size; i++){
            for (int j = 0; j<size; j++) {
                image[i][j] = new int[]{i, j, i + j};
            }
        }
    }


    public void adjustImage(int[][][] adjustMatrix) {
        for (int i = 0; i < size; i++){
            for (int j = 0; j<size; j++) {
                image[i][j][0] = image[i][j][0]+adjustMatrix[i][j][0];
                image[i][j][1] = image[i][j][1]+adjustMatrix[i][j][1];
                image[i][j][2] = image[i][j][2]+adjustMatrix[i][j][2];
            }
        }
    }

    public void adjustImageX(int[][] adjustArray) {
        for (int i = 0; i < size; i++){
            for (int j = 0; j<size; j++) {
                image[i][j][0] = image[i][j][0]+adjustArray[i][0];
                image[i][j][1] = image[i][j][1]+adjustArray[i][1];
                image[i][j][2] = image[i][j][2]+adjustArray[i][2];
            }
        }
    }

    public void adjustImageY(int[][] adjustArray) {
        for (int i = 0; i < size; i++){
            for (int j = 0; j<size; j++) {
                image[i][j][0] = image[i][j][0]+adjustArray[j][0];
                image[i][j][1] = image[i][j][1]+adjustArray[j][1];
                image[i][j][2] = image[i][j][2]+adjustArray[j][2];
            }
        }
    }


    public int getSize() {
        return size;
    }

    public int[][][] getimage() {
        return image;
    }


}
