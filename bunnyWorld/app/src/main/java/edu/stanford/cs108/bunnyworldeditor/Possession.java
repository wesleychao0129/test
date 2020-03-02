package edu.stanford.cs108.bunnyworldeditor;

import java.util.ArrayList;

public class Possession {
    public static ArrayList<Shape> ITEMSHAPES = new ArrayList<Shape>();
    public static int itemsNum;

    public Possession() {

    }
    public void loadItemShapes(ArrayList<Shape> shapes) {
        this.ITEMSHAPES = shapes;
        this.itemsNum = shapes.size();
    }

}
