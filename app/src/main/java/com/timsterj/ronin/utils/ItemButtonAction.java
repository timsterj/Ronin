package com.timsterj.ronin.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.timsterj.ronin.listeners.ItemButtonActionListener;

public class ItemButtonAction {
    private int imageId, color, pos;
    private RectF clickRegion;
    private ItemButtonActionListener clickListener;
    private Context context;
    private Resources resources;

    public ItemButtonAction(Context context, int imageId, int color, ItemButtonActionListener clickListener) {
        this.imageId = imageId;
        this.color = color;
        this.clickListener = clickListener;
        this.context = context;
        resources = context.getResources();

    }

    public boolean onClick(float x, float y) {
        if (clickRegion != null && clickRegion.contains(x, y)) {
            clickListener.onClick(pos);
            return true;
        }
        return false;
    }

    public void onDraw(Canvas c, RectF rectF, int pos) {
        Paint p = new Paint();
        p.setColor(color);
        c.drawRect(rectF, p);

        Drawable d = ContextCompat.getDrawable(context, imageId);
        Bitmap bitmap = drawableToBitmap(d);

        float centreX = (rectF.left + rectF.right + bitmap.getWidth()) / 2;
        float centreY = (rectF.top + rectF.bottom + bitmap.getHeight()) / 2;

        c.drawBitmap(bitmap,
                centreX,
                centreY,
                p
        );

        clickRegion = rectF;
        this.pos = pos;

    }

    private Bitmap drawableToBitmap(Drawable d) {
        if (d instanceof BitmapDrawable) {
            return ((BitmapDrawable) d).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(d.getIntrinsicWidth(),
                d.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888
        );

        Canvas canvas = new Canvas(bitmap);
        d.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        d.draw(canvas);

        return bitmap;

    }

}
