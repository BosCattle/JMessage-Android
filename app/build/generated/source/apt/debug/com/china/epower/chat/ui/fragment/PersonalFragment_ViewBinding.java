// Generated code from Butter Knife. Do not modify!
package com.china.epower.chat.ui.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.china.epower.chat.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PersonalFragment_ViewBinding implements Unbinder {
  private PersonalFragment target;

  private View view2131689635;

  @UiThread
  public PersonalFragment_ViewBinding(final PersonalFragment target, View source) {
    this.target = target;

    View view;
    target.mPersonalList = Utils.findRequiredViewAsType(source, R.id.personal_list, "field 'mPersonalList'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.login_button, "field 'mLoginButton' and method 'onClick'");
    target.mLoginButton = Utils.castView(view, R.id.login_button, "field 'mLoginButton'", AppCompatButton.class);
    view2131689635 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    PersonalFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mPersonalList = null;
    target.mLoginButton = null;

    view2131689635.setOnClickListener(null);
    view2131689635 = null;
  }
}
