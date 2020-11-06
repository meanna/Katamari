public class Point2 {

    double x;
    double y;


    public Point2(double x, double y){
        this.x = x;
        this.y = y;

    }
    public Point2(){

    }


    public void setX(double x){
        this.x = x;
    }
    public void setY(double y){
        this.y = y;
    }

    public static double calSlope(Point2 p1,Point2 p2){
        return (p2.y-p1.y ) / (p2.x - p1.x);
    }


    public  static double calDistance(double x1, double y1, double x2, double y2) {
        double difX = x1 - x2;
        double difY = y1 -y2;
        return Math.sqrt((difX * difX) + (difY * difY));

    }

    public static  double calDistancePoint(Point2 p1,Point2 p2) {
        double difX = p1.x - p2.x;
        double difY = p1.y -p2.y;
        return Math.sqrt((difX * difX) + (difY * difY));

    }
    public static void printPoints(Point2 source,Point2 goal, double l, double m){
        Point2 a, b;
        a = new Point2();
        b = new Point2();

        // slope is 0
        if(m==0){

            a.x = source.x + l;
            a.y = source.y;

            b.x = source.x - l;
            b.y = source.y;

        }  else if (m ==  Double.POSITIVE_INFINITY ||m ==  Double.NEGATIVE_INFINITY) {
            a.x = source.x;
            a.y = source.y + l;

            b.x = source.x;
            b.y = source.y - l;
        } else {
            double dx = (l / Math.sqrt(1 + (m * m)));
            double dy = m * dx;
            a.x = source.x + dx;
            a.y = source.y + dy;
            b.x = source.x - dx;
            b.y = source.y - dy;
        }



        if(calDistancePoint(goal, a)<calDistancePoint(goal, b)){
            System.out.println(a.x +" ,"+a.y);
        }else{
            System.out.println(b.x +" , "+b.y);

        }

        //

    }

    public static Point2 findPointOnLine(Point2 source,Point2 goal, double l, double m){
        Point2 a, b;
        a = new Point2();
        b = new Point2();

        // slope is 0
        if(m==0){

            a.x = source.x + l;
            a.y = source.y;

            b.x = source.x - l;
            b.y = source.y;

        }  else if (m ==  Double.POSITIVE_INFINITY ||m ==  Double.NEGATIVE_INFINITY) {
            a.x = source.x;
            a.y = source.y + l;

            b.x = source.x;
            b.y = source.y - l;
        } else {
            double dx = (l / Math.sqrt(1 + (m * m)));
            double dy = m * dx;
            a.x = source.x + dx;
            a.y = source.y + dy;
            b.x = source.x - dx;
            b.y = source.y - dy;
        }



        if(calDistancePoint(goal, a)<calDistancePoint(goal, b)){
            return a;
        }else{
            return b;

        }

        //

    }

    public static Point2[] createPoints(int num, Point2 p, double mobsize) {
        Point2[] pointList = new Point2[num];

        double slice = 2 * Math.PI / num;
        for (int i = 0; i < num; i++) {
            double angle = slice * i;
            double newX = (p.x + mobsize * Math.cos((angle)));
            double newY = (p.y + mobsize * Math.sin((angle)));
            Point2 newP = new Point2();
            newP.setX(newX);
            newP.setY(newY);
            // System.out.println(p);
            pointList[i] = newP;

        }
        return pointList;
    }

}
