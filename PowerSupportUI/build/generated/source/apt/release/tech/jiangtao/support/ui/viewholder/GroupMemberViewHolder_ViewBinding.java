// Generated code from Butter Knife. Do not modify!
package tech.jiangtao.support.ui.viewholder;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;
import tech.jiangtao.support.ui.R;

public class GroupMemberViewHolder_ViewBinding implements Unbinder {
  private GroupMemberViewHolder target;

  @UiThread
  public GroupMemberViewHolder_ViewBinding(GroupMemberViewHolder target, View source) {
    this.target = target;

    target.mGroupMemberAvatar = Utils.findRequiredViewAsType(source, R.id.group_member_avatar, "field 'mGroupMemberAvatar'", CircleImageView.class);
    target.mGroupMemberName = Utils.findRequiredViewAsType(source, R.id.group_member_name, "field 'mGroupMemberName'", TextView.class);
    target.mGroupMemberCheckbox = Utils.findRequiredViewAsType(source, R.id.group_member_checkbox, "field 'mGroupMemberCheckbox'", AppCompatCheckBox.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    GroupMemberViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mGroupMemberAvatar = null;
    target.mGroupMemberName = null;
    target.mGroupMemberCheckbox = null;
  }
}
