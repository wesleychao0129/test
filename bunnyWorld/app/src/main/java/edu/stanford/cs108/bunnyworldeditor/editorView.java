package edu.stanford.cs108.bunnyworldeditor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;


public class editorView extends View {
    public editorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }
    // initialization and set up (load starter page or inventory)
    // on draw
    // detect touch motion
//    HashMap<String, ArrayList<Shape>> pageMap = new HashMap<String, ArrayList<Shape>>();

    ArrayList<Shape> curShapes = new ArrayList<Shape>();
    Game curGame;
    Page curPage;
    Shape chosenShape;

    Inventory inventory = new Inventory();
    ArrayList<Shape> ITEMSHAPES = new ArrayList<Shape>();


    // set up (load and create shapes for possession and load and draw new page)
    private void init() {
        System.out.println("FUCKKK");
        createItemShapes();
        inventory.loadItemShapes(new ArrayList<Shape>(ITEMSHAPES));
        long ID = MainActivity.getCurrentGameID();
        curGame = MainActivity.getGame(ID);
        if (curGame.getCurrentPage() == null) {
            Page newPage = new Page(ID, "");
            curGame.addPage(newPage);
        }

        //loadNewPage();



        // curPage = curGame.getCurrentPage();


    }

    // create constant inventory shapes
    private void createItemShapes() {
        ITEMSHAPES.add(new Shape(new RectF(0,700, 200, 900),
                (BitmapDrawable) getResources().getDrawable(R.drawable.carrot), "Carrot"));
        ITEMSHAPES.add(new Shape(new RectF(200,700, 400, 900),
                (BitmapDrawable) getResources().getDrawable(R.drawable.carrot2), "Carrot2"));
        ITEMSHAPES.add(new Shape(new RectF(400,700, 600, 900),
                (BitmapDrawable) getResources().getDrawable(R.drawable.evilbunny), "Evil Bunny"));
        ITEMSHAPES.add(new Shape(new RectF(600,700, 800, 900),
                (BitmapDrawable) getResources().getDrawable(R.drawable.duck), "Duck"));
        ITEMSHAPES.add(new Shape(new RectF(800,700, 1000, 900),
                (BitmapDrawable) getResources().getDrawable(R.drawable.fire), "Fire"));
        ITEMSHAPES.add(new Shape(new RectF(1000,700, 1200, 900),
                (BitmapDrawable) getResources().getDrawable(R.drawable.mystic), "Mystic Bunny"));
    }

//    public static void loadNewPage() {
//        curPage = new Page(new ArrayList<Shape>(Inventory.ITEMSHAPES));
//    }

    // override onDraw function
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        curPage = curGame.getCurrentPage();
        curPage.drawPage(canvas);
    }

    // set variable here instead of in the onTouchEvent function to ensure they won't disappear every time we call this function
    float x = 0, y = 0;
    float left_start = 0, right_start = 0, top_start = 0, bottom_start = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                x = event.getX();
                y = event.getY();

                chosenShape = null;
                curShapes = curPage.getShapes();
                for (int i = 0; i < curShapes.size(); i++) {
                    float left = curShapes.get(i).getDim().left;
                    float right = curShapes.get(i).getDim().right;
                    float top = curShapes.get(i).getDim().top;
                    float bottom = curShapes.get(i).getDim().bottom;
                    if (x <= right && x >= left && y >= top && y <= bottom) {
                        chosenShape = curShapes.get(i);
                    }
                }
                if (chosenShape != null) {
                    if (chosenShape.getIsInventory()) {
                        chosenShape = new Shape(new RectF(chosenShape.getDim()), chosenShape.getBitmapDrawable(), chosenShape.getImageName());
                        chosenShape.getDim().bottom = chosenShape.getDim().bottom - 200;
                        chosenShape.getDim().top = chosenShape.getDim().top - 200;
                        chosenShape.setName("Shape" + String.valueOf(curShapes.size() - Inventory.ITEMSHAPES.size() + 1));
                        curShapes.add(chosenShape);
                        invalidate();
                    } else {
                        left_start = chosenShape.getDim().left;
                        right_start = chosenShape.getDim().right;
                        top_start = chosenShape.getDim().top;
                        bottom_start = chosenShape.getDim().bottom;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (chosenShape != null && chosenShape.getMovable()) {
                    float x_ = 0, y_ = 0, dx = 0, dy = 0;
                    x_ = event.getX();
                    y_ = event.getY();
                    dx = x_ - x;
                    dy = y_ - y;
                    chosenShape.getDim().bottom = bottom_start + dy;
                    chosenShape.getDim().top = top_start + dy;
                    chosenShape.getDim().left = left_start + dx;
                    chosenShape.getDim().right = right_start + dx;
                    invalidate();
                }
                break;


        }
        return true;
    }
}
