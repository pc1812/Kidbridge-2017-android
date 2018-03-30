package education.zhiyuan.com.picturebooks.bean;

import java.io.Serializable;

/**
 * Created by Spring on 2017/7/27.
 */

public class RecordBean implements Serializable{
    private String time;
    private String filePath;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public RecordBean(String time, String filePath, String fileName) {

        this.time = time;
        this.filePath = filePath;
        this.fileName = fileName;
    }

    private String fileName;



}
