package education.zhiyuan.com.picturebooks.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import education.zhiyuan.com.picturebooks.MainActivity;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.utils.SharedPreferencesUtil;

/**
 * Created by Lance on 2017/6/16.
 * Email : COCOINUT@163.com
 * Introduce : 欢迎页
 */

public class WelcomeActivity extends AppCompatActivity {

    private static final int ANIM_TIME = 2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.layout_welcome);
        handler.sendEmptyMessageDelayed(0, ANIM_TIME);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (SharedPreferencesUtil.getLoginState(getApplicationContext())) {
                        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));//MainActivity
                    } else {
                        startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));//MainActivity
                    }
                    finish();
                    break;
            }
            super.handleMessage(msg);
        }
    };
}
