package education.zhiyuan.com.picturebooks.bean;

import java.util.List;

/**
 * Created by Spring on 2017/8/23.
 * 课程打卡
 */

public class CourseSignBean {

    /**
     * event : SUCCESS
     * data : {"id":13,"name":"叫什么名字好呢","courseDetailList":[{"id":23,"book":{"id":8,"icon":["Fs2SC0bnWgsIdN6kwlils2uNRmIK"]},"cycle":7,"startTime":1502726400000},{"id":24,"book":{"id":10,"icon":["FqyXflMnPLRnQkz7qpuOeFlMm4L_"]},"cycle":7,"startTime":1503331200000},{"id":25,"book":{"id":9,"icon":["Fg3Bl_3WEcH3a6KB4dBddJ7HL0Cw"]},"cycle":3,"startTime":1503936000000},{"id":26,"book":{"id":11,"icon":["Fsksd_hDeyKlbls7LXP4S6Xcd_-G"]},"cycle":7,"startTime":1504195200000}]}
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
         * id : 13
         * name : 叫什么名字好呢
         * courseDetailList : [{"id":23,"book":{"id":8,"icon":["Fs2SC0bnWgsIdN6kwlils2uNRmIK"]},"cycle":7,"startTime":1502726400000},{"id":24,"book":{"id":10,"icon":["FqyXflMnPLRnQkz7qpuOeFlMm4L_"]},"cycle":7,"startTime":1503331200000},{"id":25,"book":{"id":9,"icon":["Fg3Bl_3WEcH3a6KB4dBddJ7HL0Cw"]},"cycle":3,"startTime":1503936000000},{"id":26,"book":{"id":11,"icon":["Fsksd_hDeyKlbls7LXP4S6Xcd_-G"]},"cycle":7,"startTime":1504195200000}]
         */

        private int id;
        private String name;
        private List<CourseDetailListBean> courseDetailList;

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

        public List<CourseDetailListBean> getCourseDetailList() {
            return courseDetailList;
        }

        public void setCourseDetailList(List<CourseDetailListBean> courseDetailList) {
            this.courseDetailList = courseDetailList;
        }

        public static class CourseDetailListBean {
            /**
             * id : 23
             * book : {"id":8,"icon":["Fs2SC0bnWgsIdN6kwlils2uNRmIK"]}
             * cycle : 7
             * startTime : 1502726400000
             */

            private int id;
            private BookBean book;
            private int cycle;
            private long startTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public BookBean getBook() {
                return book;
            }

            public void setBook(BookBean book) {
                this.book = book;
            }

            public int getCycle() {
                return cycle;
            }

            public void setCycle(int cycle) {
                this.cycle = cycle;
            }

            public long getStartTime() {
                return startTime;
            }

            public void setStartTime(long startTime) {
                this.startTime = startTime;
            }

            public static class BookBean {
                /**
                 * id : 8
                 * icon : ["Fs2SC0bnWgsIdN6kwlils2uNRmIK"]
                 */

                private int id;
                private List<String> icon;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public List<String> getIcon() {
                    return icon;
                }

                public void setIcon(List<String> icon) {
                    this.icon = icon;
                }
            }
        }
    }
}
