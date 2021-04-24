package com.example.moragame.objects;

import java.util.Random;

public class Computer extends Player {
    private onComputerCompletedListener onComputerCompletedListener;

    public void Computer() {
        setLife(1);
    }

    public void setOnComputerCompletedListener(onComputerCompletedListener onComputerCompletedListener) {
        this.onComputerCompletedListener = onComputerCompletedListener;
    }

    public void AI() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setMora(getRandomMora());
                onComputerCompletedListener.complete();
            }
        }).start();

    }


    public int getRandomMora() {
        return new Random().nextInt(PAPER + 1);
    }

    public interface onComputerCompletedListener {
        void complete();
    }
}
