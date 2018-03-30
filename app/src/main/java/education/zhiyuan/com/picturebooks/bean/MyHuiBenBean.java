package education.zhiyuan.com.picturebooks.bean;

import java.util.List;

/**
 * Created by Spring on 2017/8/11.
 * //我的绘本
 */

public class MyHuiBenBean {

    /**
     * event : SUCCESS
     * data : [{"id":9,"name":"暴风雨中的孩子","icon":["Fg3Bl_3WEcH3a6KB4dBddJ7HL0Cw"],"price":0,"fit":1,"tag":["家庭"]}]
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
         * id : 9
         * name : 暴风雨中的孩子
         * icon : ["Fg3Bl_3WEcH3a6KB4dBddJ7HL0Cw"]
         * price : 0.0
         * fit : 1
         * tag : ["家庭"]
         */

        private int id;
        private String name;
        private double price;
        private int fit;
        private List<String> icon;
        private List<String> tag;

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

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getFit() {
            return fit;
        }

        public void setFit(int fit) {
            this.fit = fit;
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
    }
}
