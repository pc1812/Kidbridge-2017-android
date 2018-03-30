package education.zhiyuan.com.picturebooks.http;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Spring on 2017/8/2.
 */

public class LoadApkUtils {

    public String loadApk(String path, String filePath) {
        try {
            URL url = new URL(path);
            // 连接对象
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置连接方式
            conn.setRequestMethod("POST");
            // 超时处理
            conn.setReadTimeout(5 * 1000);
            conn.setConnectTimeout(5 * 1000);
            // 建立实际连接
            conn.connect();
            // 接收数据
            if (conn.getResponseCode() == 200) {
                // 输入流，读信息
                InputStream is = conn.getInputStream();
                FileOutputStream fos = new FileOutputStream(filePath);
                byte[] buf = new byte[1024 * 10];
                int len = 0;
                while ((len = is.read(buf)) != -1) {
                    fos.write(buf, 0, len);
                    fos.flush();
                }
                fos.close();
                is.close();
                return filePath;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


}

