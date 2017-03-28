// Generated code from Butter Knife. Do not modify!
package com.china.epower.chat.ui.viewholder;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.china.epower.chat.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ContactViewHolder_ViewBinding implements Unbinder {
  private ContactViewHolder target;

  @UiThread
  public ContactViewHolder_ViewBinding(ContactViewHolder target, View source) {
    this.target = target;

    target.mItemChatImg = Utils.findRequiredViewAsType(source, R.id.item_chat_img, "field 'mItemChatImg'", CircleImageView.class);
    target.mItemChatUsername = Utils.findRequiredViewAsType(source, R.id.item_chat_username, "field 'mItemChatUsername'", TextView.class);
    target.mItemChatMessage = Utils.findRequiredViewAsType(source, R.id.item_chat_message, "field 'mItemChatMessage'", TextView.class);
    target.mItemChatTime = Utils.findRequiredViewAsType(source, R.id.item_chat_time, "field 'mItemChatTime'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ContactViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mItemChatImg = null;
    target.mItemChatUsername = null;
    target.mItemChatMessage = null;
    target.mItemChatTime = null;
  }
}
