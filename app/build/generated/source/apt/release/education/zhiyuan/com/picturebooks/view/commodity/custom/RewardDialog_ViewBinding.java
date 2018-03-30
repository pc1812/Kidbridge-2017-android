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

public class RewardDialog_ViewBinding implements Unbinder {
  private RewardDialog target;

  @UiThread
  public RewardDialog_ViewBinding(RewardDialog target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RewardDialog_ViewBinding(RewardDialog target, View source) {
    this.target = target;

    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.etReward = Utils.findRequiredViewAsType(source, R.id.et_reward, "field 'etReward'", EditText.class);
    target.tvSure = Utils.findRequiredViewAsType(source, R.id.tv_sure, "field 'tvSure'", TextView.class);
    target.tvCancle = Utils.findRequiredViewAsType(source, R.id.tv_cancle, "field 'tvCancle'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RewardDialog target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.etReward = null;
    target.tvSure = null;
    target.tvCancle = null;
  }
}
