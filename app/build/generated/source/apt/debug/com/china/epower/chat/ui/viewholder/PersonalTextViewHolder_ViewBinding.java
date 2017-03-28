// Generated code from Butter Knife. Do not modify!
package com.china.epower.chat.ui.viewholder;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.china.epower.chat.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PersonalTextViewHolder_ViewBinding implements Unbinder {
  private PersonalTextViewHolder target;

  @UiThread
  public PersonalTextViewHolder_ViewBinding(PersonalTextViewHolder target, View source) {
    this.target = target;

    target.mListItemTitle = Utils.findRequiredViewAsType(source, R.id.list_item_title, "field 'mListItemTitle'", TextView.class);
    target.mListItemSubtitle = Utils.findRequiredViewAsType(source, R.id.list_item_subtitle, "field 'mListItemSubtitle'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PersonalTextViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mListItemTitle = null;
    target.mListItemSubtitle = null;
  }
}
