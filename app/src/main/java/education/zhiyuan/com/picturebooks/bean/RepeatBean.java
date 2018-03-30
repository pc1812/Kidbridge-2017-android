package education.zhiyuan.com.picturebooks.bean;

import java.util.List;

/**
 * Created by Spring on 2017/8/18.
 * 我的跟读-绘本跟读
 */

public class RepeatBean {

    /**
     * event : SUCCESS
     * data : [{"id":23,"userBook":{"book":{"id":8,"name":"寻找维尼","icon":["Fs2SC0bnWgsIdN6kwlils2uNRmIK"],"fit":0,"tag":["教育"]}}}]
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
         * id : 23
         * userBook : {"book":{"id":8,"name":"寻找维尼","icon":["Fs2SC0bnWgsIdN6kwlils2uNRmIK"],"fit":0,"tag":["教育"]}}
         */

        private int id;
        private UserBookBean userBook;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public UserBookBean getUserBook() {
            return userBook;
        }

        public void setUserBook(UserBookBean userBook) {
            this.userBook = userBook;
        }

        public static class UserBookBean {
            /**
             * book : {"id":8,"name":"寻找维尼","icon":["Fs2SC0bnWgsIdN6kwlils2uNRmIK"],"fit":0,"tag":["教育"]}
             */

            private BookBean book;

            public BookBean getBook() {
                return book;
            }

            public void setBook(BookBean book) {
                this.book = book;
            }

            public static class BookBean {
                /**
                 * id : 8
                 * name : 寻找维尼
                 * icon : ["Fs2SC0bnWgsIdN6kwlils2uNRmIK"]
                 * fit : 0
                 * tag : ["教育"]
                 */

                private int id;
                private String name;
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
    }
}
