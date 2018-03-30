package education.zhiyuan.com.picturebooks.view.commodity.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.utils.EditByteFilter;

/**
 * Created by LH on 2017/9/7.
 * 签名dialog
 */

public class SignDialog extends Dialog implements View.OnClickListener {

    @BindView(R.id.et_sign)
    EditText etSign;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    @BindView(R.id.tv)
    TextView tv;

    private Context mContext;
    private String title;
    private OnCloseListener listener;
    private String reward;
    private String edText;
    private String content;

    public SignDialog(Context context, int themeResId, String title, String content, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.listener = listener;
        this.title = title;
        this.content = content;
    }

    public SignDialog(Context context, int themeResId, String title, String content) {
        super(context, themeResId);
        this.mContext = context;
        this.title = title;
        this.content = content;
    }

    public void setListener(OnCloseListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sign);
        setCanceledOnTouchOutside(false);
        ButterKnife.bind(this);
        tv.setText(title);
        etSign.setHint(content);
        if (title.equals("昵称")) {
            etSign.setFilters(new InputFilter[]{new EditByteFilter(50)});
        }
        setCanceledOnTouchOutside(false);
        initView();
    }


    private void initView() {

        tvSure.setOnClickListener(this);
        tvCancle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sure:
                if (listener != null) {
                    listener.onClick(this, true, etSign);
                }
                this.dismiss();
                break;
            case R.id.tv_cancle:
                if (listener != null) {
                    listener.onClick(this, false, etSign);
                }
                this.dismiss();
                break;
        }
    }

    public interface OnCloseListener {
        void onClick(Dialog dialog, boolean confirm, EditText et);
    }

}