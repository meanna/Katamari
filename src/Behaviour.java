public abstract class Behaviour
{

    public abstract boolean IsValid(Model model, Mob mob);
    public abstract void ModifyMobVelocity(Model model, Mob mob, double acceleration);

    public static Behaviour CreateFromString(String name)
    {
        switch (name)
        {
            case "random": return new BehaviourRandom();
            case "stand": return new BehaviourStand();
        }

        return null;
    }

}
