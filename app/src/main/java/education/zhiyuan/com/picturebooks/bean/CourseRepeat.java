package education.zhiyuan.com.picturebooks.bean;

import java.util.List;

/**
 * Created by LH on 2017/9/18.
 * 我的跟读-课程
 */

public class CourseRepeat {

    /**
     * event : SUCCESS
     * data : [{"userCourse":{"id":39,"course":{"id":16,"name":"不要觉得我糊弄","icon":["Fs2SC0bnWgsIdN6kwlils2uNRmIK"],"fit":1,"cycle":35,"status":1,"tag":["什么","鬼"]}}}]
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
         * userCourse : {"id":39,"course":{"id":16,"name":"不要觉得我糊弄","icon":["Fs2SC0bnWgsIdN6kwlils2uNRmIK"],"fit":1,"cycle":35,"status":1,"tag":["什么","鬼"]}}
         */

        private UserCourseBean userCourse;

        public UserCourseBean getUserCourse() {
            return userCourse;
        }

        public void setUserCourse(UserCourseBean userCourse) {
            this.userCourse = userCourse;
        }

        public static class UserCourseBean {
            /**
             * id : 39
             * course : {"id":16,"name":"不要觉得我糊弄","icon":["Fs2SC0bnWgsIdN6kwlils2uNRmIK"],"fit":1,"cycle":35,"status":1,"tag":["什么","鬼"]}
             */

            private int id;
            private CourseBean course;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public CourseBean getCourse() {
                return course;
            }

            public void setCourse(CourseBean course) {
                this.course = course;
            }

            public static class CourseBean {
                /**
                 * id : 16
                 * name : 不要觉得我糊弄
                 * icon : ["Fs2SC0bnWgsIdN6kwlils2uNRmIK"]
                 * fit : 1
                 * cycle : 35
                 * status : 1
                 * tag : ["什么","鬼"]
                 */

                private int id;
                private String name;
                private int fit;
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
        }
    }
}
