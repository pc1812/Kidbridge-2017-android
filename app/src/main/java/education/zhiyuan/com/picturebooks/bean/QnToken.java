package education.zhiyuan.com.picturebooks.bean;

/**
 * Created by Spring on 2017/8/17.
 */

public class QnToken {

    /**
     * event : SUCCESS
     * data : {"token":"jJEBx6Xxlz-7vmKhIAcx73Pbkxv5kcdEP4u3lSS1:jDl7vp7yv-V7MJLsfH6YtH1kVno=:eyJzY29wZSI6InBpY3R1cmVib29rIiwiZGVhZGxpbmUiOjE1MDI5NDE1NTV9"}
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
         * token : jJEBx6Xxlz-7vmKhIAcx73Pbkxv5kcdEP4u3lSS1:jDl7vp7yv-V7MJLsfH6YtH1kVno=:eyJzY29wZSI6InBpY3R1cmVib29rIiwiZGVhZGxpbmUiOjE1MDI5NDE1NTV9
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
