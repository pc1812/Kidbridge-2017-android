package education.zhiyuan.com.picturebooks.http;

/**
 * Created by Spring on 2017/8/2.
 */

public interface HttpCallBack {
    void onSuccess(String jsonStr);
    void onFaile(String msg);
}
