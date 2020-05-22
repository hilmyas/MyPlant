package com.assodikyhilmy.myplant;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by lenovo on 31/08/2017.
 */

public class Enemy {

    //bitmap for the enemy
    //we have already pasted the bitmap in the drawable folder
    private Bitmap displayedBitmap;
    private Bitmap bitmap;
    private Bitmap bitmap2;
    private boolean upState;
    private int delay;
    private int maxDelay=3; //frames

    //x and y coordinates
    private int x;
    private int y;

    //enemy speed
    private int speed;

    //min and max coordinates to keep the enemy inside the screen
    private int maxX;
    private int minX;

    private int maxY;
    private int minY;

    private boolean right;
    private boolean isAlive;

    //creating a rect object
    private Rect detectCollision;

    public Enemy(Context context, int screenX, int screenY) {
        //getting bitmap from drawable resource
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy);
        bitmap = Bitmap.createScaledBitmap(bitmap,72,72,false);
        bitmap2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy2);
        bitmap2 = Bitmap.createScaledBitmap(bitmap2,72,72,false);
        displayedBitmap = bitmap;

        //initializing min and max coordinates
        maxX = screenX - bitmap.getWidth();
        maxY = screenY*3/4 - bitmap.getHeight();
        minX = 0;
        minY = 0 - bitmap.getHeight();

        //generating a random coordinate to add enemy
        Random generator = new Random();
        speed = generator.nextInt(10) + 20;
        if(speed%2==0){
            right=true;
        }
        else{
            right=false;
        }
        x = generator.nextInt(maxX);
        y = minY;

        isAlive=true;

        //initializing rect object
        this.detectCollision = new Rect(x, y, x+displayedBitmap.getWidth(), y+displayedBitmap.getHeight());

        upState=true;
        delay= 0;
    }

    public void update() {
        if(upState){
            delay++;
            if(delay==maxDelay) {
                delay=0;
                upState = false;
                displayedBitmap = bitmap2;
            }
        }
        else{
            delay++;
            if(delay==maxDelay) {
                delay = 0;
                upState = true;
                displayedBitmap = bitmap;
            }
        }

        if(right) {
            x += speed;
            if(x>maxX){
                right=false;
                y+=speed*5;
            }
        }else{
            x -= speed;
            if(x<minX){
                right=true;
                y+=speed*5;
            }
        }
        //if the enemy reaches the left edge
        if (y > maxY || !isAlive) {
            //adding the enemy again to the right edge
            isAlive=true;
            Random generator = new Random();
            speed = generator.nextInt(10) + 20;
            if(speed%2==0){
                right=true;
            }
            else{
                right=false;
            }
            x = generator.nextInt(maxX);
            y = minY;
        }

        //Adding the top, left, bottom and right to the rect object
        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + displayedBitmap.getWidth();
        detectCollision.bottom = y + displayedBitmap.getHeight();
    }

    //adding a setter to x coordinate so that we can change it after collision
    public void setAlive(boolean isAlive){
        this.isAlive = isAlive;
    }

    //one more getter for getting the rect object
    public Rect getDetectCollision() {
        return detectCollision;
    }

    //getters
    public Bitmap getBitmap() {
        return displayedBitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }
}
