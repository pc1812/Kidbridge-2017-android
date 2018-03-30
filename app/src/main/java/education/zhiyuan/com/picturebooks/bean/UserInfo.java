package education.zhiyuan.com.picturebooks.bean;

/**
 * Created by Spring on 2017/8/2.
 * 用户信息，用来保存登录成功后的信息
 */

public class UserInfo {
    private String token;
    private String phone;

    public UserInfo(String token, String phone, String nickname, String headIcon, String pwd) {
        this.token = token;
        this.phone = phone;
        this.nickname = nickname;
        this.headIcon = headIcon;
        this.pwd = pwd;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
    }

    private String nickname;
    private String headIcon;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public UserInfo(String token, String phone, String pwd) {
        this.token = token;
        this.phone = phone;
        this.pwd = pwd;
    }

    public UserInfo() {
    }

    private String pwd;
}
