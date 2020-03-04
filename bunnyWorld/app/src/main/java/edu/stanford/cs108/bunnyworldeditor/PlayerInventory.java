package edu.stanford.cs108.bunnyworldeditor;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.List;

public class PlayerInventory {
    private List<PlayerShape> playerShapeList = new ArrayList<>();;
    private Paint inventoryPaint;
    private RectF inventoryDimensions = new RectF(0, 700, 2000, 1000);

    public PlayerInventory() {
        init();
    }

    private void init() {
        inventoryPaint = new Paint();
        inventoryPaint.setColor(Color.rgb(255, 255, 204));
        inventoryPaint.setStyle(Paint.Style.FILL);
    }

    public void addShape(PlayerShape playerShape) {
        playerShapeList.add(playerShape);
    }

    public void removeShape(PlayerShape playerShape) {
        playerShapeList.remove(playerShape);
    }

    public boolean shapeIsInInventory(PlayerShape playerShape) {
        for (PlayerShape inventoryPlayerShape : playerShapeList) {
            if (inventoryPlayerShape == playerShape) {
                return true;
            }
        }
        return false;
    }

    public PlayerShape getShapeByName(String shapeName) {
        PlayerShape playerShape;
        for (int i = 0; i < playerShapeList.size(); i++) {
            playerShape = playerShapeList.get(i);
            if (playerShape.getShapeName().equals(shapeName)) {
                return playerShape;
            }
        }
        return null;
    }

    public boolean clickInInventory(float clickPositionX, float clickPositionY) {
        if (clickPositionX <= inventoryDimensions.right &&
                clickPositionX >= inventoryDimensions.left &&
                clickPositionY >= inventoryDimensions.top &&
                clickPositionY <= inventoryDimensions.bottom) {
            return true;
        }
        return false;
    }

    public PlayerShape selectShape(float x, float y) {
        for (PlayerShape playerShape : playerShapeList) {
            if (playerShape.positionInShape(x, y)) return playerShape;
        }
        return null;
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(inventoryDimensions, inventoryPaint);
        for (PlayerShape playerShape : playerShapeList) {
            playerShape.draw(canvas);
        }
    }

}
