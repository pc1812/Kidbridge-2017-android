package education.zhiyuan.com.picturebooks.bean;

import java.util.List;

/**
 * Created by Spring on 2017/8/19.
 * 绘本跟读评论
 */

public class RepeatComment {

    /**
     * event : SUCCESS
     * data : {"comment":[{"id":174,"replyList":[{"id":175,"user":{"id":13,"head":"P70912-214725.jpg","nickname":"We're "},"content":{"text":"","audio":{"source":"FpVgzIWV7uGkIBmd7ZZplBoP_SZa","time":3908}},"createTime":1505734317000},{"id":179,"user":{"id":34,"head":"activity-spring-share.ac17dc70.jpg","nickname":"噼里啪啦"},"content":{"text":"123654","audio":{"source":"","time":""}},"createTime":1505786364000}],"user":{"id":13,"head":"P70912-214725.jpg","nickname":"We're "},"content":{"text":"123456","audio":{"source":"Fj8g4mdPpyV3SPUQL2Ir8tNHh6A4","time":3000}},"createTime":1505731782000},{"id":173,"replyList":[],"user":{"id":13,"head":"P70912-214725.jpg","nickname":"We're "},"content":{"text":"哈哈","audio":{"source":"FrlWZdzklUB0Mi0iK1CWc8nHLnrt","time":2000}},"createTime":1505731612000},{"id":172,"replyList":[],"user":{"id":13,"head":"P70912-214725.jpg","nickname":"We're "},"content":{"text":"","audio":{"source":"FoQOtZU9I618DGKz8s1xMp6iETu9","time":4402}},"createTime":1505728821000}]}
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
        private List<CommentBean> comment;

        public List<CommentBean> getComment() {
            return comment;
        }

        public void setComment(List<CommentBean> comment) {
            this.comment = comment;
        }

        public static class CommentBean {
            /**
             * id : 174
             * replyList : [{"id":175,"user":{"id":13,"head":"P70912-214725.jpg","nickname":"We're "},"content":{"text":"","audio":{"source":"FpVgzIWV7uGkIBmd7ZZplBoP_SZa","time":3908}},"createTime":1505734317000},{"id":179,"user":{"id":34,"head":"activity-spring-share.ac17dc70.jpg","nickname":"噼里啪啦"},"content":{"text":"123654","audio":{"source":"","time":""}},"createTime":1505786364000}]
             * user : {"id":13,"head":"P70912-214725.jpg","nickname":"We're "}
             * content : {"text":"123456","audio":{"source":"Fj8g4mdPpyV3SPUQL2Ir8tNHh6A4","time":3000}}
             * createTime : 1505731782000
             */

            private int id;
            private UserBean user;
            private ContentBean content;
            private long createTime;
            private List<ReplyListBean> replyList;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }

            public ContentBean getContent() {
                return content;
            }

            public void setContent(ContentBean content) {
                this.content = content;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public List<ReplyListBean> getReplyList() {
                return replyList;
            }

            public void setReplyList(List<ReplyListBean> replyList) {
                this.replyList = replyList;
            }

            public static class UserBean {
                /**
                 * id : 13
                 * head : P70912-214725.jpg
                 * nickname : We're
                 */

                private int id;
                private String head;
                private String nickname;

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
            }

            public static class ContentBean {
                /**
                 * text : 123456
                 * audio : {"source":"Fj8g4mdPpyV3SPUQL2Ir8tNHh6A4","time":3000}
                 */

                private String text;
                private AudioBean audio;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public AudioBean getAudio() {
                    return audio;
                }

                public void setAudio(AudioBean audio) {
                    this.audio = audio;
                }

                public static class AudioBean {
                    /**
                     * source : Fj8g4mdPpyV3SPUQL2Ir8tNHh6A4
                     * time : 3000
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

            public static class ReplyListBean {
                /**
                 * id : 175
                 * user : {"id":13,"head":"P70912-214725.jpg","nickname":"We're "}
                 * content : {"text":"","audio":{"source":"FpVgzIWV7uGkIBmd7ZZplBoP_SZa","time":3908}}
                 * createTime : 1505734317000
                 */

                private int id;
                private UserBeanX user;
                private ContentBeanX content;
                private long createTime;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public UserBeanX getUser() {
                    return user;
                }

                public void setUser(UserBeanX user) {
                    this.user = user;
                }

                public ContentBeanX getContent() {
                    return content;
                }

                public void setContent(ContentBeanX content) {
                    this.content = content;
                }

                public long getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(long createTime) {
                    this.createTime = createTime;
                }

                public static class UserBeanX {
                    /**
                     * id : 13
                     * head : P70912-214725.jpg
                     * nickname : We're
                     */

                    private int id;
                    private String head;
                    private String nickname;

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
                }

                public static class ContentBeanX {
                    /**
                     * text :
                     * audio : {"source":"FpVgzIWV7uGkIBmd7ZZplBoP_SZa","time":3908}
                     */

                    private String text;
                    private AudioBeanX audio;

                    public String getText() {
                        return text;
                    }

                    public void setText(String text) {
                        this.text = text;
                    }

                    public AudioBeanX getAudio() {
                        return audio;
                    }

                    public void setAudio(AudioBeanX audio) {
                        this.audio = audio;
                    }

                    public static class AudioBeanX {
                        /**
                         * source : FpVgzIWV7uGkIBmd7ZZplBoP_SZa
                         * time : 3908
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
        }
    }
}
