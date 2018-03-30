// Generated code from Butter Knife. Do not modify!
package education.zhiyuan.com.picturebooks.adpter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import education.zhiyuan.com.picturebooks.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AdapterToReadHui$ViewHolder_ViewBinding implements Unbinder {
  private AdapterToReadHui.ViewHolder target;

  @UiThread
  public AdapterToReadHui$ViewHolder_ViewBinding(AdapterToReadHui.ViewHolder target, View source) {
    this.target = target;

    target.ivImage = Utils.findRequiredViewAsType(source, R.id.iv_image, "field 'ivImage'", ImageView.class);
    target.ivCharge = Utils.findRequiredViewAsType(source, R.id.iv_charge, "field 'ivCharge'", ImageView.class);
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.tvLeft = Utils.findRequiredViewAsType(source, R.id.tv_left, "field 'tvLeft'", TextView.class);
    target.tvRight = Utils.findRequiredViewAsType(source, R.id.tv_right, "field 'tvRight'", TextView.class);
    target.tvCen = Utils.findRequiredViewAsType(source, R.id.tv_cen, "field 'tvCen'", TextView.class);
    target.todayLeft = Utils.findRequiredViewAsType(source, R.id.today_left, "field 'todayLeft'", CardView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AdapterToReadHui.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ivImage = null;
    target.ivCharge = null;
    target.tvTitle = null;
    target.tvLeft = null;
    target.tvRight = null;
    target.tvCen = null;
    target.todayLeft = null;
  }
}
