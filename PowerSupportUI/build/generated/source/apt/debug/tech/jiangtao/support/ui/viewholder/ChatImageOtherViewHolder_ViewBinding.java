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

public class ChatImageOtherViewHolder_ViewBinding implements Unbinder {
  private ChatImageOtherViewHolder target;

  @UiThread
  public ChatImageOtherViewHolder_ViewBinding(ChatImageOtherViewHolder target, View source) {
    this.target = target;

    target.mItemChatAvatar = Utils.findRequiredViewAsType(source, R.id.item_chat_avatar, "field 'mItemChatAvatar'", ImageView.class);
    target.mItemChatImg = Utils.findRequiredViewAsType(source, R.id.item_chat_img, "field 'mItemChatImg'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ChatImageOtherViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mItemChatAvatar = null;
    target.mItemChatImg = null;
  }
}
