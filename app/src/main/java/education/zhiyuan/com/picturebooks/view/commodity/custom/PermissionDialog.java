package education.zhiyuan.com.picturebooks.view.commodity.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import education.zhiyuan.com.picturebooks.R;


/**
 * Created by Spring on 2017/7/24.
 */

public class PermissionDialog extends Dialog {
    private Context mContext;
    private String content;

    private String title;
    private TextView tv_title;
    private TextView tv_content;

    public PermissionDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public PermissionDialog(Context context, String title) {
        super(context );
        this.mContext = context;
        this.title = title;
    }


    protected PermissionDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public PermissionDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permission_dialog);
        setCanceledOnTouchOutside(true);
        tv_title = findViewById(R.id.tv_title);
        tv_content = findViewById(R.id.tv_content);
        tv_title.setText("请允许"+title);
        tv_content.setText("由于无法获取"+title+"，此功能不能正常运行，请开启权限后，再继续操作。");
    }

}
