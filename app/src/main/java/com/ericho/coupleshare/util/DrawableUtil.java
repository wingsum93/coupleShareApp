package com.ericho.coupleshare.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;

/**
 * Created by EricH on 18/4/2017.
 */

public class DrawableUtil {
    public static Drawable getDrawble(Context context, @DrawableRes int res){
        if(Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            return context.getResources().getDrawable(res,null);
        }else {
            return context.getResources().getDrawable(res);
        }
    }
}
