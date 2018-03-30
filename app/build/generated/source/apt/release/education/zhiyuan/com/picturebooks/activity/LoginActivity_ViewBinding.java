// Generated code from Butter Knife. Do not modify!
package education.zhiyuan.com.picturebooks.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import education.zhiyuan.com.picturebooks.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  private View view2131689904;

  private View view2131689905;

  private View view2131689898;

  private View view2131689671;

  private View view2131689908;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(final LoginActivity target, View source) {
    this.target = target;

    View view;
    target.edPhone = Utils.findRequiredViewAsType(source, R.id.ed_phone, "field 'edPhone'", EditText.class);
    target.edPassword = Utils.findRequiredViewAsType(source, R.id.ed_password, "field 'edPassword'", EditText.class);
    view = Utils.findRequiredView(source, R.id.tv_new_user, "field 'tvNewUser' and method 'onViewClicked'");
    target.tvNewUser = Utils.castView(view, R.id.tv_new_user, "field 'tvNewUser'", TextView.class);
    view2131689904 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_forget, "field 'tvForget' and method 'onViewClicked'");
    target.tvForget = Utils.castView(view, R.id.tv_forget, "field 'tvForget'", TextView.class);
    view2131689905 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.spinner = Utils.findRequiredViewAsType(source, R.id.spinner, "field 'spinner'", Spinner.class);
    target.ivPhone = Utils.findRequiredViewAsType(source, R.id.iv_phone, "field 'ivPhone'", ImageView.class);
    target.pb = Utils.findRequiredViewAsType(source, R.id.pb, "field 'pb'", ProgressBar.class);
    view = Utils.findRequiredView(source, R.id.iv_logo, "field 'ivLogo' and method 'onViewClicked'");
    target.ivLogo = Utils.castView(view, R.id.iv_logo, "field 'ivLogo'", ImageView.class);
    view2131689898 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_login, "method 'onViewClicked'");
    view2131689671 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
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
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.edPhone = null;
    target.edPassword = null;
    target.tvNewUser = null;
    target.tvForget = null;
    target.spinner = null;
    target.ivPhone = null;
    target.pb = null;
    target.ivLogo = null;

    view2131689904.setOnClickListener(null);
    view2131689904 = null;
    view2131689905.setOnClickListener(null);
    view2131689905 = null;
    view2131689898.setOnClickListener(null);
    view2131689898 = null;
    view2131689671.setOnClickListener(null);
    view2131689671 = null;
    view2131689908.setOnClickListener(null);
    view2131689908 = null;
  }
}
