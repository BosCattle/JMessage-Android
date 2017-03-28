// Generated code from Butter Knife. Do not modify!
package tech.jiangtao.support.ui.viewholder;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import tech.jiangtao.support.ui.R;

public class ExtraFuncViewHolder_ViewBinding implements Unbinder {
  private ExtraFuncViewHolder target;

  @UiThread
  public ExtraFuncViewHolder_ViewBinding(ExtraFuncViewHolder target, View source) {
    this.target = target;

    target.mChatFuncExtra = Utils.findRequiredViewAsType(source, R.id.chat_func_extra, "field 'mChatFuncExtra'", ImageView.class);
    target.mChatFuncExtraText = Utils.findRequiredViewAsType(source, R.id.chat_func_extra_text, "field 'mChatFuncExtraText'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ExtraFuncViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mChatFuncExtra = null;
    target.mChatFuncExtraText = null;
  }
}
