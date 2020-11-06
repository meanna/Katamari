public class BehaviourCustom extends Behaviour {

    private String action;
    private String entity;
    private int distance;



    private Model model;

    public BehaviourCustom(Model model, String action, String entity, int distance) {
        this.model = model;
        this.action = action;
        this.entity = entity;
        this.distance = distance;
    }
    public BehaviourCustom( String action, String entity, int distance) {

        this.action = action;
        this.entity = entity;
        this.distance = distance;
    }

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
/**
        Point2[] pointsAroundCircle = Point2.createPoints(20, new Point2(m.getxPos(), m.getyPos()), this.distance);

        for (Point2 p : pointsAroundCircle) {
            if (p.x <= 0 || p.x >= model.getMap().getMapWidth() || p.y <= 0 || p.y >= model.getMap().getMapHeight()) {
                continue;
            } else if (model.getMap().getValueAt(p.x, p.y) == false) {
                continue;
            } else {
                goToPoint(m.getxPos(), m.getyPos(), p.x, p.y, 10, m);
            }
        }
**/

    }

    public void goToPoint(double x1, double y1, double x2, double y2, double speed, Mob m) {

        double tx = x1, ty = y1, sx = x2, sy = y2;

        double deltaX = tx - sx;
        double deltaY = ty - sy;
        double angle = Math.atan2(deltaY, deltaX);

        m.setXVelocity(-speed * Math.cos(angle));
        m.setYVelocity(-speed * Math.sin(angle));


        //currentX += speed * Math.cos( angle );
        //currentY += speed * Math.sin( angle );


    }


    public void chase(Mob m, Mob smaller, double speed) {
        double distance = calDistance(m, smaller);
        double tx = m.xPos, ty = m.yPos, sx = smaller.xPos, sy = smaller.yPos;

        double deltaX = tx - sx;
        double deltaY = ty - sy;
        double angle = Math.atan2(deltaY, deltaX);

        m.setXVelocity(-speed * Math.cos(angle));
        m.setYVelocity(-speed * Math.sin(angle));

        //currentX += speed * Math.cos( angle );
        //currentY += speed * Math.sin( angle );


    }

    public double calDistance(Mob m1, Mob m2) {
        double difX = m1.getxPos() - m2.getxPos();
        double difY = m1.getyPos() - m2.getyPos();
        return Math.sqrt((difX * difX) + (difY * difY));

    }

    public void applyAction(Mob m1, Mob m2) {
        if (this.action == "flee") {
            flee(m1, m2, 4);
            System.out.println(m1.getKind() + "flee");
        } else if (this.action == "chase") {
            chase(m1, m2, 3);
        }

    }

    public boolean IsEntity(Mob m1, Mob m2) {
        boolean isentity = false;

        switch (this.entity) {
            case "Bigger Player":
                if (m1.getSize() < m2.getSize()) {
                    isentity = true;
                }
                break;

            case "Bug":
                if (m2.kind == "Bug")
                    isentity = true;
                break;


            default:
                isentity = false;

        }

        return isentity;

    }

    public Model getModel() {
        return model;
    }


    @Override
    public void ModifyMobVelocity(Model model, Mob mob, double acceleration) {

    }

    @Override
    public boolean IsValid(Model model, Mob mob) {
        return true;
    } //Needs to be changed

    public String getAction() {
        return action;
    }

    public String getEntity() {
        return entity;
    }

    public int getDistance() {
        return distance;
    }
}
