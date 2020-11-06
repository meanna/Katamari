import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Map {


    private boolean[][] collisionMap;

    private boolean isReady;

    public boolean getReady()
    {
        return isReady;
    }

    public Map(String image, int collisionScale)
    {
        //LevelReader reader = new LevelReader(new File(image + ".png"));
        //reader.load();
       // reader.createMap();
        //collisionMap = reader.getMap(collisionScale);
       // isReady = true;

        /*
        try
        {

        }
        /*catch (IOException e)
        {
            System.out.print("File not found");
        }*/
    }

    public void CheckCollisionWithWalls(ArrayList<Mob> mobList)
    {
        return;
        /*for (Mob m : mobList)
        {
            for (int x = 0; x < collisionMap.length; x++)
            {
                for (int y = 0; y < collisionMap[x].length; y++)
                {
                    if (collisionMap[x][y] && m.CollidesWithPoint(x, y)) m.CollidedWithPoint(x, y);
                }
            }
        }*/
    }
}
