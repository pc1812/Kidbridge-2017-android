package education.zhiyuan.com.picturebooks.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by LH on 2017/9/29.
 * 推送消息
 */

public class JGPushBean extends DataSupport {
    private String tag;//用户标记
    private long createTime;
    private String text;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
