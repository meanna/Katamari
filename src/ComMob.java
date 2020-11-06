import java.util.ArrayList;

public class ComMob extends Mob
{

    private boolean hungry;
    private Behaviour defaultBehave;
    private String kind;
    private  ArrayList<BehaviourCustom> behaviours;

    //private ArrayList<Behaviour> behaviours;

    public ComMob(String kind, double size, String img, boolean hungry)
    {
        super(kind, size, img);
        this.hungry = hungry;

        //behaviours = new ArrayList<Behaviour>();
        this.behaviours = new ArrayList<BehaviourCustom>();
    }

    public ComMob(String kind, double xPos, double yPos, double size, String img, boolean hungry)
    {
        super(kind, xPos, yPos, size, img);
        this.kind = kind;
        this.hungry = hungry;
        this.setMass(Math.pow(size, 3));

       // behaviours = new ArrayList<Behaviour>();
        this.behaviours = new ArrayList<BehaviourCustom>();
    }


    @Override
    public void Move(){
        //System.out.println("behave applied");
        //moveWithBehaviour();
        setxPos(xPos + xVelocity);
        setyPos(yPos + yVelocity);
        //System.out.println(getxPos());
        //System.out.println(getyPos());

    }

    public void moveWithBehaviour() {

        boolean valid = false;

        loop: for (BehaviourCustom b : behaviours) {
            for (Mob m2 : b.getModel().getMobList()) {
                double m2Dist = b.getModel().calDistance(this, m2);

                if(this!=m2){
                    //System.out.println(m2.getKind());
                    //System.out.println(m2Dist +" : "+ b.getDistance());
                    // System.out.println(b.IsEntity(this, m2));
                    if ( m2Dist < (b.getDistance()+m2.getSize() + this.getSize() )&& b.IsEntity(this, m2)) {
                        // System.out.println(m2.getKind()+ " is " +(int)m2Dist+ " away");
                        //System.out.println(m2.getKind()+" is entity");
                        //System.out.println(m2Dist);
                        //System.out.println(this.getKind()+"   "+b.getAction()+" "+m2.getKind());
                        b.applyAction(this, m2);
                        valid = true;
                        break loop;


                    }
                }


            }
        }  //this.randomMove(3);



    }












    //public void addBehaviour(Behaviour behaviour){behaviours.add(behaviour);}

    public void addBehaviour(BehaviourCustom behaviour) {
        behaviours.add(behaviour);
    }

    @Override
    public void UpdateVelocity(Model model, double acceleration)
    {
        //Check AI for movement direction, multiply direction with acceleration, (direction* acceleration)+= Velocity

        for (Behaviour b : behaviours)
        {
            if (b.IsValid(model, this))
            {
                b.ModifyMobVelocity(model, this, acceleration);
                return;
            }
        }

        if (defaultBehave.IsValid(model, this)) defaultBehave.ModifyMobVelocity(model, this, acceleration);
    }

    @Override
    public boolean getHungry()
    {
        return hungry;
    }

    public Behaviour getDefaultBehave()
    {
        return defaultBehave;
    }

    public void setDefaultBehave(Behaviour defaultBehave)
    {
        this.defaultBehave = defaultBehave;
    }

    public void setHungry(boolean hungry)
    {
        this.hungry = hungry;
    }


}
