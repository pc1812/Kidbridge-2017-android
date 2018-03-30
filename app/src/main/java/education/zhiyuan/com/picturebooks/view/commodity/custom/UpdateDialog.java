package education.zhiyuan.com.picturebooks.view.commodity.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import education.zhiyuan.com.picturebooks.R;


/**
 * Created by Spring on 2017/7/24.
 */


public class UpdateDialog extends Dialog implements View.OnClickListener {
    private Context mContext;
    private String time, info, content;
    private TextView tvTime, tvInfo, tvContent, tvLater, tvNow;

    private UpdateClick updateClick;

    public UpdateDialog(Context context, UpdateClick updateClick) {
        super(context);
        this.mContext = context;
        this.updateClick = updateClick;
    }


    public UpdateDialog setTime(String time) {
        this.time = time;
        return this;
    }

    public UpdateDialog setInfo(String info) {
        this.info = info;
        return this;
    }

    public UpdateDialog setContent(String content) {
        this.content = content;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_dialog);
        setCanceledOnTouchOutside(false);
        tvContent = findViewById(R.id.tv_content);
        tvInfo = findViewById(R.id.tv_info);
        tvTime = findViewById(R.id.tv_time);
        tvLater = findViewById(R.id.tv_later);
        tvNow = findViewById(R.id.tv_now);

        tvLater.setOnClickListener(this);
        tvNow.setOnClickListener(this);

        if (!TextUtils.isEmpty(info)) {
            tvInfo.setText(info);
        }

        if (!TextUtils.isEmpty(time)) {
            tvTime.setText(time);
        }

        if (!TextUtils.isEmpty(content)) {
            tvContent.setText(content);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_later:
                updateClick.laterClick();
                break;
            case R.id.tv_now:
                updateClick.nowClick();
                break;
        }
    }

    public interface UpdateClick {
        void laterClick();

        void nowClick();
    }

}
