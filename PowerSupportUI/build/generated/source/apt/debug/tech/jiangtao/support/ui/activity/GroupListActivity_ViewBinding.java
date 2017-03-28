// Generated code from Butter Knife. Do not modify!
package tech.jiangtao.support.ui.activity;

import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import tech.jiangtao.support.ui.R;

public class GroupListActivity_ViewBinding extends BaseActivity_ViewBinding {
  private GroupListActivity target;

  @UiThread
  public GroupListActivity_ViewBinding(GroupListActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public GroupListActivity_ViewBinding(GroupListActivity target, View source) {
    super(target, source);

    this.target = target;

    target.mTvToolbar = Utils.findRequiredViewAsType(source, R.id.tv_toolbar, "field 'mTvToolbar'", TextView.class);
    target.mToolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'mToolbar'", Toolbar.class);
    target.mGroupImage = Utils.findRequiredViewAsType(source, R.id.group_image, "field 'mGroupImage'", ImageView.class);
    target.mGroupList = Utils.findRequiredViewAsType(source, R.id.group_list, "field 'mGroupList'", RecyclerView.class);
    target.mGroupSwiftRefresh = Utils.findRequiredViewAsType(source, R.id.group_swift_refresh, "field 'mGroupSwiftRefresh'", SwipeRefreshLayout.class);
  }

  @Override
  public void unbind() {
    GroupListActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mTvToolbar = null;
    target.mToolbar = null;
    target.mGroupImage = null;
    target.mGroupList = null;
    target.mGroupSwiftRefresh = null;

    super.unbind();
  }
}
