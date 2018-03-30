package education.zhiyuan.com.picturebooks.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Spring on 2017/7/21.
 */

public class WeiPayBean {

    /**
     * event : SUCCESS
     * data : {"payment":{"package":"Sign=WXPay","appid":"wxdb11265ac6b10f86","sign":"8818F2E6A19CA982AE1A0F24E160E3FE","partnerid":"1428905702","prepayid":"wx20170831184122d4ca1d623d0151383644","noncestr":"6SBR1bnnlGCcbpPhOzW9Z16M4z6d9bts","timestamp":1504176079}}
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
         * payment : {"package":"Sign=WXPay","appid":"wxdb11265ac6b10f86","sign":"8818F2E6A19CA982AE1A0F24E160E3FE","partnerid":"1428905702","prepayid":"wx20170831184122d4ca1d623d0151383644","noncestr":"6SBR1bnnlGCcbpPhOzW9Z16M4z6d9bts","timestamp":1504176079}
         */

        private PaymentBean payment;

        public PaymentBean getPayment() {
            return payment;
        }

        public void setPayment(PaymentBean payment) {
            this.payment = payment;
        }

        public static class PaymentBean {
            /**
             * package : Sign=WXPay
             * appid : wxdb11265ac6b10f86
             * sign : 8818F2E6A19CA982AE1A0F24E160E3FE
             * partnerid : 1428905702
             * prepayid : wx20170831184122d4ca1d623d0151383644
             * noncestr : 6SBR1bnnlGCcbpPhOzW9Z16M4z6d9bts
             * timestamp : 1504176079
             */

            @SerializedName("package")
            private String packageX;
            private String appid;
            private String sign;
            private String partnerid;
            private String prepayid;
            private String noncestr;
            private int timestamp;

            public String getPackageX() {
                return packageX;
            }

            public void setPackageX(String packageX) {
                this.packageX = packageX;
            }

            public String getAppid() {
                return appid;
            }

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public String getPartnerid() {
                return partnerid;
            }

            public void setPartnerid(String partnerid) {
                this.partnerid = partnerid;
            }

            public String getPrepayid() {
                return prepayid;
            }

            public void setPrepayid(String prepayid) {
                this.prepayid = prepayid;
            }

            public String getNoncestr() {
                return noncestr;
            }

            public void setNoncestr(String noncestr) {
                this.noncestr = noncestr;
            }

            public int getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(int timestamp) {
                this.timestamp = timestamp;
            }
        }
    }
}
