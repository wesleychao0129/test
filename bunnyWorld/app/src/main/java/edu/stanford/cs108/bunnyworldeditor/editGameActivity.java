package edu.stanford.cs108.bunnyworldeditor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class editGameActivity extends AppCompatActivity {

    private long currentGameId;;
    private Game currentGame;
    editorView ev;
    ArrayList<Shape> shapes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_game);

        this.currentGameId = MainActivity.getCurrentGameID();
        this.currentGame = MainActivity.getGame(currentGameId);

        this.ev = findViewById(R.id.editorView);
        this.shapes = ev.shapes;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.editpagemenu, menu);
        return true;
    }


    public void handleNewPage(MenuItem item) {

        Page newPage = new Page(currentGameId, "");
        currentGame.addPage(newPage);

        Toast toast = Toast.makeText(getApplicationContext(), "A new page is created!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();


    }

    public void handleGoPage(MenuItem item) {

    }

    public void handleRenamePage(MenuItem item) {

    }

    public void handleDeletePage(MenuItem item) {
        Toast toast = Toast.makeText(getApplicationContext(), "The current page is deleted!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

    public void handleSavePage(MenuItem item) {
        Toast toast = Toast.makeText(getApplicationContext(), "The current page is saved!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

    public void handleSetProperty(View view) {
        LayoutInflater li = LayoutInflater.from(editGameActivity.this);
        View promptsView = li.inflate(R.layout.activity_set_property, null);
        AlertDialog.Builder dialog = new AlertDialog.Builder(editGameActivity.this);
        dialog.setTitle("Set Shape Property");
        dialog.setView(promptsView);
        Spinner image_spinner = (Spinner) promptsView.findViewById(R.id.image_spinner);
        String[] images = {"carrot1", "carrot2", "evil bunny", "duck", "fire", "mystic bunny"};
        ArrayAdapter<String> imageList = new ArrayAdapter<String>(editGameActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                images);
        image_spinner.setAdapter(imageList);
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        dialog.show();
    }

    // rename page
    // create page
    // delete page
    // clear page
    // save page
}
