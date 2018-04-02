package education.zhiyuan.com.picturebooks.bean;

/**
 * Created by Spring on 2017/9/1.
 */

public class DataBean {
    public BookDetial.DataBean.BookListBean getBookList() {
        return bookList;
    }

    public void setBookList(BookDetial.DataBean.BookListBean bookList) {
        this.bookList = bookList;
    }

    private BookDetial.DataBean.BookListBean bookList;
   private int type;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    private String icon;
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }




}
