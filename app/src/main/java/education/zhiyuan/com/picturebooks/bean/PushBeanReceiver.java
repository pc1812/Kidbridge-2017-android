package education.zhiyuan.com.picturebooks.bean;

/**
 * Created by LH on 2017/9/29.
 * 获得的推送消息
 */

public class PushBeanReceiver {
    /**
     * type : 1
     * body : {"message":{"createTime":1506673743714,"text":"您已解锁新绘本：《Clifford\u2019s Field Day[3-5]》，快快去跟读吧 ~"}}
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
         * message : {"createTime":1506673743714,"text":"您已解锁新绘本：《Clifford\u2019s Field Day[3-5]》，快快去跟读吧 ~"}
         */

        private MessageBean message;

        public MessageBean getMessage() {
            return message;
        }

        public void setMessage(MessageBean message) {
            this.message = message;
        }

        public static class MessageBean {
            /**
             * createTime : 1506673743714
             * text : 您已解锁新绘本：《Clifford’s Field Day[3-5]》，快快去跟读吧 ~
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
    }
//
//    /**
//     * type : 1
//     * body : {"message":{"createTime":1231123,"text":"hello world"}}
//     */
//
//    private int type;
//    private BodyBean body;
//
//    public int getType() {
//        return type;
//    }
//
//    public void setType(int type) {
//        this.type = type;
//    }
//
//    public BodyBean getBody() {
//        return body;
//    }
//
//    public void setBody(BodyBean body) {
//        this.body = body;
//    }
//
//    public static class BodyBean {
//        /**
//         * message : {"createTime":1231123,"text":"hello world"}
//         */
//
//        private MessageBean message;
//
//        public MessageBean getMessage() {
//            return message;
//        }
//
//        public void setMessage(MessageBean message) {
//            this.message = message;
//        }
//
//        public static class MessageBean {
//            /**
//             * createTime : 1231123
//             * text : hello world
//             */
//
//            private int createTime;
//            private String text;
//
//            public int getCreateTime() {
//                return createTime;
//            }
//
//            public void setCreateTime(int createTime) {
//                this.createTime = createTime;
//            }
//
//            public String getText() {
//                return text;
//            }
//
//            public void setText(String text) {
//                this.text = text;
//            }
//        }
//    }


}
