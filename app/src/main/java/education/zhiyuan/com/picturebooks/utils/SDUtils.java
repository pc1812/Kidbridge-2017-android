package education.zhiyuan.com.picturebooks.utils;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Created by Spring on 2017/7/11.
 */

public class SDUtils {

    public static boolean isSdCardExist() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    //写数据到SD中的文件
    public static void writeFileSdcardFile(String fileName, String data) {
        if (isSdCardExist()) {
            try {
                String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/hb/" + fileName;
                File file = new File(SDPath);
                //第二个参数意义是说是否以append方式添加内容
                BufferedWriter bw = new BufferedWriter(new FileWriter(file, false));
                bw.write(data);
                bw.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    //读SD中的文件
    public static String readFileSdcardFile(String fileName) {
        if (isSdCardExist()) {
            String readline = "";
            StringBuffer sb = new StringBuffer();
            try {
                String SDPath = Environment.getExternalStorageDirectory().getPath() + "//" + fileName;
                File file = new File(SDPath);
                if (file.exists()) {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    while ((readline = br.readLine()) != null) {
                        sb.append(readline);
                    }
                    br.close();
                } else {
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return sb.toString();
        } else {
            return null;
        }
    }
}