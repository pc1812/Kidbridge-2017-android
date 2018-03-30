// Generated code from Butter Knife. Do not modify!
package education.zhiyuan.com.picturebooks.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.view.commodity.custom.CarouselBanner;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PreviewActivity_ViewBinding implements Unbinder {
  private PreviewActivity target;

  private View view2131689668;

  private View view2131689723;

  @UiThread
  public PreviewActivity_ViewBinding(PreviewActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public PreviewActivity_ViewBinding(final PreviewActivity target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.tvRight = Utils.findRequiredViewAsType(source, R.id.tv_right, "field 'tvRight'", TextView.class);
    target.audioRecycler = Utils.findRequiredViewAsType(source, R.id.audio_recycler, "field 'audioRecycler'", RecyclerView.class);
    target.lanceBanner = Utils.findRequiredViewAsType(source, R.id.lanceBanner, "field 'lanceBanner'", CarouselBanner.class);
    view = Utils.findRequiredView(source, R.id.btn_release, "field 'btnRelease' and method 'onViewClicked'");
    target.btnRelease = Utils.castView(view, R.id.btn_release, "field 'btnRelease'", Button.class);
    view2131689668 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.iv_back, "method 'onViewClicked'");
    view2131689723 = view;
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
    PreviewActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.tvRight = null;
    target.audioRecycler = null;
    target.lanceBanner = null;
    target.btnRelease = null;

    view2131689668.setOnClickListener(null);
    view2131689668 = null;
    view2131689723.setOnClickListener(null);
    view2131689723 = null;
  }
}
