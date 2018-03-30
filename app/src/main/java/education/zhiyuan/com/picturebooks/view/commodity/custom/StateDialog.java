package education.zhiyuan.com.picturebooks.view.commodity.custom;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import education.zhiyuan.com.picturebooks.R;

/**
 * Created by Spring on 2017/8/5.
 */

/**
 * 加载提醒对话框
 */
public class StateDialog extends ProgressDialog {
    private ImageView iv_loading;
    AnimationDrawable animationDrawable;
    private String content;
    private TextView tvContent;

    public StateDialog(Context context) {
        super(context);
    }

    public StateDialog(Context context, int theme) {
        super(context, theme);
    }

    public StateDialog setContent(String content) {
        this.content = content;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(getContext());
        tvContent = findViewById(R.id.tv_load_dialog);
        if (!TextUtils.isEmpty(content)) {
            tvContent.setText(content);
        }
        iv_loading = findViewById(R.id.pb_load);
        animationDrawable = (AnimationDrawable) iv_loading.getDrawable();
    }

    private void init(Context context) {
        //设置不可取消，点击其他区域不能取消
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.progress_dialog_state);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);

    }

    @Override
    public void show() {
        super.show();
        if (!animationDrawable.isRunning()) {
            animationDrawable.start();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (animationDrawable.isRunning()) {
            animationDrawable.stop();
        }
    }
}

