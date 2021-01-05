package com.vlasenko.year;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import static com.vlasenko.year.YearGame.*;

public class Snow {
    private Texture texture;
    private Cloud[] clouds;
    private Vector2 postition;
    private Vector2 speed;
    private float size;

    public Snow(Cloud[] clouds) {
        if (texture == null) {
            this.texture = new Texture("snow.png");
        }
        this.clouds = clouds;
        this.postition = new Vector2(MathUtils.random(WIDTH), MathUtils.random(HEIGHT));
        this.speed = new Vector2(MathUtils.random(-15.0f, 15.0f), MathUtils.random(-60.0f, -20.0f));
        this.size = MathUtils.random(5.0f, 12.0f);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, postition.x, postition.y, size, size);
        if (MathUtils.random(1000) < 3) {
            batch.draw(texture, postition.x, postition.y, size * 1.3f, size * 1.3f);
        }
    }

    public void update(float dt) {
        postition.mulAdd(speed, dt);
        if (postition.y < -20) {
            int cloudIndex = MathUtils.random(clouds.length - 1);

            postition.set(clouds[cloudIndex].getRandomXPoint(), clouds[cloudIndex].getYPoint());    //обида с любовью <3
            speed.set(MathUtils.random(-15.0f, 15.0f), MathUtils.random(-60.0f, -20.0f));
            size = MathUtils.random(5.0f, 11.0f);
        }
    }
}
