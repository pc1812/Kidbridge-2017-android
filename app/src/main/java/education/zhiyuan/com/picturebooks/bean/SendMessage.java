package education.zhiyuan.com.picturebooks.bean;

/**
 * Created by Spring on 2017/7/26.
 */

public class SendMessage {
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String success;

    public SendMessage(String msg, String success, String token) {
        this.msg = msg;
        this.success = success;
        this.token = token;
    }

    private String token;
}
