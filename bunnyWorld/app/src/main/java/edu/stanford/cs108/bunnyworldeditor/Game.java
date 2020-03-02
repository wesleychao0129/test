package edu.stanford.cs108.bunnyworldeditor;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class Game {

    // get the database
    private SQLiteDatabase db = MainActivity.db;

    // Database field
    private long game_id;
    private String game_name;

    // internal data structure
    private HashMap<Long, Page> pageMap;
//    private ArrayList<Page> pages;


    /*
     * This constructor is for Play, or if you don't want to add the Game into database
    */
    public Game(long ID, String name) {
        this.game_id = ID;
        this.game_name = name;
        pageMap = new HashMap<Long, Page>();
//        pages = new ArrayList<Page>();
    }

    /*
     * This constructor is for Editor, which whenever we create a new Game,
     * we want to store in the database
    */
    public Game(String name) {
        ContentValues values = new ContentValues();
        values.put("game_name", name);
        long ID = db.insert("Games", null, values);

        // if name is empty, update database game_name = GAME_ID
        if (name.length() == 0) {
            name = "GAME_" + ID;
            ContentValues update_value = new ContentValues();
            update_value.put("game_name", name);
            String selection = "game_id = ?";
            String[] selectionArgs = {Long.toString(ID)};
            int res = db.update("Games", update_value, selection, selectionArgs );


//            String testDB = "SELECT * FROM Games";
//            Cursor cursor = db.rawQuery( testDB, null);
//            String output = "";
//            while (cursor.moveToNext()) {
//                output += cursor.getString(0) + ", " + cursor.getString(1) + "\n";
//            }
//            Log.d("CREATION", output);
        }

        this.game_id = ID;
        this.game_name = name;
        pageMap = new HashMap<Long, Page>();
//        pages = new ArrayList<Page>();
    }

    public long getGameID() {
        return game_id;
    }

    public String getGameName() {
        return game_name;
    }

    public Page getPage(long ID) {
        return pageMap.get(ID);
    }

    public void addPage(Page page) {
        long ID = page.getPageID();
        pageMap.put(ID, page);
    }

    public void removePage(long ID){

    }

    public void updatePage(long ID) {

    }
}
