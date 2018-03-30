package education.zhiyuan.com.picturebooks.bean;

/**
 * Created by Spring on 2017/8/11.
 * 水滴
 */

public class BonusBean {

    /**
     * event : SUCCESS
     * data : {"bonus":123}
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
         * bonus : 123
         */

        private int bonus;

        public int getBonus() {
            return bonus;
        }

        public void setBonus(int bonus) {
            this.bonus = bonus;
        }
    }
}
