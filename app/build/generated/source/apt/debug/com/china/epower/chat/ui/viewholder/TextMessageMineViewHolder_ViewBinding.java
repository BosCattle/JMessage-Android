// Generated code from Butter Knife. Do not modify!
package com.china.epower.chat.ui.viewholder;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.china.epower.chat.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TextMessageMineViewHolder_ViewBinding implements Unbinder {
  private TextMessageMineViewHolder target;

  @UiThread
  public TextMessageMineViewHolder_ViewBinding(TextMessageMineViewHolder target, View source) {
    this.target = target;

    target.mItemChatAvatar = Utils.findRequiredViewAsType(source, R.id.item_chat_avatar, "field 'mItemChatAvatar'", ImageView.class);
    target.mItemChatMessage = Utils.findRequiredViewAsType(source, R.id.item_chat_message, "field 'mItemChatMessage'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    TextMessageMineViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mItemChatAvatar = null;
    target.mItemChatMessage = null;
  }
}
