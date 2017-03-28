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

public class ContactsViewHolder_ViewBinding implements Unbinder {
  private ContactsViewHolder target;

  @UiThread
  public ContactsViewHolder_ViewBinding(ContactsViewHolder target, View source) {
    this.target = target;

    target.mItemChat = Utils.findRequiredViewAsType(source, R.id.wocao, "field 'mItemChat'", CircleImageView.class);
    target.mItemChatUsername = Utils.findRequiredViewAsType(source, R.id.item_chat_username, "field 'mItemChatUsername'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ContactsViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mItemChat = null;
    target.mItemChatUsername = null;
  }
}
