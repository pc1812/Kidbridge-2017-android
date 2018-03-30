package education.zhiyuan.com.picturebooks.bean;

/**
 * Created by Spring on 2017/8/15.
 */

public class MyBean {

    /**
     * event : SUCCESS
     * data : {"phase":{"over":174,"future":{"id":4,"name":"嫩芽勋章","bonus":180},"now":{"name":"","id":-1}},"role":1,"user":{"id":57,"head":"FrNWT_G5TMluAoy32VbtQfVi38bw","nickname":"哎呦喂哈哈哈哈哈哈","age":46,"bonus":6,"balance":123344.01,"signature":"红红火火恍恍惚惚"},"customerservice":"13656690321"}
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
         * phase : {"over":174,"future":{"id":4,"name":"嫩芽勋章","bonus":180},"now":{"name":"","id":-1}}
         * role : 1
         * user : {"id":57,"head":"FrNWT_G5TMluAoy32VbtQfVi38bw","nickname":"哎呦喂哈哈哈哈哈哈","age":46,"bonus":6,"balance":123344.01,"signature":"红红火火恍恍惚惚"}
         * customerservice : 13656690321
         */

        private PhaseBean phase;
        private int role;
        private UserBean user;
        private String customerservice;

        public PhaseBean getPhase() {
            return phase;
        }

        public void setPhase(PhaseBean phase) {
            this.phase = phase;
        }

        public int getRole() {
            return role;
        }

        public void setRole(int role) {
            this.role = role;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public String getCustomerservice() {
            return customerservice;
        }

        public void setCustomerservice(String customerservice) {
            this.customerservice = customerservice;
        }

        public static class PhaseBean {
            /**
             * over : 174
             * future : {"id":4,"name":"嫩芽勋章","bonus":180}
             * now : {"name":"","id":-1}
             */

            private int over;
            private FutureBean future;
            private NowBean now;

            public int getOver() {
                return over;
            }

            public void setOver(int over) {
                this.over = over;
            }

            public FutureBean getFuture() {
                return future;
            }

            public void setFuture(FutureBean future) {
                this.future = future;
            }

            public NowBean getNow() {
                return now;
            }

            public void setNow(NowBean now) {
                this.now = now;
            }

            public static class FutureBean {
                /**
                 * id : 4
                 * name : 嫩芽勋章
                 * bonus : 180
                 */

                private int id;
                private String name;
                private int bonus;

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

                public int getBonus() {
                    return bonus;
                }

                public void setBonus(int bonus) {
                    this.bonus = bonus;
                }
            }

            public static class NowBean {
                /**
                 * name :
                 * id : -1
                 */

                private String name;
                private int id;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }
            }
        }

        public static class UserBean {
            /**
             * id : 57
             * head : FrNWT_G5TMluAoy32VbtQfVi38bw
             * nickname : 哎呦喂哈哈哈哈哈哈
             * age : 46
             * bonus : 6
             * balance : 123344.01
             * signature : 红红火火恍恍惚惚
             */

            private int id;
            private String head;
            private String nickname;
            private String age;
            private int bonus;
            private double balance;
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

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public int getBonus() {
                return bonus;
            }

            public void setBonus(int bonus) {
                this.bonus = bonus;
            }

            public double getBalance() {
                return balance;
            }

            public void setBalance(double balance) {
                this.balance = balance;
            }

            public String getSignature() {
                return signature;
            }

            public void setSignature(String signature) {
                this.signature = signature;
            }
        }
    }
}
