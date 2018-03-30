// Generated code from Butter Knife. Do not modify!
package education.zhiyuan.com.picturebooks.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import education.zhiyuan.com.picturebooks.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ComActivity_ViewBinding implements Unbinder {
  private ComActivity target;

  private View view2131689723;

  private View view2131689894;

  @UiThread
  public ComActivity_ViewBinding(ComActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ComActivity_ViewBinding(final ComActivity target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.tvRight = Utils.findRequiredViewAsType(source, R.id.tv_right, "field 'tvRight'", TextView.class);
    target.voiceRecyclerView = Utils.findRequiredViewAsType(source, R.id.voiceRecyclerView, "field 'voiceRecyclerView'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.iv_back, "field 'ivBack' and method 'onViewClicked'");
    target.ivBack = Utils.castView(view, R.id.iv_back, "field 'ivBack'", ImageView.class);
    view2131689723 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.chatRecord = Utils.findRequiredViewAsType(source, R.id.chat_record, "field 'chatRecord'", ImageView.class);
    target.chatTvSoundNotice = Utils.findRequiredViewAsType(source, R.id.chat_tv_sound_notice, "field 'chatTvSoundNotice'", TextView.class);
    target.et = Utils.findRequiredViewAsType(source, R.id.et, "field 'et'", EditText.class);
    view = Utils.findRequiredView(source, R.id.btn_commit, "method 'onViewClicked'");
    view2131689894 = view;
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
    ComActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.tvRight = null;
    target.voiceRecyclerView = null;
    target.ivBack = null;
    target.chatRecord = null;
    target.chatTvSoundNotice = null;
    target.et = null;

    view2131689723.setOnClickListener(null);
    view2131689723 = null;
    view2131689894.setOnClickListener(null);
    view2131689894 = null;
  }
}
