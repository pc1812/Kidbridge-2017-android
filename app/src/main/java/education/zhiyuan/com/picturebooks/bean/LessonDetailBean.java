package education.zhiyuan.com.picturebooks.bean;

import java.util.List;

/**
 * Created by Spring on 2017/8/16.
 * 课程详情
 */

public class LessonDetailBean {


    /**
     * event : SUCCESS
     * data : {"belong":38,"course":{"id":13,"copyright":{"user":{"id":-1}},"name":"叫什么名字好呢","icon":["FkYPf_ows4KltHX06oZo3ehTOXZ_","Fsksd_hDeyKlbls7LXP4S6Xcd_-G"],"fit":0,"enter":3,"limit":9,"price":99.9,"outline":"词穷","cycle":24,"status":1,"tag":["健康","有爱"]}}
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
         * belong : 38
         * course : {"id":13,"copyright":{"user":{"id":-1}},"name":"叫什么名字好呢","icon":["FkYPf_ows4KltHX06oZo3ehTOXZ_","Fsksd_hDeyKlbls7LXP4S6Xcd_-G"],"fit":0,"enter":3,"limit":9,"price":99.9,"outline":"词穷","cycle":24,"status":1,"tag":["健康","有爱"]}
         */

        private int belong;
        private CourseBean course;

        public int getBelong() {
            return belong;
        }

        public void setBelong(int belong) {
            this.belong = belong;
        }

        public CourseBean getCourse() {
            return course;
        }

        public void setCourse(CourseBean course) {
            this.course = course;
        }

        public static class CourseBean {
            /**
             * id : 13
             * copyright : {"user":{"id":-1}}
             * name : 叫什么名字好呢
             * icon : ["FkYPf_ows4KltHX06oZo3ehTOXZ_","Fsksd_hDeyKlbls7LXP4S6Xcd_-G"]
             * fit : 0
             * enter : 3
             * limit : 9
             * price : 99.9
             * outline : 词穷
             * cycle : 24
             * status : 1
             * tag : ["健康","有爱"]
             */

            private int id;
            private CopyrightBean copyright;
            private String name;
            private int fit;
            private int enter;
            private int limit;
            private double price;
            private String outline;
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

            public CopyrightBean getCopyright() {
                return copyright;
            }

            public void setCopyright(CopyrightBean copyright) {
                this.copyright = copyright;
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

            public String getOutline() {
                return outline;
            }

            public void setOutline(String outline) {
                this.outline = outline;
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

            public static class CopyrightBean {
                /**
                 * user : {"id":-1}
                 */

                private UserBean user;

                public UserBean getUser() {
                    return user;
                }

                public void setUser(UserBean user) {
                    this.user = user;
                }

                public static class UserBean {
                    /**
                     * id : -1
                     */

                    private int id;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }
                }
            }
        }
    }
}
