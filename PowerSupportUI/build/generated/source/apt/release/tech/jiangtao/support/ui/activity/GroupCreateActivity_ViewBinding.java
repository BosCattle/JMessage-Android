// Generated code from Butter Knife. Do not modify!
package tech.jiangtao.support.ui.activity;

import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import tech.jiangtao.support.ui.R;

public class GroupCreateActivity_ViewBinding extends BaseActivity_ViewBinding {
  private GroupCreateActivity target;

  @UiThread
  public GroupCreateActivity_ViewBinding(GroupCreateActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public GroupCreateActivity_ViewBinding(GroupCreateActivity target, View source) {
    super(target, source);

    this.target = target;

    target.mTvToolbar = Utils.findRequiredViewAsType(source, R.id.tv_toolbar, "field 'mTvToolbar'", TextView.class);
    target.mToolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'mToolbar'", Toolbar.class);
    target.mGroupAdd = Utils.findRequiredViewAsType(source, R.id.group_add, "field 'mGroupAdd'", RecyclerView.class);
    target.mSearchView = Utils.findRequiredViewAsType(source, R.id.group_edit, "field 'mSearchView'", SearchView.class);
  }

  @Override
  public void unbind() {
    GroupCreateActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mTvToolbar = null;
    target.mToolbar = null;
    target.mGroupAdd = null;
    target.mSearchView = null;

    super.unbind();
  }
}
