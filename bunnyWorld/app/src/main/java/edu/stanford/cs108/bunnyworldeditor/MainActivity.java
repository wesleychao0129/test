package edu.stanford.cs108.bunnyworldeditor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // create db
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
        addSavedGamesOnSpinner();
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
        this.gameMapStr = new HashMap<String, Game>();
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
    static private HashMap<String, Game> gameMapStr;

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
                
                // use getGameName to avoid empty String
                gameMapStr.put(newGame.getGameName(), newGame);


                // Go to editGameActivity
                // *** editGameActivity should only edit on currentGame ***
                Intent intent = new Intent(MainActivity.this, editGameActivity.class);
                startActivity(intent);
                addSavedGamesOnSpinner();
            }
        });
        dialog.show();
    }

    public void onEditExistingGame(View view) {
        Spinner editGameSpinner = findViewById(R.id.edit_games_spinner);
        String gameName = "";
        if (editGameSpinner.getSelectedItem() != null) {
            gameName = editGameSpinner.getSelectedItem().toString();
            // edit existing game
            Game curGame = gameMapStr.get(gameName);
            currentEditGameID = curGame.getGameID();

            // Go to editGameActivity
            // *** editGameActivity should only edit on currentGame ***
            Intent intent = new Intent(MainActivity.this, editGameActivity.class);
            startActivity(intent);
        }
//        LayoutInflater li = LayoutInflater.from(MainActivity.this);
//        final View promptsView = li.inflate(R.layout.activity_edit_existing_game, null);
//        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
//
//        TextView game_name = (TextView) promptsView.findViewById(R.id.game_name);
//        Cursor gamesCursor = db.rawQuery(
//                "SELECT * FROM Games;", null);
//
//        int[] gameIdx = {R.id.game1, R.id.game2, R.id.game3, R.id.game4, R.id.game5};
//        int i = 0;
//        while (gamesCursor.moveToNext()) {
//            RadioButton curButton = promptsView.findViewById(gameIdx[i]);
//            curButton.setText(gamesCursor.getString(0));
//            i++;
//
//        }
//
//        dialog.setTitle("Edit Existing Games");
//        dialog.setView(promptsView);
//        dialog.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                RadioGroup gamesGroup = promptsView.findViewById(R.id.gamesGroup);
//                int chosenGame = gamesGroup.getCheckedRadioButtonId();
//                RadioButton chosenButton = null;
//                switch(chosenGame) {
//                    case R.id.game1:
//                        chosenButton = promptsView.findViewById(R.id.game1);
//                        break;
//                    case R.id.game2:
//                        chosenButton = promptsView.findViewById(R.id.game2);
//                        break;
//                    case R.id.game3:
//                        chosenButton = promptsView.findViewById(R.id.game3);
//                        break;
//                    case R.id.game4:
//                        chosenButton = promptsView.findViewById(R.id.game4);
//                        break;
//                    case R.id.game5:
//                        chosenButton = promptsView.findViewById(R.id.game5);
//                        break;
//                }
//                String name = chosenButton.getText().toString();
//
//                // edit existing game
//                Game curGame = gameMapStr.get(name);
//                currentEditGameID = curGame.getGameID();
//
//                // Go to editGameActivity
//                // *** editGameActivity should only edit on currentGame ***
//                Intent intent = new Intent(MainActivity.this, editGameActivity.class);
//                startActivity(intent);
//            }
//        });
//        dialog.show();
    }

    public void onResetDataBase(View view) {
        String dropGames = "DROP TABLE IF EXISTS Games;";
        String dropPages = "DROP TABLE IF EXISTS Pages;";
        String dropShapes = "DROP TABLE IF EXISTS Shapes;";
        db.execSQL(dropGames);
        db.execSQL(dropPages);
        db.execSQL(dropShapes);
        db.execSQL(createGameTableStr);
        db.execSQL(createPageTableStr);
        db.execSQL(createShapeTableStr);
        addSavedGamesOnSpinner();
        Toast toast = Toast.makeText(MainActivity.this,"DataBase Reset", Toast.LENGTH_SHORT);
        toast.show();
        // System.out.println("Done");
    }

    public void onBeginDefaultGame(View view) {
        Intent intent = new Intent(this, PlayerActivity.class);
        startActivity(intent);
    }

    public void onLoadSavedGames(View view) {
        Spinner savedGameSpinner = findViewById(R.id.saved_games_spinner);
        Toast toast = Toast.makeText(MainActivity.this,"Selected Game : "
                + savedGameSpinner.getSelectedItem(), Toast.LENGTH_SHORT);
        toast.show();

    }

    public void addSavedGamesOnSpinner() {
        Spinner savedGameSpinner = findViewById(R.id.saved_games_spinner);
        Spinner editGameSpinner = findViewById(R.id.edit_games_spinner);
        List<String> gameList = new ArrayList<>();
        Cursor gamesCursor = db.rawQuery(
                "SELECT * FROM Games;", null);
        while (gamesCursor.moveToNext()) {
            String gameName = gamesCursor.getString(0);
            gameList.add(gameName);
        }
        ArrayAdapter<String> gameListAdaptor = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, gameList);
        gameListAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        savedGameSpinner.setAdapter(gameListAdaptor);
        editGameSpinner.setAdapter(gameListAdaptor);
    }


}
