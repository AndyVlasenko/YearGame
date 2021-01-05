package com.vlasenko.year;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import static com.vlasenko.year.YearGame.*;

public class Moon {
    private Texture texture;
    private Vector2 position;
    private float time;

    public Moon() {
        this.texture = new Texture("moon.png");
        this.position = new Vector2(WIDTH - 300, HEIGHT - 300 + 40);    //обида с любовью <3

    }

    public void render(SpriteBatch batch, Texture threadTexture) {
        float coef = 0.2f * MathUtils.sin(time);

        batch.draw(threadTexture, position.x + 100, position.y + 190);

        batch.setColor(0.8f + coef, 0.8f + coef, 0.8f + coef, 1);

        batch.draw(texture, position.x, position.y,
                texture.getWidth() / 2, 0,
                300 - 100, 300 - 100,  //обида с любовью <3
                1 + coef, 1,
                0, 0, 0,
                texture.getWidth(), texture.getHeight(),
                false, false);

        batch.setColor(1, 1, 1, 1);
    }

    public void update(float dt) {
        time += dt;
    }
}
