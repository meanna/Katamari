
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.SplitPane;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;


public class View  {


    private Scene scene;
    private SplitPane sp=new SplitPane();
    private Pane p1;
    private Pane p2;


    private  int tilesize;

    private ArrayList<Tile> tiles= new ArrayList<Tile>();
    private ArrayList<Mob> mobs=new ArrayList<>();


    private double numTilesHoriz;
    private double numTilesVert;
    private double width;
    private double height;

    public View(Model model,Stage stage) {




        JParser jparser=new JParser();
        tiles=jparser.getTileList();
        tilesize=jparser.getTilesize();


        numTilesHoriz=getTileMaxX(tiles)+1;
        numTilesVert=getTileMaxY(tiles)+1;
        width=numTilesHoriz*tilesize;
        height=numTilesVert*tilesize;

        mobs=model.getMobList();


        sp.setPrefWidth(1400);
        sp.setPrefHeight(1400);


        p1=createBackground();
        p1.setMinWidth(700);
        p1.setMinHeight(1400);


        p2=createBackground();
        p2.setMinWidth(700);
        p2.setMinHeight(1400);

        sp.getItems().addAll(p1,p2);
        scene=new Scene(new BorderPane(sp),1400,1400);




        for (Mob m:mobs) {
            Image image = new Image(m.getImg()+".png", m.getSize()*2,
                    m.getSize()*2, false, false);
            ImageView iv = new ImageView(image);

            iv.setX(m.getxPos());
            iv.setY(m.getyPos());
            p1.getChildren().addAll(iv);

        }



        for (Mob m:mobs) {
            Image image = new Image(m.getImg()+".png", m.getSize()*2,
                    m.getSize()*2, false, false);
            ImageView iv = new ImageView(image);

            iv.setX(m.getxPos());
            iv.setY(m.getyPos());
            p2.getChildren().addAll(iv);

        }



        Image ImgPlayer1=new Image(model.getPlayer1().getImg()+".png",
                model.getPlayer1().getSize()*2,model.getPlayer1().getSize()*2,
                false,false);
        ImageView iv_player1=new ImageView(ImgPlayer1);
        iv_player1.setX(model.getPlayer1().getxPos());
        iv_player1.setY(model.getPlayer1().getyPos());



        Image ImgPlayer2=new Image(model.getPlayer2().getImg()+".png",
                model.getPlayer2().getSize()*2,model.getPlayer2().getSize()*2,
                false,false);
        ImageView iv_player2=new ImageView(ImgPlayer2);
        iv_player2.setX(model.getPlayer2().getxPos());
        iv_player2.setY(model.getPlayer2().getyPos());



        Rectangle clip1 = new Rectangle();

        clip1.widthProperty().bind(scene.widthProperty());
        clip1.heightProperty().bind(scene.heightProperty());

        clip1.xProperty().bind(Bindings.createDoubleBinding(
                () -> clampRange(iv_player1.getX() - scene.getWidth() / 4,
                        0, p1.getWidth()*2 - scene.getWidth()/2),
                iv_player1.xProperty(), scene.widthProperty()));
        clip1.yProperty().bind(Bindings.createDoubleBinding(
                () -> clampRange(iv_player1.getY() - scene.getHeight() / 2,
                        0, p1.getHeight() - scene.getHeight()),
                iv_player1.yProperty(), scene.heightProperty()));

        p1.setClip(clip1);
        p1.translateXProperty().bind(clip1.xProperty().multiply(-1));
        p1.translateYProperty().bind(clip1.yProperty().multiply(-1));



        Rectangle clip2 = new Rectangle();

        clip2.widthProperty().bind(scene.widthProperty());
        clip2.heightProperty().bind(scene.heightProperty());

        clip2.xProperty().bind(Bindings.createDoubleBinding(
                () -> clampRange(iv_player2.getX() - scene.getWidth() / 4,
                        0, p2.getWidth()*2 - scene.getWidth()/2),
                iv_player2.xProperty(), scene.widthProperty()));
        clip2.yProperty().bind(Bindings.createDoubleBinding(
                () -> clampRange(iv_player2.getY() - scene.getHeight() / 2,
                        0, p2.getHeight() - scene.getHeight()),
                iv_player2.yProperty(), scene.heightProperty()));

        p2.setClip(clip2);
        p2.translateXProperty().bind(clip2.xProperty().multiply(-1));
        p2.translateYProperty().bind(clip2.yProperty().multiply(-1));



        Timeline t = new Timeline(new KeyFrame(Duration.millis(1000 / (double)20),
                (x) -> {
                    model.Update();
                    update();



                    iv_player1.setX(model.getPlayer1().getxPos());
                    iv_player1.setY(model.getPlayer1().getyPos());

                    iv_player2.setX(model.getPlayer2().getxPos());
                    iv_player2.setY(model.getPlayer2().getyPos());



                    if(model.getGameState()== Model.GameState.Win && model.getPlayer1().isAlive
                    &&! model.getPlayer2().isAlive) {
                        Text text1 = writeText("Win");
                        text1.setX(iv_player1.getX());
                        text1.setY(iv_player1.getY());
                        p1.getChildren().add(text1);
                    }


               if(model.getGameState()== Model.GameState.Win && model.getPlayer2().isAlive
               && ! model.getPlayer1().isAlive) {
              Text text2=writeText("Win");
                        text2.setX(iv_player2.getX());
                        text2.setY(iv_player2.getY());
                        p2.getChildren().add(text2);
                    }


                    if(model.getGameState()== Model.GameState.Lose) {
                        Text text=writeText("Lose");
                        text.setX(350);
                        text.setY(700);
                        p1.getChildren().add(text);
                        p2.getChildren().add(text);
                    }


                }



                ));
        t.setCycleCount(Animation.INDEFINITE);
        t.play();

        }




    public void update() {

        p1.getChildren().clear();
            for(Tile tile:tiles) {
                double x=tile.getX();
                double y=tile.getY();
                Image img = new Image(tile.getName()+".png");
                ImageView iv_img = new ImageView(img);
                p1.getChildren().addAll(iv_img);
                iv_img.relocate(x*tilesize, y*tilesize);
            }



            for (Mob m : mobs) {
                Image image = new Image(m.getImg() + ".png", m.getSize() * 2,
                        m.getSize() * 2, false, false);
                ImageView iv = new ImageView(image);

            iv.setX(m.getxPos());
            iv.setY(m.getyPos());
            p1.getChildren().addAll(iv);


        }



        p2.getChildren().clear();
        for(Tile tile:tiles) {
            double x=tile.getX();
            double y=tile.getY();
            Image img = new Image(tile.getName()+".png");
            ImageView iv_img = new ImageView(img);
            p2.getChildren().addAll(iv_img);
            iv_img.relocate(x*tilesize, y*tilesize);
        }


        for (Mob m : mobs) {
            Image image = new Image(m.getImg() + ".png", m.getSize() * 2,
                    m.getSize() * 2, false, false);
            ImageView iv = new ImageView(image);

            iv.setX(m.getxPos());
            iv.setY(m.getyPos());
            p2.getChildren().addAll(iv);


        }

    }



    public void show(Stage stage){
        stage.setTitle("Game");
        stage.setScene(scene);
        stage.show();
    }
    private double clampRange(double value, double min, double max) {
        if (value < min) return min ;
        if (value > max) return max ;
        return value ;
    }

    private Text writeText(String s) {
        Text text = new Text(s);
        text.setCache(true);
        text.setFill(Color.RED);
        text.setFont(Font.font(null, FontWeight.BOLD, 100));
        text.setEffect(new GaussianBlur());
        return(text);
    }



    private double getTileMaxX(ArrayList<Tile> tiles){
        double MaxX=0;
        for(Tile t:tiles){
            if (t.getX() > MaxX){
                MaxX = t.getX();}
        }
        return MaxX;
    }

    private double getTileMaxY(ArrayList<Tile> tiles){
        double MaxY=0;
        for(Tile t:tiles){
            if (t.getX() > MaxY){
                MaxY = t.getX();}
        }
        return MaxY;
    }


    private Pane createBackground() {
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Pane pane = new Pane(canvas);
        pane.setMinSize(width, height);
        pane.setPrefSize(width, height);
        pane.setMaxSize(width, height);
        for(Tile tile:tiles) {
            double x=tile.getX();
            double y=tile.getY();
            Image img = new Image(tile.getName()+".png");
            ImageView iv_img = new ImageView(img);
            pane.getChildren().addAll(iv_img);
            iv_img.relocate(x*tilesize, y*tilesize);
        }
        return pane ;


    }
    public Scene getScene() {
        return scene;
    }
}

