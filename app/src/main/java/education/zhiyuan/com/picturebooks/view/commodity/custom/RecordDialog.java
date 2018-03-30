package education.zhiyuan.com.picturebooks.view.commodity.custom;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
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
public class RecordDialog extends ProgressDialog {
    private ImageView iv_loading;
    AnimationDrawable animationDrawable;
    private TextView tv;

    public void setMsg(String msg) {
        this.msg = msg;
        tv.setText(msg);
    }

    private String msg;

    public RecordDialog(Context context) {
        super(context);
    }

    public RecordDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(getContext());
        iv_loading = findViewById(R.id.pb_load);
        animationDrawable = (AnimationDrawable) iv_loading.getDrawable();
        tv = findViewById(R.id.tv_load_dialog);
    }

    private void init(Context context) {
        //设置不可取消，点击其他区域不能取消
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.recording_dialog);
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