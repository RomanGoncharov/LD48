package com.DealWithIt.game.handlers;

public class MyInput {
    public static boolean[] keys;
    public static boolean[] pkeys;

    public static final int NUM_KEYS = 6;
    public static int LEFT = 0;
    public static int RIGHT = 1;
    public static int RED_Z = 2;
    public static int GREEN_X = 3;
    public static int BLUE_C = 4;
    public static int SSPACE = 5;

    static {
        keys = new boolean[NUM_KEYS];
        pkeys = new boolean[NUM_KEYS];
    }

    public  static void update(){
        for (int i=0; i<NUM_KEYS; i++){
            pkeys[i]=keys[i];
        }
    }
    public  static void setKey(int i, boolean b){ keys[i] = b;}
    public static boolean isDown(int i){
        return keys[i];
    }
    public static boolean isPressed(int i){
        return !pkeys[i] && keys[i];
    }
}
