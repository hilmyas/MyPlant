package com.assodikyhilmy.myplant;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

/**
 * Created by lenovo on 31/08/2017.
 */

public class Plant {
    private boolean isAlive;
    private int age;
    private boolean isHealthy;
    private int health_point;
    private String species;
    private Bitmap bitmapSeed;
    private Bitmap bitmapBud;
    private Bitmap bitmapBaby;
    private Bitmap bitmapKid;
    private Bitmap bitmapYoung;
    private Bitmap bitmapAdult;
    private Bitmap bitmapBloom;
    private Bitmap displayedBitmap;
    private int screenX;
    private int screenY;
    private int x;
    private int y;
    private int exp;
    private int[] maxExp={100,200,300,400,500,600,700,100};
    private Rect detectCollision;

    public Plant(Context context, String species, int screenX, int screenY){
        this.bitmapSeed = BitmapFactory.decodeResource(context.getResources(), R.drawable.seed);
        this.bitmapBud = BitmapFactory.decodeResource(context.getResources(), R.drawable.bud);
        this.bitmapBaby = BitmapFactory.decodeResource(context.getResources(), R.drawable.baby);
        this.bitmapKid = BitmapFactory.decodeResource(context.getResources(), R.drawable.kid);
        this.bitmapYoung = BitmapFactory.decodeResource(context.getResources(), R.drawable.young);
        this.bitmapAdult = BitmapFactory.decodeResource(context.getResources(), R.drawable.adult);
        this.bitmapBloom = BitmapFactory.decodeResource(context.getResources(), R.drawable.bloom);
        this.displayedBitmap = Bitmap.createScaledBitmap(bitmapSeed,bitmapSeed.getWidth()/4,bitmapSeed.getHeight()/4,false);
        this.screenX=screenX;
        this.screenY=screenY;
        this.x=this.screenX/2-displayedBitmap.getWidth()/2;
        this.y=this.screenY*3/4+displayedBitmap.getHeight();
        this.species=species;
        this.health_point=100;
        this.isAlive=true;
        this.isHealthy=true;
        this.age=0;
        this.exp=0;
        this.detectCollision =  new Rect(x, y, x+displayedBitmap.getWidth(), y+displayedBitmap.getHeight());
    }

    public void update(){
        exp++;

        if(age==0){
            if(exp>=maxExp[age]){
                age++;
                exp=0;
            }
            this.displayedBitmap = Bitmap.createScaledBitmap(bitmapSeed,bitmapSeed.getWidth()/4,bitmapSeed.getHeight()/4,false);
            this.x=this.screenX/2-displayedBitmap.getWidth()/2;
            this.y=this.screenY*3/4+displayedBitmap.getHeight();
        }
        else if(age==1){
            if(exp>=maxExp[age]){
                age++;
                exp=0;
            }
            this.displayedBitmap = Bitmap.createScaledBitmap(bitmapBud,bitmapBud.getWidth()/2,bitmapBud.getHeight()/2,false);
            this.x=this.screenX/2-displayedBitmap.getWidth()/2;
            this.y=this.screenY*3/4;
        }
        else if(age==2){
            if(exp>=maxExp[age]){
                age++;
                exp=0;
            }
            this.displayedBitmap = Bitmap.createScaledBitmap(bitmapBaby,bitmapBaby.getWidth()/2,bitmapBaby.getHeight()/2,false);
            this.x=this.screenX/2-displayedBitmap.getWidth()/2;
            this.y=this.screenY*3/4-displayedBitmap.getHeight()/2;
        }
        else if(age==3){
            if(exp>=maxExp[age]){
                age++;
                exp=0;
            }
            this.displayedBitmap = Bitmap.createScaledBitmap(bitmapKid,bitmapKid.getWidth()/2,bitmapKid.getHeight()/2,false);
            this.x=this.screenX/2-displayedBitmap.getWidth()/2;
            this.y=this.screenY*3/4-displayedBitmap.getHeight()/2;
        }
        else if(age==4){
            if(exp>=maxExp[age]){
                age++;
                exp=0;
            }
            this.displayedBitmap = Bitmap.createScaledBitmap(bitmapYoung,bitmapYoung.getWidth()/2,bitmapYoung.getHeight()/2,false);
            this.x=this.screenX/2-displayedBitmap.getWidth()/2;
            this.y=this.screenY*3/4-displayedBitmap.getHeight()/2;
        }
        else if(age==5){
            if(exp>=maxExp[age]){
                age++;
                exp=0;
            }
            this.displayedBitmap = Bitmap.createScaledBitmap(bitmapAdult,bitmapAdult.getWidth()/2,bitmapAdult.getHeight()/2,false);
            this.x=this.screenX/2-displayedBitmap.getWidth()/2;
            this.y=this.screenY*3/4-displayedBitmap.getHeight()/2;
        }
        else if(age==6){
            if(exp>=maxExp[age]){
                age++;
                exp=0;
            }
            this.displayedBitmap = Bitmap.createScaledBitmap(bitmapBloom,bitmapBloom.getWidth()/2,bitmapBloom.getHeight()/2,false);
            this.x=this.screenX/2-displayedBitmap.getWidth()/2;
            this.y=this.screenY*3/4-displayedBitmap.getHeight()/2;
        }

        //adding top, left, bottom and right to the rect object
        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + displayedBitmap.getWidth();
        detectCollision.bottom = y + displayedBitmap.getHeight();
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

    public int getExp(){return this.exp;}

    public int getMaxExp(){return this.maxExp[age];}

    public int getAge() {
        return age;
    }
}
