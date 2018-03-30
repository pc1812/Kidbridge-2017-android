// Generated code from Butter Knife. Do not modify!
package education.zhiyuan.com.picturebooks.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.view.commodity.MyScrollView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentToRead_ViewBinding implements Unbinder {
  private FragmentToRead target;

  private View view2131689806;

  private View view2131689807;

  @UiThread
  public FragmentToRead_ViewBinding(final FragmentToRead target, View source) {
    this.target = target;

    View view;
    target.tvIndex = Utils.findRequiredViewAsType(source, R.id.tv_index, "field 'tvIndex'", TextView.class);
    target.tvEnContent = Utils.findRequiredViewAsType(source, R.id.tv_en_content, "field 'tvEnContent'", TextView.class);
    target.view1 = Utils.findRequiredView(source, R.id.view1, "field 'view1'");
    target.liBottom = Utils.findRequiredViewAsType(source, R.id.li_bottom, "field 'liBottom'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.iv_play, "field 'ivPlay' and method 'onViewClicked'");
    target.ivPlay = Utils.castView(view, R.id.iv_play, "field 'ivPlay'", ImageView.class);
    view2131689806 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.iv_record, "field 'ivRecord' and method 'onViewClicked'");
    target.ivRecord = Utils.castView(view, R.id.iv_record, "field 'ivRecord'", ImageView.class);
    view2131689807 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.viewLeft = Utils.findRequiredView(source, R.id.viewLeft, "field 'viewLeft'");
    target.viewRight = Utils.findRequiredView(source, R.id.viewRight, "field 'viewRight'");
    target.scrollView = Utils.findRequiredViewAsType(source, R.id.scrollView, "field 'scrollView'", MyScrollView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FragmentToRead target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvIndex = null;
    target.tvEnContent = null;
    target.view1 = null;
    target.liBottom = null;
    target.ivPlay = null;
    target.ivRecord = null;
    target.viewLeft = null;
    target.viewRight = null;
    target.scrollView = null;

    view2131689806.setOnClickListener(null);
    view2131689806 = null;
    view2131689807.setOnClickListener(null);
    view2131689807 = null;
  }
}
