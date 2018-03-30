package education.zhiyuan.com.picturebooks.bean;

/**
 * Created by LH on 2017/9/23.
 * 跟读提交
 */

public class ReadPutBean {

    /**
     * event : SUCCESS
     * data : {"repeat":29,"type":1}
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
         * repeat : 29
         * type : 1
         */

        private int repeat;
        private int type;

        public int getRepeat() {
            return repeat;
        }

        public void setRepeat(int repeat) {
            this.repeat = repeat;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
