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

public class ContactHeadViewHolder_ViewBinding implements Unbinder {
  private ContactHeadViewHolder target;

  @UiThread
  public ContactHeadViewHolder_ViewBinding(ContactHeadViewHolder target, View source) {
    this.target = target;

    target.mItemChatHead = Utils.findRequiredViewAsType(source, R.id.item_chat_head, "field 'mItemChatHead'", CircleImageView.class);
    target.mItemChatTitle = Utils.findRequiredViewAsType(source, R.id.item_chat_title, "field 'mItemChatTitle'", TextView.class);
    target.mItemOnlineStatus = Utils.findRequiredViewAsType(source, R.id.item_online_status, "field 'mItemOnlineStatus'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ContactHeadViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mItemChatHead = null;
    target.mItemChatTitle = null;
    target.mItemOnlineStatus = null;
  }
}
