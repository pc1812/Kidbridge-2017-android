// Generated code from Butter Knife. Do not modify!
package education.zhiyuan.com.picturebooks;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  private View view2131689647;

  private View view2131689650;

  private View view2131689653;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(final MainActivity target, View source) {
    this.target = target;

    View view;
    target.ivHome = Utils.findRequiredViewAsType(source, R.id.iv_home, "field 'ivHome'", ImageView.class);
    target.ivLesson = Utils.findRequiredViewAsType(source, R.id.iv_lesson, "field 'ivLesson'", ImageView.class);
    target.ivMine = Utils.findRequiredViewAsType(source, R.id.iv_mine, "field 'ivMine'", ImageView.class);
    target.redPoint = Utils.findRequiredViewAsType(source, R.id.iv_red, "field 'redPoint'", ImageView.class);
    target.tvHb = Utils.findRequiredViewAsType(source, R.id.tv_hb, "field 'tvHb'", TextView.class);
    target.tvLess = Utils.findRequiredViewAsType(source, R.id.tv_less, "field 'tvLess'", TextView.class);
    target.tvM = Utils.findRequiredViewAsType(source, R.id.tv_m, "field 'tvM'", TextView.class);
    view = Utils.findRequiredView(source, R.id.li_home, "method 'onViewClicked'");
    view2131689647 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.li_lesson, "method 'onViewClicked'");
    view2131689650 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.li_mine, "method 'onViewClicked'");
    view2131689653 = view;
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
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ivHome = null;
    target.ivLesson = null;
    target.ivMine = null;
    target.redPoint = null;
    target.tvHb = null;
    target.tvLess = null;
    target.tvM = null;

    view2131689647.setOnClickListener(null);
    view2131689647 = null;
    view2131689650.setOnClickListener(null);
    view2131689650 = null;
    view2131689653.setOnClickListener(null);
    view2131689653 = null;
  }
}
