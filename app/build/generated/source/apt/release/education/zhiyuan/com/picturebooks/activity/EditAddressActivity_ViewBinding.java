// Generated code from Butter Knife. Do not modify!
package education.zhiyuan.com.picturebooks.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
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

public class EditAddressActivity_ViewBinding implements Unbinder {
  private EditAddressActivity target;

  private View view2131689726;

  private View view2131689612;

  private View view2131689723;

  @UiThread
  public EditAddressActivity_ViewBinding(EditAddressActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public EditAddressActivity_ViewBinding(final EditAddressActivity target, View source) {
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
    target.etName = Utils.findRequiredViewAsType(source, R.id.et_name, "field 'etName'", EditText.class);
    target.etPhone = Utils.findRequiredViewAsType(source, R.id.et_phone, "field 'etPhone'", EditText.class);
    view = Utils.findRequiredView(source, R.id.tv_address, "field 'tvAddress' and method 'onViewClicked'");
    target.tvAddress = Utils.castView(view, R.id.tv_address, "field 'tvAddress'", TextView.class);
    view2131689612 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.etDetail = Utils.findRequiredViewAsType(source, R.id.et_detail, "field 'etDetail'", EditText.class);
    target.layoutMain = Utils.findRequiredViewAsType(source, R.id.layout_main, "field 'layoutMain'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.iv_back, "method 'onViewClicked'");
    view2131689723 = view;
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
    EditAddressActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.tvRight = null;
    target.etName = null;
    target.etPhone = null;
    target.tvAddress = null;
    target.etDetail = null;
    target.layoutMain = null;

    view2131689726.setOnClickListener(null);
    view2131689726 = null;
    view2131689612.setOnClickListener(null);
    view2131689612 = null;
    view2131689723.setOnClickListener(null);
    view2131689723 = null;
  }
}
