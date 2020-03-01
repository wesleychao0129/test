package edu.stanford.cs108.bunnyworldeditor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class editGameActivity extends AppCompatActivity {

    editorView ev;
    ArrayList<Shape> shapes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_game);
        ev = findViewById(R.id.editorView);
        shapes = ev.shapes;
        // System.out.println(shapes.get(0).getDim().right);
    }

    // rename page
    // create page
    // delete page
    // clear page
    // save page
}
