package com.assodikyhilmy.myplant;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;

/**
 * Created by lenovo on 31/08/2017.
 */

public class Soil {

    private int x;
    private int y;
    private int humidity;
    private int fertility;
    private int decreaseDelay=3;
    private int humDelay=0;
    private int ferDelay=0;
    private Bitmap bitmap;
    private Bitmap scaledBitmap;
    private Bitmap displayedBitmap;

    public Soil(Context context, int screenX, int screenY){
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.soil);
        this.scaledBitmap = Bitmap.createScaledBitmap(bitmap,screenX,screenY/4,false);
        this.displayedBitmap = Bitmap.createScaledBitmap(bitmap,screenX,screenY/4,false);

        this.x=0;
        this.y=screenY*3/4;
        this.humidity=60;
        this.fertility=60;
    }

    public void update(boolean isWatering,boolean isFertilizing){
        if(isWatering) {
            if (humidity < 100) {
                humidity++;
            }
        }
        else{
            if (humidity > 0) {
                if(humDelay<decreaseDelay){
                    humDelay++;
                }
                else{
                    humidity--;
                    humDelay=0;
                }

            }
        }

        if(isFertilizing) {
            if (fertility < 100) {
                fertility++;
            }
        }
        else{
            if (fertility > 0) {
                if(ferDelay<decreaseDelay){
                    ferDelay++;
                }
                else{
                    fertility--;
                    ferDelay=0;
                }
            }
        }
    }

    public int getHumidity(){
        return this.humidity;
    }

    public int getFertility() {
        return this.fertility;
    }

    public Bitmap getBitmap() {
        return displayedBitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
