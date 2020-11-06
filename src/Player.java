public class Player extends Mob
{

    private String control;

    // for the input direction in Controller
    private boolean up, down, left, right;

    public Player(String kind, double size, String img)
    {
        super(kind, size, img);
    }

    public Player(String kind, double xPos, double yPos, double size, String img)
    {
        super(kind, xPos, yPos, size, img);
    }

    @Override
    public boolean getHungry()
    {
        return true;
    } //Players are always hungry

    public void setControl(String control)
    {
        this.control = control;
    }

    @Override
    public void UpdateVelocity(Model model, double acceleration)
    {
        //Check input for movement direction, multiply direction with acceleration, (direction* acceleartion)+= Velocity

        double x = 0;
        double y = 0;

        if (up) y = -1;
        else if (down) y = 1;
        else y = 0;

        if (left) x = -1;
        else if (right) x = 1;
        else x = 0;

        xVelocity += x * acceleration;
        yVelocity += y * acceleration;
    }

    @Override
    public void Eat(Model model, Mob mob)
    {
        if (!CanEat(mob)) return;

        //Increase size and mass
        this.setMass(this.mass  += mob.getMass());

        super.Eat(model, mob);
    }

    public String getControl()
    {
        return control;
    }

    public void setUp(boolean state) { up = state; }
    public void setDown(boolean state) { down = state; }
    public void setLeft(boolean state) { left = state; }
    public void setRight(boolean state) { right = state; }
}
