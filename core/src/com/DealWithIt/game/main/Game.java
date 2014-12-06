package com.DealWithIt.game.main;

import com.DealWithIt.game.handlers.Content;
import com.DealWithIt.game.handlers.MyInput;
import com.DealWithIt.game.handlers.InputProcessor;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.DealWithIt.game.handlers.GameStateManager;
import java.util.Random;

public class Game implements ApplicationListener{

    public static final String TITLE = "ENCADENADO";
    public static final int V_WIDTH = 800;
    public static final int V_HEIGHT = 600;

    public static final float STEP = 1/60f;
    private float accum;

    private SpriteBatch sb;
    private OrthographicCamera cam;
    private OrthographicCamera hudCam;

    private GameStateManager gsm;

    public static Content res;

    public void create() {
        res = new Content();
        res.loadTexture("data/OLD.png", "deal");
        res.loadTexture("data/BackGround.png", "bg");
        res.loadTexture("data/Bandit.png", "bandit");
        Gdx.input.setInputProcessor(new InputProcessor());
        sb = new SpriteBatch();
        cam = new OrthographicCamera();
        cam.setToOrtho(false, V_WIDTH, V_HEIGHT);
        hudCam = new OrthographicCamera();
        hudCam.setToOrtho(false, V_WIDTH, V_HEIGHT);
        gsm = new GameStateManager(this);
    }
    public void render() {
        accum+= Gdx.graphics.getDeltaTime();
        while (accum >= STEP){
            accum -= STEP;
            gsm.update(STEP);
            gsm.render();
            MyInput.update();
        }
        sb.begin();
        sb.draw(res.getTexture("deal"),0,0); // отрисовка текстуры
        sb.end();
    }
    public void dispose(){}

    public SpriteBatch getSpriteBatch() {return sb;}
    public OrthographicCamera getCamera(){return cam;}
    public OrthographicCamera getHUDCamera(){return hudCam;}

    public void resize(int w, int h){}
    public void pause(){}
    public void resume(){}
}
