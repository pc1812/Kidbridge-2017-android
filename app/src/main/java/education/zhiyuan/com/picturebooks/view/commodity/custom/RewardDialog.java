package education.zhiyuan.com.picturebooks.view.commodity.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import education.zhiyuan.com.picturebooks.R;


/**
 * Created by Spring on 2017/6/26.
 */

public class RewardDialog extends Dialog implements View.OnClickListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_reward)
    EditText etReward;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    private Context mContext;


    private String title;
    private OnCloseListener listener;
    private String reward;
   private String edText;



    public RewardDialog(Context context,int themeResId, OnCloseListener listener) {
        super(context,themeResId);
        this.mContext = context;
        this.listener = listener;
    }

   public  RewardDialog setEText(String text){
       this.edText=text;
       return this;
   }

    public RewardDialog setTitle(String title) {
        this.title = title;
        return this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reward);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }
        if (!TextUtils.isEmpty(edText)){
            etReward.setHint(edText);
        }
        tvSure.setOnClickListener(this);
        tvCancle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sure:
                if (listener != null) {
                    listener.onClick(this, true, etReward);
                }

                break;
            case R.id.tv_cancle:
                if (listener != null) {
                    listener.onClick(this, false, etReward);
                }
                this.dismiss();
                break;
        }
    }

    public interface OnCloseListener {
        void onClick(Dialog dialog, boolean confirm, EditText et);
    }
}
