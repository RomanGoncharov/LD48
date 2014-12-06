package com.DealWithIt.game.handlers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class InputProcessor extends InputAdapter{
    public  boolean keyDown(int k){
        if (k==com.badlogic.gdx.Input.Keys.LEFT) {
            MyInput.setKey(MyInput.LEFT, true);
        }
        if (k==com.badlogic.gdx.Input.Keys.RIGHT){
            MyInput.setKey(MyInput.RIGHT, true);
        }
        if (k==com.badlogic.gdx.Input.Keys.Z){
            MyInput.setKey(MyInput.RED_Z, true);
        }
        if (k==com.badlogic.gdx.Input.Keys.X){
            MyInput.setKey(MyInput.GREEN_X, true);
        }
        if (k==com.badlogic.gdx.Input.Keys.C){
            MyInput.setKey(MyInput.BLUE_C, true);
        }
        if (k== Input.Keys.SPACE){
            MyInput.setKey(MyInput.SSPACE, true);
        }
        return true;
    }
    public  boolean keyUp(int k){
        if (k==com.badlogic.gdx.Input.Keys.LEFT) {
            MyInput.setKey(MyInput.LEFT, false);
        }
        if (k==com.badlogic.gdx.Input.Keys.RIGHT){
            MyInput.setKey(MyInput.RIGHT, false);
        }
        if (k==com.badlogic.gdx.Input.Keys.Z){
            MyInput.setKey(MyInput.RED_Z, false);
        }
        if (k==com.badlogic.gdx.Input.Keys.X){
            MyInput.setKey(MyInput.GREEN_X, false);
        }
        if (k==com.badlogic.gdx.Input.Keys.C){
            MyInput.setKey(MyInput.BLUE_C, false);
        }
        if (k== Input.Keys.SPACE){
            MyInput.setKey(MyInput.SSPACE, false);
        }
        return true;
    }
}
