// Generated code from Butter Knife. Do not modify!
package tech.jiangtao.support.ui.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import tech.jiangtao.support.ui.R;

public class AddFriendFragment_ViewBinding implements Unbinder {
  private AddFriendFragment target;

  @UiThread
  public AddFriendFragment_ViewBinding(AddFriendFragment target, View source) {
    this.target = target;

    target.mSearchView = Utils.findRequiredViewAsType(source, R.id.friend_edit, "field 'mSearchView'", SearchView.class);
    target.mFriendContaner = Utils.findRequiredViewAsType(source, R.id.friend_list, "field 'mFriendContaner'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AddFriendFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mSearchView = null;
    target.mFriendContaner = null;
  }
}
