package education.zhiyuan.com.picturebooks.bean;

import java.util.List;

/**
 * Created by Spring on 2017/8/26.
 * 今日打卡
 */

public class SignTodayBean {


    /**
     * event : SUCCESS
     * data : {"sign":[{"head":"P70912-214725.jpg","createTime":1506302275000,"repeat":31,"nickname":"emmmm","id":13}]}
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
        private List<SignBean> sign;

        public List<SignBean> getSign() {
            return sign;
        }

        public void setSign(List<SignBean> sign) {
            this.sign = sign;
        }

        public static class SignBean {
            /**
             * head : P70912-214725.jpg
             * createTime : 1506302275000
             * repeat : 31
             * nickname : emmmm
             * id : 13
             */

            private String head;
            private long createTime;
            private int repeat;
            private String nickname;
            private int id;

            public String getHead() {
                return head;
            }

            public void setHead(String head) {
                this.head = head;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public int getRepeat() {
                return repeat;
            }

            public void setRepeat(int repeat) {
                this.repeat = repeat;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
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
