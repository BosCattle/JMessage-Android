// Generated code from Butter Knife. Do not modify!
package tech.jiangtao.support.ui.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import tech.jiangtao.support.ui.R;

public class ChatListFragment_ViewBinding implements Unbinder {
  private ChatListFragment target;

  @UiThread
  public ChatListFragment_ViewBinding(ChatListFragment target, View source) {
    this.target = target;

    target.mChatList = Utils.findRequiredViewAsType(source, R.id.chat_list, "field 'mChatList'", RecyclerView.class);
    target.mSwipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.chat_swift_refresh, "field 'mSwipeRefreshLayout'", SwipeRefreshLayout.class);
    target.mImageView = Utils.findRequiredViewAsType(source, R.id.chat_frame, "field 'mImageView'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ChatListFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mChatList = null;
    target.mSwipeRefreshLayout = null;
    target.mImageView = null;
  }
}
