package education.zhiyuan.com.picturebooks.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by LH on 2017/9/29.
 * 搜索记录
 */

public class SecordRecordBean extends DataSupport {
    public SecordRecordBean(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private long id;
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public SecordRecordBean(String content) {
        this.content = content;
    }

    public String content;

}
