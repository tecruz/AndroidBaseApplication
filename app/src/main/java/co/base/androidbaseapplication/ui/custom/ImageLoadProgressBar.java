package co.base.androidbaseapplication.ui.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.TypedValue;

import com.facebook.drawee.drawable.ProgressBarDrawable;

import co.base.androidbaseapplication.R;
import co.base.androidbaseapplication.injection.ApplicationContext;

public class ImageLoadProgressBar extends ProgressBarDrawable {

    float level;

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    int color = 0;

    final RectF oval = new RectF();

    int radius = 20;

    public ImageLoadProgressBar(@ApplicationContext Context mContext) {
        TypedValue typedValue = new TypedValue();
        mContext.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        color = typedValue.data;
        paint.setStrokeWidth(2.0f);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected boolean onLevelChange(int level) {
        this.level = level;
        invalidateSelf();
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        oval.set(canvas.getWidth() / 2 - radius, canvas.getHeight() / 2 - radius,
                canvas.getWidth() / 2 + radius, canvas.getHeight() / 2 + radius);

        drawCircle(canvas, level, color);
    }


    private void drawCircle(Canvas canvas, float level, int color) {
        paint.setColor(color);
        float angle;
        angle = 360 / 1f;
        angle = level * angle;
        canvas.drawArc(oval, 0, Math.round(angle), false, paint);
    }

}

