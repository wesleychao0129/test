package edu.stanford.cs108.bunnyworldeditor;

import java.util.ArrayList;

public class Inventory {
    public static ArrayList<Shape> ITEMSHAPES = new ArrayList<Shape>();
    public static int itemsNum;

    public Inventory() {

    }
    public void loadItemShapes(ArrayList<Shape> shapes) {
        this.ITEMSHAPES = shapes;
        this.itemsNum = shapes.size();
        for (Shape shape : this.ITEMSHAPES) {
            shape.setMovable(false);
            shape.setIsInventory(true);
        }
    }


}
