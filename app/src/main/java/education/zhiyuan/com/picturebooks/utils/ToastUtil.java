package education.zhiyuan.com.picturebooks.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Spring on 2017/7/3.
 */

public class ToastUtil {

    private static Toast toast;

    private static Toast layoutToast;

    private ToastUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isShow = true;

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, CharSequence message) {
        if (toast != null) {
            toast.cancel();
        }
        if (context != null) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public static void showLayoutShort(Context context, CharSequence message) {
        if (toast != null) {
            toast.cancel();
        }
        if (layoutToast != null) {
            layoutToast.cancel();
        }
        if (context != null) {
            layoutToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
//            View layout = View.inflate(context, R.layout.toast,null);
//            toast.setView(layout);
            layoutToast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
            layoutToast.show();
        }
    }



    public static void showLayoutLong(Context context, CharSequence message) {
        if (toast != null) {
            toast.cancel();
        }
        if (layoutToast != null) {
            layoutToast.cancel();
        }
        if (context != null) {
            layoutToast = Toast.makeText(context, message, Toast.LENGTH_LONG);
//            View layout = View.inflate(context, R.layout.toast,null);
//            toast.setView(layout);
            layoutToast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
            layoutToast.show();
        }
    }


    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, int message) {
//        if (isShow)
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, CharSequence message) {
        if (toast != null) {
            toast.cancel();
        }
        if (context != null) {
            toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, int message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, CharSequence message, int duration) {
        if (isShow)
            Toast.makeText(context, message, duration).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, int message, int duration) {
        if (isShow)
            Toast.makeText(context, message, duration).show();
    }

}
