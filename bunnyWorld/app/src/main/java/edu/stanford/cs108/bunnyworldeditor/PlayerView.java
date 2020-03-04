package edu.stanford.cs108.bunnyworldeditor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayerView extends View {

    protected float clickPositionX;
    protected float clickPositionY;
    protected float moveFromPositionX;
    protected float moveFromPositionY;
    protected PlayerPage displayPlayerPage;
    protected PlayerShape selectedPlayerShape;
    protected PlayerShape dropToPlayerShape;
    protected PlayerInventory playerInventory = new PlayerInventory();
    protected final RectF rectangleShape = new RectF(500, 500, 600, 600);

    Map<String, BitmapDrawable> drawables = new HashMap<>();
    Map<String, PlayerPage> pagesInGame = new HashMap<>();
    Map<String, Integer> sounds = new HashMap<>();

    public PlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    // initialize view
    private void init() {
        createDrawables();
        createSounds();
        loadDefaultPages();
        onEnterScreen();
    }

    public void loadDefaultPages() {
        // page 1
        PlayerPage playerPage1 = new PlayerPage(1, "start");
        PlayerShape page1PlayerShape1 = new PlayerShape(1, "shape1", false,
                "rectangle", new RectF(500, 500, 600, 600),
                true, "on click goto playerPage2");
        PlayerShape page1PlayerShape2 = new PlayerShape(2, "door2", false,
                "rectangle", new RectF(800, 500, 900, 600),
                false, "on click goto playerPage3");
        PlayerShape page1PlayerShape3 = new PlayerShape(3, "shape3", false,
                "rectangle", new RectF(1100, 500, 1200, 600),
                true, "on click goto playerPage4");
        PlayerShape page1PlayerShape4 = new PlayerShape(4, "shape4", false,
                "text", new RectF(500, 100, 1200, 300),
                true, "", "Bunny World!", 100);
        PlayerShape page1PlayerShape5 = new PlayerShape(5, "shape5", false,
                "text", new RectF(200, 300, 1500, 500),
                true, "", "You are in a maze of twisty little passages, all alike",
                60);

        playerPage1.addShapeToPage(page1PlayerShape1);
        playerPage1.addShapeToPage(page1PlayerShape2);
        playerPage1.addShapeToPage(page1PlayerShape3);
        playerPage1.addShapeToPage(page1PlayerShape4);
        playerPage1.addShapeToPage(page1PlayerShape5);
        // page 2
        PlayerPage playerPage2 = new PlayerPage(2, "playerPage2");
        PlayerShape page2PlayerShape1 = new PlayerShape(6, "shape6", false,
                "image", new RectF(500, 100, 700, 300),
                true,
                "on click hide carrot play hooray; on enter show door2;",
                drawables.get("mystic"));
        PlayerShape page2PlayerShape2 = new PlayerShape(7, "shape7", false,
                "text", new RectF(200, 300, 1500, 500),
                true, "", "Mystic Bunny - Rub my tummy for a big surprise!",
                60);
        PlayerShape page2PlayerShape3 = new PlayerShape(8, "shape8", false,
                "rectangle", new RectF(500, 500, 600, 600),
                true, "on click goto start");

        playerPage2.addShapeToPage(page2PlayerShape1);
        playerPage2.addShapeToPage(page2PlayerShape2);
        playerPage2.addShapeToPage(page2PlayerShape3);

        // page 3
        PlayerPage playerPage3 = new PlayerPage(3, "playerPage3");
        PlayerShape page3PlayerShape1 = new PlayerShape(9, "shape9", false, "rectangle",
                new RectF(700, 500, 800, 600),
                true, "on click goto playerPage2");
        PlayerShape page3PlayerShape2 = new PlayerShape(10, "shape10", false,
                "text", new RectF(200, 300, 1500, 500),
                true, "", "Eek! Fire-Room. Run away!",
                60);
        PlayerShape page3PlayerShape3 = new PlayerShape(11, "carrot", true, "image",
                new RectF(900, 100, 1100, 300),
                true, "", drawables.get("carrot"));
        playerPage3.addShapeToPage(page3PlayerShape1);
        playerPage3.addShapeToPage(page3PlayerShape2);
        playerPage3.addShapeToPage(page3PlayerShape3);

        // page 4
        PlayerPage playerPage4 = new PlayerPage(4, "playerPage4");
        PlayerShape page4PlayerShape1 = new PlayerShape(12, "evilbunny", false, "image",
                new RectF(900, 100, 1100, 300),
                true, "on enter play evillaugh; on drop carrot hide carrot hide evilbunny show exit; on click play evillaugh;",
                drawables.get("evilbunny"));
        PlayerShape page4PlayerShape2 = new PlayerShape(13, "shape11", false, "text",
                new RectF(200, 300, 1500, 500), true,
                "", "You must appease the Bunny of Death!", 60);
        PlayerShape page4exit = new PlayerShape(14, "exit", false, "rectangle",
                new RectF(700, 500, 800, 600),
                false, "on click goto victoryPage");

        playerPage4.addShapeToPage(page4PlayerShape1);
        playerPage4.addShapeToPage(page4PlayerShape2);
        playerPage4.addShapeToPage(page4exit);


        // page 5
        PlayerPage playerPage5 = new PlayerPage(5, "victoryPage");
        PlayerShape page5PlayerShape1 = new PlayerShape(15, "carrot1", true, "image",
                new RectF(900, 100, 1100, 300),
                true, "", drawables.get("carrot"));
        PlayerShape page5PlayerShape2 = new PlayerShape(15, "carrot2", true, "image",
                new RectF(200, 100, 400, 300),
                true, "", drawables.get("carrot"));
        PlayerShape page5PlayerShape3 = new PlayerShape(15, "carrot3", true, "image",
                new RectF(600, 100, 800, 300),
                true, "", drawables.get("carrot"));
        PlayerShape page5PlayerShape4 = new PlayerShape(10, "shape12", false,
                "text", new RectF(200, 300, 1500, 500),
                true, "", "You win!",
                60);

        playerPage5.addShapeToPage(page5PlayerShape1);
        playerPage5.addShapeToPage(page5PlayerShape2);
        playerPage5.addShapeToPage(page5PlayerShape3);
        playerPage5.addShapeToPage(page5PlayerShape4);

        // Add pages to page map
        pagesInGame.put(playerPage1.getPageName(), playerPage1);
        pagesInGame.put(playerPage2.getPageName(), playerPage2);
        pagesInGame.put(playerPage3.getPageName(), playerPage3);
        pagesInGame.put(playerPage4.getPageName(), playerPage4);
        pagesInGame.put(playerPage5.getPageName(), playerPage5);

        // Choose current display page
        displayPlayerPage = playerPage1;
    }

    private void performOnEnterOnShape(PlayerShape playerShape) {
        if (playerShape.hasScript()) {
            Map<String, ArrayList<Instruction.Action>> instructionMapping;
            instructionMapping = playerShape.getInstructions().getMapping();
            if (instructionMapping != null && instructionMapping.containsKey(Instruction.ON_ENTER)) {
                for (Instruction.Action action : instructionMapping.get(Instruction.ON_ENTER)) {
                    onActionDo(action);
                }
            }
        }
    }

    private void performOnClickOnShape(PlayerShape playerShape) {
        if (playerShape.hasScript() && playerShape.isShapeVisible()) {
            Map<String, ArrayList<Instruction.Action>> instructionMapping;
            instructionMapping = playerShape.getInstructions().getMapping();
            if (instructionMapping != null && instructionMapping.containsKey(Instruction.ON_CLICK)) {
                for (Instruction.Action action : instructionMapping.get(Instruction.ON_CLICK)) {
                    onActionDo(action);
                }
            }
        }
    }

    private PlayerShape shapeIsOnDrop(float x, float y) {
        for (PlayerShape playerShape : displayPlayerPage.getShapesInPage()) {
            if (playerShape != selectedPlayerShape) {
                if (playerShape.positionInShape(x, y)) {
                    return playerShape;
                }
            }
        }
        return null;
    }

    private void performOnDropIfDropOnAnotherShape(float x, float y) {
        dropToPlayerShape = shapeIsOnDrop(x, y);
        if (dropToPlayerShape != null) {
            Instruction instructions = dropToPlayerShape.getInstructions();
            Map<String, ArrayList<Instruction.Action>> instructionMapping =
                    instructions.getMapping();
            if (instructions != null && instructionMapping.containsKey(Instruction.ON_DROP) &&
                    instructions.onDropWithShape.equals(selectedPlayerShape.getShapeName())) {
                for (Instruction.Action action : instructionMapping.get(Instruction.ON_DROP)) {
                    onActionDo(action);
                }
            }
        }
        dropToPlayerShape = null;
    }

    private void onDropToShape(float x, float y) {
        if (selectedPlayerShape != null) {
            performOnDropIfDropOnAnotherShape(x, y);
        }
    }

    private void onEnterScreen() {
        for (PlayerShape playerShape : displayPlayerPage.getShapesInPage()) {
            performOnEnterOnShape(playerShape);
        }
    }

    private void onClickShape() {
        if (selectedPlayerShape != null && selectedPlayerShape.hasScript()) {
            performOnClickOnShape(selectedPlayerShape);
        }
    }

    private void onActionDo(Instruction.Action action) {
        switch (action.actionType) {
            case Instruction.GOTO:
                if (action.pageName != null &&
                        pagesInGame.containsKey(action.pageName)) {
                    displayPlayerPage = pagesInGame.get(action.pageName);
                    selectedPlayerShape = null;
                    invalidate();
                    onEnterScreen();
                }
                break;
            case Instruction.PLAY:
                if (sounds.containsKey(action.soundName)) {
                    MediaPlayer mp = MediaPlayer.create(
                            getContext(), sounds.get(action.soundName));
                    mp.start();
                }
                break;
            case Instruction.HIDE:
                Log.d("action", "HIDE now!");
                if (selectedPlayerShape != null) {
                    Log.d("PlayerShape to hide in PlayerPage", selectedPlayerShape.getShapeName());
                    selectedPlayerShape.hide();
                    invalidate();
                    break;
                }
            case Instruction.SHOW:
                for (PlayerPage playerPage : pagesInGame.values()) {
                    PlayerShape playerShapeToShow = playerPage.getShapeByName(action.shapeName);
                    if (playerShapeToShow != null) {
                        playerShapeToShow.show();
                        invalidate();
                        break;
                    }
                }
        }
    }

    private void actionDown(MotionEvent event) {
        clickPositionX = event.getX();
        clickPositionY = event.getY();
        moveFromPositionX = clickPositionX;
        moveFromPositionY = clickPositionY;
        if (playerInventory.clickInInventory(clickPositionX, clickPositionY)) {
            actionDownInInventory(clickPositionX, clickPositionY);
        } else {
            actionDownNotInInventory(clickPositionX, clickPositionY);
        }
    }

    private void actionDownInInventory(float x, float y) {
        selectedPlayerShape = playerInventory.selectShape(x, y);
        if (selectedPlayerShape != null && selectedPlayerShape.isMovable()) {
            playerInventory.removeShape(selectedPlayerShape);
        }
    }

    private void actionDownNotInInventory(float x, float y) {
        selectedPlayerShape = displayPlayerPage.selectShape(x ,y);
        onClickShape();
        if (selectedPlayerShape != null && selectedPlayerShape.isMovable()) {
            displayPlayerPage.removeShapeFromPage(selectedPlayerShape);
        }
    }

    private void actionMove(MotionEvent event) {
        if (selectedPlayerShape != null && selectedPlayerShape.isMovable()) {
            selectedPlayerShape.shapeIsMoving = true;
            float newPositionX = event.getX();
            float newPositionY = event.getY();
            float positionChangeX = newPositionX - moveFromPositionX;
            float positionChangeY = newPositionY - moveFromPositionY;
            selectedPlayerShape.move(positionChangeX, positionChangeY);
            moveFromPositionX = newPositionX;
            moveFromPositionY = newPositionY;

            dropToPlayerShape = shapeIsOnDrop(newPositionX, newPositionY);
            if (dropToPlayerShape != null) {
                dropToPlayerShape.highlight = true;
            }

            invalidate();
        }
    }

    private void actionUp(MotionEvent event) {
        clickPositionX = event.getX();
        clickPositionY = event.getY();
        if (playerInventory.clickInInventory(clickPositionX, clickPositionY)) {
            actionUpInInventory(clickPositionX, clickPositionY);
        } else {
            actionUpNotInInventory(clickPositionX, clickPositionY);
        }
    }

    private void actionUpInInventory(float x, float y) {
        if (selectedPlayerShape != null && selectedPlayerShape.isMovable() && selectedPlayerShape.shapeIsMoving) {
            playerInventory.addShape(selectedPlayerShape);
            selectedPlayerShape.shapeIsMoving = false;
            selectedPlayerShape = null;
            invalidate();
        }
    }

    private void actionUpNotInInventory(float x, float y) {
        if (selectedPlayerShape != null && selectedPlayerShape.shapeIsMoving) {
            onDropToShape(x, y);
            displayPlayerPage.addShapeToPage(selectedPlayerShape);
            selectedPlayerShape.shapeIsMoving = false;
            selectedPlayerShape = null;
            invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                actionDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                actionMove(event);
                break;
            case MotionEvent.ACTION_UP:
                actionUp(event);
                break;
        }
        return true;
    }

    public void createDrawables() {
        drawables.put("carrot", (BitmapDrawable) getResources().getDrawable(R.drawable.carrot));
        drawables.put("duck", (BitmapDrawable) getResources().getDrawable(R.drawable.duck));
        drawables.put("evilbunny", (BitmapDrawable) getResources().getDrawable(R.drawable.evilbunny));
        drawables.put("mystic", (BitmapDrawable) getResources().getDrawable(R.drawable.mystic));
    }

    public void createSounds() {
        sounds.put("evillaugh", R.raw.evillaugh);
        sounds.put("hooray", R.raw.hooray);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        displayPlayerPage.draw(canvas);
        playerInventory.draw(canvas);
//        if (selectedPlayerShape != null) {
//            selectedPlayerShape.draw(canvas);
//        }
    }
}
