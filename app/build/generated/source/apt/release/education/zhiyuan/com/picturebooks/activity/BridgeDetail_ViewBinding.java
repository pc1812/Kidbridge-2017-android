// Generated code from Butter Knife. Do not modify!
package education.zhiyuan.com.picturebooks.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.view.commodity.MScrollview;
import education.zhiyuan.com.picturebooks.view.commodity.custom.CarouselBanner;
import java.lang.IllegalStateException;
import java.lang.Override;

public class BridgeDetail_ViewBinding implements Unbinder {
  private BridgeDetail target;

  private View view2131689888;

  private View view2131689889;

  private View view2131689887;

  private View view2131689723;

  @UiThread
  public BridgeDetail_ViewBinding(BridgeDetail target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public BridgeDetail_ViewBinding(final BridgeDetail target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.tvRight = Utils.findRequiredViewAsType(source, R.id.tv_right, "field 'tvRight'", TextView.class);
    target.lanceBanner = Utils.findRequiredViewAsType(source, R.id.lanceBanner, "field 'lanceBanner'", CarouselBanner.class);
    target.mRecyclerView = Utils.findRequiredViewAsType(source, R.id.mRecyclerView, "field 'mRecyclerView'", RecyclerView.class);
    target.tvStory = Utils.findRequiredViewAsType(source, R.id.tv_story, "field 'tvStory'", TextView.class);
    target.tvFelling = Utils.findRequiredViewAsType(source, R.id.tv_felling, "field 'tvFelling'", TextView.class);
    target.tvAge = Utils.findRequiredViewAsType(source, R.id.tv_age, "field 'tvAge'", TextView.class);
    target.tvKeyword = Utils.findRequiredViewAsType(source, R.id.tv_keyword, "field 'tvKeyword'", TextView.class);
    view = Utils.findRequiredView(source, R.id.tv_Read, "field 'tvRead' and method 'onViewClicked'");
    target.tvRead = Utils.castView(view, R.id.tv_Read, "field 'tvRead'", TextView.class);
    view2131689888 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_Appreciation, "field 'tvAppreciation' and method 'onViewClicked'");
    target.tvAppreciation = Utils.castView(view, R.id.tv_Appreciation, "field 'tvAppreciation'", TextView.class);
    view2131689889 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.llFree = Utils.findRequiredViewAsType(source, R.id.ll_free, "field 'llFree'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.tv_nlock, "field 'tvNlock' and method 'onViewClicked'");
    target.tvNlock = Utils.castView(view, R.id.tv_nlock, "field 'tvNlock'", TextView.class);
    view2131689887 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.myScroll = Utils.findRequiredViewAsType(source, R.id.myScroll, "field 'myScroll'", MScrollview.class);
    target.reBom = Utils.findRequiredViewAsType(source, R.id.re_bom, "field 'reBom'", RelativeLayout.class);
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
    BridgeDetail target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.tvRight = null;
    target.lanceBanner = null;
    target.mRecyclerView = null;
    target.tvStory = null;
    target.tvFelling = null;
    target.tvAge = null;
    target.tvKeyword = null;
    target.tvRead = null;
    target.tvAppreciation = null;
    target.llFree = null;
    target.tvNlock = null;
    target.myScroll = null;
    target.reBom = null;

    view2131689888.setOnClickListener(null);
    view2131689888 = null;
    view2131689889.setOnClickListener(null);
    view2131689889 = null;
    view2131689887.setOnClickListener(null);
    view2131689887 = null;
    view2131689723.setOnClickListener(null);
    view2131689723 = null;
  }
}
