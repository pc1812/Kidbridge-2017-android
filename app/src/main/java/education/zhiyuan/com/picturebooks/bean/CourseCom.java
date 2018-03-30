package education.zhiyuan.com.picturebooks.bean;

import java.util.List;

/**
 * Created by LH on 2017/9/26.
 */

public class CourseCom {

    private List<CoursecomBean> coursecom;

    public List<CoursecomBean> getCoursecom() {
        return coursecom;
    }

    public void setCoursecom(List<CoursecomBean> coursecom) {
        this.coursecom = coursecom;
    }

    public static class CoursecomBean {
        /**
         * id : 38
         * user : {"id":13,"head":"P70912-214725.jpg","nickname":"哈哈哈哈","teacher":{"id":-1,"realname":""}}
         * replyList : [{"id":40,"user":{"id":14,"head":"Frmk5KhvaMnot4AuHgPyLoDHiHTd","nickname":"emmmm","teacher":{"id":5,"realname":"毛老师"}},"content":{"text":"老师子回复","audio":{"source":"","time":-1}},"createTime":1506412394000},{"id":39,"user":{"id":13,"head":"P70912-214725.jpg","nickname":"哈哈哈哈","teacher":{"id":-1,"realname":""}},"content":{"text":"子回复","audio":{"source":"","time":-1}},"createTime":1506411131000}]
         * content : {"text":"什么","audio":{"source":"","time":-1}}
         * createTime : 1506411109000
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
             * nickname : 哈哈哈哈
             * teacher : {"id":-1,"realname":""}
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

        public static class ContentBean {
            /**
             * text : 什么
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
             * id : 40
             * user : {"id":14,"head":"Frmk5KhvaMnot4AuHgPyLoDHiHTd","nickname":"emmmm","teacher":{"id":5,"realname":"毛老师"}}
             * content : {"text":"老师子回复","audio":{"source":"","time":-1}}
             * createTime : 1506412394000
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
                 * id : 14
                 * head : Frmk5KhvaMnot4AuHgPyLoDHiHTd
                 * nickname : emmmm
                 * teacher : {"id":5,"realname":"毛老师"}
                 */

                private int id;
                private String head;
                private String nickname;
                private TeacherBeanX teacher;

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

                public TeacherBeanX getTeacher() {
                    return teacher;
                }

                public void setTeacher(TeacherBeanX teacher) {
                    this.teacher = teacher;
                }

                public static class TeacherBeanX {
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

            public static class ContentBeanX {
                /**
                 * text : 老师子回复
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
        }
    }
}
