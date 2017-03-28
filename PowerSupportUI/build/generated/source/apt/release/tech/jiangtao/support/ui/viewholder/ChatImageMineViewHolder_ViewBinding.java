// Generated code from Butter Knife. Do not modify!
package tech.jiangtao.support.ui.viewholder;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import tech.jiangtao.support.ui.R;

public class ChatImageMineViewHolder_ViewBinding implements Unbinder {
  private ChatImageMineViewHolder target;

  @UiThread
  public ChatImageMineViewHolder_ViewBinding(ChatImageMineViewHolder target, View source) {
    this.target = target;

    target.mItemChatAvatar = Utils.findRequiredViewAsType(source, R.id.item_chat_avatar, "field 'mItemChatAvatar'", ImageView.class);
    target.mItemChatImage = Utils.findRequiredViewAsType(source, R.id.item_chat_message, "field 'mItemChatImage'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ChatImageMineViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mItemChatAvatar = null;
    target.mItemChatImage = null;
  }
}
