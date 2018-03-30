// Generated code from Butter Knife. Do not modify!
package education.zhiyuan.com.picturebooks.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import education.zhiyuan.com.picturebooks.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentLessonAge_ViewBinding implements Unbinder {
  private FragmentLessonAge target;

  @UiThread
  public FragmentLessonAge_ViewBinding(FragmentLessonAge target, View source) {
    this.target = target;

    target.mRecyclerView = Utils.findRequiredViewAsType(source, R.id.mRecyclerView, "field 'mRecyclerView'", RecyclerView.class);
    target.swipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.swipeRefreshLayout, "field 'swipeRefreshLayout'", SwipeRefreshLayout.class);
    target.ll = Utils.findRequiredViewAsType(source, R.id.ll, "field 'll'", LinearLayout.class);
    target.llMes = Utils.findRequiredViewAsType(source, R.id.ll_mes, "field 'llMes'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FragmentLessonAge target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mRecyclerView = null;
    target.swipeRefreshLayout = null;
    target.ll = null;
    target.llMes = null;
  }
}
