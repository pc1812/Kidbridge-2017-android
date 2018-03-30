package education.zhiyuan.com.picturebooks.bean;

import java.util.List;

/**
 * Created by LH on 2017/9/26.
 */

public class CourseTeacher {

    /**
     * event : SUCCESS
     * data : [{"id":37,"user":{"id":14,"head":"Frmk5KhvaMnot4AuHgPyLoDHiHTd","nickname":"emmmm","teacher":{"id":5,"realname":"毛老师"}},"userCourseRepeat":{"id":36},"content":{"text":"啊  。。。","audio":{"source":"","time":-1}},"createTime":1506410648000}]
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
         * id : 37
         * user : {"id":14,"head":"Frmk5KhvaMnot4AuHgPyLoDHiHTd","nickname":"emmmm","teacher":{"id":5,"realname":"毛老师"}}
         * userCourseRepeat : {"id":36}
         * content : {"text":"啊  。。。","audio":{"source":"","time":-1}}
         * createTime : 1506410648000
         */

        private int id;
        private UserBean user;
        private UserCourseRepeatBean userCourseRepeat;
        private ContentBean content;
        private long createTime;

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

        public UserCourseRepeatBean getUserCourseRepeat() {
            return userCourseRepeat;
        }

        public void setUserCourseRepeat(UserCourseRepeatBean userCourseRepeat) {
            this.userCourseRepeat = userCourseRepeat;
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

        public static class UserCourseRepeatBean {
            /**
             * id : 36
             */

            private int id;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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
    }
}
