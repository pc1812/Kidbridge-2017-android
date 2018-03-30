package education.zhiyuan.com.picturebooks.bean;

/**
 * Created by Spring on 2017/8/11.
 */

public class MyInfo {
    /**
     * event : SUCCESS
     * data : {"phone":"+8618767174822","head":"FhJDMOsQF_B0LS36KpDuK8tFJ9oR","nickname":"哈哈哈","birthday":920217600000,"receivingContact":"22365","receivingPhone":"18767174822","receivingRegion":"北京市-北京市-昌平区","receivingStreet":"佰富12"}
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
         * phone : +8618767174822
         * head : FhJDMOsQF_B0LS36KpDuK8tFJ9oR
         * nickname : 哈哈哈
         * birthday : 920217600000
         * receivingContact : 22365
         * receivingPhone : 18767174822
         * receivingRegion : 北京市-北京市-昌平区
         * receivingStreet : 佰富12
         */

        private String phone;
        private String head;
        private String nickname;
        private long birthday;
        private String receivingContact;
        private String receivingPhone;
        private String receivingRegion;
        private String receivingStreet;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public long getBirthday() {
            return birthday;
        }

        public void setBirthday(long birthday) {
            this.birthday = birthday;
        }

        public String getReceivingContact() {
            return receivingContact;
        }

        public void setReceivingContact(String receivingContact) {
            this.receivingContact = receivingContact;
        }

        public String getReceivingPhone() {
            return receivingPhone;
        }

        public void setReceivingPhone(String receivingPhone) {
            this.receivingPhone = receivingPhone;
        }

        public String getReceivingRegion() {
            return receivingRegion;
        }

        public void setReceivingRegion(String receivingRegion) {
            this.receivingRegion = receivingRegion;
        }

        public String getReceivingStreet() {
            return receivingStreet;
        }

        public void setReceivingStreet(String receivingStreet) {
            this.receivingStreet = receivingStreet;
        }
    }

//    /**
//     * event : SUCCESS
//     * data : {"phone":"+8618767174822","head":"","nickname":"","birthday":-28800000,"receivingContact":"","receivingPhone":"","receivingRegion":"","receivingStreet":""}
//     * describe :
//     */
//
//    private String event;
//    private DataBean data;
//    private String describe;
//
//    public String getEvent() {
//        return event;
//    }
//
//    public void setEvent(String event) {
//        this.event = event;
//    }
//
//    public DataBean getData() {
//        return data;
//    }
//
//    public void setData(DataBean data) {
//        this.data = data;
//    }
//
//    public String getDescribe() {
//        return describe;
//    }
//
//    public void setDescribe(String describe) {
//        this.describe = describe;
//    }
//
//    public static class DataBean {
//        /**
//         * phone : +8618767174822
//         * head :
//         * nickname :
//         * birthday : -28800000
//         * receivingContact :
//         * receivingPhone :
//         * receivingRegion :
//         * receivingStreet :
//         */
//
//        private String phone;
//        private String head;
//        private String nickname;
//        private int birthday;
//        private String receivingContact;
//        private String receivingPhone;
//        private String receivingRegion;
//        private String receivingStreet;
//
//        public String getPhone() {
//            return phone;
//        }
//
//        public void setPhone(String phone) {
//            this.phone = phone;
//        }
//
//        public String getHead() {
//            return head;
//        }
//
//        public void setHead(String head) {
//            this.head = head;
//        }
//
//        public String getNickname() {
//            return nickname;
//        }
//
//        public void setNickname(String nickname) {
//            this.nickname = nickname;
//        }
//
//        public int getBirthday() {
//            return birthday;
//        }
//
//        public void setBirthday(int birthday) {
//            this.birthday = birthday;
//        }
//
//        public String getReceivingContact() {
//            return receivingContact;
//        }
//
//        public void setReceivingContact(String receivingContact) {
//            this.receivingContact = receivingContact;
//        }
//
//        public String getReceivingPhone() {
//            return receivingPhone;
//        }
//
//        public void setReceivingPhone(String receivingPhone) {
//            this.receivingPhone = receivingPhone;
//        }
//
//        public String getReceivingRegion() {
//            return receivingRegion;
//        }
//
//        public void setReceivingRegion(String receivingRegion) {
//            this.receivingRegion = receivingRegion;
//        }
//
//        public String getReceivingStreet() {
//            return receivingStreet;
//        }
//
//        public void setReceivingStreet(String receivingStreet) {
//            this.receivingStreet = receivingStreet;
//        }
//    }
}
