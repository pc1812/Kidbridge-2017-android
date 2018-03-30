package education.zhiyuan.com.picturebooks.view.commodity;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by nee on 2017/9/14.
 */

public class ScrollviewListener extends ScrollView {
    private static String TAG = PullMyScrollView.class.getName();
    private PullMyScrollView.setScrollchangListener setscrollchangelis;
    int scrollHeight;
    int scrollY;
    int contentHeight;
    private boolean bottom = false;

    public void setScrollListener(ScrollListener scrollListener) {
        this.mScrollListener = scrollListener;
    }

    private ScrollListener mScrollListener;

    public ScrollviewListener(Context context) {
        super(context);
    }

    public ScrollviewListener(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollviewListener(Context context, AttributeSet attrs,
                              int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (mScrollListener != null) {
                    contentHeight = getChildAt(0).getHeight();
                    scrollHeight = getHeight();
                    scrollY = getScrollY();
                    if (scrollY + scrollHeight >= contentHeight
                            || contentHeight <= scrollHeight) {
                        bottom = true;
                        scrollY=0;
                        Log.e("kkkk", "onTouchEvent: ======move" );
                    }
                    if (bottom) {
                        Log.e("TAG", "onTouchEvent: ==haha"+scrollY );
                        if (scrollY > 0) {
                            Log.e("TAG", "onTouchEvent: ===more" +scrollY);
                            mScrollListener.onScrollToAddMore();
                            bottom = false;
                        }
                    }
                }

//                if (mScrollListener != null) {
//                    contentHeight = getChildAt(0).getHeight();
//                    scrollHeight = getHeight();
//                    scrollY = getScrollY();
//                    mScrollListener.onScroll(scrollY);
//                    if (scrollY + scrollHeight >= contentHeight
//                            || contentHeight <= scrollHeight) {
//                        // mScrollListener.onScrollToBottom();
//                    } else {
//                        mScrollListener.notBottom();
//                    }
//                    if (scrollY == 0) {
//                        mScrollListener.onScrollToTop();
//                    }
//
//                }
                break;

            case MotionEvent.ACTION_SCROLL:
                Log.e("kkkk", "onTouchEvent: ====" );
                break;

            case MotionEvent.ACTION_UP:
                if (mScrollListener != null) {
                    if (scrollY + scrollHeight >= contentHeight
                            || contentHeight <= scrollHeight) {
//                        mScrollListener.onScrollToBottom();
                        Log.e("TAG", "onTouchEvent: ===bottom" );
                        bottom = true;
                    }
                }

                break;
        }
        boolean result = super.onTouchEvent(ev);
        requestDisallowInterceptTouchEvent(false);

        return result;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (setscrollchangelis != null) {
            setscrollchangelis.setScrollChangeListener(this, l, t, oldl, oldt);
        }
    }


    public interface ScrollListener {
//        void onScrollToBottom();

        //
//        void onScrollToTop();
//
//        void onScroll(int scrollY);
//
//        void notBottom();

        void onScrollToAddMore();
    }

    public interface setScrollchangListener {
        void setScrollChangeListener(ScrollView view, int l, int t, int oldl,
                                     int oldt);
    }

    public void SetScrollChangeListener(PullMyScrollView.setScrollchangListener set) {
        this.setscrollchangelis = set;

    }
}
