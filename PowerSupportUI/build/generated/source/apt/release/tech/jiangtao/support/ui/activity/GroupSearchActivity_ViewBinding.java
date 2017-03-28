// Generated code from Butter Knife. Do not modify!
package tech.jiangtao.support.ui.activity;

import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import tech.jiangtao.support.ui.R;

public class GroupSearchActivity_ViewBinding extends BaseActivity_ViewBinding {
  private GroupSearchActivity target;

  @UiThread
  public GroupSearchActivity_ViewBinding(GroupSearchActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public GroupSearchActivity_ViewBinding(GroupSearchActivity target, View source) {
    super(target, source);

    this.target = target;

    target.mTvToolbar = Utils.findRequiredViewAsType(source, R.id.tv_toolbar, "field 'mTvToolbar'", TextView.class);
    target.mToolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'mToolbar'", Toolbar.class);
    target.mGroupAdd = Utils.findRequiredViewAsType(source, R.id.group_add, "field 'mGroupAdd'", RecyclerView.class);
  }

  @Override
  public void unbind() {
    GroupSearchActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mTvToolbar = null;
    target.mToolbar = null;
    target.mGroupAdd = null;

    super.unbind();
  }
}
