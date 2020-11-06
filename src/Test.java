
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
public class Test extends Application {

    public Pane canvas;
    public CollisionMap map;
    int scale;
    Model model;


    @Override
    public void start(Stage stage) {
        //JParser jParser = new JParser();

        //Give model data from JParser and View
      //  Model model = new Model(jParser);

        this.scale = 1;

        map = new CollisionMap("/home/oem/IdeaProjects/dev2/img/single", scale);
        // map.printMap();

//create Mobs here
        Mob a = new Player("dd", 600, 300, 25.0, "a");
        Mob b = new ComMob("Bug", 100, 400.0, 10.0, "b", true);
        Mob c = new ComMob("Bug", 600, 400.0, 10.0, "c", true);
        Mob d = new ComMob("alice", 400.0, 250.0, 15.0, "d", true);

        setMobSpeed(a,0, 0);
        setMobSpeed(b, 2, 3);
        setMobSpeed(c,4, -2);
        setMobSpeed(d,3, -2);
        //d.randomMove(2);
        //b.randomMove(3);
        //d.randomMove(2);
        //b.setVelocity(0, 2);
        //c.setVelocity(2, 0);
        //d.setVelocity(-3, 2);

       a.setXVelocity(7);
       a.setYVelocity(7);

        ArrayList<Mob> mobList = new ArrayList<Mob>();


       // mobList.add(d);


        this.model = new Model();

        BehaviourCustom behave1 = new BehaviourCustom(model, "flee", "Bigger Player", 100);
        BehaviourCustom behave2 = new BehaviourCustom(model, "chase", "Bug", 50);

        ((ComMob) d).addBehaviour(behave1);
        ((ComMob) d).addBehaviour(behave2);
        ((ComMob) b).addBehaviour(behave1);
        ((ComMob) b).addBehaviour(behave2);
        ((ComMob) c).addBehaviour(behave1);
        ((ComMob) c).addBehaviour(behave2);


       // mobList.add(a);
        mobList.add(b);
        mobList.add(c);
        mobList.add(d);

        model.setMobList(mobList);


        model.setMap(map);


// can not work now
        /**
         Behaviour behave1 = new BehaviourCustom( "flee","Player Big",  100);
         Behaviour behave2 = new BehaviourCustom( "chase","bug",  100);
         ((ComMob) d).addBehaviour(behave1);
         ((ComMob) d).addBehaviour(behave2);
         **/



        // View
        Image image =new  Image("file:/home/oem/IdeaProjects/dev2/img/single.png");
        ImageView iv = new ImageView();
        iv.setImage(image);
        iv.setFitHeight(map.getMapHeight());
        iv.setFitWidth(map.getMapWidth());
        canvas = new Pane();
        Scene scene = new Scene(canvas, map.getMapWidth(), map.getMapHeight(), Color.ALICEBLUE);
        scene.setOnKeyPressed(keyPressed);
        scene.setOnKeyReleased(keyReleased);
        canvas.getChildren().add(iv);


        ArrayList<Circle> ballList = new ArrayList<Circle>();
        model.Update2();

        for (int i = 0; i < model.getMobList().size(); i++) {
            Circle ball = new Circle(model.getMobList().get(i).getSize(), Color.CADETBLUE);
            ball.relocate(model.getMobList().get(i).getxPos(), model.getMobList().get(i).getyPos());
            ballList.add(ball);
            canvas.getChildren().add(ball);

        }



        stage.setTitle("Animated Ball");
        stage.setScene(scene);
        stage.show();


        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20),
                new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent t) {

                        model.Update2();


                        for (int i = 0; i < model.getMobList().size(); i++) {
                            ballList.get(i).setLayoutX(model.getMobList().get(i).getxPos());
                            ballList.get(i).setLayoutY(model.getMobList().get(i).getyPos());

                        }


                    }


                }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        //timeline.setCycleCount(10);
        timeline.play();



    }

    private EventHandler<KeyEvent> keyPressed = new EventHandler<KeyEvent>() {

        @Override
        public void handle(KeyEvent event) {
            // start movement according to key pressed
            switch (event.getCode()) {
                case RIGHT:
                    model.getMobList().get(0).setXVelocity(1* Model.getAcceleration());
                    break;
                case LEFT:
                    model.getMobList().get(0).setXVelocity(-1* Model.getAcceleration());
                    break;
                case UP:
                    model.getMobList().get(0).setYVelocity(-1* Model.getAcceleration());
                    break;
                case DOWN:
                    model.getMobList().get(0).setYVelocity(1* Model.getAcceleration());
                    break;
            }

        }
    };
    private EventHandler<KeyEvent> keyReleased = new EventHandler<KeyEvent>() {

        @Override
        public void handle(KeyEvent event) {
            // set movement to 0, if the released key was responsible for the paddle
            switch (event.getCode()) {
                case RIGHT:
                    model.getMobList().get(0).setXVelocity(0);
                    break;

                case LEFT:
                    model.getMobList().get(0).setXVelocity(0);
                    break;
                case UP:
                    model.getMobList().get(0).setYVelocity(0);
                    break;
                case DOWN:
                    //rightPaddleDY = 0;
                    model.getMobList().get(0).setYVelocity(0);
                    break;
            }
        }

    };


public void setMobSpeed(Mob m, double dx, double dy){
    m.setXVelocity(dx);
    m.setYVelocity(dy);
}


    public static void main(String[] args) {
        launch();

    }


}

