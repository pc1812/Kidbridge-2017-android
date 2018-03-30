// Generated code from Butter Knife. Do not modify!
package education.zhiyuan.com.picturebooks.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import education.zhiyuan.com.picturebooks.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class UserInfoActivity_ViewBinding implements Unbinder {
  private UserInfoActivity target;

  private View view2131689723;

  private View view2131689935;

  private View view2131689934;

  private View view2131689865;

  private View view2131689936;

  @UiThread
  public UserInfoActivity_ViewBinding(UserInfoActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public UserInfoActivity_ViewBinding(final UserInfoActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.iv_back, "field 'ivBack' and method 'onViewClicked'");
    target.ivBack = Utils.castView(view, R.id.iv_back, "field 'ivBack'", ImageView.class);
    view2131689723 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.tvRight = Utils.findRequiredViewAsType(source, R.id.tv_right, "field 'tvRight'", TextView.class);
    view = Utils.findRequiredView(source, R.id.tv_birthDay, "field 'tvBirthDay' and method 'onViewClicked'");
    target.tvBirthDay = Utils.castView(view, R.id.tv_birthDay, "field 'tvBirthDay'", TextView.class);
    view2131689935 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.et_nickName, "field 'etNickName' and method 'onViewClicked'");
    target.etNickName = Utils.castView(view, R.id.et_nickName, "field 'etNickName'", TextView.class);
    view2131689934 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.tvPhone = Utils.findRequiredViewAsType(source, R.id.tv_phone, "field 'tvPhone'", TextView.class);
    view = Utils.findRequiredView(source, R.id.iv_head, "field 'ivHead' and method 'onViewClicked'");
    target.ivHead = Utils.castView(view, R.id.iv_head, "field 'ivHead'", ImageView.class);
    view2131689865 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.tvAddress = Utils.findRequiredViewAsType(source, R.id.tv_address, "field 'tvAddress'", TextView.class);
    target.ivAddress = Utils.findRequiredViewAsType(source, R.id.iv_address, "field 'ivAddress'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.re_address, "method 'onViewClicked'");
    view2131689936 = view;
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
    UserInfoActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ivBack = null;
    target.tvTitle = null;
    target.tvRight = null;
    target.tvBirthDay = null;
    target.etNickName = null;
    target.tvPhone = null;
    target.ivHead = null;
    target.tvAddress = null;
    target.ivAddress = null;

    view2131689723.setOnClickListener(null);
    view2131689723 = null;
    view2131689935.setOnClickListener(null);
    view2131689935 = null;
    view2131689934.setOnClickListener(null);
    view2131689934 = null;
    view2131689865.setOnClickListener(null);
    view2131689865 = null;
    view2131689936.setOnClickListener(null);
    view2131689936 = null;
  }
}
