package education.zhiyuan.com.picturebooks.bean;

import java.util.List;

/**
 * Created by LH on 2017/9/20.
 * 课程跟读，评论
 */

public class CourseComment {

    /**
     * event : SUCCESS
     * data : {"important":[{"id":37,"user":{"id":14,"head":"Frmk5KhvaMnot4AuHgPyLoDHiHTd","nickname":"emmmm","teacher":{"id":5,"realname":"毛老师"}},"replyList":[{"user":{"teacher":{"id":-1,"realname":""}}}],"content":{"text":"啊  。。。","audio":{"source":"","time":-1}},"createTime":1506410648000}],"normal":[{"id":38,"user":{"id":13,"head":"P70912-214725.jpg","nickname":"哈哈哈哈","teacher":{"id":-1,"realname":""}},"replyList":[{"id":40,"user":{"id":14,"head":"Frmk5KhvaMnot4AuHgPyLoDHiHTd","nickname":"emmmm","teacher":{"id":5,"realname":"毛老师"}},"content":{"text":"老师子回复","audio":{"source":"","time":-1}},"createTime":1506412394000},{"id":39,"user":{"id":13,"head":"P70912-214725.jpg","nickname":"哈哈哈哈","teacher":{"id":-1,"realname":""}},"content":{"text":"子回复","audio":{"source":"","time":-1}},"createTime":1506411131000}],"content":{"text":"什么","audio":{"source":"","time":-1}},"createTime":1506411109000},{"id":34,"user":{"id":13,"head":"P70912-214725.jpg","nickname":"哈哈哈哈","teacher":{"id":-1,"realname":""}},"replyList":[{"id":35,"user":{"id":13,"head":"P70912-214725.jpg","nickname":"哈哈哈哈","teacher":{"id":-1,"realname":""}},"content":{"text":"123","audio":{"source":"","time":-1}},"createTime":1506394907000}],"content":{"text":"haha","audio":{"source":"","time":-1}},"createTime":1506394898000},{"id":33,"user":{"id":13,"head":"P70912-214725.jpg","nickname":"哈哈哈哈","teacher":{"id":-1,"realname":""}},"replyList":[{"id":36,"user":{"id":13,"head":"P70912-214725.jpg","nickname":"哈哈哈哈","teacher":{"id":-1,"realname":""}},"content":{"text":"hehe","audio":{"source":"","time":-1}},"createTime":1506394928000}],"content":{"text":"haha","audio":{"source":"","time":-1}},"createTime":1506394878000}]}
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
        private List<ImportantBean> important;
        private List<NormalBean> normal;

        public List<ImportantBean> getImportant() {
            return important;
        }

        public void setImportant(List<ImportantBean> important) {
            this.important = important;
        }

        public List<NormalBean> getNormal() {
            return normal;
        }

        public void setNormal(List<NormalBean> normal) {
            this.normal = normal;
        }

        public static class ImportantBean {
            /**
             * id : 37
             * user : {"id":14,"head":"Frmk5KhvaMnot4AuHgPyLoDHiHTd","nickname":"emmmm","teacher":{"id":5,"realname":"毛老师"}}
             * replyList : [{"user":{"teacher":{"id":-1,"realname":""}}}]
             * content : {"text":"啊  。。。","audio":{"source":"","time":-1}}
             * createTime : 1506410648000
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
                 * id : 14
                 * head : Frmk5KhvaMnot4AuHgPyLoDHiHTd
                 * nickname : emmmm
                 * teacher : {"id":5,"realname":"毛老师"}
                 */

                private int id;
                private String head;
                private String nickname;
                private TeacherBean teacher;

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

                public TeacherBean getTeacher() {
                    return teacher;
                }

                public void setTeacher(TeacherBean teacher) {
                    this.teacher = teacher;
                }

                public static class TeacherBean {
                    /**
                     * id : 5
                     * realname : 毛老师
                     */

                    private int id;
                    private String realname;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getRealname() {
                        return realname;
                    }

                    public void setRealname(String realname) {
                        this.realname = realname;
                    }
                }
            }

            public static class ContentBean {
                /**
                 * text : 啊  。。。
                 * audio : {"source":"","time":-1}
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
                     * source :
                     * time : -1
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
                 * user : {"teacher":{"id":-1,"realname":""}}
                 */

                private UserBeanX user;

                public UserBeanX getUser() {
                    return user;
                }

                public void setUser(UserBeanX user) {
                    this.user = user;
                }

                public static class UserBeanX {
                    /**
                     * teacher : {"id":-1,"realname":""}
                     */

                    private TeacherBeanX teacher;

                    public TeacherBeanX getTeacher() {
                        return teacher;
                    }

                    public void setTeacher(TeacherBeanX teacher) {
                        this.teacher = teacher;
                    }

                    public static class TeacherBeanX {
                        /**
                         * id : -1
                         * realname :
                         */

                        private int id;
                        private String realname;

                        public int getId() {
                            return id;
                        }

                        public void setId(int id) {
                            this.id = id;
                        }

                        public String getRealname() {
                            return realname;
                        }

                        public void setRealname(String realname) {
                            this.realname = realname;
                        }
                    }
                }
            }
        }

        public static class NormalBean {
            /**
             * id : 38
             * user : {"id":13,"head":"P70912-214725.jpg","nickname":"哈哈哈哈","teacher":{"id":-1,"realname":""}}
             * replyList : [{"id":40,"user":{"id":14,"head":"Frmk5KhvaMnot4AuHgPyLoDHiHTd","nickname":"emmmm","teacher":{"id":5,"realname":"毛老师"}},"content":{"text":"老师子回复","audio":{"source":"","time":-1}},"createTime":1506412394000},{"id":39,"user":{"id":13,"head":"P70912-214725.jpg","nickname":"哈哈哈哈","teacher":{"id":-1,"realname":""}},"content":{"text":"子回复","audio":{"source":"","time":-1}},"createTime":1506411131000}]
             * content : {"text":"什么","audio":{"source":"","time":-1}}
             * createTime : 1506411109000
             */

            private int id;
            private UserBeanXX user;
            private ContentBeanX content;
            private long createTime;
            private List<ReplyListBeanX> replyList;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public UserBeanXX getUser() {
                return user;
            }

            public void setUser(UserBeanXX user) {
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

            public List<ReplyListBeanX> getReplyList() {
                return replyList;
            }

            public void setReplyList(List<ReplyListBeanX> replyList) {
                this.replyList = replyList;
            }

            public static class UserBeanXX {
                /**
                 * id : 13
                 * head : P70912-214725.jpg
                 * nickname : 哈哈哈哈
                 * teacher : {"id":-1,"realname":""}
                 */

                private int id;
                private String head;
                private String nickname;
                private TeacherBeanXX teacher;

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

                public TeacherBeanXX getTeacher() {
                    return teacher;
                }

                public void setTeacher(TeacherBeanXX teacher) {
                    this.teacher = teacher;
                }

                public static class TeacherBeanXX {
                    /**
                     * id : -1
                     * realname :
                     */

                    private int id;
                    private String realname;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getRealname() {
                        return realname;
                    }

                    public void setRealname(String realname) {
                        this.realname = realname;
                    }
                }
            }

            public static class ContentBeanX {
                /**
                 * text : 什么
                 * audio : {"source":"","time":-1}
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
                     * source :
                     * time : -1
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

            public static class ReplyListBeanX {
                /**
                 * id : 40
                 * user : {"id":14,"head":"Frmk5KhvaMnot4AuHgPyLoDHiHTd","nickname":"emmmm","teacher":{"id":5,"realname":"毛老师"}}
                 * content : {"text":"老师子回复","audio":{"source":"","time":-1}}
                 * createTime : 1506412394000
                 */

                private int id;
                private UserBeanXXX user;
                private ContentBeanXX content;
                private long createTime;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public UserBeanXXX getUser() {
                    return user;
                }

                public void setUser(UserBeanXXX user) {
                    this.user = user;
                }

                public ContentBeanXX getContent() {
                    return content;
                }

                public void setContent(ContentBeanXX content) {
                    this.content = content;
                }

                public long getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(long createTime) {
                    this.createTime = createTime;
                }

                public static class UserBeanXXX {
                    /**
                     * id : 14
                     * head : Frmk5KhvaMnot4AuHgPyLoDHiHTd
                     * nickname : emmmm
                     * teacher : {"id":5,"realname":"毛老师"}
                     */

                    private int id;
                    private String head;
                    private String nickname;
                    private TeacherBeanXXX teacher;

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

                    public TeacherBeanXXX getTeacher() {
                        return teacher;
                    }

                    public void setTeacher(TeacherBeanXXX teacher) {
                        this.teacher = teacher;
                    }

                    public static class TeacherBeanXXX {
                        /**
                         * id : 5
                         * realname : 毛老师
                         */

                        private int id;
                        private String realname;

                        public int getId() {
                            return id;
                        }

                        public void setId(int id) {
                            this.id = id;
                        }

                        public String getRealname() {
                            return realname;
                        }

                        public void setRealname(String realname) {
                            this.realname = realname;
                        }
                    }
                }

                public static class ContentBeanXX {
                    /**
                     * text : 老师子回复
                     * audio : {"source":"","time":-1}
                     */

                    private String text;
                    private AudioBeanXX audio;

                    public String getText() {
                        return text;
                    }

                    public void setText(String text) {
                        this.text = text;
                    }

                    public AudioBeanXX getAudio() {
                        return audio;
                    }

                    public void setAudio(AudioBeanXX audio) {
                        this.audio = audio;
                    }

                    public static class AudioBeanXX {
                        /**
                         * source :
                         * time : -1
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
