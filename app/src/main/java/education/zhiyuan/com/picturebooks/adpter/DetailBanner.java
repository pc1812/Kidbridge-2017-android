package education.zhiyuan.com.picturebooks.adpter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import education.zhiyuan.com.picturebooks.R;

/**
 * Created by leo on 16/5/7.
 */
public class DetailBanner extends PagerAdapter {
    private Context context;
    private List<String> imgsList;

    public DetailBanner(Context context, List<String> imgs) {
        this.context = context;
        this.imgsList = imgs;
    }


    @Override
    public int getCount() {
        if (imgsList.size() > 0) {
            return Integer.MAX_VALUE;
        } else {
            return 0;
        }
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.detail_banner, null);
        ImageView imageView = view.findViewById(R.id.iv_image);
        Glide.with(context).load(imgsList.get(position % imgsList.size()))
                .into(imageView);
        container.addView(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnClickListener != null) {
                    mOnClickListener.setOnItemClickListener(position % imgsList.size());
                }
            }
        });
        return view;

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    public AdapterOnClickListener mOnClickListener;

    public interface AdapterOnClickListener {
        void setOnItemClickListener(int position);
    }

    public void setAdapterOnclickListener(AdapterOnClickListener adapterOnclickListener) {
        this.mOnClickListener = adapterOnclickListener;
    }

}
