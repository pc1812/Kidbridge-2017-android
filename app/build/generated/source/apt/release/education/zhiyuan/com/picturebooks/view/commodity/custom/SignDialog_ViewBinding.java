// Generated code from Butter Knife. Do not modify!
package education.zhiyuan.com.picturebooks.view.commodity.custom;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import education.zhiyuan.com.picturebooks.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SignDialog_ViewBinding implements Unbinder {
  private SignDialog target;

  @UiThread
  public SignDialog_ViewBinding(SignDialog target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SignDialog_ViewBinding(SignDialog target, View source) {
    this.target = target;

    target.etSign = Utils.findRequiredViewAsType(source, R.id.et_sign, "field 'etSign'", EditText.class);
    target.tvSure = Utils.findRequiredViewAsType(source, R.id.tv_sure, "field 'tvSure'", TextView.class);
    target.tvCancle = Utils.findRequiredViewAsType(source, R.id.tv_cancle, "field 'tvCancle'", TextView.class);
    target.tv = Utils.findRequiredViewAsType(source, R.id.tv, "field 'tv'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SignDialog target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.etSign = null;
    target.tvSure = null;
    target.tvCancle = null;
    target.tv = null;
  }
}
