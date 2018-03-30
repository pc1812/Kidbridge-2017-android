// Generated code from Butter Knife. Do not modify!
package education.zhiyuan.com.picturebooks.adpter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import education.zhiyuan.com.picturebooks.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AdapterCommentVoice$ViewHolder_ViewBinding implements Unbinder {
  private AdapterCommentVoice.ViewHolder target;

  @UiThread
  public AdapterCommentVoice$ViewHolder_ViewBinding(AdapterCommentVoice.ViewHolder target,
      View source) {
    this.target = target;

    target.chatTvVoiceLen = Utils.findRequiredViewAsType(source, R.id.chat_tv_voice_len, "field 'chatTvVoiceLen'", TextView.class);
    target.ivVoiceImage = Utils.findRequiredViewAsType(source, R.id.iv_voice_image, "field 'ivVoiceImage'", ImageView.class);
    target.voiceLayout = Utils.findRequiredViewAsType(source, R.id.voice_layout, "field 'voiceLayout'", RelativeLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AdapterCommentVoice.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.chatTvVoiceLen = null;
    target.ivVoiceImage = null;
    target.voiceLayout = null;
  }
}
