// Generated code from Butter Knife. Do not modify!
package education.zhiyuan.com.picturebooks.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import education.zhiyuan.com.picturebooks.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ForgetPwdActivity_ViewBinding implements Unbinder {
  private ForgetPwdActivity target;

  private View view2131689635;

  private View view2131689723;

  private View view2131689636;

  @UiThread
  public ForgetPwdActivity_ViewBinding(ForgetPwdActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ForgetPwdActivity_ViewBinding(final ForgetPwdActivity target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.tvRight = Utils.findRequiredViewAsType(source, R.id.tv_right, "field 'tvRight'", TextView.class);
    target.etPhone = Utils.findRequiredViewAsType(source, R.id.et_phone, "field 'etPhone'", EditText.class);
    target.etCode = Utils.findRequiredViewAsType(source, R.id.et_code, "field 'etCode'", EditText.class);
    view = Utils.findRequiredView(source, R.id.btn_code, "field 'btnCode' and method 'onViewClicked'");
    target.btnCode = Utils.castView(view, R.id.btn_code, "field 'btnCode'", Button.class);
    view2131689635 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.spinner = Utils.findRequiredViewAsType(source, R.id.spinner, "field 'spinner'", Spinner.class);
    target.etNewPwd = Utils.findRequiredViewAsType(source, R.id.et_newPwd, "field 'etNewPwd'", EditText.class);
    view = Utils.findRequiredView(source, R.id.iv_back, "method 'onViewClicked'");
    view2131689723 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_next, "method 'onViewClicked'");
    view2131689636 = view;
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
    ForgetPwdActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.tvRight = null;
    target.etPhone = null;
    target.etCode = null;
    target.btnCode = null;
    target.spinner = null;
    target.etNewPwd = null;

    view2131689635.setOnClickListener(null);
    view2131689635 = null;
    view2131689723.setOnClickListener(null);
    view2131689723 = null;
    view2131689636.setOnClickListener(null);
    view2131689636 = null;
  }
}
