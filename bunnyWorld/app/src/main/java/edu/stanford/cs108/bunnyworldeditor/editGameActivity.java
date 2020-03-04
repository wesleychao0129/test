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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class editGameActivity extends AppCompatActivity {

    private long currentGameId;;
    private Game currentGame;
    private Page currentPage;
    editorView ev;
    // ArrayList<Shape> curShapes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_game);

        this.currentGameId = MainActivity.getCurrentGameID();
        this.currentGame = MainActivity.getGame(currentGameId);

        this.ev = findViewById(R.id.editorView);
        // this.curShapes = ev.curShapes;
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
        ev.invalidate();

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
        dialog.setView(promptsView);

        final Shape currentShape = ev.chosenShape;

        TextView imageName = (TextView) promptsView.findViewById(R.id.imageName);

        final EditText shapeName = (EditText) promptsView.findViewById(R.id.shapeName);
        final EditText left = (EditText) promptsView.findViewById(R.id.leftX);
        final EditText top = (EditText) promptsView.findViewById(R.id.topY);
        final EditText width = (EditText) promptsView.findViewById(R.id.widthW);
        final EditText height = (EditText) promptsView.findViewById(R.id.heightH);

        final Switch movable = (Switch) promptsView.findViewById(R.id.movable);
        final Switch hidden = (Switch) promptsView.findViewById(R.id.hidden);

        if (currentShape != null) {
            imageName.setText(currentShape.getImageName());
            shapeName.setText(currentShape.getName());
            left.setText(String.valueOf(currentShape.getDim().left));
            top.setText(String.valueOf(currentShape.getDim().top));
            width.setText(String.valueOf(currentShape.getDim().width()));
            height.setText(String.valueOf(currentShape.getDim().height()));
            movable.setChecked(currentShape.getMovable());
            hidden.setChecked(currentShape.getHidden());
        }

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        dialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (currentShape != null) {
                    currentShape.setName(shapeName.getText().toString());
                    currentShape.getDim().left = Float.valueOf(left.getText().toString());
                    currentShape.getDim().top = Float.valueOf(top.getText().toString());
                    currentShape.getDim().right = currentShape.getDim().left + Float.valueOf(width.getText().toString());
                    currentShape.getDim().bottom = currentShape.getDim().top + Float.valueOf(height.getText().toString());
                    currentShape.setMovable(movable.isChecked());
                    currentShape.setHidden(hidden.isChecked());
                    ev.invalidate();
                }
            }
        });

        dialog.show();
    }

    public void handleSetScript(View view) {
        LayoutInflater li = LayoutInflater.from(editGameActivity.this);
        final View promptsView = li.inflate(R.layout.activity_set_script, null);
        AlertDialog.Builder dialog = new AlertDialog.Builder(editGameActivity.this);
        dialog.setView(promptsView);

        final Shape currentShape = ev.chosenShape;

        TextView imageName = (TextView) promptsView.findViewById(R.id.shapeName);
        if (currentShape != null) {
            imageName.setText(currentShape.getName());
        }

        Spinner trigger_spinner = (Spinner) promptsView.findViewById(R.id.trigger);
        String[] triggers = {"on click", "on enter", "on drop"};
        ArrayAdapter<String> triggerList = new ArrayAdapter<String>(editGameActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                triggers);
        trigger_spinner.setAdapter(triggerList);

        final Spinner shape_spinner = (Spinner) promptsView.findViewById(R.id.onDropShape);

        trigger_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] shapes;
                if (position < 2) {
                    String[] shapes1 = {"no shape"};
                    shapes = shapes1;
                } else {
                    String[] shapes2 = {"shape1", "shape2", "shape3", "shape4", "shape5"};
                    shapes = shapes2;
                }
                ArrayAdapter<String> shapeList = new ArrayAdapter<String>(editGameActivity.this,
                        android.R.layout.simple_spinner_dropdown_item,
                        shapes);
                shape_spinner.setAdapter(shapeList);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                String[] shapes = {"no shape"};
                ArrayAdapter<String> shapeList = new ArrayAdapter<String>(editGameActivity.this,
                        android.R.layout.simple_spinner_dropdown_item,
                        shapes);
                shape_spinner.setAdapter(shapeList);
            }
        });

        Spinner action_spinner = (Spinner) promptsView.findViewById(R.id.action);
        String[] actions = {"go to", "play", "hide", "show"};
        ArrayAdapter<String> actionList = new ArrayAdapter<String>(editGameActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                actions);
        action_spinner.setAdapter(actionList);

        final Spinner modifier_spinner = (Spinner) promptsView.findViewById(R.id.modifier);

        action_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] modifiers;
                if (position == 0) {
                    String[] modifiers1 = {"page1", "page2", "page3", "page4", "page5"};
                    modifiers = modifiers1;
                } else if (position == 1) {
                    String[] modifiers2 = {"music1", "music2", "music3", "music4", "music5"};
                    modifiers = modifiers2;
                } else {
                    String[] modifiers3 = {"shape1", "shape2", "shape3", "shape4", "shape5"};
                    modifiers = modifiers3;
                }

                ArrayAdapter<String> modifierList = new ArrayAdapter<String>(editGameActivity.this,
                        android.R.layout.simple_spinner_dropdown_item,
                        modifiers);
                modifier_spinner.setAdapter(modifierList);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                String[] modifiers = {"page1", "page2", "page3", "page4", "page5"};
                ArrayAdapter<String> modifierList = new ArrayAdapter<String>(editGameActivity.this,
                        android.R.layout.simple_spinner_dropdown_item,
                        modifiers);
                modifier_spinner.setAdapter(modifierList);
            }
        });


        dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
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
