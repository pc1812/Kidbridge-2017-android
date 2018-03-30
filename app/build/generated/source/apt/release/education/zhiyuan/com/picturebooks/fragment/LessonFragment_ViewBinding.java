// Generated code from Butter Knife. Do not modify!
package education.zhiyuan.com.picturebooks.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.view.commodity.custom.CarouselBanner;
import education.zhiyuan.com.picturebooks.view.commodity.custom.CustomViewpager;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LessonFragment_ViewBinding implements Unbinder {
  private LessonFragment target;

  private View view2131689774;

  private View view2131689777;

  private View view2131689778;

  @UiThread
  public LessonFragment_ViewBinding(final LessonFragment target, View source) {
    this.target = target;

    View view;
    target.banner = Utils.findRequiredViewAsType(source, R.id.banner, "field 'banner'", CarouselBanner.class);
    target.lessonRecycler = Utils.findRequiredViewAsType(source, R.id.lessonRecycler, "field 'lessonRecycler'", RecyclerView.class);
    target.ageTabLayout = Utils.findRequiredViewAsType(source, R.id.ageTabLayout, "field 'ageTabLayout'", TabLayout.class);
    target.ageViewPage = Utils.findRequiredViewAsType(source, R.id.ageViewPage, "field 'ageViewPage'", CustomViewpager.class);
    target.hotRecyclerView = Utils.findRequiredViewAsType(source, R.id.hotRecyclerView, "field 'hotRecyclerView'", RecyclerView.class);
    target.swipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.swipeRefreshLayout, "field 'swipeRefreshLayout'", SwipeRefreshLayout.class);
    view = Utils.findRequiredView(source, R.id.tv_mylesson_more, "method 'onViewClicked'");
    view2131689774 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_age_more, "method 'onViewClicked'");
    view2131689777 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_hotCourse_more, "method 'onViewClicked'");
    view2131689778 = view;
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
    LessonFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.banner = null;
    target.lessonRecycler = null;
    target.ageTabLayout = null;
    target.ageViewPage = null;
    target.hotRecyclerView = null;
    target.swipeRefreshLayout = null;

    view2131689774.setOnClickListener(null);
    view2131689774 = null;
    view2131689777.setOnClickListener(null);
    view2131689777 = null;
    view2131689778.setOnClickListener(null);
    view2131689778 = null;
  }
}
