package edu.stanford.cs108.bunnyworldeditor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.app.Activity;
import java.util.ArrayList;
import java.util.HashMap;


public class editorView extends View {
    public editorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }
    // initialization and set up (load starter page or inventory)
    // on draw
    // detect touch motion
//    HashMap<String, ArrayList<Shape>> pageMap = new HashMap<String, ArrayList<Shape>>();

    ArrayList<Shape> shapes = new ArrayList<Shape>();
    ArrayList<Shape> curShapes = new ArrayList<Shape>();
    Page curPage;
    Shape chosenShape;
    Possession possession = new Possession();
    ArrayList<Shape> ITEMSHAPES = new ArrayList<Shape>();
    boolean movable = false;
    boolean addToCurrentPage = false;

    // set up (load and create shapes for possession and load and draw new page)
    private void init() {
        createItemShapes();
        possession.loadItemShapes(new ArrayList<Shape>(ITEMSHAPES));
        loadNewPage();


    }

    // create constant inventory shapes
    private void createItemShapes() {
        ITEMSHAPES.add(new Shape(new RectF(0,700, 200, 900),
                ((BitmapDrawable) getResources().getDrawable(R.drawable.carrot))));
        ITEMSHAPES.add(new Shape(new RectF(200,700, 400, 900),
                ((BitmapDrawable) getResources().getDrawable(R.drawable.carrot2))));
        ITEMSHAPES.add(new Shape(new RectF(400,700, 600, 900),
                ((BitmapDrawable) getResources().getDrawable(R.drawable.evilbunny))));
        ITEMSHAPES.add(new Shape(new RectF(600,700, 800, 900),
                ((BitmapDrawable) getResources().getDrawable(R.drawable.duck))));
        ITEMSHAPES.add(new Shape(new RectF(800,700, 1000, 900),
                ((BitmapDrawable) getResources().getDrawable(R.drawable.fire))));
        ITEMSHAPES.add(new Shape(new RectF(1000,700, 1200, 900),
                ((BitmapDrawable) getResources().getDrawable(R.drawable.mystic))));
    }


    private void loadNewPage() {
        curPage = new Page(new ArrayList<Shape>(Possession.ITEMSHAPES));
    }

    // override onDraw function
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        curPage.drawPage(canvas);
    }

    // set variable here instead of in the onTouchEvent function to ensure they won't disappear every time we call this function
    float x = 0, y = 0;
    float left_start = 0, right_start = 0, top_start = 0, bottom_start = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                movable = false;
                addToCurrentPage = false;
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
                        if (x >= 0 && x <= 1200 && y <= 900 && y >= 700 && i < Possession.itemsNum) {
                            // if is in inventory
                            chosenShape = new Shape(new RectF(curShapes.get(i).getDim()), curShapes.get(i).getBitmapDrawable());
                            chosenShape.getDim().bottom = chosenShape.getDim().bottom - 200;
                            chosenShape.getDim().top = chosenShape.getDim().top - 200;
                            addToCurrentPage = true;
                        } else { // not in inventory
                            chosenShape = curShapes.get(i);
                            left_start = curShapes.get(i).getDim().left;
                            right_start = curShapes.get(i).getDim().right;
                            top_start = curShapes.get(i).getDim().top;
                            bottom_start = curShapes.get(i).getDim().bottom;
                            movable = true;
                        }

                    }
                }
                if (addToCurrentPage && !movable) {
                    curShapes.add(chosenShape);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (movable) {
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
