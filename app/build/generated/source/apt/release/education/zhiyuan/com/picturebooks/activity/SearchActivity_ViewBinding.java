// Generated code from Butter Knife. Do not modify!
package education.zhiyuan.com.picturebooks.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
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

public class SearchActivity_ViewBinding implements Unbinder {
  private SearchActivity target;

  private View view2131689723;

  private View view2131689698;

  private View view2131689702;

  @UiThread
  public SearchActivity_ViewBinding(SearchActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SearchActivity_ViewBinding(final SearchActivity target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.tvRight = Utils.findRequiredViewAsType(source, R.id.tv_right, "field 'tvRight'", TextView.class);
    target.etContent = Utils.findRequiredViewAsType(source, R.id.et_content, "field 'etContent'", EditText.class);
    view = Utils.findRequiredView(source, R.id.iv_back, "field 'ivBack' and method 'onViewClicked'");
    target.ivBack = Utils.castView(view, R.id.iv_back, "field 'ivBack'", ImageView.class);
    view2131689723 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.reSearch = Utils.findRequiredViewAsType(source, R.id.re_search, "field 'reSearch'", RelativeLayout.class);
    view = Utils.findRequiredView(source, R.id.iv_delete, "field 'ivDelete' and method 'onViewClicked'");
    target.ivDelete = Utils.castView(view, R.id.iv_delete, "field 'ivDelete'", ImageView.class);
    view2131689698 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_clear, "field 'tvClear' and method 'onViewClicked'");
    target.tvClear = Utils.castView(view, R.id.tv_clear, "field 'tvClear'", TextView.class);
    view2131689702 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.llRecord = Utils.findRequiredViewAsType(source, R.id.ll_record, "field 'llRecord'", LinearLayout.class);
    target.searchRecyclerView = Utils.findRequiredViewAsType(source, R.id.search_recyclerView, "field 'searchRecyclerView'", RecyclerView.class);
    target.recyclerViewRecord = Utils.findRequiredViewAsType(source, R.id.recyclerView_record, "field 'recyclerViewRecord'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SearchActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.tvRight = null;
    target.etContent = null;
    target.ivBack = null;
    target.reSearch = null;
    target.ivDelete = null;
    target.tvClear = null;
    target.llRecord = null;
    target.searchRecyclerView = null;
    target.recyclerViewRecord = null;

    view2131689723.setOnClickListener(null);
    view2131689723 = null;
    view2131689698.setOnClickListener(null);
    view2131689698 = null;
    view2131689702.setOnClickListener(null);
    view2131689702 = null;
  }
}
