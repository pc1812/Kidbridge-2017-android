package education.zhiyuan.com.picturebooks.bean;

import java.util.List;

/**
 * Created by Spring on 2017/8/17.
 * 跟读详情
 */

public class ReadBean {
    /**
     * event : SUCCESS
     * data : {"repeat":{"id":11,"like":0,"userBook":{"user":{"id":54,"head":"Fur6UynsgLdEM5cVoBW_6fN3YvPl","nickname":"呵呵广告","signature":""},"book":{"id":140,"name":"Blueberries For Sal-part1 (619)","icon":["FmlRtOzuXl6s2xtJDd9vUqSWX-Bu"],"outline":"小Sal跟着妈妈去蓝莓山上采蓝莓，准备做成罐头，留着冬天吃。另一边的山坡上，一只小熊跟着熊妈妈来吃蓝莓，他们要在肚子里多储存些食物，好度过又冷又长的冬天。贪玩的小Sal和小熊迷了路，在蓝莓山上的蓝莓丛里，小熊和小Sal到底找到各自的妈妈没有呢？一起来绘本里寻找答案吧！"}},"segment":[{"id":2874,"audio":{"source":"Fk5DobKvRcUJxktZEmV1mXjCpCE4","time":1298}},{"id":2875,"audio":{"source":"FqJUxtTXkRq7GkE4x9m5lrXDzdHr","time":1172}},{"id":2876,"audio":{"source":"Fmb3I_DoS0nRzCGRex0QZtyx2Kbi","time":1077}},{"id":2877,"audio":{"source":"Flw2D_qNclae31Wlin2IZX3HXpYc","time":1149}},{"id":2878,"audio":{"source":"Ft2Up8tH2qFkYW8KnQlKMBhtM6l6","time":1289}},{"id":2879,"audio":{"source":"FnXkVoyeGJdNWkfpsyxu-_8xsT_X","time":1401}},{"id":2880,"audio":{"source":"FgfqBm_EbfEiN9i2_WkAvLY6xJig","time":1387}},{"id":2881,"audio":{"source":"FsWYZi9uLwqHa3VhxujtdhczUDXa","time":1541}},{"id":2882,"audio":{"source":"Fn65pDppVYz1qdoLH2A5wcpOWtMY","time":1696}},{"id":2883,"audio":{"source":"Fsg0mIfNeqVlpoPVqhZSp3p0Yp5C","time":1658}},{"id":2884,"audio":{"source":"FtC-JSQswaT9sdusroGfDFatupCh","time":1646}}],"createTime":1514515712000,"book":{"bookSegmentList":[{"id":2884,"icon":"FoAqFwhxjpx06L4iZhg0zfc9P6VD","audio":"Fpt16eb14sNJCXWsubN-GmVWr3t1"},{"id":2874,"icon":"FmlRtOzuXl6s2xtJDd9vUqSWX-Bu","audio":"Fm8EDpUoWsmBzeoZ8N93PFgxBIWr"},{"id":2875,"icon":"FnaSs_G2aSALEHVubwZEOo47OUwY","audio":"FhvbiabuUvVYHEVhUTUOtk6w0RIr"},{"id":2876,"icon":"FqPtEvODsDUlQc4qj5VFzPE930E8","audio":"FuaHgNmZ9XHluW9urwkzNCIcowXg"},{"id":2877,"icon":"FnZHwB5LYzKbHqllalSaz0KeQ2Ys","audio":"Fqx0t97V7iyOt7B7FVdf1M7u7gDH"},{"id":2878,"icon":"FvdQ7Ry8_3lpZPzP_pd3bMYpCbtW","audio":"FsDKw79Lde-mFfv9_nt5l668WXuD"},{"id":2879,"icon":"FkMt_yoL8l4S5l57vCo6MOxFiYpU","audio":"FodbbkIG6HHxofq5Addo5xTLqtTZ"},{"id":2880,"icon":"FsK8GEz9InuDf64FahDT7x6Jb9_V","audio":"Fh3kOdsDmTOgEe-3enbg36CWa1Mb"},{"id":2881,"icon":"FgbTvITL4cq-Mwsowd6djBu47Isn","audio":"Fu6PgqVAvxFc9CHnnQLXLAHZWeZ1"},{"id":2882,"icon":"Fou8aq4ry0s6bkttdmuPCblfu-9f","audio":"Fo0-Dgalm97cV7wHlyZOV3SR1o0I"},{"id":2883,"icon":"FqFroHzJuVC-Ynw--4wMxHQ-0oEb","audio":"Fk6HHIMcuA2ZtzVhSGvYkiOHzcwP"}]}},"user":{"like":0}}
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
         * repeat : {"id":11,"like":0,"userBook":{"user":{"id":54,"head":"Fur6UynsgLdEM5cVoBW_6fN3YvPl","nickname":"呵呵广告","signature":""},"book":{"id":140,"name":"Blueberries For Sal-part1 (619)","icon":["FmlRtOzuXl6s2xtJDd9vUqSWX-Bu"],"outline":"小Sal跟着妈妈去蓝莓山上采蓝莓，准备做成罐头，留着冬天吃。另一边的山坡上，一只小熊跟着熊妈妈来吃蓝莓，他们要在肚子里多储存些食物，好度过又冷又长的冬天。贪玩的小Sal和小熊迷了路，在蓝莓山上的蓝莓丛里，小熊和小Sal到底找到各自的妈妈没有呢？一起来绘本里寻找答案吧！"}},"segment":[{"id":2874,"audio":{"source":"Fk5DobKvRcUJxktZEmV1mXjCpCE4","time":1298}},{"id":2875,"audio":{"source":"FqJUxtTXkRq7GkE4x9m5lrXDzdHr","time":1172}},{"id":2876,"audio":{"source":"Fmb3I_DoS0nRzCGRex0QZtyx2Kbi","time":1077}},{"id":2877,"audio":{"source":"Flw2D_qNclae31Wlin2IZX3HXpYc","time":1149}},{"id":2878,"audio":{"source":"Ft2Up8tH2qFkYW8KnQlKMBhtM6l6","time":1289}},{"id":2879,"audio":{"source":"FnXkVoyeGJdNWkfpsyxu-_8xsT_X","time":1401}},{"id":2880,"audio":{"source":"FgfqBm_EbfEiN9i2_WkAvLY6xJig","time":1387}},{"id":2881,"audio":{"source":"FsWYZi9uLwqHa3VhxujtdhczUDXa","time":1541}},{"id":2882,"audio":{"source":"Fn65pDppVYz1qdoLH2A5wcpOWtMY","time":1696}},{"id":2883,"audio":{"source":"Fsg0mIfNeqVlpoPVqhZSp3p0Yp5C","time":1658}},{"id":2884,"audio":{"source":"FtC-JSQswaT9sdusroGfDFatupCh","time":1646}}],"createTime":1514515712000,"book":{"bookSegmentList":[{"id":2884,"icon":"FoAqFwhxjpx06L4iZhg0zfc9P6VD","audio":"Fpt16eb14sNJCXWsubN-GmVWr3t1"},{"id":2874,"icon":"FmlRtOzuXl6s2xtJDd9vUqSWX-Bu","audio":"Fm8EDpUoWsmBzeoZ8N93PFgxBIWr"},{"id":2875,"icon":"FnaSs_G2aSALEHVubwZEOo47OUwY","audio":"FhvbiabuUvVYHEVhUTUOtk6w0RIr"},{"id":2876,"icon":"FqPtEvODsDUlQc4qj5VFzPE930E8","audio":"FuaHgNmZ9XHluW9urwkzNCIcowXg"},{"id":2877,"icon":"FnZHwB5LYzKbHqllalSaz0KeQ2Ys","audio":"Fqx0t97V7iyOt7B7FVdf1M7u7gDH"},{"id":2878,"icon":"FvdQ7Ry8_3lpZPzP_pd3bMYpCbtW","audio":"FsDKw79Lde-mFfv9_nt5l668WXuD"},{"id":2879,"icon":"FkMt_yoL8l4S5l57vCo6MOxFiYpU","audio":"FodbbkIG6HHxofq5Addo5xTLqtTZ"},{"id":2880,"icon":"FsK8GEz9InuDf64FahDT7x6Jb9_V","audio":"Fh3kOdsDmTOgEe-3enbg36CWa1Mb"},{"id":2881,"icon":"FgbTvITL4cq-Mwsowd6djBu47Isn","audio":"Fu6PgqVAvxFc9CHnnQLXLAHZWeZ1"},{"id":2882,"icon":"Fou8aq4ry0s6bkttdmuPCblfu-9f","audio":"Fo0-Dgalm97cV7wHlyZOV3SR1o0I"},{"id":2883,"icon":"FqFroHzJuVC-Ynw--4wMxHQ-0oEb","audio":"Fk6HHIMcuA2ZtzVhSGvYkiOHzcwP"}]}}
         * user : {"like":0}
         */

        private RepeatBean repeat;
        private UserBeanX user;

        public RepeatBean getRepeat() {
            return repeat;
        }

        public void setRepeat(RepeatBean repeat) {
            this.repeat = repeat;
        }

        public UserBeanX getUser() {
            return user;
        }

        public void setUser(UserBeanX user) {
            this.user = user;
        }

        public static class RepeatBean {
            /**
             * id : 11
             * like : 0
             * userBook : {"user":{"id":54,"head":"Fur6UynsgLdEM5cVoBW_6fN3YvPl","nickname":"呵呵广告","signature":""},"book":{"id":140,"name":"Blueberries For Sal-part1 (619)","icon":["FmlRtOzuXl6s2xtJDd9vUqSWX-Bu"],"outline":"小Sal跟着妈妈去蓝莓山上采蓝莓，准备做成罐头，留着冬天吃。另一边的山坡上，一只小熊跟着熊妈妈来吃蓝莓，他们要在肚子里多储存些食物，好度过又冷又长的冬天。贪玩的小Sal和小熊迷了路，在蓝莓山上的蓝莓丛里，小熊和小Sal到底找到各自的妈妈没有呢？一起来绘本里寻找答案吧！"}}
             * segment : [{"id":2874,"audio":{"source":"Fk5DobKvRcUJxktZEmV1mXjCpCE4","time":1298}},{"id":2875,"audio":{"source":"FqJUxtTXkRq7GkE4x9m5lrXDzdHr","time":1172}},{"id":2876,"audio":{"source":"Fmb3I_DoS0nRzCGRex0QZtyx2Kbi","time":1077}},{"id":2877,"audio":{"source":"Flw2D_qNclae31Wlin2IZX3HXpYc","time":1149}},{"id":2878,"audio":{"source":"Ft2Up8tH2qFkYW8KnQlKMBhtM6l6","time":1289}},{"id":2879,"audio":{"source":"FnXkVoyeGJdNWkfpsyxu-_8xsT_X","time":1401}},{"id":2880,"audio":{"source":"FgfqBm_EbfEiN9i2_WkAvLY6xJig","time":1387}},{"id":2881,"audio":{"source":"FsWYZi9uLwqHa3VhxujtdhczUDXa","time":1541}},{"id":2882,"audio":{"source":"Fn65pDppVYz1qdoLH2A5wcpOWtMY","time":1696}},{"id":2883,"audio":{"source":"Fsg0mIfNeqVlpoPVqhZSp3p0Yp5C","time":1658}},{"id":2884,"audio":{"source":"FtC-JSQswaT9sdusroGfDFatupCh","time":1646}}]
             * createTime : 1514515712000
             * book : {"bookSegmentList":[{"id":2884,"icon":"FoAqFwhxjpx06L4iZhg0zfc9P6VD","audio":"Fpt16eb14sNJCXWsubN-GmVWr3t1"},{"id":2874,"icon":"FmlRtOzuXl6s2xtJDd9vUqSWX-Bu","audio":"Fm8EDpUoWsmBzeoZ8N93PFgxBIWr"},{"id":2875,"icon":"FnaSs_G2aSALEHVubwZEOo47OUwY","audio":"FhvbiabuUvVYHEVhUTUOtk6w0RIr"},{"id":2876,"icon":"FqPtEvODsDUlQc4qj5VFzPE930E8","audio":"FuaHgNmZ9XHluW9urwkzNCIcowXg"},{"id":2877,"icon":"FnZHwB5LYzKbHqllalSaz0KeQ2Ys","audio":"Fqx0t97V7iyOt7B7FVdf1M7u7gDH"},{"id":2878,"icon":"FvdQ7Ry8_3lpZPzP_pd3bMYpCbtW","audio":"FsDKw79Lde-mFfv9_nt5l668WXuD"},{"id":2879,"icon":"FkMt_yoL8l4S5l57vCo6MOxFiYpU","audio":"FodbbkIG6HHxofq5Addo5xTLqtTZ"},{"id":2880,"icon":"FsK8GEz9InuDf64FahDT7x6Jb9_V","audio":"Fh3kOdsDmTOgEe-3enbg36CWa1Mb"},{"id":2881,"icon":"FgbTvITL4cq-Mwsowd6djBu47Isn","audio":"Fu6PgqVAvxFc9CHnnQLXLAHZWeZ1"},{"id":2882,"icon":"Fou8aq4ry0s6bkttdmuPCblfu-9f","audio":"Fo0-Dgalm97cV7wHlyZOV3SR1o0I"},{"id":2883,"icon":"FqFroHzJuVC-Ynw--4wMxHQ-0oEb","audio":"Fk6HHIMcuA2ZtzVhSGvYkiOHzcwP"}]}
             */

            private int id;
            private int like;
            private UserBookBean userBook;
            private long createTime;
            private BookBeanX book;
            private List<SegmentBean> segment;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getLike() {
                return like;
            }

            public void setLike(int like) {
                this.like = like;
            }

            public UserBookBean getUserBook() {
                return userBook;
            }

            public void setUserBook(UserBookBean userBook) {
                this.userBook = userBook;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public BookBeanX getBook() {
                return book;
            }

            public void setBook(BookBeanX book) {
                this.book = book;
            }

            public List<SegmentBean> getSegment() {
                return segment;
            }

            public void setSegment(List<SegmentBean> segment) {
                this.segment = segment;
            }

            public static class UserBookBean {
                /**
                 * user : {"id":54,"head":"Fur6UynsgLdEM5cVoBW_6fN3YvPl","nickname":"呵呵广告","signature":""}
                 * book : {"id":140,"name":"Blueberries For Sal-part1 (619)","icon":["FmlRtOzuXl6s2xtJDd9vUqSWX-Bu"],"outline":"小Sal跟着妈妈去蓝莓山上采蓝莓，准备做成罐头，留着冬天吃。另一边的山坡上，一只小熊跟着熊妈妈来吃蓝莓，他们要在肚子里多储存些食物，好度过又冷又长的冬天。贪玩的小Sal和小熊迷了路，在蓝莓山上的蓝莓丛里，小熊和小Sal到底找到各自的妈妈没有呢？一起来绘本里寻找答案吧！"}
                 */

                private UserBean user;
                private BookBean book;

                public UserBean getUser() {
                    return user;
                }

                public void setUser(UserBean user) {
                    this.user = user;
                }

                public BookBean getBook() {
                    return book;
                }

                public void setBook(BookBean book) {
                    this.book = book;
                }

                public static class UserBean {
                    /**
                     * id : 54
                     * head : Fur6UynsgLdEM5cVoBW_6fN3YvPl
                     * nickname : 呵呵广告
                     * signature :
                     */

                    private int id;
                    private String head;
                    private String nickname;
                    private String signature;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getHead() {
                        return head;
                    }

                    public void setHead(String head) {
                        this.head = head;
                    }

                    public String getNickname() {
                        return nickname;
                    }

                    public void setNickname(String nickname) {
                        this.nickname = nickname;
                    }

                    public String getSignature() {
                        return signature;
                    }

                    public void setSignature(String signature) {
                        this.signature = signature;
                    }
                }

                public static class BookBean {
                    /**
                     * id : 140
                     * name : Blueberries For Sal-part1 (619)
                     * icon : ["FmlRtOzuXl6s2xtJDd9vUqSWX-Bu"]
                     * outline : 小Sal跟着妈妈去蓝莓山上采蓝莓，准备做成罐头，留着冬天吃。另一边的山坡上，一只小熊跟着熊妈妈来吃蓝莓，他们要在肚子里多储存些食物，好度过又冷又长的冬天。贪玩的小Sal和小熊迷了路，在蓝莓山上的蓝莓丛里，小熊和小Sal到底找到各自的妈妈没有呢？一起来绘本里寻找答案吧！
                     */

                    private int id;
                    private String name;
                    private String outline;
                    private List<String> icon;

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

                    public String getOutline() {
                        return outline;
                    }

                    public void setOutline(String outline) {
                        this.outline = outline;
                    }

                    public List<String> getIcon() {
                        return icon;
                    }

                    public void setIcon(List<String> icon) {
                        this.icon = icon;
                    }
                }
            }

            public static class BookBeanX {
                private List<BookSegmentListBean> bookSegmentList;

                public List<BookSegmentListBean> getBookSegmentList() {
                    return bookSegmentList;
                }

                public void setBookSegmentList(List<BookSegmentListBean> bookSegmentList) {
                    this.bookSegmentList = bookSegmentList;
                }

                public static class BookSegmentListBean {
                    /**
                     * id : 2884
                     * icon : FoAqFwhxjpx06L4iZhg0zfc9P6VD
                     * audio : Fpt16eb14sNJCXWsubN-GmVWr3t1
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

            public static class SegmentBean {
                /**
                 * id : 2874
                 * audio : {"source":"Fk5DobKvRcUJxktZEmV1mXjCpCE4","time":1298}
                 */

                private int id;
                private AudioBean audio;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public AudioBean getAudio() {
                    return audio;
                }

                public void setAudio(AudioBean audio) {
                    this.audio = audio;
                }

                public static class AudioBean {
                    /**
                     * source : Fk5DobKvRcUJxktZEmV1mXjCpCE4
                     * time : 1298
                     */

                    private String source;
                    private int time;

                    public String getSource() {
                        return source;
                    }

                    public void setSource(String source) {
                        this.source = source;
                    }

                    public int getTime() {
                        return time;
                    }

                    public void setTime(int time) {
                        this.time = time;
                    }
                }
            }
        }

        public static class UserBeanX {
            /**
             * like : 0
             */

            private int like;

            public int getLike() {
                return like;
            }

            public void setLike(int like) {
                this.like = like;
            }
        }
    }

//    /**
//     * event : SUCCESS
//     * data : {"repeat":{"id":62,"like":0,"userBook":{"user":{"id":13,"nickname":"We're ","signature":"haha"},"book":{"id":13,"icon":["FpYQH8c6pmbR1e1x3v3xguK9_Mx8"],"outline":"　　一个充满好奇心的少年在海边捡到了一架古老的水下照相机，他把相机里的胶卷冲印了出来，通过印好的照片他发现，这架相机已经借助大海的潮汐漂流，辗转流传于十一位少年之手\u2014\u2014每个少年都手举前一个传递者的照片拍下一张新的照片，以便将信息再传递给下 "}},"segment":[{"id":23,"audio":{"source":"Fn6XAEB-dezz5EQtsBQG6aa1iWmA","time":1396}},{"id":24,"audio":{"source":"FrnVl8hKjgjkdHfPiG1UR8AbY0c5","time":1065}},{"id":25,"audio":{"source":"FrJ1r0LXyDcpLcC_VvHQCvZgCckZ","time":2482}}],"createTime":1505892036000},"user":{"like":0}}
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
//         * repeat : {"id":62,"like":0,"userBook":{"user":{"id":13,"nickname":"We're ","signature":"haha"},"book":{"id":13,"icon":["FpYQH8c6pmbR1e1x3v3xguK9_Mx8"],"outline":"　　一个充满好奇心的少年在海边捡到了一架古老的水下照相机，他把相机里的胶卷冲印了出来，通过印好的照片他发现，这架相机已经借助大海的潮汐漂流，辗转流传于十一位少年之手\u2014\u2014每个少年都手举前一个传递者的照片拍下一张新的照片，以便将信息再传递给下 "}},"segment":[{"id":23,"audio":{"source":"Fn6XAEB-dezz5EQtsBQG6aa1iWmA","time":1396}},{"id":24,"audio":{"source":"FrnVl8hKjgjkdHfPiG1UR8AbY0c5","time":1065}},{"id":25,"audio":{"source":"FrJ1r0LXyDcpLcC_VvHQCvZgCckZ","time":2482}}],"createTime":1505892036000}
//         * user : {"like":0}
//         */
//
//        private RepeatBean repeat;
//        private UserBeanX user;
//
//        public RepeatBean getRepeat() {
//            return repeat;
//        }
//
//        public void setRepeat(RepeatBean repeat) {
//            this.repeat = repeat;
//        }
//
//        public UserBeanX getUser() {
//            return user;
//        }
//
//        public void setUser(UserBeanX user) {
//            this.user = user;
//        }
//
//        public static class RepeatBean {
//            /**
//             * id : 62
//             * like : 0
//             * userBook : {"user":{"id":13,"nickname":"We're ","signature":"haha"},"book":{"id":13,"icon":["FpYQH8c6pmbR1e1x3v3xguK9_Mx8"],"outline":"　　一个充满好奇心的少年在海边捡到了一架古老的水下照相机，他把相机里的胶卷冲印了出来，通过印好的照片他发现，这架相机已经借助大海的潮汐漂流，辗转流传于十一位少年之手\u2014\u2014每个少年都手举前一个传递者的照片拍下一张新的照片，以便将信息再传递给下 "}}
//             * segment : [{"id":23,"audio":{"source":"Fn6XAEB-dezz5EQtsBQG6aa1iWmA","time":1396}},{"id":24,"audio":{"source":"FrnVl8hKjgjkdHfPiG1UR8AbY0c5","time":1065}},{"id":25,"audio":{"source":"FrJ1r0LXyDcpLcC_VvHQCvZgCckZ","time":2482}}]
//             * createTime : 1505892036000
//             */
//
//            private int id;
//            private int like;
//            private UserBookBean userBook;
//            private long createTime;
//            private List<SegmentBean> segment;
//
//            public int getId() {
//                return id;
//            }
//
//            public void setId(int id) {
//                this.id = id;
//            }
//
//            public int getLike() {
//                return like;
//            }
//
//            public void setLike(int like) {
//                this.like = like;
//            }
//
//            public UserBookBean getUserBook() {
//                return userBook;
//            }
//
//            public void setUserBook(UserBookBean userBook) {
//                this.userBook = userBook;
//            }
//
//            public long getCreateTime() {
//                return createTime;
//            }
//
//            public void setCreateTime(long createTime) {
//                this.createTime = createTime;
//            }
//
//            public List<SegmentBean> getSegment() {
//                return segment;
//            }
//
//            public void setSegment(List<SegmentBean> segment) {
//                this.segment = segment;
//            }
//
//            public static class UserBookBean {
//                /**
//                 * user : {"id":13,"nickname":"We're ","signature":"haha"}
//                 * book : {"id":13,"icon":["FpYQH8c6pmbR1e1x3v3xguK9_Mx8"],"outline":"　　一个充满好奇心的少年在海边捡到了一架古老的水下照相机，他把相机里的胶卷冲印了出来，通过印好的照片他发现，这架相机已经借助大海的潮汐漂流，辗转流传于十一位少年之手\u2014\u2014每个少年都手举前一个传递者的照片拍下一张新的照片，以便将信息再传递给下 "}
//                 */
//
//                private UserBean user;
//                private BookBean book;
//
//                public UserBean getUser() {
//                    return user;
//                }
//
//                public void setUser(UserBean user) {
//                    this.user = user;
//                }
//
//                public BookBean getBook() {
//                    return book;
//                }
//
//                public void setBook(BookBean book) {
//                    this.book = book;
//                }
//
//                public static class UserBean {
//                    /**
//                     * id : 13
//                     * nickname : We're
//                     * signature : haha
//                     */
//
//                    private int id;
//                    private String nickname;
//                    private String signature;
//
//                    public int getId() {
//                        return id;
//                    }
//
//                    public void setId(int id) {
//                        this.id = id;
//                    }
//
//                    public String getNickname() {
//                        return nickname;
//                    }
//
//                    public void setNickname(String nickname) {
//                        this.nickname = nickname;
//                    }
//
//                    public String getSignature() {
//                        return signature;
//                    }
//
//                    public void setSignature(String signature) {
//                        this.signature = signature;
//                    }
//                }
//
//                public static class BookBean {
//                    /**
//                     * id : 13
//                     * icon : ["FpYQH8c6pmbR1e1x3v3xguK9_Mx8"]
//                     * outline : 　　一个充满好奇心的少年在海边捡到了一架古老的水下照相机，他把相机里的胶卷冲印了出来，通过印好的照片他发现，这架相机已经借助大海的潮汐漂流，辗转流传于十一位少年之手——每个少年都手举前一个传递者的照片拍下一张新的照片，以便将信息再传递给下
//                     */
//
//                    private int id;
//                    private String outline;
//                    private List<String> icon;
//
//                    public int getId() {
//                        return id;
//                    }
//
//                    public void setId(int id) {
//                        this.id = id;
//                    }
//
//                    public String getOutline() {
//                        return outline;
//                    }
//
//                    public void setOutline(String outline) {
//                        this.outline = outline;
//                    }
//
//                    public List<String> getIcon() {
//                        return icon;
//                    }
//
//                    public void setIcon(List<String> icon) {
//                        this.icon = icon;
//                    }
//                }
//            }
//
//            public static class SegmentBean {
//                /**
//                 * id : 23
//                 * audio : {"source":"Fn6XAEB-dezz5EQtsBQG6aa1iWmA","time":1396}
//                 */
//
//                private int id;
//                private AudioBean audio;
//
//                public int getId() {
//                    return id;
//                }
//
//                public void setId(int id) {
//                    this.id = id;
//                }
//
//                public AudioBean getAudio() {
//                    return audio;
//                }
//
//                public void setAudio(AudioBean audio) {
//                    this.audio = audio;
//                }
//
//                public static class AudioBean {
//                    /**
//                     * source : Fn6XAEB-dezz5EQtsBQG6aa1iWmA
//                     * time : 1396
//                     */
//
//                    private String source;
//                    private int time;
//
//                    public String getSource() {
//                        return source;
//                    }
//
//                    public void setSource(String source) {
//                        this.source = source;
//                    }
//
//                    public int getTime() {
//                        return time;
//                    }
//
//                    public void setTime(int time) {
//                        this.time = time;
//                    }
//                }
//            }
//        }
//
//        public static class UserBeanX {
//            /**
//             * like : 0
//             */
//
//            private int like;
//
//            public int getLike() {
//                return like;
//            }
//
//            public void setLike(int like) {
//                this.like = like;
//            }
//        }
//    }
//

}
