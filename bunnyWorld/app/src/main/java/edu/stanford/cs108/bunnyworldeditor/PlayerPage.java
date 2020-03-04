package edu.stanford.cs108.bunnyworldeditor;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

public class PlayerPage {
    private int pageId;
    private String pageName;
    private List<PlayerShape> playerShapes = new ArrayList<>();

    PlayerPage(int pageId, String pageName) {
        this.pageId = pageId;
        this.pageName = pageName;
    }

    public int getPageId() {
        return pageId;
    }

    public String getPageName() {
        return pageName;
    }

    public int getNumShapes() {
        return playerShapes.size();
    }

    public void addShapeToPage(PlayerShape playerShape) {
        playerShapes.add(playerShape);
    }

    public void removeShapeFromPage(PlayerShape playerShape) {
        playerShapes.remove(playerShape);
    }

    public PlayerShape selectShape(float positionX, float positionY) {
        for (PlayerShape playerShape : playerShapes) {
            if (playerShape.positionInShape(positionX, positionY)) {
                return playerShape;
            }
        }
        return null;
    }

    public List<PlayerShape> getShapesInPage() {
        return playerShapes;
    }

    public PlayerShape getShapeByName(String shapeName) {
        PlayerShape playerShape;
        for (int i = 0; i < playerShapes.size(); i++) {
            playerShape = playerShapes.get(i);
            if (playerShape.getShapeName().equals(shapeName)) {
                return playerShape;
            }
        }
        return null;
    }

    public void draw(Canvas canvas) {
        for (PlayerShape playerShape : playerShapes) {
            playerShape.draw(canvas);
        }
    }
}
