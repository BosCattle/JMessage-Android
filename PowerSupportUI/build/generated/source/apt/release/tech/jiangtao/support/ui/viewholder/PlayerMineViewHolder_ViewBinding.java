// Generated code from Butter Knife. Do not modify!
package tech.jiangtao.support.ui.viewholder;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import tech.jiangtao.support.ui.R;

public class PlayerMineViewHolder_ViewBinding implements Unbinder {
  private PlayerMineViewHolder target;

  @UiThread
  public PlayerMineViewHolder_ViewBinding(PlayerMineViewHolder target, View source) {
    this.target = target;

    target.mItemChatAvatar = Utils.findRequiredViewAsType(source, R.id.item_chat_avatar, "field 'mItemChatAvatar'", ImageView.class);
    target.mViewPlayerStyle = Utils.findRequiredView(source, R.id.view_player_style, "field 'mViewPlayerStyle'");
    target.mViewPlayerContainer = Utils.findRequiredViewAsType(source, R.id.view_player_container, "field 'mViewPlayerContainer'", FrameLayout.class);
    target.mItemChatMessage = Utils.findRequiredViewAsType(source, R.id.item_chat_message, "field 'mItemChatMessage'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PlayerMineViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mItemChatAvatar = null;
    target.mViewPlayerStyle = null;
    target.mViewPlayerContainer = null;
    target.mItemChatMessage = null;
  }
}
