/*
 * Copyright (C) 2017 Dominik Mosberger <https://github.com/mosberger>
 * Copyright (C) 2013 Leszek Mzyk <https://github.com/imbryk>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sufficientlysecure.htmltextview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.LeadingMarginSpan;

/**
 * Class to use Numbered Lists in TextViews.
 * The span works the same as {@link android.text.style.BulletSpan} and all lines of the entry have
 * the same leading margin.
 */
public class NumberSpan implements LeadingMarginSpan {

    private int mGapWidth;
    private float mTextHeight;
    private int mLevel;
    private final String mNumber;
    private final int mTextWidth;

    public NumberSpan(TextPaint textPaint, int number) {
        mNumber = Integer.toString(number).concat(".");
        mTextWidth = (int) textPaint.measureText(mNumber);
    }

    public int getGapWidth() {
        return mGapWidth;
    }

    public void setGapWidth(int gapWidth) {
        mGapWidth = gapWidth;
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

    @Override
    public int getLeadingMargin(boolean first) {
        return (int) (mTextWidth * 1.2f + mGapWidth);
    }

    @Override
    public void drawLeadingMargin(Canvas c, Paint p, int x, int dir, int top, int baseline,
                                  int bottom, CharSequence text, int start, int end,
                                  boolean first, Layout l) {
        if (text instanceof Spanned) {
            int spanStart = ((Spanned) text).getSpanStart(this);
            if (spanStart == start) {
                c.drawText(mNumber, mLevel * (mTextHeight * 0.9f), baseline, p);
            }
        }
    }
}
