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
import education.zhiyuan.com.picturebooks.calenders.MonthCalendar;
import education.zhiyuan.com.picturebooks.view.commodity.MScrollview;
import java.lang.IllegalStateException;
import java.lang.Override;

public class StartLessonDetailActivity_ViewBinding implements Unbinder {
  private StartLessonDetailActivity target;

  private View view2131689712;

  private View view2131689723;

  private View view2131689717;

  private View view2131689621;

  private View view2131689622;

  @UiThread
  public StartLessonDetailActivity_ViewBinding(StartLessonDetailActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public StartLessonDetailActivity_ViewBinding(final StartLessonDetailActivity target,
      View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.tvRight = Utils.findRequiredViewAsType(source, R.id.tv_right, "field 'tvRight'", TextView.class);
    target.clockRecycler = Utils.findRequiredViewAsType(source, R.id.clock_recycler, "field 'clockRecycler'", RecyclerView.class);
    target.lessonRecycler = Utils.findRequiredViewAsType(source, R.id.lesson_Recycler, "field 'lessonRecycler'", RecyclerView.class);
    target.monthCalendar = Utils.findRequiredViewAsType(source, R.id.monthCalendar, "field 'monthCalendar'", MonthCalendar.class);
    target.tvTime = Utils.findRequiredViewAsType(source, R.id.tv_time, "field 'tvTime'", TextView.class);
    view = Utils.findRequiredView(source, R.id.tv_toDaka, "field 'tvToDaka' and method 'onViewClicked'");
    target.tvToDaka = Utils.castView(view, R.id.tv_toDaka, "field 'tvToDaka'", TextView.class);
    view2131689712 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.myScroll = Utils.findRequiredViewAsType(source, R.id.myScroll, "field 'myScroll'", MScrollview.class);
    target.llTeacher = Utils.findRequiredViewAsType(source, R.id.ll_teacher, "field 'llTeacher'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.iv_back, "method 'onViewClicked'");
    view2131689723 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_toDiscussion, "method 'onViewClicked'");
    view2131689717 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.iv_left, "method 'onViewClicked'");
    view2131689621 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.iv_right, "method 'onViewClicked'");
    view2131689622 = view;
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
    StartLessonDetailActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.tvRight = null;
    target.clockRecycler = null;
    target.lessonRecycler = null;
    target.monthCalendar = null;
    target.tvTime = null;
    target.tvToDaka = null;
    target.myScroll = null;
    target.llTeacher = null;

    view2131689712.setOnClickListener(null);
    view2131689712 = null;
    view2131689723.setOnClickListener(null);
    view2131689723 = null;
    view2131689717.setOnClickListener(null);
    view2131689717 = null;
    view2131689621.setOnClickListener(null);
    view2131689621 = null;
    view2131689622.setOnClickListener(null);
    view2131689622 = null;
  }
}
