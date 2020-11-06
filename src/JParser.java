import org.json.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class JParser {

  /*Data to be read out of the JSON files*/
  private int collisionscale;
  private String collisionmap;
  private int tilesize;

  /*List of kinds of mobs*/
  ArrayList<Mob> mobKinds = new ArrayList<Mob>();
  /*List of mobs*/
  ArrayList<Mob> mobList = new ArrayList<Mob>();
  /*List of Tiles*/
  ArrayList<Tile> tileList = new ArrayList<Tile>();
  /*List of Behaviours*/
  ArrayList<BehaviourCustom> behaviourList = new ArrayList<BehaviourCustom>();

  /*Reference to the Json File*/
  private String levelResource = "levels/new_level.json";

  private String test = "";

  private FileReader fileReader;

  /*Here the actual parsing takes place*/ {
    try {
      fileReader = new FileReader(levelResource);

      JSONTokener tokener = new JSONTokener(fileReader);
      JSONObject level = new JSONObject(tokener);

      /*Reading of single values*/
      collisionscale = level.getInt("collisionscale");
      collisionmap = level.getString("collisionmap");
      tilesize = level.getInt("tilesize");

      /*Reading of kinds of mobs*/
      JSONObject mobdef = level.getJSONObject("mobdef");

      for (String k : mobdef.keySet()) {
        JSONObject mobKind = mobdef.getJSONObject(k);
        String kind = k;
        String img = mobKind.getString("img");
        int size = mobKind.getInt("size");
        int mass = (int) Math.pow(size, 3);

        /*Checking if Mob is computer controlled or not*/
        Object checkObject = mobKind.get("control");
        if (checkObject instanceof String) {
          String control = mobKind.getString("control");
          Player mob = new Player(k, size, img);
          mob.setControl(control);
          mob.setMass(mass);
          mobKinds.add(mob);
        } else if (checkObject instanceof JSONObject) {

          JSONObject control = mobKind.getJSONObject("control");

          /*Array with behave information*/
          JSONArray behaveArray = control.getJSONArray("behave");

          if(behaveArray.length() != 0) {
            for(int i = 0; i < behaveArray.length(); i++) {
              JSONObject behaveObject = behaveArray.getJSONObject(i);
              int distance = behaveObject.getInt("dist");
              String action = behaveObject.getString("action");
              String entity = behaveObject.getString("entity");

              BehaviourCustom behaviour = new BehaviourCustom(action, entity, distance);
              behaviourList.add(behaviour);
            }
          }

          boolean hungry = mobKind.getBoolean("hungry");
          String defaultBehave = control.getString("default");

          ComMob mob = new ComMob(kind, size, img, true);
          mob.setMass(mass);
          mob.setDefaultBehave(Behaviour.CreateFromString(defaultBehave));
          mobKinds.add(mob);
        }
      }

      /*Reading of the mobs on the field at the beginning of the game*/
      JSONArray mobs = level.getJSONArray("mobs");
      for (int i = 0; i < mobs.length(); i++) {
        JSONObject mob = mobs.getJSONObject(i);
        String kind = mob.getString("kind");
        int xPos = mob.getInt("x");
        int yPos = mob.getInt("y");

        for (Mob m : mobKinds) {
          if (m.getKind().equals(kind) && m instanceof Player) {
            Player newMob = new Player(kind, xPos, yPos, m.getSize(), m.getImg());
            newMob.setControl(((Player) m).getControl());
            newMob.setImg(m.getImg());
            newMob.setSize(m.getSize());
            newMob.setMass(m.getMass());
            mobList.add(newMob);
          } else if (m.getKind().equals(kind) && m instanceof ComMob) {
            ComMob newMob = new ComMob(kind, xPos, yPos, m.getSize(), m.getImg(), m.getHungry());
            newMob.setImg(m.getImg());
            newMob.setSize(m.getSize());
            newMob.setMass(m.getMass());
            newMob.setHungry(((ComMob) m).getHungry());
            newMob.setDefaultBehave(((ComMob) m).getDefaultBehave());
            mobList.add(newMob);
          }
        }
      }

      for (Mob m : mobList) {
        System.out.println(m.getKind() + ", " + m.getSize() + ", " + m.getxPos() + ", " + m.getyPos() + ", " + m.getMass());
      }

      /*Reading the tiles of the level*/
      JSONArray tiles = level.getJSONArray("tiles");
      for (int i = 0; i < tiles.length(); i++) {
        JSONObject tile = tiles.getJSONObject(i);
        int xPos = tile.getInt("x");
        int yPos = tile.getInt("y");
        String name = tile.getString("name");
        Tile newTile = new Tile(xPos, yPos, name);

        tileList.add(newTile);
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  /*Getters*/
  /*Only for testing, will be removed later*/
  public String getTest() {
    return test;
  }

  public int getCollisionscale() {
    return collisionscale;
  }

  public String getCollisionmap() {
    return collisionmap;
  }

  public int getTilesize() {
    return tilesize;
  }

  public ArrayList<Mob> getMobList() {
    return mobList;
  }

  public ArrayList<Tile> getTileList() {
    return tileList;
  }

  public ArrayList<BehaviourCustom> getBehaviourList() {
    return behaviourList;
  }
}
