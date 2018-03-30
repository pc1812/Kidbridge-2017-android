package education.zhiyuan.com.picturebooks.adpter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import education.zhiyuan.com.picturebooks.R;

/**
 * Created by Lance on 2017/6/22.
 * Email : COCOINUT@163.com
 * Introduce :
 */

public class AdapterToReadHui extends RecyclerView.Adapter {

    private Context context;
    private List<String> list;
    private boolean Iseditor = false;

    public AdapterToReadHui(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    public void editor(boolean editor) {
        this.Iseditor = editor;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_to_read_books, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (Iseditor) {

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_image)
        ImageView ivImage;
        @BindView(R.id.iv_charge)
        ImageView ivCharge;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_left)
        TextView tvLeft;
        @BindView(R.id.tv_right)
        TextView tvRight;
        @BindView(R.id.tv_cen)
        TextView tvCen;
        @BindView(R.id.today_left)
        CardView todayLeft;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
