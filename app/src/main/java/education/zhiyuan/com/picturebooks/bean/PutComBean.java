package education.zhiyuan.com.picturebooks.bean;

/**
 * Created by LH on 2017/9/18.
 */

public class PutComBean {

    /**
     * id : 49
     * text : 差不多了。
     * audio : {"source":"FizMGEyPzgFPYu3orpGXkgdBJkch","time":1238}
     */

    private int id;
    private String text;
    private AudioBean audio;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public AudioBean getAudio() {
        return audio;
    }

    public void setAudio(AudioBean audio) {
        this.audio = audio;
    }

    public static class AudioBean {
        /**
         * source : FizMGEyPzgFPYu3orpGXkgdBJkch
         * time : 1238
         */

        private String source;
        private int time;

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }
    }
}
