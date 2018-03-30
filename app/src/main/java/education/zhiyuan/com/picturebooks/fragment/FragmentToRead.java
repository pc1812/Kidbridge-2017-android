package education.zhiyuan.com.picturebooks.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.bean.EventPlay;
import education.zhiyuan.com.picturebooks.bean.EventRecord;
import education.zhiyuan.com.picturebooks.bean.HuiBenRepeatBean;
import education.zhiyuan.com.picturebooks.utils.SharedPreferencesUtil;
import education.zhiyuan.com.picturebooks.utils.TextViewUtils;
import education.zhiyuan.com.picturebooks.view.commodity.MyScrollView;

/**
 * Created by Lance on 2017/6/24.
 * Email : COCOINUT@163.com
 * Introduce :
 */

public class FragmentToRead extends Fragment {

    @BindView(R.id.tv_index)
    TextView tvIndex;
    @BindView(R.id.tv_en_content)
    TextView tvEnContent;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.li_bottom)
    LinearLayout liBottom;
    @BindView(R.id.iv_play)
    ImageView ivPlay;
    @BindView(R.id.iv_record)
    ImageView ivRecord;
    @BindView(R.id.viewLeft)
    View viewLeft;
    @BindView(R.id.viewRight)
    View viewRight;
    @BindView(R.id.scrollView)
    MyScrollView scrollView;
    Unbinder unbinder;

    private HuiBenRepeatBean.DataBean.BookSegmentListBean bookSegmentBean;
    private int size;
    private EventPlay eventPlay;
    private EventRecord eventRecord;
    private int index;
    private Submit submit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(FragmentToRead.this);
        if (getArguments() != null) {
            index = getArguments().getInt("index");
            size = getArguments().getInt("size");
            bookSegmentBean = (HuiBenRepeatBean.DataBean.BookSegmentListBean) getArguments().getSerializable("bean");
            submit = (Submit) getActivity();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_to_read, container, false);
        unbinder = ButterKnife.bind(this, view);
        tvIndex.setText(index + 1 + "/" + size);
        viewLeft.setVisibility(index == 0 ? View.VISIBLE : View.GONE);
        viewRight.setVisibility(index == size - 1 ? View.VISIBLE : View.GONE);
        tvEnContent.setText(bookSegmentBean.getText());
        eventPlay = SharedPreferencesUtil.getHuibenRead(getContext());
        if (eventPlay.getPosition() == index) {
            if (eventPlay.isPlay()) {
                ivPlay.setImageResource(R.drawable.iv_reading);
            } else {
                ivPlay.setImageResource(R.drawable.iv_to_read_play);
            }
        }
        eventRecord = SharedPreferencesUtil.getHuibenRecord(getContext());
        if (eventRecord.getPosition() == index) {
            if (eventRecord.isRecprding()) {
                Glide.with(getContext()).load(R.drawable.record).into(ivRecord);
            } else {
                Glide.with(getContext()).load(R.drawable.iv_recording).into(ivRecord);
            }
        }
        return view;
    }

    /**
     * 播放
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(EventPlay eventPlay) {
        if (index != eventPlay.getPosition()) {
            if (eventPlay.isPlay()) {  //其他iv显示不播放
                ivPlay.setImageResource(R.drawable.iv_to_read_play);
            }
        }
    }

    /**
     * 录音
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(EventRecord eventRecord) {
        if (eventRecord != null) {
            if (index != eventRecord.getPosition()) {
                if (eventRecord.isRecprding()) {  //其他iv显示不录音
                    if (isResumed()) {
                        Glide.with(getActivity().getApplicationContext()).load(R.drawable.iv_recording).into(ivRecord);
                    }
                }
            }
        }
    }

    /**
     * 改变上面颜色
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(String nowIndex) {
        if (nowIndex.contains("readPos")) {
                int nowPos = Integer.valueOf(nowIndex.replace("readPos", ""));
                if (index == nowPos) {
                    TextViewUtils.setTextColor(tvIndex, tvIndex.getText().toString(), 0, (index + 1 + "").length(), Color.parseColor("#15CF30"));
                } else {
                    TextViewUtils.setTextColor(tvIndex, tvIndex.getText().toString(), 0, (index + 1 + "").length(), Color.BLACK);
                }
        }
    }

    public static FragmentToRead newInstance(int i, int size, HuiBenRepeatBean.DataBean.BookSegmentListBean bookSegmentBean) {
        FragmentToRead tabFragment = new FragmentToRead();
        Bundle bundle = new Bundle();
        bundle.putInt("index", i);
        bundle.putInt("size", size);
        bundle.putSerializable("bean", bookSegmentBean);
        tabFragment.setArguments(bundle);
        return tabFragment;
    }


    @OnClick({R.id.iv_play, R.id.iv_record})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_play:
                submit.clickPlay(view, index);
                break;
            case R.id.iv_record:
                submit.clickRecord(view, index);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(FragmentToRead.this);
    }

    public interface Submit {
        void clickPlay(View view, int pos);  //播放

        void clickRecord(View view, int pos); //录音点击
    }

}
