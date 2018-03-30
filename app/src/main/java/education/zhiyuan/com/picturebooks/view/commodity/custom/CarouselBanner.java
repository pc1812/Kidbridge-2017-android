package education.zhiyuan.com.picturebooks.view.commodity.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.List;

import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.utils.GlideUtils;

/**
 * Created by Lance on 2017/6/20.
 * Email : COCOINUT@163.com
 * Introduce : 自定义图片轮播
 * 如果是画廊效果，要在最外层的布局中设置  android:clipChildren="false"， 以及CarouselBanner中设置 android:clipChildren="false"
 */

public class CarouselBanner extends RelativeLayout {
    private static final int RMP = LayoutParams.MATCH_PARENT;
    private static final int RWP = LayoutParams.WRAP_CONTENT;
    private static final int LWC = LinearLayout.LayoutParams.WRAP_CONTENT;
    private static final int WHAT_AUTO_PLAY = 1000;

    //Point位置
    public static final int CENTER = 0;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    //展示样式 一般样式，画廊样式
    public static final int NORMAL = 0;
    public static final int GALLERY = 1;
    //指示点
    private LinearLayout mPointRealContainerLl;

    public ViewPager getmViewPager() {
        return mViewPager;
    }

    //ViewPager
    private ViewPager mViewPager;
    //本地图片资源
    private List<Integer> mImages;
    //网络图片资源
    private List<String> mImageUrls;
    //是否是网络图片
    private boolean mIsImageUrl = false;
    //是否只有一张图片
    private boolean mIsOneImg = false;
    //是否可以自动播放
    private boolean mAutoPlayAble = true;
    //是否正在播放
    private boolean mIsAutoPlaying = false;
    //自动播放时间
    private int mAutoPalyTime = 2000;
    //指示点位置
    private int mPointPosition = CENTER;
    //指示点资源
    private int mPointDrawableResId = R.drawable.select_lance_point;
    //指示容器背景
    private Drawable mPointContainerBackgroundDrawable;
    //指示容器布局规则
    private LayoutParams mPointRealContainerLp;
    //指示点是否可见
    private boolean mPointsIsVisible = true;
    //样式
    private int mType = 0;
    //间距
    private int mPageMargin;
    //实际size
    private int totalSize;
    //圆角
    private int radius = 0;
    //阴影
    private float cardElevation = 0;
    private float[] pointMargin = {0, 0, 0, 0};
    //item点击事件
    private OnItemClickListener mOnItemClickListener;

    private Handler mAutoPlayHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
            mAutoPlayHandler.sendEmptyMessageDelayed(WHAT_AUTO_PLAY, mAutoPalyTime);
        }
    };

    public CarouselBanner(Context context) {
        this(context, null);
    }

    public CarouselBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CarouselBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.banner);
        mType = a.getInt(R.styleable.banner_showType, NORMAL);
        mPageMargin = a.getInt(R.styleable.banner_pageMargin, 0);
        mPointPosition = a.getInt(R.styleable.banner_pointPosition, RIGHT);
        mAutoPalyTime = a.getInt(R.styleable.banner_autoPalyTime, 3000);
        mPointsIsVisible = a.getBoolean(R.styleable.banner_showPoint, true);
        pointMargin[0] = a.getDimension(R.styleable.banner_pointMarginLeft, 0);
        pointMargin[1] = a.getDimension(R.styleable.banner_pointMarginTop, 0);
        pointMargin[2] = a.getDimension(R.styleable.banner_pointMarginRight, 0);
        pointMargin[3] = a.getDimension(R.styleable.banner_pointMarginBottom, 0);
        mPointContainerBackgroundDrawable = a.getDrawable(R.styleable.banner_points_container_background);
        a.recycle();
        setLayout(context);
    }

    private void setLayout(Context context) {
        //关闭view的OverScroll
        setOverScrollMode(OVER_SCROLL_NEVER);
        //设置指示器背景
        if (mPointContainerBackgroundDrawable == null) {
            mPointContainerBackgroundDrawable = new ColorDrawable(Color.parseColor("#00aaaaaa"));
        }
        //TODO 添加ViewPager
        mViewPager = new ViewPager(context);
        if (mType == 1) {
            mViewPager.setClipChildren(false);
        }
        addView(mViewPager, new LayoutParams(RMP, RMP));
        //设置指示器背景容器
        RelativeLayout pointContainerRl = new RelativeLayout(context);
        if (Build.VERSION.SDK_INT >= 16) {
            pointContainerRl.setBackground(mPointContainerBackgroundDrawable);
        } else {
            pointContainerRl.setBackgroundDrawable(mPointContainerBackgroundDrawable);
        }
        //设置内边距
        pointContainerRl.setPadding(0, 10, 0, 10);
        //设定指示器容器布局及位置
        LayoutParams pointContainerLp = new LayoutParams(RMP, RWP);
        pointContainerLp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        addView(pointContainerRl, pointContainerLp);
        //设置指示器容器
        mPointRealContainerLl = new LinearLayout(context);
        mPointRealContainerLl.setOrientation(LinearLayout.HORIZONTAL);
        mPointRealContainerLp = new LayoutParams(RWP, RWP);
        pointContainerRl.addView(mPointRealContainerLl, mPointRealContainerLp);
        //设置指示器容器是否可见
        if (mPointRealContainerLl != null) {
            if (mPointsIsVisible) {
                mPointRealContainerLl.setVisibility(View.VISIBLE);
            } else {
                mPointRealContainerLl.setVisibility(View.GONE);
            }
        }
        //设置指示器布局位置
        if (mPointPosition == CENTER) {
            mPointRealContainerLp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        } else if (mPointPosition == LEFT) {
            mPointRealContainerLp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        } else if (mPointPosition == RIGHT) {
            mPointRealContainerLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        }
    }

    /**
     * 设置本地图片
     *
     * @param images
     */
    public void setImages(List<Integer> images) {
        //加载本地图片
        mIsImageUrl = false;
        this.mImages = images;
        if (images.size() <= 1) {
            mIsOneImg = true;
        }
        totalSize = images.size();
        //初始化ViewPager
        initViewPager();
    }

    /**
     * 设置网络图片
     *
     * @param urls
     */
    public void setImagesUrl(List<String> urls) {
        mIsImageUrl = true;
        this.mImageUrls = urls;
        if (urls.size() <= 1)
            mIsOneImg = true;
        totalSize = urls.size();
        initViewPager();
    }

    /**
     * 设置指示点是否可见
     *
     * @param isVisible
     */
    public void setPointsIsVisible(boolean isVisible) {
        if (mPointRealContainerLl != null) {
            if (isVisible) {
                mPointRealContainerLl.setVisibility(View.VISIBLE);
            } else {
                mPointRealContainerLl.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 对应三个位置 CENTER,RIGHT,LEFT
     *
     * @param position
     */
    public void setPoinstPosition(int position) {
        //设置指示器布局位置
        if (position == CENTER) {
            mPointRealContainerLp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        } else if (position == LEFT) {
            mPointRealContainerLp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        } else if (position == RIGHT) {
            mPointRealContainerLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        }
    }

    /**
     * 对应两个样式 NORMAL, GALLERY
     *
     * @param type
     */
    public void setmType(int type) {
        mType = type;
    }

    /**
     * 每页间距
     *
     * @param pageMargin TODO 要在setmType之后调用
     */
    public void setmPageMargin(int pageMargin) {
        mPageMargin = pageMargin;
    }

    /**
     * 阴影
     *
     * @param cardElevation
     */
    public void setCardElevation(float cardElevation) {
        this.cardElevation = cardElevation;
    }

    /**
     * 圆角
     *
     * @param radius
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }

    /**
     * 指示器圆点margin值
     *
     * @param pointMargin
     */
    public void setPointMargin(float[] pointMargin) {
        this.pointMargin = pointMargin;
    }

    private void initViewPager() {
        //当图片多于1张时添加指示点
        if (!mIsOneImg) {
            addPoints();
        }
        //设置ViewPager
        FlyPageAdapter adapter = new FlyPageAdapter();
        if (mType == 1) {
            mViewPager.setClipChildren(false);
            mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
            mViewPager.setOffscreenPageLimit(2);
        }
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(mOnPageChangeListener);
        if (mType == 0) {
            mViewPager.setCurrentItem(totalSize * 100, false);
        } else {
            mViewPager.setCurrentItem(totalSize * 100 + 1, false);
        }
        //当图片多于1张时开始轮播 , 默认自动轮播
        if (!mIsOneImg) {
            startAutoPlay();
        }
    }

    /**
     * 返回真实的位置
     *
     * @param position
     * @return
     */
    private int toRealPosition(int position) {
        return totalSize == 0 ? 0 : (position) % totalSize;
    }

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switchToPoint(toRealPosition(position));
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private class FlyPageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            //当只有一张图片时返回1
            if (mIsOneImg) {
                return 1;
            } else {
                return Integer.MAX_VALUE;
            }
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            CardView cardView = new CardView(container.getContext());
            cardView.setRadius(radius);
            cardView.setCardElevation(cardElevation);
            ImageView imageView = new ImageView(getContext());
            cardView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(toRealPosition(position));
                    }
                }
            });
            if (mIsImageUrl) {
//                Glide.with(getContext())
//                        .load(mImageUrls.get(toRealPosition(position)))
//                        .into(imageView);
                GlideUtils.GlideNormal(getContext(), mImageUrls.get(toRealPosition(position)), imageView, R.drawable.iv_replace_les);
            } else {
                imageView.setImageResource(mImages.get(toRealPosition(position)));
            }
            cardView.addView(imageView);
            container.addView(cardView);
            return cardView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            if (object != null)
                object = null;
        }
    }

    /**
     * 添加指示点
     */
    private void addPoints() {
        mPointRealContainerLl.removeAllViews();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LWC, LWC);
        //TODO 设置指示圆点的margin值
        lp.setMargins((int) pointMargin[0], (int) pointMargin[1], (int) pointMargin[2], (int) pointMargin[3]);
        ImageView imageView;
        int length = mIsImageUrl ? mImageUrls.size() : mImages.size();
        for (int i = 0; i < length; i++) {
            imageView = new ImageView(getContext());
            imageView.setLayoutParams(lp);
            imageView.setImageResource(mPointDrawableResId);
            mPointRealContainerLl.addView(imageView);
        }
    }

    /**
     * 切换指示器
     *
     * @param currentPoint
     */
    private void switchToPoint(final int currentPoint) {
        for (int i = 0; i < mPointRealContainerLl.getChildCount(); i++) {
            mPointRealContainerLl.getChildAt(i).setEnabled(false);
        }
        mPointRealContainerLl.getChildAt(currentPoint).setEnabled(true);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mAutoPlayAble && !mIsOneImg) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    stopAutoPlay();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_OUTSIDE:
                    startAutoPlay();
                    break;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 开始播放
     */
    public void startAutoPlay() {
        if (mAutoPlayAble && !mIsAutoPlaying) {
            mIsAutoPlaying = true;
            mAutoPlayHandler.sendEmptyMessageDelayed(WHAT_AUTO_PLAY, mAutoPalyTime);
        }
    }

    /**
     * 停止播放
     */
    public void stopAutoPlay() {
        if (mAutoPlayAble && mIsAutoPlaying) {
            mIsAutoPlaying = false;
            mAutoPlayHandler.removeMessages(WHAT_AUTO_PLAY);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
