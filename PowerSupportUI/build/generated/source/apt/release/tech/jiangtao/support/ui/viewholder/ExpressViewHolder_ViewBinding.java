// Generated code from Butter Knife. Do not modify!
package tech.jiangtao.support.ui.viewholder;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import tech.jiangtao.support.ui.R;

public class ExpressViewHolder_ViewBinding implements Unbinder {
  private ExpressViewHolder target;

  @UiThread
  public ExpressViewHolder_ViewBinding(ExpressViewHolder target, View source) {
    this.target = target;

    target.mChatExpress = Utils.findRequiredViewAsType(source, R.id.chat_express, "field 'mChatExpress'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ExpressViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mChatExpress = null;
  }
}
