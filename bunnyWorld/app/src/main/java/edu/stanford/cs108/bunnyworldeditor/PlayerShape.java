package edu.stanford.cs108.bunnyworldeditor;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

public class PlayerShape {
    private int shapeId;
    private String shapeName;
    private boolean movable;
    public boolean shapeIsMoving;
    private final String RECT = "rectangle";
    private final String IMAGE = "image";
    private final String TEXT = "text";
    private String shapeType;
    private RectF shapeDimensions;
    private boolean shapeVisible;
    private String text;
    private Paint rectOutlinePaint;
    private Paint rectOutlineFill;
    private TextPaint textPaint;
    private int textColorInTextBox = Color.rgb(0, 0, 0);
    private int textSizeInTextBox = 50;
    private BitmapDrawable drawable;
    private String script;
    private Instruction instructions;
    public boolean highlight;
    public static final Paint highlightPaint = new Paint();

    PlayerShape(int shapeId, String shapeName, boolean movable, String shapeType,
                RectF shapeDimensions, boolean shapeVisible, String script) {
        this.shapeId = shapeId;
        this.shapeName = shapeName;
        this.movable = movable;
        this.shapeType = shapeType;
        this.shapeDimensions = shapeDimensions;
        this.shapeVisible = shapeVisible;
        this.script = script;
        parseScript();
        init();
    }

    PlayerShape(int shapeId, String shapeName, boolean movable, String shapeType,
                RectF shapeDimensions, boolean shapeVisible, String script, String text, int textSize) {
        this(shapeId, shapeName, movable, shapeType, shapeDimensions, shapeVisible, script);
        this.text = text;
        this.textSizeInTextBox = textSize;
    }

    PlayerShape(int shapeId, String shapeName, boolean movable, String shapeType,
                RectF shapeDimensions, boolean shapeVisible, String script, BitmapDrawable drawable) {
        this(shapeId, shapeName, movable, shapeType, shapeDimensions, shapeVisible, script);
        this.drawable = drawable;
    }

    public String getShapeType() {
        return shapeType;
    }

    public boolean isShapeVisible() {
        return shapeVisible;
    }

    public String getShapeName() {
        return shapeName;
    }

    public RectF getDimensions() {
        return shapeDimensions;
    }

    public boolean isMovable() {
        return movable;
    }

    public String getScript() {
        return script;
    }

    public boolean hasScript() {
        return !script.isEmpty();
    }

    public void hide() {
        shapeVisible = false;
    }

    public void show() {
        shapeVisible = true;
    }

    public Instruction getInstructions() {
        return instructions;
    }

    private void parseScript() {
        if (hasScript()) {
            instructions = new Instruction(script);
        }
    }

    public void move(float x, float y) {
        shapeDimensions.left += x;
        shapeDimensions.right += x;
        shapeDimensions.top += y;
        shapeDimensions.bottom += y;
    }

    private void init() {
        initRectanglePaint();
        initTextPaint();
        initHighlightPaint();
    }

    private void initHighlightPaint() {
        highlightPaint.setColor(Color.rgb(0, 0, 255));
        highlightPaint.setStyle(Paint.Style.STROKE);
        highlightPaint.setStrokeWidth(10.0f);
    }

    private void initRectanglePaint() {
        rectOutlineFill = new Paint();
        rectOutlineFill.setColor(Color.rgb(255, 255, 255));
        rectOutlineFill.setStyle(Paint.Style.FILL);
        rectOutlinePaint = new Paint();
        rectOutlinePaint.setColor(Color.rgb(140,21,21));
        rectOutlinePaint.setStyle(Paint.Style.STROKE);
        rectOutlinePaint.setStrokeWidth(5.0f);
    }

    private void initTextPaint() {
        textPaint = new TextPaint();
        textPaint.setTextSize(textSizeInTextBox);
        textPaint.setColor(textColorInTextBox);
    }

    public void draw(Canvas canvas) {
        if (!shapeVisible) {
            return;
        }
        if (shapeType == RECT) {
            canvas.drawRect(shapeDimensions, rectOutlineFill);
            canvas.drawRect(shapeDimensions, rectOutlinePaint);
        }
        if (shapeType == TEXT) {
            StaticLayout textLayout = new StaticLayout(text, textPaint, (int) shapeDimensions.width(),
                    Layout.Alignment.ALIGN_CENTER, 1, 0, false);
            canvas.save();
            canvas.translate(shapeDimensions.left, shapeDimensions.top);
            textLayout.draw(canvas);
            canvas.restore();
        }
        if (shapeType == IMAGE) {
            canvas.drawBitmap(drawable.getBitmap(), null, shapeDimensions, null);
            if (highlight == true) {
                canvas.drawRect(
                        shapeDimensions.left - 2,
                        shapeDimensions.top - 2,
                        shapeDimensions.right + 2,
                        shapeDimensions.bottom + 2,
                        PlayerShape.highlightPaint);
            }
            highlight = false;
        }
    }

    public boolean positionInShape(float x, float y) {
        if (x <= shapeDimensions.right && x >= shapeDimensions.left &&
                y <= shapeDimensions.bottom && y >= shapeDimensions.top) {
            return true;
        }
        return false;
    }
}
