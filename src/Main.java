import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception {

        JParser jParser = new JParser();
      
        //Give model data from JParser and View
        Model model = new Model(jParser);
        View view = new View(model,primaryStage); 
        Controller controller = new Controller(model, view);

        primaryStage.setScene(view.getScene());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    /*public static void main(String[] args)
    {
        JParser jParser = new JParser();
        System.out.println(jParser.getTest());
    }*/
}
