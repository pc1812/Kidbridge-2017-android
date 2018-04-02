package education.zhiyuan.com.picturebooks.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import education.zhiyuan.com.picturebooks.MyApp;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.bean.BookDetial;
import education.zhiyuan.com.picturebooks.bean.DataBean;
import education.zhiyuan.com.picturebooks.http.Api;
import education.zhiyuan.com.picturebooks.utils.GlideUtils;


/**
 * Created by syy on 2017/5/4.
 */
public class BookListRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ItemOnClick itemOnClick;
    private Map<Integer, String> fitMap;
    private List<DataBean> bookList;
    public BookListRecyclerViewAdapter(Context context, List<DataBean> bookList) {
        this.context = context;
        this.bookList=bookList;
        fitMap = new HashMap<>();
        fitMap.put(0, "3-5岁");
        fitMap.put(1, "6-8岁");
        fitMap.put(2, "9-12岁");
        fitMap.put(3, "4-7岁");
        fitMap.put(4, "8-10岁");
        itemOnClick=(ItemOnClick)context;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       if(viewType==1){
           View v = LayoutInflater.from(parent.getContext())
                   .inflate(R.layout.item_card_imag_books, parent, false);
           return new ViewHolderImag(v);
       } else if(viewType==2) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_card_books, parent, false);
            return new ViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int itemViewType = getItemViewType(position);
        if(itemViewType==1){
            final ViewHolderImag viewHolder = ( ViewHolderImag ) holder;
            String icon=bookList.get(position).getIcon();
            GlideUtils.GlideNormal(context.getApplicationContext(), Api.QN + icon, viewHolder.imageView, R.drawable.iv_replace_les);
            viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemOnClick.imageClick(position);
                }
            });
        }
      else  if(itemViewType==2) {
            final ViewHolder viewHolder = (ViewHolder) holder;
            BookDetial.DataBean.BookListBean bookListBean = bookList.get(position).getBookList();
            if (bookListBean.getIcon().size() != 0) {
                GlideUtils.GlideNormal(MyApp.getInstance(), Api.QN + bookListBean.getIcon().get(0), viewHolder.imageView, R.drawable.iv_replace_hb);
            }
            viewHolder.textView.setText(bookListBean.getName());
            viewHolder.linearLayout.removeAllViews();
            if (bookListBean.getTag().size() > 0) {
                for (int i = 0; i < 1; i++) {  //dataBean.getTag().size()
                    View view = LayoutInflater.from(MyApp.getInstance()).inflate(R.layout.item_lesson_tag, null);
                    TextView textView = view.findViewById(R.id.tv_tag);
                    textView.setText(bookListBean.getTag().get(i));
                    viewHolder.linearLayout.addView(view);
                }
            }

            if (bookListBean.getLock() == 0) {
                viewHolder.chargeImg.setVisibility(View.VISIBLE);
            } else {
                viewHolder.chargeImg.setVisibility(View.GONE);
            }

            if (bookListBean.getPrice() > 0) {
                viewHolder.todayMind.setVisibility(View.GONE);
                viewHolder.todayLeftAge.setText(fitMap.get(bookListBean.getFit()));
            } else {
                viewHolder.todayMind.setVisibility(View.VISIBLE);
                if (bookListBean.getFit() == 0) {
                    viewHolder.todayLeftAge.setText("3-5岁");
                } else if (bookListBean.getFit() == 1) {
                    viewHolder.todayLeftAge.setText("6-8岁");
                } else {
                    viewHolder.todayLeftAge.setText("9-12岁");
                }
                viewHolder.todayLeftAge.setText("免费");
            }

            viewHolder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemOnClick.itemClick(position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return bookList.get(position).getType();
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
       TextView textView;
       ImageView imageView,chargeImg;
       LinearLayout linearLayout;
       TextView todayMind,todayLeftAge;
       LinearLayout item;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView= itemView.findViewById(R.id.iv_today_left);
            textView=itemView.findViewById(R.id.tv_today_left);
            linearLayout=itemView.findViewById(R.id.ll_tag);
            chargeImg= itemView.findViewById(R.id.iv_charge);
            todayMind=itemView.findViewById(R.id.tv_today_mid);
            todayLeftAge=itemView.findViewById(R.id.tv_today_left_age);
            item=itemView.findViewById(R.id.li_books);
        }
    }

    private static class ViewHolderImag extends RecyclerView.ViewHolder {

        ImageView imageView;
        LinearLayout linearLayout;
        public ViewHolderImag(View itemView) {
            super(itemView);
            imageView= itemView.findViewById(R.id.iv_todayclock);
            linearLayout=itemView.findViewById(R.id.li);
        }
    }
    public interface ItemOnClick{
        void itemClick(int position);
        void imageClick(int position);
    }


}
