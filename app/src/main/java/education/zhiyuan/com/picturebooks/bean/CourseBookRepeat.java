package education.zhiyuan.com.picturebooks.bean;

import java.util.List;

/**
 * Created by LH on 2017/9/18.
 */

public class CourseBookRepeat {

    /**
     * event : SUCCESS
     * data : [{"fit":0,"repeat":[{"date":1505318400000,"id":9},{"date":1505232000000,"id":10}],"name":"寻找维尼","icon":["Fs2SC0bnWgsIdN6kwlils2uNRmIK"],"id":8,"tag":["教育"]},{"fit":0,"repeat":[{"date":1505059200000,"id":11}],"name":"他们都看见了一只猫","icon":["FkYPf_ows4KltHX06oZo3ehTOXZ_"],"id":12,"tag":["健康"]}]
     * describe :
     */

    private String event;
    private String describe;
    private List<DataBean> data;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * fit : 0
         * repeat : [{"date":1505318400000,"id":9},{"date":1505232000000,"id":10}]
         * name : 寻找维尼
         * icon : ["Fs2SC0bnWgsIdN6kwlils2uNRmIK"]
         * id : 8
         * tag : ["教育"]
         */

        private int fit;
        private String name;
        private int id;
        private List<RepeatBean> repeat;
        private List<String> icon;
        private List<String> tag;

        public int getFit() {
            return fit;
        }

        public void setFit(int fit) {
            this.fit = fit;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<RepeatBean> getRepeat() {
            return repeat;
        }

        public void setRepeat(List<RepeatBean> repeat) {
            this.repeat = repeat;
        }

        public List<String> getIcon() {
            return icon;
        }

        public void setIcon(List<String> icon) {
            this.icon = icon;
        }

        public List<String> getTag() {
            return tag;
        }

        public void setTag(List<String> tag) {
            this.tag = tag;
        }

        public static class RepeatBean {
            /**
             * date : 1505318400000
             * id : 9
             */

            private long date;
            private int id;

            public long getDate() {
                return date;
            }

            public void setDate(long date) {
                this.date = date;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }
    }
}
