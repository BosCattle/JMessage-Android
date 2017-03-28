// Generated code from Butter Knife. Do not modify!
package tech.jiangtao.support.ui.viewholder;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;
import tech.jiangtao.support.ui.R;

public class GroupListViewHolder_ViewBinding implements Unbinder {
  private GroupListViewHolder target;

  @UiThread
  public GroupListViewHolder_ViewBinding(GroupListViewHolder target, View source) {
    this.target = target;

    target.mGroupAvatar = Utils.findRequiredViewAsType(source, R.id.group_avatar, "field 'mGroupAvatar'", CircleImageView.class);
    target.mGroupName = Utils.findRequiredViewAsType(source, R.id.group_name, "field 'mGroupName'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    GroupListViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mGroupAvatar = null;
    target.mGroupName = null;
  }
}
