package education.zhiyuan.com.picturebooks.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by LH on 2017/9/29.
 * 评论消息
 */

public class JGCommentBean extends DataSupport {
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getCommentPid() {
        return commentPid;
    }

    public void setCommentPid(int commentPid) {
        this.commentPid = commentPid;
    }

    public int getCommentType() {
        return commentType;
    }

    public void setCommentType(int commentType) {
        this.commentType = commentType;
    }

    public int getCommentSid() {
        return commentSid;
    }

    public void setCommentSid(int commentSid) {
        this.commentSid = commentSid;
    }

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private String tag;//区分用户
    private int commentPid;
    private int commentType;
    private int commentSid;
    private long createTime;
    private String text;
    private String head;
    private String nickname;
    private int userId;
}
