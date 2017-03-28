// Generated code from Butter Knife. Do not modify!
package com.china.epower.chat.ui.viewholder;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.china.epower.chat.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PersonShadowViewHolder_ViewBinding implements Unbinder {
  private PersonShadowViewHolder target;

  @UiThread
  public PersonShadowViewHolder_ViewBinding(PersonShadowViewHolder target, View source) {
    this.target = target;

    target.mItemShadow = Utils.findRequiredViewAsType(source, R.id.item_shadow, "field 'mItemShadow'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PersonShadowViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mItemShadow = null;
  }
}
