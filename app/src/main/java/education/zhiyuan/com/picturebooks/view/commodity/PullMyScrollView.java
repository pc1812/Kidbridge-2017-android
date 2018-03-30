package education.zhiyuan.com.picturebooks.view.commodity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by baoyunlong on 16/6/8.
 */
public class PullMyScrollView extends ScrollView {
	private static String TAG = PullMyScrollView.class.getName();
	private setScrollchangListener setscrollchangelis;

	public void setScrollListener(ScrollListener scrollListener) {
		this.mScrollListener = scrollListener;
	}

	private ScrollListener mScrollListener;

	public PullMyScrollView(Context context) {
		super(context);
	}

	public PullMyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PullMyScrollView(Context context, AttributeSet attrs,
							int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		switch (ev.getAction()) {
		case MotionEvent.ACTION_MOVE:

			if (mScrollListener != null) {
				int contentHeight = getChildAt(0).getHeight();
				int scrollHeight = getHeight();
				int scrollY = getScrollY();
				mScrollListener.onScroll(scrollY);
				if (scrollY + scrollHeight >= contentHeight
						|| contentHeight <= scrollHeight) {
					mScrollListener.onScrollToBottom();
				} else {
					mScrollListener.notBottom();
				}
				if (scrollY == 0) {
					mScrollListener.onScrollToTop();
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
		void onScrollToBottom();

		void onScrollToTop();

		void onScroll(int scrollY);

		void notBottom();
	}

	public interface setScrollchangListener {
		void setScrollChangeListener(ScrollView view, int l, int t, int oldl,
									 int oldt);
	}

    public void SetScrollChangeListener(setScrollchangListener set) {
		this.setscrollchangelis = set;

	}
}
