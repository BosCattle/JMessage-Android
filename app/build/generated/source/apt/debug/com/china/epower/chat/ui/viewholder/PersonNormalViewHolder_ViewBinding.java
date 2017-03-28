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

public class PersonNormalViewHolder_ViewBinding implements Unbinder {
  private PersonNormalViewHolder target;

  @UiThread
  public PersonNormalViewHolder_ViewBinding(PersonNormalViewHolder target, View source) {
    this.target = target;

    target.mListItemTitle = Utils.findRequiredViewAsType(source, R.id.list_item_title, "field 'mListItemTitle'", TextView.class);
    target.mListItemArrow = Utils.findRequiredViewAsType(source, R.id.list_item_arrow, "field 'mListItemArrow'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PersonNormalViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mListItemTitle = null;
    target.mListItemArrow = null;
  }
}
