package education.zhiyuan.com.picturebooks.bean;

/**
 * Created by Spring on 2017/8/1.
 * 登录
 */

public class LoginBean {


    /**
     * event : SUCCESS
     * data : {"head":"FtDDWWDpZY9PjIjen0GmLCsRfnoR","nickname":"Hello ","id":"d91ff8397f2f41c3ec54fde0d304c539","token":"160a9dfd5ab645a7b1bc6914e096ac91"}
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
         * head : FtDDWWDpZY9PjIjen0GmLCsRfnoR
         * nickname : Hello
         * id : d91ff8397f2f41c3ec54fde0d304c539
         * token : 160a9dfd5ab645a7b1bc6914e096ac91
         */

        private String head;
        private String nickname;
        private String id;
        private String token;

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
