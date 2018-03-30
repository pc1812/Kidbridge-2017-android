// Generated code from Butter Knife. Do not modify!
package education.zhiyuan.com.picturebooks.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.view.commodity.custom.CarouselBanner;
import java.lang.IllegalStateException;
import java.lang.Override;

public class HomeFragment_ViewBinding implements Unbinder {
  private HomeFragment target;

  private View view2131689926;

  private View view2131689759;

  private View view2131689766;

  private View view2131689769;

  private View view2131689927;

  @UiThread
  public HomeFragment_ViewBinding(final HomeFragment target, View source) {
    this.target = target;

    View view;
    target.banner = Utils.findRequiredViewAsType(source, R.id.banner, "field 'banner'", CarouselBanner.class);
    target.ageTabLayout = Utils.findRequiredViewAsType(source, R.id.ageTabLayout, "field 'ageTabLayout'", TabLayout.class);
    target.ageViewPage = Utils.findRequiredViewAsType(source, R.id.ageViewPage, "field 'ageViewPage'", ViewPager.class);
    target.hotRecyclerView = Utils.findRequiredViewAsType(source, R.id.hotRecyclerView, "field 'hotRecyclerView'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.ivBarLeft, "field 'ivBarLeft' and method 'onViewClicked'");
    target.ivBarLeft = Utils.castView(view, R.id.ivBarLeft, "field 'ivBarLeft'", ImageView.class);
    view2131689926 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.ivTodayClock = Utils.findRequiredViewAsType(source, R.id.iv_today_clock, "field 'ivTodayClock'", ImageView.class);
    target.ivMore = Utils.findRequiredViewAsType(source, R.id.iv_more, "field 'ivMore'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.li_today_more, "field 'liTodayMore' and method 'onViewClicked'");
    target.liTodayMore = Utils.castView(view, R.id.li_today_more, "field 'liTodayMore'", LinearLayout.class);
    view2131689759 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.li_age_more, "field 'liAgeMore' and method 'onViewClicked'");
    target.liAgeMore = Utils.castView(view, R.id.li_age_more, "field 'liAgeMore'", LinearLayout.class);
    view2131689766 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.ivHomeHot = Utils.findRequiredViewAsType(source, R.id.iv_home_hot, "field 'ivHomeHot'", ImageView.class);
    target.hotMore = Utils.findRequiredViewAsType(source, R.id.hot_more, "field 'hotMore'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.li_hot_more, "field 'liHotMore' and method 'onViewClicked'");
    target.liHotMore = Utils.castView(view, R.id.li_hot_more, "field 'liHotMore'", LinearLayout.class);
    view2131689769 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.todayRecycler = Utils.findRequiredViewAsType(source, R.id.todayRecycler, "field 'todayRecycler'", RecyclerView.class);
    target.todayLl = Utils.findRequiredViewAsType(source, R.id.today_ll, "field 'todayLl'", LinearLayout.class);
    target.swipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.swipeRefreshLayout, "field 'swipeRefreshLayout'", SwipeRefreshLayout.class);
    target.tvToday = Utils.findRequiredViewAsType(source, R.id.tv_today, "field 'tvToday'", TextView.class);
    target.tvMoreToday = Utils.findRequiredViewAsType(source, R.id.tv_more_today, "field 'tvMoreToday'", TextView.class);
    view = Utils.findRequiredView(source, R.id.tvSearch, "method 'onViewClicked'");
    view2131689927 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    HomeFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.banner = null;
    target.ageTabLayout = null;
    target.ageViewPage = null;
    target.hotRecyclerView = null;
    target.ivBarLeft = null;
    target.ivTodayClock = null;
    target.ivMore = null;
    target.liTodayMore = null;
    target.liAgeMore = null;
    target.ivHomeHot = null;
    target.hotMore = null;
    target.liHotMore = null;
    target.todayRecycler = null;
    target.todayLl = null;
    target.swipeRefreshLayout = null;
    target.tvToday = null;
    target.tvMoreToday = null;

    view2131689926.setOnClickListener(null);
    view2131689926 = null;
    view2131689759.setOnClickListener(null);
    view2131689759 = null;
    view2131689766.setOnClickListener(null);
    view2131689766 = null;
    view2131689769.setOnClickListener(null);
    view2131689769 = null;
    view2131689927.setOnClickListener(null);
    view2131689927 = null;
  }
}
