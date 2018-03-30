package education.zhiyuan.com.picturebooks.utils;

import com.qiniu.android.common.AutoZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;

/**
 * Created by LH on 2017/10/21.
 * Description ：
 * data = "File对象、或 文件路径、或 字节数组";  //文件路径
 * key = "指定七牛服务上的文件名，或 null";  //上传文件的key值 如果指定，则不会生成唯一的key值，所以一般填null
 * token = "从服务端SDK获取"; //服务器请求key值
 */

public class QnUtils {
    private static UploadManager uploadManager;
    private QnCallBack callBack;

    public QnUtils(QnCallBack callBack) {
        this.callBack = callBack;
        initQn();
    }

    public void initQn() {
        Configuration config = new Configuration.Builder()
                .connectTimeout(10)        // 链接超时。默认10秒
                .responseTimeout(60)      // 服务器响应超时。默认60秒
                .zone(AutoZone.autoZone)  //制定区域
                .build();
        // 重用uploadManager。一般地，只需要创建一个uploadManager对象
        if (uploadManager == null) {
            uploadManager = new UploadManager(config);
        }
    }

    public void upload(String data, String key, String token) {
        //上传  为确保存储的key值唯一性，一般设置为null    uploadManager.put(data, null, token,
        uploadManager.put(data, key, token,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {  //上传回调函数
                        //res包含hash、key等信息，具体字段取决于上传策略的设置
                        if (info.isOK()) {
                            callBack.uploadSuccess(key, info, res);
                        } else {
                            callBack.uploadFail(key, info, res);
                        }
                    }
                }, new UploadOptions(null, null, false, new UpProgressHandler() {   //上传进度
                    @Override
                    public void progress(String key, double percent) {
                    }
                }, null));

    }

    public interface QnCallBack {
        void uploadSuccess(String key, ResponseInfo info, JSONObject res);

        void uploadFail(String key, ResponseInfo info, JSONObject res);
    }
}
