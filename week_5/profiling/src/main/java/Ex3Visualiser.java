public class Ex3Visualiser {


    public static void rgb(Ex3ScreenImage imageIn) {
        int rgbMaxValue = 256;
        int[][][] image = imageIn.getimage();
        int size = imageIn.getSize();
        for (int j = 0; j < size; j++){
            for (int i = 0; i<size; i++) {
                image[i][j][0] = Math.floorMod(image[i][j][0], rgbMaxValue);
                image[i][j][1] = Math.floorMod(image[i][j][1], rgbMaxValue);
                image[i][j][2] = Math.floorMod(image[i][j][2], rgbMaxValue);
            }
        }
    }
}
