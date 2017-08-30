package com.kursx.tactics;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Field extends View {

    private List<Point> redPoints = new ArrayList<>();
    private List<Point> bluePoints = new ArrayList<>();
    private List<Point> bullets = new ArrayList<>();
    private List<Point> bonus = new ArrayList<>();
    private int width;
    private int height;
    private MainActivity activity;
    private Paint paint = new Paint();
    private int liveCycleDop=0;
    Ship ship = new Ship(this);

    private boolean started = false;
    public void SetActivity(MainActivity activity) {
        this.activity=activity;
    }
    public MainActivity GetActivity() {
        return this.activity;
    }

    public void SetSize(int width,int height) {
        this.width = width;
        this.height = height;
    }

    public Field(Context context) {

        super(context);
        init();
    }

    public void moveShip(int y) {

        ship.setY(y);
    }
    public Field(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Field(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }
    boolean flag = false;
    public void start() {
        if(!flag) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    liveCycleDop-=3;
                }
            }, 5000, 5000);


            Timer timer1 = new Timer();
            timer1.schedule(new TimerTask() {
                @Override
                public void run() {
                    int by = new Random().nextInt(height - 200);
                    bonus.add(new Point(width, by, 40, 30, Field.this));

                }
            }, 5000, 5000);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        int by = new Random().nextInt(height - 200);
                        redPoints.add(new Point(width, by, 30, 20 + liveCycleDop, Field.this));

                        try {
                            Thread.sleep(1700);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        int by = new Random().nextInt(height - 200);
                        bluePoints.add(new Point(width, by, 40, 30 + liveCycleDop, Field.this));
                        try {
                            Thread.sleep(3500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (!started) {
                        started = true;
                        while (true) {
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            post(new Runnable() {
                                public void run() {
                                    invalidate();
                                }
                            });
                        }
                    }
                }
            }).start();
            flag=true;
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        paint.setColor(Color.BLUE);
//        paint.setStyle(Paint.Style.FILL);
//        for (Point point : new ArrayList<>(bluePoints)) {
//            canvas.drawCircle(point.getX(), point.getY(), Util.dpToPx(10), paint);
//        }
        paint.setColor(Color.RED);
        for (Point point : new ArrayList<>(redPoints)) {
            canvas.drawCircle(point.getX(), point.getY(), Util.dpToPx(point.getHealth()), paint);
        }
        paint.setColor(Color.BLUE);
        for (Point point : new ArrayList<>(bluePoints)) {
            canvas.drawCircle(point.getX(), point.getY(), Util.dpToPx(point.getHealth()), paint);
        }


        paint.setColor(Color.GREEN);
        for (Point point : new ArrayList<>(bonus)) {
            canvas.drawCircle(point.getX(), point.getY(), Util.dpToPx(point.getHealth()), paint);
        }

        paint.setColor(Color.BLACK);
        canvas.drawCircle(ship.getX(), ship.getY(), Util.dpToPx(ship.getHealth()), paint);

        paint.setColor(Color.YELLOW);
        for (Point point : new ArrayList<>(bullets)) {
            canvas.drawCircle(point.getX(), point.getY(), Util.dpToPx(point.getHealth()), paint);
        }


    }

    public void removeRedPoints(Point point){
        if (redPoints.contains(point)) redPoints.remove(point);
    }
    public void removeBonus(Point point){
        if (bonus.contains(point)) bonus.remove(point);
    }
    public void removeBluePoints(Point point){
        if (bluePoints.contains(point)) bluePoints.remove(point);
    }

    public void removeBullet(Bullet bullet){
        if (bullets.contains(bullet)) bullets.remove(bullet);
    }

    public void fire(){
        Bullet bullet=new Bullet(ship.getX(),ship.getY(),7, this);
        bullets.add(bullet);
    }


    public List<Point> getRedPoints() {
        return new ArrayList<>(redPoints);
    }

    public void setRedPoints(List<Point> redPoints) {

        this.redPoints = redPoints;
    }



    public int getHeght() {

        return height;
    }

    public void setHeight(int height) {

        this.height = height;
    }
    public List<Point> getBullets() {

        return bullets;
    }

    public int getWdth() {

        return width;
    }

    public void setWidth(int width) {

        this.width = width;
    }
    public void setBullets(List<Point> bullets) {

        this.bullets = bullets;
    }

    public List<Point> getBluePoints() {
        return bluePoints;
    }

    public void setBluePoints(List<Point> bluePoints) {
        this.bluePoints = bluePoints;
    }
    public List<Point> getBonus() {
        return bonus;
    }
}
