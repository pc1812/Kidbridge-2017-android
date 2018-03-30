package education.zhiyuan.com.picturebooks.bean;

/**
 * Created by Spring on 2017/8/22.
 */

public class EventPlay {

    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isPlay() {
        return isPlay;
    }

    public void setPlay(boolean play) {
        isPlay = play;
    }

    public EventPlay(int position, boolean isPlay) {

        this.position = position;
        this.isPlay = isPlay;
    }

    private boolean isPlay;

    public EventPlay() {
    }
}
