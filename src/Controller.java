import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.util.BitSet;
import java.util.Timer;
import java.util.TimerTask;


public class Controller {


    private BitSet keyboardBitSet = new BitSet();
    private Timer timer;
    public Mob player;

    private KeyCode player1UpKey = KeyCode.UP;
    private KeyCode player1DownKey = KeyCode.DOWN;
    private KeyCode player1LeftKey = KeyCode.LEFT;
    private KeyCode player1RightKey = KeyCode.RIGHT;

    private KeyCode player2UpKey = KeyCode.W;
    private KeyCode player2DownKey = KeyCode.S;
    private KeyCode player2LeftKey = KeyCode.A;
    private KeyCode player2RightKey = KeyCode.D;

    private Model model;
    private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;

        this.addListeners();

        TimerTask update = new TimerTask() {
            @Override
            public void run() {
            if (model.getPlayer1() != null)
            {
                if (player1MovesUp()) {
                    model.getPlayer1().setUp(true);
                    model.getPlayer1().setDown(false);
               /*double yVelocity = model.getPlayer1().getyVelocity();
               double moreVelocity = yVelocity + 0.1;
               double newVelocity = Math.min(moreVelocity, 5);  // velocity not more than 5
               model.getPlayer1().setYVelocity(newVelocity);*/
                }
                else if (player1MovesDown()) {
                    model.getPlayer1().setUp(false);
                    model.getPlayer1().setDown(true);
                /*double yVelocity = model.getPlayer1().getyVelocity();
                double moreVelocity = yVelocity - 0.1;
                double newVelocity = Math.max(moreVelocity, -5);  // velocity not less than -5
                model.getPlayer1().setYVelocity(newVelocity);*/
                }
                else
                {
                    model.getPlayer1().setUp(false);
                    model.getPlayer1().setDown(false);
                }

                if (player1MovesRight()) {
                    model.getPlayer1().setRight(true);
                    model.getPlayer1().setLeft(false);
                /*double xVelocity = model.getPlayer1().getxVelocity();
                double moreVelocity = xVelocity + 0.1;
                double newVelocity = Math.min(moreVelocity, 5);  // velocity not more than 5
                model.getPlayer1().setXVelocity(newVelocity);*/
                }
                else if (player1MovesLeft()) {
                    model.getPlayer1().setRight(false);
                    model.getPlayer1().setLeft(true);
                /*double xVelocity = model.getPlayer1().getxVelocity();
                double moreVelocity = xVelocity - 0.1;
                double newVelocity = Math.max(moreVelocity, -5);  // velocity not less than -5
                model.getPlayer1().setXVelocity(newVelocity);*/
                }
                else
                {
                    model.getPlayer1().setRight(false);
                    model.getPlayer1().setLeft(false);
                }
            }
            if (model.getPlayer2() != null)
            {
                if (player2MovesUp()) {
                    model.getPlayer2().setUp(true);
                    model.getPlayer2().setDown(false);
                /*double yVelocity = model.getPlayer2().getyVelocity();
                double moreVelocity = yVelocity + 0.1;
                double newVelocity = Math.min(moreVelocity, 5);  // velocity not more than 5
                model.getPlayer2().setYVelocity(newVelocity);*/
                }
                else if (player2MovesDown()) {
                    model.getPlayer2().setUp(false);
                    model.getPlayer2().setDown(true);
                /*double yVelocity = model.getPlayer2().getyVelocity();
                double moreVelocity = yVelocity - 0.1;
                double newVelocity = Math.max(moreVelocity, -5);  // velocity not less than -5
                model.getPlayer2().setYVelocity(newVelocity);*/
                }
                else
                {
                    model.getPlayer2().setUp(false);
                    model.getPlayer2().setDown(false);
                }

                if (player2MovesRight()) {
                    model.getPlayer2().setRight(true);
                    model.getPlayer2().setLeft(false);
                /*double xVelocity = model.getPlayer2().getxVelocity();
                double moreVelocity = xVelocity + 0.1;
                double newVelocity = Math.min(moreVelocity, 5);  // velocity not more than 5
                model.getPlayer2().setXVelocity(newVelocity);*/
                }
                else if (player2MovesLeft()) {
                    model.getPlayer2().setRight(false);
                    model.getPlayer2().setLeft(true);
                /*double xVelocity = model.getPlayer2().getxVelocity();
                double moreVelocity = xVelocity - 0.1;
                double newVelocity = Math.max(moreVelocity, -5);  // velocity not less than -5
                model.getPlayer2().setXVelocity(newVelocity);*/
                }
                else
                {
                    model.getPlayer2().setRight(false);
                    model.getPlayer2().setLeft(false);
                }
            }

            }
        };

        timer = new Timer();
        timer.scheduleAtFixedRate(update, 0, (1000 / 50));  // TODO change this value
    }

    public void addListeners() {
        Scene scene = this.view.getScene();
        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyPressedEventHandler);
        scene.addEventFilter(KeyEvent.KEY_RELEASED, keyReleasedEventHandler);

    }


    // for game over?
    public void removeListeners() {
        Scene scene = this.view.getScene();
        scene.removeEventFilter(KeyEvent.KEY_PRESSED, keyPressedEventHandler);
        scene.removeEventFilter(KeyEvent.KEY_RELEASED, keyReleasedEventHandler);

    }


    private EventHandler<KeyEvent> keyPressedEventHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            // register key down
            keyboardBitSet.set(event.getCode().ordinal(), true);
        }
    };


    private EventHandler<KeyEvent> keyReleasedEventHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {

            // register key up
            keyboardBitSet.set(event.getCode().ordinal(), false);

        }
    };


    public boolean player1MovesUp() {
        return keyboardBitSet.get(player1UpKey.ordinal());
    }

    public boolean player1MovesDown() {
        return keyboardBitSet.get(player1DownKey.ordinal());
    }

    public boolean player1MovesLeft() {
        return keyboardBitSet.get(player1LeftKey.ordinal());
    }

    public boolean player1MovesRight() {
        return keyboardBitSet.get(player1RightKey.ordinal());
    }
    
    
    public boolean player2MovesUp() {
        return keyboardBitSet.get(player2UpKey.ordinal());
    }

    public boolean player2MovesDown() {
        return keyboardBitSet.get(player2DownKey.ordinal());
    }

    public boolean player2MovesLeft() {
        return keyboardBitSet.get(player2LeftKey.ordinal());
    }

    public boolean player2MovesRight() {
        return keyboardBitSet.get(player2RightKey.ordinal());
    }

}