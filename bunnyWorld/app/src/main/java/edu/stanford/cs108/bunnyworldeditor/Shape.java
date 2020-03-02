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
    // draw
    //

    public Shape(RectF dim, BitmapDrawable bitmapDrawable) {
        this.dim = dim;
        this.bitmapDrawable = bitmapDrawable;
        this.bitmap = bitmapDrawable.getBitmap();
    }

//    public Shape(RectF dim, Bitmap bitmap) {
//        this.dim = dim;
//        this.bitmap = bitmap;
//    }

    public String getName() {
        return name;
    }

    public RectF getDim() {
        return dim;
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

}
