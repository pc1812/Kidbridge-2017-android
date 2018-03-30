package education.zhiyuan.com.picturebooks.bean;

/**
 * Created by Spring on 2017/9/1.
 */

public class AliBean {

    /**
     * event : SUCCESS
     * data : {"payment":"alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2016122704667500&biz_content=%7B%22out_trade_no%22%3A%2217083100016%22%2C%22passback_params%22%3A%22%7B%5C%22id%5C%22%3A13%7D%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E8%97%A4%E6%A1%A5%E6%95%99%E8%82%B2-%E4%BD%99%E9%A2%9D%E5%85%85%E5%80%BC%22%2C%22total_amount%22%3A%2213.14%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&sign=HZABM7r8iqTFZEm4qBbTaGabbUfWZ%2F5JG91qcjKFefOgG8eWJmqd39dT0UwjMG0SpYtDRR1bVRNtcP%2B3JL%2FrBuhHyOelm9U3EWfnA%2FyxlBwYjZwTuWgx517LKqnR8Y6XM62WR6UnXFgbfMx57z%2BjKTAmI%2Bu0OE7%2BUf0v8w8xbJNAEcBDfZBACsUAR6sR%2FM19y9ZpIylOlrEsxWwgjlhAXaJYOcI48agoYfq8p9wqh3nu0F%2BGDBL06hF7SrlVKzj1CFD%2FkEmk4AZRLey%2FdLUysJxXLCNTnSJBVzM4WDUqatQjOE%2BA9yCO7m4pXSZHBMsiMmqb6xUxIjIwYEwsd37g%2Fg%3D%3D&sign_type=RSA2&timestamp=2017-08-31+18%3A39%3A30&version=1.0"}
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
         * payment : alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2016122704667500&biz_content=%7B%22out_trade_no%22%3A%2217083100016%22%2C%22passback_params%22%3A%22%7B%5C%22id%5C%22%3A13%7D%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E8%97%A4%E6%A1%A5%E6%95%99%E8%82%B2-%E4%BD%99%E9%A2%9D%E5%85%85%E5%80%BC%22%2C%22total_amount%22%3A%2213.14%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&sign=HZABM7r8iqTFZEm4qBbTaGabbUfWZ%2F5JG91qcjKFefOgG8eWJmqd39dT0UwjMG0SpYtDRR1bVRNtcP%2B3JL%2FrBuhHyOelm9U3EWfnA%2FyxlBwYjZwTuWgx517LKqnR8Y6XM62WR6UnXFgbfMx57z%2BjKTAmI%2Bu0OE7%2BUf0v8w8xbJNAEcBDfZBACsUAR6sR%2FM19y9ZpIylOlrEsxWwgjlhAXaJYOcI48agoYfq8p9wqh3nu0F%2BGDBL06hF7SrlVKzj1CFD%2FkEmk4AZRLey%2FdLUysJxXLCNTnSJBVzM4WDUqatQjOE%2BA9yCO7m4pXSZHBMsiMmqb6xUxIjIwYEwsd37g%2Fg%3D%3D&sign_type=RSA2&timestamp=2017-08-31+18%3A39%3A30&version=1.0
         */

        private String payment;

        public String getPayment() {
            return payment;
        }

        public void setPayment(String payment) {
            this.payment = payment;
        }
    }
}
