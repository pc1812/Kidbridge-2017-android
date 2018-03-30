package education.zhiyuan.com.picturebooks.view.commodity.custom;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import education.zhiyuan.com.picturebooks.R;


/**
 * Created by Spring on 2017/6/26.
 */

public class CusDialog extends Dialog implements View.OnClickListener {
    @BindView(R.id.tv_title)
    TextView contentTxt;
    @BindView(R.id.tv_left)
    TextView submitTxt;
    @BindView(R.id.tv_right)
    TextView cancelTxt;

    private Context mContext;
    private String content;
    private OnCloseListener listener;
    private String positiveName;
    private String negativeName;
    private String title;
    private String color;

    public CusDialog(Context context, int themeResId, String content, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
        this.listener = listener;
    }

    public CusDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public CusDialog setPositiveButton(String name) {
        this.positiveName = name;
        return this;
    }

    public CusDialog setNegativeButton(String name) {
        this.negativeName = name;
        return this;
    }

    public CusDialog setColor(String color) {
        this.color = color;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_one);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        initView();
    }

    private void initView() {
        submitTxt.setOnClickListener(this);
        cancelTxt.setOnClickListener(this);
        contentTxt.setText(content);
        if (!TextUtils.isEmpty(positiveName)) {
            submitTxt.setText(positiveName);
        }

        if (!TextUtils.isEmpty(negativeName)) {
            cancelTxt.setText(negativeName);
        }

        if (!TextUtils.isEmpty(color)) {
            submitTxt.setTextColor(Color.parseColor(color));
            cancelTxt.setTextColor(Color.parseColor(color));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                if (listener != null) {
                    listener.onClick(this, true);
                }
                this.dismiss();
                break;
            case R.id.tv_right:
                if (listener != null) {
                    listener.onClick(this, false);
                }
                this.dismiss();
                break;
        }
    }

    public interface OnCloseListener {
        void onClick(Dialog dialog, boolean confirm);
    }
}
