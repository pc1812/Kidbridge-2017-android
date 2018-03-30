package education.zhiyuan.com.picturebooks.bean;

import java.util.List;

/**
 * Created by Spring on 2017/8/14.
 */

public class MyLessonBean {
    /**
     * event : SUCCESS
     * data : [{"id":19,"lock":1,"name":"运动会系列","icon":["FiAaneQFiaMnJ13XEkXyOYxrg0wL"],"fit":1,"enter":1,"limit":3,"price":123.4,"cycle":21,"status":1,"tag":["动物","运动","故事"]}]
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
         * id : 19
         * lock : 1
         * name : 运动会系列
         * icon : ["FiAaneQFiaMnJ13XEkXyOYxrg0wL"]
         * fit : 1
         * enter : 1
         * limit : 3
         * price : 123.4
         * cycle : 21
         * status : 1
         * tag : ["动物","运动","故事"]
         */

        private int id;
        private int lock;
        private String name;
        private int fit;
        private int enter;
        private int limit;
        private double price;
        private int cycle;
        private int status;
        private List<String> icon;
        private List<String> tag;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLock() {
            return lock;
        }

        public void setLock(int lock) {
            this.lock = lock;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getFit() {
            return fit;
        }

        public void setFit(int fit) {
            this.fit = fit;
        }

        public int getEnter() {
            return enter;
        }

        public void setEnter(int enter) {
            this.enter = enter;
        }

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getCycle() {
            return cycle;
        }

        public void setCycle(int cycle) {
            this.cycle = cycle;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
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

//    /**
//     * event : SUCCESS
//     * data : [{"id":13,"name":"叫什么名字好呢","icon":["FkYPf_ows4KltHX06oZo3ehTOXZ_","Fsksd_hDeyKlbls7LXP4S6Xcd_-G"],"fit":0,"enter":1,"limit":9,"price":99.9,"cycle":28,"status":0,"tag":["健康","有爱"]}]
//     * describe :
//     */
//
//    private String event;
//    private String describe;
//    private List<DataBean> data;
//
//    public String getEvent() {
//        return event;
//    }
//
//    public void setEvent(String event) {
//        this.event = event;
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
//    public List<DataBean> getData() {
//        return data;
//    }
//
//    public void setData(List<DataBean> data) {
//        this.data = data;
//    }
//
//    public static class DataBean {
//        /**
//         * id : 13
//         * name : 叫什么名字好呢
//         * icon : ["FkYPf_ows4KltHX06oZo3ehTOXZ_","Fsksd_hDeyKlbls7LXP4S6Xcd_-G"]
//         * fit : 0
//         * enter : 1
//         * limit : 9
//         * price : 99.9
//         * cycle : 28
//         * status : 0
//         * tag : ["健康","有爱"]
//         */
//
//        private int id;
//        private String name;
//        private int fit;
//        private int enter;
//        private int limit;
//        private double price;
//        private int cycle;
//        private int status;
//        private List<String> icon;
//        private List<String> tag;
//
//        public int getId() {
//            return id;
//        }
//
//        public void setId(int id) {
//            this.id = id;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public int getFit() {
//            return fit;
//        }
//
//        public void setFit(int fit) {
//            this.fit = fit;
//        }
//
//        public int getEnter() {
//            return enter;
//        }
//
//        public void setEnter(int enter) {
//            this.enter = enter;
//        }
//
//        public int getLimit() {
//            return limit;
//        }
//
//        public void setLimit(int limit) {
//            this.limit = limit;
//        }
//
//        public double getPrice() {
//            return price;
//        }
//
//        public void setPrice(double price) {
//            this.price = price;
//        }
//
//        public int getCycle() {
//            return cycle;
//        }
//
//        public void setCycle(int cycle) {
//            this.cycle = cycle;
//        }
//
//        public int getStatus() {
//            return status;
//        }
//
//        public void setStatus(int status) {
//            this.status = status;
//        }
//
//        public List<String> getIcon() {
//            return icon;
//        }
//
//        public void setIcon(List<String> icon) {
//            this.icon = icon;
//        }
//
//        public List<String> getTag() {
//            return tag;
//        }
//
//        public void setTag(List<String> tag) {
//            this.tag = tag;
//        }
//    }

}
