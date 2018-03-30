package education.zhiyuan.com.picturebooks.view.commodity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by LH on 2017/9/19.
 */

public class MScrollview extends ScrollView {
    private static String TAG = PullMyScrollView.class.getName();
    private PullMyScrollView.setScrollchangListener setscrollchangelis;
    int scrollHeight;
    int scrollY;
    int contentHeight;

    public void setScrollListener(ScrollListener scrollListener) {
        this.mScrollListener = scrollListener;
    }

    private ScrollListener mScrollListener;

    public MScrollview(Context context) {
        super(context);
    }

    public MScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MScrollview(Context context, AttributeSet attrs,
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
                }
                break;

            case MotionEvent.ACTION_UP:
                if (mScrollListener != null) {
                    if (scrollY + scrollHeight >= contentHeight
                            || contentHeight <= scrollHeight) {
                        mScrollListener.onScrollToAddMore();
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