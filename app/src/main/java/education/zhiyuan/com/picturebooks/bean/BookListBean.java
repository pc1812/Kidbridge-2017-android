package education.zhiyuan.com.picturebooks.bean;

import java.util.List;

/**
 * Created by Spring on 2017/8/14.
 * 书单
 */

public class BookListBean {

    /**
     * event : SUCCESS
     * data : [{"id":1,"name":"梦幻西游","icon":"Fg3Bl_3WEcH3a6KB4dBddJ7HL0Cw","fit":1,"tag":["友善","爱国"]},{"id":2,"name":"王者荣耀","icon":"FqyXflMnPLRnQkz7qpuOeFlMm4L_","fit":0,"tag":["友善","爱国"]}]
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
         * id : 1
         * name : 梦幻西游
         * icon : Fg3Bl_3WEcH3a6KB4dBddJ7HL0Cw
         * fit : 1
         * tag : ["友善","爱国"]
         */

        private int id;
        private String name;
        private String icon;
        private int fit;
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

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getFit() {
            return fit;
        }

        public void setFit(int fit) {
            this.fit = fit;
        }

        public List<String> getTag() {
            return tag;
        }

        public void setTag(List<String> tag) {
            this.tag = tag;
        }
    }
}
