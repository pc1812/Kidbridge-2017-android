// Generated code from Butter Knife. Do not modify!
package education.zhiyuan.com.picturebooks.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import education.zhiyuan.com.picturebooks.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CommentsActivity_ViewBinding implements Unbinder {
  private CommentsActivity target;

  @UiThread
  public CommentsActivity_ViewBinding(CommentsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public CommentsActivity_ViewBinding(CommentsActivity target, View source) {
    this.target = target;

    target.ivBack = Utils.findRequiredViewAsType(source, R.id.iv_back, "field 'ivBack'", ImageView.class);
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.tvRight = Utils.findRequiredViewAsType(source, R.id.tv_right, "field 'tvRight'", TextView.class);
    target.mIvRecord = Utils.findRequiredViewAsType(source, R.id.chat_record, "field 'mIvRecord'", ImageView.class);
    target.mTvNotice = Utils.findRequiredViewAsType(source, R.id.chat_tv_sound_notice, "field 'mTvNotice'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CommentsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ivBack = null;
    target.tvTitle = null;
    target.tvRight = null;
    target.mIvRecord = null;
    target.mTvNotice = null;
  }
}
