package edu.stanford.cs108.bunnyworldeditor;

import android.graphics.Canvas;

import java.util.ArrayList;

public class Page {
    private ArrayList<Shape> shapes;

    public Page(ArrayList<Shape> shapes) {
        this.shapes = shapes;
    }

    public void drawPage(Canvas canvas) {
        for (Shape shape : shapes) {
            shape.draw(canvas);
        }
    }
    public ArrayList<Shape> getShapes() {
        return shapes;
    }
    // get shapes

    // add shape

    // remove shape

    //
}
