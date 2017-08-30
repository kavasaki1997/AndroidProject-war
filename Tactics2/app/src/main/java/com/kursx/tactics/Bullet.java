package com.kursx.tactics;


import android.content.Context;
import android.content.Intent;
import android.util.ArraySet;
import android.util.Log;

import java.util.ArrayList;

public class Bullet extends Point {

    private Field field;

    private boolean isAlive = true;



    private int health;

    Bullet(int x, int y, int helth, Field field) {
        super(x, y, helth, 5,field);
        this.field = field;
        this.health=helth;

    }
    boolean flag = false;
    @Override
    public void startLive() {
        if (!flag) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    flag = true;
                    while (isAlive) {
                        try {


                            for (Point point : new ArrayList<Point>(field.getRedPoints())) {

                                double k = Math.sqrt((Math.pow(point.getY() - y, 2)) + (Math.pow(point.getX() - x, 2)));

                                if ((k < point.getHealth() + health)) {
                                    point.punch();
                                    if (point.getHealth() <= 0) {
                                        field.removeRedPoints(point);
                                    }
                                    field.removeBullet(Bullet.this);
                                    isAlive = false;
                                    break;
                                }
                                if (point.getX() <= 0) {

                                    field.GetActivity().gameOver();
                                    isAlive = false;
                                    break;
                                }


                            }

                            for (Point point : new ArrayList<Point>(field.getBluePoints())) {

                                double k = Math.sqrt((Math.pow(point.getY() - y, 2)) + (Math.pow(point.getX() - x, 2)));

                                if ((k < point.getHealth() + health)) {
                                    point.punch();
                                    if (point.getHealth() <= 0) {
                                        field.removeBluePoints(point);
                                    }
                                    field.removeBullet(Bullet.this);
                                    isAlive = false;
                                    break;
                                }
                                if (point.getX() <= 0) {

                                    field.GetActivity().gameOver();
                                    isAlive = false;
                                    break;
                                }
                            }
                            if (x >= field.getWdth()) {
                                field.removeBullet(Bullet.this);
                            }

                            for (Point point : new ArrayList<Point>(field.getBonus())) {

                                double k = Math.sqrt((Math.pow(point.getY() - y, 2)) + (Math.pow(point.getX() - x, 2)));

                                if ((k < point.getHealth() + 5)) {
                                    field.removeBullet(Bullet.this);
                                    field.removeBonus(point);
                                    health+=10;
                                    isAlive = false;
                                    break;
                                }
                                if (point.getX() <= 0) {

                                    field.removeBonus(point);
                                    break;
                                }
                            }

                            x++;


                            Thread.sleep(liveCycle);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }


            }).start();
        }
    }
}
