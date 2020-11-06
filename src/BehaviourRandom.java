public class BehaviourRandom extends Behaviour
{
    private final int TickUpdateInterval = 60;

    private int tickCount;

    private double lastRandX, lastRandY;

    @Override
    public void ModifyMobVelocity(Model model, Mob mob, double acceleration)
    {
        if (tickCount >= TickUpdateInterval)
        {
            tickCount = 0;

            lastRandX = Math.random() % 2 - 1;
            lastRandY = Math.random() % 2 - 1;
        }
        else tickCount++;

        mob.setXVelocity(mob.getxVelocity() + lastRandX * acceleration);
        mob.setYVelocity(mob.getyVelocity() + lastRandY * acceleration);
    }

    @Override
    public boolean IsValid(Model model, Mob mob)
    {
        return true;
    }
}
