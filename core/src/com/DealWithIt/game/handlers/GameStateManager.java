package com.DealWithIt.game.handlers;

import com.DealWithIt.game.main.Game;
import com.DealWithIt.game.states.GameState;
import com.DealWithIt.game.states.MainMenu;
import com.DealWithIt.game.states.Play;
import com.sun.media.jfxmedia.events.PlayerStateEvent;

import java.util.Stack;

public class GameStateManager {

    private Game game;
    private Stack<GameState> gameStates;

    public static final int PLAY = 1351235;
    public static final int MAIN_MENU = 1351236;

    public GameStateManager(Game game){
        this.game=game;
        gameStates=new Stack<GameState>();
        pushState(MAIN_MENU);
    }

    public Game game(){return game;}

    public void update(float dt){
        gameStates.peek().update(dt);
    }

    public void render(){
        gameStates.peek().render();
    }

    private GameState getState (int state){
        if(state == PLAY) return new Play(this);
        if(state == MAIN_MENU) return new MainMenu(this);
        return null;
    }

    public void setState(int state){
        popState();
        pushState(state);
    }

    public void pushState(int state){
        gameStates.push(getState(state));
    }

    public void popState(){
        GameState state = gameStates.pop();
        state.dispose();
    }
}
