package com.kursx.tactics;

import android.util.DisplayMetrics;

public class Util {

    public static int dpToPx(int dp) {
        DisplayMetrics displayMetrics = MyApplication.getContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
