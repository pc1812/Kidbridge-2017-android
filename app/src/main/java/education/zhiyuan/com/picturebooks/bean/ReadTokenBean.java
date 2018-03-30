package education.zhiyuan.com.picturebooks.bean;

/**
 * Created by Spring on 2017/8/17.
 */

public class ReadTokenBean {

    /**
     * event : SUCCESS
     * data : {"token":"d6f7287647b64ad9bfc3de62e2276cb0"}
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
         * token : d6f7287647b64ad9bfc3de62e2276cb0
         */

        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
