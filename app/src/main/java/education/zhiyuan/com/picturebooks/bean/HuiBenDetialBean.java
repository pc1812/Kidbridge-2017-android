package education.zhiyuan.com.picturebooks.bean;

import java.util.List;

/**
 * Created by Spring on 2017/8/11.
 * 绘本详情
 */

public class HuiBenDetialBean {

    /**
     * event : SUCCESS
     * data : {"belong":40,"book":{"id":9,"name":"暴风雨中的孩子","icon":["Fg3Bl_3WEcH3a6KB4dBddJ7HL0Cw"],"copyright":{"user":{"id":-1}},"price":0,"fit":1,"outline":"　　夏日乡间的午后一片酷热，万物懒洋洋。灰蒙蒙的天空渐渐出现变化，似乎有事情正要发生，却又无声无息，小男孩静静等待着。突然之间，一阵暴风雨席卷而来，扫过乡间、城市和海滨。人和动物都倾听着轰隆隆的巨大雷声和雨水啪啪的敲打声，等待着暴风雨过去。而当这阵狂风暴雨过后，极其美妙的事情发生了\u2026\u2026夏洛特·佐罗托用诗一般优美而细腻的语言，描述了暴风雨这一自然现象和小男孩观察世界的细微心理活动。","feeling":"","difficulty":"一般","tag":["家庭"]}}
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
         * belong : 40
         * book : {"id":9,"name":"暴风雨中的孩子","icon":["Fg3Bl_3WEcH3a6KB4dBddJ7HL0Cw"],"copyright":{"user":{"id":-1}},"price":0,"fit":1,"outline":"　　夏日乡间的午后一片酷热，万物懒洋洋。灰蒙蒙的天空渐渐出现变化，似乎有事情正要发生，却又无声无息，小男孩静静等待着。突然之间，一阵暴风雨席卷而来，扫过乡间、城市和海滨。人和动物都倾听着轰隆隆的巨大雷声和雨水啪啪的敲打声，等待着暴风雨过去。而当这阵狂风暴雨过后，极其美妙的事情发生了\u2026\u2026夏洛特·佐罗托用诗一般优美而细腻的语言，描述了暴风雨这一自然现象和小男孩观察世界的细微心理活动。","feeling":"","difficulty":"一般","tag":["家庭"]}
         */

        private int belong;
        private BookBean book;

        public int getBelong() {
            return belong;
        }

        public void setBelong(int belong) {
            this.belong = belong;
        }

        public BookBean getBook() {
            return book;
        }

        public void setBook(BookBean book) {
            this.book = book;
        }

        public static class BookBean {
            /**
             * id : 9
             * name : 暴风雨中的孩子
             * icon : ["Fg3Bl_3WEcH3a6KB4dBddJ7HL0Cw"]
             * copyright : {"user":{"id":-1}}
             * price : 0.0
             * fit : 1
             * outline : 　　夏日乡间的午后一片酷热，万物懒洋洋。灰蒙蒙的天空渐渐出现变化，似乎有事情正要发生，却又无声无息，小男孩静静等待着。突然之间，一阵暴风雨席卷而来，扫过乡间、城市和海滨。人和动物都倾听着轰隆隆的巨大雷声和雨水啪啪的敲打声，等待着暴风雨过去。而当这阵狂风暴雨过后，极其美妙的事情发生了……夏洛特·佐罗托用诗一般优美而细腻的语言，描述了暴风雨这一自然现象和小男孩观察世界的细微心理活动。
             * feeling :
             * difficulty : 一般
             * tag : ["家庭"]
             */

            private int id;
            private String name;
            private CopyrightBean copyright;
            private double price;
            private int fit;
            private String outline;
            private String feeling;
            private String difficulty;
            private List<String> icon;
            private List<String> tag;
            private String lexile;

            public String getLexile() {
                return lexile;
            }

            public void setLexile(String lexile) {
                this.lexile = lexile;
            }

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

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public int getFit() {
                return fit;
            }

            public void setFit(int fit) {
                this.fit = fit;
            }

            public String getOutline() {
                return outline;
            }

            public void setOutline(String outline) {
                this.outline = outline;
            }

            public String getFeeling() {
                return feeling;
            }

            public void setFeeling(String feeling) {
                this.feeling = feeling;
            }

            public String getDifficulty() {
                return difficulty;
            }

            public void setDifficulty(String difficulty) {
                this.difficulty = difficulty;
            }

            public List<String> getIcon() {
                return icon;
            }

            public void setIcon(List<String> icon) {
                this.icon = icon;
            }

            public List<String> getTag() {
                return tag;
            }

            public void setTag(List<String> tag) {
                this.tag = tag;
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
        }
    }
}
