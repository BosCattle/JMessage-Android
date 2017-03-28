// Generated code from Butter Knife. Do not modify!
package tech.jiangtao.support.ui.viewholder;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import tech.jiangtao.support.ui.R;

public class ContactCellViewHolder_ViewBinding implements Unbinder {
  private ContactCellViewHolder target;

  @UiThread
  public ContactCellViewHolder_ViewBinding(ContactCellViewHolder target, View source) {
    this.target = target;

    target.mLetterCell = Utils.findRequiredViewAsType(source, R.id.letter_cell, "field 'mLetterCell'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ContactCellViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLetterCell = null;
  }
}
