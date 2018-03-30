package education.zhiyuan.com.picturebooks.bean;

import java.util.List;

/**
 * Created by Spring on 2017/8/21.
 * 绘本赏析
 */

public class Appreciation {
    /**
     * event : SUCCESS
     * data : {"book":{"id":187,"name":"Phonics Tales th-The Thing That Went Thump ( 779 )","copyright":{"user":{"id":-1}},"outline":"Theo晚上睡觉前一直听到门外砰砰砰的声音，他害怕极了，他躲在被子里想象到一系列可怕的事情，结果进来的却是...","bookSegmentList":[{"id":4944,"icon":"Fv2MmzRdEPRJ3Zh7zFLADIKC3gDt","audio":"FmiZmmNljqYD-l9s60QSkTnlNarg"},{"id":4945,"icon":"FtdXs2iVJ9FBCECuc79a-TsL6ZEq","audio":"Fr6Ku6HDHRv35PWKv8pGdIuWZAIy"},{"id":4946,"icon":"FrEff7IzGJDRkF-as-O7TemV4F5W","audio":"Fmi3pzzv6pvgefnmwHKODNGycUfg"},{"id":4939,"icon":"FguzBOIDakw6lwEsjXdknKpABiBu","audio":"Ft8LlBqutB2XG_MpEzti7It70BK0"},{"id":4940,"icon":"FgigkKu1hfsfuoqCvkMhkyttt8KQ","audio":"Fs1Ur9xIkcovxmKQTeRddio33EY3"},{"id":4941,"icon":"FrpGiILvUsQ8NXXShpWgUIiEjv4O","audio":"FraLjunBFEcIvbkAZom9OQDvPpoT"},{"id":4942,"icon":"FrqeSMy113raJoS6DvOkCzZzGIPD","audio":"FphsOyVvJVNt7qJZf5MTMjfiXoyE"},{"id":4943,"icon":"FrkOPcf68o3LGGjsQKhhMi7cfqgH","audio":"FtcTXs2UlGsoBfMt3f644qeoXAD_"}],"audio":"FkBojcq-wUsHQ-0U3jfRxQRzYaX7"}}
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
         * book : {"id":187,"name":"Phonics Tales th-The Thing That Went Thump ( 779 )","copyright":{"user":{"id":-1}},"outline":"Theo晚上睡觉前一直听到门外砰砰砰的声音，他害怕极了，他躲在被子里想象到一系列可怕的事情，结果进来的却是...","bookSegmentList":[{"id":4944,"icon":"Fv2MmzRdEPRJ3Zh7zFLADIKC3gDt","audio":"FmiZmmNljqYD-l9s60QSkTnlNarg"},{"id":4945,"icon":"FtdXs2iVJ9FBCECuc79a-TsL6ZEq","audio":"Fr6Ku6HDHRv35PWKv8pGdIuWZAIy"},{"id":4946,"icon":"FrEff7IzGJDRkF-as-O7TemV4F5W","audio":"Fmi3pzzv6pvgefnmwHKODNGycUfg"},{"id":4939,"icon":"FguzBOIDakw6lwEsjXdknKpABiBu","audio":"Ft8LlBqutB2XG_MpEzti7It70BK0"},{"id":4940,"icon":"FgigkKu1hfsfuoqCvkMhkyttt8KQ","audio":"Fs1Ur9xIkcovxmKQTeRddio33EY3"},{"id":4941,"icon":"FrpGiILvUsQ8NXXShpWgUIiEjv4O","audio":"FraLjunBFEcIvbkAZom9OQDvPpoT"},{"id":4942,"icon":"FrqeSMy113raJoS6DvOkCzZzGIPD","audio":"FphsOyVvJVNt7qJZf5MTMjfiXoyE"},{"id":4943,"icon":"FrkOPcf68o3LGGjsQKhhMi7cfqgH","audio":"FtcTXs2UlGsoBfMt3f644qeoXAD_"}],"audio":"FkBojcq-wUsHQ-0U3jfRxQRzYaX7"}
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
             * id : 187
             * name : Phonics Tales th-The Thing That Went Thump ( 779 )
             * copyright : {"user":{"id":-1}}
             * outline : Theo晚上睡觉前一直听到门外砰砰砰的声音，他害怕极了，他躲在被子里想象到一系列可怕的事情，结果进来的却是...
             * bookSegmentList : [{"id":4944,"icon":"Fv2MmzRdEPRJ3Zh7zFLADIKC3gDt","audio":"FmiZmmNljqYD-l9s60QSkTnlNarg"},{"id":4945,"icon":"FtdXs2iVJ9FBCECuc79a-TsL6ZEq","audio":"Fr6Ku6HDHRv35PWKv8pGdIuWZAIy"},{"id":4946,"icon":"FrEff7IzGJDRkF-as-O7TemV4F5W","audio":"Fmi3pzzv6pvgefnmwHKODNGycUfg"},{"id":4939,"icon":"FguzBOIDakw6lwEsjXdknKpABiBu","audio":"Ft8LlBqutB2XG_MpEzti7It70BK0"},{"id":4940,"icon":"FgigkKu1hfsfuoqCvkMhkyttt8KQ","audio":"Fs1Ur9xIkcovxmKQTeRddio33EY3"},{"id":4941,"icon":"FrpGiILvUsQ8NXXShpWgUIiEjv4O","audio":"FraLjunBFEcIvbkAZom9OQDvPpoT"},{"id":4942,"icon":"FrqeSMy113raJoS6DvOkCzZzGIPD","audio":"FphsOyVvJVNt7qJZf5MTMjfiXoyE"},{"id":4943,"icon":"FrkOPcf68o3LGGjsQKhhMi7cfqgH","audio":"FtcTXs2UlGsoBfMt3f644qeoXAD_"}]
             * audio : FkBojcq-wUsHQ-0U3jfRxQRzYaX7
             */

            private int id;
            private String name;
            private CopyrightBean copyright;
            private String outline;
            private String audio;
            private List<BookSegmentListBean> bookSegmentList;

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

            public CopyrightBean getCopyright() {
                return copyright;
            }

            public void setCopyright(CopyrightBean copyright) {
                this.copyright = copyright;
            }

            public String getOutline() {
                return outline;
            }

            public void setOutline(String outline) {
                this.outline = outline;
            }

            public String getAudio() {
                return audio;
            }

            public void setAudio(String audio) {
                this.audio = audio;
            }

            public List<BookSegmentListBean> getBookSegmentList() {
                return bookSegmentList;
            }

            public void setBookSegmentList(List<BookSegmentListBean> bookSegmentList) {
                this.bookSegmentList = bookSegmentList;
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

            public static class BookSegmentListBean {
                /**
                 * id : 4944
                 * icon : Fv2MmzRdEPRJ3Zh7zFLADIKC3gDt
                 * audio : FmiZmmNljqYD-l9s60QSkTnlNarg
                 */

                private int id;
                private String icon;
                private String audio;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getIcon() {
                    return icon;
                }

                public void setIcon(String icon) {
                    this.icon = icon;
                }

                public String getAudio() {
                    return audio;
                }

                public void setAudio(String audio) {
                    this.audio = audio;
                }
            }
        }
    }


//    /**
//     * event : SUCCESS
//     * data : {"book":{"id":16,"name":"Clifford\u2019s Field Day[6-8]","copyright":{"user":{"id":14}},"outline":"大红狗要参加运动会啦！真是让人激动啊，套袋赛跑，两人三足，跨栏障碍赛，高低杠，翻筋斗，拔河，还有打棒球呢！想知道Clifford最后有没有赢得比赛？一起来看看吧！","bookSegmentList":[{"id":58,"icon":"FnEfU9p5ftV38WhF34LsiRa-RAje","audio":"FpkcBbShSAyjbwo69jHIBekFHZgl"},{"id":59,"icon":"FuR6KGrj3ZNOC7_GOtHCNLoi9HZg","audio":"Fl5mIbIzcLl9EQUkJl4Q9KmAm7TX"},{"id":57,"icon":"FiAaneQFiaMnJ13XEkXyOYxrg0wL","audio":"FgcsBweG8qoMGZfxNOHs8dP_On8j"}]}}
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
//         * book : {"id":16,"name":"Clifford\u2019s Field Day[6-8]","copyright":{"user":{"id":14}},"outline":"大红狗要参加运动会啦！真是让人激动啊，套袋赛跑，两人三足，跨栏障碍赛，高低杠，翻筋斗，拔河，还有打棒球呢！想知道Clifford最后有没有赢得比赛？一起来看看吧！","bookSegmentList":[{"id":58,"icon":"FnEfU9p5ftV38WhF34LsiRa-RAje","audio":"FpkcBbShSAyjbwo69jHIBekFHZgl"},{"id":59,"icon":"FuR6KGrj3ZNOC7_GOtHCNLoi9HZg","audio":"Fl5mIbIzcLl9EQUkJl4Q9KmAm7TX"},{"id":57,"icon":"FiAaneQFiaMnJ13XEkXyOYxrg0wL","audio":"FgcsBweG8qoMGZfxNOHs8dP_On8j"}]}
//         */
//
//        private BookBean book;
//
//        public BookBean getBook() {
//            return book;
//        }
//
//        public void setBook(BookBean book) {
//            this.book = book;
//        }
//
//        public static class BookBean {
//            /**
//             * id : 16
//             * name : Clifford’s Field Day[6-8]
//             * copyright : {"user":{"id":14}}
//             * outline : 大红狗要参加运动会啦！真是让人激动啊，套袋赛跑，两人三足，跨栏障碍赛，高低杠，翻筋斗，拔河，还有打棒球呢！想知道Clifford最后有没有赢得比赛？一起来看看吧！
//             * bookSegmentList : [{"id":58,"icon":"FnEfU9p5ftV38WhF34LsiRa-RAje","audio":"FpkcBbShSAyjbwo69jHIBekFHZgl"},{"id":59,"icon":"FuR6KGrj3ZNOC7_GOtHCNLoi9HZg","audio":"Fl5mIbIzcLl9EQUkJl4Q9KmAm7TX"},{"id":57,"icon":"FiAaneQFiaMnJ13XEkXyOYxrg0wL","audio":"FgcsBweG8qoMGZfxNOHs8dP_On8j"}]
//             */
//
//            private int id;
//            private String name;
//            private CopyrightBean copyright;
//            private String outline;
//            private List<BookSegmentListBean> bookSegmentList;
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
//            public CopyrightBean getCopyright() {
//                return copyright;
//            }
//
//            public void setCopyright(CopyrightBean copyright) {
//                this.copyright = copyright;
//            }
//
//            public String getOutline() {
//                return outline;
//            }
//
//            public void setOutline(String outline) {
//                this.outline = outline;
//            }
//
//            public List<BookSegmentListBean> getBookSegmentList() {
//                return bookSegmentList;
//            }
//
//            public void setBookSegmentList(List<BookSegmentListBean> bookSegmentList) {
//                this.bookSegmentList = bookSegmentList;
//            }
//
//            public static class CopyrightBean {
//                /**
//                 * user : {"id":14}
//                 */
//
//                private UserBean user;
//
//                public UserBean getUser() {
//                    return user;
//                }
//
//                public void setUser(UserBean user) {
//                    this.user = user;
//                }
//
//                public static class UserBean {
//                    /**
//                     * id : 14
//                     */
//
//                    private int id;
//
//                    public int getId() {
//                        return id;
//                    }
//
//                    public void setId(int id) {
//                        this.id = id;
//                    }
//                }
//            }
//
//            public static class BookSegmentListBean {
//                /**
//                 * id : 58
//                 * icon : FnEfU9p5ftV38WhF34LsiRa-RAje
//                 * audio : FpkcBbShSAyjbwo69jHIBekFHZgl
//                 */
//
//                private int id;
//                private String icon;
//                private String audio;
//
//                public int getId() {
//                    return id;
//                }
//
//                public void setId(int id) {
//                    this.id = id;
//                }
//
//                public String getIcon() {
//                    return icon;
//                }
//
//                public void setIcon(String icon) {
//                    this.icon = icon;
//                }
//
//                public String getAudio() {
//                    return audio;
//                }
//
//                public void setAudio(String audio) {
//                    this.audio = audio;
//                }
//            }
//        }
//    }



}
