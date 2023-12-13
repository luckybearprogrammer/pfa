package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;



public class Player extends Sprite {
    private World world;
    public Body body;
    private float time;
    Texture idle, runRight, runLeft, jumpLeft, jumpRight, attack1Right, attack2Right, attack3Right,
            deathRight, fallRight, takeHitRight, attack1Left, attack2Left, attack3Left,
            deathLeft, fallLeft, takeHitLeft;
    Animation<TextureRegion> animationIdle, animationRunRight, animationRunLeft,
            animationAttack1Right, animationAttack2Right, animationAttack3Right, animationDeathRight,
            animationFallRight, animationTakeHitRight, animationAttack1Left, animationAttack2Left,
            animationAttack3Left, animationDeathLeft, animationFallLeft, animationTakeHitLeft,
            animationJumpRight, animationJumpLeft;
    SpriteBatch batch;
    private float speed = 100, width = 162, height = 162;
    private float size = (float) 0.0001;
    public static Joystick joystick;


    public Player(World world, SpriteBatch batch, Joystick joystick) {
        this.world = world;
        this.batch = batch;
        this.joystick = joystick;
        definePlayer();
        idle = new Texture(Gdx.files.internal("bg/players/FantasyWarrior/Sprites/Idle.png"));
        TextureRegion[][] texturesIdle = TextureRegion.split(idle, (int) width, (int) height);
        Array<TextureRegion> animationFramesIdle = new Array<>();
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 10; j++) animationFramesIdle.add(texturesIdle[i][j]);
        }
        animationIdle = new Animation<TextureRegion>(0.1f, animationFramesIdle, Animation.PlayMode.LOOP);

        jumpRight = new Texture(Gdx.files.internal("bg/players/FantasyWarrior/Sprites/JumpRight.png"));
        TextureRegion[][] texturesjumpRight = TextureRegion.split(jumpRight, (int) width, (int) height);
        Array<TextureRegion> animationFramesJumpRight = new Array<>();
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 3; j++) animationFramesJumpRight.add(texturesjumpRight[i][j]);
        }
        animationJumpRight = new Animation<TextureRegion>(0.1f, animationFramesJumpRight, Animation.PlayMode.LOOP);

        jumpLeft = new Texture(Gdx.files.internal("bg/players/FantasyWarrior/Sprites/JumpLeft.png"));
        TextureRegion[][] texturesjumpLeft = TextureRegion.split(jumpLeft, (int) width, (int) height);
        Array<TextureRegion> animationFramesJumpLeft = new Array<>();
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 3; j++) animationFramesJumpLeft.add(texturesjumpLeft[i][j]);
        }
        animationJumpLeft = new Animation<TextureRegion>(0.1f, animationFramesJumpLeft, Animation.PlayMode.LOOP);


        runRight = new Texture(Gdx.files.internal("bg/players/FantasyWarrior/Sprites/RunRight.png"));
        TextureRegion[][] texturesRunRight = TextureRegion.split(runRight, (int) width, (int) height);
        Array<TextureRegion> animationFramesRunRight = new Array<>();
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 8; j++) animationFramesRunRight.add(texturesRunRight[i][j]);
        }
        animationRunRight = new Animation<TextureRegion>(0.1f, animationFramesRunRight, Animation.PlayMode.LOOP);

        runLeft = new Texture(Gdx.files.internal("bg/players/FantasyWarrior/Sprites/RunLeft.png"));
        TextureRegion[][] texturesRunLeft = TextureRegion.split(runLeft, (int) width, (int) height);
        Array<TextureRegion> animationFramesRunLeft = new Array<>();
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 8; j++) animationFramesRunLeft.add(texturesRunLeft[i][j]);
        }
        animationRunLeft = new Animation<TextureRegion>(0.1f, animationFramesRunLeft, Animation.PlayMode.LOOP);

        attack1Right = new Texture(Gdx.files.internal("bg/players/FantasyWarrior/Sprites/Attack1Right.png"));
        TextureRegion[][] texturesattack1Rigth = TextureRegion.split(attack1Right, (int) width, (int) height);
        Array<TextureRegion> animationFramesAttack1Right = new Array<>();
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 7; j++) animationFramesAttack1Right.add(texturesattack1Rigth[i][j]);
        }
        animationAttack1Right = new Animation<TextureRegion>(0.1f, animationFramesAttack1Right, Animation.PlayMode.LOOP);

        attack1Left = new Texture(Gdx.files.internal("bg/players/FantasyWarrior/Sprites/Attack1Left.png"));
        TextureRegion[][] texturesattack1Left = TextureRegion.split(attack1Left, (int) width, (int) height);
        Array<TextureRegion> animationFramesAttack1Left = new Array<>();
        for (int i = 0; i < 1; i++) {
            for (int j = 6; j > -1; j--) animationFramesAttack1Left.add(texturesattack1Left[i][j]);
        }
        animationAttack1Left = new Animation<TextureRegion>(0.1f, animationFramesAttack1Left, Animation.PlayMode.LOOP);


        attack2Right = new Texture(Gdx.files.internal("bg/players/FantasyWarrior/Sprites/Attack2Right.png"));
        TextureRegion[][] texturesattack2Rigth = TextureRegion.split(attack2Right, (int) width, (int) height);
        Array<TextureRegion> animationFramesAttack2Right = new Array<>();
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 7; j++) animationFramesAttack2Right.add(texturesattack2Rigth[i][j]);
        }
        animationAttack2Right = new Animation<TextureRegion>(0.1f, animationFramesAttack2Right, Animation.PlayMode.LOOP);

        attack2Left = new Texture(Gdx.files.internal("bg/players/FantasyWarrior/Sprites/Attack2Left.png"));
        TextureRegion[][] texturesattack2Left = TextureRegion.split(attack2Left, (int) width, (int) height);
        Array<TextureRegion> animationFramesAttack2Left = new Array<>();
        for (int i = 0; i < 1; i++) {
            for (int j = 6; j > -1; j--) animationFramesAttack2Left.add(texturesattack2Left[i][j]);
        }
        animationAttack2Left = new Animation<TextureRegion>(0.1f, animationFramesAttack2Left, Animation.PlayMode.LOOP);

        attack3Right = new Texture(Gdx.files.internal("bg/players/FantasyWarrior/Sprites/Attack3Right.png"));
        TextureRegion[][] texturesattack3Rigth = TextureRegion.split(attack3Right, (int) width, (int) height);
        Array<TextureRegion> animationFramesAttack3Right = new Array<>();
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 7; j++) animationFramesAttack3Right.add(texturesattack3Rigth[i][j]);
        }
        animationAttack3Right = new Animation<TextureRegion>(0.1f, animationFramesAttack3Right, Animation.PlayMode.LOOP);

        attack3Left = new Texture(Gdx.files.internal("bg/players/FantasyWarrior/Sprites/Attack3Left.png"));
        TextureRegion[][] texturesattack3Left = TextureRegion.split(attack3Left, (int) width, (int) height);
        Array<TextureRegion> animationFramesAttack3Left = new Array<>();
        for (int i = 0; i < 1; i++) {
            for (int j = 6; j > -1; j--) animationFramesAttack3Left.add(texturesattack3Left[i][j]);
        }
        animationAttack3Left = new Animation<TextureRegion>(0.1f, animationFramesAttack3Left, Animation.PlayMode.LOOP);

        deathRight = new Texture(Gdx.files.internal("bg/players/FantasyWarrior/Sprites/DeathRight.png"));
        TextureRegion[][] texturesDearthRight = TextureRegion.split(deathRight, (int) width, (int) height);
        Array<TextureRegion> animationFramesDearthRight = new Array<>();
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 7; j++) animationFramesDearthRight.add(texturesDearthRight[i][j]);
        }
        animationDeathRight = new Animation<TextureRegion>(0.1f, animationFramesDearthRight, Animation.PlayMode.LOOP);

        deathLeft = new Texture(Gdx.files.internal("bg/players/FantasyWarrior/Sprites/DeathLeft.png"));
        TextureRegion[][] texturesDearthLeft = TextureRegion.split(deathLeft, (int) width, (int) height);
        Array<TextureRegion> animationFramesDearthLeft = new Array<>();
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 7; j++) animationFramesDearthLeft.add(texturesDearthLeft[i][j]);
        }
        animationDeathLeft = new Animation<TextureRegion>(0.1f, animationFramesDearthLeft, Animation.PlayMode.LOOP);

        fallRight = new Texture(Gdx.files.internal("bg/players/FantasyWarrior/Sprites/FallRight.png"));
        TextureRegion[][] texturesFallRight = TextureRegion.split(fallRight, (int) width, (int) height);
        Array<TextureRegion> animationFramesFallRight = new Array<>();
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 3; j++) animationFramesFallRight.add(texturesFallRight[i][j]);
        }
        animationFallRight = new Animation<TextureRegion>(0.1f, animationFramesFallRight, Animation.PlayMode.LOOP);

        fallLeft = new Texture(Gdx.files.internal("bg/players/FantasyWarrior/Sprites/FallLeft.png"));
        TextureRegion[][] texturesFallLeft = TextureRegion.split(fallLeft, (int) width, (int) height);
        Array<TextureRegion> animationFramesFallLeft = new Array<>();
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 3; j++) animationFramesFallLeft.add(texturesFallLeft[i][j]);
        }
        animationFallLeft = new Animation<TextureRegion>(0.1f, animationFramesFallLeft, Animation.PlayMode.LOOP);

        takeHitRight = new Texture(Gdx.files.internal("bg/players/FantasyWarrior/Sprites/TakeHitRight.png"));
        TextureRegion[][] texturesTakeHitRight = TextureRegion.split(takeHitRight, (int) width, (int) height);
        Array<TextureRegion> animationFramesTakeHitRight = new Array<>();
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 3; j++) animationFramesTakeHitRight.add(texturesTakeHitRight[i][j]);
        }
        animationTakeHitRight = new Animation<TextureRegion>(0.1f, animationFramesTakeHitRight, Animation.PlayMode.LOOP);

        takeHitLeft = new Texture(Gdx.files.internal("bg/players/FantasyWarrior/Sprites/TakeHitLeft.png"));
        TextureRegion[][] texturesTakeHitLeft = TextureRegion.split(takeHitLeft, (int) width, (int) height);
        Array<TextureRegion> animationFramesTakeHitLeft = new Array<>();
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 3; j++) animationFramesTakeHitLeft.add(texturesTakeHitLeft[i][j]);
        }
        animationTakeHitLeft = new Animation<TextureRegion>(0.1f, animationFramesTakeHitLeft, Animation.PlayMode.LOOP);


    }

    private void definePlayer() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(new Vector2(80,330));
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(5, 5);
        fixtureDef.shape = shape;
        fixtureDef.friction = 0.95f;
        fixtureDef.density = 0;
        body.createFixture(fixtureDef);

    }

    public void render(float delta) {

        time += delta;
//        batch.draw(animationTakeHitLeft.getKeyFrame(time), body.getPosition().x - (int) (width * 1.7),
//                body.getPosition().y - (int) (height * 1.7), size, size);

        if (joystick.getResult().x > 0.75f && joystick.getResult().y < 0.5f)
            batch.draw(animationRunRight.getKeyFrame(time), body.getPosition().x,
                    body.getPosition().y, size * width, size * height);
        if (joystick.getResult().x < -0.75f && joystick.getResult().y < 0.5f)
            batch.draw(animationRunLeft.getKeyFrame(time), body.getPosition().x,
                    body.getPosition().y , size * width, size * height);

        if (joystick.getResult().y <= -0.1f && joystick.getResult().x < 0.75f && joystick.getResult().x > -0.75f) {
            batch.draw(animationJumpRight.getKeyFrame(time), body.getPosition().x,
                    body.getPosition().y, size * width, size * height);
        }
        else {
            if (joystick.getResult().x > -0.75f && joystick.getResult().x < 0.75f)
                batch.draw(animationIdle.getKeyFrame(time), 330,
                        80, 20, 20);
        }

//        if (joystick.getResult().y > 0.75f)
//            if (joystick.getResult().x > 0)
//                batch.draw(animationjumpLeft.getKeyFrame(time), body.getPosition().x - (int) (width * 2.5),
//                    body.getPosition().y - (int) (height * 2.5) - 10, size, size);


    }


}