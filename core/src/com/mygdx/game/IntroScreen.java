package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.VideoPlayerCreator;

import java.io.FileNotFoundException;

public class IntroScreen implements Screen {
    private VideoPlayer videoPlayer;

    private MyGdxGame myGdxGame;
    private final SpriteBatch batch;
    private OrthographicCamera camera;

    public IntroScreen(final MyGdxGame myGdxGame, SpriteBatch batch, OrthographicCamera camera) {
        this.myGdxGame = myGdxGame;
        this.batch = batch;
        this.camera = camera;
        videoPlayer = VideoPlayerCreator.createVideoPlayer();

        videoPlayer.setOnCompletionListener(new VideoPlayer.CompletionListener() {
            @Override
            public void onCompletionListener(FileHandle file) {
                myGdxGame.setScreen(myGdxGame.testMapScreen);
            }
        });
    }

    @Override
    public void show() {
        try {
            videoPlayer.play(Gdx.files.internal("Int.webm"));
        } catch (FileNotFoundException e){
            System.out.println("Я ГЕЙ НАХ*Й");
        }

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        videoPlayer.update();

        batch.begin();
        Texture frame = videoPlayer.getTexture();
        if (frame != null){
            batch.draw(frame, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
