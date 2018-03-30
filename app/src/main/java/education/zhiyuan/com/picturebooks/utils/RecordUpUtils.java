package education.zhiyuan.com.picturebooks.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import education.zhiyuan.com.picturebooks.bean.RecordBean;

/**
 * Created by LH on 2017/9/11.
 */

public class RecordUpUtils {
    public String forChange(Map<Integer, RecordBean> mapRecoder, String outPath, String sourcePath, String targetPath) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < mapRecoder.size(); i++) {
            list.add(mapRecoder.get(i).getFilePath());
        }
        try {
            M4aPra.appendMp4List(list,
                    Environment.getExternalStorageDirectory().getAbsolutePath() + "/hb/one_a" + System.currentTimeMillis() + ".amr");
            return toMp3(sourcePath, targetPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String toMp3(String sourcePath, String targetPath) {
        FileOutputStream fos = null;
        FileInputStream in = null;
        byte bs[] = new byte[1024 * 4];
        int len = 0;
        try {
            in = new FileInputStream(sourcePath);
            fos = new FileOutputStream(targetPath);
            while ((len = in.read(bs)) != -1) {
                fos.write(bs, 0, len);
                fos.flush();
            }
            fos.close();
            in.close();
            if (new File(targetPath).exists()) {
                return targetPath;
            } else {
                return null;
            }
        } catch (Exception e) {
            Log.e("aaaaa", "One: " + e.getMessage());
        }
        return null;
    }
}
