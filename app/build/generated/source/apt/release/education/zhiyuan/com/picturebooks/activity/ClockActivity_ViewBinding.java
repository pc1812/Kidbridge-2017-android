// Generated code from Butter Knife. Do not modify!
package education.zhiyuan.com.picturebooks.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import education.zhiyuan.com.picturebooks.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ClockActivity_ViewBinding implements Unbinder {
  private ClockActivity target;

  private View view2131689723;

  private View view2131689721;

  @UiThread
  public ClockActivity_ViewBinding(ClockActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ClockActivity_ViewBinding(final ClockActivity target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.tvRight = Utils.findRequiredViewAsType(source, R.id.tv_right, "field 'tvRight'", TextView.class);
    target.tabLayout = Utils.findRequiredViewAsType(source, R.id.tabLayout, "field 'tabLayout'", TabLayout.class);
    target.viewPage = Utils.findRequiredViewAsType(source, R.id.viewPage, "field 'viewPage'", ViewPager.class);
    target.etReplay = Utils.findRequiredViewAsType(source, R.id.et_replay, "field 'etReplay'", EditText.class);
    target.llReplay = Utils.findRequiredViewAsType(source, R.id.ll_replay, "field 'llReplay'", LinearLayout.class);
    target.swipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.swipeRefreshLayout, "field 'swipeRefreshLayout'", SwipeRefreshLayout.class);
    view = Utils.findRequiredView(source, R.id.iv_back, "method 'onViewClicked'");
    view2131689723 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_send, "method 'onViewClicked'");
    view2131689721 = view;
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
    ClockActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.tvRight = null;
    target.tabLayout = null;
    target.viewPage = null;
    target.etReplay = null;
    target.llReplay = null;
    target.swipeRefreshLayout = null;

    view2131689723.setOnClickListener(null);
    view2131689723 = null;
    view2131689721.setOnClickListener(null);
    view2131689721 = null;
  }
}
