package education.zhiyuan.com.picturebooks.bean;

/**
 * Created by LH on 2017/9/29.
 * 获得的评论消息
 */

public class CommentBeanReceiver {

    /**
     * type : 0
     * body : {"comment":{"pid":89,"type":0,"sid":219},"message":{"createTime":1506674812011,"text":"vv"},"user":{"head":"Fm8Q7L3X9zaJCahNGXxioxrlk5hv","nickname":"哈哈哈哈","id":13}}
     */

    private int type;
    private BodyBean body;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public static class BodyBean {
        /**
         * comment : {"pid":89,"type":0,"sid":219}
         * message : {"createTime":1506674812011,"text":"vv"}
         * user : {"head":"Fm8Q7L3X9zaJCahNGXxioxrlk5hv","nickname":"哈哈哈哈","id":13}
         */

        private CommentBean comment;
        private MessageBean message;
        private UserBean user;

        public CommentBean getComment() {
            return comment;
        }

        public void setComment(CommentBean comment) {
            this.comment = comment;
        }

        public MessageBean getMessage() {
            return message;
        }

        public void setMessage(MessageBean message) {
            this.message = message;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class CommentBean {
            /**
             * pid : 89
             * type : 0
             * sid : 219
             */

            private int pid;
            private int type;
            private int sid;

            public int getPid() {
                return pid;
            }

            public void setPid(int pid) {
                this.pid = pid;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getSid() {
                return sid;
            }

            public void setSid(int sid) {
                this.sid = sid;
            }
        }

        public static class MessageBean {
            /**
             * createTime : 1506674812011
             * text : vv
             */

            private long createTime;
            private String text;

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }
        }

        public static class UserBean {
            /**
             * head : Fm8Q7L3X9zaJCahNGXxioxrlk5hv
             * nickname : 哈哈哈哈
             * id : 13
             */

            private String head;
            private String nickname;
            private int id;

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

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }
    }
}
