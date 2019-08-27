package com.maple.filepickerlibrary.util;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * UiUtils
 * Created by 李波 on 2018/2/8.
 */

public class UiUtils {

    /***
     * DP 转 PX
     * @param c
     * @param dipValue
     * @return
     */
    public static int dpToPx(Context c, float dipValue) {
        DisplayMetrics metrics = c.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

    public static int getImageResize(Context context, RecyclerView recyclerView) {
        int mImageResize;
        RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();
        int spanCount = ((GridLayoutManager) lm).getSpanCount();
        int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        mImageResize = screenWidth / spanCount;
        return mImageResize;
    }

}
