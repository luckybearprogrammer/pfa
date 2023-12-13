package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.Date;
import java.util.HashMap;

public class TestMapScreen implements Screen {
    private static final float unitScale = 0.2f;
    private final SpriteBatch batch;
    private final OrthographicCamera camera, hudCamera;
    private FitViewport gameViewport;
    private FitViewport hudViewport;
    private Stage hudStage;
    private TmxMapLoader tmxMapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private Box2DDebugRenderer b2dr;
    private World world;
    private Player player;
    private Vector2 touch, worldTouch;
    private Joystick joystick;

    private Texture first, second, third;
    private HashMap<String, BackgroundCircle> parallaxBg = new HashMap<>();

//    Texture deleteLater;

    public TestMapScreen(SpriteBatch batch, OrthographicCamera camera, OrthographicCamera hudCamera) {
        this.batch = batch;
        this.camera = camera;
        this.hudCamera = hudCamera;

        gameViewport = new FitViewport(MyGdxGame.WIDTH, MyGdxGame.HEIGHT, camera);
        hudViewport = new FitViewport(MyGdxGame.WIDTH, MyGdxGame.HEIGHT, hudCamera);
        hudStage = new Stage(hudViewport, batch);

//        deleteLater= new Texture("badlogic.jpg");
        tmxMapLoader = new TmxMapLoader();
        map = tmxMapLoader.load("jo.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, unitScale, batch);

        world = new World(new Vector2(0, -3600), true);

        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;

        b2dr = new Box2DDebugRenderer();

        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.x + rect.getWidth() / 2) * unitScale,
                    (rect.y + rect.getHeight() / 2) * unitScale);

            body = world.createBody(bodyDef);

            shape.setAsBox(rect.getWidth() / 2 * unitScale, rect.getHeight() / 2 * unitScale);
            fixtureDef.shape = shape;
            body.createFixture(fixtureDef);
        }

        Date date1 = new Date();

        player = new Player(world, batch, joystick);

        touch = new Vector2(
                Gdx.input.getX() - gameViewport.getScreenX(),
                gameViewport.getScreenHeight() - Gdx.input.getY() + gameViewport.getScreenY()
        );
        if (date1.getHours() < 18) {
            first = new Texture("bg/SET1/png/SET1_bakcground_night1.png");
            second = new Texture("bg/SET1/png/SET1_bakcground_night2.png");
            third = new Texture("bg/SET1/png/SET1_bakcground_night3.png");
        } else {
            first = new Texture("bg/SET1/png/SET1_bakcground_day1.png");
            second = new Texture("bg/SET1/png/SET1_bakcground_day2.png");
            third = new Texture("bg/SET1/png/SET1_bakcground_day3.png");
        }


        parallaxBg.put("firstBg", new BackgroundCircle(first, batch, camera, -0.2f));
        parallaxBg.put("secondBg", new BackgroundCircle(second, batch, camera, -0.15f));
        parallaxBg.put("thirdBg", new BackgroundCircle(third, batch, camera, -0.3f));
        joystick = new Joystick(hudViewport, hudCamera, new Texture("bgJoystick.png"),
                new Texture("fgStick.png"), 20, 6);
        hudStage.addActor(joystick);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 0);
        updateTouch();

        camera.position.x = player.body.getPosition().x;
        camera.position.y = player.body.getPosition().y;

        if (joystick.getResult().y > 0.5f && joystick.getResult().x < 0.75f && joystick.getResult().x > -0.75f) {
            player.body.applyLinearImpulse(new Vector2(0, 700000),
                    player.body.getPosition(), true);
        }
        if (joystick.getResult().x > 0.75f && joystick.getResult().y < 0.5f) {
            player.body.applyForceToCenter(new Vector2(3000, 0), true);
        }
        if (joystick.getResult().x < -0.75f && joystick.getResult().y < 0.5f) {
            player.body.applyForceToCenter(new Vector2(-3000, 0), true);
        }
        if (Gdx.input.isTouched()) {
            System.out.println(
                    (touch.x) + ", " +
                            (touch.y)
            );
            System.out.println(
                    "!!! " + worldTouch.x + ", " + worldTouch.y
            );
        }

        camera.position.add(
                joystick.getResult().x / 3f,
                joystick.getResult().y / 3f,
                0
        );

        //Обновление камеры
        camera.update();
        hudCamera.update();

        //Для отображения объектов через batch.begin() batch.end()
        batch.setProjectionMatrix(camera.combined);

        //Отображение карты
        renderer.setView(camera);
        renderer.render();

        //Отвечает за отрисовку границ rectangle
        b2dr.render(world, camera.combined);

        hudStage.act(delta);
        hudStage.draw();

        //Физическая симуляция мира
        world.step(1 / 160f, 6, 2);
    }

    @Override
    public void resize(int width, int height) {
        gameViewport.update(width, height, true);
        hudViewport.update(width, height, true);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        MyGdxGame.SCREEN_WIDTH = gameViewport.getScreenWidth();
        MyGdxGame.SCREEN_HEIGHT = gameViewport.getScreenHeight();
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

    private void updateTouch() {
        touch.set(
                Gdx.input.getX() - gameViewport.getScreenX(),
                gameViewport.getScreenHeight() - Gdx.input.getY() + gameViewport.getScreenY()
        );
        worldTouch = gameViewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
    }
}