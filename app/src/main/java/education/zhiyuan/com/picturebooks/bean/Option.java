package education.zhiyuan.com.picturebooks.bean;

/**
 * Created by Spring on 2017/8/17.
 */

public class Option {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Option(int id) {

        this.id = id;
    }

    private int id;

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public Option(String repeat) {

        this.repeat = repeat;
    }

    private String repeat;
}
