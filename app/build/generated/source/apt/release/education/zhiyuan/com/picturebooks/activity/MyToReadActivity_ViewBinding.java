// Generated code from Butter Knife. Do not modify!
package education.zhiyuan.com.picturebooks.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import education.zhiyuan.com.picturebooks.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MyToReadActivity_ViewBinding implements Unbinder {
  private MyToReadActivity target;

  private View view2131689726;

  private View view2131689917;

  private View view2131689723;

  private View view2131689918;

  @UiThread
  public MyToReadActivity_ViewBinding(MyToReadActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MyToReadActivity_ViewBinding(final MyToReadActivity target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    view = Utils.findRequiredView(source, R.id.tv_right, "field 'tvRight' and method 'onViewClicked'");
    target.tvRight = Utils.castView(view, R.id.tv_right, "field 'tvRight'", TextView.class);
    view2131689726 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.llSelectDelete = Utils.findRequiredViewAsType(source, R.id.ll_select_delete, "field 'llSelectDelete'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.cb_selectAll, "field 'cbSelectAll' and method 'onViewClicked'");
    target.cbSelectAll = Utils.castView(view, R.id.cb_selectAll, "field 'cbSelectAll'", CheckBox.class);
    view2131689917 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.tabLayout = Utils.findRequiredViewAsType(source, R.id.tabLayout, "field 'tabLayout'", TabLayout.class);
    target.viewPage = Utils.findRequiredViewAsType(source, R.id.viewPage, "field 'viewPage'", ViewPager.class);
    target.swipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.swipeRefreshLayout, "field 'swipeRefreshLayout'", SwipeRefreshLayout.class);
    target.tvNoData = Utils.findRequiredViewAsType(source, R.id.tv_no_data, "field 'tvNoData'", TextView.class);
    view = Utils.findRequiredView(source, R.id.iv_back, "method 'onViewClicked'");
    view2131689723 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ll_delete, "method 'onViewClicked'");
    view2131689918 = view;
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
    MyToReadActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.tvRight = null;
    target.llSelectDelete = null;
    target.cbSelectAll = null;
    target.tabLayout = null;
    target.viewPage = null;
    target.swipeRefreshLayout = null;
    target.tvNoData = null;

    view2131689726.setOnClickListener(null);
    view2131689726 = null;
    view2131689917.setOnClickListener(null);
    view2131689917 = null;
    view2131689723.setOnClickListener(null);
    view2131689723 = null;
    view2131689918.setOnClickListener(null);
    view2131689918 = null;
  }
}
