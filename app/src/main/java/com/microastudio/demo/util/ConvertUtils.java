package com.microastudio.demo.util;

/**
 * @author peng
 * @date 2018/7/3
 */

import android.content.Context;

/**
 * Author       v0cn123
 * Date         2018/4/11
 * Desc	        ${转换工具类}
 */
public class ConvertUtils {

    /**
     * dp值转换成px值
     *
     * @param dpValue dp值
     * @return px值
     */
    public static int dp2px(Context context, final float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px值转换成dp值
     *
     * @param pxValue px值
     * @return dp值
     */
    public static int px2dp(Context context, final float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp值转换成px值
     *
     * @param spValue sp值
     * @return px值
     */
    public static int sp2px(Context context, final float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px值转换成sp值
     *
     * @param pxValue px值
     * @return sp值
     */
    public static int px2sp(Context context, final float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
