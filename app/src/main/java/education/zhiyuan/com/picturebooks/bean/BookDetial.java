package education.zhiyuan.com.picturebooks.bean;

import java.util.List;

/**
 * Created by Spring on 2017/8/14.
 * 书单详情
 */

public class BookDetial {
    /**
     * event : SUCCESS
     * data : {"id":25,"name":"Curious George","cover":{"icon":"FmOuT_CIcbR1lVWJX0fUxXTh-wb7","link":"http://api.kidbridge.org/article/21"},"bookList":[{"id":143,"name":"Curious George Goes to the Hospital [Part1] (528)","icon":["FvGRU-cLUHYb-UWJY2wJUzqagGrX"],"price":10,"fit":4,"tag":["动物","故事"],"lock":0},{"id":144,"name":"Curious George Goes to the Hospital [Part2] (529)","icon":["FlNhL2VMHdpclCYSvDo3PH09TCK1"],"price":10,"fit":3,"tag":["动物","故事"],"lock":0},{"id":145,"name":"Curious George Goes to the Hospital[Part3] (530)","icon":["Fn7lv5O8mB_hKWJGJ7E8sR8jL5b5"],"price":10,"fit":4,"tag":["动物","故事"],"lock":0}]}
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
         * id : 25
         * name : Curious George
         * cover : {"icon":"FmOuT_CIcbR1lVWJX0fUxXTh-wb7","link":"http://api.kidbridge.org/article/21"}
         * bookList : [{"id":143,"name":"Curious George Goes to the Hospital [Part1] (528)","icon":["FvGRU-cLUHYb-UWJY2wJUzqagGrX"],"price":10,"fit":4,"tag":["动物","故事"],"lock":0},{"id":144,"name":"Curious George Goes to the Hospital [Part2] (529)","icon":["FlNhL2VMHdpclCYSvDo3PH09TCK1"],"price":10,"fit":3,"tag":["动物","故事"],"lock":0},{"id":145,"name":"Curious George Goes to the Hospital[Part3] (530)","icon":["Fn7lv5O8mB_hKWJGJ7E8sR8jL5b5"],"price":10,"fit":4,"tag":["动物","故事"],"lock":0}]
         */

        private int id;
        private String name;
        private CoverBean cover;
        private List<BookListBean> bookList;

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

        public CoverBean getCover() {
            return cover;
        }

        public void setCover(CoverBean cover) {
            this.cover = cover;
        }

        public List<BookListBean> getBookList() {
            return bookList;
        }

        public void setBookList(List<BookListBean> bookList) {
            this.bookList = bookList;
        }

        public static class CoverBean {
            /**
             * icon : FmOuT_CIcbR1lVWJX0fUxXTh-wb7
             * link : http://api.kidbridge.org/article/21
             */

            private String icon;
            private String link;

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }
        }

        public static class BookListBean {
            /**
             * id : 143
             * name : Curious George Goes to the Hospital [Part1] (528)
             * icon : ["FvGRU-cLUHYb-UWJY2wJUzqagGrX"]
             * price : 10.0
             * fit : 4
             * tag : ["动物","故事"]
             * lock : 0
             */

            private int id;
            private String name;
            private double price;
            private int fit;
            private int lock;
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

            public int getLock() {
                return lock;
            }

            public void setLock(int lock) {
                this.lock = lock;
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


//    /**
//     * event : SUCCESS
//     * data : {"id":1,"name":"梦幻西游","cover":{"icon":"Fsksd_hDeyKlbls7LXP4S6Xcd_-G","link":"http://api.dev.51zhiyuan.net/article/1"},"bookList":[{"id":8,"name":"寻找维尼","icon":["Fs2SC0bnWgsIdN6kwlils2uNRmIK"],"price":39.9,"fit":0,"tag":["教育"]},{"id":10,"name":"彼得兔的故事","icon":["FqyXflMnPLRnQkz7qpuOeFlMm4L_"],"price":29.8,"fit":0,"tag":["和谐"]},{"id":11,"name":"点点点","icon":["Fsksd_hDeyKlbls7LXP4S6Xcd_-G"],"price":0,"fit":1,"tag":["关爱"]}]}
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
//         * id : 1
//         * name : 梦幻西游
//         * cover : {"icon":"Fsksd_hDeyKlbls7LXP4S6Xcd_-G","link":"http://api.dev.51zhiyuan.net/article/1"}
//         * bookList : [{"id":8,"name":"寻找维尼","icon":["Fs2SC0bnWgsIdN6kwlils2uNRmIK"],"price":39.9,"fit":0,"tag":["教育"]},{"id":10,"name":"彼得兔的故事","icon":["FqyXflMnPLRnQkz7qpuOeFlMm4L_"],"price":29.8,"fit":0,"tag":["和谐"]},{"id":11,"name":"点点点","icon":["Fsksd_hDeyKlbls7LXP4S6Xcd_-G"],"price":0,"fit":1,"tag":["关爱"]}]
//         */
//
//        private int id;
//        private String name;
//        private CoverBean cover;
//        private List<BookListBean> bookList;
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
//        public CoverBean getCover() {
//            return cover;
//        }
//
//        public void setCover(CoverBean cover) {
//            this.cover = cover;
//        }
//
//        public List<BookListBean> getBookList() {
//            return bookList;
//        }
//
//        public void setBookList(List<BookListBean> bookList) {
//            this.bookList = bookList;
//        }
//
//        public static class CoverBean {
//            /**
//             * icon : Fsksd_hDeyKlbls7LXP4S6Xcd_-G
//             * link : http://api.dev.51zhiyuan.net/article/1
//             */
//
//            private String icon;
//            private String link;
//
//            public String getIcon() {
//                return icon;
//            }
//
//            public void setIcon(String icon) {
//                this.icon = icon;
//            }
//
//            public String getLink() {
//                return link;
//            }
//
//            public void setLink(String link) {
//                this.link = link;
//            }
//        }
//
//        public static class BookListBean {
//            /**
//             * id : 8
//             * name : 寻找维尼
//             * icon : ["Fs2SC0bnWgsIdN6kwlils2uNRmIK"]
//             * price : 39.9
//             * fit : 0
//             * tag : ["教育"]
//             */
//
//            private int id;
//            private String name;
//            private double price;
//            private int fit;
//            private List<String> icon;
//            private List<String> tag;
//
//            public int getId() {
//                return id;
//            }
//
//            public void setId(int id) {
//                this.id = id;
//            }
//
//            public String getName() {
//                return name;
//            }
//
//            public void setName(String name) {
//                this.name = name;
//            }
//
//            public double getPrice() {
//                return price;
//            }
//
//            public void setPrice(double price) {
//                this.price = price;
//            }
//
//            public int getFit() {
//                return fit;
//            }
//
//            public void setFit(int fit) {
//                this.fit = fit;
//            }
//
//            public List<String> getIcon() {
//                return icon;
//            }
//
//            public void setIcon(List<String> icon) {
//                this.icon = icon;
//            }
//
//            public List<String> getTag() {
//                return tag;
//            }
//
//            public void setTag(List<String> tag) {
//                this.tag = tag;
//            }
//        }
//    }
}
