package education.zhiyuan.com.picturebooks.http;

import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by LH on 2017/9/21.
 */

public class HttpLoadMu {
    public static String load(String loadPath, String fPath) {
        // 线程数
        int threadCount = 6;
        try {
            // 创建连接
            URL url = new URL(loadPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.connect();
            // 响应成功
            if (conn.getResponseCode() == 200) {
                // 获取连接文件的大小
                int length = conn.getContentLength();
                //
                RandomAccessFile file = new RandomAccessFile(fPath, "rw");
                file.setLength(length);
                // 设置每个线程下载的长度
                int avgLen = length / threadCount;
                // 使用线程下载
                for (int i = 0; i < threadCount; i++) {
                    // 最后一个线程，要下载至文件尾部
                    if (i == threadCount - 1) {
                        new HrcFileM(loadPath, fPath, i * avgLen, length).start();
                    }
                    new HrcFileM(loadPath, fPath, i * avgLen, (i + 1) * avgLen)
                            .start();
                }
                return fPath;
            } else {
                return null;
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}

