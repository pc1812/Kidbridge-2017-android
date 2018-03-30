package education.zhiyuan.com.picturebooks.http;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by LH on 2017/9/21.
 */

public class HrcFileM extends Thread {
    // 下载地址
    String path;
    // 保存地址
    String filePath;
    int start;
    int end;

    public HrcFileM(String path, String filePath, int start, int end) {
        this.path = path;
        this.filePath = filePath;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "开始");
        InputStream is = null;
        RandomAccessFile raf = null;
        try {
            // 创建连接
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 访问方式
            conn.setRequestMethod("GET");
            // 超时处理
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            // 下载从start到end这部分字节
            conn.setRequestProperty("range", "bytes=" + start + "-" + (end - 1));
            // 连接
            conn.connect();
            // 多线程，响应成功时--》206
            if (conn.getResponseCode() == 206) {
                // 移动至文件指定位置
                // 参1-->文件路径，，参2-->打开文件的方式 r->只读 rw->读写
                //当模式为rw时，当文件不存在时，会自己动创建文件，
                //当文件已经存在时 不会对原有文件进行覆盖。
                //调用此线程之前为了获取每个线程下载的长度，，已经创建
                raf = new RandomAccessFile(new File(filePath),
                        "rw");
                raf.seek(start);
                // 读

                is = conn.getInputStream();
                byte[] buf = new byte[1024];
                int num = 0;
                while ((num = is.read(buf)) != -1) {
                    raf.write(buf, 0, num);
                }
                System.out.println(Thread.currentThread().getName() + "下载完成");
            } else {
                System.out.println("响应失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            try {
                if (raf != null) {
                    raf.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}

