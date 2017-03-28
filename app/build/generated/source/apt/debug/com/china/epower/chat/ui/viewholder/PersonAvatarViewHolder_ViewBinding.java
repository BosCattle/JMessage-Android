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

public class PersonAvatarViewHolder_ViewBinding implements Unbinder {
  private PersonAvatarViewHolder target;

  @UiThread
  public PersonAvatarViewHolder_ViewBinding(PersonAvatarViewHolder target, View source) {
    this.target = target;

    target.mItemPersonAvatar = Utils.findRequiredViewAsType(source, R.id.item_person_avatar, "field 'mItemPersonAvatar'", ImageView.class);
    target.mItemPersonUsername = Utils.findRequiredViewAsType(source, R.id.item_person_username, "field 'mItemPersonUsername'", TextView.class);
    target.mItemPersonStyle = Utils.findRequiredViewAsType(source, R.id.item_person_style, "field 'mItemPersonStyle'", TextView.class);
    target.mItemPersonArrow = Utils.findRequiredViewAsType(source, R.id.item_person_arrow, "field 'mItemPersonArrow'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PersonAvatarViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mItemPersonAvatar = null;
    target.mItemPersonUsername = null;
    target.mItemPersonStyle = null;
    target.mItemPersonArrow = null;
  }
}
