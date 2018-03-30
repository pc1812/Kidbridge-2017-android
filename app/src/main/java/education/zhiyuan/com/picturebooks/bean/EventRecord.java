package education.zhiyuan.com.picturebooks.bean;

/**
 * Created by LH on 2017/9/9.
 */

public class EventRecord {
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isRecprding() {
        return isRecprding;
    }

    public void setRecprding(boolean recprding) {
        isRecprding = recprding;
    }

    public EventRecord(int position, boolean isRecprding) {

        this.position = position;
        this.isRecprding = isRecprding;
    }

    public EventRecord() {
    }

    private boolean isRecprding;
}
