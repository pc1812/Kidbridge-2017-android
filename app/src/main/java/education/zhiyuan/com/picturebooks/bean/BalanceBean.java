package education.zhiyuan.com.picturebooks.bean;

/**
 * Created by Spring on 2017/8/11.
 */

public class BalanceBean {


    /**
     * event : SUCCESS
     * data : {"balance":66.88}
     * describe :
     *
     * {"event":"SUCCESS","data":{"balance":66.88},"describe":""}
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
         * balance : 66.88
         */

        private double balance;

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }
    }


}
