package education.zhiyuan.com.picturebooks.bean;

import java.util.List;

/**
 * Created by Spring on 2017/8/15.
 * 我的成就
 */

public class MyAchievement {

    /**
     * event : SUCCESS
     * data : {"medalList":[{"id":4,"name":"嫩芽勋章","icon":{"active":"FnCj0Tqwb1GsU4dn_K41veE1aMHW","quiet":"FuYiE9iAWcJvKEYNNqp2oNMZftBB"},"bonus":30},{"id":5,"name":"竹子勋章","icon":{"active":"Fg2eutRAhd4DAN_JFhnhJWfxTNBC","quiet":"FrYCvxU2yW4dpm0Vbbz-nvLhclZU"},"bonus":60},{"id":6,"name":"垂柳勋章","icon":{"active":"FlxMuF-M2UgMoA5b8Z_AQu7o0-12","quiet":"FjGKV9JF50gGHwSsxeSy0WPNqjE1"},"bonus":90},{"id":7,"name":"松柏勋章","icon":{"active":"Fn_HhO8qSTZSZo6IqV5Rysk5cWRI","quiet":"FgBEmut2IJiAMYwmcz1qYhoyj-Ob"},"bonus":120},{"id":8,"name":"香樟勋章","icon":{"active":"Fo6tytpvW5qsczlZ8r6Jsqw5wqpp","quiet":"FvKW-qJySFEaScwLmYrY72FzQmgR"},"bonus":150}],"bonus":70}
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
         * medalList : [{"id":4,"name":"嫩芽勋章","icon":{"active":"FnCj0Tqwb1GsU4dn_K41veE1aMHW","quiet":"FuYiE9iAWcJvKEYNNqp2oNMZftBB"},"bonus":30},{"id":5,"name":"竹子勋章","icon":{"active":"Fg2eutRAhd4DAN_JFhnhJWfxTNBC","quiet":"FrYCvxU2yW4dpm0Vbbz-nvLhclZU"},"bonus":60},{"id":6,"name":"垂柳勋章","icon":{"active":"FlxMuF-M2UgMoA5b8Z_AQu7o0-12","quiet":"FjGKV9JF50gGHwSsxeSy0WPNqjE1"},"bonus":90},{"id":7,"name":"松柏勋章","icon":{"active":"Fn_HhO8qSTZSZo6IqV5Rysk5cWRI","quiet":"FgBEmut2IJiAMYwmcz1qYhoyj-Ob"},"bonus":120},{"id":8,"name":"香樟勋章","icon":{"active":"Fo6tytpvW5qsczlZ8r6Jsqw5wqpp","quiet":"FvKW-qJySFEaScwLmYrY72FzQmgR"},"bonus":150}]
         * bonus : 70
         */

        private int bonus;
        private List<MedalListBean> medalList;

        public int getBonus() {
            return bonus;
        }

        public void setBonus(int bonus) {
            this.bonus = bonus;
        }

        public List<MedalListBean> getMedalList() {
            return medalList;
        }

        public void setMedalList(List<MedalListBean> medalList) {
            this.medalList = medalList;
        }

        public static class MedalListBean {
            /**
             * id : 4
             * name : 嫩芽勋章
             * icon : {"active":"FnCj0Tqwb1GsU4dn_K41veE1aMHW","quiet":"FuYiE9iAWcJvKEYNNqp2oNMZftBB"}
             * bonus : 30
             */

            private int id;
            private String name;
            private IconBean icon;
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

            public IconBean getIcon() {
                return icon;
            }

            public void setIcon(IconBean icon) {
                this.icon = icon;
            }

            public int getBonus() {
                return bonus;
            }

            public void setBonus(int bonus) {
                this.bonus = bonus;
            }

            public static class IconBean {
                /**
                 * active : FnCj0Tqwb1GsU4dn_K41veE1aMHW
                 * quiet : FuYiE9iAWcJvKEYNNqp2oNMZftBB
                 */

                private String active;
                private String quiet;

                public String getActive() {
                    return active;
                }

                public void setActive(String active) {
                    this.active = active;
                }

                public String getQuiet() {
                    return quiet;
                }

                public void setQuiet(String quiet) {
                    this.quiet = quiet;
                }
            }
        }
    }
}
