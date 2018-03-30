package education.zhiyuan.com.picturebooks.bean;

import java.util.List;

/**
 * Created by Spring on 2017/8/10.
 * 轮播图
 */

public class WhirligigBean {

    /**
     * event : SUCCESS
     * data : [{"link":"http://www.baidu.com","icon":"FupRhT_SNTOfvta1aopBlZ0zQURK"},{"link":"http://www.baidu.com","icon":"FqBkWeMpnAdaaakckKZEJNh__3pR"},{"link":"http://www.baidu.com","icon":"Fq_heGnd8fWSFX9vYLVj7Rmm2-p8"}]
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
         * link : http://www.baidu.com
         * icon : FupRhT_SNTOfvta1aopBlZ0zQURK
         */

        private String link;
        private String icon;

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }
}
