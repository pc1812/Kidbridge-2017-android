// Generated code from Butter Knife. Do not modify!
package education.zhiyuan.com.picturebooks.view.commodity.custom;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import education.zhiyuan.com.picturebooks.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CusDialog_ViewBinding implements Unbinder {
  private CusDialog target;

  @UiThread
  public CusDialog_ViewBinding(CusDialog target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public CusDialog_ViewBinding(CusDialog target, View source) {
    this.target = target;

    target.contentTxt = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'contentTxt'", TextView.class);
    target.submitTxt = Utils.findRequiredViewAsType(source, R.id.tv_left, "field 'submitTxt'", TextView.class);
    target.cancelTxt = Utils.findRequiredViewAsType(source, R.id.tv_right, "field 'cancelTxt'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CusDialog target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.contentTxt = null;
    target.submitTxt = null;
    target.cancelTxt = null;
  }
}
