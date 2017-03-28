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

public class PersonalImageViewHolder_ViewBinding implements Unbinder {
  private PersonalImageViewHolder target;

  @UiThread
  public PersonalImageViewHolder_ViewBinding(PersonalImageViewHolder target, View source) {
    this.target = target;

    target.mItemTitle = Utils.findRequiredViewAsType(source, R.id.item_title, "field 'mItemTitle'", TextView.class);
    target.mItemImage = Utils.findRequiredViewAsType(source, R.id.item_image, "field 'mItemImage'", CircleImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PersonalImageViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mItemTitle = null;
    target.mItemImage = null;
  }
}
