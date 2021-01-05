package com.vlasenko.year;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import static com.vlasenko.year.YearGame.*;

public class Cloud {
    private static Texture texture;
    private Vector2 position;
    private float speed;
    private float size;
    private float time;

    public float getRandomXPoint() {
        float coef = 0.2f * MathUtils.sin(time) * size;
        return MathUtils.random(position.x - texture.getWidth() / 2 * (size + coef), position.x + texture.getWidth() / 2 * (size + coef));
    }

    public float getYPoint() {
        return position.y + texture.getHeight() / 2 * size * 0.3f;
    }

    public Cloud() {
        if (texture == null) {
            texture = new Texture("cloud.png");
        }
        this.position = new Vector2(MathUtils.random(WIDTH * 1.4f), HEIGHT - 40 - MathUtils.random(240.0f));
        this.speed = MathUtils.random(20.0f, 40.0f);
        this.size = MathUtils.random(0.3f, 0.6f);
        this.time = MathUtils.random(360.0f);
    }

    public void render(SpriteBatch batch, Texture threadTexture) {
        float coef = 0.2f * MathUtils.sin(time) * size;
        float colorCoef = 0.8f + Math.abs(coef);

        batch.draw(threadTexture, position.x, position.y + texture.getHeight() / 2 * size,
                0, 0,
                threadTexture.getWidth(), threadTexture.getHeight(),
                1, 1,
                0, 0, 0,
                texture.getWidth(), texture.getHeight(),
                false, false);

        batch.setColor(colorCoef, colorCoef, colorCoef, 1);

        batch.draw(texture, position.x - texture.getWidth() / 2, position.y,
                texture.getWidth() / 2, 0,
                texture.getWidth(), texture.getHeight(),
                size + coef, size,
                0, 0, 0,
                texture.getWidth(), texture.getHeight(),
                false, false);

        batch.setColor(1, 1, 1, 1);

    }

    public void update(float dt) {
        time += dt;
        position.x -= speed * dt;
        if (position.x < -texture.getWidth()) {
            position.x = WIDTH * 1.2f + MathUtils.random(WIDTH * 0.4f);
            speed = MathUtils.random(20.0f, 40.0f);
            size = MathUtils.random(0.3f, 0.6f);
        }
    }
}
