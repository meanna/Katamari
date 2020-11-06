import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;


public class Model {
    public enum GameState {
        None, Running, Win, Lose
    }

    // The FPS with which the program runs
    private final int TicksPerSecond = 20;

    private CollisionMap map;
    private ArrayList<Mob> mobList;
    private Player player1;
    private Player player2;
    private static double friction = 0.75;


    private static double acceleration = 10;
    private static double breakCoefficient = 0.2;
    private static double repelCoefficient = 25;

    private GameState gameState = GameState.None;
    private int tick;

    public Model(JParser jparser) {
        map = new CollisionMap(jparser.getCollisionmap(), jparser.getCollisionscale());
        mobList = jparser.getMobList();

        GetPlayersFromMoblist(jparser);

        System.out.println("Loaded " + mobList.size() + " mobs");

        gameState = GameState.Running;

        //    Timeline t = new Timeline(new KeyFrame(Duration.millis(1000 / (double)TicksPerSecond), (x) -> Update()));
        //   t.setCycleCount(Animation.INDEFINITE);
        //   t.play();
    }

    // For testing
    public Model() {

    }

    public void setMap(CollisionMap map) {
        this.map = map;
    }

    public void setMobList(ArrayList<Mob> mobList) {
        this.mobList = mobList;
    }

    // easy contructor for testing without json
    public Model(CollisionMap map, ArrayList<Mob> mobList) {
        this.map = map;
        this.mobList = mobList;

        Update();

    }

    public void Update2() {
        UpdatePhysics3();
        //checkBehave(100);
        //checkMobCollision();
        checkWallCollision();


    }

    public void UpdatePhysics3() {
        for (Mob m : mobList) {
            //m.Move();
            //m.randomMove(10);
            m.Move();

        }

    }

    public void checkWallCollision() {

        for (Mob m : mobList) {

            //wallCollision(m);
            //wallCollision4(m);
            //wallCollision5(m);
            wallCollision6(m);


        }

    }

    //need iterator TODO
    public void checkMobCollision() {


        for (int i = 0; i < this.mobList.size(); i++) {
            for (int j = i + 1; j < this.mobList.size(); j++) {

                if (this.mobList.get(i) != this.mobList.get(j) && this.mobList.get(i).isAlive && this.mobList.get(j).isAlive) {
                    //System.out.println(this.mobList.get(i).getImg()+ "  with "+ this.mobList.get(j).getImg());
                    afterCollision(this.mobList.get(i), this.mobList.get(j));


                }

            }
        }

        for (int x = 0; x < this.mobList.size(); x++) {
            // System.out.println(this.mobList.get(x).getImg());
            //System.out.println(this.mobList.get(x).getIsAlive());

            if (!this.mobList.get(x).isAlive) {
                this.mobList.remove(this.mobList.get(x) + " " + this.mobList.get(x).getIsAlive());
                //System.out.println();
                //System.out.println(" removed " +this.mobList.get(x).getImg());
            }
        }


    }

    // cal every small step before moving
    public void wallCollision6(Mob mob) {
        double breakCoef = 0.4;
        double xBoundLeft = mob.getxPos() - mob.size;
        double xBoundRight = mob.getxPos() + mob.size;
        double yBoundTop = mob.getyPos() - mob.size;
        double yBoundBottom = mob.getyPos() + mob.size;
        double offset = 5;


        int w = map.getMapWidth();
        int h = map.getMapHeight();

        double newX = mob.getxPos();
        double newY = mob.getyPos();
        double previousX = mob.getxPos() - mob.getxVelocity();
        double previousY = mob.getyPos() - mob.getyVelocity();


        Point2 newPos = new Point2(newX, newY);
        Point2 oldPos = new Point2(previousX, previousY);

        //System.out.println("new"+ newPos.x +" , "+ newPos.y);
        //System.out.println("old" +oldPos.x + ": " + oldPos.y);

        int step = 1;
        double dist = Point2.calDistancePoint(newPos, oldPos);
        double numPos = (int) (dist / step);
        double slop = Point2.calSlope(newPos, oldPos);

        Point2[] positionOfsmallStep = new Point2[(int) numPos];
        //System.out.println(oldPos.x+","+oldPos.y+" newpos= " +newPos.x+","+newPos.y);
        mean:
        {
            for (int i = 0; i < numPos; i += step) {
                double nextStep = step;

                positionOfsmallStep[i] = Point2.findPointOnLine(oldPos, newPos, i, slop);
                //System.out.println(oldPos.x+","+oldPos.y+" newpos= " +newPos.x+","+newPos.y+" : "
                // + positionOfsmallStep[i].x+","+ positionOfsmallStep[i].y );
                // System.out.println(positionOfsmallStep[i].x+","+ positionOfsmallStep[i].y );
                //System.out.println("step : "+nextStep);

                //System.out.println(positionOfsmallStep[i].x);
                //System.out.println(positionOfsmallStep[i].y);
                //Point2[] createPoints(int points, Point2 p, double mobsize) {
                Point2[] pointsAroundCircle = Point2.createPoints(20, positionOfsmallStep[i], mob.getSize());
                int numP = 0;

                for (Point2 p : pointsAroundCircle) {
                    //xBoundLeft >= 0 && xBoundRight < w && yBoundBottom < h && yBoundTop >= 0
                    if (p.x >= 0 && p.y >= 0 && p.x < w && p.y < h) {
                        if (map.getValueAt(p.x, p.y) == false) {
                            numP += 1;
                            //System.out.println("numP " + numP);
                            //System.out.println("Wall!");

                            if (i - 1 >= 0) {
                                mob.setxPos(positionOfsmallStep[i - 1].x);
                                mob.setyPos(positionOfsmallStep[i - 1].y);
                               // flee2(mob,p,10);
                                // addFactor(mob,0.4);


                            } else {

                                System.out.println("hit wall");
                                // addFactor(mob,0.4);

                                if (mob.xVelocity <= 0) {
                                    //setXDirection(mob, -1);
                                    //mob.setxPos(previousX+offset);
                                    mob.setxPos(positionOfsmallStep[i].x + offset);

                                } else {
                                    //setXDirection(mob, -1);
                                    // mob.setxPos(previousX-offset);
                                    mob.setxPos(positionOfsmallStep[i].x - offset);

                                }
                                if (mob.yVelocity <= 0) {
                                    // setYDirection(mob, -1);
                                    // mob.setyPos(previousY+offset);
                                    mob.setyPos(positionOfsmallStep[i].y + offset);
                                } else {
                                    //setYDirection(mob, -1);
                                    // mob.setyPos(previousY-offset);
                                    mob.setyPos(positionOfsmallStep[i].y - offset);
                                }

                               //  chase(mob,new Point2(previousX,previousY),10);
                               mob.setYVelocity(0);
                                mob.setXVelocity(0);
                                break mean;

                            }

                            //mob.setxPos(previousX);
                            //mob.setyPos(previousY);
                            //System.out.println(mob.xPos + " " + mob.getyPos());

                            //mob.setXVelocity(-mob.getxVelocity());
                            //mob.setYVelocity(-mob.getyVelocity());
                        }
                    } else {
                            System.out.println("beyon wall");
                            // mob.setxPos(positionOfsmallStep[i].x);
                            //mob.setyPos(positionOfsmallStep[i].y);
                            mob.setYVelocity(0);
                            mob.setXVelocity(0);
                            mob.setxPos(previousX);
                            mob.setyPos(previousY);


                        //mob.CollidedWithPoint(0.4, 1,previousX, previousX);
/**
 if (xBoundLeft < 0 || xBoundRight > w ) {
 mob.setXVelocity(-mob.getxVelocity());

 } if ((yBoundBottom > h)|| (yBoundTop > 0)) {
 mob.setYVelocity(-mob.getyVelocity());

 }**/


                    }
                }


            }


        }


    }

    public void setXDirection(Mob m, double direction) {

        m.setXVelocity(m.xVelocity * direction);

    }

    public void setYDirection(Mob m, double direction) {

        m.setYVelocity(m.yVelocity * direction);
    }


    public void addFactor(Mob m, double coef) {
        m.setXVelocity(m.getxVelocity() * coef);
        m.setYVelocity(m.getyVelocity() * coef);
    }


    public void flee2(Mob m, Point2 p, double speed) {
        double distance = Model.calDistance2(m.getxPos(), m.getyPos(), p.x, p.y);
        double tx = m.xPos, ty = m.yPos, sx = p.x, sy = p.y;

        double deltaX = tx - sx;
        double deltaY = ty - sy;
        double angle = Math.atan2(deltaY, deltaX);

        m.setXVelocity(speed * Math.cos(angle) * 0.4);
        m.setYVelocity(speed * Math.sin(angle) * 0.4);

        //currentX += speed * Math.cos( angle );
        //currentY += speed * Math.sin( angle );


    }

    public Point[] createPoints(int points, Mob mob) {
        Point[] pointList = new Point[points];

        double slice = 2 * Math.PI / points;
        for (int i = 0; i < points; i++) {
            double angle = slice * i;
            int newX = (int) (mob.getxPos() + mob.getSize() * Math.cos((angle)));
            int newY = (int) (mob.getyPos() + mob.getSize() * Math.sin((angle)));
            Point p = new Point(newX, newY);
            // System.out.println(p);
            pointList[i] = p;

        }
        return pointList;
    }

    // only for testing, still need fixing
    public void afterCollision(Mob m1, Mob m2) {
        if (calDistance(m1, m2) <= m1.getSize() + m2.getSize()) {
            if (m1.getSize() > m2.getSize()) {
                m2.setXVelocity(0);
                m2.setYVelocity(0);

                m2.isAlive = false;
                //removeMob(m2);
                System.out.println("first");
                System.out.println(m1.getImg() + "with size " + m1.getSize());
                System.out.println("eats" + m2.getImg() + " with size " + m2.getSize());
            } else if (m1.getSize() < m2.getSize()) {
                // removeMob(m1);
                m1.isAlive = false;
                System.out.println(m2.getImg() + " with size " + m2.getSize());
                System.out.println("eats " + m1.getImg() + " with size " + m1.getSize());

                m1.setXVelocity(0);
                m1.setYVelocity(0);
            } else {
                flee(m1, m2, 0.4);
                flee(m2, m1, 0.4);

            }
        }
    }

    public static double calDistance(Mob m1, Mob m2) {
        double difX = m1.getxPos() - m2.getxPos();
        double difY = m1.getyPos() - m2.getyPos();
        return Math.sqrt((difX * difX) + (difY * difY));

    }

    public void removeMob(Mob m) {
        this.mobList.remove(m);
        System.out.println("number of mob " + this.mobList.size());
    }

    public boolean checkOverLap(Mob m1, Mob m2) {

        return Math.hypot(m1.getxPos() - m2.getxPos(), m1.getyPos() - m2.getyPos()) < m1.getSize() + m2.getSize();

    }

    //TODO  if mob hits wall, it should find a new direction to flee
    public void flee(Mob m, Mob enemy, double speed) {
        double distance = calDistance(m, enemy);
        double tx = m.xPos, ty = m.yPos, sx = enemy.xPos, sy = enemy.yPos;

        double deltaX = tx - sx;
        double deltaY = ty - sy;
        double angle = Math.atan2(deltaY, deltaX);

        m.setXVelocity(speed * Math.cos(angle));
        m.setYVelocity(speed * Math.sin(angle));

        //currentX += speed * Math.cos( angle );
        //currentY += speed * Math.sin( angle );


    }


    public static double calDistance2(double x1, double y1, double x2, double y2) {
        double difX = x1 - x2;
        double difY = y1 - y2;
        return Math.sqrt((difX * difX) + (difY * difY));

    }


///////////////////////////////////


    // Identifies the Players from the pool of mobs in the moblist
    private void GetPlayersFromMoblist(JParser jparser) {
        for (Mob m : mobList) {
            if (m instanceof Player) {
                if (player1 == null) player1 = (Player) m;
                else if (player2 == null) player2 = (Player) m;
            }
        }
    }

    //TODO: Needs more rules, to include "player big" for example
    public ComMob GetClosesMobToPoint(String kind, double x, double y) {
        if (mobList.size() <= 0) return null;

        double curX = Double.MAX_VALUE;
        double curY = Double.MAX_VALUE;

        ComMob currentMob = null;

        for (Mob m : mobList) {
            if (!m.kind.equals(kind)) continue;

            if (Math.abs(m.xPos - x) < curX && Math.abs(m.yPos - y) < curY) {
                curX = Math.abs(m.xPos - x);
                curY = Math.abs(m.yPos - y);
                currentMob = (ComMob) m;
            }
        }

        return currentMob;
    }


    public void Update() {
        if (!map.getReady()) return;

        System.out.println("-----------------------------------------");
        System.out.println(gameState.toString());
        System.out.println("TICK " + tick);

        UpdatePhysics();
        //map.CheckCollisionWithWalls(mobList);
        CheckCollisionWithWalls(mobList);

        CheckCollision();

        CheckWinLose();

        tick++;
    }

    public void CheckCollisionWithWalls(ArrayList<Mob> mobList) {


    }

    private void CheckWinLose() {
        // Checks if the inverse is not true for ease of use
        boolean allPlayersDead = true;

        if (player1 != null && player1.isAlive) allPlayersDead = false;
        if (player2 != null && player2.isAlive) allPlayersDead = false;

        if (allPlayersDead) {
            gameState = GameState.Lose;
            return;
        }

        // If one or both Players are the last remaining mobs in the list, you win
        if (mobList.size() == 1 && mobList.get(0) instanceof Player) gameState = GameState.Win;
        if (mobList.size() == 2 && mobList.get(0) instanceof Player && mobList.get(1) instanceof Player)
            gameState = GameState.Win;
    }

    private void UpdatePhysics() {
        for (Mob m : mobList) {
            System.out.println(m.toString());
            m.UpdateVelocity(this, acceleration);
            m.ModifyVelocityByFactor(friction);
            m.Move();
        }
    }

    //Checks if two mobs collide
    private void CheckCollision() {
        for (Mob m1 : mobList) {
            for (Mob m2 : mobList) {
                if (m1 == m2) continue;

                if (m1.CollidesWithPoint(m2.xPos, m2.yPos)) CollideWith(m1, m2);
            }
        }
    }

    // Says what happens when the two mobs DO collide: Repelling and Eating
    private void CollideWith(Mob mob1, Mob mob2) {
        mob1.CollidedWithPoint(breakCoefficient, repelCoefficient, mob2.xPos, mob2.yPos);

        //Checks if one mobs eats the other
        if (mob1.CanEat(mob2)) {
            mob1.Eat(this, mob2);
        }
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public ArrayList<Mob> getMobList() {
        return mobList;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public int getTick() {
        return tick;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public CollisionMap getMap() {
        return map;
    }

    public static double getFriction() {
        return friction;
    }

    public static double getAcceleration() {
        return acceleration;
    }

    public static double getBreakCoefficient() {
        return breakCoefficient;
    }

    public static double getRepelCoefficient() {
        return repelCoefficient;
    }

}