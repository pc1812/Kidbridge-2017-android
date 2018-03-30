package education.zhiyuan.com.picturebooks.bean;

import java.io.Serializable;

/**
 * Created by Spring on 2017/8/28.
 */

public class AddressBean implements Serializable{

    /**
     * event : SUCCESS
     * data : {"phone":"18767174822","street":"佰富","contact":"11111","region":"北京市-北京市-昌平区"}
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
         * phone : 18767174822
         * street : 佰富
         * contact : 11111
         * region : 北京市-北京市-昌平区
         */

        private String phone;
        private String street;
        private String contact;
        private String region;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }
    }
}
