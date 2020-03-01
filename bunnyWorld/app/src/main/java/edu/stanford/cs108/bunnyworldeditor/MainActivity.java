package edu.stanford.cs108.bunnyworldeditor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    // crate db
    SQLiteDatabase db; // instance variable on Activity
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

    String loadDataStr = "INSERT INTO cities VALUES "
            + "('Cairo','Africa',15200000, NULL), "
            + "('Lagos','Africa',21000000, NULL), "
            + "('Kyoto','Asia',1474570, NULL), "
            + "('Mumbai','Asia',20400000, NULL), "
            + "('Shanghai','Asia',24152700, NULL), "
            + "('Melbourne','Australia',3900000, NULL), "
            + "('London','Europe',8580000, NULL), "
            + "('Rome','Europe',2715000, NULL), "
            + "('Rostov-on-Don','Europe',1052000, NULL), "
            + "('San Francisco','North America',5780000, NULL), "
            + "('San Jose','North America',7354555, NULL), "
            + "('New York','North America',21295000, NULL), "
            + "('Rio de Janeiro','South America',12280702, NULL), "
            + "('Santiago','South America',5507282, NULL) "
            + ";";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // open db
//        db = openOrCreateDatabase("myDB",MODE_PRIVATE,null);
//        Cursor tablesCursor = db.rawQuery(
//                "SELECT * FROM sqlite_master WHERE type='table' AND name='cities';", null);
//        if (tablesCursor.getCount() == 0) {
//            db.execSQL(createTableStr);
//            db.execSQL(loadDataStr);
//        }
    }

    public void onEditGame(View view) {
        Intent intent = new Intent(this, editGameActivity.class);
        startActivity(intent);
    }
}
