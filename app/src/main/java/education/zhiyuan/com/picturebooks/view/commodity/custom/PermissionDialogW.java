package education.zhiyuan.com.picturebooks.view.commodity.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import education.zhiyuan.com.picturebooks.R;


/**
 * Created by Spring on 2017/7/24.
 */


public class PermissionDialogW extends Dialog implements View.OnClickListener {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.tv_right)
    TextView tvRight;
    private Context mContext;
    private String content;

    private String title;

    private OnClickListener onClickListener;


    public PermissionDialogW(Context context) {
        super(context);
        this.mContext = context;
    }

    public PermissionDialogW(Context context, String title, OnClickListener onClickListener) {
        super(context);
        this.mContext = context;
        this.title = title;
        this.onClickListener = onClickListener;
    }


    protected PermissionDialogW(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public PermissionDialogW setTitle(String title) {
        this.title = title;
        return this;
    }

    public PermissionDialogW setContent(String content) {
        this.content = content;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permission_dialog_w);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(true);
        tvLeft.setOnClickListener(this);
        tvRight.setOnClickListener(this);
//        tvTitle.setText(title);
//        tvContent.setText("由于无法获取"+title+",此功能不能正常运行，请开启权限后，再继续操作。");
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_left:
                onClickListener.cancle();
                break;
            case R.id.tv_right:
                onClickListener.toSetting();
                break;
        }
    }

    public interface OnClickListener {
        void cancle();

        void toSetting();
    }
}
