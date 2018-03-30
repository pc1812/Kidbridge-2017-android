// Generated code from Butter Knife. Do not modify!
package education.zhiyuan.com.picturebooks.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import education.zhiyuan.com.picturebooks.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RegisteredActivity_ViewBinding implements Unbinder {
  private RegisteredActivity target;

  private View view2131689924;

  private View view2131689922;

  private View view2131689925;

  private View view2131689908;

  @UiThread
  public RegisteredActivity_ViewBinding(RegisteredActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RegisteredActivity_ViewBinding(final RegisteredActivity target, View source) {
    this.target = target;

    View view;
    target.edPhone = Utils.findRequiredViewAsType(source, R.id.edPhone, "field 'edPhone'", EditText.class);
    target.edPassword = Utils.findRequiredViewAsType(source, R.id.et_p, "field 'edPassword'", EditText.class);
    view = Utils.findRequiredView(source, R.id.tv_has, "field 'tvHas' and method 'onViewClicked'");
    target.tvHas = Utils.castView(view, R.id.tv_has, "field 'tvHas'", TextView.class);
    view2131689924 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.edCode = Utils.findRequiredViewAsType(source, R.id.ed_code, "field 'edCode'", EditText.class);
    view = Utils.findRequiredView(source, R.id.tv_code, "field 'tvCode' and method 'onViewClicked'");
    target.tvCode = Utils.castView(view, R.id.tv_code, "field 'tvCode'", TextView.class);
    view2131689922 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.ivPhone = Utils.findRequiredViewAsType(source, R.id.iv_phone, "field 'ivPhone'", ImageView.class);
    target.spinner = Utils.findRequiredViewAsType(source, R.id.spinner, "field 'spinner'", Spinner.class);
    view = Utils.findRequiredView(source, R.id.btn_register, "field 'btnRegister' and method 'onViewClicked'");
    target.btnRegister = Utils.castView(view, R.id.btn_register, "field 'btnRegister'", Button.class);
    view2131689925 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.pb = Utils.findRequiredViewAsType(source, R.id.pb, "field 'pb'", ProgressBar.class);
    target.reView = Utils.findRequiredViewAsType(source, R.id.re_view, "field 'reView'", RelativeLayout.class);
    target.iv = Utils.findRequiredViewAsType(source, R.id.iv, "field 'iv'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.WChatLogin, "method 'onViewClicked'");
    view2131689908 = view;
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
    RegisteredActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.edPhone = null;
    target.edPassword = null;
    target.tvHas = null;
    target.edCode = null;
    target.tvCode = null;
    target.ivPhone = null;
    target.spinner = null;
    target.btnRegister = null;
    target.pb = null;
    target.reView = null;
    target.iv = null;

    view2131689924.setOnClickListener(null);
    view2131689924 = null;
    view2131689922.setOnClickListener(null);
    view2131689922 = null;
    view2131689925.setOnClickListener(null);
    view2131689925 = null;
    view2131689908.setOnClickListener(null);
    view2131689908 = null;
  }
}
