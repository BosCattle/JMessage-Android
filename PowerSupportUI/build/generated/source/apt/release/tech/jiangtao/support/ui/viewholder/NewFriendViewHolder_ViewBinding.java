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

public class NewFriendViewHolder_ViewBinding implements Unbinder {
  private NewFriendViewHolder target;

  @UiThread
  public NewFriendViewHolder_ViewBinding(NewFriendViewHolder target, View source) {
    this.target = target;

    target.mNewFriendAvatar = Utils.findRequiredViewAsType(source, R.id.new_friend_avatar, "field 'mNewFriendAvatar'", ImageView.class);
    target.mNewFriendNickname = Utils.findRequiredViewAsType(source, R.id.new_friend_nickname, "field 'mNewFriendNickname'", TextView.class);
    target.mNewFriendAgree = Utils.findRequiredViewAsType(source, R.id.new_friend_agree, "field 'mNewFriendAgree'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    NewFriendViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mNewFriendAvatar = null;
    target.mNewFriendNickname = null;
    target.mNewFriendAgree = null;
  }
}
