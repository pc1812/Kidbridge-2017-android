// Generated code from Butter Knife. Do not modify!
package education.zhiyuan.com.picturebooks.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import education.zhiyuan.com.picturebooks.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MyAchievementActivity_ViewBinding implements Unbinder {
  private MyAchievementActivity target;

  private View view2131689723;

  @UiThread
  public MyAchievementActivity_ViewBinding(MyAchievementActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MyAchievementActivity_ViewBinding(final MyAchievementActivity target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.tvRight = Utils.findRequiredViewAsType(source, R.id.tv_right, "field 'tvRight'", TextView.class);
    target.medalRecyclerView = Utils.findRequiredViewAsType(source, R.id.medal_recyclerView, "field 'medalRecyclerView'", RecyclerView.class);
    target.llModel = Utils.findRequiredViewAsType(source, R.id.ll_model, "field 'llModel'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.iv_back, "method 'onViewClicked'");
    view2131689723 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    MyAchievementActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.tvRight = null;
    target.medalRecyclerView = null;
    target.llModel = null;

    view2131689723.setOnClickListener(null);
    view2131689723 = null;
  }
}
