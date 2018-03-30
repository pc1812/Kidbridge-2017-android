package education.zhiyuan.com.picturebooks.bean;

import java.util.List;

/**
 * Created by Spring on 2017/8/17.
 * 提交绘本跟读信息
 */

public class PutDataBean {


    /**
     * repeat : {"segment":[{"id":6,"audio":{"source":"FizMGEyPzgFPYu3orpGXkgdBJkch","time":123}},{"id":7,"audio":{"source":"FizMGEyPzgFPYu3orpGXkgdBJkch","time":123}},{"id":8,"audio":{"source":"FizMGEyPzgFPYu3orpGXkgdBJkch","time":123}}],"token":"1d9d47b42b5a48c2b60bb900acd826dc"}
     */

    private RepeatBean repeat;

    public RepeatBean getRepeat() {
        return repeat;
    }

    public void setRepeat(RepeatBean repeat) {
        this.repeat = repeat;
    }

    public static class RepeatBean {
        /**
         * segment : [{"id":6,"audio":{"source":"FizMGEyPzgFPYu3orpGXkgdBJkch","time":123}},{"id":7,"audio":{"source":"FizMGEyPzgFPYu3orpGXkgdBJkch","time":123}},{"id":8,"audio":{"source":"FizMGEyPzgFPYu3orpGXkgdBJkch","time":123}}]
         * token : 1d9d47b42b5a48c2b60bb900acd826dc
         */

        private String token;
        private List<SegmentBean> segment;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public List<SegmentBean> getSegment() {
            return segment;
        }

        public void setSegment(List<SegmentBean> segment) {
            this.segment = segment;
        }

        public static class SegmentBean {
            /**
             * id : 6
             * audio : {"source":"FizMGEyPzgFPYu3orpGXkgdBJkch","time":123}
             */

            private int id;
            private AudioBean audio;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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
                 * time : 123
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
    }
}
