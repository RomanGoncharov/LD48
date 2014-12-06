package com.DealWithIt.game.handlers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import java.lang.Math;
import static com.DealWithIt.game.handlers.B2DVars.*;

public class Enemy {
    public Body body;
    public short health;
    public short healthbar[];
    public boolean fromleft;
    public float speed;
    public boolean shouldDie;

    public Enemy(boolean fromleft, short health, float speed, World world){
        shouldDie = false;
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        this.fromleft = fromleft;
        if (fromleft){ bdef.position.set(-40 / PPM, 200 / PPM);}
        else {bdef.position.set(840 / PPM, 200 / PPM);}
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);
        shape.setAsBox(20 / PPM, 40 / PPM);
        fdef.shape = shape;
        fdef.restitution = 0.0f;
        fdef.filter.categoryBits = BIT_ENEMY;
        fdef.filter.maskBits = BIT_GND | BIT_RED | BIT_GREEN | BIT_BLUE | BIT_ENEMY |BIT_PLAYER;
        body.createFixture(fdef).setUserData("enemy");
        this.health=health;
        healthbar = new short[health];
        for(int i=0; i<health; i++){
            short power = (short)(1 + Math.random() * 3);
            healthbar[i] = (short)(Math.pow(2.0, power));
        }
        this.speed = speed;


        shape = new PolygonShape();
        if (fromleft) {
            shape.setAsBox(20 / PPM, 3 / PPM, new Vector2( 10/ PPM, -20 / PPM), 0);
        }else{
            shape.setAsBox(20 / PPM, 3 / PPM, new Vector2( -10/ PPM, -20 / PPM), 0);
        }
        fdef.shape = shape;
        fdef.isSensor = true;
        fdef.filter.categoryBits = B2DVars.BIT_WEAPON;
        fdef.filter.maskBits = B2DVars.BIT_PLAYER;
        body.createFixture(fdef).setUserData("weapon");
        shape.dispose();
    }

    public void die(){
        shouldDie = true;
    }

    public void getHit(){
        if(!body.getFixtureList().get(0).getUserData().equals("enemy")){
            if(body.getFixtureList().get(0).getUserData().equals(healthbar[health-1])){
                health--;
                body.applyForceToCenter(-body.getLinearVelocity().x * 75, 0, true);
            }
            body.getFixtureList().get(0).setUserData("enemy");
            if(health == 0){
                die();
            }
        }
    }

    public void update(float dt){
        if (fromleft){
            body.applyForceToCenter(speed*dt,1,true);
        }else{
            body.applyForceToCenter(-speed * dt, 1, true);
        }
        getHit();
    }

    public String getHealthbar(){
        StringBuilder str = new StringBuilder();
        for(int i=health-1; i>=0; i--) {
            if(healthbar[i] == BIT_RED)str.append('z');
            if(healthbar[i] == BIT_GREEN)str.append('x');
            if(healthbar[i] == BIT_BLUE)str.append('c');
        }
        return str.toString();
    }
}

