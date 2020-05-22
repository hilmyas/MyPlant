package com.assodikyhilmy.myplant;

import java.util.Random;

/**
 * Created by lenovo on 31/08/2017.
 */

public class Water {
    private int x;
    private int y;
    private int speed;

    private int maxX;
    private int maxY;
    private int minX;
    private int minY;

    private int screenY;

    public Water(int screenX, int screenY) {
        this.screenY=screenY;
        maxX = screenX;
        maxY = screenY*5/6;
        minX = 0;
        minY = 0;
        Random generator = new Random();
        speed = generator.nextInt(10)+20;

        //generating a random coordinate
        //but keeping the coordinate inside the screen size
        x = generator.nextInt(maxX/2)+maxX/4;
        y = generator.nextInt(maxY)-maxY;
    }

    public void update(boolean isWatering) {
        //animating the star horizontally left side
        //by decreasing x coordinate with player speed
        if(isWatering) {
            y += speed;
        }
        else{
            if(y>=0&&y<=maxY){
                y += speed;
            }
        }
        //if the star reached the left edge of the screen
        if (y > maxY) {
            //again starting the star from right edge
            //this will give a infinite scrolling background effect

            Random generator = new Random();
            y = generator.nextInt(maxY)-maxY;
            x = generator.nextInt(maxX/2)+maxX/4;
            speed = generator.nextInt(10)+20;
        }
    }

    public float getWaterWidth() {
        //Making the star width random so that
        //it will give a real look
        float minX = 5.0f;
        float maxX = 7.0f;
        Random rand = new Random();
        float finalX = rand.nextFloat() * (maxX - minX) + minX;
        return finalX;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
