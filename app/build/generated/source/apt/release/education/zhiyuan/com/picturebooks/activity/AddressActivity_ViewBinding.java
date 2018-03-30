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

public class AddressActivity_ViewBinding implements Unbinder {
  private AddressActivity target;

  private View view2131689723;

  private View view2131689614;

  @UiThread
  public AddressActivity_ViewBinding(AddressActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AddressActivity_ViewBinding(final AddressActivity target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.tvName = Utils.findRequiredViewAsType(source, R.id.tv_name, "field 'tvName'", TextView.class);
    target.tvPhone = Utils.findRequiredViewAsType(source, R.id.tv_phone, "field 'tvPhone'", TextView.class);
    target.tvAddress = Utils.findRequiredViewAsType(source, R.id.tv_address, "field 'tvAddress'", TextView.class);
    view = Utils.findRequiredView(source, R.id.iv_back, "method 'onViewClicked'");
    view2131689723 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ll, "method 'onViewClicked'");
    view2131689614 = view;
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
    AddressActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.tvName = null;
    target.tvPhone = null;
    target.tvAddress = null;

    view2131689723.setOnClickListener(null);
    view2131689723 = null;
    view2131689614.setOnClickListener(null);
    view2131689614 = null;
  }
}
