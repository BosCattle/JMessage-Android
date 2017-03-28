// Generated code from Butter Knife. Do not modify!
package tech.jiangtao.support.ui.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.kevin.library.widget.SideBar;
import java.lang.IllegalStateException;
import java.lang.Override;
import tech.jiangtao.support.ui.R;

public class ContactFragment_ViewBinding implements Unbinder {
  private ContactFragment target;

  @UiThread
  public ContactFragment_ViewBinding(ContactFragment target, View source) {
    this.target = target;

    target.mContactList = Utils.findRequiredViewAsType(source, R.id.contact_list, "field 'mContactList'", RecyclerView.class);
    target.mSideBar = Utils.findRequiredViewAsType(source, R.id.sidebar, "field 'mSideBar'", SideBar.class);
    target.mUiViewBuddle = Utils.findRequiredViewAsType(source, R.id.ui_view_bubble, "field 'mUiViewBuddle'", TextView.class);
    target.mSwipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.contact_swift_refresh, "field 'mSwipeRefreshLayout'", SwipeRefreshLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ContactFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mContactList = null;
    target.mSideBar = null;
    target.mUiViewBuddle = null;
    target.mSwipeRefreshLayout = null;
  }
}
