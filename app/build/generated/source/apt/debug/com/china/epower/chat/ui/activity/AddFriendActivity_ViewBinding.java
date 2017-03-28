// Generated code from Butter Knife. Do not modify!
package com.china.epower.chat.ui.activity;

import android.support.annotation.UiThread;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.internal.Utils;
import com.china.epower.chat.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddFriendActivity_ViewBinding extends BaseActivity_ViewBinding {
  private AddFriendActivity target;

  @UiThread
  public AddFriendActivity_ViewBinding(AddFriendActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AddFriendActivity_ViewBinding(AddFriendActivity target, View source) {
    super(target, source);

    this.target = target;

    target.mTvToolbar = Utils.findRequiredViewAsType(source, R.id.tv_toolbar, "field 'mTvToolbar'", TextView.class);
    target.mToolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'mToolbar'", Toolbar.class);
    target.mAddFriend = Utils.findRequiredViewAsType(source, R.id.add_friend, "field 'mAddFriend'", FrameLayout.class);
  }

  @Override
  public void unbind() {
    AddFriendActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mTvToolbar = null;
    target.mToolbar = null;
    target.mAddFriend = null;

    super.unbind();
  }
}
