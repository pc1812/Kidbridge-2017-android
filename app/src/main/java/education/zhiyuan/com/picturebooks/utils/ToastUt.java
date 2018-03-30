package education.zhiyuan.com.picturebooks.utils;

import android.widget.Toast;

import education.zhiyuan.com.picturebooks.MyApp;

/**
 * Created by LH on 2017/9/19.
 */

public class ToastUt {
    private static Toast toast;

    public static void showShort(String content) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(MyApp.getContext(), content, Toast.LENGTH_SHORT);
        toast.show();
    }
}
