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

public class AllChatListViewHolder_ViewBinding implements Unbinder {
  private AllChatListViewHolder target;

  @UiThread
  public AllChatListViewHolder_ViewBinding(AllChatListViewHolder target, View source) {
    this.target = target;

    target.mItemChatAvatar = Utils.findRequiredViewAsType(source, R.id.item_chat_avatar, "field 'mItemChatAvatar'", ImageView.class);
    target.mSessionNickname = Utils.findRequiredViewAsType(source, R.id.session_nickname, "field 'mSessionNickname'", TextView.class);
    target.mSessionMessage = Utils.findRequiredViewAsType(source, R.id.session_message, "field 'mSessionMessage'", TextView.class);
    target.mSessionTime = Utils.findRequiredViewAsType(source, R.id.session_time, "field 'mSessionTime'", TextView.class);
    target.mSessionUnreadCount = Utils.findRequiredViewAsType(source, R.id.session_unread_count, "field 'mSessionUnreadCount'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AllChatListViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mItemChatAvatar = null;
    target.mSessionNickname = null;
    target.mSessionMessage = null;
    target.mSessionTime = null;
    target.mSessionUnreadCount = null;
  }
}
