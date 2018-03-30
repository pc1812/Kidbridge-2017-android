// Generated code from Butter Knife. Do not modify!
package education.zhiyuan.com.picturebooks.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.view.commodity.MScrollview;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ToReadDetailActivity_ViewBinding implements Unbinder {
  private ToReadDetailActivity target;

  private View view2131689630;

  private View view2131689694;

  private View view2131689675;

  private View view2131689692;

  private View view2131689679;

  private View view2131689622;

  private View view2131689931;

  private View view2131689932;

  private View view2131689723;

  private View view2131689688;

  @UiThread
  public ToReadDetailActivity_ViewBinding(ToReadDetailActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ToReadDetailActivity_ViewBinding(final ToReadDetailActivity target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.seekBar = Utils.findRequiredViewAsType(source, R.id.seekBar, "field 'seekBar'", SeekBar.class);
    target.commentRecycler = Utils.findRequiredViewAsType(source, R.id.comment_recycler, "field 'commentRecycler'", RecyclerView.class);
    target.ivAdmire = Utils.findRequiredViewAsType(source, R.id.iv_admire, "field 'ivAdmire'", ImageView.class);
    target.llComment = Utils.findRequiredViewAsType(source, R.id.ll_comment, "field 'llComment'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.tv_comment, "field 'tvComment' and method 'onViewClicked'");
    target.tvComment = Utils.castView(view, R.id.tv_comment, "field 'tvComment'", TextView.class);
    view2131689630 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_toRead, "field 'tvToRead' and method 'onViewClicked'");
    target.tvToRead = Utils.castView(view, R.id.tv_toRead, "field 'tvToRead'", TextView.class);
    view2131689694 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.myScrollView = Utils.findRequiredViewAsType(source, R.id.scrollView, "field 'myScrollView'", MScrollview.class);
    view = Utils.findRequiredView(source, R.id.iv_start, "field 'ivStart' and method 'onViewClicked'");
    target.ivStart = Utils.castView(view, R.id.iv_start, "field 'ivStart'", ImageView.class);
    view2131689675 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.timeLength = Utils.findRequiredViewAsType(source, R.id.time_length, "field 'timeLength'", TextView.class);
    target.tvName = Utils.findRequiredViewAsType(source, R.id.tv_name, "field 'tvName'", TextView.class);
    target.tvSign = Utils.findRequiredViewAsType(source, R.id.tv_sign, "field 'tvSign'", TextView.class);
    target.tvCount = Utils.findRequiredViewAsType(source, R.id.tv_count, "field 'tvCount'", TextView.class);
    target.tvTime = Utils.findRequiredViewAsType(source, R.id.tv_time, "field 'tvTime'", TextView.class);
    target.llInfo = Utils.findRequiredViewAsType(source, R.id.ll_info, "field 'llInfo'", LinearLayout.class);
    target.llTime = Utils.findRequiredViewAsType(source, R.id.ll_time, "field 'llTime'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.tv_matchComment, "field 'tvMatchComment' and method 'onViewClicked'");
    target.tvMatchComment = Utils.castView(view, R.id.tv_matchComment, "field 'tvMatchComment'", TextView.class);
    view2131689692 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.tvPlayTime = Utils.findRequiredViewAsType(source, R.id.tv_playTime, "field 'tvPlayTime'", TextView.class);
    view = Utils.findRequiredView(source, R.id.iv_dz, "field 'ivDz' and method 'onViewClicked'");
    target.ivDz = Utils.castView(view, R.id.iv_dz, "field 'ivDz'", ImageView.class);
    view2131689679 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.llBottom = Utils.findRequiredViewAsType(source, R.id.ll_bottom, "field 'llBottom'", LinearLayout.class);
    target.llNoComment = Utils.findRequiredViewAsType(source, R.id.ll_no_comment, "field 'llNoComment'", LinearLayout.class);
    target.tvType = Utils.findRequiredViewAsType(source, R.id.tv_type, "field 'tvType'", TextView.class);
    target.llEncourage = Utils.findRequiredViewAsType(source, R.id.ll_encourage, "field 'llEncourage'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.iv_right, "field 'ivRight' and method 'onViewClicked'");
    target.ivRight = Utils.castView(view, R.id.iv_right, "field 'ivRight'", ImageView.class);
    view2131689622 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.webview = Utils.findRequiredViewAsType(source, R.id.webview, "field 'webview'", WebView.class);
    target.rootLayout = Utils.findRequiredViewAsType(source, R.id.rootLayout, "field 'rootLayout'", LinearLayout.class);
    target.tvLoadFinish = Utils.findRequiredViewAsType(source, R.id.tv_load_finish, "field 'tvLoadFinish'", TextView.class);
    view = Utils.findRequiredView(source, R.id.iv_play_left, "field 'ivPlayLeft' and method 'onViewClicked'");
    target.ivPlayLeft = Utils.castView(view, R.id.iv_play_left, "field 'ivPlayLeft'", ImageView.class);
    view2131689931 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.iv_play_right, "field 'ivPlayRight' and method 'onViewClicked'");
    target.ivPlayRight = Utils.castView(view, R.id.iv_play_right, "field 'ivPlayRight'", ImageView.class);
    view2131689932 = view;
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
    view = Utils.findRequiredView(source, R.id.btn_encourage, "method 'onViewClicked'");
    view2131689688 = view;
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
    ToReadDetailActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.seekBar = null;
    target.commentRecycler = null;
    target.ivAdmire = null;
    target.llComment = null;
    target.tvComment = null;
    target.tvToRead = null;
    target.myScrollView = null;
    target.ivStart = null;
    target.timeLength = null;
    target.tvName = null;
    target.tvSign = null;
    target.tvCount = null;
    target.tvTime = null;
    target.llInfo = null;
    target.llTime = null;
    target.tvMatchComment = null;
    target.tvPlayTime = null;
    target.ivDz = null;
    target.llBottom = null;
    target.llNoComment = null;
    target.tvType = null;
    target.llEncourage = null;
    target.ivRight = null;
    target.webview = null;
    target.rootLayout = null;
    target.tvLoadFinish = null;
    target.ivPlayLeft = null;
    target.ivPlayRight = null;

    view2131689630.setOnClickListener(null);
    view2131689630 = null;
    view2131689694.setOnClickListener(null);
    view2131689694 = null;
    view2131689675.setOnClickListener(null);
    view2131689675 = null;
    view2131689692.setOnClickListener(null);
    view2131689692 = null;
    view2131689679.setOnClickListener(null);
    view2131689679 = null;
    view2131689622.setOnClickListener(null);
    view2131689622 = null;
    view2131689931.setOnClickListener(null);
    view2131689931 = null;
    view2131689932.setOnClickListener(null);
    view2131689932 = null;
    view2131689723.setOnClickListener(null);
    view2131689723 = null;
    view2131689688.setOnClickListener(null);
    view2131689688 = null;
  }
}
