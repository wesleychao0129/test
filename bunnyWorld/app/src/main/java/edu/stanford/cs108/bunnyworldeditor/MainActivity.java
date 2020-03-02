package edu.stanford.cs108.bunnyworldeditor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    // crate db
    public static SQLiteDatabase db; // instance variable on Activity
    String createGameTableStr = "CREATE TABLE Games ("
            + "game_name TEXT, "
            + "game_id INTEGER PRIMARY KEY AUTOINCREMENT"
            + ");";
    String createPageTableStr = "CREATE TABLE Pages ("
            + "page_name TEXT, game_id INTEGER, "
            + "page_id INTEGER PRIMARY KEY AUTOINCREMENT"
            + ");";
    String createShapeTableStr = "CREATE TABLE Shapes ("
            + "shape_name TEXT, page_id INTEGER, game_id INTEGER, hidden BOOLEAN, movable BOOLEAN, script TEXT, bbox TEXT, "
            + "shape_id INTEGER PRIMARY KEY AUTOINCREMENT"
            + ");";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDatabase();
        initEditor();
    }

    // Initialize database here
    private void initDatabase() {
        // create db
        db = openOrCreateDatabase("DB",MODE_PRIVATE,null);
        // check if table Games is already exist
        Cursor tablesCursor1 = db.rawQuery(
                "SELECT * FROM sqlite_master WHERE type='table' AND name='Games';", null);
        if (tablesCursor1.getCount() == 0) {
            db.execSQL(createGameTableStr);
        }
        // check if table Pages is already exist
        Cursor tablesCursor2 = db.rawQuery(
                "SELECT * FROM sqlite_master WHERE type='table' AND name='Pages';", null);
        if (tablesCursor2.getCount() == 0) {
            db.execSQL(createPageTableStr);
        }
        // check if table Shapes is already exist
        Cursor tablesCursor3 = db.rawQuery(
                "SELECT * FROM sqlite_master WHERE type='table' AND name='Shapes';", null);
        if (tablesCursor3.getCount() == 0) {
            db.execSQL(createShapeTableStr);
        }
    }

    // // Initialize editor here
    private void initEditor() {
        this.currentEditGameID = 0;
        this.gameMap = new HashMap<Long, Game>();
    }

    /* Editor part"
    *  ------------------------
    *  In the front page of the app, click "CREATE GAME" buttom to create a new game.
    *  Then the app will go into editGameActivity to edit "only the latest created game"
    *
    *  Public function for Editor part:
    *  1. getCurrentGameID()
    *  2. getGameBy()
    *  3. onCreateGame()
    * */

    // internal data structure
    static private long currentEditGameID;
    static private HashMap<Long, Game> gameMap;

    static public long getCurrentGameID() {
        return currentEditGameID;
    }

    static public Game getGame(long id) {
        return gameMap.get(id);
    }
    /* onCreateGame:
    *  ------------------
    *  Create a game by given a game_name, if not given, the name will be GAME_ID
    * */

    public void onCreateGame(View view) {

        LayoutInflater li = LayoutInflater.from(MainActivity.this);
        final View promptsView = li.inflate(R.layout.activity_create_game, null);
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("Create a new game");
        dialog.setView(promptsView);
        dialog.setNegativeButton("Create", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                EditText game_name = (EditText) promptsView.findViewById(R.id.game_name);
                String name = game_name.getText().toString();

                // create a new game
                Game newGame = new Game(name);
                long ID = newGame.getGameID();

                // update
                currentEditGameID = ID;
                gameMap.put(ID, newGame);

                // Go to editGameActivity
                // *** editGameActivity should only edit on currentGame ***
                Intent intent = new Intent(MainActivity.this, editGameActivity.class);
                startActivity(intent);
            }
        });
        dialog.show();
    }
}
