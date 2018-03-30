// Generated code from Butter Knife. Do not modify!
package education.zhiyuan.com.picturebooks.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.view.commodity.custom.CarouselBanner;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LessonDetailActivity_ViewBinding implements Unbinder {
  private LessonDetailActivity target;

  private View view2131689643;

  private View view2131689723;

  @UiThread
  public LessonDetailActivity_ViewBinding(LessonDetailActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LessonDetailActivity_ViewBinding(final LessonDetailActivity target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.tvRight = Utils.findRequiredViewAsType(source, R.id.tv_right, "field 'tvRight'", TextView.class);
    target.lanceBanner = Utils.findRequiredViewAsType(source, R.id.lanceBanner, "field 'lanceBanner'", CarouselBanner.class);
    view = Utils.findRequiredView(source, R.id.tv_boom, "field 'tvBoom' and method 'onViewClicked'");
    target.tvBoom = Utils.castView(view, R.id.tv_boom, "field 'tvBoom'", TextView.class);
    view2131689643 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.webview = Utils.findRequiredViewAsType(source, R.id.webview, "field 'webview'", WebView.class);
    target.rootLayout = Utils.findRequiredViewAsType(source, R.id.rootLayout, "field 'rootLayout'", LinearLayout.class);
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
    LessonDetailActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.tvRight = null;
    target.lanceBanner = null;
    target.tvBoom = null;
    target.webview = null;
    target.rootLayout = null;

    view2131689643.setOnClickListener(null);
    view2131689643 = null;
    view2131689723.setOnClickListener(null);
    view2131689723 = null;
  }
}
