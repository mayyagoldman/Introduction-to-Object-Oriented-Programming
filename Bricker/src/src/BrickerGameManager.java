package src;

import bricker.brick_strategies.CollisionStrategy;
import src.gameobjects.*;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;
import java.util.Random;

public class BrickerGameManager extends GameManager {

    private static final int DEFAULT_SCORE = 4 ;
    private static final float BALL_SPEED = 300 ;
    private static final float BRICK_2_BRICK_DIST = 1;
    private static final float BRICK_2_WALL_DIST = 5;
    private static final float BRICK_HEIGHT = 15 ;
    private static final int NUM_BRICK_ROWS = 5 ;
    private static final int NUM_BRICK_COLS = 8 ;
    public static final int BORDER_WIDTH = 10;
    private static final int BALL_RADIUS = 20;
    private static final float PADDLE_HEIGHT = 15;


    public static final String VICTORY_MSG = "YOU WIN!\n" ;
    public static final String LOSS_MSG = "YOU LOSE!\n" ;
    public static final String PLAY_AGAIN_MSG = "Play again?" ;

    public static final Color BORDER_COLOR = Color.BLACK;
    private static final float PADDLE_WIDTH = 100 ;
    private static final float LIFE_RADIUS = 10;
    private Ball ball;
    private Vector2 windowDimensions;
    private WindowController windowController;
    private int score;
    private GraphicLifeCounter[] lives ;
    private Counter brickCounter;
    private Counter livesCounter;
    public NumericLifeCounter life;

    public BrickerGameManager(String windowTitle , Vector2 windowDimensions) {super(windowTitle , windowDimensions);}


    private void generateBricks( Renderable brickImage)
    {
        float brickSizeX = (windowDimensions.x() - ((NUM_BRICK_COLS - 1 ) * BRICK_2_BRICK_DIST) - (2 * BORDER_WIDTH) - (2*BRICK_2_WALL_DIST) ) /NUM_BRICK_COLS;

        for (int i = 0 ; i < NUM_BRICK_ROWS ; i++)
        {
            for (int j = 0 ; j < NUM_BRICK_COLS ; j++)
            {
                CollisionStrategy collisionStrategy = new CollisionStrategy(gameObjects());
                GameObject brick = new Brick(Vector2.ZERO, new Vector2(brickSizeX,BRICK_HEIGHT) , brickImage ,collisionStrategy , brickCounter);
                brick.setCenter(new Vector2(BORDER_WIDTH + BRICK_2_WALL_DIST + (j * BRICK_2_BRICK_DIST)+ (j * brickSizeX) + (brickSizeX/2),
                        (BORDER_WIDTH + BRICK_2_WALL_DIST + (BRICK_HEIGHT / 2 ) +(i * BRICK_2_BRICK_DIST)+(i * BRICK_HEIGHT))));
                gameObjects().addGameObject(brick,Layer.STATIC_OBJECTS);
                brickCounter.increment();
            }
        }


    }
    private void generatWalls(WindowController windowController)
    {
        // rightwall
        gameObjects().addGameObject(new GameObject(Vector2.ZERO,
                new Vector2(BORDER_WIDTH, windowDimensions.y()),
                new RectangleRenderable(BORDER_COLOR)));
        // leftwall
        gameObjects().addGameObject(new GameObject(new Vector2(windowDimensions.x() - BORDER_WIDTH , 0),
                new Vector2(BORDER_WIDTH, windowDimensions.y()),
                new RectangleRenderable(BORDER_COLOR)));
        //upperwall
        gameObjects().addGameObject(new GameObject(Vector2.ZERO,
                new Vector2(windowDimensions.x() , BORDER_WIDTH),
                new RectangleRenderable(BORDER_COLOR)));
    }

    private void generateBackground(WindowController windowController , Renderable backgroundImage )
    {
        GameObject background = new GameObject(Vector2.ZERO, windowDimensions, backgroundImage );
        gameObjects().addGameObject(background , Layer.BACKGROUND);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);}

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkForGameEnd();

    }

    private void modifyBallSpeed()
    {
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        Random rand = new Random();
        if (rand.nextBoolean()){ballVelX*=-1 ; ballVelY*=-1;}
        ball.setVelocity(new Vector2(ballVelX , ballVelY));
        ball.setCenter(windowDimensions.mult(0.5F));
    }

    private void checkForGameEnd() {
        float ballHeight = ball.getCenter().y();
        String prompt = "";
        if (brickCounter.value() == 0 ) {prompt = VICTORY_MSG;}
        if (windowDimensions.y() < ballHeight)
        {
            this.score--;
            livesCounter.decrement();
            gameObjects().removeGameObject(this.lives[score]);
            gameObjects().removeGameObject(this.life);
            life = new NumericLifeCounter(livesCounter , Vector2.ZERO , new Vector2(30,30) , gameObjects() ) ;
            life.setCenter(new Vector2( windowDimensions.x() -BORDER_WIDTH + - (30 /2) , windowDimensions.y()- 30));
            gameObjects().addGameObject(life);
            if (this.score > 0 )
            {
                modifyBallSpeed();
                return;}

            else {prompt = LOSS_MSG ;}
        }
        if (!prompt.isEmpty())
        {prompt += PLAY_AGAIN_MSG ;
            if(windowController.openYesNoDialog(prompt)) {windowController.resetGame();}
            else {windowController.closeWindow();}}
    }

    private void generateBall(Renderable ballImage, Sound collisionSound)
    {
        Ball ball = new Ball(Vector2.ZERO, new Vector2(BALL_RADIUS,BALL_RADIUS) , ballImage , collisionSound);
        this.ball = ball;
        modifyBallSpeed();
        gameObjects().addGameObject(ball);
    }

    private void generatePaddle(Renderable paddleImage, UserInputListener inputListener)
    {
        GameObject userPaddle = new Paddle(Vector2.ZERO, new Vector2(PADDLE_WIDTH,PADDLE_HEIGHT) , paddleImage , inputListener,windowDimensions , BORDER_WIDTH);
        userPaddle.setCenter(new Vector2(windowDimensions.x() / 2 , (int)windowDimensions.y() - PADDLE_HEIGHT));
        gameObjects().addGameObject(userPaddle);
    }

    private void generateScore(Renderable lifeImage)
    {
        score = DEFAULT_SCORE ;
        livesCounter.increaseBy(score);
        lives = new GraphicLifeCounter [score];
        for (int i = 0 ; i < score ; i++)
        {
            GraphicLifeCounter life = new GraphicLifeCounter(Vector2.ZERO , new Vector2(LIFE_RADIUS,LIFE_RADIUS) , livesCounter, lifeImage , gameObjects() ,i);
            life.setCenter(new Vector2( BORDER_WIDTH + (i * LIFE_RADIUS) + (LIFE_RADIUS /2) , windowDimensions.y()- LIFE_RADIUS));
            gameObjects().addGameObject(life);
            lives[i] = life;


        }
        life = new NumericLifeCounter(livesCounter , Vector2.ZERO , new Vector2(30,30) , gameObjects() ) ;
        life.setCenter(new Vector2( windowDimensions.x() -BORDER_WIDTH + - (30 /2) , windowDimensions.y()- 30));
        gameObjects().addGameObject(life);

    }



    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.windowController = windowController;
        this.windowDimensions = windowController.getWindowDimensions();
        brickCounter = new Counter();
        livesCounter = new Counter();


        Renderable backgroundImage = imageReader.readImage( "assets/DARK_BG2_small.jpeg" , false) ;
        Renderable ballImage = imageReader.readImage("assets/ball.png" , true) ;
        Sound collisionSound = soundReader.readSound("assets/blop_cut_silenced.wav");
        Renderable paddleImage = imageReader.readImage("assets/paddle.png",true);
        Renderable brickImage = imageReader.readImage("assets/brick.png",false);
        Renderable lifeImage = imageReader.readImage("assets/heart.png",false);

        generateBackground(windowController , backgroundImage);
        generatWalls(windowController);
        generatePaddle(paddleImage , inputListener);
        generateBricks(brickImage );
        generateBall(ballImage, collisionSound);
        generateScore(lifeImage);


    }


    public static void main (String[] args) {new BrickerGameManager("Bricker" , new Vector2(700 , 500)).run();}
}
