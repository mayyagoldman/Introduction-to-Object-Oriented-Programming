package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class GraphicLifeCounter extends GameObject {

    private Counter livesCounter;
    private int numOfLives;
    private GameObjectCollection gameObjectCollection;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     */
    public GraphicLifeCounter(Vector2 widgetTopLeftCorner, Vector2 widgetDimensions, Counter livesCounter , Renderable widgetRenderable,  GameObjectCollection gameObjectCollection , int numOfLives) {
        super(widgetTopLeftCorner, widgetDimensions, widgetRenderable);
        this.livesCounter = livesCounter;
        this.numOfLives = numOfLives;
        this.gameObjectCollection = gameObjectCollection;
    }


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (numOfLives >= livesCounter.value())
        {gameObjectCollection.removeGameObject(this , Layer.STATIC_OBJECTS);}
    }



}
