package com.assodikyhilmy.myplant;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

/**
 * Created by lenovo on 31/08/2017.
 */

public class Button {
    private int type;
    private int x;
    private int y;
    private Bitmap bitmap;
    private Bitmap displayedBitmap;
    private Rect detectCollision;

    public Button(Context context, int type, int screenX, int screenY){
        if(type==1){
            this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.wateringbtn);
        }
        else{
            this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.fertilizingbtn);
        }

        this.displayedBitmap = Bitmap.createScaledBitmap(bitmap,bitmap.getWidth()/3,bitmap.getHeight()/3,false);
        this.y=screenY*4/5;
        if(type==1){
            this.x=screenX-50-displayedBitmap.getWidth();
        }
        else{
            this.x=50;
        }
        this.detectCollision =  new Rect(x, y, x+displayedBitmap.getWidth(), y+displayedBitmap.getHeight());
    }

    //one more getter for getting the rect object
    public Rect getDetectCollision() {
        return detectCollision;
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
