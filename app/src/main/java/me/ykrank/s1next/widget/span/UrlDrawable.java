package me.ykrank.s1next.widget.span;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.style.ImageSpan;

/**
 * Implements {@link Drawable.Callback} in order to show animated GIFs in the TextView.
 * <p>
 * Used in {@link GlideImageGetter}.
 */
final class UrlDrawable extends Drawable implements Drawable.Callback {
    public static final int TARGET_SIZE_UNSET = -1;

    private Drawable mDrawable;
    @Nullable
    private ImageSpan imageSpan;
    private String url;
    private int widthTargetSize = TARGET_SIZE_UNSET;
    private int heightTargetSize = TARGET_SIZE_UNSET;
    private boolean keepScaleRatio = true;
    /**
     * Size over this will scale to target size
     */
    private int triggerSize = -1;

    public UrlDrawable(String url) {
        this(url, TARGET_SIZE_UNSET, TARGET_SIZE_UNSET, true);
    }

    /**
     * @param url              image url
     * @param widthTargetSize  bound target width
     * @param heightTargetSize bound target height
     * @param keepScaleRatio   keep image ratio when scale to target size
     */
    public UrlDrawable(String url, int widthTargetSize, int heightTargetSize, boolean keepScaleRatio) {
        this.url = url;
        this.widthTargetSize = widthTargetSize;
        this.heightTargetSize = heightTargetSize;
        this.keepScaleRatio = keepScaleRatio;
    }

    public void setWidthTargetSize(int widthTargetSize) {
        this.widthTargetSize = widthTargetSize;
    }

    public void setHeightTargetSize(int heightTargetSize) {
        this.heightTargetSize = heightTargetSize;
    }

    public void setKeepScaleRatio(boolean keepScaleRatio) {
        this.keepScaleRatio = keepScaleRatio;
    }

    public void setTriggerSize(int triggerSize) {
        this.triggerSize = triggerSize;
    }

    public String getUrl() {
        return url;
    }

    @Nullable
    public ImageSpan getImageSpan() {
        return imageSpan;
    }

    public void setImageSpan(@Nullable ImageSpan imageSpan) {
        this.imageSpan = imageSpan;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if (mDrawable != null) {
            mDrawable.draw(canvas);
        }
    }

    @Override
    public void setAlpha(int alpha) {
        if (mDrawable != null) {
            mDrawable.setAlpha(alpha);
        }
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        if (mDrawable != null) {
            mDrawable.setColorFilter(cf);
        }
    }

    @Override
    public int getOpacity() {
        if (mDrawable != null) {
            return mDrawable.getOpacity();
        }
        return PixelFormat.UNKNOWN;
    }

    public void setDrawable(@Nullable Drawable drawable) {
        if (this.mDrawable == drawable) {
            return;
        }
        if (this.mDrawable != null) {
            this.mDrawable.setCallback(null);
        }
        if (drawable != null) {
            drawable.setCallback(this);
        }
        this.mDrawable = drawable;
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        //Size is big enough
        if ((widthTargetSize != TARGET_SIZE_UNSET && (right - left) > triggerSize) ||
                (heightTargetSize != TARGET_SIZE_UNSET && (bottom - top) > triggerSize)) {
            if (keepScaleRatio) {
                if (widthTargetSize != TARGET_SIZE_UNSET && heightTargetSize != TARGET_SIZE_UNSET) {
                    float imageRatio = (float) (right - left) / (bottom - top);
                    float targetRatio = (float) widthTargetSize / heightTargetSize;
                    if (imageRatio > targetRatio) {
                        right = left + widthTargetSize;
                        bottom = top + Math.round(widthTargetSize / imageRatio);
                    } else {
                        bottom = top + heightTargetSize;
                        right = left + Math.round(heightTargetSize * imageRatio);
                    }
                } else if (widthTargetSize != TARGET_SIZE_UNSET) {
                    float imageRatio = (float) (right - left) / (bottom - top);
                    right = left + widthTargetSize;
                    bottom = top + Math.round(widthTargetSize / imageRatio);
                } else {
                    float imageRatio = (float) (right - left) / (bottom - top);
                    bottom = top + heightTargetSize;
                    right = left + Math.round(heightTargetSize * imageRatio);
                }
            } else {
                if (widthTargetSize != TARGET_SIZE_UNSET) {
                    right = left + widthTargetSize;
                }
                if (heightTargetSize != TARGET_SIZE_UNSET) {
                    bottom = top + heightTargetSize;
                }
            }
        }

        Rect oldBounds = getBounds();
        if (oldBounds.left != left || oldBounds.top != top ||
                oldBounds.right != right || oldBounds.bottom != bottom) {
            if (this.mDrawable != null) {
                this.mDrawable.setBounds(left, top, right, bottom);
            }
            super.setBounds(left, top, right, bottom);
        }
    }

    @Override
    public void invalidateDrawable(@NonNull Drawable who) {
        if (getCallback() != null) {
            getCallback().invalidateDrawable(who);
        }
    }

    @Override
    public void scheduleDrawable(@NonNull Drawable who, @NonNull Runnable what, long when) {
        if (getCallback() != null) {
            getCallback().scheduleDrawable(who, what, when);
        }
    }

    @Override
    public void unscheduleDrawable(@NonNull Drawable who, @NonNull Runnable what) {
        if (getCallback() != null) {
            getCallback().unscheduleDrawable(who, what);
        }
    }
}
