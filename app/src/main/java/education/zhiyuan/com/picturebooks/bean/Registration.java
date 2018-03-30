package education.zhiyuan.com.picturebooks.bean;

/**
 * Created by Spring on 2017/7/26.
 * 注册
 */

public class Registration {
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

    public Registration(String msg, String success) {

        this.msg = msg;
        this.success = success;
    }

    private String success;
}
