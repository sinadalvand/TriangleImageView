package ir.sinadalvand.libs.triangleimageview;
/*
 *  Copyright (c)  2020 by Sina Dalvand
 *  All Rights Reserved.
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  All Classes & Files Created for CoolPlayer project
 *  This file created by LOGAN Username
 */


import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Property;
import android.view.View;
import android.view.ViewOutlineProvider;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.view.ViewCompat;



public class TriangleImageView extends AppCompatImageView {


    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
    private final Paint mBitmapPaint = new Paint();
    private Bitmap mBitmap;
    private BitmapShader mBitmapShader;

    private static final int COLORDRAWABLE_DIMENSION = 2;
    private final Matrix mShaderMatrix = new Matrix();
    private final RectF mDrawableRect = new RectF();
    private int mBitmapWidth;
    private int mBitmapHeight;

    private float morph = 1;

    private TriangleImageMode mode = TriangleImageMode.EXPANDED;

    private float radius = -1;



    public TriangleImageView(Context context) {
        super(context);
    }

    public TriangleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TriangleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }


    @Override
    protected void onDraw(Canvas canvas) {


        if (mBitmap != null) {
            mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            mBitmapPaint.setAntiAlias(true);
            mBitmapPaint.setShader(mBitmapShader);
            mBitmapPaint.setColor(Color.BLACK);
            mDrawableRect.set(calculateBounds());

            mBitmapHeight = mBitmap.getHeight();
            mBitmapWidth = mBitmap.getWidth();

            updateShaderMatrix();
            mBitmapShader.setLocalMatrix(mShaderMatrix);
            canvas.drawPath(getPath(), mBitmapPaint);
        } else {
            super.onDraw(canvas);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && !isInEditMode()) {
            ViewCompat.setElevation(this, getElevation());
            setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {


                    float minsize = Math.min(getWidth(), getHeight());
                    float rad = radius < 0 ? minsize / (5 + 8 * morph) : radius;

                    int h = getHeight();
                    int w = getWidth();
                    float revmorph = 1 - morph;

                    float d = (h / 2f - rad);

                    Path path = new Path();
                    float[] size = Angle(rad, d * revmorph, w - 2 * rad);

                    path.moveTo(rad + (size[0] * revmorph), size[1] * revmorph);
                    path.lineTo(w - rad + (size[0] * revmorph), (d * revmorph) + (size[1] * revmorph));
                    path.lineTo(w, rad + (d * revmorph));
                    path.lineTo(w, h - rad - (d * revmorph));
                    path.lineTo(w - rad + (size[0] * revmorph), h - (d * revmorph) - (size[1] * revmorph));
                    path.lineTo(rad + (size[0] * revmorph), h - (size[1] * revmorph));
                    path.lineTo(0, h - rad);
                    path.lineTo(0, rad);
                    path.close();

                    outline.setConvexPath(path);
                }
            });
        }


    }


    private Path getPath() {

        float minsize = Math.min(getWidth(), getHeight());
        float rad = radius < 0 ? minsize / (5 + 8 * morph) : radius;

        int h = getHeight();
        int w = getWidth();
        float revmorph = 1 - morph;

        float d = (h / 2f - rad);

        Path path = new Path();
        path.addCircle(rad, rad, rad, Path.Direction.CW);
        path.addCircle(getWidth() - rad, rad + (d * revmorph), rad, Path.Direction.CW);
        path.addCircle(getWidth() - rad, getHeight() - rad - (d * revmorph), rad, Path.Direction.CW);
        path.addCircle(rad, getHeight() - rad, rad, Path.Direction.CW);


        float[] size = Angle(rad, d * revmorph, w - 2 * rad);

        path.moveTo(rad + (size[0] * revmorph), size[1] * revmorph);
        path.lineTo(w - rad + (size[0] * revmorph), (d * revmorph) + (size[1] * revmorph));
        path.lineTo(w, rad + (d * revmorph));
        path.lineTo(w, h - rad - (d * revmorph));
        path.lineTo(w - rad + (size[0] * revmorph), h - (d * revmorph) - (size[1] * revmorph));
        path.lineTo(rad + (size[0] * revmorph), h - (size[1] * revmorph));
        path.lineTo(0, h - rad);
        path.lineTo(0, rad);
        path.close();

        return path;
    }


    private float[] Angle(float rad, float H, float W) {
        double degree = Math.atan(H / W);
        float skipX = (float) Math.sin(degree) * rad;
        float skipY = (float) (rad - Math.cos(degree) * rad);
        return new float[]{skipX, skipY};
    }

    private void updateShaderMatrix() {
        float scale;
        float dx = 0;
        float dy = 0;

        mShaderMatrix.set(null);

        if (mBitmapWidth * mDrawableRect.height() > mDrawableRect.width() * mBitmapHeight) {
            scale = mDrawableRect.height() / (float) mBitmapHeight;
            dx = (mDrawableRect.width() - mBitmapWidth * scale) * 0.5f;
        } else {
            scale = mDrawableRect.width() / (float) mBitmapWidth;
            dy = (mDrawableRect.height() - mBitmapHeight * scale) * 0.5f;
        }

        mShaderMatrix.setScale(scale, scale);
        mShaderMatrix.postTranslate((int) (dx + 0.5f) + mDrawableRect.left, (int) (dy + 0.5f) + mDrawableRect.top);

        mBitmapShader.setLocalMatrix(mShaderMatrix);
    }


    private RectF calculateBounds() {
        int availableWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        int availableHeight = getHeight() - getPaddingTop() - getPaddingBottom();

        int sideLength = Math.min(availableWidth, availableHeight);

        float left = getPaddingLeft() + (availableWidth - sideLength) / 2f;
        float top = getPaddingTop() + (availableHeight - sideLength) / 2f;

        return new RectF(left, top, left + sideLength, top + sideLength);
    }


    private void initializeBitmap() {
        mBitmap = getBitmapFromDrawable(getDrawable());
    }


    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        try {
            Bitmap bitmap;

            if (drawable instanceof ColorDrawable) {
                bitmap = Bitmap.createBitmap(COLORDRAWABLE_DIMENSION, COLORDRAWABLE_DIMENSION, BITMAP_CONFIG);
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), BITMAP_CONFIG);
            }

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        initializeBitmap();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        initializeBitmap();
    }

    @Override
    public void setImageResource(@DrawableRes int resId) {
        super.setImageResource(resId);
        initializeBitmap();
    }

    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        initializeBitmap();
    }


    /**
     * @param morph Float value to set Collapsed or Expanded mode in View
     *              if set 1.0 view will be Rect and if set 0.0 you will see Triangle
     */
    public void setMorph(float morph) {
        this.morph = morph;

        if (morph == 0) {
            mode = TriangleImageMode.COLLAPSED;
        } else if (morph == 1) {
            mode = TriangleImageMode.EXPANDED;
        }

        invalidate();
    }

    /**
     * @return Float morph value
     */
    public float getMorph() {
        return this.morph;
    }


    /**
     * @param mode set image view to be rect EXPANDED or triangle COLLAPSED
     * @see TriangleImageMode
     */
    public void setMode(TriangleImageMode mode) {
        this.mode = mode;
        if (mode == TriangleImageMode.COLLAPSED) {
            morph = 0f;
        } else {
            morph = 1f;
        }
        invalidate();
    }

    /**
     * @return Image view Mode
     * @see TriangleImageMode
     */
    public TriangleImageMode getMode() {
        return mode;
    }


    /**
     * Animation Toggle between Expanded mode and Collapsed mode
     */
    public void Animate() {
        float destination = mode == TriangleImageMode.COLLAPSED ? 1f : 0f;
        mode = mode == TriangleImageMode.COLLAPSED ? TriangleImageMode.EXPANDED : TriangleImageMode.COLLAPSED;

        ObjectAnimator animator = ObjectAnimator.ofFloat(this, new Property<TriangleImageView, Float>(Float.class, "morph") {
            @Override
            public Float get(TriangleImageView object) {
                return null;
            }

            @Override
            public void set(TriangleImageView object, Float value) {
                morph = value;
                invalidate();
                Log.e("APP", "Value: " + value);
            }

        }, morph, destination);
        animator.cancel();
        animator.setDuration(500);
        animator.start();

    }


    /**
     * @param radius set image corner radius in px
     */
    public void setRadius(@Px float radius) {
        this.radius = radius;
        invalidate();
    }

    /**
     * @return corner radius in px
     */
    public float getRadius() {
        return this.radius;
    }

}