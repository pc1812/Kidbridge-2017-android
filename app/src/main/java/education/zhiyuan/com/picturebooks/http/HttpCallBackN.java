package education.zhiyuan.com.picturebooks.http;

/**
 * Created by LH on 2017/9/19.
 */

public interface HttpCallBackN {
    void onSuccess( int type,String str);

    void onError(String msg);

}
