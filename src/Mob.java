/**
 *
 */
public abstract class Mob
{

    protected double mass;
    protected double size;
    protected double xPos;
    protected double yPos;
    protected double xVelocity;
    protected double yVelocity;
    private String img;
    protected  String kind;
    protected  boolean isAlive;

    public abstract boolean getHungry();

    /*Regular Mob Constructor*/
    public Mob(String kind, double size, String img)
    {
        setKind(kind);
        setSize(size);
        setImg(img);
        isAlive = true;
    }

    /*Constructor for Mob Kind List created in JParser*/
    public Mob(String kind, double xPos, double yPos, double size, String img)
    {
        setxPos(xPos);
        setyPos(yPos);
        setKind(kind);
        setSize(size);
        setImg(img);
        setSize(size);
        isAlive = true;
    }

    /**
     * Returns true if this.mob can eat mob
     * @param mob: the mob to check against
     */
    public boolean CanEat(Mob mob)
    {
        if(!getHungry()) return false; //Ensures at that this.mob is hungry
        double sizePercent = this.getSize() / mob.getSize();
        if(sizePercent < 1.05) return false; // Ensures that this.mob is at least 5% bigger than the Mob given in Eat()
        return true;

    }

    // Removes eaten mob from the MobList, called in Model
    public void Eat(Model model, Mob mob)
    {
        if(!CanEat(mob)) return;
        mob.isAlive = false;
        model.getMobList().remove(mob);
    }

    // Used in Model for both friction and slowdown by the given factor
    public void ModifyVelocityByFactor(double factor)
    {
        setXVelocity(xVelocity * factor);
        setYVelocity(yVelocity * factor);
    }

    // Changes x and y Position based on Velocity
    public void Move()
    {
        setxPos(xPos + xVelocity);
        setyPos(yPos + yVelocity);
    }

    @Override
    public String toString()
    {
        return "Kind: " + kind + ", Pos: (" + xPos + "|" + yPos + ")" + ", Vel: (" + xVelocity + "|" + yVelocity + ")" + "Size: " + size;
    }

    public abstract void UpdateVelocity(Model model, double acceleration);

    //Checks Collision with a given Point
    public boolean CollidesWithPoint(double xPos, double yPos)
    {
        double x = this.xPos - xPos;
        double y = this.yPos - yPos;

        double xx = x * x;
        double yy = y * y;

        double radSqr = size * size;

        return xx + yy < radSqr;
    }

    //After Collision
    public void CollidedWithPoint(double breakCoefficient, double repelCoefficient, double xPos, double yPos)
    {
        ModifyVelocityByFactor(breakCoefficient);

        // Vector calculation; end - start
        double xDir = this.xPos - xPos;
        double yDir = this.yPos - yPos;
        double length = xDir * xDir + yDir * yDir; // immer positive, keine negative

        if (length == 0) //Only happens if the positions are exactly the same, move mob up to get them "unstuck"
        {
            yPos++;
            return;
        }

        //Normalises the Vector; else the repelVelocity would be influenced by the length of the Vector
        double xNorm = xDir / length;
        double yNorm = yDir / length;

        setXVelocity(getxVelocity() + xNorm * repelCoefficient);
        setYVelocity(getyVelocity() + yNorm * repelCoefficient);
    }

    /***Getter and Setter***/
    public double getMass()
    {
        return mass;
    }

    public void setMass(double mass)
    {
        this.mass = mass;
        this.size = Math.pow(mass, 1/3.0);
    }

    public double getSize()
    {
        return size;
    }

    public void setSize(double size)
    {
        this.size = size;
        this.mass = Math.pow(size, 3);
    }

    public double getxPos()
    {
        return xPos;
    }

    public void setxPos(double xPos)
    {
        this.xPos = xPos;
    }

    public double getyPos()
    {
        return yPos;
    }

    public void setyPos(double yPos)
    {
        this.yPos = yPos;
    }

    public boolean getIsAlive() { return isAlive; }

    public String getImg()
    {
        return img;
    }

    public void setImg(String img)
    {
        this.img = img;
    }

    public String getKind()
    {
        return kind;
    }

    public void setKind(String kind)
    {
        this.kind = kind;
    }

    public void setXVelocity(double xVelocity)
    {
        this.xVelocity = xVelocity;
    }

    public void setYVelocity(double yVelocity)
    {
        this.yVelocity = yVelocity;
    }

    public double getxVelocity() {
        return xVelocity;
    }

    public double getyVelocity() {
        return yVelocity;
    }
}