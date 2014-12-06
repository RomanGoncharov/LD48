package com.DealWithIt.game.handlers;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

public class MyContactListener implements ContactListener {
    private boolean killed;
//    private int num

    private Array<Body> bodiesToRemove;
    public MyContactListener(){
        super();
        killed = false;
        bodiesToRemove = new Array<Body>();
    }

    public void beginContact(Contact c){
        Fixture fa = c.getFixtureA();
        Fixture fb = c.getFixtureB();
        if(fa == null || fb == null) return;
        if (fa.getUserData().equals("bullet")){
            fb.setUserData(fa.getFilterData().categoryBits);
            bodiesToRemove.add(fa.getBody());
        }
        if (fb.getUserData().equals("bullet")){
            fa.setUserData(fb.getFilterData().categoryBits);
            bodiesToRemove.add(fb.getBody());
        }
        if (fa.getUserData().equals("weapon")){
            killed = true;
        }
        if (fb.getUserData().equals("weapon")){
            killed = true;
        }
    }
    public void endContact(Contact c){
        Fixture fa = c.getFixtureA();
        Fixture fb = c.getFixtureB();
        if(fa == null || fb == null) return;
    }
    //public boolean isPlayerOnGround() {return numFootContacts>0;}
    public Array<Body> getBodiesToRemove() { return bodiesToRemove;}
    public boolean checkIfKilled(){return killed;}
    public void preSolve(Contact c, Manifold m){}
    public void postSolve(Contact c, ContactImpulse ci){}
}
