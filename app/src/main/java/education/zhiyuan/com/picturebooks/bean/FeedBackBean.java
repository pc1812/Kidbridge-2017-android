package education.zhiyuan.com.picturebooks.bean;

import java.util.List;

/**
 * Created by Spring on 2017/8/11.
 */

public class FeedBackBean {

    /**
     * event : SUCCESS
     * data : []
     * describe :
     */

    private String event;
    private String describe;
    private List<?> data;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
