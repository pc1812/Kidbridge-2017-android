package education.zhiyuan.com.picturebooks.view.commodity.selectaddress;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.view.commodity.selectaddress.adapters.ArrayWheelAdapter;
import education.zhiyuan.com.picturebooks.view.commodity.selectaddress.view.OnWheelOneChangedListener;
import education.zhiyuan.com.picturebooks.view.commodity.selectaddress.view.WheelViewOne;


/**
 * Created by zidan on 2017/11/2.
 */

public class SelectOneWindow extends PopupWindow implements OnWheelOneChangedListener {
    private static final String TAG = "SelectAddressWindow";
    private View mMenuView;
    private WheelViewOne mViewProvince;
    private Context context;
    private TextView confirm, cancel;

    public String getSelectData() {
        return selectData;
    }

    public void setSelectData(String selectData) {
        this.selectData = selectData;
    }

    private String selectData;
    /**
     * 所有
     */
    protected String[] data;

    public SelectOneWindow(Context context, String[] data, View.OnClickListener listener) {
        super(context);
        this.context = context;
        this.data = data;
        LayoutInflater inflater = LayoutInflater.from(context);
        mMenuView = inflater.inflate(R.layout.view_select_one_pop, null);

        mViewProvince = mMenuView.findViewById(R.id.wheelView);

        confirm = mMenuView.findViewById(R.id.tv_ok);
        cancel = mMenuView.findViewById(R.id.tv_cancel);
        //添加取消按钮点击事件事件
        cancel.setOnClickListener(listener);
        confirm.setOnClickListener(listener);
        setUpData();
        setUpListener();
        this.setContentView(mMenuView);

        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);

        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        this.setFocusable(true);

        this.setAnimationStyle(R.style.PopupAnimation);

        ColorDrawable dw = new ColorDrawable(0x00000000);

        this.setBackgroundDrawable(dw);


        mMenuView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });

    }

    @Override
    public void onChanged(WheelViewOne wheel, int oldValue, int newValue) {
        selectData = data[newValue];
    }

    private void updateAreas() {
        int pCurrent = mViewProvince.getCurrentItem();
        selectData = data[pCurrent];
    }

    private void setUpData() {
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<>(context, data));
        // 设置可见条目数量
        mViewProvince.setVisibleItems(5);
        updateAreas();
    }

    private void setUpListener() {
        // 添加change事件
        mViewProvince.addChangingListener(this);
    }

}
