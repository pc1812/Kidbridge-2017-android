package education.zhiyuan.com.picturebooks.bean;

/**
 * Created by LH on 2017/12/16.
 * Description ：hahah
 */

public class HeadBean {

    /**
     * event : SUCCESS
     * data : {"head":"FroMr_1cl64yF04TlXVsoff4Z8_W"}
     * describe :
     */

    private String event;
    private DataBean data;
    private String describe;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public static class DataBean {
        /**
         * head : FroMr_1cl64yF04TlXVsoff4Z8_W
         */

        private String head;

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }
    }
}
