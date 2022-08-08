package src.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.components.Transform;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class Paddle extends GameObject {


    private UserInputListener inputListener;
    private Vector2 windowDimensions;
    private int minDistanceFromEdge;


    /**
     * Construct a new GameObject instance.
     *  @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     * @param inputListener
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, UserInputListener inputListener , danogl.util.Vector2 windowDimensions, int minDistanceFromEdge) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;

        this.windowDimensions = windowDimensions;
        this.minDistanceFromEdge = minDistanceFromEdge;
    }

    private void checkPaddleRange()
    {
        if (getTopLeftCorner().x()< minDistanceFromEdge )
        {transform().setTopLeftCornerX(minDistanceFromEdge);}
        if(getTopLeftCorner().x() > windowDimensions.x() - minDistanceFromEdge - getDimensions().x())
        {
            transform().setTopLeftCornerX(windowDimensions.x() - minDistanceFromEdge - getDimensions().x());
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = Vector2.ZERO ;
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT)){
           movementDir = movementDir.add(Vector2.LEFT);
        }
        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT)){
            movementDir = movementDir.add(Vector2.RIGHT);
        }

        setVelocity(movementDir.mult(BrickerGameManager.PADDLE_SPEED));
        checkPaddleRange();

    }
}

