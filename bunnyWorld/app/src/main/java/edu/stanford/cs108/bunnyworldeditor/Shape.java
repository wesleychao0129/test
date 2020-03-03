package edu.stanford.cs108.bunnyworldeditor;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.content.Context;

public class Shape {
    private String name;
    private RectF dim;
    private BitmapDrawable bitmapDrawable;
    private Bitmap bitmap;
    private boolean movable;
    private boolean hidden;
    private boolean isInventory;
    // draw
    //

    public Shape(RectF dim, BitmapDrawable bitmapDrawable) {
        this.dim = dim;
        this.bitmapDrawable = bitmapDrawable;
        this.bitmap = bitmapDrawable.getBitmap();
        this.movable = true;
        this.hidden = false;
        this.isInventory = false;
    }

    public String getName() {
        return name;
    }

    public RectF getDim() {
        return dim;
    }

    public boolean getIsInventory() {
        return isInventory;
    }

    public boolean getMovable() {
        return movable;
    }

    public Bitmap getBitmap() {
        return bitmapDrawable.getBitmap();
    }

    public BitmapDrawable getBitmapDrawable() {
        return bitmapDrawable;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, dim, null);
    }

    // set movable value for shape
    public void setMovable(boolean val) {
        movable = val;
    }

    // set hidden value for shape
    public void setHidden(boolean val) {
        hidden = val;
    }

    // set isInventory value for shape
    public void setIsInventory(boolean val) {
        isInventory = val;
    }


}
