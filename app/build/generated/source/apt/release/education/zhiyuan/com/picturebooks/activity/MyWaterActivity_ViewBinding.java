// Generated code from Butter Knife. Do not modify!
package education.zhiyuan.com.picturebooks.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import education.zhiyuan.com.picturebooks.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MyWaterActivity_ViewBinding implements Unbinder {
  private MyWaterActivity target;

  private View view2131689663;

  private View view2131689723;

  private View view2131689911;

  @UiThread
  public MyWaterActivity_ViewBinding(MyWaterActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MyWaterActivity_ViewBinding(final MyWaterActivity target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.tvRight = Utils.findRequiredViewAsType(source, R.id.tv_right, "field 'tvRight'", TextView.class);
    target.bonusNum = Utils.findRequiredViewAsType(source, R.id.bonus_num, "field 'bonusNum'", TextView.class);
    view = Utils.findRequiredView(source, R.id.tv_detail, "field 'tvDetail' and method 'onViewClicked'");
    target.tvDetail = Utils.castView(view, R.id.tv_detail, "field 'tvDetail'", TextView.class);
    view2131689663 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.iv_back, "method 'onViewClicked'");
    view2131689723 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_dh, "method 'onViewClicked'");
    view2131689911 = view;
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
    MyWaterActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.tvRight = null;
    target.bonusNum = null;
    target.tvDetail = null;

    view2131689663.setOnClickListener(null);
    view2131689663 = null;
    view2131689723.setOnClickListener(null);
    view2131689723 = null;
    view2131689911.setOnClickListener(null);
    view2131689911 = null;
  }
}
