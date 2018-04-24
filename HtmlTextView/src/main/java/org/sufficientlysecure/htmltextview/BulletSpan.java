package org.sufficientlysecure.htmltextview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.Layout;
import android.text.Spanned;
import android.text.style.LeadingMarginSpan;

public class BulletSpan implements LeadingMarginSpan {

    private int mGapWidth;
    private int mBulletRadius;
    private float mTextHeight;
    private int mLevel;
    private final boolean mWantColor;
    private final int mColor;

    private static final int STANDARD_BULLET_RADIUS = 2;
    private static final int STANDARD_GAP_WIDTH = 2;
    private static Path sBulletPath = null;

    public BulletSpan() {
        mGapWidth = STANDARD_GAP_WIDTH;
        mBulletRadius = STANDARD_BULLET_RADIUS;
        mWantColor = false;
        mColor = 0;
    }

    public BulletSpan(int color) {
        mGapWidth = STANDARD_GAP_WIDTH;
        mBulletRadius = STANDARD_BULLET_RADIUS;
        mWantColor = true;
        mColor = color;
    }

    public int getGapWidth() {
        return mGapWidth;
    }

    public void setGapWidth(int gapWidth) {
        mGapWidth = gapWidth;
    }

    public int getBulletRadius() {
        return mBulletRadius;
    }

    public void setBulletRadius(int bulletRadius) {
        mBulletRadius = bulletRadius;
    }

    public float getTextHeight() {
        return mTextHeight;
    }

    public void setTextHeight(float textHeight) {
        mTextHeight = textHeight;
    }

    public int getLevel() {
        return mLevel;
    }

    public void setLevel(int level) {
        mLevel = level;
    }

    public int getLeadingMargin(boolean first) {
        return 2 * mBulletRadius + mGapWidth;
    }

    public void drawLeadingMargin(Canvas c, Paint p, int x, int dir,
                                  int top, int baseline, int bottom,
                                  CharSequence text, int start, int end,
                                  boolean first, Layout l) {
        if (((Spanned) text).getSpanStart(this) == start) {
            Paint.Style style = p.getStyle();
            int oldcolor = 0;

            if (mWantColor) {
                oldcolor = p.getColor();
                p.setColor(mColor);
            }

            p.setStyle(Paint.Style.FILL);

            if (c.isHardwareAccelerated()) {
                if (sBulletPath == null) {
                    sBulletPath = new Path();
                    // Bullet is slightly better to avoid aliasing artifacts on mdpi devices.
                    sBulletPath.addCircle(0.0f, 0.0f, 1.2f * mBulletRadius, Path.Direction.CW);
                }

                c.save();
                c.translate(mLevel * mTextHeight + dir * mBulletRadius * 2, (top + bottom - (mTextHeight > 0 ? (bottom - top - mTextHeight) : 0)) / 2f + mBulletRadius);
                c.drawPath(sBulletPath, p);
                c.restore();
            } else {
                c.drawCircle(mLevel * mTextHeight + dir * (mBulletRadius * 2), (top + bottom - (mTextHeight > 0 ? (bottom - top - mTextHeight) : 0)) / 2f + mBulletRadius, mBulletRadius, p);
            }

            if (mWantColor) {
                p.setColor(oldcolor);
            }

            p.setStyle(style);
        }
    }
}
