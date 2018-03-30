package education.zhiyuan.com.picturebooks.view.commodity.custom;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by leo on 16/5/7.
 */
public class ZoomOutPageTransformer implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.80f;
    private static final float MIN_ALPHA = 0.8f;

    private static float defaultScale = 0.80f;

    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(0);
            view.setScaleX(defaultScale);
            view.setScaleY(defaultScale);
        } else if (position <= 1) { // [-1,1]
            // Modify the default slide transition to shrink the page as well
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            float vertMargin = pageHeight * (1 - scaleFactor) / 2;
            float horzMargin = pageWidth * (1 - scaleFactor) / 2;
            if (position < 0) {
                view.setTranslationX(horzMargin - vertMargin / 2);
            } else {
                view.setTranslationX(-horzMargin + vertMargin / 2);
            }
            // Scale the page down (between MIN_SCALE and 1)
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
            // Fade the page relative to its size.
            view.setAlpha(MIN_ALPHA +
                    (scaleFactor - MIN_SCALE) /
                            (1 - MIN_SCALE) * (1 - MIN_ALPHA));
        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(0);
            view.setScaleX(defaultScale);
            view.setScaleY(defaultScale);
        }
    }
//private static final float MAX_SCALE = 1.0f;
//
//    private static final float MIN_SCALE = 0.95f;//0.85f
//
//    private static final  float MIN_ALPHA = 0.6f;
//
//    private static final String TAG = "PageTransformer";
//    @Override
//    public void transformPage(View view, float position) {
//        //setScaleY只支持api11以上
//        if (position < -1) {
//            view.setScaleX(MIN_SCALE);
//            view.setScaleY(MIN_SCALE);
//            view.setAlpha(MIN_ALPHA);//左边的左边的Page
//        } else if (position <= 1) {
//            float scaleFactor = MIN_SCALE + (1 - Math.abs(position)) * (MAX_SCALE - MIN_SCALE);
//            if (position > 0) {
//                view.setTranslationX(-scaleFactor);
//            } else if (position < 0) {
//                view.setTranslationX(scaleFactor);
//            }
//            view.setScaleY(scaleFactor);
//            view.setScaleX(scaleFactor);
//            // float alpha = 1f -  Math.abs(position) * (1 - );
//            float alpha = MIN_ALPHA + (1 - MIN_ALPHA) * (1 - Math.abs(position));
//            view.setAlpha(alpha);
//        } else { // (1,+Infinity]
//            view.setScaleX(MIN_SCALE);
//            view.setScaleY(MIN_SCALE);
//            view.setAlpha(MIN_ALPHA);
//        }
//    }


}
