package education.zhiyuan.com.picturebooks.bean;

/**
 * Created by LH on 2018/2/10.
 * Description ：hahah
 */

public class VersionCheck {

    /**
     * event : SUCCESS
     * data : {"number":"1.0.1","content":"ltzYOMS5tjNRAa31g2tSYOgARDy2"}
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
         * number : 1.0.1
         * content : ltzYOMS5tjNRAa31g2tSYOgARDy2
         */

        private String number;
        private String content;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
