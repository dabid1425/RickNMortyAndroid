package customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.example.ricknmortyandroid.R;

public class CustomGradientView extends View {
    private Paint paint;
    private int startColor;
    private int stopColor;
    private float startX;
    private float startY;
    private float stopX;
    private float stopY;
    private float cornerRadius;
    private int borderColor;
    private float borderWidth;

    private Rect textBounds;
    public CustomGradientView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

        // Retrieve attributes from XML
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomGradientView);


        startColor = typedArray.getColor(R.styleable.CustomGradientView_startColor, Color.RED);
        stopColor = typedArray.getColor(R.styleable.CustomGradientView_stopColor, Color.BLUE);
        startX = typedArray.getFloat(R.styleable.CustomGradientView_startX, 0f);
        startY = typedArray.getFloat(R.styleable.CustomGradientView_startY, 0f);
        stopX = typedArray.getFloat(R.styleable.CustomGradientView_stopX, 1f);
        stopY = typedArray.getFloat(R.styleable.CustomGradientView_stopY, 1f);
        cornerRadius = typedArray.getDimension(R.styleable.CustomGradientView_cornerRadius, 0f);
        borderColor = typedArray.getColor(R.styleable.CustomGradientView_borderColor, Color.BLACK);
        borderWidth = typedArray.getDimension(R.styleable.CustomGradientView_borderWidth, 0f);
        typedArray.recycle();
        paint = new Paint();
        textBounds = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Draw the border
        if (borderWidth > 0) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(borderColor);
            paint.setStrokeWidth(borderWidth);
            canvas.drawRoundRect(getRectF(), cornerRadius, cornerRadius, paint);
        }

        // Draw the gradient background
        paint.setStyle(Paint.Style.FILL);
        paint.setShader(new LinearGradient(
                startX * getWidth(),
                startY * getHeight(),
                stopX * getWidth(),
                stopY * getHeight(),
                startColor,
                stopColor,
                Shader.TileMode.CLAMP
        ));

        canvas.drawRoundRect(getRectF(), cornerRadius, cornerRadius, paint);
        paint.reset();
        paint.setAntiAlias(true);

    }

    private RectF getRectF() {
        return new RectF(
                borderWidth / 2f,
                borderWidth / 2f,
                getWidth() - borderWidth / 2f,
                getHeight() - borderWidth / 2f
        );
    }
}
