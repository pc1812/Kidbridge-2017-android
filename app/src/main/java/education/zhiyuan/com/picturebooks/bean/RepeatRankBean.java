package education.zhiyuan.com.picturebooks.bean;

import java.util.List;

/**
 * Created by Spring on 2017/8/19.
 * 绘本详情，跟读榜
 */

public class RepeatRankBean {

    /**
     * event : SUCCESS
     * data : {"rank":[{"id":25,"like":1,"userBook":{"user":{"id":13,"head":"FhRPAf4xGMMKgtscsHe8ASsyRwNB","nickname":"Spring ."}},"createTime":1502962986000},{"id":26,"like":0,"userBook":{"user":{"id":14,"head":"FhRPAf4xGMMKgtscsHe8ASsyRwNB","nickname":"helloworld"}},"createTime":1502963166000}]}
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
        private List<RankBean> rank;

        public List<RankBean> getRank() {
            return rank;
        }

        public void setRank(List<RankBean> rank) {
            this.rank = rank;
        }

        public static class RankBean {
            /**
             * id : 25
             * like : 1
             * userBook : {"user":{"id":13,"head":"FhRPAf4xGMMKgtscsHe8ASsyRwNB","nickname":"Spring ."}}
             * createTime : 1502962986000
             */

            private int id;
            private int like;
            private UserBookBean userBook;
            private long createTime;

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

            public static class UserBookBean {
                /**
                 * user : {"id":13,"head":"FhRPAf4xGMMKgtscsHe8ASsyRwNB","nickname":"Spring ."}
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
                     * id : 13
                     * head : FhRPAf4xGMMKgtscsHe8ASsyRwNB
                     * nickname : Spring .
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
            }
        }
    }
}
