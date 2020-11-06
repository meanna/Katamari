public class BehaviourStand extends Behaviour
{
    @Override
    public void ModifyMobVelocity(Model model, Mob mob, double acceleration)
    {
        return; //Nothing to do
    }

    @Override
    public boolean IsValid(Model model, Mob mob)
    {
        return true;
    }
}
