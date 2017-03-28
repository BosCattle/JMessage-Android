// Generated code from Butter Knife. Do not modify!
package tech.jiangtao.support.ui.viewholder;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import tech.jiangtao.support.ui.R;

public class AddFriendViewHolder_ViewBinding implements Unbinder {
  private AddFriendViewHolder target;

  @UiThread
  public AddFriendViewHolder_ViewBinding(AddFriendViewHolder target, View source) {
    this.target = target;

    target.mAddFriendImg = Utils.findRequiredViewAsType(source, R.id.add_friend_img, "field 'mAddFriendImg'", ImageView.class);
    target.mAddFriendUsername = Utils.findRequiredViewAsType(source, R.id.add_friend_username, "field 'mAddFriendUsername'", TextView.class);
    target.mAddFriendEmail = Utils.findRequiredViewAsType(source, R.id.add_friend_email, "field 'mAddFriendEmail'", TextView.class);
    target.mAddFriendSubmit = Utils.findRequiredViewAsType(source, R.id.add_friend_submit, "field 'mAddFriendSubmit'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AddFriendViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mAddFriendImg = null;
    target.mAddFriendUsername = null;
    target.mAddFriendEmail = null;
    target.mAddFriendSubmit = null;
  }
}
