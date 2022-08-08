package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import danogl.util.Counter;

import javax.swing.*;

public class NumericLifeCounter extends GameObject {
    private GameObjectCollection gameObjectCollection;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     */
    public NumericLifeCounter(Counter livesCounter, Vector2 topLeftCorner, Vector2 dimensions, GameObjectCollection gameObjectCollection) {
        super(topLeftCorner, dimensions, new TextRenderable(String.valueOf(livesCounter.value())) );
        this.gameObjectCollection = gameObjectCollection;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }
}

