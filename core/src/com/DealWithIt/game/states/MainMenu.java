package com.DealWithIt.game.states;

import com.DealWithIt.game.handlers.GameStateManager;
import com.DealWithIt.game.handlers.MyInput;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class MainMenu extends GameState {
    private BitmapFont font;
    public MainMenu(GameStateManager gsm){
        super(gsm);
        font = new BitmapFont(false);
    }
    public void handleInput(){
        if (MyInput.isPressed(MyInput.SSPACE)) {
            gsm.setState(gsm.PLAY);
        }

    };
    public void update (float dt){
        handleInput();
    };
    public void render(){
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.begin();
        font.draw(sb, "ENCADENADO", 400.0f, 500);
        font.draw(sb, "Press space key to play!", 400.0f, 470);
        sb.end();
    };
    public void dispose(){};
}
