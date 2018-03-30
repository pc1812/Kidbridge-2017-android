package education.zhiyuan.com.picturebooks.utils;

import android.app.Activity;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by LH on 2017/12/15.
 * Description ：hahah
 */

public class TextViewUtils {
    /**
     * Drawable
     */
    public static void setDrawable(Activity activity, TextView textView, int icon, int width, int height, int type) {
        Drawable drawable = activity.getResources().getDrawable(icon);
        drawable.setBounds(0, 0, width, height);
        if (type == 0) {
            textView.setCompoundDrawables(drawable, null, null, null);
        }
        if (type == 1) {
            textView.setCompoundDrawables(null, drawable, null, null);
        }
        if (type == 2) {
            textView.setCompoundDrawables(null, null, drawable, null);
        }
        if (type == 3) {
            textView.setCompoundDrawables(null, null, null, drawable);
        }
    }

    public static void setDrawableLR(Activity activity, TextView textView, int iconLeft, int iconRight, int[] left, int[] right) {
        Drawable drawableLeft = activity.getResources().getDrawable(iconLeft);
        drawableLeft.setBounds(0, 0, left[0], left[1]);
        Drawable drawableRight = activity.getResources().getDrawable(iconRight);
        drawableRight.setBounds(0, 0, right[0], right[1]);
        textView.setCompoundDrawables(drawableLeft, null, drawableRight, null);
    }


    /**
     * 设置字体样式  fonnts/PingFangBold.ttf
     */
    public static void setTextType(Activity activity, TextView textView, String path) {
        Typeface typeface = Typeface.createFromAsset(activity.getAssets(), path);
        textView.setTypeface(typeface);
    }

    /**
     * 字体颜色
     */
    public static void setTextColor(TextView tv, String text, String first, String end, int color) {
        SpannableString ss = new SpannableString(text);
        int one = text.indexOf(first) + 1;
        int two = text.indexOf(end);
        ss.setSpan(new ForegroundColorSpan(color), one, two, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(ss);
    }

    /**
     * 字体颜色
     */
    public static void setTextColor(TextView tv, String text, int begin, int end, int color) {
        SpannableString ss = new SpannableString(text);
        ss.setSpan(new ForegroundColorSpan(color), begin, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(ss);
    }

    /**
     * 去掉换行
     */
    public static String replaceBlank(String src) {
//        String dest = "";
//        if (src != null) {
//            Pattern pattern = Pattern.compile("\r|\n|\\s*");
//            Matcher matcher = pattern.matcher(src);
//            dest = matcher.replaceAll("");
//        }
        return src;
    }

    /**
     * 去掉换行
     */
    public static String replaceBlankHa(String src) {
        String dest = "";
        if (src != null) {
            Pattern pattern = Pattern.compile("\t|\r|\n|\\s*");
            Matcher matcher = pattern.matcher(src);
            dest = matcher.replaceAll("");
        }
        return dest;
    }

}
