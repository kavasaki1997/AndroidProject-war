package com.kursx.tactics;


import android.content.Context;

import java.util.ArrayList;

public class Point {

    protected int x, y;
    protected int liveCycle;
        private int health;
    private boolean isAlive = true;

    private Field field;



    Point(int x, int y, int health, int liveCucle, Field field) {
        this.x = x;
        this.y = y;
        startLive();
        this.health = health;
        this.liveCycle = liveCucle;
        this.field = field;
    }


    protected void startLive() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isAlive) {
                    try {
                        for (Point point : new ArrayList<Point>(field.getRedPoints())) {


                            if (point.getX() <= 0) {

                                field.GetActivity().gameOver();
                                isAlive = false;

                                field.removeRedPoints(point);
                                break;
                            }
                        }

                        for (Point point : new ArrayList<Point>(field.getBluePoints())) {


                            if (point.getX() <= 0) {

                                field.GetActivity().gameOver();
                                isAlive = false;

                                field.removeBluePoints(point);
                                break;
                            }
                        }

                        x--;
                        Thread.sleep(liveCycle);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();





    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHealth() {
        return health;
    }

    public void punch() {

        health-= 10;
    }
}
