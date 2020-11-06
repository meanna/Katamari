public class Tile {

   private double x;
   private double y;
   private String name;

   public Tile(double xPos, double yPos, String name){

       this.x=xPos;
       this.y=yPos;
       this.name=name;

   }

   public void setX(double xPos){

       this.x=xPos;
   }

    public void setY(double yPos){

        this.y=yPos;
    }



    public double getX(){
       return this.x;

    }

    public double getY() {
        return this.y;


    }

    public void setName(String name) {

        this.name = name;
    }


        public String getName(){
            return this.name;

        }

    }





