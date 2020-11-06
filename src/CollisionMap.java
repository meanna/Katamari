import java.awt.*;
import java.io.File;

public class CollisionMap {
    private boolean[][] collisionMap;
    private int imageHeight;
    private int imageWidth;
    private int mapWidth;
    private int mapHeight;
    private boolean isReady;
    private LevelReader r;

    public CollisionMap(String image, int collisionScale) {
        r = new LevelReader(new File(image+".png"),collisionScale);
        r.readImage();
        r.createWallMap(collisionScale);
        collisionMap = r.getWallMap();
        this.imageHeight = r.getImageH();
        this.imageWidth = r.getImageW();
        this.mapHeight = r.getImageH() * collisionScale;
        this.mapWidth = r.getImageW() * collisionScale;
        isReady = true;
    }

    public boolean getValueAt(double x, double y) {

        if (collisionMap[(int) y][(int) x] == true) {
            return true;
        } else {
            return false;
        }
    }

    public boolean getReady() {
        return isReady;
    }
    public void printMap(){
        for (int y = 0; y < collisionMap.length; y++) {
            System.out.println();
            for (int x = 0; x < collisionMap[0].length; x++) {
                if (collisionMap[y][x] == true) System.out.print("*");
                else System.out.print("-");

            }

        }
    }


    public boolean[][] getCollisionMap() {
        return collisionMap;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public boolean isReady() {
        return isReady;
    }

    public LevelReader getR() {
        return r;
    }
}