package education.zhiyuan.com.picturebooks.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by LH on 2018/1/11.
 * Description ：hahah
 */

public class GlideUtils {
    private static RequestOptions optionsNormal = null, optionsCircle = null;

    /**
     * 圆形
     */
    public static void GlideCircle(Context context, String url, ImageView iv, int iconPlace) {
        if (optionsCircle == null) {
            optionsCircle = new RequestOptions();
            optionsCircle.apply(RequestOptions.circleCropTransform());
            optionsCircle.placeholder(iconPlace);
            optionsCircle.error(iconPlace);
        }
        Glide.with(context)
                .load(url)
                .apply(optionsCircle)
                .into(iv);
    }


    /**
     * 一般
     */
    public static void GlideNormal(Context context, String url, ImageView iv, int iconPlace) {
        if (optionsNormal == null) {
            optionsNormal = new RequestOptions();
        }
        optionsNormal.placeholder(iconPlace);
        optionsNormal.error(iconPlace);
        Glide.with(context)
                .load(url)
                .apply(optionsNormal)
                .into(iv);
    }

}
