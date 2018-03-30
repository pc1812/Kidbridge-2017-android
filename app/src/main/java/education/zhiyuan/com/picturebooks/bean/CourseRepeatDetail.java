package education.zhiyuan.com.picturebooks.bean;

import java.util.List;

/**
 * Created by LH on 2017/9/18.
 */

public class CourseRepeatDetail {

    /**
     * event : SUCCESS
     * data : {"repeat":{"id":11,"userCourse":{"user":{"id":57,"head":"FrNWT_G5TMluAoy32VbtQfVi38bw","nickname":"哎呦喂哈哈哈哈哈哈","signature":"红红火火恍恍惚惚"},"course":{"id":33,"name":"1015 L2","icon":["Fr-tXj6okUGtwRZaEftFer8H0xL5"]}},"book":{"id":86,"name":"This is My Town","bookSegmentList":[{"id":3449,"icon":"FmihG01u2BML5QUy70ErS-Y4Zuqb","audio":"Fu3GtN5s7jRoSbgByCgut7Ui-OvO"},{"id":3450,"icon":"FrME0WRyM0ytQX3Lp6Z4fztU14sH","audio":"FtP-snuN6pmQ2QGBNbb7DhpxmnqT"},{"id":3451,"icon":"Fi9OgVA9n0amUyx_ORgeymScdVKP","audio":"FoYZmOUEKys8QN6ps1oO8cmUp8Am"},{"id":3452,"icon":"Fi8nMCrsh59h1U8qKxKJaZVq7gX0","audio":"FmOBl7zPdKvtt70DzNCKL_uBm1hf"},{"id":3453,"icon":"Fh4jGQkyGrxG4K9ziQXBzQ14Hpvk","audio":"FmAhKaEqLnDQ9EpdfdR2l1dEjw6P"},{"id":3454,"icon":"FlJiA5ZNynovovapi-6TQPjkoGGE","audio":"Fpnla0kNwwVSHaR4voBXN67mvw69"},{"id":3455,"icon":"FrpzgszRMSFvubLc4EsHFQ8UQ7jb","audio":"FkT-zp5XApkCdjCI1_gJZ1sutWMB"},{"id":3445,"icon":"Fr4vLgOMMmpN87LH0am_CpX8-Khp","audio":"Fq6VDD4pNAtwLlkgKnbJV_rfGE9c"},{"id":3456,"icon":"Fjs0lzbVTskszQYyldOAW9rMTFBZ","audio":"FvsQjF91JxQr8kryLVus_CwqQlq8"},{"id":3446,"icon":"FiyAYL0E7kWSUJB37tob3W8z41G-","audio":"FvD1B1XZM1IG7FQEOcALmCd-uDzz"},{"id":3457,"icon":"Fs-y-4lbAjEjj1fJUYBCvwUDYyJv","audio":"FksgdhaKkQhfqB75_lDj_UkKqsOM"},{"id":3447,"icon":"Fp94OxaWJWshbpjN3CP_ZRgGhu3b","audio":"FlEl1s9mtqRAbRxBV4usSRQT-kuU"},{"id":3458,"icon":"FlWnsEenf7rxAPhUKlbK1Z9y63ya","audio":"Fob7gGJuOKqVIwGOeOeNirOBV3ea"},{"id":3448,"icon":"FgHIiVAccaJgCwQGF6tokX0NXreD","audio":"FnAbJBqg_Zp31mcZo0d6vzgbDYWP"},{"id":3459,"icon":"FgDq9LVoC3jmD1mxrnXfdaNOSPgt","audio":"FjJ4XwbbzVcCFRhA_UKnBvsW0gAN"}]},"like":0,"segment":[{"id":3445,"audio":{"source":"Fqy7lmPhFN6YhBDDzJaa0s-GGSOb","time":2520}},{"id":3446,"audio":{"source":"FrBX_j6q3gGr45J9N1voTwUL7jGr","time":2232}},{"id":3447,"audio":{"source":"FgG4aM4z2jyoXcoRVzrHDx6hMoFF","time":3024}},{"id":3448,"audio":{"source":"Fs9WfpI87GhSTuTR4g9gScVLMt0M","time":3528}},{"id":3449,"audio":{"source":"FpBX54ZYsRw68hRfDNPtdyOrRBZC","time":2664}},{"id":3450,"audio":{"source":"FsbKLwll97cDEC-9rJvdLKhUMpEx","time":3744}},{"id":3451,"audio":{"source":"FihWv5GhRnv0cahm7Pg3R-KR_8le","time":2304}},{"id":3452,"audio":{"source":"FpJG1q_dA6MF6YG7YQGMgoCVRD8W","time":2664}},{"id":3453,"audio":{"source":"FqpvwKz4v-xvr8DFhYiWnZDbRMzd","time":1944}},{"id":3454,"audio":{"source":"FnsmYdTVWNtE4Jg8yQJtJyQ2HRFw","time":2304}},{"id":3455,"audio":{"source":"Fk5lOJW7yThoRACHSUU_Qt0sAGj_","time":2808}},{"id":3456,"audio":{"source":"Fiz2OHo4FOHACKsAziB-vjeA-5Hs","time":2952}},{"id":3457,"audio":{"source":"FpZgxtlYyrVaFyIqRyDo6z3UgT6N","time":2304}},{"id":3458,"audio":{"source":"FiK4Un2PY2xpk1_I6vfs5v--sZ-g","time":2880}},{"id":3459,"audio":{"source":"Fry0DxWuh5hcv3aTp4ENfG-8ILXa","time":2376}}],"createTime":1515340800000},"user":{"like":0}}
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
         * repeat : {"id":11,"userCourse":{"user":{"id":57,"head":"FrNWT_G5TMluAoy32VbtQfVi38bw","nickname":"哎呦喂哈哈哈哈哈哈","signature":"红红火火恍恍惚惚"},"course":{"id":33,"name":"1015 L2","icon":["Fr-tXj6okUGtwRZaEftFer8H0xL5"]}},"book":{"id":86,"name":"This is My Town","bookSegmentList":[{"id":3449,"icon":"FmihG01u2BML5QUy70ErS-Y4Zuqb","audio":"Fu3GtN5s7jRoSbgByCgut7Ui-OvO"},{"id":3450,"icon":"FrME0WRyM0ytQX3Lp6Z4fztU14sH","audio":"FtP-snuN6pmQ2QGBNbb7DhpxmnqT"},{"id":3451,"icon":"Fi9OgVA9n0amUyx_ORgeymScdVKP","audio":"FoYZmOUEKys8QN6ps1oO8cmUp8Am"},{"id":3452,"icon":"Fi8nMCrsh59h1U8qKxKJaZVq7gX0","audio":"FmOBl7zPdKvtt70DzNCKL_uBm1hf"},{"id":3453,"icon":"Fh4jGQkyGrxG4K9ziQXBzQ14Hpvk","audio":"FmAhKaEqLnDQ9EpdfdR2l1dEjw6P"},{"id":3454,"icon":"FlJiA5ZNynovovapi-6TQPjkoGGE","audio":"Fpnla0kNwwVSHaR4voBXN67mvw69"},{"id":3455,"icon":"FrpzgszRMSFvubLc4EsHFQ8UQ7jb","audio":"FkT-zp5XApkCdjCI1_gJZ1sutWMB"},{"id":3445,"icon":"Fr4vLgOMMmpN87LH0am_CpX8-Khp","audio":"Fq6VDD4pNAtwLlkgKnbJV_rfGE9c"},{"id":3456,"icon":"Fjs0lzbVTskszQYyldOAW9rMTFBZ","audio":"FvsQjF91JxQr8kryLVus_CwqQlq8"},{"id":3446,"icon":"FiyAYL0E7kWSUJB37tob3W8z41G-","audio":"FvD1B1XZM1IG7FQEOcALmCd-uDzz"},{"id":3457,"icon":"Fs-y-4lbAjEjj1fJUYBCvwUDYyJv","audio":"FksgdhaKkQhfqB75_lDj_UkKqsOM"},{"id":3447,"icon":"Fp94OxaWJWshbpjN3CP_ZRgGhu3b","audio":"FlEl1s9mtqRAbRxBV4usSRQT-kuU"},{"id":3458,"icon":"FlWnsEenf7rxAPhUKlbK1Z9y63ya","audio":"Fob7gGJuOKqVIwGOeOeNirOBV3ea"},{"id":3448,"icon":"FgHIiVAccaJgCwQGF6tokX0NXreD","audio":"FnAbJBqg_Zp31mcZo0d6vzgbDYWP"},{"id":3459,"icon":"FgDq9LVoC3jmD1mxrnXfdaNOSPgt","audio":"FjJ4XwbbzVcCFRhA_UKnBvsW0gAN"}]},"like":0,"segment":[{"id":3445,"audio":{"source":"Fqy7lmPhFN6YhBDDzJaa0s-GGSOb","time":2520}},{"id":3446,"audio":{"source":"FrBX_j6q3gGr45J9N1voTwUL7jGr","time":2232}},{"id":3447,"audio":{"source":"FgG4aM4z2jyoXcoRVzrHDx6hMoFF","time":3024}},{"id":3448,"audio":{"source":"Fs9WfpI87GhSTuTR4g9gScVLMt0M","time":3528}},{"id":3449,"audio":{"source":"FpBX54ZYsRw68hRfDNPtdyOrRBZC","time":2664}},{"id":3450,"audio":{"source":"FsbKLwll97cDEC-9rJvdLKhUMpEx","time":3744}},{"id":3451,"audio":{"source":"FihWv5GhRnv0cahm7Pg3R-KR_8le","time":2304}},{"id":3452,"audio":{"source":"FpJG1q_dA6MF6YG7YQGMgoCVRD8W","time":2664}},{"id":3453,"audio":{"source":"FqpvwKz4v-xvr8DFhYiWnZDbRMzd","time":1944}},{"id":3454,"audio":{"source":"FnsmYdTVWNtE4Jg8yQJtJyQ2HRFw","time":2304}},{"id":3455,"audio":{"source":"Fk5lOJW7yThoRACHSUU_Qt0sAGj_","time":2808}},{"id":3456,"audio":{"source":"Fiz2OHo4FOHACKsAziB-vjeA-5Hs","time":2952}},{"id":3457,"audio":{"source":"FpZgxtlYyrVaFyIqRyDo6z3UgT6N","time":2304}},{"id":3458,"audio":{"source":"FiK4Un2PY2xpk1_I6vfs5v--sZ-g","time":2880}},{"id":3459,"audio":{"source":"Fry0DxWuh5hcv3aTp4ENfG-8ILXa","time":2376}}],"createTime":1515340800000}
         * user : {"like":0}
         */

        private RepeatBean repeat;
        private UserBeanX user;

        public RepeatBean getRepeat() {
            return repeat;
        }

        public void setRepeat(RepeatBean repeat) {
            this.repeat = repeat;
        }

        public UserBeanX getUser() {
            return user;
        }

        public void setUser(UserBeanX user) {
            this.user = user;
        }

        public static class RepeatBean {
            /**
             * id : 11
             * userCourse : {"user":{"id":57,"head":"FrNWT_G5TMluAoy32VbtQfVi38bw","nickname":"哎呦喂哈哈哈哈哈哈","signature":"红红火火恍恍惚惚"},"course":{"id":33,"name":"1015 L2","icon":["Fr-tXj6okUGtwRZaEftFer8H0xL5"]}}
             * book : {"id":86,"name":"This is My Town","bookSegmentList":[{"id":3449,"icon":"FmihG01u2BML5QUy70ErS-Y4Zuqb","audio":"Fu3GtN5s7jRoSbgByCgut7Ui-OvO"},{"id":3450,"icon":"FrME0WRyM0ytQX3Lp6Z4fztU14sH","audio":"FtP-snuN6pmQ2QGBNbb7DhpxmnqT"},{"id":3451,"icon":"Fi9OgVA9n0amUyx_ORgeymScdVKP","audio":"FoYZmOUEKys8QN6ps1oO8cmUp8Am"},{"id":3452,"icon":"Fi8nMCrsh59h1U8qKxKJaZVq7gX0","audio":"FmOBl7zPdKvtt70DzNCKL_uBm1hf"},{"id":3453,"icon":"Fh4jGQkyGrxG4K9ziQXBzQ14Hpvk","audio":"FmAhKaEqLnDQ9EpdfdR2l1dEjw6P"},{"id":3454,"icon":"FlJiA5ZNynovovapi-6TQPjkoGGE","audio":"Fpnla0kNwwVSHaR4voBXN67mvw69"},{"id":3455,"icon":"FrpzgszRMSFvubLc4EsHFQ8UQ7jb","audio":"FkT-zp5XApkCdjCI1_gJZ1sutWMB"},{"id":3445,"icon":"Fr4vLgOMMmpN87LH0am_CpX8-Khp","audio":"Fq6VDD4pNAtwLlkgKnbJV_rfGE9c"},{"id":3456,"icon":"Fjs0lzbVTskszQYyldOAW9rMTFBZ","audio":"FvsQjF91JxQr8kryLVus_CwqQlq8"},{"id":3446,"icon":"FiyAYL0E7kWSUJB37tob3W8z41G-","audio":"FvD1B1XZM1IG7FQEOcALmCd-uDzz"},{"id":3457,"icon":"Fs-y-4lbAjEjj1fJUYBCvwUDYyJv","audio":"FksgdhaKkQhfqB75_lDj_UkKqsOM"},{"id":3447,"icon":"Fp94OxaWJWshbpjN3CP_ZRgGhu3b","audio":"FlEl1s9mtqRAbRxBV4usSRQT-kuU"},{"id":3458,"icon":"FlWnsEenf7rxAPhUKlbK1Z9y63ya","audio":"Fob7gGJuOKqVIwGOeOeNirOBV3ea"},{"id":3448,"icon":"FgHIiVAccaJgCwQGF6tokX0NXreD","audio":"FnAbJBqg_Zp31mcZo0d6vzgbDYWP"},{"id":3459,"icon":"FgDq9LVoC3jmD1mxrnXfdaNOSPgt","audio":"FjJ4XwbbzVcCFRhA_UKnBvsW0gAN"}]}
             * like : 0
             * segment : [{"id":3445,"audio":{"source":"Fqy7lmPhFN6YhBDDzJaa0s-GGSOb","time":2520}},{"id":3446,"audio":{"source":"FrBX_j6q3gGr45J9N1voTwUL7jGr","time":2232}},{"id":3447,"audio":{"source":"FgG4aM4z2jyoXcoRVzrHDx6hMoFF","time":3024}},{"id":3448,"audio":{"source":"Fs9WfpI87GhSTuTR4g9gScVLMt0M","time":3528}},{"id":3449,"audio":{"source":"FpBX54ZYsRw68hRfDNPtdyOrRBZC","time":2664}},{"id":3450,"audio":{"source":"FsbKLwll97cDEC-9rJvdLKhUMpEx","time":3744}},{"id":3451,"audio":{"source":"FihWv5GhRnv0cahm7Pg3R-KR_8le","time":2304}},{"id":3452,"audio":{"source":"FpJG1q_dA6MF6YG7YQGMgoCVRD8W","time":2664}},{"id":3453,"audio":{"source":"FqpvwKz4v-xvr8DFhYiWnZDbRMzd","time":1944}},{"id":3454,"audio":{"source":"FnsmYdTVWNtE4Jg8yQJtJyQ2HRFw","time":2304}},{"id":3455,"audio":{"source":"Fk5lOJW7yThoRACHSUU_Qt0sAGj_","time":2808}},{"id":3456,"audio":{"source":"Fiz2OHo4FOHACKsAziB-vjeA-5Hs","time":2952}},{"id":3457,"audio":{"source":"FpZgxtlYyrVaFyIqRyDo6z3UgT6N","time":2304}},{"id":3458,"audio":{"source":"FiK4Un2PY2xpk1_I6vfs5v--sZ-g","time":2880}},{"id":3459,"audio":{"source":"Fry0DxWuh5hcv3aTp4ENfG-8ILXa","time":2376}}]
             * createTime : 1515340800000
             */

            private int id;
            private UserCourseBean userCourse;
            private BookBean book;
            private int like;
            private long createTime;
            private List<SegmentBean> segment;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public UserCourseBean getUserCourse() {
                return userCourse;
            }

            public void setUserCourse(UserCourseBean userCourse) {
                this.userCourse = userCourse;
            }

            public BookBean getBook() {
                return book;
            }

            public void setBook(BookBean book) {
                this.book = book;
            }

            public int getLike() {
                return like;
            }

            public void setLike(int like) {
                this.like = like;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public List<SegmentBean> getSegment() {
                return segment;
            }

            public void setSegment(List<SegmentBean> segment) {
                this.segment = segment;
            }

            public static class UserCourseBean {
                /**
                 * user : {"id":57,"head":"FrNWT_G5TMluAoy32VbtQfVi38bw","nickname":"哎呦喂哈哈哈哈哈哈","signature":"红红火火恍恍惚惚"}
                 * course : {"id":33,"name":"1015 L2","icon":["Fr-tXj6okUGtwRZaEftFer8H0xL5"]}
                 */

                private UserBean user;
                private CourseBean course;

                public UserBean getUser() {
                    return user;
                }

                public void setUser(UserBean user) {
                    this.user = user;
                }

                public CourseBean getCourse() {
                    return course;
                }

                public void setCourse(CourseBean course) {
                    this.course = course;
                }

                public static class UserBean {
                    /**
                     * id : 57
                     * head : FrNWT_G5TMluAoy32VbtQfVi38bw
                     * nickname : 哎呦喂哈哈哈哈哈哈
                     * signature : 红红火火恍恍惚惚
                     */

                    private int id;
                    private String head;
                    private String nickname;
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

                    public String getSignature() {
                        return signature;
                    }

                    public void setSignature(String signature) {
                        this.signature = signature;
                    }
                }

                public static class CourseBean {
                    /**
                     * id : 33
                     * name : 1015 L2
                     * icon : ["Fr-tXj6okUGtwRZaEftFer8H0xL5"]
                     */

                    private int id;
                    private String name;
                    private List<String> icon;

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

                    public List<String> getIcon() {
                        return icon;
                    }

                    public void setIcon(List<String> icon) {
                        this.icon = icon;
                    }
                }
            }

            public static class BookBean {
                /**
                 * id : 86
                 * name : This is My Town
                 * bookSegmentList : [{"id":3449,"icon":"FmihG01u2BML5QUy70ErS-Y4Zuqb","audio":"Fu3GtN5s7jRoSbgByCgut7Ui-OvO"},{"id":3450,"icon":"FrME0WRyM0ytQX3Lp6Z4fztU14sH","audio":"FtP-snuN6pmQ2QGBNbb7DhpxmnqT"},{"id":3451,"icon":"Fi9OgVA9n0amUyx_ORgeymScdVKP","audio":"FoYZmOUEKys8QN6ps1oO8cmUp8Am"},{"id":3452,"icon":"Fi8nMCrsh59h1U8qKxKJaZVq7gX0","audio":"FmOBl7zPdKvtt70DzNCKL_uBm1hf"},{"id":3453,"icon":"Fh4jGQkyGrxG4K9ziQXBzQ14Hpvk","audio":"FmAhKaEqLnDQ9EpdfdR2l1dEjw6P"},{"id":3454,"icon":"FlJiA5ZNynovovapi-6TQPjkoGGE","audio":"Fpnla0kNwwVSHaR4voBXN67mvw69"},{"id":3455,"icon":"FrpzgszRMSFvubLc4EsHFQ8UQ7jb","audio":"FkT-zp5XApkCdjCI1_gJZ1sutWMB"},{"id":3445,"icon":"Fr4vLgOMMmpN87LH0am_CpX8-Khp","audio":"Fq6VDD4pNAtwLlkgKnbJV_rfGE9c"},{"id":3456,"icon":"Fjs0lzbVTskszQYyldOAW9rMTFBZ","audio":"FvsQjF91JxQr8kryLVus_CwqQlq8"},{"id":3446,"icon":"FiyAYL0E7kWSUJB37tob3W8z41G-","audio":"FvD1B1XZM1IG7FQEOcALmCd-uDzz"},{"id":3457,"icon":"Fs-y-4lbAjEjj1fJUYBCvwUDYyJv","audio":"FksgdhaKkQhfqB75_lDj_UkKqsOM"},{"id":3447,"icon":"Fp94OxaWJWshbpjN3CP_ZRgGhu3b","audio":"FlEl1s9mtqRAbRxBV4usSRQT-kuU"},{"id":3458,"icon":"FlWnsEenf7rxAPhUKlbK1Z9y63ya","audio":"Fob7gGJuOKqVIwGOeOeNirOBV3ea"},{"id":3448,"icon":"FgHIiVAccaJgCwQGF6tokX0NXreD","audio":"FnAbJBqg_Zp31mcZo0d6vzgbDYWP"},{"id":3459,"icon":"FgDq9LVoC3jmD1mxrnXfdaNOSPgt","audio":"FjJ4XwbbzVcCFRhA_UKnBvsW0gAN"}]
                 */

                private int id;
                private String name;
                private List<BookSegmentListBean> bookSegmentList;

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

                public List<BookSegmentListBean> getBookSegmentList() {
                    return bookSegmentList;
                }

                public void setBookSegmentList(List<BookSegmentListBean> bookSegmentList) {
                    this.bookSegmentList = bookSegmentList;
                }

                public static class BookSegmentListBean {
                    /**
                     * id : 3449
                     * icon : FmihG01u2BML5QUy70ErS-Y4Zuqb
                     * audio : Fu3GtN5s7jRoSbgByCgut7Ui-OvO
                     */

                    private int id;
                    private String icon;
                    private String audio;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getIcon() {
                        return icon;
                    }

                    public void setIcon(String icon) {
                        this.icon = icon;
                    }

                    public String getAudio() {
                        return audio;
                    }

                    public void setAudio(String audio) {
                        this.audio = audio;
                    }
                }
            }

            public static class SegmentBean {
                /**
                 * id : 3445
                 * audio : {"source":"Fqy7lmPhFN6YhBDDzJaa0s-GGSOb","time":2520}
                 */

                private int id;
                private AudioBean audio;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public AudioBean getAudio() {
                    return audio;
                }

                public void setAudio(AudioBean audio) {
                    this.audio = audio;
                }

                public static class AudioBean {
                    /**
                     * source : Fqy7lmPhFN6YhBDDzJaa0s-GGSOb
                     * time : 2520
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

        public static class UserBeanX {
            /**
             * like : 0
             */

            private int like;

            public int getLike() {
                return like;
            }

            public void setLike(int like) {
                this.like = like;
            }
        }
    }
}
