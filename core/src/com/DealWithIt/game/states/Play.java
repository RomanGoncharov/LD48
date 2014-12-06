package com.DealWithIt.game.states;

import com.DealWithIt.game.handlers.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.DealWithIt.game.main.Game;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.utils.Array;

import static com.DealWithIt.game.handlers.B2DVars.*;


public class Play extends GameState {

    List<Enemy> enemies;
    private World world;
    private Box2DDebugRenderer b2dr;

    private OrthographicCamera box2dCam;
    private Body playerBody;
    private MyContactListener contactListener;
    private boolean orientationLeft;
    private boolean failed;

    private float spawnDelay;
    private float spawnMaxLevel;
    private float spawnCount;
    private float spawnCurrentCount;
    private int killcount;

    private BitmapFont font;

    private void spawnSequence(float dt){
        spawnDelay-=dt;
        if (spawnDelay<0){
            spawnDelay = (5-spawnMaxLevel)/2;
            float speed = (float)(100*Math.random()+150);
            boolean toleft = Math.random()>0.5;
            short health = (short)(spawnMaxLevel*Math.random()+1);
            if(!failed) {
                enemies.add(new Enemy(toleft, health
                        , speed, world));
                spawnCurrentCount++;
            }
        }
        if (spawnCurrentCount==spawnCount){
            spawnCurrentCount=0;
            if (spawnMaxLevel<4) {
                spawnMaxLevel++;
            }
            spawnDelay = (5-spawnMaxLevel)/2;
        }

    }

    public void createPlayer(){
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        bdef.position.set(400 / PPM, 200 / PPM);
        bdef.type = BodyDef.BodyType.DynamicBody; //должен быть статик
        playerBody = world.createBody(bdef);

        shape.setAsBox(20 / PPM, 40 / PPM);
        fdef.shape = shape;
        fdef.restitution = 0.0f;
        fdef.filter.categoryBits = BIT_PLAYER;
        fdef.filter.maskBits = BIT_GND | BIT_WEAPON | BIT_ENEMY;
        playerBody.createFixture(fdef).setUserData("player");
        orientationLeft=false;
    }

    public void createGnd(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(0 / PPM, 120 / PPM);
        bdef.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(bdef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1000 / PPM, 5 / PPM);
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.filter.categoryBits = BIT_GND;
        fdef.filter.maskBits = BIT_ENEMY | BIT_PLAYER;
        body.createFixture(fdef).setUserData("ground");
    }

    public void createBullet(short color){
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.KinematicBody;
        bdef.position.set(playerBody.getPosition().x, playerBody.getPosition().y);
        if (orientationLeft) {
            bdef.linearVelocity.set(-20, 0);
        }else{
            bdef.linearVelocity.set(20, 0);
        }
        shape.setAsBox(2 / PPM, 2 / PPM);
        fdef.shape = shape;
        fdef.isSensor = true;
        fdef.filter.categoryBits = color;
        fdef.filter.maskBits = BIT_ENEMY | BIT_GND;
        Body body = world.createBody(bdef);
        body.createFixture(fdef).setUserData("bullet");
    }

    public Play(GameStateManager gsm) {
        super(gsm);
        failed = false;
        spawnDelay = 2;
        spawnMaxLevel = 1;
        spawnCount = 10;
        spawnCurrentCount = 0;
        killcount = 0;

        font = new BitmapFont(false);

        //set up box2d
        world = new World(new Vector2(0, -9.81f), true);
        contactListener = new MyContactListener();
        world.setContactListener(contactListener);
        b2dr = new Box2DDebugRenderer();

        //создание платформы
        createGnd();

        //персонаж
        createPlayer();
        enemies = new ArrayList<Enemy>();
        box2dCam = new OrthographicCamera();
        box2dCam.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT / PPM);
    }

    public void handleInput(){

        if(MyInput.isPressed(MyInput.LEFT)) {
            orientationLeft = true;
        }
        if(MyInput.isPressed(MyInput.RIGHT)) {
            orientationLeft = false;
        }
        if(MyInput.isPressed(MyInput.RED_Z)) {
            createBullet(BIT_RED);
        }else
        if(MyInput.isPressed(MyInput.GREEN_X)) {
            createBullet(BIT_GREEN);
        }else
        if(MyInput.isPressed(MyInput.BLUE_C)) {
            createBullet(BIT_BLUE);
        }
        if (failed){
            if(MyInput.isPressed(MyInput.SSPACE)){
                gsm.setState(gsm.MAIN_MENU);
            }
        }

    }
    public void update (float dt){
        handleInput();
        world.step(dt,6,2);
        spawnSequence(dt);
        //6 -- точность проработки ускорений
        //2 -- точность проработки позиций
        Array <Body> bodies = contactListener.getBodiesToRemove();
        for(int i=0; i<enemies.size(); i++) {
            enemies.get(i).update(dt);
            if (enemies.get(i).shouldDie) {
                world.destroyBody(enemies.get(i).body);
                enemies.remove(i);
                killcount++;
            }
        }
        for (int i=0; i<bodies.size; i++){
            Body b = bodies.get(i);
            world.destroyBody(b);
        }
        bodies.clear();
        if(contactListener.checkIfKilled()){
            failed = true;
        }
    }
    public void render() {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.begin();
        Texture bg = Game.res.getTexture("bg");
        sb.draw(bg, 0, 0, bg.getWidth()*5, bg.getHeight()*5);
        sb.end();
        b2dr.render(world, box2dCam.combined);
        sb.begin();
        if (!failed) {
            for (int i = 0; i < enemies.size(); i++) {
                font.draw(sb, enemies.get(i).getHealthbar(), 100.0f, 100.0f + i * 20);
            }
        }else{
            font.draw(sb, Integer.toString(killcount), 390.0f, 200.0f);
            font.draw(sb, "Game over... Press space", 390.0f, 230.0f);
        }
        sb.end();
    }
    public void dispose() {}
}
