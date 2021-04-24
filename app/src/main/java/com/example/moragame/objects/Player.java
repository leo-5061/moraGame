package com.example.moragame.objects;

public class Player {
    public static final int READY = -1;
    public static final int SCISSORS = 0;
    public static final int ROCK = 1;
    public static final int PAPER = 2;
    private int mora;
    private int life;

    public void Player() {
        mora = READY;
        life = 3;
    }

    public int getMora() {
        return mora;
    }

    public void setMora(int mora) {
        this.mora = mora;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }
}
