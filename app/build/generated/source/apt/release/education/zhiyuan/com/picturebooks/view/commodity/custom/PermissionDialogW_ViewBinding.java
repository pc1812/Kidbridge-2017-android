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

public class PermissionDialogW_ViewBinding implements Unbinder {
  private PermissionDialogW target;

  @UiThread
  public PermissionDialogW_ViewBinding(PermissionDialogW target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public PermissionDialogW_ViewBinding(PermissionDialogW target, View source) {
    this.target = target;

    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.tvContent = Utils.findRequiredViewAsType(source, R.id.tv_content, "field 'tvContent'", TextView.class);
    target.tvLeft = Utils.findRequiredViewAsType(source, R.id.tv_left, "field 'tvLeft'", TextView.class);
    target.tvRight = Utils.findRequiredViewAsType(source, R.id.tv_right, "field 'tvRight'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PermissionDialogW target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.tvContent = null;
    target.tvLeft = null;
    target.tvRight = null;
  }
}
