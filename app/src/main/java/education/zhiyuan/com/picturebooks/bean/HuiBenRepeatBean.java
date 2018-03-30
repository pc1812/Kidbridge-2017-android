package education.zhiyuan.com.picturebooks.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Spring on 2017/8/15.
 * 绘本跟读
 */

public class HuiBenRepeatBean implements Serializable {


    /**
     * event : SUCCESS
     * data : {"id":17,"name":"Clifford\u2019s Field Day[9-12]","repeatActiveTime":600,"bookSegmentList":[{"id":87,"icon":"FiAaneQFiaMnJ13XEkXyOYxrg0wL","text":"[\"Clifford\u2019s Field Day\",\"Clifford的户外活动日\"]","audio":"FgcsBweG8qoMGZfxNOHs8dP_On8j"},{"id":88,"icon":"FnEfU9p5ftV38WhF34LsiRa-RAje","text":"[\"Clifford is excited for Field Day.\",\"Clifford非常期待这次的户外运动\"]","audio":"FpkcBbShSAyjbwo69jHIBekFHZgl"},{"id":89,"icon":"FuR6KGrj3ZNOC7_GOtHCNLoi9HZg","text":"[\"There will be games.\",\"这一天会组织游戏\",\"There will be races.\",\"还会会举办比赛\"]","audio":"Fl5mIbIzcLl9EQUkJl4Q9KmAm7TX"}]}
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
         * id : 17
         * name : Clifford’s Field Day[9-12]
         * repeatActiveTime : 600
         * bookSegmentList : [{"id":87,"icon":"FiAaneQFiaMnJ13XEkXyOYxrg0wL","text":"[\"Clifford\u2019s Field Day\",\"Clifford的户外活动日\"]","audio":"FgcsBweG8qoMGZfxNOHs8dP_On8j"},{"id":88,"icon":"FnEfU9p5ftV38WhF34LsiRa-RAje","text":"[\"Clifford is excited for Field Day.\",\"Clifford非常期待这次的户外运动\"]","audio":"FpkcBbShSAyjbwo69jHIBekFHZgl"},{"id":89,"icon":"FuR6KGrj3ZNOC7_GOtHCNLoi9HZg","text":"[\"There will be games.\",\"这一天会组织游戏\",\"There will be races.\",\"还会会举办比赛\"]","audio":"Fl5mIbIzcLl9EQUkJl4Q9KmAm7TX"}]
         */

        private int id;
        private String name;
        private int repeatActiveTime;
        private List<BookSegmentListBean> bookSegmentList;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getRepeatActiveTime() {
            return repeatActiveTime;
        }

        public void setRepeatActiveTime(int repeatActiveTime) {
            this.repeatActiveTime = repeatActiveTime;
        }

        public List<BookSegmentListBean> getBookSegmentList() {
            return bookSegmentList;
        }

        public void setBookSegmentList(List<BookSegmentListBean> bookSegmentList) {
            this.bookSegmentList = bookSegmentList;
        }

        public static class BookSegmentListBean implements Serializable{
            /**
             * id : 87
             * icon : FiAaneQFiaMnJ13XEkXyOYxrg0wL
             * text : ["Clifford’s Field Day","Clifford的户外活动日"]
             * audio : FgcsBweG8qoMGZfxNOHs8dP_On8j
             */

            private int id;
            private String icon;
            private String text;
            private String audio;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getAudio() {
                return audio;
            }

            public void setAudio(String audio) {
                this.audio = audio;
            }
        }
    }
}
