package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BackgroundCircle {
    private Texture bg;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private int n = -1;
    private int width, height;
    private float paralaxkoef;
    private float pallaxdelta;

    public BackgroundCircle(Texture bg, SpriteBatch batch, OrthographicCamera camera, float paralaxkoef) {
        this.bg = bg;
        this.camera = camera;
        this.batch = batch;
        this.paralaxkoef = paralaxkoef;
        width = (int) (MyGdxGame.WIDTH);
        height = (int) (MyGdxGame.HEIGHT);
        width = (height / bg.getHeight()) * bg.getWidth();

        n = (int) MyGdxGame.WIDTH / bg.getWidth();

        if (n < 4) {
            n = 4;
        } else n += 2;
        n++;

    }

    public void render(float delta, float y) {
        int leftBottomPointcamera = (int) (camera.position.x) - (int) MyGdxGame.WIDTH / 2;
        float startPoint = (((float) (camera.position.x) - (float) MyGdxGame.WIDTH / 2) / (width))
                * width - width;
        for (int i = 0; i < n; i++) {
            batch.draw(bg, startPoint + i * width + (leftBottomPointcamera * paralaxkoef) % width,
                    y - MyGdxGame.HEIGHT / 2 - 5, width, height);
        }
    }
}
