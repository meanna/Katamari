import java.awt.Color;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


/**
 * true for pixel with white color
 * false for other colors
 */

public class LevelReader {


    private boolean[][] wallMap;
    private int w;
    private int h;
    private int scale;


    private File file;
    private Color[] colors;
    private boolean[][] boolMap;
    private int[][] pixelMap;
    private Color[][] colorMap;


    public LevelReader(File file,int scale) {

        this.file = file;
        this.scale = scale;

    }

    public void readImage() {

        BufferedImage img;

        try {
            img = ImageIO.read(file);

            h = img.getHeight();
            w = img.getWidth();
            this.pixelMap = new int[w][h];
            this.colorMap = new Color[w][h];
            int total_pixels = (h * w);
            colors = new Color[total_pixels];
            int i = 0;

            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    colors[i] = new Color(img.getRGB(x, y));
                    this.pixelMap[x][y] = img.getRGB(x, y);
                    this.colorMap[x][y] = new Color(pixelMap[x][y]);
                    i++;


                }
            }
            System.out.println("map is loaded");


        } catch (IOException e) {
        }

    }


    public void createWallMap(int scale) {
        createBoolMap();
        wallMap = scaleBoolMap(boolMap, scale);
        //printMap(wallMap);



    }


    public boolean[][] getWallMap() {
        return this.wallMap;
    }




    public void printMap(boolean[][] map) {
        for (int y = 0; y < map.length; y++) {
            System.out.println();
            for (int x = 0; x < map[0].length; x++) {
                if (map[y][x] == true) System.out.print("*");
                else System.out.print("-");

            }

        }
    }

    // read pixel in to boolean
    private void createBoolMap() {

        boolMap = new boolean[h][w];
        for (int x = 0; x < w; x++) {

            for (int y = 0; y < h; y++) {
                if (this.colorMap[x][y].getRGB() == Color.white.getRGB()) {
                    boolMap[y][x] = true;
                } else {
                    boolMap[y][x] = false;
                }

            }
        }


    }


    private boolean[][] scaleBoolMap(boolean[][] map, int scale) {
        this.scale = scale;
        int h = map.length;
        int w = map[0].length;

        boolean[][] biggerMap = new boolean[h * scale][w * scale];

        for (int row = 0; row < h; row++) {

            for (int col = 0; col < w; col++) {

                for (int a = 0; a < scale; a++) {

                    for (int b = 0; b < scale; b++) {

                        biggerMap[(row * scale) + a][(col * scale) + b] = map[row][col];

                    }

                }
            }
        }
        return biggerMap;
    }


    public Color[] getColorMap() { // map is static
        return this.colors;
    }

    public Color getAGB(int x, int y) {
        return this.colors[x + (y * w)];
    }



    public int getImageW() {
        return w;
    }

    public int getImageH() {
        return h;
    }

    public int getScale() {
        return scale;
    }


}