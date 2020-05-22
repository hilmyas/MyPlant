package com.assodikyhilmy.myplant;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by lenovo on 31/08/2017.
 */

public class GameView extends SurfaceView implements Runnable {

    volatile boolean playing;
    boolean isGameOver;
    private Thread gameThread = null;

    //These objects will be used for drawing
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    private int maxFer=90;
    private int minFer=80;
    private int maxHum=70;
    private int minHum=60;

    private Plant plant1;
    private Soil soil1;

    private int enemyCount = 3;
    private int displayedEnemyCount = 1;
    private Enemy[] enemies;

    private boolean isWatering;
    private boolean isFertilizing;
    private ArrayList<Water> waters = new
            ArrayList();
    private ArrayList<Water> soils = new
            ArrayList();

    private Button wateringBtn;
    private Button fertilizingBtn;

    private int screenX;
    private int screenY;

    //Shared Prefernces to store the High Scores
    SharedPreferences sharedPreferences;

    public GameView(Context context, int screenX, int screenY) {
        super(context);

        sharedPreferences = context.getSharedPreferences("TOP SCORES", Context.MODE_PRIVATE);

        this.isGameOver=false;
        this.screenX=screenX;
        this.screenY=screenY;

        plant1=new Plant(context,"plantA",screenX,screenY);
        soil1=new Soil(context,screenX,screenY);

        isWatering=false;
        isFertilizing=false;
        //adding 100 waters you may increase the number
        int waterNum = 100;
        for (int i = 0; i < waterNum; i++) {
            Water s  = new Water(screenX, screenY);
            waters.add(s);
        }
        for (int i = 0; i < waterNum; i++) {
            Water s  = new Water(screenX, screenY);
            soils.add(s);
        }

        enemies = new Enemy[enemyCount];
        for (int i = 0; i < enemyCount; i++) {
            enemies[i] = new Enemy(context, screenX, screenY);
        }

        wateringBtn=new Button(context,1,screenX,screenY);
        fertilizingBtn=new Button(context,2,screenX,screenY);

        //initializing drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();
    }

    @Override
    public void run() {
        while (playing) {
            update();
            draw();
            control();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_UP:
                if(motionEvent.getY()<wateringBtn.getDetectCollision().bottom&&motionEvent.getY()>wateringBtn.getDetectCollision().top&&motionEvent.getX()<wateringBtn.getDetectCollision().right&&motionEvent.getX()>wateringBtn.getDetectCollision().left){
                    this.isWatering = false;
                }
                else if(motionEvent.getY()<fertilizingBtn.getDetectCollision().bottom&&motionEvent.getY()>fertilizingBtn.getDetectCollision().top&&motionEvent.getX()<fertilizingBtn.getDetectCollision().right&&motionEvent.getX()>fertilizingBtn.getDetectCollision().left){
                    this.isFertilizing = false;
                }
                break;
            case MotionEvent.ACTION_DOWN:
                if(motionEvent.getY()<wateringBtn.getDetectCollision().bottom&&motionEvent.getY()>wateringBtn.getDetectCollision().top&&motionEvent.getX()<wateringBtn.getDetectCollision().right&&motionEvent.getX()>wateringBtn.getDetectCollision().left){
                    this.isWatering = true;
                }
                else if(motionEvent.getY()<fertilizingBtn.getDetectCollision().bottom&&motionEvent.getY()>fertilizingBtn.getDetectCollision().top&&motionEvent.getX()<fertilizingBtn.getDetectCollision().right&&motionEvent.getX()>fertilizingBtn.getDetectCollision().left){
                    this.isFertilizing = true;
                }
                else{
                    for(int i=0;i<displayedEnemyCount;i++) {
                        if (motionEvent.getY() < enemies[i].getDetectCollision().bottom && motionEvent.getY() > enemies[i].getDetectCollision().top && motionEvent.getX() < enemies[i].getDetectCollision().right && motionEvent.getX() > enemies[i].getDetectCollision().left) {
                            enemies[i].setAlive(false);
                        }
                    }
                }
                break;
        }
        return true;
    }

    private void update() {

        soil1.update(this.isWatering,this.isFertilizing);
        if(soil1.getHumidity()==0||soil1.getFertility()==0||soil1.getHumidity()==100){
            isGameOver=true;
            playing=false;
        }

        if(plant1.getAge()>2) {
            for (int i = 0; i < displayedEnemyCount; i++) {
                enemies[i].update();
                if (Rect.intersects(enemies[i].getDetectCollision(), plant1.getDetectCollision())) {
                    isGameOver = true;
                    playing = false;
                }
            }
        }

        for (Water s : waters) {
            s.update(isWatering);
        }

        for (Water s : soils) {
            s.update(isFertilizing);
        }

        if(soil1.getFertility()>=minFer&&soil1.getFertility()<=maxFer&&soil1.getHumidity()>=minHum&&soil1.getHumidity()<=maxHum) {
            plant1.update();
            if(plant1.getAge()==4&&displayedEnemyCount<2){
                displayedEnemyCount=2;
            }
            else if(plant1.getAge()==5&&displayedEnemyCount<3){
                displayedEnemyCount=3;
            }
            if (plant1.getAge() > 6) {
                isGameOver = true;
                playing = false;
            }
        }

    }

    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.rgb(150, 200, 255));

            canvas.drawBitmap(
                    plant1.getBitmap(),
                    plant1.getX(),
                    plant1.getY(),
                    paint
            );

            canvas.drawBitmap(
                    soil1.getBitmap(),
                    soil1.getX(),
                    soil1.getY(),
                    paint
            );

            for (int i = 0; i < displayedEnemyCount; i++) {
                canvas.drawBitmap(
                        enemies[i].getBitmap(),
                        enemies[i].getX(),
                        enemies[i].getY(),
                        paint
                );
            }

            paint.setColor(Color.WHITE);
            for (Water s : waters) {
                paint.setStrokeWidth(s.getWaterWidth());
                canvas.drawPoint(s.getX(), s.getY(), paint);
            }

            paint.setColor(Color.rgb(150,100,50));
            for (Water s : soils) {
                paint.setStrokeWidth(s.getWaterWidth());
                canvas.drawPoint(s.getX(), s.getY(), paint);
            }

            paint.setColor(Color.WHITE);
            paint.setTextSize(30);
            canvas.drawText("Good: "+minHum+"-"+maxHum,screenX*3/4,200,paint);
            canvas.drawText("Good: "+minFer+"-"+maxFer,25,200,paint);
            paint.setTextSize(50);
            canvas.drawText("Age "+plant1.getAge(),screenX-165,screenY/3-50,paint);
            paint.setTextSize(30);
            canvas.drawText("Exp ",screenX-90,screenY/3-5,paint);
            canvas.drawRect(screenX-90,screenY/3+5,screenX-40,screenY*2/3+5,paint);
            paint.setColor(Color.GREEN);
            canvas.drawRect(screenX-85,screenY*2/3-((screenY*2/3-screenY/3)*plant1.getExp()/plant1.getMaxExp()),screenX-45,screenY*2/3,paint);

            canvas.drawBitmap(
                    wateringBtn.getBitmap(),
                    wateringBtn.getX(),
                    wateringBtn.getY(),
                    paint
            );

            canvas.drawBitmap(
                    fertilizingBtn.getBitmap(),
                    fertilizingBtn.getX(),
                    fertilizingBtn.getY(),
                    paint
            );

            paint.setColor(Color.rgb(150,100,50));
            paint.setTextSize(30);
            canvas.drawText("Humidity: ",screenX*3/4,50,paint);
            paint.setTextSize(100);
            if(this.soil1.getHumidity()<minHum){
                paint.setColor(Color.BLUE);
            }
            else if(this.soil1.getHumidity()>maxHum){
                paint.setColor(Color.RED);
            }
            else{
                paint.setColor(Color.WHITE);
            }
            canvas.drawText(""+this.soil1.getHumidity(),screenX*3/4,150,paint);

            paint.setTextSize(30);
            paint.setColor(Color.rgb(150,100,50));
            canvas.drawText("Fertility: ",25,50,paint);
            paint.setTextSize(100);
            if(this.soil1.getFertility()<minFer){
                paint.setColor(Color.BLUE);
            }
            else if(this.soil1.getFertility()>maxFer){
                paint.setColor(Color.RED);
            }
            else{
                paint.setColor(Color.WHITE);
            }
            canvas.drawText(""+this.soil1.getFertility(),25,150,paint);

            //draw game Over when the game is over
            if (isGameOver) {
                paint.setColor(Color.WHITE);
                paint.setTextSize(100);
                paint.setTextAlign(Paint.Align.CENTER);

                int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));
                canvas.drawText("Game Over", canvas.getWidth() / 2, yPos, paint);
            }

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control() {
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
        }
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
}