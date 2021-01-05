package com.vlasenko.year;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;

public class YearGame extends ApplicationAdapter {
    public static final int WIDTH = 640;
    public static final int HEIGHT = 800;

    private SpriteBatch batch;
    private PolygonSpriteBatch polygonSpriteBatch;
    private Snow[] flakes;
    private Moon moon;
    private Cloud[] clouds;
    private House house;
    private Tree tree;

    private Texture threadTexture;
    private Texture edgeTexture;

    private PolygonSprite[] ground;

    @Override
    public void create() {
        this.batch = new SpriteBatch();
        this.polygonSpriteBatch = new PolygonSpriteBatch();
        this.threadTexture = new Texture("thread.png");
        this.edgeTexture = new Texture("edge.png");

        this.clouds = new Cloud[10];
        for (int i = 0; i < clouds.length; i++) {
            clouds[i] = new Cloud();
        }

        this.flakes = new Snow[700];
        for (int i = 0; i < flakes.length; i++) {
            flakes[i] = new Snow(clouds);
        }

        this.moon = new Moon();
        this.house = new House();
        this.tree = new Tree();

        this.ground = new PolygonSprite[]{
                generatePoygonSprite(300, 130, 0.1f),
                generatePoygonSprite(240, 100, 0.3f),
                generatePoygonSprite(180, 70, 0.7f),
                generatePoygonSprite(100, 10, 1)
        };

    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        update(dt);

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        polygonSpriteBatch.begin();
        for (int i = 0; i < ground.length ; i++) {
            ground[i].draw(polygonSpriteBatch);
        }
        polygonSpriteBatch.end();

        moon.render(batch, threadTexture);
        house.render(batch);
        tree.render(batch);

        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        for (int i = 0; i < flakes.length; i++) {
            flakes[i].render(batch);
        }
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        for (int i = 0; i < clouds.length; i++) {
            clouds[i].render(batch, threadTexture);
        }

        for (int i = 0; i < WIDTH / 25; i++) {
            batch.draw(edgeTexture, i * 25, -20);
            batch.draw(edgeTexture, i * 25, HEIGHT - 20);
        }

        for (int i = 0; i < HEIGHT / 25; i++) {
            batch.draw(edgeTexture, -20, i * 25);
            batch.draw(edgeTexture, WIDTH - 20, i * 25);
        }
        batch.end();
    }

    public void update(float dt) {
        for (int i = 0; i < flakes.length; i++) {
            flakes[i].update(dt);
        }

        for (int i = 0; i < clouds.length; i++) {
            clouds[i].update(dt);
        }

        moon.update(dt);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    private void getCentre(float[] array, int left, int right, int rnd) {
        int mid = (left + right) / 2;
        array[mid] = (array[left] + array[right]) / 2 + MathUtils.random(-rnd, rnd);
        if (right - left > 1) {
            if (rnd > 2) {
                rnd /= 2;
            }
            getCentre(array, left, mid, rnd);
            getCentre(array, mid, right, rnd);
        }

    }

    private PolygonSprite generatePoygonSprite(float baseHeight, int randomHeight, float colorFading) {
        int BLOCK_WIDTH = 5;
        int BLOCKS_COUNT = WIDTH / BLOCK_WIDTH + 5;

        float[] vertices = new float[BLOCKS_COUNT * 4];
        short[] indices = new short[BLOCKS_COUNT * 6];

        float[] heightMap = new float[BLOCKS_COUNT];

        heightMap[0] = baseHeight + MathUtils.random(-50, 50);
        heightMap[heightMap.length - 1] = baseHeight + MathUtils.random(-50, 50);
        getCentre(heightMap, 0, heightMap.length - 1, randomHeight);

        float maxHeight = 0.0f;
        for (int i = 0; i < heightMap.length; i++) {
            if ((heightMap[i] > maxHeight)) {
                maxHeight = heightMap[i];
            }
        }

        for (int i = 0; i < BLOCKS_COUNT; i++) {
            vertices[i * 4 + 0] = i * BLOCK_WIDTH;
            vertices[i * 4 + 1] = 0;
            vertices[i * 4 + 2] = i * BLOCK_WIDTH;
            vertices[i * 4 + 3] = heightMap[i];
        }

        for (int i = 0; i < BLOCKS_COUNT - 1; i++) {
            indices[i * 6 + 0] = (short) (i * 2 + 0);
            indices[i * 6 + 1] = (short) (i * 2 + 1);
            indices[i * 6 + 2] = (short) (i * 2 + 2);
            indices[i * 6 + 3] = (short) (i * 2 + 1);
            indices[i * 6 + 4] = (short) (i * 2 + 3);
            indices[i * 6 + 5] = (short) (i * 2 + 2);
        }

        Pixmap pix = new Pixmap(2, (int) maxHeight, Pixmap.Format.RGBA8888);
        for (int i = 0; i < maxHeight; i++) {
            float c = (1.0f - i / maxHeight) * colorFading;
            pix.setColor(c, c, c, 1.0f);
            pix.fillRectangle(0, i * 4, 2, 4);
        }
        TextureRegion textureRegion = new TextureRegion(new Texture(pix));

        return new PolygonSprite(new PolygonRegion(textureRegion, vertices, indices));
    }
}
