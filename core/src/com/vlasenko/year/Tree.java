package com.vlasenko.year;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import static com.vlasenko.year.YearGame.*;

public class Tree {
    private Texture texture;
    private Vector2 position;

    public Tree() {
        this.texture = new Texture("tree.png");
        this.position = new Vector2(350,50);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y,
                300, 300);
    }
}
