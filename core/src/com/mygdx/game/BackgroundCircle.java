package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.nio.file.attribute.FileTime;

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

    public void render(float delta, float y, FitViewport gameView) {
        int leftBottomPointcamera = (int) (camera.position.x) - (int) MyGdxGame.SCREEN_WIDTH / 2;
        //System.out.println(camera.position.x + "<- Normal " + gameView.getScreenX() + "<- Fucking gay");
        float startPoint = (((float) (camera.position.x) - (float) MyGdxGame.SCREEN_WIDTH / 2) / (width))
                * width - width;
        for (int i = 0; i < n; i++) {
            batch.draw(bg, 0,
                    0, width, height);
        }
    }
}
