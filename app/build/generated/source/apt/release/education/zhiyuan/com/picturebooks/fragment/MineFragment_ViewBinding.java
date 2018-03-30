// Generated code from Butter Knife. Do not modify!
package education.zhiyuan.com.picturebooks.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import education.zhiyuan.com.picturebooks.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MineFragment_ViewBinding implements Unbinder {
  private MineFragment target;

  private View view2131689781;

  private View view2131689796;

  private View view2131689779;

  private View view2131689783;

  private View view2131689787;

  private View view2131689788;

  private View view2131689790;

  private View view2131689791;

  private View view2131689792;

  private View view2131689793;

  private View view2131689794;

  private View view2131689795;

  private View view2131689797;

  private View view2131689798;

  private View view2131689799;

  private View view2131689801;

  private View view2131689800;

  @UiThread
  public MineFragment_ViewBinding(final MineFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.iv_Head, "field 'ivHead' and method 'onViewClicked'");
    target.ivHead = Utils.castView(view, R.id.iv_Head, "field 'ivHead'", ImageView.class);
    view2131689781 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.tvAge = Utils.findRequiredViewAsType(source, R.id.tv_age, "field 'tvAge'", TextView.class);
    target.tvContent = Utils.findRequiredViewAsType(source, R.id.tv_content, "field 'tvContent'", TextView.class);
    target.tvMedal = Utils.findRequiredViewAsType(source, R.id.tv_medal, "field 'tvMedal'", TextView.class);
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.redPoint = Utils.findRequiredViewAsType(source, R.id.redPoint, "field 'redPoint'", ImageView.class);
    target.tvBlance = Utils.findRequiredViewAsType(source, R.id.tv_blance, "field 'tvBlance'", TextView.class);
    target.tvName = Utils.findRequiredViewAsType(source, R.id.tv_name, "field 'tvName'", TextView.class);
    view = Utils.findRequiredView(source, R.id.re_teacher, "field 'reTeacher' and method 'onViewClicked'");
    target.reTeacher = Utils.castView(view, R.id.re_teacher, "field 'reTeacher'", RelativeLayout.class);
    view2131689796 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.iv_message, "method 'onViewClicked'");
    view2131689779 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.li_signature, "method 'onViewClicked'");
    view2131689783 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.re_achievement, "method 'onViewClicked'");
    view2131689787 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.re_balance, "method 'onViewClicked'");
    view2131689788 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.re_data, "method 'onViewClicked'");
    view2131689790 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.re_water, "method 'onViewClicked'");
    view2131689791 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.re_read, "method 'onViewClicked'");
    view2131689792 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.re_bridge, "method 'onViewClicked'");
    view2131689793 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.re_lesson, "method 'onViewClicked'");
    view2131689794 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.re_evaluation, "method 'onViewClicked'");
    view2131689795 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.re_setting, "method 'onViewClicked'");
    view2131689797 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.re_feedback, "method 'onViewClicked'");
    view2131689798 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.re_about, "method 'onViewClicked'");
    view2131689799 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.re_out, "method 'onViewClicked'");
    view2131689801 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.re_service, "method 'onViewClicked'");
    view2131689800 = view;
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
    MineFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ivHead = null;
    target.tvAge = null;
    target.tvContent = null;
    target.tvMedal = null;
    target.tvTitle = null;
    target.redPoint = null;
    target.tvBlance = null;
    target.tvName = null;
    target.reTeacher = null;

    view2131689781.setOnClickListener(null);
    view2131689781 = null;
    view2131689796.setOnClickListener(null);
    view2131689796 = null;
    view2131689779.setOnClickListener(null);
    view2131689779 = null;
    view2131689783.setOnClickListener(null);
    view2131689783 = null;
    view2131689787.setOnClickListener(null);
    view2131689787 = null;
    view2131689788.setOnClickListener(null);
    view2131689788 = null;
    view2131689790.setOnClickListener(null);
    view2131689790 = null;
    view2131689791.setOnClickListener(null);
    view2131689791 = null;
    view2131689792.setOnClickListener(null);
    view2131689792 = null;
    view2131689793.setOnClickListener(null);
    view2131689793 = null;
    view2131689794.setOnClickListener(null);
    view2131689794 = null;
    view2131689795.setOnClickListener(null);
    view2131689795 = null;
    view2131689797.setOnClickListener(null);
    view2131689797 = null;
    view2131689798.setOnClickListener(null);
    view2131689798 = null;
    view2131689799.setOnClickListener(null);
    view2131689799 = null;
    view2131689801.setOnClickListener(null);
    view2131689801 = null;
    view2131689800.setOnClickListener(null);
    view2131689800 = null;
  }
}
