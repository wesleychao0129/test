package edu.stanford.cs108.bunnyworldeditor;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class Page {

    // get the database
    private SQLiteDatabase db = MainActivity.db;

    // Database field
    private String page_name;
    private long game_id;
    private long page_id;

    // internal data structure
    private ArrayList<Shape> shapes;


    /*
     * This constructor is for Editor, which whenever we create a new Page,
     * we want to store in the database. If ths given name is empty string,
     * set the name to "PAGE_ID".
     * --------------------------------
     * @ game_id : game this page belong to.
     * @ page_name : page name
     */
    public Page(long game_id, String page_name) {
        ContentValues values = new ContentValues();
        values.put("page_name", page_name);
        values.put("game_id", game_id);
        long ID = db.insert("Pages", null, values);

        // if name is empty, update database game_name = GAME_ID
        if (page_name.length() == 0) {
            page_name = "PAGE_" + ID;
            ContentValues update_value = new ContentValues();
            update_value.put("page_name", page_name);
            update_value.put("game_id", game_id);
            String selection = "page_id = ?";
            String[] selectionArgs = {Long.toString(ID)};
            // update() return the # of updated rows
            int res = db.update("Pages", update_value, selection, selectionArgs );

            String testDB = "SELECT * FROM Pages";
            Cursor cursor = db.rawQuery( testDB, null);
            String output = "";
            while (cursor.moveToNext()) {
                output += cursor.getString(0) + ", " + cursor.getString(1) + "\n";
            }
            Log.d("CREATION", output);
        }

        this.page_id = ID;
        this.game_id = game_id;
        this.page_name = page_name;
//        shapes = new HashMap<Long, Page>();
    }

    /*
    *  This constructor is for Play, or if you don't want to add the Page into database
    * */
    public Page(long game_id, long page_id, String page_name) {
        this.game_id = game_id;
        this.page_id = page_id;
        this.page_name = page_name;
    }

    // Thsi is old constructor
    public Page(ArrayList<Shape> shapes) {
        this.shapes = shapes;
    }

    public long getPageID() {
        return this.page_id;
    }

    public String getPageName() {
        return this.page_name;
    }

    public long getGameID() {
        return this.game_id;
    }

    public void drawPage(Canvas canvas) {
        for (Shape shape : shapes) {
            shape.draw(canvas);
        }
    }

    // get shapes
    public ArrayList<Shape> getShapes() {
        return shapes;
    }

    // add shape
    public void addShape(RectF dim, BitmapDrawable bd) {
        shapes.add(new Shape(dim, bd));
    }

    // remove shape
    public void removeShape() {

    }
    //
}
