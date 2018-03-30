// Generated code from Butter Knife. Do not modify!
package education.zhiyuan.com.picturebooks.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import education.zhiyuan.com.picturebooks.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ToReadActivity_ViewBinding implements Unbinder {
  private ToReadActivity target;

  private View view2131689723;

  private View view2131689929;

  private View view2131689806;

  @UiThread
  public ToReadActivity_ViewBinding(ToReadActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ToReadActivity_ViewBinding(final ToReadActivity target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.tvRight = Utils.findRequiredViewAsType(source, R.id.tv_right, "field 'tvRight'", TextView.class);
    target.mViewPage = Utils.findRequiredViewAsType(source, R.id.mViewPage, "field 'mViewPage'", ViewPager.class);
    target.llRead = Utils.findRequiredViewAsType(source, R.id.ll_read, "field 'llRead'", RelativeLayout.class);
    view = Utils.findRequiredView(source, R.id.iv_back, "field 'ivBack' and method 'onViewClicked'");
    target.ivBack = Utils.castView(view, R.id.iv_back, "field 'ivBack'", ImageView.class);
    view2131689723 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.ivBgImage = Utils.findRequiredViewAsType(source, R.id.iv_bg_image, "field 'ivBgImage'", ImageView.class);
    target.tvTime = Utils.findRequiredViewAsType(source, R.id.tv_time, "field 'tvTime'", TextView.class);
    target.tvIndex = Utils.findRequiredViewAsType(source, R.id.tv_index, "field 'tvIndex'", TextView.class);
    view = Utils.findRequiredView(source, R.id.tv_readFinish, "field 'tvReadFinish' and method 'onViewClicked'");
    target.tvReadFinish = Utils.castView(view, R.id.tv_readFinish, "field 'tvReadFinish'", TextView.class);
    view2131689929 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.ivLeft = Utils.findRequiredViewAsType(source, R.id.iv_left, "field 'ivLeft'", ImageView.class);
    target.tvContent = Utils.findRequiredViewAsType(source, R.id.tv_content, "field 'tvContent'", TextView.class);
    view = Utils.findRequiredView(source, R.id.iv_play, "field 'ivPlay' and method 'onViewClicked'");
    target.ivPlay = Utils.castView(view, R.id.iv_play, "field 'ivPlay'", ImageView.class);
    view2131689806 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.re = Utils.findRequiredViewAsType(source, R.id.re, "field 're'", RelativeLayout.class);
    target.layoutMain = Utils.findRequiredViewAsType(source, R.id.layout_main, "field 'layoutMain'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ToReadActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.tvRight = null;
    target.mViewPage = null;
    target.llRead = null;
    target.ivBack = null;
    target.ivBgImage = null;
    target.tvTime = null;
    target.tvIndex = null;
    target.tvReadFinish = null;
    target.ivLeft = null;
    target.tvContent = null;
    target.ivPlay = null;
    target.re = null;
    target.layoutMain = null;

    view2131689723.setOnClickListener(null);
    view2131689723 = null;
    view2131689929.setOnClickListener(null);
    view2131689929 = null;
    view2131689806.setOnClickListener(null);
    view2131689806 = null;
  }
}
